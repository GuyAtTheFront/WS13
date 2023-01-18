package com.example.ws13.repositories;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.ws13.models.Contact;

import io.netty.util.internal.ThreadLocalRandom;

@Repository
public class ContactsRedis {
    
    @Autowired
    RedisTemplate<String, Object> template;

    private final String ID_TO_NAME_HASH = "idToNameHash";
    
    public void createContact(Contact contact) {

        String id = generateId();
        while (null != template.opsForHash().get(id, "name")) {
            id = generateId();
        }

        System.out.println("ID -->" + id);
        template.opsForHash().put(id, "name", contact.getName());
        template.opsForHash().put(id, "email", contact.getEmail());
        template.opsForHash().put(id, "phoneNumber", contact.getPhoneNumber());
        template.opsForHash().put(id, "dateOfBirth", contact.getDateOfBirth());
        
        template.opsForHash().put(ID_TO_NAME_HASH, id, contact.getName());
        System.out.println("createContact --> created");
        return;
    }

    public Contact getContactByID(String id) {
        Map<Object, Object> res = template.opsForHash().entries(id);
        Contact contact = new Contact();
        contact.setName((String) res.get("name"));
        contact.setEmail((String) res.get("email"));
        contact.setPhoneNumber((String) res.get("phoneNumber"));
        contact.setDateOfBirth((String) res.get("dateOfBirth"));
        return contact;
    }

    public Map<Object, Object> getAllIdAndNames() {
        Map<Object, Object> ids = template.opsForHash().entries(ID_TO_NAME_HASH);
        System.out.println("getAllIdAndNames --> " + ids.keySet());
        // how to convert Map<Object, Object> to Map<String, String?
        // This is NOT fine :sadage:
        return ids;             
    }


    private static String generateId() {
    
        long max = Long.parseLong("ffffffff", 16);
        
        long randLong = ThreadLocalRandom.current().nextLong(0, max + 1);
    
        String fname = ("00000000" + Long.toHexString(randLong)).substring(8);
        return fname;
    };
    
}

package com.example.ws13.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ws13.models.Contact;
import com.example.ws13.repositories.ContactsRedis;
import com.example.ws13.utils.Contacts;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    Contacts contacts;
    
    @Autowired
    ContactsRedis contactsRedis;

    @Value("${dataDir}")
    private String fileDir;

    public String index() {
        return "index";
    }

    @GetMapping("/form")
    public String form(@ModelAttribute Contact contact) {
        return "form";
    }

    // @GetMapping("/contact")
    // public String getContact() {
    //     return
    // }

    @PostMapping("/contact")
    public String contact(
        @Valid Contact contact, 
        BindingResult bindingResult,
        Model model, 
        HttpServletResponse response) {

        contact.invalidDateOfBirth(bindingResult);
        if(bindingResult.hasErrors()){
            ObjectError err = new ObjectError("dateOfBirth", "test error");
            bindingResult.addError(err);
            FieldError err2 = new FieldError("dateOfBirth", "dateOfBirth", "field error");
            bindingResult.addError(err2);
            System.out.println(bindingResult.toString());
            return "form";
        }

        // if(bindingResult.hasErrors()) {
        //     // System.out.println("hello");
        //     return "form";
        // }

        // if(contact.invalidDateOfBirth(bindingResult)) {
        //     System.out.print("invalid dob");
            
        //     return "form";
        // }

        // System.out.println("bye");
        
        
        
        //-------------- Updated ---------------
        // Contacts.saveToFile(contact, fileDir);
        contactsRedis.createContact(contact);
        //--------------------------------------

        // System.out.println("Controller --> saving to redis");
        response.setStatus(HttpServletResponse.SC_CREATED);
        return "result";
    }

    @GetMapping("/contact/{id}")
    public String result(Model model, @PathVariable String id) {

        //--------- Updated ----------
        // Contact contact = Contacts.getContact(id, fileDir);
        Contact contact = contactsRedis.getContactByID(id);
        //----------------------------

        model.addAttribute("contact", contact);
        
        // System.out.println(contact);
        return "result";
    }

    @GetMapping("/list")
    public String contacts(Model model) {
        // TODO
        // HashMap<String, Contact> names = Contacts.getAllFiles(fileDir);
        Map<Object, Object> names = contactsRedis.getAllIdAndNames();
        model.addAttribute("names", names);
        // model.addAttribute("test", "test");
        System.out.println("Printing names... " + names);
        return "contacts";
    }
}

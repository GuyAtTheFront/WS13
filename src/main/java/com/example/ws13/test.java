package com.example.ws13;

import com.example.ws13.models.Contact;
import com.example.ws13.utils.Contacts;

public class test {
    public static void main(String[] args) {
    String BASE_DIR = "src";

    Contact contact = new Contact();
    contact.setName("name");
    contact.setEmail("email");
    contact.setPhoneNumber("number");
    contact.setDateOfBirth("date");
    
    Contacts.saveToFile(contact, BASE_DIR);
    
    }
}

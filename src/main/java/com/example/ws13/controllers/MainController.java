package com.example.ws13.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ws13.models.Contact;
import com.example.ws13.utils.Contacts;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    Contacts contacts;

    @Value("${dataDir}")
    private String fileDir;

    public String index() {
        return "index";
    }

    @GetMapping("/form")
    public String form(@ModelAttribute Contact contact) {
        System.out.println("hello\n\n\n\n\n\n");
        return "form";
    }

    @PostMapping("/contact")
    public String contact(
        @Valid Contact contact, 
        BindingResult bindingResult,
        Model model, 
        HttpServletResponse response) {

        contact.invalidDateOfBirth(bindingResult);
        if(bindingResult.hasErrors()){
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
        Contacts.saveToFile(contact, fileDir);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return "result";
    }

    @GetMapping("/contact/{id}")
    public String result(Model model, @PathVariable String id) {

        Contact contact = Contacts.getContact(id, fileDir);
        model.addAttribute("contact", contact);
        
        // System.out.println(contact);
        return "result";
    }

    @GetMapping("/list")
    public String contacts(Model model) {
        HashMap<String, Contact> names = Contacts.getAllFiles(fileDir);
        model.addAttribute("names", names);
        model.addAttribute("test", "test");
        // System.out.println("Printing names... " + names);
        return "contacts";
    }
}

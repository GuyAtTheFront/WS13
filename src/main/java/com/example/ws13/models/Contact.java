package com.example.ws13.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import jakarta.validation.constraints.*;

// This class a POJO (plain-old-java-object)
// It serves to hold form data
// It is bound to a form

public class Contact {

    @NotBlank
    @Size(min=3, max=64, message="Name must be Between 3 and 64 characters")
    private String name;
    
    @Email(message = "Invalid email format")
    private String email;
    
    @Pattern(regexp = "^[()+*#-]*(?:\\d[()+*#-]*){7,}$", message="Number must contain at least 7 digits")
    private String phoneNumber;
    
    // Custom Validation
    private String dateOfBirth;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name.trim();
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email.trim();
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber.trim();
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth.trim();
    }
    
    @Override
    public String toString() {
        return "Contact [name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", dateOfBirth="
                + dateOfBirth + "]";
    }

    public boolean invalidDateOfBirth(BindingResult bindingResult) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        // System.out.println("testing dob");
        LocalDate date = null;
        ObjectError err = null;
        
        // Check Format dd-MMM-yyyy
        try {
            date = LocalDate.parse(this.getDateOfBirth(), formatter);
            
        } catch (DateTimeParseException e) {
            // System.out.println("failed");
            err = new ObjectError("globalError", "date must be in dd-Mmm-yyyy");
            bindingResult.addError(err);
            return true;
        }

        // Check past
        if(date.isAfter(LocalDate.now())) {
            err = new ObjectError("globalError", "date must be past");
            bindingResult.addError(err);
            return true;
        }
            
        // Check Age between 10 and 100  (now = 2023)
        LocalDate latestDate = LocalDate.now().minusYears(10); // 2013
        LocalDate earliestDate = LocalDate.now().minusYears(100); // 1923
        if(date.isBefore(earliestDate) || date.isAfter(latestDate)) {
            err = new ObjectError("globalError", "you are not between 10-100 years old");
            bindingResult.addError(err);
            return true;
        }

        return false;

    }
}

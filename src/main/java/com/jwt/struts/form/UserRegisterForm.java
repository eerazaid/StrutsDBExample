package com.jwt.struts.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.validator.ValidatorForm;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class UserRegisterForm extends ActionForm {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    
    @Override
    public void reset(org.apache.struts.action.ActionMapping mapping, javax.servlet.http.HttpServletRequest request) {
        // Reset form fields
        this.firstName = "";
        this.lastName = "";
        this.email = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.jwt.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.Action;

import com.jwt.struts.dao.UserRegisterDAO;
import com.jwt.struts.form.UserRegisterForm;

public class UserAddAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserRegisterForm userForm = (UserRegisterForm) form;
        UserRegisterDAO dao = new UserRegisterDAO();

        ActionErrors errors = new ActionErrors();
     // Validate form fields
        if (userForm.getFirstName() == null || userForm.getFirstName().trim().isEmpty()) {
            errors.add("firstName", new ActionMessage("error.user.firstName.required"));
        } else if (!userForm.getFirstName().matches("^[a-zA-Z\\s]+$")) {
            errors.add("firstName", new ActionMessage("error.user.firstName.invalid"));
        }

        if (userForm.getLastName() == null || userForm.getLastName().trim().isEmpty()) {
            errors.add("lastName", new ActionMessage("error.user.lastName.required"));
        } else if (!userForm.getLastName().matches("^[a-zA-Z\\s]+$")) {
            errors.add("lastName", new ActionMessage("error.user.lastName.invalid"));
        }

        if (userForm.getEmail() == null || userForm.getEmail().trim().isEmpty()) {
            errors.add("email", new ActionMessage("error.user.email.required"));
        } else if (!userForm.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            errors.add("email", new ActionMessage("error.user.email.invalid"));
        }


        // If there are validation errors, save them and return to the input page
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("input"); // Return to input page with errors
        }

        // Insert the new user into the database
        dao.insertData(userForm.getFirstName(), userForm.getLastName(), userForm.getEmail());

        // Fetch updated user list and set it in request
        List<UserRegisterForm> users = dao.getAllUsers();
        request.setAttribute("userList", users);

        // Clear the form fields for the add user form
        userForm.reset(mapping, request);

        // Redirect back to the user list page
        return mapping.findForward("success");
    }
}

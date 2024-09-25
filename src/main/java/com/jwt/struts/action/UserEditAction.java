package com.jwt.struts.action;

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

public class UserEditAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserRegisterForm userForm = (UserRegisterForm) form;

        // If this is a GET request (edit), we need to fetch the user details
        if (request.getMethod().equalsIgnoreCase("GET")) {
            int userId = Integer.parseInt(request.getParameter("id")); // Get the ID from the request
            UserRegisterDAO dao = new UserRegisterDAO();
            UserRegisterForm user = dao.getUserById(userId); // Fetch the user details

            if (user != null) {
                // Populate the form with user data
                userForm.setId(user.getId());
                userForm.setFirstName(user.getFirstName());
                userForm.setLastName(user.getLastName());
                userForm.setEmail(user.getEmail());
            }

            return mapping.findForward("edit"); // Forward to edit page
        } else { // This is a POST request (update)
            // Validate form fields
            ActionErrors errors = new ActionErrors();

            if (userForm.getFirstName() == null || userForm.getFirstName().trim().isEmpty()) {
                errors.add("firstName", new ActionMessage("error.user.firstName.required"));
            } else if (!userForm.getFirstName().matches("^[a-zA-Z\\s]+$")) {  // Corrected regex pattern
                errors.add("firstName", new ActionMessage("error.user.firstName.invalid"));
            }

            if (userForm.getLastName() == null || userForm.getLastName().trim().isEmpty()) {
                errors.add("lastName", new ActionMessage("error.user.lastName.required"));
            } else if (!userForm.getLastName().matches("^[a-zA-Z\\s]+$")) {  // Corrected regex pattern
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
                return mapping.findForward("edit"); // Return to the edit page with errors
            }

            // Update the user with the values from the form
            UserRegisterDAO dao = new UserRegisterDAO();
            dao.updateUser(userForm, userForm.getId());

            // Clear the form fields for the add user form
            userForm.reset(mapping, request);
            // After successful update, redirect to user list page
            return mapping.findForward("success");
        }
    }
}

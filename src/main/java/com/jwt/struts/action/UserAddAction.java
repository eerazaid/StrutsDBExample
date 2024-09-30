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
        } else if (!userForm.getFirstName().matches("^[a-zA-Z\\s/@'-]+$")) {
            errors.add("firstName", new ActionMessage("error.user.firstName.invalid"));
        }

        if (userForm.getLastName() == null || userForm.getLastName().trim().isEmpty()) {
            errors.add("lastName", new ActionMessage("error.user.lastName.required"));
        } else if (!userForm.getLastName().matches("^[a-zA-Z\\s/@'-]+$")) {
            errors.add("lastName", new ActionMessage("error.user.lastName.invalid"));
        }

        if (userForm.getEmail() == null || userForm.getEmail().trim().isEmpty()) {
            errors.add("email", new ActionMessage("error.user.email.required"));
        } else if (!userForm.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            errors.add("email", new ActionMessage("error.user.email.invalid"));
        }

        if (userForm.getPassword() == null || userForm.getPassword().trim().isEmpty()) {
            errors.add("password", new ActionMessage("error.user.password.required"));
        }

        if (userForm.getConfirmPassword() == null || userForm.getConfirmPassword().trim().isEmpty()) {
            errors.add("confirmPassword", new ActionMessage("error.user.confirmPassword.required"));
        } else if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            errors.add("confirmPassword", new ActionMessage("error.user.password.mismatch"));
        }

        if (userForm.getLoginId() == null || userForm.getLoginId().trim().isEmpty()) {
            errors.add("loginId", new ActionMessage("error.user.loginId.required"));
        } else if (!userForm.getLoginId().matches("^[a-zA-Z0-9_]+$")) {
            errors.add("loginId", new ActionMessage("error.user.loginId.invalid"));
        } else if (dao.isLoginIdExists(userForm.getLoginId())) {
            errors.add("loginId", new ActionMessage("error.user.loginId.exists"));
        }

        // If there are  errors, save them and return to the input page
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("add"); // Return to input page with errors
        }

        // Insert the new user into the database 
        dao.insertData(userForm.getLoginId(), userForm.getFirstName(), userForm.getLastName(), userForm.getEmail(), userForm.getPassword());

        // Fetch updated user list and set it in request
        List<UserRegisterForm> users = dao.getAllUsers();
        request.setAttribute("userList", users);

        // Clear the form fields for the add user form
        userForm.reset(mapping, request);

        // Redirect to userList action to prevent form resubmission
        return new ActionForward("/userList.do", true); // Set the redirect flag to true
    }
}

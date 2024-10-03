package com.jwt.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import com.jwt.struts.dao.UserRegisterDAO;
import com.jwt.struts.form.UserRegisterForm;

public class UserEditAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserRegisterForm userForm = (UserRegisterForm) form;
        UserRegisterDAO dao = new UserRegisterDAO();

        if (request.getMethod().equalsIgnoreCase("GET")) {
            int userId = Integer.parseInt(request.getParameter("id")); // Get the ID from the request
            UserRegisterForm user = dao.getUserById(userId); // Fetch the user details

            if (user != null) {
                userForm.setId(user.getId());
                userForm.setFirstName(user.getFirstName());
                userForm.setLastName(user.getLastName());
                userForm.setEmail(user.getEmail());
                userForm.setLoginId(user.getLoginId());

                // Logging user details for debugging
                System.out.println("Id : " + user.getId());
                System.out.println("loginId : " + user.getLoginId());
            } else {
                // Handle case where user is not found
                ActionErrors errors = new ActionErrors();
                errors.add("userNotFound", new ActionMessage("error.user.notFound"));
                saveErrors(request, errors);
                return mapping.findForward("error"); // Forward to an error page
            }

            return mapping.findForward("edit"); // Forward to edit page
        } else {
            ActionErrors errors = new ActionErrors();

            // Validate first name
            if (userForm.getFirstName() == null || userForm.getFirstName().trim().isEmpty()) {
                errors.add("firstName", new ActionMessage("error.user.firstName.required"));
            } else if (!userForm.getFirstName().matches("^[a-zA-Z\\s]+$")) {
                errors.add("firstName", new ActionMessage("error.user.firstName.invalid"));
            }

            // Validate last name
            if (userForm.getLastName() == null || userForm.getLastName().trim().isEmpty()) {
                errors.add("lastName", new ActionMessage("error.user.lastName.required"));
            } else if (!userForm.getLastName().matches("^[a-zA-Z\\s]+$")) {
                errors.add("lastName", new ActionMessage("error.user.lastName.invalid"));
            }

            // Validate email
            if (userForm.getEmail() == null || userForm.getEmail().trim().isEmpty()) {
                errors.add("email", new ActionMessage("error.user.email.required"));
            } else if (!userForm.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                errors.add("email", new ActionMessage("error.user.email.invalid"));
            }

            // Check if the user is attempting to change the password
            String currentPassword = userForm.getCurrentPassword();
            String newPassword = userForm.getNewPassword();
            String confirmPassword = userForm.getConfirmPassword();

            boolean isPasswordChange = (currentPassword != null && !currentPassword.trim().isEmpty()) ||
                                       (newPassword != null && !newPassword.trim().isEmpty()) ||
                                       (confirmPassword != null && !confirmPassword.trim().isEmpty());

            if (isPasswordChange) {
                // Validate current password
                if (currentPassword == null || currentPassword.trim().isEmpty()) {
                    errors.add("currentPassword", new ActionMessage("error.user.currentPassword.required"));
                } else {
                    boolean isCurrentPasswordValid = dao.verifyCurrentPassword(userForm.getLoginId(), currentPassword);
                    if (!isCurrentPasswordValid) {
                        errors.add("currentPassword", new ActionMessage("error.user.password.invalid"));
                    }
                }

                // Validate new password
                if (newPassword == null || newPassword.trim().isEmpty()) {
                    errors.add("newPassword", new ActionMessage("error.user.newPassword.required"));
                }

                // Validate confirm password
                if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
                    errors.add("confirmPassword", new ActionMessage("error.user.confirmPassword.required"));
                }

                // Check if new password and confirm password match
                if (newPassword != null && confirmPassword != null && !newPassword.equals(confirmPassword)) {
                    errors.add("confirmPassword", new ActionMessage("error.user.confirmPassword.mismatch"));
                }

                // If there are errors, save them and return to the edit page
                if (!errors.isEmpty()) {
                    saveErrors(request, errors);
                    return mapping.findForward("edit");
                }

                // Update the password in the database if validation passes
                dao.updatePassword(userForm.getLoginId(), newPassword, confirmPassword);
                System.out.println("Password updated successfully for loginId: " + userForm.getLoginId());
            }

            // Update the user with the form values excluding password
          //  dao.updateUser(userForm, userForm.getId());
            userForm.reset(mapping, request);
            return mapping.findForward("success");
            
            
        }
        
        
    }
}

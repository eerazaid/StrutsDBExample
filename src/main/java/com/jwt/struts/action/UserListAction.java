package com.jwt.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.jwt.struts.dao.UserRegisterDAO;
import java.util.List;
import com.jwt.struts.form.UserRegisterForm;

public class UserListAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        UserRegisterDAO dao = new UserRegisterDAO();
        List<UserRegisterForm> users = dao.getAllUsers();
        request.setAttribute("userList", users);
        return mapping.findForward("success");
    }
}

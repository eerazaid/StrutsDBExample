package com.jwt.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.jwt.struts.dao.UserRegisterDAO;

public class UserDeleteAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String userId = request.getParameter("id");
        UserRegisterDAO dao = new UserRegisterDAO();
        dao.deleteUser(Integer.parseInt(userId));
        return mapping.findForward("success");
    }
}

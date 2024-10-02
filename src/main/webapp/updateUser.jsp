<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<jsp:include page="/inc/header.jsp" />


<html>
<head>
    <title>Update User</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>
    <div class="wrapper">
        <div class="registration_form">
            <div class="title">
                Update User
            </div>
            <div style="color:red;">
                <html:errors/>
            </div>

            <div class="form_wrap">
                <html:form action="/updateUser" method="post">
                    <html:hidden property="id"/>
                    
                   
					
                    <div class="input_wrap">
                        <label>
                            <bean:message key="label.user.firstName" /> :
                            <html:text property="firstName" size="25" />
                        </label>
                    </div>
                    <div class="input_wrap">
                        <label>
                            <bean:message key="label.user.lastName" /> :
                            <html:text property="lastName" size="25" />
                        </label>
                    </div>
                    <div class="input_wrap">
                        <label>
                            <bean:message key="label.user.email" /> :
                            <html:text property="email" size="25" />
                        </label>
                    </div>
                    <div class="input_wrap">
                        <label>
                            <bean:message key="label.user.currentPassword" /> :
                            <html:text property="currentPassword" size="25" />
                        </label>
                    </div>
                    <div class="input_wrap">
                        <label>
                            <bean:message key="label.user.newPassword" /> :
                            <html:text property="newPassword" size="25" />
                        </label>
                    </div>
                    <div class="input_wrap">
                        <label>
                            <bean:message key="label.user.confirmPassword" /> : <!-- Changed to confirmNewPassword -->
                            <html:text property="confirmPassword" size="25" />
                        </label>
                    </div>

                    <html:hidden property="loginId"/> <!-- Hidden field for loginId -->
                    
                    <div class="input_wrap">
					    <html:submit value="Update" styleClass="submit_btn"/>
					</div>

                </html:form>
            </div>

            <form action="userList.do">
                	<input type="submit" value="Back" class="submit_btn"/>
            	</form>
        </div>
    </div>
</body>
<jsp:include page="/inc/footer.jsp" />

</html>

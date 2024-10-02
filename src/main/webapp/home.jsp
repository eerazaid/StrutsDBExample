<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<jsp:include page="/inc/header.jsp" />

<html>
<head>
    <title>Home Page</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/styles.css">    
</head>


<body>
    <div class="wrapper">
        <div class="welcome_message" style="text-align: center;">
            <p style="color: white;">Click the button below to view the user table. <br></p>
            <div class="button_wrap" style="margin-top: 20px;">
	            <form action="userList.do" method="post">
				    <input type="submit" value="View User Table" class="submit_btn"/>
				</form>
	        </div>
        </div>
        
    </div>
</body>

<jsp:include page="/inc/footer.jsp" />

</html>

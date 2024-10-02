<!-- header.jsp -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Struts DB Example</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/styles.css">
	<link rel="stylesheet" href="https://cdn.datatables.net/2.1.7/css/dataTables.dataTables.css">
	<link rel="stylesheet" href="https://cdn.datatables.net/buttons/3.1.2/css/buttons.dataTables.css">
</head>
<body>
    <header>
        <h4 class="header-title">Welcome to User Management</h4>
        
        <nav style="text-align:right;">
            <ul>
                <li><a href="home.jsp">Home</a></li>
                <li><a href="userList.do">User List</a></li>
            </ul>
        </nav>
        
        <img src="<%= request.getContextPath() %>/img/icon.png" alt="Icon" class="header-icon" style="width: 30px;"/>
    </header>
    <hr>
</body>
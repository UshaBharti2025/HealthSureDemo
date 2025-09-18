<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<f:view>
<html>
<head>
    <title>Recipient Dashboard</title>
    <style>
        body {
            background-color: #f3f4f6; /* Light gray bg */
            color: #1f2937; /* Dark gray text */
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        .navbar {
            background-color: #1e3a8a; /* Deep blue */
            padding: 15px 0;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
            display: flex;
            justify-content: center;
            gap: 30px;
            margin-bottom: 2rem;
        }


        .nav-link {
            color: white;
            text-decoration: none;
            font-size: 18px;
            font-weight: 600;
            transition: color 0.3s ease;
        }

        .nav-link:hover {
            color: #facc15; /* Yellow hover */
        }

        .content {
            text-align: center;
            margin-top: 60px;
        }

        .content h2 {
            font-size: 28px;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .content p {
            font-size: 18px;
            color: #4b5563; /* Soft gray */
        }
    </style>
</head>
<body>

    <!--Navbar -->
    <div class="navbar">
        <h:form style="display:inline;">
            <h:commandLink value="Dashboard" action="dashboard1" styleClass="nav-link" />
        </h:form>
        <h:form style="display:inline;">
            <h:commandLink value="Search" action="SearchRecipient1" styleClass="nav-link" />
        </h:form>
        <h:form style="display:inline;">
            <h:commandLink value="Show" action="#{recipientController.goToShowPage}"
 styleClass="nav-link" />
        </h:form>
        <h:form style="display:inline;">
            <h:commandLink value="Update" action="Update1" styleClass="nav-link" />
        </h:form>
    </div>


    <!--Welcome Content -->
    <div class="content">
        <h2>Welcome to Recipient Dashboard</h2>
        <p>Use the navbar above to Search, Show, or Update Recipients.</p>
    </div>

</body>
</html>
</f:view>

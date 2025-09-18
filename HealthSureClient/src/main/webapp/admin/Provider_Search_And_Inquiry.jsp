 
<!--
  JSP File: Provider and Doctor Search Page
  Purpose: This page allows users to search for doctors or providers based on specific criteria.
  Users can select the category (Doctor or Provider), the criteria for search (e.g., ID, name), and enter a search value to find matching results.
  The page also handles errors gracefully, displaying error messages if necessary, and allows for an easy return to the admin dashboard.
 
  Components:
  - Category Dropdown: Allows the user to select whether they want to search for doctors or providers.
  - Criteria Dropdown: Once a category is selected, users can choose the search criteria, like ID or name.
  - Input Box: The user enters the search value based on the selected criteria.
  - Search Button: Initiates the search process based on the selected category, criteria, and input.
  - Error Message: Displays any error messages that may arise during the search process.
  - Footer: Displays copyright information at the bottom of the page.
 
  Design:
  - The page is designed with a clean, user-friendly interface using pastel colors (background: #f1f8f6, buttons: #00796B).
  - A centered container houses all the form elements and buttons, creating a neat, focused layout.
  - The search fields are designed to be dynamic; depending on the category selected, different options appear for further refinement of the search.
  - Each component is styled to provide a modern, minimalistic feel with rounded corners, subtle borders, and hover effects for interactive elements.
  - Error messages are highlighted in red, ensuring the user can easily identify and fix issues.
 
  Additional Notes:
  - The page dynamically updates based on the user's selection of category and search criteria, driven by JSF managed beans (e.g., `doctorController.searchType`, `doctorController.searchCriteria`).
  - Form submission is triggered when the category dropdown changes, allowing for a smooth user experience without page refresh.
  - The search input and criteria options are linked to backend functionality to ensure relevant data is fetched and displayed.
 
-->
 
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Provider And Doctor Search</title>
 
<style>
body {
 
   
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	background-color: #f1f8f6;
	color: #333;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
	padding:0;
	height: 100vh;
}
 
.container {
	text-align: center;
	width: 450px;
	background-color: #ffffff;
	padding: 40px 30px 40px 43px;
	border-radius: 12px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
}
 
.header {
 
	font-size: 25px;
	font-weight: bold;
	margin-bottom: 40px;
	color: #00796b; /* Elegant darker green */
	letter-spacing: 1px;
}
 
.form-group {
	width: 100%;
	margin-bottom: 20px;
}
 
.form-group label {
	font-size: 1.1em;
	display: block;
	margin-bottom: 8px;
}
 
.menu-button {
	width: 90%;
	padding: 8px;
	font-size: 1em;
	border-radius: 5px;
	border: 1px solid #B2DFDB;
	background-color: #f1f8f6;
	margin-bottom: 15px;
	align-items: center;
	display: flex;
}
 
.input-box {
	width: 82%;
	padding: 8px;
	font-size: 1em;
	border-radius: 5px;
	border: 1px solid #B2DFDB;
	background-color: #f1f8f6;
	margin-bottom: 15px;
	margin-left: 4px;
	align-items: center;
	display: flex;
}
 
.search-button {
	display: inline-block;
	width: 50%;
	padding: 8px;
	text-align: center;
	
	background-color: #00796B;
	color: white;
	align-items: center;
	display: flex;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-size: 1.1em;
	margin-left: 75px;
	
	
	
}
 
.search-button:hover {
	background-color: #004D40;
}
 
.error-message {
	color: red;
	font-size: 0.9em;
}
.action-button {
        background-color: #00796B;
        color: white;
        padding: 10px 20px;
        font-size: 14px;
        border-radius: 8px;
        cursor: pointer;
        transition: background-color 0.3s ease, transform 0.2s ease;
    }
 
    .action-button:hover {
        background-color: #004D40;
        transform: scale(1.05);
    }
 
.footer {
            font-size: 14px;
            color: #666;
            margin-top: 20px;
        }
</style>
</head>
 
<body>
 
	<f:view>
		<div class="container">
			<div class="header">Search Doctors and Providers</div>
 
 
			<h:form>
				<!-- Step 1: Category Dropdown (Doctors or Providers) -->
				<div class="form-group">
					<h:selectOneMenu id="category"
						value="#{doctorController.searchType}"
						onchange="this.form.submit();" styleClass="menu-button">
						<f:selectItem itemLabel="---Select Provider Type---" itemValue="" />
						<f:selectItem itemLabel="Doctor" itemValue="doctor" />
						<f:selectItem itemLabel="Provider" itemValue="provider" />
					</h:selectOneMenu>
				
 
				<!-- Step 2: Criteria Dropdown (appears after selecting category) -->
				<h:selectOneMenu id="criteria"
					value="#{doctorController.searchCriteria}" styleClass="menu-button">
					<f:selectItem itemLabel="---Select Criteria---" itemValue="" />
					<f:selectItems value="#{doctorController.searchCriteriaOptions}" />
				</h:selectOneMenu>
				
 
				<!-- Step 3: Input Box (appears after selecting criteria) -->
				<h:inputText value="#{doctorController.searchInput}"
					styleClass="input-box">
					<f:attribute name="placeholder" value="Enter Your Credentials" />
					
				
				
				</h:inputText>
 
				<!-- Step 4: Search Button (appears after input field) -->
				<h:commandButton value="Search" action="#{doctorController.search}"
					styleClass="search-button" />
						
				</div>
				<!-- Error Message (If any) -->
				<h:messages globalOnly="true" styleClass="error-message" />
				<div class="footer">
                <p>&copy;   2025 Infinite Computer Solution. All rights reserved.</p>
                <!-- Back to Homepage Button -->
					<h:commandButton value="Back to Admin Dashboard"
						action="#{doctorController.backtoadmindashboard}"
						styleClass="action-button" style="margin-top: 20px;" />
					
            </div>
			</h:form>
		</div>
	</f:view>
 
</body>
</html>
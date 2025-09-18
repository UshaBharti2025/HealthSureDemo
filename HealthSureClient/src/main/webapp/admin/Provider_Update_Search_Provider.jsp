
<!-- 
  JSP File: Search Provider Page
  Purpose: This page allows users (e.g., an admin or a user) to search for a provider by their ID. The user inputs the Provider ID, and upon submission, the provider’s details are displayed, or an error message is shown if the provider is not found.
  
  Components:
  - Form: Contains an input field to enter the Provider ID.
  - Search Button: Submits the form and triggers the backend logic to fetch provider details.
  - Error Message: Displays an error message if the provider ID entered is not valid or no such provider is found.
  - Footer: Contains copyright information and is fixed at the bottom of the page.
  
  Design:
  - The page has a clean, minimalistic design with a light blue background color (#E0F7FA) and a centered form.
  - Form elements (input fields) have a light green background and rounded corners to align with the theme.
  - The Search button is styled with a hover effect, improving user interaction.
  - The footer is fixed at the bottom of the page, providing copyright information.
  - Error messages are displayed in red to alert the user to any issues.
-->


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ page import="com.infinite.jsf.provider.model.Doctors"%>

<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Search Provider</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #E0F7FA;
	color: #333;
	display: flex;
	flex-direction: column;
}

.container {
	width: 80%;
	margin: 0 auto;
	background-color: #ffffff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.footer {
	font-size: 14px;
	color: #666;
	margin-top: 20px;
	text-align:center;
}

h2 {
	text-align: center;
	color: #00796B;
}

.form-group {
	margin-bottom: 20px;
}

.form-group label {
	font-size: 1.1em;
	display: block;
	margin-bottom: 8px;
}

input[type="text"] {
	width: 30%;
	padding: 8px;
	font-size: 1em;
	border-radius: 5px;
	border: 1px solid #B2DFDB;
	background-color: #f1f8f6;
	margin-bottom: 15px;
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

.search-btn {
	background-color: #00796B;
	color: white;
	padding: 10px 20px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-size: 1.1em;
}

.search-btn:hover {
	background-color: #004D40;
}

.error {
	color: red;
	font-size: 0.9em;
}
</style>
</head>
<body>
	<f:view>
		<div class="container">
			<h2>Enter Provider ID</h2>

			<!-- Step 1: Input Provider ID -->
			<h:form>
				<h:outputLabel for="providerId" value="Provider ID:" />
				<h:inputText id="providerId"
					value="#{doctorController.provider.providerId}" >
					<f:attribute name="placeholder" value="Enter Provider ID" />
				</h:inputText>

				<!-- Search Button -->
				<h:commandButton value="Get Provider Details"
					action="#{doctorController.showProviderDetails}"
					styleClass="search-btn">
					<!-- Pass providerId as a parameter to the next page -->
					<f:param name="doctorId"
						value="#{doctorController.provider.providerId}" />
				</h:commandButton>
			</h:form>
			
			<!-- Error Message (If any) -->
			<h:outputText value="#{doctorController.errorMessage}"
				rendered="#{not empty doctorController.errorMessage}"
				styleClass="error" />

			<!-- JSF validation message handling -->
			<h:messages styleClass="error" globalOnly="true" />
			<h:form>
				<!-- Back Button -->
			<h:commandButton value="Back"
				action="#{doctorController.backtoupdatemenu}"
				styleClass="action-button" style="margin-top: 20px;" />
			</h:form>
		</div>
		
		<div class="footer">
			<p>&copy; 2025 Infinite Computer Solution. All rights reserved.</p>
		</div>
		
			

	</f:view>
</body>

</html>
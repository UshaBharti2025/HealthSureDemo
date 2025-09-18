<!-- 
  JSP File: Search Doctor Page
  Purpose: This page allows users (e.g., an admin or a user) to search for a doctor by their ID. The user inputs the Doctor ID, and upon submission, the doctor’s details are displayed or an error message is shown if the doctor is not found.
  
  Components:
  - Form: Contains an input field to enter the Doctor ID.
  - Search Button: Submits the form and triggers the backend logic to fetch doctor details.
  - Error Message: Displays an error message if the doctor ID entered is not valid or no such doctor is found.
  - Footer: Contains copyright information and is fixed at the bottom of the page.
  
  Design:
  - The page has a simple and minimalistic design with a light blue background color (#E0F7FA) and a centered form.
  - Form elements (input fields) have a light green background and rounded corners to maintain consistency with the overall theme.
  - The Search button is styled with a hover effect, providing better user interaction.
  - The footer is styled with fixed positioning to remain at the bottom of the page.
  - Error messages are shown in red and are styled appropriately to catch the user's attention.

  Additional Notes:
  - The `doctorId` input field is bound to the `doctorController.doctor.doctorId` in the backing bean.
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
<title>Search Doctor</title>
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
	display: flex;
	justify-content: center;
	align-items: center;
	position: fixed;
	bottom: 20px;
	width: 100%;
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
			<h2>Enter Doctor ID</h2>

			<!-- Step 1: Input Doctor ID -->
			<h:form>
				<h:outputLabel for="doctorId" value="Doctor ID:" />
				<h:inputText id="doctorId"
					value="#{doctorController.doctor.doctorId}">
					<f:attribute name="placeholder" value="Enter Doctor ID" />
					<f:validateLength minimum="1" />
				</h:inputText>
			
				<!-- Search Button -->
				<h:commandButton value="Get Doctor Details"
					action="#{doctorController.showDoctorDetails}"
					styleClass="search-btn">
					<!-- Pass doctorId as a parameter to the next page -->
					<f:param name="doctorId"
						value="#{doctorController.doctor.doctorId}" />
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
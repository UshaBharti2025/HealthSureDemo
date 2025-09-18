<!-- 
  JSP File: Update Provider Details Page
  Purpose: This page allows the admin to update the details of a provider. The form allows for modifications to the provider's name, hospital name, telephone, email, city, and address. After submitting the form, a success or error message is displayed based on the outcome of the update.
  
  Components:
  - Form: Contains fields to edit provider information such as name, hospital name, telephone, etc.
  - Update Button: Submits the form and triggers the update functionality.
  - Success Message: Displays when the provider details have been successfully updated.
  - Error Message: Displays an error message if there was a problem during the update.
  - Back to Homepage Button: A button to navigate back to the admin dashboard.
  - Footer: Contains copyright information.
  
  Design:
  - The page follows a clean, minimalistic design with a pastel background color (#E0F7FA).
  - The form and other components are centered on the page and enclosed in a white container with rounded corners and a subtle shadow.
  - Form fields have a light green background and rounded borders.
  - Buttons have hover effects and smooth transitions for better user interaction.
  - Success and error messages are prominently displayed in green and red, respectively.
  - The footer provides copyright information.

  Additional Notes:
  - The form's action triggers the `updateProviderDetails` method from the `doctorController` bean.
  - Success and error messages are conditionally rendered based on the backend logic.
-->


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@ page import="com.infinite.jsf.provider.model.Doctors"%>

<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Update Provider Details</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #E0F7FA;
	color: #333;
}

.container {
	width: 80%;
	margin: 0 auto;
	background-color: #ffffff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
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
	width: 100%;
	padding: 8px;
	font-size: 1em;
	border-radius: 5px;
	border: 1px solid #B2DFDB;
	background-color: #f1f8f6;
}

.update-btn {
	background-color: #00796B;
	color: white;
	padding: 10px 20px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-size: 1.1em;
}

.update-btn:hover {
	background-color: #004D40;
}
.error-message {
    color: red;
    font-weight: bold;
}

.success {
	color: green;
	font-size: 1.1em;
	text-align: center;
	margin-top: 20px;
}

.error {
	color: red;
	font-size: 0.9em;
	text-align: center;
}

.back-btn {
	background-color: #00796B;
	color: white;
	padding: 10px 20px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-size: 1.1em;
	display: block;
	margin: 20px auto;
}

.back-btn:hover {
	background-color: #004D40;
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
	display: flex;
	justify-content: center;
	align-items: center;
	position: fixed;
	bottom: 20px;
	width: 100%;
	margin-top:30px;
}
</style>
</head>
<body>
	<f:view>
		<div class="container">
			<h2>Edit Provider Details</h2>

			<!-- Provider details form -->
			<h:form>
				<h:inputHidden value="#{doctorController.provider.providerId}" />


				<h:outputLabel for="providerName" value="Provider Name:" />
				<h:inputText id="providerName"
					value="#{doctorController.provider.providerName}" />

				<h:outputLabel for="hospitalName" value="Hospital Name :" />
				<h:inputText id="hospitalName"
					value="#{doctorController.provider.hospitalName}" />

				<h:outputLabel for="telephone" value="Telephone:" />
				<h:inputText id="telephone"
					value="#{doctorController.provider.telephone}"  />

				<h:outputLabel for="email" value="Email:" />
				<h:inputText id="email" value="#{doctorController.provider.email}"
					 />

				<h:outputLabel for="city" value="City:" />
				<h:inputText id="city" value="#{doctorController.provider.city}"
					 />

				<h:outputLabel for="address" value="Address:" />
				<h:inputText id="address"
					value="#{doctorController.provider.address}"  />






				<!-- Update Button -->
				<h:commandButton value="Update Details"
					action="#{doctorController.updateProviderDetails}"
					styleClass="update-btn" style="margin-top:20px" />


				<!-- Success or error message -->
				<h:outputText value="Provider details updated successfully..."
					rendered="#{doctorController.updateSuccess}" styleClass="success" />
				<h:outputText value="#{doctorController.errorMessage}"
					rendered="#{not empty doctorController.errorMessage}"
					styleClass="error" />

				<h:form>
				<div>
					<!-- Back Button -->
					<h:commandButton value="Back"
						action="#{doctorController.backtoupdateprovidersearch}"
						styleClass="action-button" style="margin-top: 20px;" />

					<!-- Back to Homepage Button -->
					<h:commandButton value="Back to Admin Dashboard"
						action="#{doctorController.backtoadmindashboard}"
						styleClass="action-button" style="margin-top: 20px;" />
					</div>
				</h:form>
				
				<div class="footer">
					<p>&copy; 2025 Infinite Computer Solution. All rights reserved.</p>
				</div>
			</h:form>

		</div>
	</f:view>
</body>
</html>
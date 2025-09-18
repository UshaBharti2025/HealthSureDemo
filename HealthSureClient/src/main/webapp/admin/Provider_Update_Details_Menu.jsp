<!-- 
  JSP File: Provider Update Details Page
  Purpose: This page allows the user to navigate to different sections for updating provider and doctor details. It provides menu options for updating providers and doctors, and a "Back to Homepage" button for returning to the admin dashboard.
  
  Components:
  - Header Section: Displays the title of the page ("Provider Update Details").
  - Menu Options: Two links for navigating to the "Update Providers" and "Update Doctors" pages.
  - Back to Homepage Button: A button to redirect the user to the admin dashboard using the `doctorController.backtoadmindashboard` action.
  - Footer: Displays copyright information at the bottom of the page.
  
  Design:
  - The page has a clean, modern design with a soft green background (#f1f8f6) and a centered container that holds the content.
  - The container is styled with a white background, rounded corners, and subtle shadow for a modern, elegant look.
  - Menu buttons have a gradient effect and interactive hover and click effects for a more dynamic user experience.
  - The "Back to Homepage" button is designed with a solid color and hover effects to indicate interactivity.
  
  Additional Notes:
  - The action buttons are styled with hover effects and transitions to improve user experience.
  - The page is fully responsive and will adjust to different screen sizes thanks to the use of flexible layout styles.
-->


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Provider Update Details Menu</title>
<style>
body {
	background-color: #f1f8f6; /* Soft pastel green background */
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	/* Elegant font */
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
}

.container {
	background-color: #ffffff;
	padding: 40px 50px;
	border-radius: 12px;
	box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
	width: 450px;
	text-align: center;
}

.header {
	font-size: 30px;
	font-weight: bold;
	margin-bottom: 40px;
	color: #00796b; /* Elegant darker green */
	letter-spacing: 1px;
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

.menu-button {
	display: inline-block;
	background: linear-gradient(135deg, #00796b, #004d40);
	/* Gradient effect */
	color: white;
	padding: 15px 25px;
	text-align: center;
	text-decoration: none;
	font-size: 18px;
	margin: 15px 0;
	border-radius: 8px;
	width: calc(100% - 30px); /* Adjust the width to account for padding */
	transition: all 0.3s ease-in-out;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
	box-sizing: border-box; /* Ensure padding is included in width */
}

.menu-button:hover {
	background: linear-gradient(135deg, #004d40, #00796b);
	/* Reverse gradient for hover */
	transform: translateY(-4px); /* Slight lift effect */
	box-shadow: 0 8px 15px rgba(0, 0, 0, 0.15); /* Elevation effect */
}

.menu-button:active {
	background: #003d33; /* Darker shade for click */
	transform: translateY(2px); /* Button appears pressed */
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
	/* Light shadow when clicked */
}

.footer {
	font-size: 14px;
	color: #666;
	margin-top: 20px;
}
</style>
</head>
<body>
	<!-- Start of JSF view -->
	<f:view>
		<h:form>
			<div class="container">
				<!-- Header Section -->
				<div class="header">Provider Update Details</div>

				<!-- Menu Options -->
				<a class="menu-button" href="Provider_Update_Search_Provider.jsf">Update
					Providers</a> <a class="menu-button"
					href="Provider_Update_Search_Doctor.jsf">Update Doctors</a>
				<!-- Optional Footer -->
				<div class="footer">
					<p>&copy; 2025 Infinite Computer Solution. All rights reserved.</p>
				</div>
				<!-- Back to Homepage Button -->
					<h:commandButton value="Back to Admin Dashboard"
						action="#{doctorController.backtoadmindashboard}"
						styleClass="action-button" style="margin-top: 20px;" />
					
			</div>

		</h:form>
	</f:view>
	<!-- End of JSF view -->

</body>
</html>

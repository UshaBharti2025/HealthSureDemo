<!-- 
  JSP File: Admin Dashboard - Recipients
  Purpose: This page serves as the admin dashboard interface where administrators can navigate
  to different recipient-related management tasks such as member inquiry and update.
  
  Components:
  - Header: Displays the title of the dashboard in dark navy blue.
  - Menu Buttons: Two buttons for member inquiry and update functionality.
  - Footer: Displays a copyright notice at the bottom of the page.
  
  Design:
  The page uses simple, clean styling with a flexible layout that centers the content.
  Buttons are styled with gradient effects, hover, and active states for a modern UI experience.
-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Admin's Dashboard - Recipients</title>
<style>
body {
	background-color: #f1f8f6;
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	margin-top: 60px;
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
	color: #000080; /* Dark navy blue as requested */
	letter-spacing: 1px;
}

.menu-button {
	display: inline-block;
	background: linear-gradient(135deg, #00796b, #004d40);
	color: white;
	padding: 15px 25px;
	text-align: center;
	text-decoration: none;
	font-size: 18px;
	margin: 15px 0;
	border-radius: 8px;
	width: calc(100% - 30px);
	transition: all 0.3s ease-in-out;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
	box-sizing: border-box;
}

.menu-button:hover {
	background: linear-gradient(135deg, #004d40, #00796b);
	transform: translateY(-4px);
	box-shadow: 0 8px 15px rgba(0, 0, 0, 0.15);
}

.menu-button:active {
	background: #003d33;
	transform: translateY(2px);
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.footer {
	font-size: 14px;
	color: #666;
	margin-top: 20px;
}

/* Additional styles for the new buttons */
.w-full {
	width: 100%;
}

.py-3 {
	padding-top: 0.75rem;
	padding-bottom: 0.75rem;
}

.px-5 {
	padding-left: 1.25rem;
	padding-right: 1.25rem;
}

.bg-teal-600 {
	background-color: #0d9488;
}

.hover\:bg-teal-700:hover {
	background-color: #0f766e;
}

.bg-orange-600 {
	background-color: #ea580c;
}

.hover\:bg-orange-700:hover {
	background-color: #c2410c;
}

.text-white {
	color: white;
}

.rounded-xl {
	border-radius: 0.75rem;
}

.font-medium {
	font-weight: 500;
}

.shadow {
	box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
}

.transition {
	transition-property: background-color, border-color, color, fill, stroke, opacity, box-shadow, transform;
	transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
	transition-duration: 150ms;
}

.button-container {
	margin: 15px 0;
}
</style>
</head>
<body>

	<!-- Start of JSF view -->
	<f:view>
		<jsp:include page="/navbar/NavAdmin.jsp" />
		<h:form>
			<div class="container">
				<!-- Header Section -->
				<div class="header">Admins Recipient Dashboard</div>

				<!-- Menu Options -->
				<div class="button-container">
					<h:commandButton value="👥 Member Inquiry"
						action="/admin/Recipient_Search_MemberDetails?faces-redirect=true"
						styleClass="w-full py-3 px-5 bg-teal-600 hover:bg-teal-700 text-white rounded-xl font-medium shadow transition" />
				</div>
				
				<div class="button-container">
					<h:commandButton value="✏️ Update Member"
						action="/admin/Recipient_Update_MemberDetails?faces-redirect=true"
						styleClass="w-full py-3 px-5 bg-orange-600 hover:bg-orange-700 text-white rounded-xl font-medium shadow transition" />
				</div>

				<div class="footer">
					<p>&copy; 2025 Infinite Computer Solution. All rights reserved.</p>
				</div>
			</div>

		</h:form>
	</f:view>
	<!-- End of JSF view -->

</body>
</html>
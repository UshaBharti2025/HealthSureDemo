<!-- 
  JSP File: Admin Dashboard - Providers
  Purpose: This page serves as the admin dashboard interface where administrators can navigate
  to different provider-related management tasks such as provider review, search, and update details.
  
  Components:
  - Header: Displays the title of the dashboard.
  - Menu Buttons: Links to pages that provide functionality for provider management (review, search, update).
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
<title>Admin's Dashboard - Providers</title>
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
	color: #00796b;
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

</style>
</head>
<body>

	<!-- Start of JSF view -->
	<f:view>
		<jsp:include page="/navbar/NavAdmin.jsp" />
		<h:form>
			<div class="container">
				<!-- Header Section -->
				<div class="header">Admin Dashboard : Providers</div>

				<!-- Menu Options -->
				<a class="menu-button" href="Provider_Review_And_Approval.jsf">Provider Review & Approval</a> 
				<a class="menu-button" href="Provider_Search_And_Inquiry.jsf">Provider Search & Inquiry</a> 
				<a class="menu-button" href="Provider_Update_Details_Menu.jsf">Update Provider Details</a>

				<div class="footer">
					<p>&copy; 2025 Infinite Computer Solution. All rights reserved.</p>
				</div>
			</div>

		</h:form>
	</f:view>
	<!-- End of JSF view -->

</body>
</html>

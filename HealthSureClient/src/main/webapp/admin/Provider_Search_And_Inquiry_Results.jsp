<!-- 
  JSP File: Search Results Page
  Purpose: This page is designed to display the results of a search for doctors and providers. 
  Administrators can view detailed information for each doctor and provider, including their ID, name, specialization, hospital, contact details, etc. 
  The page provides options to navigate back to the admin dashboard and displays a footer with copyright information.

  Components:
  - Doctor Search Results Table: Displays a list of doctors with details such as doctor ID, provider ID, specialization, qualification, email, phone, and address.
  - Provider Search Results Table: Displays a list of providers with details such as provider ID, provider name, hospital name, city, email, status, and phone number.
  - No Results Message: Displays a message when no results are found for doctors or providers.
  - Back to Admin Dashboard Button: A button to navigate back to the admin dashboard.
  - Footer: Displays copyright information at the bottom of the page.

  Design:
  - The page features a clean, modern design with a pastel green theme (#E8F5E9 background, #00796B buttons) for a soft and professional look.
  - The page uses `h:dataTable` to dynamically display the search results for doctors and providers, ensuring that the content is easily updated based on user input.
  - Each table includes clickable column headers to display provider or doctor information, with alternating row colors for improved readability.
  - Responsive design with flexbox layout ensures the page is visually appealing across different screen sizes.
  - The action buttons are styled with hover effects for a smooth user experience.
  - No results are shown if the search yields empty results, providing a clear user feedback mechanism.

  Additional Notes:
  - The page relies on managed beans such as `doctorController.searchResults` and `doctorController.searchResultsP` to fetch data dynamically from the backend.
  - The `rendered` attribute ensures that certain tables or columns are displayed conditionally based on the search results or search type (e.g., doctor or provider).
-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Search Results</title>
<style>
/* Pastel Green Aesthetic Styling */
body {
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	background-color: #E8F5E9;
	color: #333;
	margin: 0;
	padding: 0;
}

h2 {
	text-align: center;
	color: #00796B;
}

.table-container {
	display: flex;
	justify-content: center;
	align-items: flex-start;
	height: 100vh;
	margin: 0;
	padding: 40px;
	padding-top: 60px;
	background-color: #ffffff;
	border-radius: 16px;
	box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
	border: 2px solid #B2DFDB;
}

h\\:dataTable {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
	border-spacing: 0;
}

h\\:column, th, td {
	padding: 0; /* Reduced padding to minimize space */
	border: 1px solid #B2DFDB;
	text-align: center;
	font-size: 15px;
}

th {
	background-color: #00796B;
	color: white;
	font-weight: bold;
	text-transform: uppercase;
	letter-spacing: 0.5px;
}

td {
	background-color: #F1F8F6;
	color: #333;
	text-align: center;
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
.sort-icons-container {
    display: flex;
    flex-direction: column; /* Stack the sort icons vertically */
    justify-content: center; /* Center vertically */
    margin-left: 5px; /* Add some space between column name and sort icons */
}
.h-panelgroup {
    display: flex; /* Align the column name and sort icons horizontally */
    align-items: center; /* Center vertically within the header */
    justify-content: center; /* Align the items to the left */
    gap: 6px;
}
.footer {
	font-size: 14px;
	color: #666;
	margin-top: 300px;
	text-align: center;
}
</style>
</head>
<body>
	<f:view>
		<h:form>

			<div class="table-container">
				<div class="container">
					<h2>Search Results</h2>

					<!-- Table for Doctor Results -->
					<h:dataTable value="#{doctorController.searchResults}" var="doctor"
						rendered="#{not empty doctorController.searchResults}">
						<h:column rendered="#{doctorController.searchType eq 'doctor'}">


							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="Doctor ID" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<!-- Ascending Sort Icon -->
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc1('doctorId')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'doctorId' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>

										<!-- Descending Sort Icon -->
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc1('doctorId')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'doctorId' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{doctor.doctorId}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="Provider ID" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc1('providerId')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'providerId' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc1('providerId')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'providerId' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{doctor.providers.providerId}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="Doctor Name" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc1('doctorName')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'doctorName' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc1('doctorName')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'doctorName' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{doctor.doctorName}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="Specialization" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc1('specialization')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'specialization' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc1('specialization')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'specialization' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{doctor.specialization}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="Qualification" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc1('qualification')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'qualification' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc1('qualification')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'qualification' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{doctor.qualification}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="License Number" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc1('licenseNo')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'licenseNo' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc1('licenseNo')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'licenseNo' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{doctor.licenseNo}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="Email" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc1('email')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'email' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc1('email')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'email' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{doctor.email}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="Phone Number" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc1('phoneNumber')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'phoneNumber' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc1('phoneNumber')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'phoneNumber' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{doctor.phoneNumber}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="Address" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc1('address')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'address' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc1('address')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'address' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{doctor.address}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="Doctor Type" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc1('type')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'type' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc1('type')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'type' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{doctor.type}" />
						</h:column>

					</h:dataTable>


					<!-- Table for Provider Results -->


					<!-- Table for Provider Results -->
					<h:dataTable value="#{doctorController.searchResultsP}"
						var="provider"
						rendered="#{not empty doctorController.searchResultsP}">

						<h:column rendered="#{doctorController.searchType eq 'provider'}">
							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="Provider ID" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc2('providerId')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'providerId' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc2('providerId')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'providerId' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{provider.providerId}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="Provider Name" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc2('providerName')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'providerName' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc2('providerName')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'providerName' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{provider.providerName}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="Hospital Name" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc2('hospitalName')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'hospitalName' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc2('hospitalName')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'hospitalName' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{provider.hospitalName}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="City" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc2('city')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'city' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc2('city')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'city' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{provider.city}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="Address" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc2('address')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'address' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc2('address')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'address' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{provider.address}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="Email" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc2('email')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'email' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc2('email')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'email' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{provider.email}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="Status" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc2('status')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'status' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc2('status')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'status' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{provider.status}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:panelGroup layout="block" styleClass="h-panelgroup">
									<h:outputText value="Telephone" />
									<h:panelGroup layout="block" styleClass="sort-icons-container">
										<h:commandLink value="▲"
											action="#{doctorController.sortByAsc2('telephone')}"
											rendered="#{doctorController.currentSort ne 'asc' or doctorController.sortField ne 'telephone' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
										<h:commandLink value="▼"
											action="#{doctorController.sortByDesc2('telephone')}"
											rendered="#{doctorController.currentSort ne 'desc' or doctorController.sortField ne 'telephone' }"
											style="color: white; font-size: 10px; line-height: 10px; text-decoration: none; border: none; background: none;">
										</h:commandLink>
									</h:panelGroup>
								</h:panelGroup>
							</f:facet>
							<h:outputText value="#{provider.telephone}" />
						</h:column>

					</h:dataTable>

					<!-- If no results, show message -->

					<h:panelGrid
						rendered="#{empty doctorController.searchResults and empty doctorController.searchResultsP}">

						<h:outputText value="No results found." />
					</h:panelGrid>
					<h:commandButton value="Back"
						action="#{doctorController.goToSearchPage}"
						styleClass="action-button" style="margin-top: 20px;" />



					<!-- Back to Homepage Button -->
					<h:commandButton value="Back to Admin Dashboard"
						action="#{doctorController.backtoadmindashboard}"
						styleClass="action-button" style="margin-top: 20px;" />
						<div class="footer">
						<p>&copy; 2025 Infinite Computer Solution. All rights
							reserved.</p>
					</div>
				</div>
			</div>

		</h:form>
	</f:view>
</body>
</html>
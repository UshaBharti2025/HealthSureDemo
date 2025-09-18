<!--
  Copyright © 2025 Infinite Computer Solution. All rights reserved.
 
  Author: Infinite Computer Solution
 
  Description: This is insurance admin dashboard 
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<f:view>
	<!DOCTYPE html>
	<html>
<head>
<meta charset="UTF-8">
<title>Insurance Plan List</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f2f2f2;
	margin: 0;
	padding: 0;
}

.header-bar {
	display: flex;
	justify-content: space-between;
	align-items: center;
	background-color: #3498db;
	padding: 10px 30px;
	border-radius: 5px;
	color: white;
	margin: 20px auto;
	width: 90%;
	box-sizing: border-box;
}

.header-bar h1 {
	margin: 0;
	font-size: 24px;
}

.header-bar form {
	margin: 0;
}

.header-bar .add-btn {
	background-color: #2ecc71;
	color: white;
	border: none;
	padding: 6px 14px;
	border-radius: 4px;
	cursor: pointer;
	font-size: 14px;
}

.header-bar .add-btn:hover {
	background-color: #27ae60;
}

table {
	width: 90%;
	margin: 20px auto;
	border-collapse: collapse;
}

th {
	background-color: #2980b9;
	color: white;
	padding: 8px;
}

td {
	background-color: #ecf0f1;
	padding: 8px;
	text-align: center;
}

tr:nth-child(even) td {
	background-color: #d0e4f7;
}

tr:hover td {
	background-color: #b2bec3;
}

table, th, td {
	border: 1px solid #34495e;
}

.action-btn {
	background-color: #3498db;
	color: white;
	border: none;
	padding: 8px 12px;
	border-radius: 4px;
	cursor: pointer;
	font-size: 14px;
	width: 90px; /* Fixed width for uniform size */
	text-align: center;
}

.action-btn:hover {
	background-color: #2980b9;
}

.btn-link {
	color: white;
	text-decoration: none;
	background: none;
	border: none;
	cursor: pointer;
}

.pagination {
	background-color: gray;
	color: white;
	border: none;
	padding: 8px 12px;
	border-radius: 4px;
	cursor: pointer;
	font-size: 14px;
	font-weight: 12px;
	width: 90px; /* Fixed width for uniform size */
	text-align: center;
	height: 35px;
}

.h-panelgroup {
	display: flex;
	align-items: center;
	justify-content: center;
	gap: 6px;
}

.sort-icons-container {
	display: flex;
	flex-direction: column; /* horizontal arrows */
	margin-left: 6px;
}

.sort-icons {
	cursor: pointer;
	height: 13px;
	width: 14px;
}
</style>
</head>
<body>
	<!-- 🌐 Navigation -->
	<jsp:include page="./../navbar/NavAdmin.jsp" />
	<!-- Spacing below navbar -->
	<div style="margin-top: 130px;"></div>

	<!-- ✅ Header with Add Button -->
	<h:form>
		<div class="header-bar">
			<h1>Insurance Plan List</h1>
			<h:commandButton value="Add"
				action="#{createInsuranceController.navigateToAddInsurance}"
				styleClass="action-btn" style="background-color:gray" />
		</div>
		<h:dataTable value="#{createInsuranceController.paginatedPlans}"
			var="plan" border="1" styleClass="insuranceTable">

			<!-- Plan Id -->
			<h:column>
				<f:facet name="header">
					<h:panelGroup layout="block" styleClass="h-panelgroup">
						<h:outputText value="Plan Id" />
						<h:panelGroup layout="block" styleClass="sort-icons-container">
							<h:commandLink
								action="#{createInsuranceController.sortByAsc('planId')}"
								rendered="#{!(createInsuranceController.sortAscending and createInsuranceController.sortField eq 'planId')}"
								styleClass="sort-icons">
								<h:graphicImage value="/resources/media/images/up-arrow.png"
									width="10" height="10" />
							</h:commandLink>
							<h:commandLink
								action="#{createInsuranceController.sortByDesc('planId')}"
								rendered="#{!(!createInsuranceController.sortAscending and createInsuranceController.sortField eq 'planId')}"
								styleClass="sort-icons">
								<h:graphicImage value="/resources/media/images/down-arrow.png"
									width="10" height="10" />
							</h:commandLink>
						</h:panelGroup>
					</h:panelGroup>
				</f:facet>
				<h:outputText value="#{plan.planId}" />
			</h:column>

			<!-- Plan Name -->
			<h:column>
				<f:facet name="header">
					<h:panelGroup layout="block" styleClass="h-panelgroup">
						<h:outputText value="Plan Name" />
						<h:panelGroup layout="block" styleClass="sort-icons-container">
							<h:commandLink
								action="#{createInsuranceController.sortByAsc('planName')}"
								rendered="#{!(createInsuranceController.sortAscending and createInsuranceController.sortField eq 'planName')}"
								styleClass="sort-icons">
								<h:graphicImage value="/resources/media/images/up-arrow.png"
									width="10" height="10" />
							</h:commandLink>
							<h:commandLink
								action="#{createInsuranceController.sortByDesc('planName')}"
								rendered="#{!(!createInsuranceController.sortAscending and createInsuranceController.sortField eq 'planName')}"
								styleClass="sort-icons">
								<h:graphicImage value="/resources/media/images/down-arrow.png"
									width="10" height="10" />
							</h:commandLink>
						</h:panelGroup>
					</h:panelGroup>
				</f:facet>
				<h:outputText value="#{plan.planName}" />
			</h:column>

			<!-- Type -->
			<h:column>
				<f:facet name="header">
					<h:panelGroup layout="block" styleClass="h-panelgroup">
						<h:outputText value="Type" />
						<h:panelGroup layout="block" styleClass="sort-icons-container">
							<h:commandLink
								action="#{createInsuranceController.sortByAsc('planType')}"
								rendered="#{!(createInsuranceController.sortAscending and createInsuranceController.sortField eq 'planType')}"
								styleClass="sort-icons">
								<h:graphicImage value="/resources/media/images/up-arrow.png"
									width="10" height="10" />
							</h:commandLink>
							<h:commandLink
								action="#{createInsuranceController.sortByDesc('planType')}"
								rendered="#{!(!createInsuranceController.sortAscending and createInsuranceController.sortField eq 'planType')}"
								styleClass="sort-icons">
								<h:graphicImage value="/resources/media/images/down-arrow.png"
									width="10" height="10" />
							</h:commandLink>
						</h:panelGroup>
					</h:panelGroup>
				</f:facet>
				<h:outputText value="#{plan.planType}" />
			</h:column>

			<!-- Covers -->
			<h:column>
				<f:facet name="header">
					<h:panelGroup layout="block" styleClass="h-panelgroup">
						<h:outputText value="Covers" />
						<h:panelGroup layout="block" styleClass="sort-icons-container">
							<h:commandLink
								action="#{createInsuranceController.sortByAsc('availableCoverAmounts')}"
								rendered="#{!(createInsuranceController.sortAscending and createInsuranceController.sortField eq 'availableCoverAmounts')}"
								styleClass="sort-icons">
								<h:graphicImage value="/resources/media/images/up-arrow.png"
									width="10" height="10" />
							</h:commandLink>
							<h:commandLink
								action="#{createInsuranceController.sortByDesc('availableCoverAmounts')}"
								rendered="#{!(!createInsuranceController.sortAscending and createInsuranceController.sortField eq 'availableCoverAmounts')}"
								styleClass="sort-icons">
								<h:graphicImage value="/resources/media/images/down-arrow.png"
									width="10" height="10" />
							</h:commandLink>
						</h:panelGroup>
					</h:panelGroup>
				</f:facet>
				<h:outputText value="#{plan.availableCoverAmounts}">
					<f:convertNumber pattern="0.00" />
				</h:outputText>
			</h:column>

			<!-- Waiting Period -->
			<h:column>
				<f:facet name="header">
					<h:panelGroup layout="block" styleClass="h-panelgroup">
						<h:outputText value="Waiting Period" />
						<h:panelGroup layout="block" styleClass="sort-icons-container">
							<h:commandLink
								action="#{createInsuranceController.sortByAsc('waitingPeriod')}"
								rendered="#{!(createInsuranceController.sortAscending and createInsuranceController.sortField eq 'waitingPeriod')}"
								styleClass="sort-icons">
								<h:graphicImage value="/resources/media/images/up-arrow.png"
									width="10" height="10" />
							</h:commandLink>
							<h:commandLink
								action="#{createInsuranceController.sortByDesc('waitingPeriod')}"
								rendered="#{!(!createInsuranceController.sortAscending and createInsuranceController.sortField eq 'waitingPeriod')}"
								styleClass="sort-icons">
								<h:graphicImage value="/resources/media/images/down-arrow.png"
									width="10" height="10" />
							</h:commandLink>
						</h:panelGroup>
					</h:panelGroup>
				</f:facet>
				<h:outputText value="#{plan.waitingPeriod}" />
			</h:column>

			<!-- Expire Date -->
			<h:column>
				<f:facet name="header">
					<h:panelGroup layout="block" styleClass="h-panelgroup">
						<h:outputText value="Expire Date" />
						<h:panelGroup layout="block" styleClass="sort-icons-container">
							<h:commandLink
								action="#{createInsuranceController.sortByAsc('expireDate')}"
								rendered="#{!(createInsuranceController.sortAscending and createInsuranceController.sortField eq 'expireDate')}"
								styleClass="sort-icons">
								<h:graphicImage value="/resources/media/images/up-arrow.png"
									width="10" height="10" />
							</h:commandLink>
							<h:commandLink
								action="#{createInsuranceController.sortByDesc('expireDate')}"
								rendered="#{!(!createInsuranceController.sortAscending and createInsuranceController.sortField eq 'expireDate')}"
								styleClass="sort-icons">
								<h:graphicImage value="/resources/media/images/down-arrow.png"
									width="10" height="10" />
							</h:commandLink>
						</h:panelGroup>
					</h:panelGroup>
				</f:facet>
				<h:outputText value="#{plan.expireDate}" />
			</h:column>

			<!-- Active On -->
			<h:column>
				<f:facet name="header">
					<h:panelGroup layout="block" styleClass="h-panelgroup">
						<h:outputText value="Active On" />
						<h:panelGroup layout="block" styleClass="sort-icons-container">
							<h:commandLink
								action="#{createInsuranceController.sortByAsc('activeOn')}"
								rendered="#{!(createInsuranceController.sortAscending and createInsuranceController.sortField eq 'activeOn')}"
								styleClass="sort-icons">
								<h:graphicImage value="/resources/media/images/up-arrow.png"
									width="10" height="10" />
							</h:commandLink>
							<h:commandLink
								action="#{createInsuranceController.sortByDesc('activeOn')}"
								rendered="#{!(!createInsuranceController.sortAscending and createInsuranceController.sortField eq 'activeOn')}"
								styleClass="sort-icons">
								<h:graphicImage value="/resources/media/images/down-arrow.png"
									width="10" height="10" />
							</h:commandLink>
						</h:panelGroup>
					</h:panelGroup>
				</f:facet>
				<h:outputText value="#{plan.activeOn}" />
			</h:column>

			<h:column>
				<f:facet name="header">
					<h:outputLabel value="Update" />
				</f:facet>

				<h:commandButton value="Update" styleClass="action-btn"
					action="#{createInsuranceController.updateInsurancePlan(plan.planId)}" />

			</h:column>


			<h:column>
				<f:facet name="header">
					<h:outputLabel value="Details" />
				</f:facet>

				<h:commandButton value="Details" styleClass="action-btn"
					action="#{createInsuranceController.findAllPlanDetailsByPlanId(plan.planId)}" />

			</h:column>
		</h:dataTable>

		<!-- ✅ Pagination Controls -->
		<div style="text-align: center; margin: 20px;">
			<h:commandButton value="Previous" styleClass="pagination"
				action="#{createInsuranceController.previousPage}"
				disabled="#{createInsuranceController.currentPage lt 1}" />

			<h:outputText
				value=" Page #{createInsuranceController.currentPage + 1} of #{createInsuranceController.totalPages} " />

			<h:commandButton value="Next" styleClass="pagination"
				action="#{createInsuranceController.nextPage}"
				disabled="#{!createInsuranceController.next}" />
		</div>
	</h:form>

	<h:messages globalOnly="true" style="color:red" />

</body>


	</html>
</f:view>
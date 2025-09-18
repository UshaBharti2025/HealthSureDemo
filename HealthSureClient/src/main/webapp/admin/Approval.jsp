<!--
  Copyright © 2025 Infinite Computer Solution. All rights reserved.
 
  Author: Infinite Computer Solution
 
  Description:  Pharmacy Approval By Admin
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<f:view>
	<!DOCTYPE html>
	<html>
<head>
<meta charset="UTF-8" />
<title>Pharmacy Review Table</title>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/approvePharmacy.css" />


<Script>
	window.onload = function() {
		setTimeout(function() {
			var msg = document.getElementById('globalMessages');
			if (msg) {
				msg.style.display = 'none';
			}
		}, 5000); // 5000 milliseconds = 5 seconds
	};
	function showLoader() {
		document.getElementById('loader').style.display = 'block';
	}

	window.onload = function() {
		setTimeout(function() {
			var msgBox = document.getElementById('globalMessages');
			if (msgBox) {
				msgBox.style.display = 'none';
			}
			document.getElementById('loader').style.display = 'none';
		}, 10000); // Hide loader and message after 10 seconds
	};
</Script>
</head>
<body>

	<!-- Include Fixed Navbar -->
	<!-- 🌐 Navigation -->
	<jsp:include page="./../navbar/NavAdmin.jsp" />

	
	<!-- Spacing below navbar -->
	<div style="margin-top: 100px;"></div>

	<!-- Loader Message -->
	<div id="loader">Validating, please wait...</div>


	<h:messages id="globalMessages" globalOnly="true"
		style="position: fixed; top: 70px; left: 50%; transform: translateX(-50%);
           background-color: #e3f2fd; color: #0d47a1; padding: 12px 24px;
           border: 1px solid #90caf9; border-radius: 8px; font-size: 15px;
           z-index: 9999; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2); text-align: center;
           width: auto; max-width: 90%;" />

	<!-- ✅ JavaScript for loader and message -->



	<!-- Pharmacy Table -->
	<div class="table-container">
		<h:form>
			<h2 style="text-align: center;">PHARMACY REVIEW AND APPROVAL</h2>


			<div class="table-container1">

				<h:dataTable value="#{reviewPharmacyController.paginatedPharmacies}"
					var="pharmacy">



					<!-- Pharmacy ID Column -->
					<h:column>
						<f:facet name="header">
							<h:panelGroup styleClass="h-panelgroup" layout="block">
								<h:outputText value="Pharmacy ID" />
								<h:panelGroup layout="block" styleClass="sort-icons-container">
									<h:commandLink
										action="#{reviewPharmacyController.sortByAsc('pharmacyId')}"
										rendered="#{!(reviewPharmacyController.ascending and reviewPharmacyController.sortField eq 'pharmacyId')}"
										styleClass="sort-icons">
										<h:graphicImage value="/resources/media/images/up-arrow.png"
											width="10" height="10" title="Sort Ascending"
											styleClass="backgroundcolor:transparent" />
									</h:commandLink>
									<h:commandLink
										action="#{reviewPharmacyController.sortByDesc('pharmacyId')}"
										rendered="#{!(!reviewPharmacyController.ascending and reviewPharmacyController.sortField eq 'pharmacyId')}"
										styleClass="sort-icons">
										<h:graphicImage value="/resources/media/images/down-arrow.png"
											width="10" height="10" title="Sort Descending" />
									</h:commandLink>
								</h:panelGroup>
							</h:panelGroup>
						</f:facet>
						<h:outputText value="#{pharmacy.pharmacyId}" />
					</h:column>

					<!-- Pharmacy Name Column -->
					<h:column>
						<f:facet name="header">
							<h:panelGroup styleClass="h-panelgroup" layout="block">
								<h:outputText value="Name" />
								<h:panelGroup layout="block" styleClass="sort-icons-container">
									<h:commandLink
										action="#{reviewPharmacyController.sortByAsc('pharmacyName')}"
										rendered="#{!(reviewPharmacyController.ascending and reviewPharmacyController.sortField eq 'pharmacyName')}"
										styleClass="sort-icons">
										<h:graphicImage value="/resources/media/images/up-arrow.png"
											width="10" height="10" title="Sort Ascending" />
									</h:commandLink>
									<h:commandLink
										action="#{reviewPharmacyController.sortByDesc('pharmacyName')}"
										rendered="#{!(!reviewPharmacyController.ascending and reviewPharmacyController.sortField eq 'pharmacyName')}"
										styleClass="sort-icons">
										<h:graphicImage value="/resources/media/images/down-arrow.png"
											width="10" height="10" title="Sort Descending" />
									</h:commandLink>
								</h:panelGroup>
							</h:panelGroup>
						</f:facet>
						<h:outputText value="#{pharmacy.pharmacyName}" />
					</h:column>

					<!-- Email Column -->
					<h:column>
						<f:facet name="header">
							<h:panelGroup styleClass="h-panelgroup" layout="block">
								<h:outputText value="Email" />
								<h:panelGroup layout="block" styleClass="sort-icons-container">
									<h:commandLink
										action="#{reviewPharmacyController.sortByAsc('email')}"
										rendered="#{!(reviewPharmacyController.ascending and reviewPharmacyController.sortField eq 'email')}"
										styleClass="sort-icons">
										<h:graphicImage value="/resources/media/images/up-arrow.png"
											width="10" height="10" title="Sort Ascending" />
									</h:commandLink>
									<h:commandLink
										action="#{reviewPharmacyController.sortByDesc('email')}"
										rendered="#{!(!reviewPharmacyController.ascending and reviewPharmacyController.sortField eq 'email')}"
										styleClass="sort-icons">
										<h:graphicImage value="/resources/media/images/down-arrow.png"
											width="10" height="10" title="Sort Descending" />
									</h:commandLink>
								</h:panelGroup>
							</h:panelGroup>
						</f:facet>
						<h:outputText value="#{pharmacy.email}" />
					</h:column>

					<!-- Aadhar Column -->
					<h:column>
						<f:facet name="header">
							<h:panelGroup styleClass="h-panelgroup" layout="block">
								<h:outputText value="Aadhar" />
								<h:panelGroup layout="block" styleClass="sort-icons-container">
									<h:commandLink
										action="#{reviewPharmacyController.sortByAsc('aadhar')}"
										rendered="#{!(reviewPharmacyController.ascending and reviewPharmacyController.sortField eq 'aadhar')}"
										styleClass="sort-icons">
										<h:graphicImage value="/resources/media/images/up-arrow.png"
											width="10" height="10" title="Sort Ascending" />
									</h:commandLink>
									<h:commandLink
										action="#{reviewPharmacyController.sortByDesc('aadhar')}"
										rendered="#{!(!reviewPharmacyController.ascending and reviewPharmacyController.sortField eq 'aadhar')}"
										styleClass="sort-icons">
										<h:graphicImage value="/resources/media/images/down-arrow.png"
											width="10" height="10" title="Sort Descending" />
									</h:commandLink>
								</h:panelGroup>
							</h:panelGroup>
						</f:facet>
						<h:outputText value="#{pharmacy.aadhar}" />
					</h:column>

					<!-- License No Column -->
					<h:column>
						<f:facet name="header">
							<h:panelGroup styleClass="h-panelgroup" layout="block">
								<h:outputText value="License No" />
								<h:panelGroup layout="block" styleClass="sort-icons-container">
									<h:commandLink
										action="#{reviewPharmacyController.sortByAsc('licenseNo')}"
										rendered="#{!(reviewPharmacyController.ascending and reviewPharmacyController.sortField eq 'licenseNo')}"
										styleClass="sort-icons">
										<h:graphicImage value="/resources/media/images/up-arrow.png"
											width="10" height="10" title="Sort Ascending" />
									</h:commandLink>
									<h:commandLink
										action="#{reviewPharmacyController.sortByDesc('licenseNo')}"
										rendered="#{!(!reviewPharmacyController.ascending and reviewPharmacyController.sortField eq 'licenseNo')}"
										styleClass="sort-icons">
										<h:graphicImage value="/resources/media/images/down-arrow.png"
											width="10" height="10" title="Sort Descending" />
									</h:commandLink>
								</h:panelGroup>
							</h:panelGroup>
						</f:facet>
						<h:outputText value="#{pharmacy.licenseNo}" />
					</h:column>

					<!-- GST Number Column -->
					<h:column>
						<f:facet name="header">
							<h:panelGroup styleClass="h-panelgroup" layout="block">
								<h:outputText value="GST Number" />
								<h:panelGroup layout="block" styleClass="sort-icons-container">
									<h:commandLink
										action="#{reviewPharmacyController.sortByAsc('gstNo')}"
										rendered="#{!(reviewPharmacyController.ascending and reviewPharmacyController.sortField eq 'gstNo')}"
										styleClass="sort-icons">
										<h:graphicImage value="/resources/media/images/up-arrow.png"
											width="10" height="10" title="Sort Ascending" />
									</h:commandLink>
									<h:commandLink
										action="#{reviewPharmacyController.sortByDesc('gstNo')}"
										rendered="#{!(!reviewPharmacyController.ascending and reviewPharmacyController.sortField eq 'gstNo')}"
										styleClass="sort-icons">
										<h:graphicImage value="/resources/media/images/down-arrow.png"
											width="10" height="10" title="Sort Descending" />
									</h:commandLink>
								</h:panelGroup>
							</h:panelGroup>
						</f:facet>
						<h:outputText value="#{pharmacy.gstNo}" />
					</h:column>

					<!-- Validation Column -->
					<h:column>
						<f:facet name="header">
							<h:outputLabel value="Validation" />
						</f:facet>
						<h:commandButton value="Validate"
							action="#{reviewPharmacyController.validatePharmacy(pharmacy)}"
							disabled="#{pharmacy.status eq 'ACCEPTED' or pharmacy.status eq 'REJECTED'}"
							onclick="showLoader()" styleClass="action-button" />

					</h:column>


					<!-- Status Column -->
					<h:column>
						<f:facet name="header">
							<h:panelGroup styleClass="h-panelgroup" layout="block">
								<h:outputText value="Status" />
								<h:panelGroup layout="block" styleClass="sort-icons-container">
									<h:commandLink
										action="#{reviewPharmacyController.sortByAsc('status')}"
										rendered="#{!(reviewPharmacyController.ascending and reviewPharmacyController.sortField eq 'status')}"
										styleClass="sort-icons">
										<h:graphicImage value="/resources/media/images/up-arrow.png"
											width="10" height="10" title="Sort Ascending" />
									</h:commandLink>
									<h:commandLink
										action="#{reviewPharmacyController.sortByDesc('status')}"
										rendered="#{!(!reviewPharmacyController.ascending and reviewPharmacyController.sortField eq 'status')}"
										styleClass="sort-icons">
										<h:graphicImage value="/resources/media/images/down-arrow.png"
											width="10" height="10" title="Sort Descending" />
									</h:commandLink>
								</h:panelGroup>
							</h:panelGroup>
						</f:facet>
						<h:outputText value="#{pharmacy.status}"
							styleClass="status-#{pharmacy.status}" />

					</h:column>

				</h:dataTable>
			</div>

			<!-- Pagination Controls -->
			<div style="text-align: center; margin-top: 30px;">
				<h:outputText
					value="Page #{reviewPharmacyController.page + 1} of #{reviewPharmacyController.totalPages}"
					style="margin-right: 20px; font-weight: 600; color: #2c3e50;" />

				<h:commandButton value="Previous"
					action="#{reviewPharmacyController.previousPage}"
					disabled="#{reviewPharmacyController.page eq 0}"
					style="margin-right: 10px;" styleClass="action-button" />

				<h:commandButton value="Next"
					action="#{reviewPharmacyController.nextPage}"
					disabled="#{reviewPharmacyController.page + 1 ge reviewPharmacyController.totalPages}"
					style="margin-left: 10px;" styleClass="action-button" />
			</div>

			<div
				style="display: flex; justify-content: space-between; align-items: center; margin-top: 30px;">

				<!-- Left: Page Number Block with Row Prev/Next -->
				<div style="display: flex; align-items: center;">

					<!-- Row Previous Button -->
					<h:commandButton value="Prev Pages" title="Prev row"
						action="#{reviewPharmacyController.previousBlock}"
						disabled="#{reviewPharmacyController.currentBlock eq 0}"
						style="margin-right: 8px;" styleClass="action-button" />

					<h:dataTable value="#{reviewPharmacyController.pageNumbers}"
						var="pageNum" styleClass="page-nav-row">
						<h:column>
							<h:commandButton value="#{pageNum}"
								action="#{reviewPharmacyController.goToPage(pageNum)}"
								disabled="#{pageNum == reviewPharmacyController.page + 1}"
								style="margin-right: 5px;" styleClass="action-button" />
						</h:column>
					</h:dataTable>

					<!-- Row Next Button -->
					<h:commandButton value="Next Pages" title="Next row"
						action="#{reviewPharmacyController.nextBlock}"
						disabled="#{reviewPharmacyController.currentBlock + 1 ge (reviewPharmacyController.totalPages / reviewPharmacyController.pageBlockSize)}"
						style="margin-left: 8px;" styleClass="action-button" />
				</div>

				<!-- Right: Regular Prev/Next
				<div>
					<h:commandButton value="Previous"
						action="#{reviewPharmacyController.previousBlock}"
								disabled="#{pageNum == reviewPharmacyController.page + 1}"
						style="margin-right: 10px;" styleClass="action-button" />

					<h:commandButton value="Next"
						action="#{reviewPharmacyController.nextBlock}"
						disabled="#{reviewPharmacyController.currentBlock + 1 ge (reviewPharmacyController.totalPages / reviewPharmacyController.pageBlockSize)}"
						styleClass="action-button" />
				</div>
				 -->
				<div>
					<h:outputText styleClass="summary-details"
						value="Showing #{(reviewPharmacyController.page+1)*reviewPharmacyController.pageSize} out of #{reviewPharmacyController.totalPages*reviewPharmacyController.pageSize} Result" />
				</div>
			</div>

		</h:form>
	</div>

</body>
	</html>
</f:view>
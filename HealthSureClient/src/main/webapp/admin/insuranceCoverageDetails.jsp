<!--
  Copyright © 2025 Infinite Computer Solution. All rights reserved.
 
  
 
  Description: This is insurance details page
-->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<f:view>
	<html>
<head>
<meta charset="UTF-8">
<title>Show Plan Details</title>
<style>
/* Base Styling */
body {
	font-family: 'Segoe UI', 'Roboto', 'Helvetica Neue', sans-serif;
	background-color: #f4f6f8;
	margin: 0;
	padding: 30px;
	color: #333;
}

/* Main Container */
.form-box {
	background-color: #ffffff;
	padding: 40px;
	border-radius: 12px;
	box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
	max-width: 1100px;
	margin: auto;
}

/* Section Headings */
h3 {
	text-align: center;
	color: #2c3e50;
	margin-bottom: 30px;
	font-size: 24px;
}

/* Row Layout */
.form-row {
	display: flex;
	justify-content: space-between;
	margin-bottom: 25px;
	gap: 20px;
}

/* Field Group */
.form-group {
	flex: 1;
	min-width: 280px;
	margin-right: 20px;
	margin-bottom: 15px;
}

/* Labels */
label {
	font-weight: 600;
	display: block;
	margin-bottom: 6px;
	color: #444;
}

/* Output Text Styling */
h\:outputText {
	display: block;
	padding: 10px 14px;
	background-color: #f8f9fa;
	border: 1px solid #ddd;
	border-radius: 6px;
	color: #555;
	font-size: 15px;
}

/* Member Selection Section */
#memberValidation {
	font-size: 18px;
	font-weight: 600;
	color: #2c3e50;
	margin-bottom: 10px;
	display: block;
	text-align: center;
}

.member-checkboxes {
	display: flex;
	flex-wrap: wrap;
	justify-content: space-evenly;
	padding: 20px 0;
}

.member-checkboxes div {
	flex: 1;
	min-width: 140px;
	margin: 10px;
	font-weight: 500;
}

/* Coverage Box Styling */
.coverage-box {
	border: 2px solid #28a745;
	border-radius: 10px;
	padding: 20px;
	margin: 20px 0;
	background-color: #fefefe;
	width: 100%;
}

.coverage-box h3 {
	color: #28a745;
	text-align: center;
	margin-bottom: 20px;
}

.coverage-box label {
	font-weight: 600;
	margin-top: 10px;
	display: block;
}

.coverage-box h\:inputText, .coverage-box h\:outputText, .coverage-box h\:selectOneMenu
	{
	display: block;
	width: 100%;
	padding: 10px 14px;
	background-color: #f8f9fa;
	border: 1px solid #ccc;
	border-radius: 6px;
	margin-bottom: 10px;
	font-size: 15px;
	color: #555;
}

/* Error Message Styling */
.error {
	color: red;
	font-size: 13px;
	margin-top: 4px;
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

.button-row {
	display: flex;
	justify-content: space-between;
	margin-top: 10px;
}

.action-btn {
	padding: 10px 20px;
	font-size: 14px;
	cursor: pointer;
}

.action-btn:hover {
	background-color: #2980b9;
}

.planDetails {
	margin-left: 20px;
}
</style>
</head>
<body>
	<h:form id="companyForm">
		<div class="form-box">
			<h3>Insurance Plan Details</h3>
			<div class="planDetails">
				<!-- Row 1 -->
				<div class="form-row">
					<div class="form-group">
						<label>Company Name:</label>
						<h:outputText
							value="#{createInsuranceController.insurancePlan.insuranceCompany.name}" />
					</div>
					<div class="form-group">
						<label>Plan Name:</label>
						<h:outputText
							value="#{createInsuranceController.insurancePlan.planName}" />
					</div>
					<div class="form-group">
						<label>Plan Type:</label>
						<h:outputText
							value="#{createInsuranceController.insurancePlan.planType}" />
					</div>
				</div>

				<!-- Row 2 -->
				<div class="form-row">
					<div class="form-group">
						<label>Max Age:</label>
						<h:outputText
							value="#{createInsuranceController.insurancePlan.maxEntryAge}" />
					</div>
					<div class="form-group">
						<label>Min Age:</label>
						<h:outputText
							value="#{createInsuranceController.insurancePlan.minEntryAge}" />
					</div>

					<div class="form-group">
						<label>Description:</label>
						<h:outputText
							value="#{createInsuranceController.insurancePlan.description}" />
					</div>
				</div>

				<!-- Row 3 -->
				<div class="form-row">
					<div class="form-group">
						<label>Available Amount:</label>
						<h:outputText
							value="#{createInsuranceController.insurancePlan.availableCoverAmounts}" />
					</div>
					<div class="form-group">
						<label>Waiting Period:</label>
						<h:outputText
							value="#{createInsuranceController.insurancePlan.waitingPeriod}" />
					</div>
					<div class="form-group">
						<label>Active On:</label>
						<h:outputText
							value="#{createInsuranceController.insurancePlan.activeOn}">
							<f:convertDateTime pattern="yyyy-MM-dd" />
						</h:outputText>
					</div>
				</div>

				<!-- Row 4 -->
				<div class="form-row">
					<div class="form-group">
						<label>Maximum Member Allowed:</label>
						<h:outputText
							value="#{createInsuranceController.insurancePlan.maximumMemberAllowed}" />
					</div>
					<div class="form-group">
						<label>Minimum Member Allowed:</label>
						<h:outputText
							value="#{createInsuranceController.insurancePlan.minimumMeberAllowed}" />
					</div>
					<div class="form-group">
						<label>Created On:</label>
						<h:outputText
							value="#{createInsuranceController.insurancePlan.createdOn}">

							<f:convertDateTime pattern="yyyy-MM-DD" />
						</h:outputText>

					</div>
				</div>

				<!-- Row 5 -->
				<div class="form-row">
					<div class="form-group">
						<label>Periodic Diseases:</label>
						<h:outputText
							value="#{createInsuranceController.insurancePlan.periodicDiseases}" />
					</div>
					<div class="form-group">
						<label>Expire Date:</label>
						<h:outputText
							value="#{createInsuranceController.insurancePlan.expireDate}" />
					</div>
				</div>
			</div>
			<!-- Multiple selection for Members -->
			<div>
				<h3>
					<h:outputLabel id="memberValidation"> Members allowed in the plan </h:outputLabel>
				</h3>


				<br />

				<div
					style="padding-left: 5px; display: flex; flex-wrap: wrap; justify-content: space-evenly; width: 100%;">
					<div style="flex: 1; min-width: 120px;">
						<label><h:selectBooleanCheckbox
								value="#{createInsuranceController.relationMap['DAUGHTER1']}" />
							Daughter1</label>
					</div>
					<div style="flex: 1; min-width: 120px;">
						<label><h:selectBooleanCheckbox
								value="#{createInsuranceController.relationMap['DAUGHTER2']}" />
							Daughter2</label>
					</div>
					<div style="flex: 1; min-width: 120px;">
						<label><h:selectBooleanCheckbox
								value="#{createInsuranceController.relationMap['SON1']}" />
							Son1</label>
					</div>
					<div style="flex: 1; min-width: 120px;">
						<label><h:selectBooleanCheckbox
								value="#{createInsuranceController.relationMap['SON2']}" />
							Son2</label>
					</div>
					<div style="flex: 1; min-width: 120px;">
						<label><h:selectBooleanCheckbox
								value="#{createInsuranceController.relationMap['FATHER']}" />
							Father</label>
					</div>
					<div style="flex: 1; min-width: 120px;">
						<label><h:selectBooleanCheckbox
								value="#{createInsuranceController.relationMap['MOTHER']}" />
							Mother</label>
					</div>
					<div style="flex: 1; min-width: 120px;">
						<label><h:selectBooleanCheckbox
								value="#{createInsuranceController.relationMap['HUSBAND']}" />
							Husband</label>
					</div>
					<div style="flex: 1; min-width: 120px;">
						<label><h:selectBooleanCheckbox
								value="#{createInsuranceController.relationMap['WIFE']}" />
							Wife</label>
					</div>

					<div style="flex: 1; min-width: 120px;">
						<label><h:selectBooleanCheckbox
								value="#{createInsuranceController.relationMap['INDIVIDUAL']}" />
							INDIVIDUAL</label>
					</div>
				</div>
				<br />
			</div>
			<!-- Coverage Options -->
			<h3>Show Coverage Options</h3>
			<div class="form-row">

				<!-- Coverage Option 1 -->
				<div class="coverage-box">
					<h3>Silver Option</h3>

					<div class="form-group">
						<label>Premium Amount:</label>
						<h:outputText
							value="#{createInsuranceController.coverageOption1.premiumAmount}" />
					</div>

					<div class="form-group">
						<label>Coverage Amount:</label>
						<h:outputText
							value="#{createInsuranceController.coverageOption1.coverageAmount}" />
					</div>

					<div class="form-group">
						<label>Coverage Type:</label>
						<h:outputText
							value="#{createInsuranceController.coverageOption1.coverageType}" />
					</div>
				</div>

				<!-- Coverage Option 2 -->
				<div class="coverage-box">
					<h3>Gold Option</h3>

					<div class="form-group">
						<label>Premium Amount:</label>
						<h:outputText
							value="#{createInsuranceController.coverageOption2.premiumAmount}" />
					</div>

					<div class="form-group">
						<label>Coverage Amount:</label>
						<h:outputText
							value="#{createInsuranceController.coverageOption2.coverageAmount}" />
					</div>

					<div class="form-group">
						<label>Coverage Type:</label>
						<h:outputText
							value="#{createInsuranceController.coverageOption2.coverageType}" />
					</div>
				</div>

				<!-- Coverage Option 3 -->
				<div class="coverage-box">
					<h3>Platinum Option</h3>

					<div class="form-group">
						<label>Premium Amount:</label>
						<h:outputText
							value="#{createInsuranceController.coverageOption3.premiumAmount}" />
					</div>

					<div class="form-group">
						<label>Coverage Amount:</label>
						<h:outputText
							value="#{createInsuranceController.coverageOption3.coverageAmount}" />
					</div>

					<div class="form-group">
						<label>Coverage Type:</label>
						<h:outputText
							value="#{createInsuranceController.coverageOption3.coverageType}" />
					</div>
				</div>
			</div>

			<div class="button-row">

				<h:commandButton value="Back" action="insuranceAdminDashBoard.jsp"
					styleClass="action-btn right-btn" />
				<h:commandButton value="Cancel" action="insuranceAdminDashBoard.jsp"
					styleClass="action-btn" />
			</div>
		</div>
	</h:form>

</body>
	</html>
</f:view>
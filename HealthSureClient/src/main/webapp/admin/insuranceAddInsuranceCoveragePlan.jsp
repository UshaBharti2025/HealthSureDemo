<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<f:view>
	<!DOCTYPE html>
	<html>
<head>
<title>Insurance Plan and Coverage Options</title>
<%-- Link to external CSS file --%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/addInsurance.css" />

<%-- Link to external Java Script file (keep if it has non-ajax related functions) --%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/script/addInsurance.js"></script>

</head>
<body>
	<div class="container">
		<br />
		<h:messages id="globalMsg" globalOnly="true" layout="table"
			styleClass="global-message global-error" />

		<!-- Insurance Plan Form -->
		<div class="card">
			<div class="card-header">Add Insurance Plan</div>
			<div class="card-body">
				<h:form id="companyForm">
					<div class="plan-details">
						<h3 class="sub-heading">Insurance Plan Details</h3>
						<div class="form-grid">
							<!-- Company Info -->
							<div class="form-group">
								<label for="companyId">Company Name</label>
								<h:inputText id="companyId" styleClass="form-control"
									readonly="true" title="Can't change"
									value="#{createInsuranceController.insurancePlan.insuranceCompany.name}" />
								<br />
								<h:message for="companyId" styleClass="error-message" />
							</div>

							<div class="form-group">
								<label for="planName" class="required-field">Plan Name</label>
								<h:inputText id="planName" styleClass="form-control"
									value="#{createInsuranceController.insurancePlan.planName}" />
								<br />
								<h:message for="planName" styleClass="error-message" />
							</div>

							<div class="form-group">
								<label for="planType" class="required-field">Plan Type</label>
								<h:selectOneMenu id="planType" styleClass="form-control"
									onchange="selectOneMenu();"
									value="#{createInsuranceController.insurancePlan.planType}">
									<f:selectItem itemLabel="--Select--" itemValue="" />
									<f:selectItem itemLabel="INDIVIDUAL" itemValue="INDIVIDUAL" />
									<f:selectItem itemLabel="FAMILY" itemValue="FAMILY" />
								</h:selectOneMenu>
								<br />

								<h:message for="planType" styleClass="error-message" />
							</div>

							<!-- Age Limits -->
							<div class="form-group">
								<label for="maxAge" class="required-field">Max Age</label>
								<h:selectOneMenu id="maxAge" styleClass="form-control"
									value="#{createInsuranceController.insurancePlan.maxEntryAge}">
									<f:selectItem itemLabel="Select Age" itemValue="" />
									<f:selectItem itemLabel="65 Years" itemValue="65" />
									<f:selectItem itemLabel="66 Years" itemValue="66" />
									<f:selectItem itemLabel="67 Years" itemValue="67" />
									<f:selectItem itemLabel="68 Years" itemValue="68" />
									<f:selectItem itemLabel="69 Years" itemValue="69" />
									<f:selectItem itemLabel="70 Years" itemValue="70" />
								</h:selectOneMenu>
								<br />
								<h:message for="maxAge" styleClass="error-message" />
							</div>


							<div class="form-group">
								<label for="minAge" class="required-field">Min Age</label>
								<h:selectOneMenu id="minAge" styleClass="form-control"
									value="#{createInsuranceController.insurancePlan.minEntryAge}">
									<f:selectItem itemLabel="Select Age" itemValue="" />
									<f:selectItem itemLabel="1 Year" itemValue="1" />
									<f:selectItem itemLabel="2 Years" itemValue="2" />
									<f:selectItem itemLabel="3 Years" itemValue="3" />
									<f:selectItem itemLabel="18 Years" itemValue="18" />
								</h:selectOneMenu>
								<br />
								<h:message for="minAge" styleClass="error-message" />
							</div>


							<div class="form-group">
								<label for="periodicDiseases" class="required-field">Periodic
									Diseases</label>
								<h:selectOneMenu id="periodicDiseases" styleClass="form-control"
									value="#{createInsuranceController.insurancePlan.periodicDiseases}">
									<f:selectItem itemLabel="--Select--" itemValue="" />
									<f:selectItem itemLabel="YES" itemValue="YES" />
									<f:selectItem itemLabel="NO" itemValue="NO" />
								</h:selectOneMenu>
								<br />
								<h:message for="periodicDiseases" styleClass="error-message" />
							</div>

							<!-- Financial Details -->
							<div class="form-group">
								<label for="cover" class="required-field">Available
									Amount</label>
								<h:inputText id="cover" styleClass="form-control"
									value="#{createInsuranceController.insurancePlan.availableCoverAmounts}">
									<f:convertNumber type="number" maxFractionDigits="2"
										minFractionDigits="2" />
								</h:inputText>
								<br />
								<h:message for="cover" styleClass="error-message" />
							</div>

							<div class="form-group">
								<label for="waitingPeriod" class="required-field">Waiting
									Period (Month)</label>
								<h:selectOneMenu id="waitingPeriod" styleClass="form-control"
									value="#{createInsuranceController.insurancePlan.waitingPeriod}">
									<f:selectItem itemLabel="-- Select --" itemValue="" />
									<f:selectItem itemLabel="1 Month" itemValue="1" />
									<f:selectItem itemLabel="2 Months" itemValue="2" />
									<f:selectItem itemLabel="3 Months" itemValue="3" />
								</h:selectOneMenu>
								<br />
								<h:message for="waitingPeriod" styleClass="error-message" />
							</div>

							<div class="form-group">
								<label for="yearsToAdd" class="required-field">Duration</label>
								<h:selectOneMenu id="yearsToAdd" styleClass="form-control"
									value="#{createInsuranceController.yearsToAdd}">
									<f:selectItem itemLabel="--Select--" itemValue="" />
									<f:selectItem itemLabel="1 Year" itemValue="1" />
									<f:selectItem itemLabel="2 Years" itemValue="2" />
									<f:selectItem itemLabel="3 Years" itemValue="3" />
									<f:selectItem itemLabel="5 Years" itemValue="5" />
									<f:selectItem itemLabel="10 Years" itemValue="10" />
									<f:selectItem itemLabel="12 Years" itemValue="12" />
								</h:selectOneMenu>
								<br />
								<h:message for="yearsToAdd" styleClass="error-message" />
							</div>

							<!-- Date and Member Info -->
							<div class="form-group">
								<label for="activeOn" class="required-field">Active On</label>
								<h:inputText id="activeOn" styleClass="form-control date-input"
									value="#{createInsuranceController.insurancePlan.activeOn}">
									<f:convertDateTime pattern="yyyy-MM-dd" />
								</h:inputText>
								<br />
								<h:message for="activeOn" styleClass="error-message" />
							</div>

							<div class="form-group">
								<label for="maximumMemberAllowed" class="required-field">Maximum
									Members Allowed</label>
								<h:selectOneMenu id="maximumMemberAllowed"
									styleClass="form-control"
									value="#{createInsuranceController.insurancePlan.maximumMemberAllowed}">
									<f:selectItem itemLabel="-- Select --" itemValue="" />
									<f:selectItem itemLabel="1 member" itemValue="1" />
									<f:selectItem itemLabel="2 member" itemValue="2" />
									<f:selectItem itemLabel="3 member" itemValue="3" />
									<f:selectItem itemLabel="4 member" itemValue="4" />
									<f:selectItem itemLabel="5 member" itemValue="5" />
									<f:selectItem itemLabel="6 member" itemValue="6" />
									<f:selectItem itemLabel="7 member" itemValue="7" />
									<f:selectItem itemLabel="8 member" itemValue="8" />
								</h:selectOneMenu>
								<br />
								<h:message for="maximumMemberAllowed" styleClass="error-message" />
							</div>

							<div class="form-group">
								<label for="minimumMemberAllowed" class="required-field">Minimum
									Members Allowed</label>
								<h:selectOneMenu id="minimumMemberAllowed"
									styleClass="form-control"
									value="#{createInsuranceController.insurancePlan.minimumMeberAllowed}">
									<f:selectItem itemLabel="-- Select --" itemValue="" />
									<f:selectItem itemLabel="1 member" itemValue="1" />
									<f:selectItem itemLabel="2 member" itemValue="2" />
								</h:selectOneMenu>
								<br />
								<h:message for="minimumMemberAllowed" styleClass="error-message" />
							</div>

							<div class="form-group" style="grid-column: 1/-1;">
								<label for="description" class="required-field">Description</label>
								<h:inputTextarea id="description" styleClass="form-control"
									value="#{createInsuranceController.insurancePlan.description}"
									rows="4" />
								<br />
								<h:message for="description" styleClass="error-message" />
							</div>
						</div>
					</div>
					<!-- Member Details Section -->
					<div class="card" style="margin-top: 20px;">
						<div class="card-header">Member Details</div>
						<div class="card-body">

							<h:panelGroup>
								<div id="memberDetail">
									<h3 class="sub-heading">Member Details</h3>
									<h4>Multiple selection for Members</h4>
									<div class="member-options">
										<div class="member-option">
											<h:selectBooleanCheckbox
												value="#{createInsuranceController.relationMap['DAUGHTER1']}" />
											<label>Daughter1</label>
										</div>
										<div class="member-option">
											<h:selectBooleanCheckbox
												value="#{createInsuranceController.relationMap['DAUGHTER2']}" />
											<label>Daughter2</label>
										</div>
										<div class="member-option">
											<h:selectBooleanCheckbox
												value="#{createInsuranceController.relationMap['SON1']}" />
											<label>Son1</label>
										</div>
										<div class="member-option">
											<h:selectBooleanCheckbox
												value="#{createInsuranceController.relationMap['SON2']}" />
											<label>Son2</label>
										</div>
										<div class="member-option">
											<h:selectBooleanCheckbox
												value="#{createInsuranceController.relationMap['FATHER']}" />
											<label>Father</label>
										</div>
										<div class="member-option">
											<h:selectBooleanCheckbox
												value="#{createInsuranceController.relationMap['MOTHER']}" />
											<label>Mother</label>
										</div>
										<div class="member-option">
											<h:selectBooleanCheckbox
												value="#{createInsuranceController.relationMap['HUSBAND']}" />
											<label>Husband</label>
										</div>
										<div class="member-option">
											<h:selectBooleanCheckbox
												value="#{createInsuranceController.relationMap['WIFE']}" />
											<label>Wife</label>
										</div>
									</div>
									<br />
									<h:message for="memberValidation" styleClass="error-message" />
								</div>
							</h:panelGroup>

							<h:panelGroup>
								<div id="individulMember">
									<h3 class="sub-heading">Member Details</h3>

									<h4>Individual Member</h4>
									<div style="display: flex; align-items: center; gap: 15px;">
										<label>Select Gender:</label>
										<h:selectOneRadio id="individualMemberGender"
											value="#{createInsuranceController.individualMemberGender}">
											<f:selectItem itemLabel="Male" itemValue="MALE" />
											<f:selectItem itemLabel="Female" itemValue="FEMALE" />
										</h:selectOneRadio>
									</div>
									<br />
									<h:message for="individualMemberGender"
										styleClass="error-message" />
								</div>
							</h:panelGroup>
						</div>
					</div>

					<!-- Coverage Options Section -->
					<div class="card" style="margin-top: 20px;">
						<div class="card-header">Add Coverage Options</div>
						<div class="card-body">
							<h3 class="sub-heading">Coverage Options</h3>

							<div class="coverage-options">
								<!-- Silver Option -->
								<div class="coverage-card silver">
									<h3>Silver Option</h3>
									<div class="form-group">
										<label for="PremiumAmount" class="required-field">Premium
											Amount</label>
										<h:inputText id="PremiumAmount" styleClass="form-control"
											value="#{createInsuranceController.coverageOption1.premiumAmount}">
											<f:convertNumber type="number" maxFractionDigits="2"
												minFractionDigits="2" />
										</h:inputText>
										<br />
										<h:message for="PremiumAmount" styleClass="error-message" />
									</div>

									<div class="form-group">
										<label for="CoverageAmount" class="required-field">Coverage
											Amount</label>
										<h:inputText id="CoverageAmount" styleClass="form-control"
											value="#{createInsuranceController.coverageOption1.coverageAmount}">
											<f:convertNumber type="number" maxFractionDigits="2"
												minFractionDigits="2" />
										</h:inputText>
										<br />
										<h:message for="CoverageAmount" styleClass="error-message" />
									</div>

									<div class="form-group">
										<label for="coverageType" class="required-field">Coverage
											Type</label>
										<h:selectOneMenu id="coverageType" styleClass="form-control"
											value="#{createInsuranceController.coverageOption1.coverageType}">
											<f:selectItem itemLabel="SILVER" itemValue="SILVER" />
										</h:selectOneMenu>
									</div>

									<div class="checkbox-container">
										<h:selectBooleanCheckbox id="checkbox"
											value="#{createInsuranceController.silver}" />
										<label for="checkbox">Select this option</label>
									</div>
								</div>

								<!-- Gold Option -->
								<div class="coverage-card gold">
									<h3>Gold Option</h3>
									<div class="form-group">
										<label for="PremiumAmount2">Premium Amount</label>
										<h:inputText id="PremiumAmount2" styleClass="form-control"
											value="#{createInsuranceController.coverageOption2.premiumAmount}">
											<f:convertNumber type="number" maxFractionDigits="2"
												minFractionDigits="2" />
										</h:inputText>
										<br />
										<h:message for="PremiumAmount2" styleClass="error-message" />
									</div>

									<div class="form-group">
										<label for="CoverageAmount2">Coverage Amount</label>
										<h:inputText id="CoverageAmount2" styleClass="form-control"
											value="#{createInsuranceController.coverageOption2.coverageAmount}">
											<f:convertNumber type="number" maxFractionDigits="2"
												minFractionDigits="2" />
										</h:inputText>
										<br />
										<h:message for="CoverageAmount2" styleClass="error-message" />
									</div>

									<div class="form-group">
										<label for="coverageType2" class="required-field">Coverage
											Type</label>
										<h:selectOneMenu id="coverageType2" styleClass="form-control"
											value="#{createInsuranceController.coverageOption2.coverageType}">
											<f:selectItem itemLabel="GOLD" itemValue="GOLD" />
										</h:selectOneMenu>
										<br />
										<h:message for="coverageType2" styleClass="error-message" />
									</div>

									<div class="checkbox-container">
										<h:selectBooleanCheckbox id="gold"
											value="#{createInsuranceController.gold}" />
										<label for="gold">Select this option</label>
									</div>
								</div>

								<!-- Platinum Option -->
								<div class="coverage-card platinum">
									<h3>Platinum Option</h3>
									<div class="form-group">
										<label for="PremiumAmount3">Premium Amount</label>
										<h:inputText id="PremiumAmount3" styleClass="form-control"
											value="#{createInsuranceController.coverageOption3.premiumAmount}">
											<f:convertNumber type="number" maxFractionDigits="2"
												minFractionDigits="2" />
										</h:inputText>
										<br />
										<h:message for="PremiumAmount3" styleClass="error-message" />
									</div>

									<div class="form-group">
										<label for="CoverageAmount3">Coverage Amount</label>
										<h:inputText id="CoverageAmount3" styleClass="form-control"
											value="#{createInsuranceController.coverageOption3.coverageAmount}">
											<f:convertNumber type="number" maxFractionDigits="2"
												minFractionDigits="2" />
										</h:inputText>
										<br />
										<h:message for="CoverageAmount3" styleClass="error-message" />
									</div>

									<div class="form-group">
										<label for="coverageType3" class="required-field">Coverage
											Type</label>
										<h:selectOneMenu id="coverageType3" styleClass="form-control"
											value="#{createInsuranceController.coverageOption3.coverageType}">
											<f:selectItem itemLabel="PLATINUM" itemValue="PLATINUM" />
										</h:selectOneMenu>
										<br />
										<h:message for="coverageType3" styleClass="error-message" />
									</div>

									<div class="checkbox-container">
										<h:selectBooleanCheckbox id="platinum"
											value="#{createInsuranceController.platinum}" />
										<label for="platinum">Select this option</label>
									</div>
								</div>
							</div>
						</div>
					</div>

					<!-- Form Actions -->
					<div class="button-group">
						<h:commandButton value="Cancel"
							action="insuranceAdminDashBoard.jsp"
							styleClass="btn btn-secondary" />
						<h:commandButton value="Create"
							action="#{createInsuranceController.addSilverOnlyMendatory}"
							styleClass="btn" />
					</div>
				</h:form>
			</div>
		</div>
	</div>
</body>
	</html>
</f:view>

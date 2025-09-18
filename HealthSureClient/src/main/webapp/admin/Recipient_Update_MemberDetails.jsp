<%-- <%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<f:view>
	<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Recipient</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />

<style>
body {
	background-color: #f3f4f6;
	color: #1f2937;
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	min-height: 100vh;
}

/* Navbar */
.navbar {
	background-color: #1e3a8a; /* Deep blue */
	padding: 15px 0;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
	display: flex;
	justify-content: center;
	gap: 30px;
	margin-bottom: 2rem;
}

.nav-link {
	color: white;
	text-decoration: none;
	font-size: 18px;
	font-weight: 600;
	transition: color 0.3s ease;
}

.nav-link:hover {
	color: #facc15; /* Yellow hover */
}

/* Form container */
.form-container {
	max-width: 520px;
	margin: auto;
	background-color: white;
	padding: 32px;
	border-radius: 8px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
	margin-top: 200px;
}

.form-title {
	text-align: center;
	font-size: 24px;
	font-weight: bold;
	margin-bottom: 24px;
}

.form-group {
	margin-bottom: 16px;
}

.form-group label {
	display: block;
	margin-bottom: 6px;
	font-weight: 600;
}

.form-group input[type="text"] {
	width: 100%;
	padding: 10px 12px;
	border: 1px solid #d1d5db;
	border-radius: 4px;
	font-size: 14px;
	outline: none;
}

.form-group input[readonly] {
	background-color: #e5e7eb;
}

.form-group input:focus {
	border-color: #2563eb;
	box-shadow: 0 0 0 2px rgba(37, 99, 235, 0.2);
}

.error-msg {
	color: red;
	font-size: 13px;
	margin-top: 4px;
}

/* Button styling with rounded corners */
.btn {
	background-color: #003366;
	color: white;
	font-weight: 600;
	padding: 10px 20px;
	border: none;
	border-radius: 8px; /* Rounded corners */
	cursor: pointer;
	transition: background-color 0.3s;
}

/* Container for Update & Discard (same line) */
.button-row {
	display: flex;
	justify-content: space-between;
	margin-bottom: 20px; /* No extra space below */
}

/* Container for Back & Dashboard (next line) */
.link-group {
	display: flex;
	justify-content: space-between;
	margin-top: 20px; /* No extra space above */
}

/* Style for fetch button and hId container */
.hid-fetch-group {
	display: flex;
	gap: 10px;
	align-items: center;
}

.fetch-btn {
	background-color: #4CAF50;
	color: white;
	padding: 10px 15px;
	border: none;
	border-radius: 8px;
	cursor: pointer;
	transition: background-color 0.3s;
}

.fetch-btn:hover {
	background-color: #45a049;
}

/* Initially disabled fields */
.initially-disabled {
	background-color: #e5e7eb;
	pointer-events: none;
}

.link-group h\:commandLink {
	color: #1d4ed8;
	margin: 0 12px;
	font-weight: 500;
	text-decoration: none;
}

.link-group h\:commandLink:hover {
	text-decoration: underline;
}

.required-wrapper {
	position: relative;
}

.form-group label.required::after {
	content: "*";
	color: red;
	font-weight: bold;
	margin-left: 4px;
	font-size: 16px;
}

/* Toast Box */
#toastMessage {
	display: none;
	position: relative;
	margin-bottom: 15px;
	padding: 10px 20px;
	border-radius: 6px;
	font-weight: bold;
	text-align: center;
	font-size: 14px;
	color: white;
	z-index: 1;
}

.toast-visible {
	display: block;
}

.toast-hidden {
	display: none;
}
/* Color classes for different message types */
.toast-info {
	background-color: #4CAF50;
	color: white;
}

.toast-error {
	background-color: #F44336;
	color: white;
}

.toast-warn {
	background-color: #FFC107;
	color: black;
}

/* Loading Overlay Styles */
.loading-overlay {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(255, 255, 255, 0.8);
	display: flex;
	justify-content: center;
	align-items: center;
	z-index: 9999;
	backdrop-filter: blur(2px);
}

.loading-content {
	text-align: center;
	color: #2563eb;
	font-weight: bold;
	font-size: 1.2rem;
}

.loading-spinner {
	border: 8px solid #f3f3f3;
	border-top: 8px solid #3498db;
	border-radius: 50%;
	width: 30px;
	height: 30px;
	animation: spin 1s linear infinite;
	margin: 0 auto 15px;
}

@
keyframes spin { 0% {
	transform: rotate(0deg);
}

100
%
{
transform
:
rotate(
360deg
);
}
}

/* Disabled form during loading */
.form-disabled {
	opacity: 0.7;
	pointer-events: none;
}
</style>
</head>

<body>


	<!-- Include Admin Navbar -->
	<jsp:include page="/navbar/NavAdmin.jsp" />

	<!--Form Container -->
	<div class="form-container">
		<div class="form-title">Update Recipient Details</div>

		<h:form prependId="false" id="mainForm">
			<!-- Health ID with Fetch button -->
			<div class="form-group">
				<h:outputLabel for="searchHid" value="Health ID:"
					styleClass="required" />
				<div class="hid-fetch-group">
					<div class="required-wrapper">
						<h:inputText id="searchHid"
							value="#{recipientController.searchHid}" />
					</div>
					<h:commandButton value="Search"
						action="#{recipientController.fetchRecipientBySearchHid}"
						styleClass="fetch-btn" onclick="showLoading('search')" />
				</div>
				<h:message for="searchHid" styleClass="error-msg" />
			</div>





			<!-- To show form fields only if recipient exists -->
			<h:panelGroup rendered="#{not empty recipientController.recipient}">
				<div class="form-group">
					<h:outputLabel for="firstName" value="First Name:"
						styleClass="required" />
					<div class="required-wrapper">
						<h:inputText id="firstName"
							value="#{recipientController.recipient.firstName}" />
					</div>
					<h:message for="firstName" styleClass="error-msg" />
				</div>

				<div class="form-group">
					<h:outputLabel for="lastName" value="Last Name:"
						styleClass="required" />
					<div class="required-wrapper">
						<h:inputText id="lastName"
							value="#{recipientController.recipient.lastName}" />
					</div>
					<h:message for="lastName" styleClass="error-msg" />
				</div>	

				<div class="form-group">
					<h:outputLabel for="mobile" value="Mobile:" styleClass="required" />
					<div class="required-wrapper">
						<h:inputText id="mobile"
							value="#{recipientController.recipient.mobile}" label="Mobile"
							maxlength="10" />
					</div>
					<h:message for="mobile" styleClass="error-msg" />
				</div>

				<div class="form-group">
					<h:outputLabel for="email" value="Email:" styleClass="required" />
					<div class="required-wrapper">
						<h:inputText id="email"
							value="#{recipientController.recipient.email}" />
					</div>
					<h:message for="email" styleClass="error-msg" />
				</div>

				<div class="form-group">
					<h:outputLabel for="address" value="Address:" />
					<div class="required-wrapper">
						<h:inputText id="address"
							value="#{recipientController.recipient.address}" />
					</div>
					<h:message for="address" styleClass="error-msg" />
				</div>







				<!-- Update and Discard buttons -->
				<div class="button-row">
					<h:commandButton value="Update Recipient"
						action="#{recipientController.updateRecipient}" styleClass="btn"
						onclick="showLoading('update')" />
					<h:commandButton value="Discard Changes"
						onclick="showModal(); return false;" styleClass="btn" />
				</div>
			</h:panelGroup>
		</h:form>





		<!-- Navigation buttons in their own form -->
		<h:form styleClass="link-group">
			<h:commandButton value="Goto search page"
				action="#{recipientController.goToSearch}" styleClass="btn"
				immediate="true" />
			<h:commandButton value="⬅️ Back to Dashboard"
				action="/admin/Admin_Dashboard_Recipient?faces-redirect=true"
				styleClass="btn" immediate="true" />

		</h:form>






		<!-- Confirm Discard Modal -->
		<div id="confirmResetModal"
			style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.6); justify-content: center; align-items: center; z-index: 1000;">
			<div
				style="background: white; padding: 20px; border-radius: 8px; width: 300px; text-align: center;">
				<h3 style="margin-bottom: 16px;">Discard Changes?</h3>
				<p>Are you sure you want to discard all unsaved changes?</p>
				<div style="margin-top: 20px;">
					<h:form id="resetForm">
						<h:commandButton value="Yes, Discard"
							action="#{recipientController.resetUpdate}" styleClass="btn" />
					</h:form>
					<button class="btn" style="background-color: grey;"
						onclick="hideModal()">Cancel</button>
				</div>
			</div>
		</div>
	</div>







	<!-- Loading Overlay -->
	<div id="loadingOverlay" class="loading-overlay" style="display: none;">
		<div class="loading-content">
			<div class="loading-spinner"></div>
			<div id="loadingText">Processing...</div>
		</div>
	</div>

	<!-- Toast Message Section -->
	<h:panelGroup id="toastWrapper" layout="block" style="display:none;">
		<h:messages id="globalMessages" globalOnly="true" layout="table"
			styleClass="toast-message" infoClass="toast-info"
			errorClass="toast-error" warnClass="toast-warn" />
	</h:panelGroup>



	<script>
        function showModal() {
            document.getElementById("confirmResetModal").style.display = "flex";
        }

        function hideModal() {
            document.getElementById("confirmResetModal").style.display = "none";
        }
        
        function enableFormFields() {
            document.querySelectorAll('.initially-disabled').forEach(field => {
                field.classList.remove('initially-disabled');
                field.style.backgroundColor = 'white';
                field.readOnly = false;
            });
        }
        
        function limitInput(input) {
            if (input.value.length > 10) {
                input.value = input.value.slice(0, 10);
            }
        }
        
        // Loading overlay functions
        function showLoading(action) {
            const overlay = document.getElementById("loadingOverlay");
            const loadingText = document.getElementById("loadingText");
            const form = document.getElementById("mainForm");
            
            // Set appropriate text based on action
            if (action === 'search') {
                loadingText.textContent = "Searching...";
            } else if (action === 'update') {
                loadingText.textContent = "Updating...";
            }
            
            // Show the overlay
            overlay.style.display = "flex";
            
            // Disable form interactions
            if (form) {
                form.classList.add("form-disabled");
            }
            
            // Auto-hide after 5 seconds as a safety measure
            setTimeout(hideLoading, 5000);
        }
        
        function hideLoading() {
            const overlay = document.getElementById("loadingOverlay");
            const form = document.getElementById("mainForm");
            
            overlay.style.display = "none";
            
            // Re-enable form interactions
            if (form) {
                form.classList.remove("form-disabled");
            }
        }
        
        // Show toast messages on page load
        document.addEventListener('DOMContentLoaded', function() {
            var toastWrapper = document.getElementById('toastWrapper');
            var messages = document.querySelectorAll('.toast-message');
            
            if (messages.length > 0) {
                toastWrapper.style.display = 'block';
                
                // Position the toast
                toastWrapper.style.position = 'fixed';
                toastWrapper.style.top = '100px';
                toastWrapper.style.left = '50%';
                toastWrapper.style.transform = 'translateX(-50%)';
                toastWrapper.style.zIndex = '9999';
                
                // Auto-hide after 4 seconds
                setTimeout(function() {
                    toastWrapper.style.display = 'none';
                }, 4000);
            }
        });
    </script>
</body>
	</html>
</f:view> --%>



















 <%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<f:view>
	<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Recipient</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />

<style>
body {
	background-color: #f3f4f6;
	color: #1f2937;
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	min-height: 100vh;
}

/* Navbar */
.navbar {
	background-color: #1e3a8a; /* Deep blue */
	padding: 15px 0;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
	display: flex;
	justify-content: center;
	gap: 30px;
	margin-bottom: 2rem;
}

.nav-link {
	color: white;
	text-decoration: none;
	font-size: 18px;
	font-weight: 600;
	transition: color 0.3s ease;
}

.nav-link:hover {
	color: #facc15; /* Yellow hover */
}

/* Form container */
.form-container {
	max-width: 520px;
	margin: auto;
	background-color: white;
	padding: 32px;
	border-radius: 8px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
	margin-top: 200px;
}

.form-title {
	text-align: center;
	font-size: 24px;
	font-weight: bold;
	margin-bottom: 24px;
}

.form-group {
	margin-bottom: 16px;
}

.form-group label {
	display: block;
	margin-bottom: 6px;
	font-weight: 600;
}

.form-group input[type="text"] {
	width: 100%;
	padding: 10px 12px;
	border: 1px solid #d1d5db;
	border-radius: 4px;
	font-size: 14px;
	outline: none;
}

.form-group input[readonly] {
	background-color: #e5e7eb;
}

.form-group input:focus {
	border-color: #2563eb;
	box-shadow: 0 0 0 2px rgba(37, 99, 235, 0.2);
}

.error-msg {
	color: red;
	font-size: 13px;
	margin-top: 4px;
}

/* Button styling with rounded corners */
.btn {
	background-color: #003366;
	color: white;
	font-weight: 600;
	padding: 10px 20px;
	border: none;
	border-radius: 8px; /* Rounded corners */
	cursor: pointer;
	transition: background-color 0.3s;
}

/* Container for Update & Discard (same line) */
.button-row {
	display: flex;
	justify-content: space-between;
	margin-bottom: 20px; /* No extra space below */
}

/* Container for Back & Dashboard (next line) */
.link-group {
	display: flex;
	justify-content: space-between;
	margin-top: 20px; /* No extra space above */
}

/* Style for fetch button and hId container */
.hid-fetch-group {
	display: flex;
	gap: 10px;
	align-items: center;
}

.fetch-btn {
	background-color: #4CAF50;
	color: white;
	padding: 10px 15px;
	border: none;
	border-radius: 8px;
	cursor: pointer;
	transition: background-color 0.3s;
}

.fetch-btn:hover {
	background-color: #45a049;
}

/* Initially disabled fields */
.initially-disabled {
	background-color: #e5e7eb;
	pointer-events: none;
}

.link-group h\:commandLink {
	color: #1d4ed8;
	margin: 0 12px;
	font-weight: 500;
	text-decoration: none;
}

.link-group h\:commandLink:hover {
	text-decoration: underline;
}

.required-wrapper {
	position: relative;
}

.form-group label.required::after {
	content: "*";
	color: red;
	font-weight: bold;
	margin-left: 4px;
	font-size: 16px;
}

/* Toast Box */
#toastMessage {
	display: none;
	position: relative;
	margin-bottom: 15px;
	padding: 10px 20px;
	border-radius: 6px;
	font-weight: bold;
	text-align: center;
	font-size: 14px;
	color: white;
	z-index: 1;
}

.toast-visible {
	display: block;
}

.toast-hidden {
	display: none;
}
/* Color classes for different message types */
.toast-info {
	background-color: #4CAF50;
	color: white;
}

.toast-error {
	background-color: #F44336;
	color: white;
}

.toast-warn {
	background-color: #FFC107;
	color: black;
}

/* Loading Overlay Styles */
.loading-overlay {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(255, 255, 255, 0.8);
	display: flex;
	justify-content: center;
	align-items: center;
	z-index: 9999;
	backdrop-filter: blur(2px);
}

.loading-content {
	text-align: center;
	color: #2563eb;
	font-weight: bold;
	font-size: 1.2rem;
}

.loading-spinner {
	border: 8px solid #f3f3f3;
	border-top: 8px solid #3498db;
	border-radius: 50%;
	width: 30px;
	height: 30px;
	animation: spin 1s linear infinite;
	margin: 0 auto 15px;
}

@
keyframes spin { 0% {
	transform: rotate(0deg);
}

100
%
{
transform
:
rotate(
360deg
);
}
}

/* Disabled form during loading */
.form-disabled {
	opacity: 0.7;
	pointer-events: none;
}



.details {
    width: 100%;
    border-collapse: collapse;
}
</style>
</head>

<body>


	<!-- Include Admin Navbar -->
	<jsp:include page="/navbar/NavAdmin.jsp" />

	<!--Form Container -->
	<div class="form-container">
		<div class="form-title">Update Recipient Details</div>





		<h:form prependId="false" id="mainForm">
		
		<!-- Always rendered until edi tmode disables it -->
			<!-- Health ID with Fetch button -->
			<div class="form-group">
				<h:outputLabel for="searchHid" value="Health ID:"
					styleClass="required" />
				<div class="hid-fetch-group">
					<div class="required-wrapper">
						<h:inputText id="searchHid"
							value="#{recipientController.searchHid}" 
 							disabled="#{recipientController.editMode }"/>
 				    </div>
					<h:commandButton value="Search"
						action="#{recipientController.fetchRecipientBySearchHid}"
						styleClass="fetch-btn" onclick="showLoading('search')" 
						disabled="#{recipientController.editMode }
						"/>
				</div>
				<h:message for="searchHid" styleClass="error-msg" />
			</div>
			
			
		
			
			
			<!--rendered only if the searched recipient is found and notin edit mode  -->
			<h:panelGroup rendered="#{not empty recipientController.recipient and not recipientController.editMode}">
          <div style="position: relative; padding: 15px; border: 1px solid #ccc; border-radius: 8px;">
    
    <!-- Edit button top right -->
    <h:commandButton value="Edit" 
                     action="#{recipientController.enableEditMode}"
                     styleClass="fetch-btn"
                     style="position: absolute; top: 10px; right: 10px;"/>





         <!-- Member Details -->
            <div class="details-card">
                <div class="details-title">Member Details</div>
                
                
                
                <h:panelGrid columns="2" styleClass="details">
                
                    <h:outputText value="Member ID:" />
                    <h:outputText value="#{recipientController.recipient.hId}" />
 
                    <h:outputText value="First Name:" />
                    <h:outputText value="#{recipientController.recipient.firstName}" />
 
                     <h:outputText value="Last Name:" />
                    <h:outputText value="#{recipientController.recipient.lastName}" />              
                    
                     <h:outputText value="Mobile:" />
                    <h:outputText value="#{recipientController.recipient.mobile}" />
                    
                     <h:outputText value="Email:" />
                    <h:outputText value="#{recipientController.recipient.email}" />
                    
                     <h:outputText value="Address:" />
                    <h:outputText value="#{recipientController.recipient.address}" />
                    
                
                    
                    </h:panelGrid>
              </div>
         </div>
</h:panelGroup>






<!-- Rendered only if the recipient is found and and edit mode is enabled -->
			<!-- To show form fields only if recipient exists -->
			<h:panelGroup rendered="#{not empty recipientController.recipient and recipientController.editMode} ">
			
			
			
				<div class="form-group">
					<h:outputLabel for="firstName" value="First Name:"
						styleClass="required" />
					<div class="required-wrapper">
						<h:inputText id="firstName"
							value="#{recipientController.recipient.firstName}" />
					</div>
					<h:message for="firstName" styleClass="error-msg" />
				</div>





				<div class="form-group">
					<h:outputLabel for="lastName" value="Last Name:"
						styleClass="required" />
					<div class="required-wrapper">
						<h:inputText id="lastName"
							value="#{recipientController.recipient.lastName}" />
					</div>
					<h:message for="lastName" styleClass="error-msg" />
				</div>





				<div class="form-group">
					<h:outputLabel for="mobile" value="Mobile:" styleClass="required" />
					<div class="required-wrapper">
						<h:inputText id="mobile"
							value="#{recipientController.recipient.mobile}" label="Mobile"
							maxlength="10" />
					</div>
					<h:message for="mobile" styleClass="error-msg" />
				</div>



				<div class="form-group">
					<h:outputLabel for="email" value="Email:" styleClass="required" />
					<div class="required-wrapper">
						<h:inputText id="email"
							value="#{recipientController.recipient.email}" />
					</div>
					<h:message for="email" styleClass="error-msg" />
				</div>



				<div class="form-group">
					<h:outputLabel for="address" value="Address:" />
					<div class="required-wrapper">
						<h:inputText id="address"
							value="#{recipientController.recipient.address}" />
					</div>
					<h:message for="address" styleClass="error-msg" />
				</div>



				<!-- Update and Discard buttons -->
				<div class="button-row">
					<h:commandButton value="Update Recipient"
						action="#{recipientController.updateRecipient}" styleClass="fetch-btn"
						onclick="showLoading('update')" />
					<h:commandButton value="Discard Changes"
						onclick="showModal(); return false;" styleClass="btn" />
				</div>
			</h:panelGroup>
			
			
			
			
			
			
			
		</h:form>




<!-- Always Rendered -->
		<!-- Navigation buttons in their own form -->
		<h:form styleClass="link-group">
			<h:commandButton value="Goto search page"
				action="#{recipientController.goToSearch}" styleClass="btn"
				immediate="true" />
			<h:commandButton value="⬅️ Back to Dashboard"
				action="/admin/Admin_Dashboard_Recipient?faces-redirect=true"
				styleClass="btn" immediate="true" />

		</h:form>





		<!-- Confirm Discard Modal -->
		<div id="confirmResetModal"
			style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.6); justify-content: center; align-items: center; z-index: 1000;">
			<div
				style="background: white; padding: 20px; border-radius: 8px; width: 300px; text-align: center;">
				<h3 style="margin-bottom: 16px;">Discard Changes?</h3>
				<p>Are you sure you want to discard all unsaved changes?</p>
				<div style="margin-top: 20px;">
					<h:form id="resetForm">
						<h:commandButton value="Yes, Discard"
							action="#{recipientController.resetUpdate}" styleClass="btn" />
					</h:form>
					<button class="btn" style="background-color: grey;"
						onclick="hideModal()">Cancel</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Loading Overlay -->
	<div id="loadingOverlay" class="loading-overlay" style="display: none;">
		<div class="loading-content">
			<div class="loading-spinner"></div>
			<div id="loadingText">Processing...</div>
		</div>
	</div>









<!-- rendered only if there are any global messages to show -->
	<!-- Toast Message Section -->
	<h:panelGroup id="toastWrapper" layout="block" style="display:none;">
		<h:messages id="globalMessages" globalOnly="true" layout="table"
			styleClass="toast-message" infoClass="toast-info"
			errorClass="toast-error" warnClass="toast-warn" /> 
	</h:panelGroup>

	<script>
        function showModal() {
            document.getElementById("confirmResetModal").style.display = "flex";
        }

        function hideModal() {
            document.getElementById("confirmResetModal").style.display = "none";
        }
        
        function enableFormFields() {
            document.querySelectorAll('.initially-disabled').forEach(field => {
                field.classList.remove('initially-disabled');
                field.style.backgroundColor = 'white';
                field.readOnly = false;
            });
        }
        
        function limitInput(input) {
            if (input.value.length > 10) {
                input.value = input.value.slice(0, 10);
            }
        }
        
        // Loading overlay functions
        function showLoading(action) {
            const overlay = document.getElementById("loadingOverlay");
            const loadingText = document.getElementById("loadingText");
            const form = document.getElementById("mainForm");
            
            // Set appropriate text based on action
            if (action === 'search') {
                loadingText.textContent = "Searching...";	
            } else if (action === 'update') {
                loadingText.textContent = "Updating...";
            }
            
            // Show the overlay
            overlay.style.display = "flex";
            
            // Disable form interactions
            if (form) {
                form.classList.add("form-disabled");
            }
            
            // Auto-hide after 5 seconds as a safety measure
            setTimeout(hideLoading, 5000);
        }
        
        function hideLoading() {
            const overlay = document.getElementById("loadingOverlay");
            const form = document.getElementById("mainForm");
            
            overlay.style.display = "none";
            
            // Re-enable form interactions
            if (form) {
                form.classList.remove("form-disabled");
            }
        }
         
        // Show toast messages on page load
        document.addEventListener('DOMContentLoaded', function() {
            var toastWrapper = document.getElementById('toastWrapper');
            var messages = document.querySelectorAll('.toast-message');
            
            if (messages.length > 0) {
                toastWrapper.style.display = 'block';
                
                // Position the toast
                toastWrapper.style.position = 'fixed';
                toastWrapper.style.top = '100px';
                toastWrapper.style.left = '50%';
                toastWrapper.style.transform = 'translateX(-50%)';
                toastWrapper.style.zIndex = '9999';
                
                // Auto-hide after 4 seconds
                setTimeout(function() {
                    toastWrapper.style.display = 'none';
                }, 4000);
            }
        });
    </script>
</body>
	</html>
</f:view>



 
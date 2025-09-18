<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<f:view>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search Recipient</title>
    <style>
        body {
            background-color: #f1f8f6; /* Light blue background to match admin dashboard */
            color: #1f2937; /* Dark gray text */
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 100%;
            padding: 40px 60px;
            box-sizing: border-box;
        }

        .form-wrapper {
            max-width: 600px;
            margin: auto;
            background-color: white;
            padding: 32px;
            margin-top: 100px;
            border-radius: 12px; /* Rounded corners like admin dashboard */
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); /* Softer shadow */
        }

        .heading {
            text-align: center;
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 24px;
            color: #000080; /* Dark navy blue like admin dashboard */
        }

        .form-group {
            margin-bottom: 16px;
        }

        .form-group label {
            display: block;
            font-weight: 600;
            margin-bottom: 6px;
        }

        .form-group select,
        .form-group input[type="text"] {
            width: 100%;
            padding: 10px 12px;
            border: 2px solid #003366;
            border-radius: 8px; /* Rounded corners */
            font-size: 14px;
            outline: none;
        }

        .form-group input[type="text"]:focus,
        .form-group select:focus {
            border-color: #2563eb;
            box-shadow: 0 0 0 2px rgba(37, 99, 235, 0.2);
        }

        .error-msg {
    color: red;
    font-size: 15px; /* Increased from 13px */
    margin-top: 6px; /* Increased from 4px */
    font-weight: 500; /* Added for better visibility */
}

       
        
          .btn {
    background-color: #003366;
    color: white;
    font-weight: 600;
    padding: 10px 20px;
    border: none;
    border-radius: 6px; /* Change this line */
    cursor: pointer;
    transition: background-color 0.3s;
}

        .btn:hover {
            background-color: #002244;
        }

        .btn:active {
            background: #003d33;
            transform: translateY(2px);
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        /* Back to Dashboard button style */
        .back-btn {
            background: linear-gradient(135deg, #1e88e5, #0d47a1);
        }

        .back-btn:hover {
            background: linear-gradient(135deg, #0d47a1, #1e88e5);
        }

        .table-wrapper {
            width: 100%;
            max-width: 1000px;
            margin: 40px auto 20px auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            overflow-x: auto;
        }

        .data-table {
            width: 100%;
            border-collapse: collapse;
            font-size: 14px;
            color: #374151;
        }

        .data-table th,
        .data-table td {
            padding: 10px 12px;
            border: 1px solid #d1d5db;
            text-align: left;
        }

        .data-table th {
            background-color: #003366;
            color: white;
            font-weight: 600;
        }

        .data-table th:first-child {
            border-top-left-radius: 8px;
        }

        .data-table th:last-child {
            border-top-right-radius: 8px;
        }

        .no-result {
            text-align: center;
            margin-top: 24px;
            color: red;
            font-weight: 600;
        }
        
        .sortable-header {
            cursor: pointer;
            text-decoration: none !important;
            color: white !important;
        }

        .sortable-header h\:outputText {
            color: white !important;
        }

        .sortable-header:hover {
            color: #facc15 !important;
            text-decoration: none !important;
        }

        .arrow-btn {
            color: #add8e6 !important;
            cursor: pointer;
            text-decoration: none !important;
            background: none;
            border: none;
            padding: 0;
            font-size: 14px;
            outline: none;
        }

        .arrow-btn:hover {
            text-decoration: none !important;
        }
        
        .arrow-btn.active-arrow,
        .arrow-btn:disabled {
            color: #003366 !important;
            cursor: default;
        }

        .arrow-btn:not(:disabled):hover {
            color: #facc15 !important;
        }

        .arrow-btn {
            background: none;
            border: none;
            cursor: pointer;
            color: white;
            font-size: 14px;
            padding: 0;
            text-decoration: none;
        }

        .arrow-btn:hover {
            color: lightblue;
        }
        
        .pagination-numbers {
            display: inline-block;
            margin: 0 10px;
        }

        .page-link {
            padding: 5px 10px;
            text-decoration: none;
            color: #003366;
            border: 1px solid #ddd;
            margin: 0 2px;
        }

        .current-page {
            padding: 5px 10px;
            font-weight: bold;
            color: white;
            background-color: #003366;
        }

        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 15px;
            margin-top: 20px;
            padding: 10px 0;
        }

        .pagination h\:commandButton {
            margin: 0 10px;
            padding: 8px 16px;
            background-color: #1e40af;
            color: white;
            border: none;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
        }

        .pagination h\:commandButton[disabled] {
            background-color: #94a3b8;
            cursor: not-allowed;
        }

        .btn:disabled {
            background-color: #cccccc !important;
            color: #666666 !important;
            cursor: not-allowed !important;
            opacity: 0.65 !important;
            border-radius: 20px !important;
        }

        .btn:disabled:hover {
            background-color: #cccccc !important;
        }

        .btn:not(:disabled):hover {
            background-color: #002244;
            transform: translateY(-1px);
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

      /*   .field-error {
            color: #d32f2f;
            font-size: 13px;
            margin-top: 5px;
            display: block;
        } */
          .field-error {
    color: #ff0000 !important;        /* Bright red */
    font-size: 15px;
    margin-top: 6px;
    display: block;
    font-weight: 700 !important;      /* Bolder text (700 is bold) */
    text-shadow: 0px 0px 1px rgba(255, 255, 255, 0.8); /* Optional: slight text shadow for better visibility */
}

.search-mode-options .ui-selectoneradio {
    display: flex;
    flex-direction: column;
    gap: 12px; /* Adds spacing between options */
    margin-top: 8px;
}

        .error-highlight {
            border-color: #d32f2f !important;
            background-color: #fff5f5 !important;
        }

        .global-error {
            background-color: #ffebee;
            color: #d32f2f;
            padding: 12px 15px;
            margin: 0 auto 20px auto;
            border-radius: 4px;
            border-left: 4px solid #d32f2f;
            max-width: 600px;
            font-weight: bold;
            text-align: center;
        }

        input:disabled {
            background-color: #e6f7ff !important;
            color: #666 !important;
            border-color: #b3e0ff !important;
            cursor: not-allowed !important;
        }
        
        
        /* Optional: Add a placeholder-like text using a pseudo-element */
.disabled-placeholder::after {
    content: "Not needed for 'Show All'";
    color: #888;
    font-style: italic;
    position: absolute;
    top: 50%;
    left: 12px;
    transform: translateY(-50%);
    pointer-events: none;
}


       input:disabled {
            background-color: #e6f7ff !important;
            color: #666 !important;
            border-color: #b3e0ff !important;
            cursor: not-allowed !important;
        }
        
        .form-group label.required::after {
            content: "*";
            color: red;
            font-weight: bold;
            margin-left: 4px;
            font-size: 16px;
        }
        
        .button-container {
            margin: 15px 0;
        }
        
        
          /* New styles for immediate visibility */
        .hidden {
            display: none;
        }
        
        .fade-in {
            animation: fadeIn 0.3s ease-in;
        }
        
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
        
        .search-mode-options {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 6px;
            margin-top: 10px;
            border-left: 4px solid #3498db;
        }
        
        
    </style>
    
     <script>
        // Handle search type change
        function handleSearchTypeChange() {
            const searchType = document.getElementById('FormId:searchType').value;
            const inputValueGroup = document.getElementById('inputValueGroup');
            const searchModeBlock = document.getElementById('searchModeBlock');
            const searchValueInput = document.getElementById('FormId:searchValue');
            
           /*  // Clear the input field when search type changes
            if (searchValueInput) {
                searchValueInput.value = '';
            } */
            
            // Show/hide input field based on selection
            if (searchType === 'showAll' || searchType === '') {
                inputValueGroup.classList.add('hidden');
            } else {
                inputValueGroup.classList.remove('hidden');
                inputValueGroup.classList.add('fade-in');
            }
            
            // Show/hide search mode options for first name
            if (searchType === 'firstName') {
                searchModeBlock.classList.remove('hidden');
                searchModeBlock.classList.add('fade-in');
            } else {
                searchModeBlock.classList.add('hidden');
            }
            
            // Submit form immediately
            document.getElementById('FormId:hiddenSubmit').click();
        }
        
        // Handle search option change
        function handleSearchOptionChange() {
            const useModeChecked = document.querySelector('input[name="FormId:searchOption"]:checked').value === 'useMode';
            const searchModeOptions = document.getElementById('searchModeOptions');
            
            if (useModeChecked) {
                searchModeOptions.classList.remove('hidden');
                searchModeOptions.classList.add('fade-in');
            } else {
                searchModeOptions.classList.add('hidden');
            }
            
            // Submit form immediately to update the backend
            document.getElementById('FormId:hiddenSubmit').click();
        }
        
        // Initialize form state
        document.addEventListener('DOMContentLoaded', function() {
            // Set up initial state
            const searchType = document.getElementById('FormId:searchType').value;
            const inputValueGroup = document.getElementById('inputValueGroup');
            const searchModeBlock = document.getElementById('searchModeBlock');
            
            // Show/hide input field based on initial selection
            if (searchType === 'showAll' || searchType === '') {
                inputValueGroup.classList.add('hidden');
            } else {
                inputValueGroup.classList.remove('hidden');
            }
            
            // Show/hide search mode options for first name
            if (searchType === 'firstName') {
                searchModeBlock.classList.remove('hidden');
                
                // Also check if useMode is selected and show options if needed
                const useModeChecked = document.querySelector('input[name="FormId:searchOption"]:checked');
                if (useModeChecked && useModeChecked.value === 'useMode') {
                    const searchModeOptions = document.getElementById('searchModeOptions');
                    searchModeOptions.classList.remove('hidden');
                }
            } else {
                searchModeBlock.classList.add('hidden');
            }
        });
    </script>
</head>

<body>
    <!-- Include Admin Navbar -->
    <jsp:include page="/navbar/NavAdmin.jsp" />

    <!-- FULL PAGE WRAPPER -->
    <div class="container">
       
        <div class="form-wrapper">
            <div class="heading">Search Recipient</div>
             <!-- Global errors ONLY appear here -->
             
             
        <h:messages globalOnly="true" layout="table" styleClass="global-error"/>
         

            <!-- SEARCH FORM - SINGLE FORM FOR ALL ELEMENTS -->
            <h:form id="FormId">
                <!-- FOR SEARCH TYPE FIELD -->
                <div class="form-group" style="margin-bottom: 24px;">
                    <h:outputLabel for="searchType" value="Search By:" styleClass="required" />
                    <h:selectOneMenu id="searchType" 
                                     value="#{recipientController.searchType}" 
                                     onchange="handleSearchTypeChange()"
                                     styleClass="#{component.valid ? '' : 'error-highlight'}">
                        <f:selectItem itemValue="" itemLabel="--Select--" />
                        <f:selectItem itemValue="hid" itemLabel="Health ID" />
                        <f:selectItem itemValue="firstName" itemLabel="First Name" />
                        <f:selectItem itemValue="mobile" itemLabel="Mobile Number" />
                        <f:selectItem itemValue="showAll" itemLabel="Show All Recipients" />              
                    </h:selectOneMenu>
                    <h:message for="searchType" styleClass="field-error" />
                </div>




                <!-- INPUT VALUE - Only show when not "showAll" -->
                <div class="form-group" id="inputValueGroup" style="margin-bottom: 24px;">
                    <h:outputLabel for="searchValue" value="Enter Value:" styleClass="required"/>
                    <h:inputText id="searchValue" 
                                 value="#{recipientController.searchValue}" 
                                 maxlength="10"
                                 styleClass="#{component.valid ? '' : 'error-highlight'}"/>
                    <h:message for="searchValue" styleClass="field-error" />
                </div>

                <!-- FIRSTNAME SEARCH MODE -->
                <div class="form-group" id="searchModeBlock" style="margin-bottom: 24px;">
                    <h:outputLabel value="First Name Search Option:" />
                    <br/>
                    
                    
                    
                    
                    <%-- <!-- First choice: Exact search or use search modes -->
                    <h:selectOneRadio id="searchOption" value="#{recipientController.searchOption}" 
                                     onchange="handleSearchOptionChange()"
                                     immediate="true">
                        <f:selectItem itemValue="exact" itemLabel="Search with exact name" />
                        <f:selectItem itemValue="useMode" itemLabel="Use search mode" />
                    </h:selectOneRadio> --%>
                    <h:selectOneRadio id="searchOption" value="#{recipientController.searchOption}" 
                 onchange="handleSearchOptionChange()"
                 immediate="true"
                 style="display: flex; flex-direction: column; gap: 10px;"> <!-- Added spacing -->
    <f:selectItem itemValue="exact" itemLabel="Search with exact name" />
    <f:selectItem itemValue="useMode" itemLabel="Use search mode" />
</h:selectOneRadio>
                    
                    <!-- Show search modes only if "Use search mode" is selected -->
                    <div id="searchModeOptions" class="search-mode-options hidden">
                        <h:outputLabel value="Select Search Mode:" />
                        <br/>
                        <h:selectOneRadio value="#{recipientController.nameSearchMode}">
                            <f:selectItem itemValue="startsWith" itemLabel="Starts With" />
                            <f:selectItem itemValue="contains" itemLabel="Contains" />
                        </h:selectOneRadio>
                        <p style="font-size: 12px; color: #666; margin-top: 5px;">
                            <strong>Note:</strong> Select a search mode for partial matching.
                        </p>
                    </div>
             
                </div>
           
                
                <!-- Hidden submit button for immediate form submission -->
                <h:commandButton id="hiddenSubmit" value="Update Form" style="display:none" 
                                action="#{recipientController.updateFormVisibility}" immediate="true" />
                
                <!-- Command Buttons -->
                <h:panelGroup layout="block" style="margin-top: 32px;">
                    <h:commandButton value="Search Recipient"
                                    action="#{recipientController.search}"
                                    styleClass="btn"  />
                    &nbsp;&nbsp;
                    <h:commandButton value="Reset Search"
                                 action="#{recipientController.resetSearch}"
                                 styleClass="btn"/>
                </h:panelGroup>
                  <!-- Back to Dashboard Button -->
                <div class="button-container">
                    <h:commandButton value="⬅️ Back to Dashboard"
                        action="/admin/Admin_Dashboard_Recipient?faces-redirect=true"
                        styleClass="btn back-btn" />
                </div>
            </h:form>
        </div> <!-- Close form-wrapper -->






        <!-- TABLE RESULT - SEPARATE FROM SEARCH FORM -->
        <h:panelGroup rendered="#{not empty recipientController.searchResultList}">
            <div class="table-wrapper">
                <h:form id="tableForm">
       
 <!-- For all search results -->
      
            
             <h:dataTable value="#{recipientController.currentSearchResultsPage}" var="rec"
        rendered="#{not empty recipientController.searchResultList}"
        styleClass="data-table" border="1">
                    
                    
                            <!-- Health ID Column -->
              				<!-- Sorting headers -->
			
<h:column>
    <f:facet name="header">
        <h:panelGroup layout="block" style="display: flex; align-items: center; gap: 6px;" styleClass="sortable-header">
            <!-- Header Label -->
            <h:outputText value="Health ID" />
            
            <!-- Vertical Arrow Buttons -->
            <h:panelGroup layout="block" style="display: flex; flex-direction: column; margin-left: 4px;">
                <!-- Ascending ▲ -->
                <h:commandLink action="#{recipientController.sortByAsc('hId')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('hId') and recipientController.isAscending() ? 'active-arrow' : ''}" 
                               title="Sort Ascending"
                               disabled="#{recipientController.isColumnSorted('hId') and recipientController.isAscending()}">
                    <h:outputText value="▲" />
                </h:commandLink>
                
                <!-- Descending ▼ -->
                <h:commandLink action="#{recipientController.sortByDesc('hId')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('hId') and recipientController.isDescending() ? 'active-arrow' : ''}" 
                               title="Sort Descending"
                               disabled="#{recipientController.isColumnSorted('hId') and recipientController.isDescending()}">
                    <h:outputText value="▼" />
                </h:commandLink>
            </h:panelGroup>
        </h:panelGroup>
    </f:facet>
    <h:commandLink value="#{rec.hId}" action="#{recipientController.prepareUpdate}">
        <f:param name="hid" value="#{rec.hId}" />
    </h:commandLink>
</h:column>





<h:column>
    <f:facet name="header">
        <h:panelGroup layout="block" style="display: flex; align-items: center; gap: 6px;" styleClass="sortable-header">
            <h:outputText value="First Name" />
            <h:panelGroup layout="block" style="display: flex; flex-direction: column; margin-left: 4px;">
                <h:commandLink action="#{recipientController.sortByAsc('firstName')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('firstName') and recipientController.isAscending() ? 'active-arrow' : ''}" 
                               title="Sort Ascending"
                               disabled="#{recipientController.isColumnSorted('firstName') and recipientController.isAscending()}">
                    <h:outputText value="▲" />
                </h:commandLink>
                <h:commandLink action="#{recipientController.sortByDesc('firstName')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('firstName') and recipientController.isDescending() ? 'active-arrow' : ''}" 
                               title="Sort Descending"
                               disabled="#{recipientController.isColumnSorted('firstName') and recipientController.isDescending()}">
                    <h:outputText value="▼" />
                </h:commandLink>
            </h:panelGroup>
        </h:panelGroup>
    </f:facet>
    <h:outputText value="#{rec.firstName}" />
</h:column>




<h:column>
    <f:facet name="header">
        <h:panelGroup layout="block" style="display: flex; align-items: center; gap: 6px;" styleClass="sortable-header">
            <h:outputText value="Last Name" />
            <h:panelGroup layout="block" style="display: flex; flex-direction: column; margin-left: 4px;">
                <h:commandLink action="#{recipientController.sortByAsc('lastName')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('lastName') and recipientController.isAscending() ? 'active-arrow' : ''}" 
                               title="Sort Ascending"
                               disabled="#{recipientController.isColumnSorted('lastName') and recipientController.isAscending()}">
                    <h:outputText value="▲" />
                </h:commandLink>
                <h:commandLink action="#{recipientController.sortByDesc('lastName')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('lastName') and recipientController.isDescending() ? 'active-arrow' : ''}" 
                               title="Sort Descending"
                               disabled="#{recipientController.isColumnSorted('lastName') and recipientController.isDescending()}">
                    <h:outputText value="▼" />
                </h:commandLink>
            </h:panelGroup>
        </h:panelGroup>
    </f:facet>
    <h:outputText value="#{rec.lastName}" />
</h:column>




<h:column>
    <f:facet name="header">
        <h:panelGroup layout="block" style="display: flex; align-items: center; gap: 6px;" styleClass="sortable-header">
            <h:outputText value="Mobile" />
            <h:panelGroup layout="block" style="display: flex; flex-direction: column; margin-left: 4px;">
                <h:commandLink action="#{recipientController.sortByAsc('mobile')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('mobile') and recipientController.isAscending() ? 'active-arrow' : ''}" 
                               title="Sort Ascending"
                               disabled="#{recipientController.isColumnSorted('mobile') and recipientController.isAscending()}">
                    <h:outputText value="▲" />
                </h:commandLink>
                <h:commandLink action="#{recipientController.sortByDesc('mobile')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('mobile') and recipientController.isDescending() ? 'active-arrow' : ''}" 
                               title="Sort Descending"
                               disabled="#{recipientController.isColumnSorted('mobile') and recipientController.isDescending()}">
                    <h:outputText value="▼" />
                </h:commandLink>
            </h:panelGroup>
        </h:panelGroup>
    </f:facet>
    <h:outputText value="#{rec.mobile}" />
</h:column>

<h:column>
    <f:facet name="header">
        <h:outputText value="Gender" />
    </f:facet>
    <h:outputText value="#{rec.gender}" />
</h:column>






<h:column>
    <f:facet name="header">
        <h:panelGroup layout="block" style="display: flex; align-items: center; gap: 6px;" styleClass="sortable-header">
            <h:outputText value="DOB" />
            <h:panelGroup layout="block" style="display: flex; flex-direction: column; margin-left: 4px;">
                <h:commandLink action="#{recipientController.sortByAsc('dob')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('dob') and recipientController.isAscending() ? 'active-arrow' : ''}" 
                               title="Sort Ascending"
                               disabled="#{recipientController.isColumnSorted('dob') and recipientController.isAscending()}">
                    <h:outputText value="▲" />
                </h:commandLink>
                <h:commandLink action="#{recipientController.sortByDesc('dob')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('dob') and recipientController.isDescending() ? 'active-arrow' : ''}" 
                               title="Sort Descending"
                               disabled="#{recipientController.isColumnSorted('dob') and recipientController.isDescending()}">
                    <h:outputText value="▼" />
                </h:commandLink>
            </h:panelGroup>
        </h:panelGroup>
    </f:facet>
    <h:outputText value="#{rec.dob}" />
</h:column>






<h:column>
    <f:facet name="header">
        <h:panelGroup layout="block" style="display: flex; align-items: center; gap: 6px;" styleClass="sortable-header">
            <h:outputText value="Email" />
            <h:panelGroup layout="block" style="display: flex; flex-direction: column; margin-left: 4px;">
                <h:commandLink action="#{recipientController.sortByAsc('email')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('email') and recipientController.isAscending() ? 'active-arrow' : ''}" 
                               title="Sort Ascending"
                               disabled="#{recipientController.isColumnSorted('email') and recipientController.isAscending()}">
                    <h:outputText value="▲" />
                </h:commandLink>
                <h:commandLink action="#{recipientController.sortByDesc('email')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('email') and recipientController.isDescending() ? 'active-arrow' : ''}" 
                               title="Sort Descending"
                               disabled="#{recipientController.isColumnSorted('email') and recipientController.isDescending()}">
                    <h:outputText value="▼" />
                </h:commandLink>
            </h:panelGroup>
        </h:panelGroup>
    </f:facet>
    <h:outputText value="#{rec.email}" />
</h:column>






<h:column>
    <f:facet name="header">
        <h:panelGroup layout="block" style="display: flex; align-items: center; gap: 6px;" styleClass="sortable-header">
            <h:outputText value="Address" />
            <h:panelGroup layout="block" style="display: flex; flex-direction: column; margin-left: 4px;">
                <h:commandLink action="#{recipientController.sortByAsc('address')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('address') and recipientController.isAscending() ? 'active-arrow' : ''}" 
                               title="Sort Ascending"
                               disabled="#{recipientController.isColumnSorted('address') and recipientController.isAscending()}">
                    <h:outputText value="▲" />
                </h:commandLink>
                <h:commandLink action="#{recipientController.sortByDesc('address')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('address') and recipientController.isDescending() ? 'active-arrow' : ''}" 
                               title="Sort Descending"
                               disabled="#{recipientController.isColumnSorted('address') and recipientController.isDescending()}">
                    <h:outputText value="▼" />
                </h:commandLink>
            </h:panelGroup>
        </h:panelGroup>
    </f:facet>
    <h:outputText value="#{rec.address}" />
</h:column>






<h:column>
    <f:facet name="header">
        <h:panelGroup layout="block" style="display: flex; align-items: center; gap: 6px;" styleClass="sortable-header">
            <h:outputText value="Created At" />
            <h:panelGroup layout="block" style="display: flex; flex-direction: column; margin-left: 4px;">
                <h:commandLink action="#{recipientController.sortByAsc('createdAt')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('createdAt') and recipientController.isAscending() ? 'active-arrow' : ''}" 
                               title="Sort Ascending"
                               disabled="#{recipientController.isColumnSorted('createdAt') and recipientController.isAscending()}">
                    <h:outputText value="▲" />
                </h:commandLink>
                <h:commandLink action="#{recipientController.sortByDesc('createdAt')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('createdAt') and recipientController.isDescending() ? 'active-arrow' : ''}" 
                               title="Sort Descending"
                               disabled="#{recipientController.isColumnSorted('createdAt') and recipientController.isDescending()}">
                    <h:outputText value="▼" />
                </h:commandLink>
            </h:panelGroup>
        </h:panelGroup>
    </f:facet>
    <h:outputText value="#{rec.createdAt}" />
</h:column>





 <h:column>
    <f:facet name="header">
        <h:panelGroup layout="block" style="display: flex; align-items: center; gap: 6px;" styleClass="sortable-header">
            <h:outputText value="Last Modified" />
            <h:panelGroup layout="block" style="display: flex; flex-direction: column; margin-left: 4px;">
                <h:commandLink action="#{recipientController.sortByAsc('lastModifiedAt')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('lastModifiedAt') and recipientController.isAscending() ? 'active-arrow' : ''}" 
                               title="Sort Ascending"
                               disabled="#{recipientController.isColumnSorted('lastModifiedAt') and recipientController.isAscending()}">
                    <h:outputText value="▲" />
                </h:commandLink>
                <h:commandLink action="#{recipientController.sortByDesc('lastModifiedAt')}" 
                               styleClass="arrow-btn #{recipientController.isColumnSorted('lastModifiedAt') and recipientController.isDescending() ? 'active-arrow' : ''}" 
                               title="Sort Descending"
                               disabled="#{recipientController.isColumnSorted('lastModifiedAt') and recipientController.isDescending()}">
                    <h:outputText value="▼" />
                </h:commandLink>
            </h:panelGroup>
        </h:panelGroup>
    </f:facet>
      <%-- <h:commandLink value="#{recipientController.getFormattedLastModified(rec.lastModifiedAt)}" 
                   action="#{recipientController.loadChangeHistory}">
        <f:param name="hid" value="#{rec.hId}" />
    </h:commandLink>  --%>
     <h:outputText value="#{recipientController.getFormattedLastModified(rec.lastModifiedAt)}" />
</h:column>    

</h:dataTable>






                  
                    
                    <div class="pagination" style="margin-top: 15px;">
    <div class="pagination-controls" style="display: flex; justify-content: center; align-items: center; gap: 15px;">
        <!-- Previous Button -->
        <h:commandButton value="Previous"
            action="#{recipientController.navigateToPreviousSearchPage}"
            disabled="#{recipientController.isFirstSearchPageDisabled() or recipientController.searchPageCount le 1}" 
            styleClass="btn pagination-btn" />
        
        <!-- Page Indicator -->
        <div style="font-weight: bold; margin: 0 10px;">
            <h:outputText value="#{'Page ' += (recipientController.searchCurrentPageIndex + 1) += ' of ' += recipientController.searchPageCount}" />
        </div>
        
        <!-- Next Button -->
        <h:commandButton value="Next"
            action="#{recipientController.navigateToNextSearchPage}"
            disabled="#{recipientController.isLastSearchPageDisabled() or recipientController.searchPageCount le 1}" 
            styleClass="btn pagination-btn" />
    </div>
</div>
                    
                </h:form>
            </div>
        </h:panelGroup>
    </div>
</body>
</html>
</f:view>






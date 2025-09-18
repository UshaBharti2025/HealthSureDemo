<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<f:view beforePhase="#{recipientController.loadRecipientForUpdate}">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Update Recipient</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
    
    
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

        .link-group h\:commandLink {
            color: #1d4ed8;
            margin: 0 12px;
            font-weight: 500;
            text-decoration: none;
        }

        .link-group h\:commandLink:hover {
            text-decoration: underline;
        }
        
        
        <%-- Toast Box --%>
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


        
    </style>
</head>
<body>

   
     <!-- Include Admin Navbar -->
    <jsp:include page="/navbar/NavAdmin.jsp" />
   
     <!--Form Container -->
    <div class="form-container">
    
    
    
     <!--Show message if recipient is not selected -->
        <h:outputText value=" Please select a recipient first from the search page." 
                      rendered="#{empty recipientController.recipient}" 
                      style="color: red; font-weight: bold;" />
                      
                      
                      
               <!--  to include the pop inside the form   -->   
               <div id="toastMessage"
                     style="display: none; position: fixed; top: 100px; left: 50%;
                transform: translateX(-50%); padding: 10px 20px;
                font-weight: bold; z-index: 9999; font-size: 16px;">
                ></div>
         
            <h:form prependId="false" >
            <div class="form-title">Update Recipient Details</div>
            
            
            
            <div class="form-group">
                <h:outputLabel for="hId" value="Health ID:" styleClass="required"/>
                                    <div class="required-wrapper">
                
                <h:inputText id="hId" value="#{recipientController.recipient.hId}" readonly="true"/>
                </div>
                <h:message for="hId" styleClass="error-msg" />
            </div>





            <div class="form-group">
                <h:outputLabel for="firstName" value="First Name:" styleClass="required"/>
                                    <div class="required-wrapper">
                
                <h:inputText id="firstName" value="#{recipientController.recipient.firstName}" />
                </div>
                <h:message for="firstName" styleClass="error-msg" />
            </div>




            <div class="form-group">
                <h:outputLabel for="lastName" value="Last Name:" styleClass="required"/>
                                    <div class="required-wrapper">
                
                <h:inputText id="lastName" value="#{recipientController.recipient.lastName}" />
                </div>
                <h:message for="lastName" styleClass="error-msg" />
                
            </div>




            <div class="form-group">
                <h:outputLabel for="mobile" value="Mobile:" styleClass="required"/>
                                    <div class="required-wrapper">
                
                <h:inputText id="mobile" value="#{recipientController.recipient.mobile}" 
             required="true" label="Mobile" maxlength="10"  />
             </div>
                <h:message for="mobile" styleClass="error-msg" />
            </div>
            
            
            
            
            
            <div class="form-group">
                <h:outputLabel for="address" value="Address:" styleClass="required"/>
                                    <div class="required-wrapper">
                
                <h:inputText id="address" value="#{recipientController.recipient.address}" 
             required="true" label="Address" maxlength="150"  />
             </div>
                <h:message for="address" styleClass="error-msg" />
            </div>
            
            
            




            <div class="form-group">
                <h:outputLabel for="email" value="Email:" styleClass="required"/>
                                    <div class="required-wrapper">
                
                <h:inputText id="email" value="#{recipientController.recipient.email}" />
                </div>
                <h:message for="email" styleClass="error-msg" />
                
            </div>
            
            
            
            <!-- Update (left) & Discard (right) on same line -->
<div class="button-row">
    <h:commandButton value="Update Recipient" action="#{recipientController.updateRecipient}" styleClass="btn" />
    <h:commandButton value="Discard Changes" onclick="showModal(); return false;" styleClass="btn" />
</div>



  <!-- Back to Search & Dashboard navigation-->
<div class="link-group">
    <h:commandButton value="Back to Search Page" action="#{recipientController.goToSearch}" styleClass="btn"  />
    <h:commandButton value="Go to Recipient Dashboard" action="#{recipientController.goToDashboard}" styleClass="btn"  />
</div>

 


            <!-- Confirm Discard Modal -->
<div id="confirmResetModal" style="display: none; position: fixed; top:0; left:0; width:100%; height:100%; background-color: rgba(0,0,0,0.6); justify-content: center; align-items: center; z-index: 1000;">
    <div style="background: white; padding: 20px; border-radius: 8px; width: 300px; text-align: center;">
        <h3 style="margin-bottom: 16px;">Discard Changes?</h3>
        <p>Are you sure you want to discard all unsaved changes?</p>
        <div style="margin-top: 20px;">
            <h:form id="resetForm">
<h:commandButton 
    value="Yes, Discard" 
    action="#{recipientController.resetUpdate}" 
    styleClass="btn" 
    immediate="true"
/>
            </h:form>
            <button class="btn" style="background-color: grey;" onclick="hideModal()">Cancel</button>
        </div>
    </div>
</div>
     
   
        </h:form>
    </div>
    
    
   <script>
  function showModal() {
    document.getElementById("confirmResetModal").style.display = "flex";
  }

  function hideModal() {
    document.getElementById("confirmResetModal").style.display = "none";
  }
</script>



 


<%-- JS for global pop ups  --%>
<%
    javax.faces.context.FacesContext ctx = javax.faces.context.FacesContext.getCurrentInstance();
    java.util.Iterator<javax.faces.application.FacesMessage> it = ctx.getMessages(null); // null → global messages only

    if (it.hasNext()) {
        javax.faces.application.FacesMessage messageObj = it.next();
        String message = messageObj.getSummary();
        String severity = messageObj.getSeverity().toString();
%>

<script>
  window.onload = function() {
    const toast = document.getElementById("toastMessage");
    toast.innerText = "<%= message.replace("\"", "\\\"") %>";

    toast.style.backgroundColor = "transparent";
    toast.style.border = "none";

    const severity = "<%= severity %>";
    if (severity.includes("INFO")) {
      toast.style.color = "green";
    } else if (severity.includes("ERROR")) {
      toast.style.color = "red";
    } else {
      toast.style.color = "black";
    }

    toast.style.display = "block";
    setTimeout(() => {
      toast.style.display = "none";
    }, 4000);
  }
</script>

<%
    }
%>



<!-- JS to limit the digits to 10 -->
<script>
function limitInput(input) {
  if (input.value.length > 10) {
    input.value = input.value.slice(0, 10);
  }
}
</script>



</body>
</html>
</f:view>
    
    
    
    
  
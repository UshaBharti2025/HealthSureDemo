<!--
  Copyright © 2025 Infinite Computer Solution. All rights reserved.

  @Author: Infinite Computer Solutions

  Description: JSF Reset Password Page using Tailwind CSS for styling.
-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<f:view>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password</title>
    <!-- Use a modern font for a clean look -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" />
    <style>
        /* --- Modern CSS for Reset Password Page --- */
        :root {
            --primary: #4361ee;
            --primary-light: #4895ef;
            --primary-dark: #3a0ca3;
            --dark: #212529;
            --gray: #6c757d;
            --gray-light: #e9ecef;
            --white: #ffffff;
            --success: #059669;
            --danger: #ef4444;
        }

        /* Full page styling with a smooth gradient */
        .modern-body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #f6f9fc 0%, #e6f0ff 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 1rem;
            color: var(--dark);
        }

        .modern-container {
            width: 100%;
            max-width: 440px;
            margin: 0 auto;
        }

        /* Card container with glassy effect and subtle shadow */
        .modern-card {
            background: rgba(255, 255, 255, 0.85);
            border-radius: 16px;
            box-shadow: 0 8px 32px rgba(31, 38, 135, 0.1);
            backdrop-filter: blur(8px);
            -webkit-backdrop-filter: blur(8px);
            border: 1px solid rgba(255, 255, 255, 0.18);
            overflow: hidden;
            padding: 2.5rem;
            transition: all 0.3s ease;
        }

        .modern-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 12px 40px rgba(31, 38, 135, 0.15);
        }
        
        .modern-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 6px;
            background: linear-gradient(90deg, var(--primary), var(--primary-light));
            border-top-left-radius: 16px;
            border-top-right-radius: 16px;
        }

        /* Typography and Headings */
        .modern-text-center {
            text-align: center;
        }
        
        .modern-title {
            font-size: 2rem;
            font-weight: 700;
            color: var(--primary-dark);
            margin-bottom: 0.5rem;
        }
        
        .modern-subtitle {
            color: var(--gray);
            font-size: 0.95rem;
            font-weight: 400;
        }

        .modern-text-sm {
            font-size: 0.875rem;
        }

        .modern-font-medium {
            font-weight: 500;
        }

        .modern-text-gray-700 {
            color: var(--dark);
        }
        
        .modern-text-danger {
            color: var(--danger);
        }
        
        .modern-text-success {
            color: var(--success);
        }

        .modern-text-gray-600 {
            color: var(--gray);
        }
        
        .modern-text-blue-600 {
            color: var(--primary-light);
        }

        /* Form elements */
        .modern-form-group {
            position: relative;
            margin-bottom: 1.5rem;
        }

        .modern-label {
            display: block;
            margin-bottom: 0.5rem;
            font-size: 0.9rem;
            font-weight: 500;
        }
        
        .modern-input-wrapper {
            position: relative;
        }

        .modern-input {
            width: 100%;
            padding: 0.75rem 1rem;
            border: 1px solid #d1d5db;
            border-radius: 0.5rem;
            font-size: 0.9rem;
            font-family: inherit;
            transition: all 0.3s ease;
            background-color: #f3f4f6;
            box-sizing: border-box;
            padding-right: 3rem; /* Make space for the icon */
        }

        .modern-input:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(67, 97, 238, 0.4);
        }
        
        .modern-password-toggle-btn {
            position: absolute;
            right: 1rem;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            cursor: pointer;
            color: var(--gray);
            transition: color 0.2s ease;
        }
        
        .modern-password-toggle-btn:hover {
            color: var(--dark);
        }

        .modern-password-toggle-btn svg {
            width: 20px;
            height: 20px;
        }

        /* Buttons */
        .modern-btn {
            display: inline-block;
            padding: 0.75rem 1rem;
            border-radius: 0.5rem;
            font-size: 1rem;
            font-weight: 600;
            text-align: center;
            text-decoration: none;
            cursor: pointer;
            transition: all 0.3s ease;
            border: none;
            width: 100%;
            display: block;
        }

        .modern-btn-primary {
            background: linear-gradient(135deg, var(--primary), var(--primary-light));
            color: var(--white);
            box-shadow: 0 4px 15px rgba(67, 97, 238, 0.3);
        }

        .modern-btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(67, 97, 238, 0.4);
            background: linear-gradient(135deg, var(--primary-light), var(--primary));
        }

        /* Spacing and Utilities */
        .modern-space-y-6 > * + * {
            margin-top: 1.5rem;
        }

        .modern-mt-1 {
            margin-top: 0.25rem;
        }

        .modern-mt-4 {
            margin-top: 1rem;
        }
        
        .modern-mt-6 {
            margin-top: 1.5rem;
        }

        .modern-ml-1 {
            margin-left: 0.25rem;
        }

        .modern-block {
            display: block;
        }

        .modern-link {
            text-decoration: none;
            transition: color 0.2s ease;
        }
        
        .modern-link:hover {
            text-decoration: underline;
            color: var(--primary-dark);
        }
    </style>
    <script>
        // SVG icons as strings
        const showIcon = `
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-eye"><path d="M2 12s3-7 10-7 10 7 10 7-3 7-10 7-10-7-10-7Z"/><circle cx="12" cy="12" r="3"/></svg>
        `;

        const hideIcon = `
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-eye-off"><path d="M9.88 9.88a3 3 0 1 0 4.24 4.24"/><path d="M10.73 5.08A10.43 10.43 0 0 1 12 5c7 0 10 7 10 7a13.16 13.16 0 0 1-1.67 2.68"/><path d="M6.61 6.61A13.52 13.52 0 0 0 2 12s3 7 10 7a9.75 9.75 0 0 0 5.39-1.61"/><line x1="2" x2="22" y1="2" y2="22"/></svg>
        `;

        // Function to toggle the password visibility
        function togglePasswordVisibility(inputFieldId, button) {
            var input = document.getElementById(inputFieldId);
            if (input.type === "password") {
                input.type = "text";
                button.innerHTML = hideIcon;
            } else {
                input.type = "password";
                button.innerHTML = showIcon;
            }
        }
    </script>
</head>
<body class="modern-body animate__animated animate__fadeIn">

    <div class="modern-container">
        <h:form id="resetPasswordForm" styleClass="modern-card" prependId="false">

            <!-- Heading -->
            <div class="modern-text-center">
                <h2 class="modern-title">Reset Your Password</h2>
                <p class="modern-subtitle">Enter your new password below.</p>
            </div>

            <div class="modern-space-y-6 modern-mt-6">
                <!-- New Password -->
                <div class="modern-form-group">
                    <h:outputLabel for="newPassword" value="New Password:" styleClass="modern-label" />
                    <div class="modern-input-wrapper">
                        <h:inputSecret id="newPassword" value="#{adminController.newPassword}" 
                                       styleClass="modern-input" />
                        <button type="button" class="modern-password-toggle-btn" onclick="togglePasswordVisibility('newPassword', this)">
                            <!-- Initial Show Icon -->
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-eye"><path d="M2 12s3-7 10-7 10 7 10 7-3 7-10 7-10-7-10-7Z"/><circle cx="12" cy="12" r="3"/></svg>
                        </button>
                    </div>
                    <h:message for="newPassword" styleClass="modern-text-danger modern-text-sm modern-mt-1" />
                </div>

                <!-- Confirm Password -->
                <div class="modern-form-group">
                    <h:outputLabel for="confirmPassword" value="Confirm Password:" styleClass="modern-label" />
                    <div class="modern-input-wrapper">
                        <h:inputSecret id="confirmPassword" value="#{adminController.confirmPassword}" 
                                       styleClass="modern-input" />
                        <button type="button" class="modern-password-toggle-btn" onclick="togglePasswordVisibility('confirmPassword', this)">
                            <!-- Initial Show Icon -->
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-eye"><path d="M2 12s3-7 10-7 10 7 10 7-3 7-10 7-10-7-10-7Z"/><circle cx="12" cy="12" r="3"/></svg>
                        </button>
                    </div>
                    <h:message for="confirmPassword" styleClass="modern-text-danger modern-text-sm modern-mt-1" />
                </div>
            </div>

            <!-- Submit Button -->
            <h:commandButton value="Reset Password"
                             action="#{adminController.resetPasswordAfterOtp}"
                             styleClass="modern-btn modern-btn-primary modern-mt-6" />

            <!-- Global Messages -->
            <h:messages globalOnly="true" layout="table"
                        styleClass="modern-text-success modern-text-center modern-font-medium modern-mt-4" />

            <!-- Back to login -->
            <div class="modern-text-center modern-text-sm modern-text-gray-600 modern-mt-4">
                <h:outputLabel value="Back to" />
                <h:outputLink value="Login.jsf"
                              styleClass="modern-text-blue-600 modern-link modern-font-medium modern-ml-1">
                    Login
                </h:outputLink>
            </div>

        </h:form>
    </div>

</body>
</html>
</f:view>
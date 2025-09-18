<!--
  Copyright © 2025 Infinite Computer Solution. All rights reserved.

  @Author: Infinite Computer Solutions

  Description: JSF Forgot Password Page with OTP verification using Tailwind CSS.
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<f:view>
	<!DOCTYPE html>
	<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Forgot Password</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" />
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="/HealthSureClient/resources/css/auth/ForgetPassword.css" />
<script>
	// Global variables for timer management
	let resendTimer;
	let lastOtpTimestamp = 0;
	let resendAttempts = 0;
	let blockedUntil = 0;
	let isBlocked = false;
	let isTimerRunning = false;

	// Function to initialize values from hidden fields
	function initializeValues() {
		try {
			const lastOtpEl = document.getElementById('lastOtpTimestamp');
			const attemptsEl = document.getElementById('resendAttempts');
			const blockedEl = document.getElementById('otpBlockedUntil');

			if (lastOtpEl)
				lastOtpTimestamp = parseInt(lastOtpEl.value) || 0;
			if (attemptsEl)
				resendAttempts = parseInt(attemptsEl.value) || 0;
			if (blockedEl)
				blockedUntil = parseInt(blockedEl.value) || 0;

			isBlocked = blockedUntil > new Date().getTime();
		} catch (e) {
			console.error("Error initializing OTP values:", e);
		}
	}

	function formatTime(num) {
		return num < 10 ? '0' + num : num;
	}

	function startCountdown() {
		if (isTimerRunning) {
			console.log("Countdown already running, skipping initialization.");
			return;
		}

		const button = document.getElementById('resendOtpBtn');
		const countdownContainer = document.getElementById('countdownContainer');
		const timerDisplay = document.getElementById('timerDisplay');
		const resendTimerGroup = document.getElementById('resendTimerGroup');

		if (!button || !countdownContainer || !timerDisplay || !resendTimerGroup) {
			console.warn("Required elements not found");
			return;
		}

		if (resendTimer)
			clearInterval(resendTimer);

		if (isBlocked) {
			isTimerRunning = true;
			startBlockCountdown(blockedUntil - new Date().getTime());
			return;
		}

		const now = new Date().getTime();
		const cooldownEnd = lastOtpTimestamp + 30000;
		let remaining = Math.max(0, cooldownEnd - now);

		if (remaining > 0) {
			isTimerRunning = true;
			button.disabled = true;
			countdownContainer.classList.remove('forgot-hidden');
			button.classList.remove('forgot-pulse-animation', 'forgot-btn-resend-ready');
			button.classList.add('forgot-btn-resend-timer', 'forgot-opacity-70', 'forgot-cursor-not-allowed');
			resendTimerGroup.classList.add('forgot-buttons-timer-active');
			countdownContainer.classList.add('forgot-countdown-timer-box');

			updateTimerDisplay(timerDisplay, remaining);

			resendTimer = setInterval(function() {
				remaining -= 1000;
				updateTimerDisplay(timerDisplay, remaining);

				if (remaining <= 0) {
					clearInterval(resendTimer);
					isTimerRunning = false;
					enableResendButton();
				}
			}, 1000);
		} else {
			enableResendButton();
		}
	}

	function startBlockCountdown(blockDuration) {
		const button = document.getElementById('resendOtpBtn');
		const countdownContainer = document.getElementById('countdownContainer');
		const timerDisplay = document.getElementById('timerDisplay');
		const resendTimerGroup = document.getElementById('resendTimerGroup');

		if (!button || !countdownContainer || !timerDisplay || !resendTimerGroup)
			return;

		button.disabled = true;
		countdownContainer.classList.remove('forgot-hidden');
		button.classList.remove('forgot-pulse-animation', 'forgot-btn-resend-ready');
		button.classList.add('forgot-btn-resend-timer', 'forgot-opacity-70', 'forgot-cursor-not-allowed');
		resendTimerGroup.classList.add('forgot-buttons-timer-active');
		countdownContainer.classList.add('forgot-countdown-timer-box');

		const minutes = Math.floor(blockDuration / 60000);
		const seconds = Math.floor((blockDuration % 60000) / 1000);
		timerDisplay.textContent = formatTime(minutes) + ":" + formatTime(seconds);

		resendTimer = setInterval(function() {
			blockDuration -= 1000;

			if (blockDuration <= 0) {
				clearInterval(resendTimer);
				isBlocked = false;
				isTimerRunning = false;
				startCountdown();
			} else {
				const minutes = Math.floor(blockDuration / 60000);
				const seconds = Math.floor((blockDuration % 60000) / 1000);
				timerDisplay.textContent = formatTime(minutes) + ":" + formatTime(seconds);
			}
		}, 1000);
	}

	function updateTimerDisplay(timerDisplay, remainingMs) {
		const seconds = Math.ceil(remainingMs / 1000);
		timerDisplay.textContent = formatTime(seconds);
	}

	function enableResendButton() {
		const button = document.getElementById('resendOtpBtn');
		const countdownContainer = document.getElementById('countdownContainer');
		const resendTimerGroup = document.getElementById('resendTimerGroup');

		if (!button || !countdownContainer || !resendTimerGroup)
			return;

		button.disabled = false;
		countdownContainer.classList.add('forgot-hidden');
		button.classList.remove('forgot-opacity-70', 'forgot-cursor-not-allowed', 'forgot-btn-resend-timer');
		button.classList.add('forgot-pulse-animation', 'forgot-btn-resend-ready');
		resendTimerGroup.classList.remove('forgot-buttons-timer-active');
		countdownContainer.classList.remove('forgot-countdown-timer-box');
	}
    
	function checkForOtpPanel() {
		const otpInput = document.getElementById('otp');
		if (otpInput && !isTimerRunning) {
			initializeValues();
			startCountdown();
		}
	}
    
	document.addEventListener('DOMContentLoaded', function() {
		initializeValues();
		checkForOtpPanel();

		const observer = new MutationObserver(function(mutations) {
			mutations.forEach(function(mutation) {
				if (mutation.addedNodes && mutation.addedNodes.length > 0) {
					checkForOtpPanel();
				}
			});
		});

		observer.observe(document.body, {
			childList: true,
			subtree: true
		});
	});

	document.forms[0]?.addEventListener('submit', function(e) {
		const resendBtn = document.getElementById('resendOtpBtn');
		if (resendBtn && resendBtn.disabled) {
			e.preventDefault();
			return false;
		}
		return true;
	});
</script>
<script >

window.addEventListener("beforeunload", function (e) { document.getElementById("forgotPasswordForm").reset();

});
</script>
</head>
<body class="forgot-body">
	<div class="forgot-container animate__animated animate__fadeIn">
		<h:form id="forgotPasswordForm" styleClass="forgot-card"
			prependId="false">
			<h:inputHidden id="lastOtpTimestamp"
				value="#{adminController.lastForgotOtpTimestamp}" />
			<h:inputHidden id="resendAttempts"
				value="#{adminController.forgotResendAttempts}" />
			<h:inputHidden id="otpBlockedUntil"
				value="#{adminController.forgotOtpBlockedUntil}" />

			<div class="forgot-header">
				<h1 class="forgot-title">Forgot Password</h1>
				<p class="forgot-subtitle">Enter your email to receive a
					verification code</p>
			</div>

			<div class="forgot-form-group">
				<h:outputLabel for="usernameOrEmail" value="Username or Email"
					styleClass="forgot-label" />
				<div class="forgot-input-wrapper">
					<div class="forgot-input-icon">
						<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
							viewBox="0 0 24 24" fill="none" stroke="currentColor"
							stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
							<path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
							<circle cx="12" cy="7" r="4"></circle>
						</svg>
					</div>
					<h:inputText id="usernameOrEmail"
						value="#{adminController.usernameOrEmail}"
						disabled="#{adminController.otpSent}"
						styleClass="forgot-input forgot-input-with-icon" />
				</div>
				<h:message for="usernameOrEmail"
					styleClass="forgot-message forgot-message-error" />
			</div>

			<h:commandButton value="Send OTP"
				action="#{adminController.sendOtpForForgotPassword}"
				rendered="#{!adminController.otpSent}"
				styleClass="forgot-btn forgot-btn-primary" />
				
			<h:panelGroup rendered="#{adminController.otpSent}"
				styleClass="animate__animated animate__fadeInUp">
				<div style="margin-top: 1.5rem;">
					<div class="forgot-form-group">
						<h:outputLabel for="otp" value="Verification Code"
							styleClass="forgot-label" />
						<div class="forgot-input-wrapper">
							<div class="forgot-input-icon">
								<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
									viewBox="0 0 24 24" fill="none" stroke="currentColor"
									stroke-width="2" stroke-linecap="round"
									stroke-linejoin="round">
									<rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
									<path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
								</svg>
							</div>
							<h:inputText id="otp" value="#{adminController.otp}"
								styleClass="forgot-input forgot-input-with-icon" />
						</div>
						<p class="forgot-help-text">We've sent a 6-digit code to your
							email</p>
                            <div class="global-message-center">
                                <h:messages globalOnly="true" layout="table"
                                    styleClass="forgot-message forgot-message-success animate__animated animate__fadeIn"
                                    id="global-messages-forgot" />
                            </div>
						<h:message for="otp"
							styleClass="forgot-message forgot-message-error" />
					</div>

					<div class="flex-buttons-and-timer-container">
						<div id="resendTimerGroup">
							<h:commandButton id="resendOtpBtn" value="Resend Code"
								action="#{adminController.sendOtpForForgotPassword}"
								styleClass="forgot-btn forgot-btn-secondary" />
							<div id="countdownContainer"
								class="forgot-hidden">
								<div class="forgot-countdown-container">
									<span class="forgot-countdown-text">Resend in</span> <span
										id="timerDisplay" class="forgot-countdown-timer">30</span>
								</div>
							</div>
						</div>

						<h:commandButton value="Verify OTP"
							action="#{adminController.verifyOtpForForgotPassword}"
							styleClass="forgot-btn forgot-btn-primary forgot-btn-verify" />
					</div>

				</div>
			</h:panelGroup>

			<div class="forgot-footer">
				Remember your password? <h:outputLink value="/HealthSureClient/admin/AuthAdmin/Login.jsf"
					styleClass="forgot-link">
					Sign in here
				</h:outputLink>
			</div>
		</h:form>
	</div>
</body>
	</html>
</f:view>
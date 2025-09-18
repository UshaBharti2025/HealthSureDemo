package com.infinite.jsf.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.infinite.jsf.admin.model.User;


public class ForgotPasswordOtpFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);

		if ("resendOtp".equals(httpRequest.getParameter("action"))) {
			if (session == null) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Session expired");
				return;
			}

			// Get username/email from request parameters
			String usernameOrEmail = httpRequest.getParameter("forgotPasswordForm:usernameOrEmail");
			if (usernameOrEmail == null || usernameOrEmail.trim().isEmpty()) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid request");
				return;
			}

			// Check if blocked for this user
			Long blockedUntil = (Long) session.getAttribute(usernameOrEmail + "forgotOtpBlockedUntil");
			if (blockedUntil != null && blockedUntil > System.currentTimeMillis()) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, 
						"You've exceeded OTP limits. Try again later.");
				return;
			}

			// Check cooldown period
			Long lastOtpTime = (Long) session.getAttribute(usernameOrEmail + "forgotLastOtpTimestamp");
			if (lastOtpTime != null && 
					(System.currentTimeMillis() - lastOtpTime) < 30000) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, 
						"Please wait before requesting another OTP.");
				return;
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
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


public class OtpResendFilter implements Filter {
    
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

            // Get user from session
            User user = (User) session.getAttribute("tempUser");
            if (user == null) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid request");
                return;
            }

            // Updated attribute names without underscores
            Long blockedUntil = (Long) session.getAttribute(user.getEmail() + "otpBlockedUntil");
            if (blockedUntil != null && blockedUntil > System.currentTimeMillis()) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, 
                        "You've exceeded OTP limits. Try again later.");
                return;
            }

            // Updated attribute names without underscores
            Long lastOtpTime = (Long) session.getAttribute(user.getEmail() + "lastOtpTimestamp");
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
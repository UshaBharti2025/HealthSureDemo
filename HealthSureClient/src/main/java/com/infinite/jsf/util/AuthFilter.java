package com.infinite.jsf.util;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String uri = req.getRequestURI();
        Object user = (session != null) ? session.getAttribute("loggedInUser") : null;

        boolean isLoginPage = uri.endsWith("Login.jsf");
        boolean isResource = uri.contains("javax.faces.resource");
        boolean isAdminPath = uri.contains("/admin/");
        boolean isAuthAdminPath = uri.contains("/admin/AuthAdmin/");
        boolean isOutsideAdmin = !isAdminPath;

        if (isResource) {
            // Allow JSF static resources
            chain.doFilter(request, response);
            return;
        }

        // 🚫 Block direct access to any admin or AuthAdmin page (including login) if not logged in
        if ((isAdminPath || isAuthAdminPath) && user == null && !isLoginPage) {
            res.sendRedirect(req.getContextPath() + "/home/Home.jsf");
            return;
        }
        
        // 🔒 Invalidate session if logged-in user tries to access login page from outside /admin/
        if (user != null && isLoginPage && isOutsideAdmin) {
            session.invalidate();
            res.sendRedirect(req.getContextPath() + "/admin/AuthAdmin/Login.jsf");
            return;
        }

        // ✅ Allow access to login page inside admin folder
        if (isLoginPage && isAuthAdminPath) {
            chain.doFilter(request, response);
            return;
        }

        // ✅ Allow access to everything else
        chain.doFilter(request, response);
    }

    @Override public void init(FilterConfig filterConfig) throws ServletException {}
    @Override public void destroy() {}
}
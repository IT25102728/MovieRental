<%@ page contentType="text/html;charset=UTF-8" %>
<%
    // Redirect root URL to login page
    response.sendRedirect(request.getContextPath() + "/login");
%>

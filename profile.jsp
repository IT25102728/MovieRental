<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>My Profile - MovieRental</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
<div class="app-wrapper">
  <jsp:include page="sidebar.jsp"/>
  <div class="main-content">
    <div class="topbar">
      <div class="d-flex align-center gap-10">
        <button class="hamburger" id="hamburger"><i class="fas fa-bars"></i></button>
        <h1><i class="fas fa-user-cog" style="color:var(--accent);margin-right:8px;"></i>My Profile</h1>
      </div>
    </div>
    <div class="page-content">
      <% User u = (User) session.getAttribute("loggedUser"); %>
      <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><i class="fas fa-exclamation-circle"></i> ${error}</div>
      <% } %>
      <% if (request.getAttribute("success") != null) { %>
        <div class="alert alert-success"><i class="fas fa-check-circle"></i> ${success}</div>
      <% } %>
      <div class="card" style="max-width:520px;">
        <div style="display:flex;align-items:center;gap:16px;margin-bottom:24px;">
          <div style="width:64px;height:64px;border-radius:50%;background:var(--accent);display:flex;align-items:center;justify-content:center;font-size:1.6rem;font-weight:700;">
            <%= u != null && u.getName() != null ? u.getName().charAt(0) : "U" %>
          </div>
          <div>
            <div style="font-size:1.1rem;font-weight:700;"><%= u != null ? u.getName() : "" %></div>
            <div style="color:var(--text-muted);font-size:.85rem;"><%= u != null ? u.getRole().toUpperCase() : "" %></div>
          </div>
        </div>
        <form action="${pageContext.request.contextPath}/updateProfile" method="post" data-validate>
          <div class="form-group">
            <label class="form-label">Full Name</label>
            <input type="text" name="name" class="form-control"
                   value="<%= u != null ? u.getName() : "" %>" required>
          </div>
          <div class="form-group">
            <label class="form-label">Email Address</label>
            <input type="email" name="email" class="form-control"
                   value="<%= u != null ? u.getEmail() : "" %>" required>
          </div>
          <div class="form-group">
            <label class="form-label">New Password <span class="text-muted">(leave blank to keep current)</span></label>
            <input type="password" name="password" id="password" class="form-control" placeholder="New password...">
          </div>
          <div class="form-group">
            <label class="form-label">Confirm New Password</label>
            <input type="password" id="confirmPassword" class="form-control" placeholder="Confirm new password...">
          </div>
          <button type="submit" class="btn btn-primary">
            <i class="fas fa-save"></i> Save Changes
          </button>
        </form>
      </div>

      <!-- Danger Zone -->
      <div class="card" style="max-width:520px;border-color:rgba(231,76,60,.3);">
        <div class="card-title"><i class="fas fa-exclamation-triangle" style="color:var(--danger);"></i> Danger Zone</div>
        <p class="text-muted" style="margin-bottom:14px;font-size:.875rem;">
          Deleting your account is permanent and cannot be undone.
        </p>
        <form action="${pageContext.request.contextPath}/deleteUser" method="post">
          <input type="hidden" name="id" value="<%= u != null ? u.getId() : "" %>">
          <button type="submit" class="btn btn-danger btn-delete-confirm">
            <i class="fas fa-user-times"></i> Delete My Account
          </button>
        </form>
      </div>
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
    // Helper: determine first letter of username for avatar
    model.User _u = (model.User) session.getAttribute("loggedUser");
    String _initials = (_u != null && _u.getName() != null && !_u.getName().isEmpty())
        ? String.valueOf(_u.getName().charAt(0)).toUpperCase() : "U";
    boolean _isAdmin = _u != null && "admin".equalsIgnoreCase(_u.getRole());
    String _ctxPath = request.getContextPath();
%>
<nav class="sidebar" id="sidebar">
  <div class="sidebar-logo">
    <i class="fas fa-film"></i>
    <span>MovieRental</span>
  </div>

  <div class="sidebar-nav">
    <div class="nav-label">Main</div>
    <a class="nav-item" href="<%= _ctxPath %>/movies">
      <i class="fas fa-video"></i> Movies
    </a>
    <a class="nav-item" href="<%= _ctxPath %>/searchMovie">
      <i class="fas fa-search"></i> Search
    </a>
    <a class="nav-item" href="<%= _ctxPath %>/rentalHistory">
      <i class="fas fa-history"></i> My Rentals
    </a>
    <a class="nav-item" href="<%= _ctxPath %>/reviews">
      <i class="fas fa-star"></i> Reviews
    </a>
    <a class="nav-item" href="<%= _ctxPath %>/paymentHistory">
      <i class="fas fa-credit-card"></i> Payments
    </a>
    <a class="nav-item" href="<%= _ctxPath %>/updateProfile">
      <i class="fas fa-user-cog"></i> Profile
    </a>

    <% if (_isAdmin) { %>
    <div class="nav-label" style="margin-top:10px;">Admin</div>
    <a class="nav-item" href="<%= _ctxPath %>/adminDashboard">
      <i class="fas fa-tachometer-alt"></i> Dashboard
    </a>
    <a class="nav-item" href="<%= _ctxPath %>/addMovie">
      <i class="fas fa-plus-circle"></i> Add Movie
    </a>
    <a class="nav-item" href="<%= _ctxPath %>/users">
      <i class="fas fa-users"></i> Users
    </a>
    <a class="nav-item" href="<%= _ctxPath %>/manageAdmins">
      <i class="fas fa-user-shield"></i> Admins
    </a>
    <a class="nav-item" href="<%= _ctxPath %>/reports">
      <i class="fas fa-chart-bar"></i> Reports
    </a>
    <% } %>
  </div>

  <div class="sidebar-footer">
    <div class="sidebar-user">
      <div class="avatar"><%= _initials %></div>
      <div>
        <div class="uname"><%= _u != null ? _u.getName() : "Guest" %></div>
        <div class="urole"><%= _isAdmin ? "Administrator" : "Member" %></div>
      </div>
    </div>
    <a class="btn btn-outline w-100" href="<%= _ctxPath %>/logout">
      <i class="fas fa-sign-out-alt"></i> Logout
    </a>
  </div>
</nav>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User, java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Manage Users - MovieRental</title>
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
        <h1><i class="fas fa-users" style="color:var(--accent);margin-right:8px;"></i>Manage Users</h1>
      </div>
    </div>
    <div class="page-content">
      <div class="card">
        <div class="card-title"><i class="fas fa-users"></i> All Users</div>
        <input type="text" id="tableFilter" class="form-control" placeholder="Filter users...">
        <div class="table-wrapper">
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
                <th>Type</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <%
                List<User> users = (List<User>) request.getAttribute("users");
                if (users != null) for (User u : users) {
              %>
              <tr>
                <td><code style="color:var(--accent);font-size:.8rem;"><%= u.getId() %></code></td>
                <td>
                  <div style="display:flex;align-items:center;gap:10px;">
                    <div style="width:32px;height:32px;border-radius:50%;background:var(--accent);display:flex;align-items:center;justify-content:center;font-weight:700;font-size:.8rem;">
                      <%= u.getName() != null && !u.getName().isEmpty() ? u.getName().charAt(0) : "?" %>
                    </div>
                    <%= u.getName() %>
                  </div>
                </td>
                <td><%= u.getEmail() %></td>
                <td>
                  <span class="badge <%= "admin".equalsIgnoreCase(u.getRole()) ? "badge-purple" : "badge-info" %>">
                    <%= u.getRole() %>
                  </span>
                </td>
                <td><%= u.getUserType() %></td>
                <td>
                  <form action="${pageContext.request.contextPath}/deleteUser" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="<%= u.getId() %>">
                    <button type="submit" class="btn btn-danger btn-sm btn-delete-confirm">
                      <i class="fas fa-trash"></i> Delete
                    </button>
                  </form>
                </td>
              </tr>
              <% } %>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>

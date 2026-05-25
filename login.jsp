<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Login - MovieRental</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
<div class="auth-page">
  <div class="auth-box">
    <div class="auth-logo">
      <i class="fas fa-film"></i>
      <h2>MovieRental</h2>
      <p>Sign in to your account</p>
    </div>

    <% if ("true".equals(request.getParameter("registered"))) { %>
      <div class="alert alert-success"><i class="fas fa-check-circle"></i> Registered! Please log in.</div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
      <div class="alert alert-danger"><i class="fas fa-exclamation-circle"></i> ${error}</div>
    <% } %>

    <form action="${pageContext.request.contextPath}/login" method="post" data-validate>
      <div class="form-group">
        <label class="form-label">Email Address</label>
        <input type="email" name="email" class="form-control" placeholder="you@example.com" required>
      </div>
      <div class="form-group">
        <label class="form-label">Password</label>
        <input type="password" name="password" class="form-control" placeholder="••••••••" required>
      </div>
      <button type="submit" class="btn btn-primary w-100" style="justify-content:center;margin-top:6px;">
        <i class="fas fa-sign-in-alt"></i> Sign In
      </button>
    </form>

    <p style="text-align:center;margin-top:20px;font-size:.85rem;color:var(--text-muted);">
      Don't have an account?
      <a href="${pageContext.request.contextPath}/register" style="color:var(--accent);">Register here</a>
    </p>

    <div style="margin-top:20px;padding:14px;background:rgba(108,99,255,.1);border-radius:8px;font-size:.78rem;color:var(--text-muted);">
      <strong style="color:var(--accent);">Demo Credentials</strong><br>
      Admin: admin@movies.com / admin123<br>
      User&nbsp;: user@movies.com / user123
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>

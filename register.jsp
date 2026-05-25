<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Register - MovieRental</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
<div class="auth-page">
  <div class="auth-box">
    <div class="auth-logo">
      <i class="fas fa-film"></i>
      <h2>Create Account</h2>
      <p>Join MovieRental today</p>
    </div>

    <% if (request.getAttribute("error") != null) { %>
      <div class="alert alert-danger"><i class="fas fa-exclamation-circle"></i> ${error}</div>
    <% } %>

    <form action="${pageContext.request.contextPath}/register" method="post" data-validate>
      <div class="form-group">
        <label class="form-label">Full Name</label>
        <input type="text" name="name" class="form-control" placeholder="John Doe" required>
      </div>
      <div class="form-group">
        <label class="form-label">Email Address</label>
        <input type="email" name="email" class="form-control" placeholder="you@example.com" required>
      </div>
      <div class="form-group">
        <label class="form-label">Password</label>
        <input type="password" name="password" id="password" class="form-control" placeholder="Min 6 characters" required>
      </div>
      <div class="form-group">
        <label class="form-label">Confirm Password</label>
        <input type="password" name="confirmPassword" id="confirmPassword" class="form-control" placeholder="Re-enter password" required>
      </div>
      <button type="submit" class="btn btn-primary w-100" style="justify-content:center;margin-top:6px;">
        <i class="fas fa-user-plus"></i> Create Account
      </button>
    </form>

    <p style="text-align:center;margin-top:20px;font-size:.85rem;color:var(--text-muted);">
      Already have an account?
      <a href="${pageContext.request.contextPath}/login" style="color:var(--accent);">Sign in</a>
    </p>
  </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>

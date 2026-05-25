<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Error - MovieRental</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
<div class="auth-page">
  <div class="auth-box" style="text-align:center;max-width:480px;">
    <i class="fas fa-exclamation-triangle" style="font-size:3.5rem;color:var(--danger);margin-bottom:16px;display:block;"></i>
    <h2 style="margin-bottom:10px;">Something went wrong</h2>
    <p class="text-muted" style="margin-bottom:24px;">
      <%= exception != null ? exception.getMessage() : "An unexpected error occurred." %>
    </p>
    <a href="${pageContext.request.contextPath}/movies" class="btn btn-primary">
      <i class="fas fa-home"></i> Go to Movies
    </a>
    &nbsp;
    <a href="javascript:history.back()" class="btn btn-outline">
      <i class="fas fa-arrow-left"></i> Go Back
    </a>
  </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>

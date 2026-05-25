<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>404 Not Found - MovieRental</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
<div class="auth-page">
  <div class="auth-box" style="text-align:center;max-width:460px;">
    <div style="font-size:6rem;font-weight:900;color:var(--accent);line-height:1;margin-bottom:10px;">404</div>
    <i class="fas fa-film" style="font-size:2rem;color:var(--text-muted);margin-bottom:16px;display:block;"></i>
    <h2 style="margin-bottom:10px;">Page Not Found</h2>
    <p class="text-muted" style="margin-bottom:24px;">
      The page you're looking for doesn't exist or has been moved.
    </p>
    <a href="${pageContext.request.contextPath}/movies" class="btn btn-primary">
      <i class="fas fa-home"></i> Back to Movies
    </a>
  </div>
</div>
</body>
</html>

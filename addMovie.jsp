<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Movie" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Add Movie - MovieRental</title>
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
        <h1><i class="fas fa-plus-circle" style="color:var(--accent);margin-right:8px;"></i>Add New Movie</h1>
      </div>
    </div>
    <div class="page-content">
      <div class="card" style="max-width:560px;">
        <% if (request.getAttribute("error") != null) { %>
          <div class="alert alert-danger"><i class="fas fa-exclamation-circle"></i> ${error}</div>
        <% } %>
        <form action="${pageContext.request.contextPath}/addMovie" method="post" data-validate>
          <div class="form-group">
            <label class="form-label">Movie Title</label>
            <input type="text" name="title" class="form-control" placeholder="e.g. The Dark Knight" required>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label class="form-label">Genre</label>
              <select name="genre" class="form-control" required>
                <option value="">-- Select Genre --</option>
                <option value="Action">Action</option>
                <option value="Comedy">Comedy</option>
                <option value="Horror">Horror</option>
                <option value="Drama">Drama</option>
                <option value="Sci-Fi">Sci-Fi</option>
                <option value="Romance">Romance</option>
                <option value="Thriller">Thriller</option>
                <option value="Animation">Animation</option>
              </select>
            </div>
            <div class="form-group">
              <label class="form-label">Rental Price ($)</label>
              <input type="number" name="price" class="form-control" placeholder="e.g. 3.99" step="0.01" min="0" required>
            </div>
          </div>
          <div class="form-group">
            <label class="form-label">Availability</label>
            <select name="available" class="form-control">
              <option value="true">Available</option>
              <option value="false">Not Available</option>
            </select>
          </div>
          <div class="d-flex gap-10 mt-2">
            <button type="submit" class="btn btn-primary">
              <i class="fas fa-save"></i> Add Movie
            </button>
            <a href="${pageContext.request.contextPath}/movies" class="btn btn-outline">
              <i class="fas fa-arrow-left"></i> Cancel
            </a>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>

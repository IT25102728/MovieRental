<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Movie" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Edit Movie - MovieRental</title>
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
        <h1><i class="fas fa-edit" style="color:var(--accent);margin-right:8px;"></i>Edit Movie</h1>
      </div>
    </div>
    <div class="page-content">
      <%
        Movie movie = (Movie) request.getAttribute("movie");
        if (movie == null) { response.sendRedirect(request.getContextPath()+"/movies"); return; }
      %>
      <div class="card" style="max-width:560px;">
        <form action="${pageContext.request.contextPath}/editMovie" method="post" data-validate>
          <input type="hidden" name="id" value="<%= movie.getId() %>">
          <div class="form-group">
            <label class="form-label">Movie Title</label>
            <input type="text" name="title" class="form-control" value="<%= movie.getTitle() %>" required>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label class="form-label">Genre</label>
              <select name="genre" class="form-control">
                <% String[] genres = {"Action","Comedy","Horror","Drama","Sci-Fi","Romance","Thriller","Animation"};
                   for (String g : genres) { %>
                <option value="<%= g %>" <%= g.equalsIgnoreCase(movie.getGenre()) ? "selected" : "" %>><%= g %></option>
                <% } %>
              </select>
            </div>
            <div class="form-group">
              <label class="form-label">Rental Price ($)</label>
              <input type="number" name="price" class="form-control" value="<%= movie.getPrice() %>" step="0.01" min="0" required>
            </div>
          </div>
          <div class="form-group">
            <label class="form-label">Availability</label>
            <select name="available" class="form-control">
              <option value="true"  <%= movie.isAvailable() ? "selected" : "" %>>Available</option>
              <option value="false" <%= !movie.isAvailable() ? "selected" : "" %>>Not Available</option>
            </select>
          </div>
          <div class="d-flex gap-10 mt-2">
            <button type="submit" class="btn btn-primary">
              <i class="fas fa-save"></i> Save Changes
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

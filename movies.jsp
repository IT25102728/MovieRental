<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Movie, model.User, java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Movies - MovieRental</title>
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
        <h1><i class="fas fa-film" style="color:var(--accent);margin-right:8px;"></i>Movies</h1>
      </div>
      <div class="topbar-actions">
        <%
          User u = (User) session.getAttribute("loggedUser");
          if (u != null && "admin".equalsIgnoreCase(u.getRole())) {
        %>
        <a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/addMovie">
          <i class="fas fa-plus"></i> Add Movie
        </a>
        <% } %>
      </div>
    </div>

    <div class="page-content">

      <% if ("true".equals(request.getParameter("added")))   { %><div class="alert alert-success"><i class="fas fa-check-circle"></i> Movie added successfully!</div><% } %>
      <% if ("true".equals(request.getParameter("updated"))) { %><div class="alert alert-success"><i class="fas fa-check-circle"></i> Movie updated successfully!</div><% } %>
      <% if ("true".equals(request.getParameter("deleted"))) { %><div class="alert alert-danger"><i class="fas fa-trash-alt"></i> Movie deleted.</div><% } %>

      <!-- Search bar -->
      <form action="${pageContext.request.contextPath}/searchMovie" method="get" class="search-bar">
        <input type="text" name="q" class="form-control" placeholder="Search movies by title or genre...">
        <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> Search</button>
      </form>

      <!-- Movie grid -->
      <div class="movies-grid">
        <%
          List<Movie> movies = (List<Movie>) request.getAttribute("movies");
          if (movies != null && !movies.isEmpty()) {
            for (Movie m : movies) {
        %>
        <div class="movie-card">
          <div class="movie-poster">
            <i class="fas fa-film"></i>
          </div>
          <div class="movie-info">
            <div class="movie-title"><%= m.getTitle() %></div>
            <div class="movie-meta">
              <span><i class="fas fa-tag"></i> <%= m.getGenre() %></span>
              <span class="badge <%= m.isAvailable() ? "badge-success" : "badge-danger" %>">
                <%= m.isAvailable() ? "Available" : "Rented" %>
              </span>
            </div>
            <div class="movie-price">$<%= String.format("%.2f",m.getPrice()) %></div>
            <div class="movie-actions">
              <% if (m.isAvailable()) { %>
              <a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/rentMovie?movieId=<%= m.getId() %>">
                <i class="fas fa-play"></i> Rent
              </a>
              <% } %>
              <a class="btn btn-outline btn-sm" href="${pageContext.request.contextPath}/reviews?movieId=<%= m.getId() %>">
                <i class="fas fa-star"></i> Reviews
              </a>
              <% if (u != null && "admin".equalsIgnoreCase(u.getRole())) { %>
              <a class="btn btn-warning btn-sm" href="${pageContext.request.contextPath}/editMovie?id=<%= m.getId() %>">
                <i class="fas fa-edit"></i>
              </a>
              <form action="${pageContext.request.contextPath}/deleteMovie" method="post" style="display:inline;">
                <input type="hidden" name="id" value="<%= m.getId() %>">
                <button type="submit" class="btn btn-danger btn-sm btn-delete-confirm">
                  <i class="fas fa-trash"></i>
                </button>
              </form>
              <% } %>
            </div>
          </div>
        </div>
        <% } } else { %>
        <div style="grid-column:1/-1;text-align:center;padding:60px;color:var(--text-muted);">
          <i class="fas fa-film" style="font-size:3rem;margin-bottom:16px;display:block;"></i>
          No movies found.
        </div>
        <% } %>
      </div>

    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Movie, model.User, java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Search Movies - MovieRental</title>
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
        <h1><i class="fas fa-search" style="color:var(--accent);margin-right:8px;"></i>Search Movies</h1>
      </div>
    </div>
    <div class="page-content">
      <form action="${pageContext.request.contextPath}/searchMovie" method="get" class="search-bar">
        <input type="text" name="q" class="form-control" value="${keyword}"
               placeholder="Search by title or genre..." style="font-size:1rem;padding:12px 16px;">
        <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> Search</button>
        <a href="${pageContext.request.contextPath}/movies" class="btn btn-outline">All Movies</a>
      </form>

      <% List<Movie> movies = (List<Movie>) request.getAttribute("movies"); %>
      <% if (request.getParameter("q") != null) { %>
        <p class="text-muted" style="margin-bottom:16px;">
          Found <strong><%= movies != null ? movies.size() : 0 %></strong> result(s) for
          "<strong>${keyword}</strong>"
        </p>
      <% } %>

      <div class="movies-grid">
        <%
          User u = (User) session.getAttribute("loggedUser");
          if (movies != null && !movies.isEmpty()) {
            for (Movie m : movies) {
        %>
        <div class="movie-card">
          <div class="movie-poster"><i class="fas fa-film"></i></div>
          <div class="movie-info">
            <div class="movie-title"><%= m.getTitle() %></div>
            <div class="movie-meta">
              <span><i class="fas fa-tag"></i> <%= m.getGenre() %></span>
              <span class="badge <%= m.isAvailable() ? "badge-success" : "badge-danger" %>">
                <%= m.isAvailable() ? "Available" : "Rented" %>
              </span>
            </div>
            <div class="movie-price">$<%= String.format("%.2f", m.getPrice()) %></div>
            <div class="movie-actions">
              <% if (m.isAvailable()) { %>
              <a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/rentMovie?movieId=<%= m.getId() %>">
                <i class="fas fa-play"></i> Rent
              </a>
              <% } %>
              <a class="btn btn-outline btn-sm" href="${pageContext.request.contextPath}/reviews?movieId=<%= m.getId() %>">
                <i class="fas fa-star"></i>
              </a>
            </div>
          </div>
        </div>
        <% } } else if (request.getParameter("q") != null) { %>
        <div style="grid-column:1/-1;text-align:center;padding:60px;color:var(--text-muted);">
          <i class="fas fa-search" style="font-size:3rem;margin-bottom:16px;display:block;"></i>
          No movies found matching your search.
        </div>
        <% } %>
      </div>
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>

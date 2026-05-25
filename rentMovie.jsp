<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Movie" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Rent Movie - MovieRental</title>
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
        <h1><i class="fas fa-play-circle" style="color:var(--accent);margin-right:8px;"></i>Rent a Movie</h1>
      </div>
    </div>
    <div class="page-content">
      <% Movie movie = (Movie) request.getAttribute("movie"); %>
      <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><i class="fas fa-exclamation-circle"></i> ${error}</div>
      <% } %>
      <% if (movie != null) { %>
      <div class="card" style="max-width:520px;">
        <div style="display:flex;gap:20px;align-items:center;margin-bottom:20px;">
          <div class="movie-poster" style="width:100px;height:100px;border-radius:12px;font-size:2.2rem;flex-shrink:0;">
            <i class="fas fa-film"></i>
          </div>
          <div>
            <h2 style="font-size:1.3rem;margin-bottom:6px;"><%= movie.getTitle() %></h2>
            <span class="badge badge-info"><%= movie.getGenre() %></span>
            <div style="margin-top:10px;font-size:1.4rem;font-weight:700;color:var(--accent);">
              $<%= String.format("%.2f", movie.getPrice()) %> / rental
            </div>
            <div style="margin-top:6px;font-size:.8rem;color:var(--text-muted);">
              <i class="fas fa-clock"></i> 3-day rental period &nbsp;|&nbsp;
              <i class="fas fa-exclamation-triangle"></i> $1.50/day late fee
            </div>
          </div>
        </div>

        <form action="${pageContext.request.contextPath}/rentMovie" method="post" data-validate>
          <input type="hidden" name="movieId" value="<%= movie.getId() %>">
          <div class="form-group">
            <label class="form-label">Payment Method</label>
            <select name="paymentMethod" class="form-control" required>
              <option value="card">Credit / Debit Card</option>
              <option value="cash">Cash</option>
            </select>
          </div>
          <div style="background:rgba(108,99,255,.1);border-radius:8px;padding:14px;margin-bottom:18px;font-size:.85rem;">
            <i class="fas fa-info-circle" style="color:var(--accent);"></i>
            By clicking "Confirm Rental" you agree to pay $<%= String.format("%.2f",movie.getPrice()) %>
            and return the movie within 3 days to avoid late fees.
          </div>
          <div class="d-flex gap-10">
            <button type="submit" class="btn btn-primary">
              <i class="fas fa-check-circle"></i> Confirm Rental
            </button>
            <a href="${pageContext.request.contextPath}/movies" class="btn btn-outline">
              <i class="fas fa-arrow-left"></i> Cancel
            </a>
          </div>
        </form>
      </div>
      <% } else { %>
        <div class="alert alert-danger">Movie not found.</div>
        <a href="${pageContext.request.contextPath}/movies" class="btn btn-outline">Back to Movies</a>
      <% } %>
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>

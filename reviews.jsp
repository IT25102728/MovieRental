<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Review, model.Movie, model.User, service.MovieService, java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Reviews - MovieRental</title>
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
        <h1><i class="fas fa-star" style="color:var(--accent);margin-right:8px;"></i>Movie Reviews</h1>
      </div>
    </div>
    <div class="page-content">
      <% if ("true".equals(request.getParameter("added")))   { %><div class="alert alert-success"><i class="fas fa-check-circle"></i> Review submitted!</div><% } %>
      <% if ("true".equals(request.getParameter("updated"))) { %><div class="alert alert-success"><i class="fas fa-check-circle"></i> Review updated!</div><% } %>
      <% if ("true".equals(request.getParameter("deleted"))) { %><div class="alert alert-danger"><i class="fas fa-trash"></i> Review deleted.</div><% } %>

      <%
        Movie filterMovie = (Movie) request.getAttribute("filterMovie");
        if (filterMovie != null) {
      %>
      <div class="card" style="margin-bottom:16px;padding:14px;">
        Showing reviews for: <strong><%= filterMovie.getTitle() %></strong>
        <a href="${pageContext.request.contextPath}/reviews" style="margin-left:12px;font-size:.85rem;color:var(--accent);">
          View all reviews
        </a>
        <a href="${pageContext.request.contextPath}/addReview?movieId=<%= filterMovie.getId() %>"
           class="btn btn-primary btn-sm" style="margin-left:12px;">
          <i class="fas fa-plus"></i> Add Review
        </a>
      </div>
      <% } %>

      <div class="card">
        <div class="card-title"><i class="fas fa-comments"></i> Reviews</div>
        <input type="text" id="tableFilter" class="form-control" placeholder="Filter reviews...">
        <div class="table-wrapper">
          <table>
            <thead>
              <tr>
                <th>Movie</th>
                <th>User</th>
                <th>Rating</th>
                <th>Comment</th>
                <th>Type</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <%
                List<Review> reviews = (List<Review>) request.getAttribute("reviews");
                MovieService ms = (MovieService) request.getAttribute("movieService");
                User currentUser = (User) session.getAttribute("loggedUser");
                if (reviews != null) {
                  for (Review r : reviews) {
                    Movie mv = ms != null ? ms.findById(r.getMovieId()) : null;
                    String movieTitle = mv != null ? mv.getTitle() : r.getMovieId();
                    StringBuilder stars = new StringBuilder();
                    for (int s = 0; s < 5; s++) stars.append(s < r.getRating() ? "★" : "☆");
              %>
              <tr>
                <td><strong><%= movieTitle %></strong></td>
                <td><%= r.getUserId() %></td>
                <td><span class="stars"><%= stars %></span></td>
                <td style="max-width:250px;"><%= r.getComment() %></td>
                <td><span class="badge badge-info"><%= r.getReviewType() %></span></td>
                <td>
                  <% boolean canEdit = currentUser != null && (currentUser.getId().equals(r.getUserId()) || "admin".equalsIgnoreCase(currentUser.getRole())); %>
                  <% if (canEdit) { %>
                  <a class="btn btn-warning btn-sm"
                     href="${pageContext.request.contextPath}/editReview?reviewId=<%= r.getReviewId() %>">
                    <i class="fas fa-edit"></i>
                  </a>
                  <form action="${pageContext.request.contextPath}/deleteReview" method="post" style="display:inline;">
                    <input type="hidden" name="reviewId" value="<%= r.getReviewId() %>">
                    <button type="submit" class="btn btn-danger btn-sm btn-delete-confirm">
                      <i class="fas fa-trash"></i>
                    </button>
                  </form>
                  <% } %>
                </td>
              </tr>
              <% } } %>
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

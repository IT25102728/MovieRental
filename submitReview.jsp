<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Movie, model.Review" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Submit Review - MovieRental</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <style>
    .star-rating { display:flex; flex-direction:row-reverse; gap:6px; margin-bottom:8px; }
    .star-rating input { display:none; }
    .star-rating label { font-size:2rem; color:#555; cursor:pointer; transition:color .15s; }
    .star-rating input:checked ~ label,
    .star-rating label:hover,
    .star-rating label:hover ~ label { color: var(--warning); }
  </style>
</head>
<body>
<div class="app-wrapper">
  <jsp:include page="sidebar.jsp"/>
  <div class="main-content">
    <div class="topbar">
      <div class="d-flex align-center gap-10">
        <button class="hamburger" id="hamburger"><i class="fas fa-bars"></i></button>
        <h1><i class="fas fa-star" style="color:var(--accent);margin-right:8px;"></i>Submit Review</h1>
      </div>
    </div>
    <div class="page-content">
      <%
        Movie movie   = (Movie)  request.getAttribute("movie");
        Review review = (Review) request.getAttribute("review");
        boolean isEdit = (review != null);
        String actionUrl = isEdit
            ? request.getContextPath() + "/editReview"
            : request.getContextPath() + "/addReview";
      %>
      <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><i class="fas fa-exclamation-circle"></i> ${error}</div>
      <% } %>
      <div class="card" style="max-width:540px;">
        <% if (movie != null) { %>
        <div style="margin-bottom:18px;padding:14px;background:rgba(108,99,255,.1);border-radius:8px;">
          <strong><%= movie.getTitle() %></strong>
          <span class="badge badge-info" style="margin-left:8px;"><%= movie.getGenre() %></span>
        </div>
        <% } %>
        <form action="<%= actionUrl %>" method="post">
          <% if (isEdit) { %>
          <input type="hidden" name="reviewId" value="<%= review.getReviewId() %>">
          <% } else if (movie != null) { %>
          <input type="hidden" name="movieId"  value="<%= movie.getId() %>">
          <% } %>

          <div class="form-group">
            <label class="form-label">Your Rating</label>
            <div class="star-rating">
              <% for (int s = 5; s >= 1; s--) {
                   boolean checked = isEdit && review.getRating() == s; %>
              <input type="radio" id="star<%= s %>" name="rating" value="<%= s %>"
                     <%= checked ? "checked" : "" %> required>
              <label for="star<%= s %>"><i class="fas fa-star"></i></label>
              <% } %>
            </div>
          </div>

          <div class="form-group">
            <label class="form-label">Your Comment</label>
            <textarea name="comment" class="form-control" rows="5"
                      placeholder="Share your thoughts about this movie..."
                      style="resize:vertical;"><%= isEdit ? review.getComment() : "" %></textarea>
          </div>

          <div class="d-flex gap-10">
            <button type="submit" class="btn btn-primary">
              <i class="fas fa-paper-plane"></i> <%= isEdit ? "Update Review" : "Submit Review" %>
            </button>
            <a href="${pageContext.request.contextPath}/reviews" class="btn btn-outline">
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

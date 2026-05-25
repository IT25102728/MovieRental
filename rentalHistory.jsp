<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.RentalTransaction, model.Movie, model.User, service.MovieService, java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Rental History - MovieRental</title>
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
        <h1><i class="fas fa-history" style="color:var(--accent);margin-right:8px;"></i>Rental History</h1>
      </div>
    </div>
    <div class="page-content">
      <% if ("true".equals(request.getParameter("rented")))   { %><div class="alert alert-success"><i class="fas fa-check-circle"></i> Movie rented successfully!</div><% } %>
      <% if ("true".equals(request.getParameter("returned"))) { %><div class="alert alert-success"><i class="fas fa-check-circle"></i> Movie returned successfully!</div><% } %>

      <div class="card">
        <div class="card-title"><i class="fas fa-list"></i> All Rentals</div>
        <input type="text" id="tableFilter" class="form-control" placeholder="Filter rentals...">
        <div class="table-wrapper">
          <table>
            <thead>
              <tr>
                <th>Rental ID</th>
                <th>Movie</th>
                <th>Rented On</th>
                <th>Return Date</th>
                <th>Status</th>
                <th>Fine</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <%
                List<RentalTransaction> rentals = (List<RentalTransaction>) request.getAttribute("rentals");
                MovieService ms = (MovieService) request.getAttribute("movieService");
                User currentUser = (User) session.getAttribute("loggedUser");
                if (rentals != null) {
                  for (int i = rentals.size()-1; i >= 0; i--) {
                    RentalTransaction r = rentals.get(i);
                    Movie mv = ms != null ? ms.findById(r.getMovieId()) : null;
                    String movieTitle = mv != null ? mv.getTitle() : r.getMovieId();
              %>
              <tr>
                <td><code style="color:var(--accent);font-size:.8rem;"><%= r.getRentalId() %></code></td>
                <td><strong><%= movieTitle %></strong></td>
                <td><%= r.getRentDate() %></td>
                <td><%= r.getReturnDate().isEmpty() ? "-" : r.getReturnDate() %></td>
                <td>
                  <span class="badge <%= "active".equals(r.getStatus()) ? "badge-warning" : "badge-success" %>">
                    <%= r.getStatus() %>
                  </span>
                </td>
                <td class="<%= r.getFine() > 0 ? "text-danger" : "text-muted" %>">
                  $<%= String.format("%.2f", r.getFine()) %>
                </td>
                <td>
                  <% if ("active".equals(r.getStatus())) {
                    boolean canReturn = "admin".equalsIgnoreCase(currentUser.getRole()) || r.getUserId().equals(currentUser.getId());
                    if (canReturn) { %>
                  <a class="btn btn-success btn-sm"
                     href="${pageContext.request.contextPath}/returnMovie?rentalId=<%= r.getRentalId() %>">
                    <i class="fas fa-undo"></i> Return
                  </a>
                  <% } } else { %>
                  <span class="text-muted" style="font-size:.8rem;">Completed</span>
                  <% } %>
                  <% if (mv != null) { %>
                  <a class="btn btn-outline btn-sm" href="${pageContext.request.contextPath}/addReview?movieId=<%= mv.getId() %>">
                    <i class="fas fa-star"></i>
                  </a>
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

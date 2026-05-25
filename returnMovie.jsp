<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.RentalTransaction, model.Movie" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Return Movie - MovieRental</title>
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
        <h1><i class="fas fa-undo" style="color:var(--accent);margin-right:8px;"></i>Return Movie</h1>
      </div>
    </div>
    <div class="page-content">
      <%
        RentalTransaction rental = (RentalTransaction) request.getAttribute("rental");
        Movie movie = (Movie) request.getAttribute("movie");
      %>
      <% if (rental != null) { %>
      <div class="card" style="max-width:520px;">
        <div style="margin-bottom:20px;">
          <h3 style="margin-bottom:12px;">Return Confirmation</h3>
          <table style="width:100%;">
            <tr><td class="text-muted" style="padding:6px 0;">Rental ID</td><td><code style="color:var(--accent);"><%= rental.getRentalId() %></code></td></tr>
            <tr><td class="text-muted" style="padding:6px 0;">Movie</td><td><%= movie != null ? movie.getTitle() : rental.getMovieId() %></td></tr>
            <tr><td class="text-muted" style="padding:6px 0;">Rented On</td><td><%= rental.getRentDate() %></td></tr>
            <tr><td class="text-muted" style="padding:6px 0;">Today</td><td><%= util.DateUtil.today() %></td></tr>
            <tr><td class="text-muted" style="padding:6px 0;">Late Fee</td>
              <td>
                <% double fine = util.DateUtil.calculateFine(rental.getRentDate(), rental.getDailyLateFee()); %>
                <span class="<%= fine > 0 ? "text-danger" : "text-success" %>" style="font-weight:700;">
                  $<%= String.format("%.2f", fine) %>
                </span>
              </td>
            </tr>
          </table>
        </div>

        <% if (rental.getDailyLateFee() > 0 && util.DateUtil.calculateFine(rental.getRentDate(), rental.getDailyLateFee()) > 0) { %>
        <div class="alert alert-warning">
          <i class="fas fa-exclamation-triangle"></i>
          A late fee of $<%= String.format("%.2f", util.DateUtil.calculateFine(rental.getRentDate(), rental.getDailyLateFee())) %> will be charged.
        </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/returnMovie" method="post">
          <input type="hidden" name="rentalId" value="<%= rental.getRentalId() %>">
          <div class="d-flex gap-10">
            <button type="submit" class="btn btn-success">
              <i class="fas fa-check"></i> Confirm Return
            </button>
            <a href="${pageContext.request.contextPath}/rentalHistory" class="btn btn-outline">
              <i class="fas fa-arrow-left"></i> Cancel
            </a>
          </div>
        </form>
      </div>
      <% } else { %>
        <div class="alert alert-danger">Rental record not found.</div>
        <a href="${pageContext.request.contextPath}/rentalHistory" class="btn btn-outline">Back to History</a>
      <% } %>
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>

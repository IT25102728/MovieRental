<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Payment, model.RentalTransaction, java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Reports - MovieRental</title>
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
        <h1><i class="fas fa-chart-bar" style="color:var(--accent);margin-right:8px;"></i>System Reports</h1>
      </div>
      <div class="topbar-actions">
        <button onclick="window.print()" class="btn btn-outline btn-sm">
          <i class="fas fa-print"></i> Print Report
        </button>
      </div>
    </div>
    <div class="page-content">

      <!-- Summary Stats -->
      <div class="stats-grid">
        <div class="stat-card">
          <div class="stat-icon yellow"><i class="fas fa-dollar-sign"></i></div>
          <div>
            <div class="stat-label">Total Revenue</div>
            <div class="stat-value" style="font-size:1.5rem;">$${totalRevenue}</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon green"><i class="fas fa-shopping-cart"></i></div>
          <div>
            <div class="stat-label">Total Rentals</div>
            <div class="stat-value">${totalRentals}</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon purple"><i class="fas fa-users"></i></div>
          <div>
            <div class="stat-label">Total Users</div>
            <div class="stat-value">${totalUsers}</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon pink"><i class="fas fa-film"></i></div>
          <div>
            <div class="stat-label">Total Movies</div>
            <div class="stat-value">${totalMovies}</div>
          </div>
        </div>
      </div>

      <!-- Revenue Bar Chart (CSS-only) -->
      <div class="card">
        <div class="card-title"><i class="fas fa-chart-bar"></i> Payment Method Breakdown</div>
        <%
          List<Payment> allPayments = (List<Payment>) request.getAttribute("allPayments");
          int cardCount = 0; int cashCount = 0;
          double cardTotal = 0; double cashTotal = 0;
          if (allPayments != null) {
            for (Payment p : allPayments) {
              if ("card".equalsIgnoreCase(p.getPaymentMethod())) { cardCount++; cardTotal += p.getAmount(); }
              else { cashCount++; cashTotal += p.getAmount(); }
            }
          }
          double grandTotal = cardTotal + cashTotal;
          int cardPct = grandTotal > 0 ? (int)((cardTotal/grandTotal)*100) : 0;
          int cashPct = 100 - cardPct;
        %>
        <div style="margin-bottom:16px;">
          <div style="display:flex;justify-content:space-between;margin-bottom:6px;">
            <span><i class="fas fa-credit-card" style="color:var(--accent);"></i> Card Payments (<%= cardCount %>)</span>
            <span style="color:var(--accent);font-weight:700;">$<%= String.format("%.2f",cardTotal) %></span>
          </div>
          <div style="height:12px;background:rgba(255,255,255,.08);border-radius:6px;overflow:hidden;">
            <div style="height:100%;width:<%= cardPct %>%;background:linear-gradient(90deg,var(--accent),#9c63ff);border-radius:6px;transition:width .5s;"></div>
          </div>
        </div>
        <div>
          <div style="display:flex;justify-content:space-between;margin-bottom:6px;">
            <span><i class="fas fa-money-bill" style="color:var(--success);"></i> Cash Payments (<%= cashCount %>)</span>
            <span style="color:var(--success);font-weight:700;">$<%= String.format("%.2f",cashTotal) %></span>
          </div>
          <div style="height:12px;background:rgba(255,255,255,.08);border-radius:6px;overflow:hidden;">
            <div style="height:100%;width:<%= cashPct %>%;background:linear-gradient(90deg,var(--success),#27ae60);border-radius:6px;transition:width .5s;"></div>
          </div>
        </div>
      </div>

      <!-- Rental Status Report -->
      <div class="card">
        <div class="card-title"><i class="fas fa-list-alt"></i> Rental Status Report</div>
        <%
          List<RentalTransaction> allRentals = (List<RentalTransaction>) request.getAttribute("allRentals");
          int activeCount = 0; int returnedCount = 0; double totalFines = 0;
          if (allRentals != null) {
            for (RentalTransaction r : allRentals) {
              if ("active".equals(r.getStatus())) activeCount++;
              else returnedCount++;
              totalFines += r.getFine();
            }
          }
        %>
        <div style="display:grid;grid-template-columns:repeat(auto-fit,minmax(160px,1fr));gap:16px;margin-bottom:20px;">
          <div style="background:rgba(243,156,18,.1);border:1px solid rgba(243,156,18,.2);border-radius:10px;padding:16px;text-align:center;">
            <div style="font-size:2rem;font-weight:700;color:var(--warning);"><%= activeCount %></div>
            <div style="color:var(--text-muted);font-size:.8rem;">Active Rentals</div>
          </div>
          <div style="background:rgba(46,204,113,.1);border:1px solid rgba(46,204,113,.2);border-radius:10px;padding:16px;text-align:center;">
            <div style="font-size:2rem;font-weight:700;color:var(--success);"><%= returnedCount %></div>
            <div style="color:var(--text-muted);font-size:.8rem;">Returned</div>
          </div>
          <div style="background:rgba(231,76,60,.1);border:1px solid rgba(231,76,60,.2);border-radius:10px;padding:16px;text-align:center;">
            <div style="font-size:2rem;font-weight:700;color:var(--danger);">$<%= String.format("%.2f",totalFines) %></div>
            <div style="color:var(--text-muted);font-size:.8rem;">Total Late Fees</div>
          </div>
        </div>

        <div class="table-wrapper">
          <table>
            <thead>
              <tr><th>Rental ID</th><th>User</th><th>Movie</th><th>Rented</th><th>Status</th><th>Fine</th></tr>
            </thead>
            <tbody>
              <%
                if (allRentals != null) {
                  for (int i = allRentals.size()-1; i >= Math.max(0, allRentals.size()-15); i--) {
                    RentalTransaction r = allRentals.get(i);
              %>
              <tr>
                <td><code style="color:var(--accent);font-size:.75rem;"><%= r.getRentalId() %></code></td>
                <td><%= r.getUserId() %></td>
                <td><%= r.getMovieId() %></td>
                <td><%= r.getRentDate() %></td>
                <td><span class="badge <%= "active".equals(r.getStatus()) ? "badge-warning" : "badge-success" %>"><%= r.getStatus() %></span></td>
                <td class="<%= r.getFine() > 0 ? "text-danger" : "text-muted" %>">$<%= String.format("%.2f",r.getFine()) %></td>
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

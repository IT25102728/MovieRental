<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Payment, java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Payment History - MovieRental</title>
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
        <h1><i class="fas fa-receipt" style="color:var(--accent);margin-right:8px;"></i>Payment History</h1>
      </div>
    </div>
    <div class="page-content">
      <% if ("true".equals(request.getParameter("paid"))) { %>
        <div class="alert alert-success"><i class="fas fa-check-circle"></i> Payment processed successfully!</div>
      <% } %>

      <!-- Summary card -->
      <div class="stats-grid" style="grid-template-columns:repeat(auto-fit,minmax(180px,1fr));">
        <div class="stat-card">
          <div class="stat-icon green"><i class="fas fa-wallet"></i></div>
          <div>
            <div class="stat-label">Total Spent</div>
            <div class="stat-value" style="font-size:1.4rem;">$${totalSpent}</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon purple"><i class="fas fa-list"></i></div>
          <div>
            <div class="stat-label">Transactions</div>
            <%
              List<Payment> payments = (List<Payment>) request.getAttribute("payments");
            %>
            <div class="stat-value"><%= payments != null ? payments.size() : 0 %></div>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-title"><i class="fas fa-credit-card"></i> Transactions</div>
        <input type="text" id="tableFilter" class="form-control" placeholder="Filter payments...">
        <div class="table-wrapper">
          <table>
            <thead>
              <tr>
                <th>Payment ID</th>
                <th>Amount</th>
                <th>Method</th>
                <th>Status</th>
                <th>Date</th>
                <th>Type</th>
              </tr>
            </thead>
            <tbody>
              <%
                if (payments != null) {
                  for (int i = payments.size()-1; i >= 0; i--) {
                    Payment p = payments.get(i);
              %>
              <tr>
                <td><code style="color:var(--accent);font-size:.78rem;"><%= p.getPaymentId() %></code></td>
                <td><strong style="color:var(--success);">$<%= String.format("%.2f", p.getAmount()) %></strong></td>
                <td>
                  <span class="badge <%= "card".equalsIgnoreCase(p.getPaymentMethod()) ? "badge-purple" : "badge-info" %>">
                    <i class="fas <%= "card".equalsIgnoreCase(p.getPaymentMethod()) ? "fa-credit-card" : "fa-money-bill" %>"></i>
                    <%= p.getPaymentMethod() %>
                  </span>
                </td>
                <td>
                  <span class="badge <%= "completed".equalsIgnoreCase(p.getStatus()) ? "badge-success" : "badge-warning" %>">
                    <%= p.getStatus() %>
                  </span>
                </td>
                <td><%= p.getDate() %></td>
                <td><%= p.getPaymentType() %></td>
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

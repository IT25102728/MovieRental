<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Make Payment - MovieRental</title>
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
        <h1><i class="fas fa-credit-card" style="color:var(--accent);margin-right:8px;"></i>Make Payment</h1>
      </div>
    </div>
    <div class="page-content">
      <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><i class="fas fa-exclamation-circle"></i> ${error}</div>
      <% } %>
      <div class="card" style="max-width:460px;">
        <form action="${pageContext.request.contextPath}/payment" method="post" data-validate>
          <div class="form-group">
            <label class="form-label">Amount ($)</label>
            <input type="number" name="amount" class="form-control" placeholder="0.00" step="0.01" min="0.01" required>
          </div>
          <div class="form-group">
            <label class="form-label">Payment Method</label>
            <select name="paymentMethod" class="form-control">
              <option value="card">Credit / Debit Card</option>
              <option value="cash">Cash</option>
            </select>
          </div>
          <button type="submit" class="btn btn-primary">
            <i class="fas fa-check-circle"></i> Process Payment
          </button>
        </form>
      </div>
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>

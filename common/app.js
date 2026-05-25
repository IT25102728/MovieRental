/* ============================================================
   MovieRental - Main JavaScript
   ============================================================ */

/* ── Sidebar Mobile Toggle ── */
const hamburger = document.getElementById('hamburger');
const sidebar   = document.getElementById('sidebar');
if (hamburger && sidebar) {
  hamburger.addEventListener('click', () => sidebar.classList.toggle('open'));
  document.addEventListener('click', (e) => {
    if (!sidebar.contains(e.target) && !hamburger.contains(e.target)) {
      sidebar.classList.remove('open');
    }
  });
}

/* ── Highlight active nav link ── */
(function () {
  const path = window.location.pathname;
  document.querySelectorAll('.nav-item').forEach(link => {
    if (link.getAttribute('href') && path.endsWith(link.getAttribute('href').split('?')[0])) {
      link.classList.add('active');
    }
  });
})();

/* ── Live table filter ── */
const tableFilter = document.getElementById('tableFilter');
if (tableFilter) {
  tableFilter.addEventListener('input', function () {
    const q = this.value.toLowerCase();
    document.querySelectorAll('tbody tr').forEach(row => {
      row.style.display = row.textContent.toLowerCase().includes(q) ? '' : 'none';
    });
  });
}

/* ── Auto-dismiss alerts ── */
document.querySelectorAll('.alert').forEach(alert => {
  setTimeout(() => {
    alert.style.transition = 'opacity .5s';
    alert.style.opacity = '0';
    setTimeout(() => alert.remove(), 500);
  }, 4000);
});

/* ── Delete confirmation ── */
document.querySelectorAll('.btn-delete-confirm').forEach(btn => {
  btn.addEventListener('click', function (e) {
    if (!confirm('Are you sure you want to delete this? This cannot be undone.')) {
      e.preventDefault();
    }
  });
});

/* ── Star rating UI ── */
(function () {
  const ratingInputs = document.querySelectorAll('.star-rating input[type=radio]');
  ratingInputs.forEach(input => {
    input.addEventListener('change', () => {
      document.querySelectorAll('.star-rating label').forEach((lbl, i) => {
        lbl.style.color = i < input.value ? '#f39c12' : '#555';
      });
    });
  });
})();

/* ── Password match check ── */
const pwdConfirm = document.getElementById('confirmPassword');
const pwd        = document.getElementById('password');
if (pwdConfirm && pwd) {
  function checkPwd() {
    if (pwdConfirm.value && pwd.value !== pwdConfirm.value) {
      pwdConfirm.setCustomValidity('Passwords do not match');
      pwdConfirm.style.borderColor = '#e74c3c';
    } else {
      pwdConfirm.setCustomValidity('');
      pwdConfirm.style.borderColor = '';
    }
  }
  pwdConfirm.addEventListener('input', checkPwd);
  pwd.addEventListener('input', checkPwd);
}

/* ── Price formatting ── */
document.querySelectorAll('.price-val').forEach(el => {
  const v = parseFloat(el.textContent);
  if (!isNaN(v)) el.textContent = '$' + v.toFixed(2);
});

/* ── Form validation ── */
document.querySelectorAll('form[data-validate]').forEach(form => {
  form.addEventListener('submit', function (e) {
    let valid = true;
    form.querySelectorAll('[required]').forEach(field => {
      if (!field.value.trim()) {
        field.style.borderColor = '#e74c3c';
        valid = false;
      } else {
        field.style.borderColor = '';
      }
    });
    if (!valid) {
      e.preventDefault();
      showToast('Please fill in all required fields.', 'danger');
    }
  });
});

/* ── Toast Notification ── */
function showToast(message, type = 'info') {
  const toast = document.createElement('div');
  toast.className = `alert alert-${type}`;
  toast.style.cssText = `
    position: fixed; bottom: 24px; right: 24px; z-index: 9999;
    min-width: 260px; box-shadow: 0 8px 32px rgba(0,0,0,.4);
    animation: slideIn .3s ease;
  `;
  toast.innerHTML = `<i class="fas fa-info-circle"></i> ${message}`;
  document.body.appendChild(toast);
  setTimeout(() => {
    toast.style.opacity = '0';
    toast.style.transition = 'opacity .4s';
    setTimeout(() => toast.remove(), 400);
  }, 3500);
}

/* ── Slide in animation ── */
const style = document.createElement('style');
style.textContent = `
  @keyframes slideIn {
    from { transform: translateX(60px); opacity: 0; }
    to   { transform: translateX(0);   opacity: 1; }
  }
`;
document.head.appendChild(style);

/* ── Responsive: close sidebar on nav click (mobile) ── */
document.querySelectorAll('.nav-item').forEach(item => {
  item.addEventListener('click', () => {
    if (sidebar) sidebar.classList.remove('open');
  });
});

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Token Expired</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">

	<%-- Navbar --%>
    <jsp:include page="navbar.jsp" />
    
    <div class="container">
        <div class="row min-vh-100 justify-content-center align-items-center">
            <div class="col-12 col-md-6 col-lg-5">
                <div class="card border-0 shadow-sm">
                    <div class="card-body text-center p-5">
                        <div class="mb-4">
                            <i class="bi bi-clock-history text-danger" style="font-size: 3rem;"></i>
                        </div>
                        <h2 class="card-title mb-3 fw-bold">Link Expired</h2>
                        <p class="card-text text-muted mb-4">
                            The password reset link has expired. Please request a new password reset link to continue.
                        </p>
                        <a href="${pageContext.request.contextPath}/public/HTML/forgetPassword.jsp" 
                           class="btn btn-primary px-4 py-2">
                            <i class="bi bi-arrow-left me-2"></i>Back to Reset Password
                        </a>
                    </div>
                </div>
                <div class="text-center mt-4">
                    <a href="${pageContext.request.contextPath}/public/HTML/login.jsp" 
                       class="text-decoration-none text-muted">
                        <i class="bi bi-house-door me-1"></i>Return to Login
                    </a>
                </div>
            </div>
        </div>
    </div>
	
	<%-- Footer --%>
    <jsp:include page="footer.jsp" />
    
    <!-- Bootstrap 5 JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
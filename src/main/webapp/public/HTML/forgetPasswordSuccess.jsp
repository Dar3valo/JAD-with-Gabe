<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-123" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    <title>Email Sent Successfully</title>
    <style>
        .success-icon {
            font-size: 5rem;
            color: #198754;
        }
        .success-card {
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
        }
        .success-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
        }
        .animation-fade-in {
            animation: fadeIn 1s ease-in;
        }
        @keyframes fadeIn {
            0% { opacity: 0; transform: translateY(20px); }
            100% { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body class="bg-light">

	<%
    { // check permission
    	request.setAttribute("pageAccessLevel", "3");
        RequestDispatcher rd = request.getRequestDispatcher("/checkAccessServlet");
        rd.include(request, response);
        
        Boolean hasAccess = (Boolean) session.getAttribute("accessCheckResult");
        
        if (hasAccess == null || !hasAccess) {
            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
            return;
        }
    }
	%>

    <!-- Navbar -->
    <jsp:include page="navbar.jsp" />

    <!-- Main Content -->
    <div class="container my-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card success-card animation-fade-in">
                    <div class="card-body text-center p-5">
                        <i class="bi bi-check-circle success-icon mb-4"></i>
                        <h2 class="card-title mb-4">Email Sent Successfully!</h2>
                        <p class="card-text text-muted mb-4">
                            Your message has been sent successfully. We'll get back to you as soon as possible.
                        </p>
                        <div class="d-grid gap-2">
                            <a href="login.jsp" class="btn btn-primary mb-2">
                                <i class="bi bi-house-door me-2"></i>Return to Login
                            </a>
                            <a href="contact.jsp" class="btn btn-outline-secondary">
                                <i class="bi bi-envelope me-2"></i>Send Another Message
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Additional Information Card -->
                <div class="card mt-4 success-card animation-fade-in">
                    <div class="card-body p-4">
                        <h5 class="card-title mb-3">What happens next?</h5>
                        <ul class="list-unstyled mb-0">
                            <li class="mb-3">
                                <i class="bi bi-clock text-primary me-2"></i>
                                You'll receive a confirmation email within 5 minutes
                            </li>
                            <li class="mb-3">
                                <i class="bi bi-person-check text-primary me-2"></i>
                                Our team will review your message
                            </li>
                            <li>
                                <i class="bi bi-reply text-primary me-2"></i>
                                We typically respond within 24-48 hours
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <jsp:include page="footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-123" crossorigin="anonymous"></script>
</body>
</html>
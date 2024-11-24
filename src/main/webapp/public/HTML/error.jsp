<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
    <style>
        .error-container {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #f8f9fa;
        }
        .error-card {
            max-width: 500px;
            width: 90%;
            animation: slideIn 0.5s ease-out;
        }
        @keyframes slideIn {
            from {
                transform: translateY(-20px);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }
        .error-icon {
            font-size: 3rem;
            color: #dc3545;
        }
    </style>
</head>
<body>
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
	
	<% 
	String errorMessage = (String) session.getAttribute("errorMessage"); 
	if (errorMessage == null || errorMessage.isEmpty()) {
		errorMessage = "An unexpected error has occurred. Please try again later.";
	}
	%>
	
    <div class="error-container">
        <div class="error-card bg-white p-4 rounded-3 shadow">
	        <div class="text-center mb-4">
	            <i class="bi bi-exclamation-triangle-fill error-icon"></i>
	        </div>
	        <div class="alert alert-danger border-0 mb-4" role="alert">
	            <h4 class="alert-heading text-center mb-3">Oops! Something went wrong</h4>
	            <p class="text-center mb-0"><%= errorMessage %></p>
	        </div>
	        <div class="d-grid gap-2">
	            <button onclick="window.history.back()" class="btn btn-outline-secondary">
	                <i class="bi bi-arrow-left me-2"></i>Go Back
	            </button>
	            <a href="/homePage.jsp" class="btn btn-primary">
	                <i class="bi bi-house-door me-2"></i>Return to Home
	            </a>
	        </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>User Profile | AllClean</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <!-- Custom CSS -->
    <style>
        :root {
            --primary-color: #007bff;
            --secondary-color: #6c757d;
            --background-color: #f8f9fa;
        }

        body {
            background-color: var(--background-color);
            min-height: 100vh;
        }

        .profile-container {
            max-width: 800px;
            margin: 2rem auto;
            padding: 0 1rem;
        }

        .profile-header {
            background: linear-gradient(135deg, var(--primary-color), #0056b3);
            color: white;
            border-radius: 15px;
            padding: 2rem;
            margin-bottom: 2rem;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .profile-photo {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            border: 4px solid white;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            object-fit: cover;
        }

        .profile-card {
            background: white;
            border-radius: 15px;
            padding: 2rem;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .info-label {
            color: var(--secondary-color);
            font-weight: 600;
            margin-bottom: 0.5rem;
        }

        .info-value {
            color: #333;
            font-size: 1.1rem;
            margin-bottom: 1.5rem;
        }

        .role-badge {
            padding: 0.5rem 1rem;
            border-radius: 20px;
            font-weight: 500;
            text-transform: capitalize;
        }

        .role-admin {
            background-color: #dc3545;
            color: white;
        }

        .role-client {
            background-color: #28a745;
            color: white;
        }

        .divider {
            height: 1px;
            background-color: #dee2e6;
            margin: 1.5rem 0;
        }
    </style>
</head>
<body>
	<%--Navbar --%>
	<jsp:include page="navbar.jsp" />
    <%
    { // check permission
        request.setAttribute("pageAccessLevel", "2");
        RequestDispatcher rd = request.getRequestDispatcher("/checkAccessServlet");
        rd.include(request, response);
        
        Boolean hasAccess = (Boolean) session.getAttribute("accessCheckResult");
        
        if (hasAccess == null || !hasAccess) {
            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
            return;
        }
    }
    %>

    <div class="profile-container">
        <%
        User user = (User) session.getAttribute("currentUser");
        
        if (user == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/GetUserInformationById");
            dispatcher.forward(request, response);
            return;
        } else {
        %>
            <div class="profile-header text-center">
                <img src="<%= user.getProfile_photo_url() %>" alt="Profile Photo" class="profile-photo mb-3"
                     onerror="this.src='https://via.placeholder.com/150'">
                <h1 class="mb-2"><%= user.getName() %></h1>
                <span class="role-badge <%= user.getRole_id() == 1 ? "role-admin" : "role-client" %>">
                    <%= user.getRole_id() == 1 ? "Administrator" : "Client" %>
                </span>
            </div>

            <div class="profile-card">
                <h2 class="mb-4">Profile Information</h2>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="mb-4">
                            <div class="info-label">
                                <i class="bi bi-envelope-fill me-2"></i>Email
                            </div>
                            <div class="info-value"><%= user.getEmail() %></div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="mb-4">
                            <div class="info-label">
                                <i class="bi bi-person-fill me-2"></i>Gender
                            </div>
                            <div class="info-value text-capitalize"><%= user.getGender() %></div>
                        </div>
                    </div>
                </div>

                <div class="divider"></div>

                <div class="d-flex justify-content-between align-items-center">
					<button class="btn btn-outline-primary">
					    <i class="bi bi-key-fill me-2"></i>Change Password
					</button>
					<button class="btn btn-primary me-2">
	                   <i class="bi bi-pencil-fill me-2"></i>Edit Profile
					</button>
                </div>
            </div>
        <% } %>
    </div>
	<%-- Footer --%>
	<jsp:include page="footer.jsp" />

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
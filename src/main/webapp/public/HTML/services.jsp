<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Service"%>
<%@ page import="model.ServiceCategory"%>
<%@ page import="model.ServiceDAO"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AllClean Services</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <!-- Custom CSS -->
    <style>
        :root {
            --primary-color: #007bff;
            --secondary-color: #6c757d;
        }

        body {
            background-color: #f8f9fa;
        }

        .page-header {
            background: linear-gradient(135deg, var(--primary-color), #0056b3);
            color: white;
            padding: 3rem 0;
            margin-bottom: 2rem;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .card {
            border: none;
            border-radius: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }

        .card:hover {
            transform: translateY(-5px);
        }

        .card-body {
            padding: 1.5rem;
        }

        .card-title {
            color: var(--primary-color);
            font-weight: 600;
            margin-bottom: 1rem;
        }

        .category-card {
            background: white;
            height: 100%;
        }

        .service-card {
            background: white;
            height: 100%;
        }

        .price-tag {
            background-color: var(--primary-color);
            color: white;
            padding: 0.5rem 1rem;
            border-radius: 20px;
            display: inline-block;
            font-weight: 500;
        }

        .section-title {
            position: relative;
            padding-bottom: 1rem;
            margin-bottom: 2rem;
        }

        .section-title::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);
            width: 100px;
            height: 3px;
            background-color: var(--primary-color);
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

    <!-- Navbar -->
    <jsp:include page="navbar.jsp" />

    <!-- Categories Section -->
    <header class="page-header">
        <div class="container">
            <h1 class="text-center mb-0">Our Services</h1>
        </div>
    </header>

    <section class="py-5">
        <div class="container">
            <h2 class="section-title text-center">Service Categories</h2>
            <div class="row g-4">
                <%
                List<ServiceCategory> categories = (List<ServiceCategory>) session.getAttribute("allCategories");
                
                if (categories == null) {
                    RequestDispatcher rd = request.getRequestDispatcher("/GetAllCategoriesServlet");
                    rd.include(request, response);
                } else if (categories.isEmpty()) {
                %>
                <div class="col-12">
                    <div class="alert alert-warning text-center">
                        Unfortunately, no categories were found. This is probably a bug. Please contact an Admin.
                    </div>
                </div>
                <% } else {
                    for (ServiceCategory category : categories) { %>
                <div class="col-md-4">
                    <div class="card category-card">
                        <div class="card-body">
                            <h5 class="card-title"><%= category.getName() %></h5>
                            <p class="card-text text-muted"><%= category.getDescription() %></p>
                        </div>
                    </div>
                </div>
                <% }} %>
            </div>
        </div>
    </section>

    <!-- Services Section -->
    <section class="py-5 bg-light">
        <div class="container">
            <h2 class="section-title text-center">Available Services</h2>
            <div class="row g-4">
                <%
                List<Service> services = (List<Service>) session.getAttribute("allServices");
                
                if (services == null) {
                    response.sendRedirect(request.getContextPath() + "/GetAllServicesServlet");
                    return;
                } else if (services.isEmpty()) { %>
                <div class="col-12">
                    <div class="alert alert-info text-center">
                        No services available at the moment.
                    </div>
                </div>
                <% } else {
                    for (Service service : services) { %>
                <div class="col-md-4">
                    <div class="card service-card">
                        <div class="card-body">
                            <h5 class="card-title"><%= service.getName() %></h5>
                            <p class="card-text mb-3"><%= service.getDescription() %></p>
                            <div class="price-tag">
                                $<%= String.format("%.2f", service.getPrice()) %>
                            </div>
                        </div>
                    </div>
                </div>
                <%
                    }
                }
                %>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <jsp:include page="footer.jsp" />

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
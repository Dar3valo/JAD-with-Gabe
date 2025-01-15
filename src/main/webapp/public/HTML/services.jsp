<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="ServiceModel.Service"%>
<%@ page import="ServiceCategoryModel.ServiceCategory"%>
<%@ page import="ServiceModel.ServiceDAO"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AllClean Services</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="../CSS/service.css" />
</head>
<body>
    <%
    { // Check permission
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

    <div class="container mt-4">
        <h1 class="text-center mb-4">Our Services</h1>
        
        <!-- Categories Section -->
        <section class="mb-5">
            <h2 class="text-center text-secondary mb-4">Service Categories</h2>
            <div class="row row-cols-1 row-cols-md-3 g-4">
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
                <div class="col">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title text-dark"><%= category.getName() %></h5>
                            <p class="card-text text-muted"><%= category.getDescription() %></p>
                        </div>
                    </div>
                </div>
                <% }} %>
            </div>
        </section>

        <!-- Services Section -->
        <section class="mb-5">
            <h2 class="text-center text-secondary mb-4">Available Services</h2>
            <div class="row row-cols-1 row-cols-md-3 g-4">
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
                <div class="col">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title"><%= service.getName() %></h5>
                            <p class="card-text mb-3"><%= service.getDescription() %></p>
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="price-tag">
                                    $<%= String.format("%.2f", service.getPrice()) %>
                                </div>
                                <a href="#" class="btn btn-dark btn-sm">View Details</a>
                            </div>
                        </div>
                    </div>
                </div>
                <%
                    }
                }
                %>
            </div>
        </section>
    </div>

    <!-- Footer -->
    <jsp:include page="footer.jsp" />

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="Models.Service.Service"%>
<%@ page import="Models.ServiceCategory.ServiceCategory"%>
<%@ page import="Models.Service.ServiceDAO"%>
<%@ page import="java.util.stream.Collectors" %>
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
    <style>
        :root {
            --dark-bg: #1a1a1a;
            --darker-bg: #121212;
            --card-bg: #2d2d2d;
            --text-primary: #ffffff;
            --text-secondary: #b3b3b3;
            --accent-color: #4CAF50;
            --hover-color: #45a049;
        }

        body {
            background-color: var(--dark-bg);
            color: var(--text-primary);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        h1, h2 {
            color: black;
            font-weight: 600;
        }

        .card {
            background-color: var(--card-bg);
            border: none;
            border-radius: 15px;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            overflow: hidden;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
        }

        .card-title {
            color: black;
            font-size: 1.25rem;
            margin-bottom: 1rem;
        }

        .card-text {
            color: var(--text-secondary);
        }

        .table {
            background-color: var(--card-bg);
            color: var(--text-primary);
        }

        .table thead th {
            background-color: var(--darker-bg);
            color: var(--text-primary);
        }

        .table tbody tr:hover {
            background-color: rgba(76, 175, 80, 0.1);
        }

        .price-column {
            font-weight: 600;
            color: var(--text-primary);
        }

        .alert {
            background-color: var(--card-bg);
            border: 1px solid var(--accent-color);
            color: var(--text-primary);
        }

        .search-container {
            background: var(--card-bg);
            padding: 1.5rem;
            border-radius: 15px;
            margin-bottom: 2rem;
        }

        #searchInput {
            background-color: var(--darker-bg);
            border: 2px solid var(--card-bg);
            color: var(--text-primary);
            padding: 0.75rem 1rem;
            border-radius: 10px;
            transition: all 0.3s ease;
        }

        #searchInput:focus {
            border-color: var(--accent-color);
            box-shadow: 0 0 0 0.2rem rgba(76, 175, 80, 0.25);
        }

        #searchInput::placeholder {
            color: var(--text-secondary);
        }

        .animate-fade-in {
            animation: fadeInUp 0.5s ease forwards;
        }

        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
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

    <div class="container mt-5">
        <h1 class="text-center mb-5">Our Services</h1>

        <!-- Categories Section -->
        <section class="mb-5">
            <h2 class="text-center mb-4">Service Categories</h2>
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <%
                List<ServiceCategory> categories = (List<ServiceCategory>) session.getAttribute("allCategories");
                
                if (categories == null) {
                    RequestDispatcher rd = request.getRequestDispatcher("/GetAllCategoriesServlet");
                    rd.include(request, response);
                } else if (categories.isEmpty()) {
                %>
                <div class="col-12">
                    <div class="alert text-center">
                        <i class="bi bi-exclamation-triangle me-2"></i>
                        Unfortunately, no categories were found. This is probably a bug. Please contact an Admin.
                    </div>
                </div>
                <% } else {
                    int delay = 0;
                    for (ServiceCategory category : categories) { %>
                <div class="col animate-fade-in" style="animation-delay: <%= delay %>ms;">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">
                                <i class="bi bi-grid me-2"></i>
                                <%= category.getName() %>
                            </h5>
                            <p class="card-text"><%= category.getDescription() %></p>
                        </div>
                    </div>
                </div>
                <% delay += 100; }} %>
            </div>
        </section>

        <!-- Services Section -->
        <section class="mb-5">
            <h2 class="text-center mb-4">Available Services</h2>

            <!-- Search Form -->
            <div class="search-container">
                <div class="input-group">
                    <span class="input-group-text bg-transparent border-0">
                        <i class="bi bi-search text-secondary"></i>
                    </span>
                    <input type="text" id="searchInput" class="form-control" placeholder="Search by service name..." onkeyup="performSearch()">
                </div>
            </div>

            <div class="table-responsive">
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">
                                <i class="bi bi-tools me-2"></i>Service Name
                            </th>
                            <th scope="col">
                                <i class="bi bi-info-circle me-2"></i>Description
                            </th>
                            <th scope="col">
                                <i class="bi bi-currency-dollar me-2"></i>Price
                            </th>
                        </tr>
                    </thead>
                    <tbody id="serviceTable">
                        <%
                        List<Service> services = (List<Service>) session.getAttribute("allServices");
                        String searchKeyword = request.getParameter("search");
                        
                        if (services == null) {
                            response.sendRedirect(request.getContextPath() + "/GetAllServicesServlet");
                            return;
                        } else if (services.isEmpty()) { %>
                        <tr>
                            <td colspan="3" class="text-center">
                                <i class="bi bi-emoji-frown me-2"></i>
                                No services available at the moment.
                            </td>
                        </tr>
                        <% } else {
                            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                                services = services.stream()
                                                   .filter(service -> service.getName().toLowerCase().contains(searchKeyword.toLowerCase()))
                                                   .collect(Collectors.toList());
                            }

                            for (Service service : services) { %>
                        <tr>
                            <td><strong style="color: var(--text-primary);"><%= service.getName() %></strong></td>
                            <td style="color: var(--text-primary);"><%= service.getDescription() %></td>
                            <td class="price-column">$<%= String.format("%.2f", service.getPrice()) %></td>
                        </tr>
                        <% }
                        } %>
                    </tbody>
                </table>
            </div>
        </section>
    </div>

    <!-- Footer -->
    <jsp:include page="footer.jsp" />

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function performSearch() {
            var input = document.getElementById('searchInput');
            var filter = input.value.toLowerCase();
            var table = document.getElementById('serviceTable');
            var rows = table.getElementsByTagName('tr');
            
            for (var i = 0; i < rows.length; i++) {
                var td = rows[i].getElementsByTagName('td')[0];
                if (td) {
                    var textValue = td.textContent || td.innerText;
                    if (textValue.toLowerCase().indexOf(filter) > -1) {
                        rows[i].style.display = "";
                    } else {
                        rows[i].style.display = "none";
                    }
                }
            }
        }

        // Add smooth fade-in animation when page loads
        document.addEventListener('DOMContentLoaded', function() {
            document.querySelectorAll('.animate-fade-in').forEach(element => {
                element.style.opacity = '0';
                element.style.animation = 'fadeInUp 0.5s ease forwards';
            });
        });
    </script>
</body>
</html>
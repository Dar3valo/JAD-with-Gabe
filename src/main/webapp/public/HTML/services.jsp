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

            <!-- Search Form -->
            <div class="mb-3">
                <input type="text" id="searchInput" class="form-control" placeholder="Search by service name..." onkeyup="performSearch()">
            </div>

            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th scope="col">Service Name</th>
                        <th scope="col">Description</th>
                        <th scope="col">Price</th>
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
                        <td colspan="3" class="text-center">No services available at the moment.</td>
                    </tr>
                    <% } else {
                        if (searchKeyword != null && !searchKeyword.isEmpty()) {
                            services = services.stream()
                                               .filter(service -> service.getName().toLowerCase().contains(searchKeyword.toLowerCase()))
                                               .collect(Collectors.toList());
                        }

                        for (Service service : services) { %>
                    <tr>
                        <td><%= service.getName() %></td>
                        <td><%= service.getDescription() %></td>
                        <td>$<%= String.format("%.2f", service.getPrice()) %></td>
                    </tr>
                    <%
                        }
                    }
                    %>
                </tbody>
            </table>
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
    </script>
</body>
</html>

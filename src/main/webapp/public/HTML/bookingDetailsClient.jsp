<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Models.Booking.Booking" %>
<!DOCTYPE html>
<html>
<head>
    <title>Booking Details</title>
    <!-- Include Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
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
    <!-- Include Navbar -->
    <jsp:include page="navbar.jsp" />
    
    <div class="container py-5">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0"><i class="bi bi-info-circle-fill ms-2"></i> Booking Details</h5>
            </div>
            <div class="card-body">
                <% Booking booking = (Booking)session.getAttribute("bookingDetails"); %>
                
                <% if (booking != null) { %>
                <div class="row">
                    <!-- Booking ID -->
                    <div class="col-md-6 mb-3">
                        <strong><i class="bi bi-hash ms-2"></i> Booking ID:</strong>
                        <p>#<%= booking.getBooking_id() %></p>
                    </div>

                    <!-- Booking Date -->
                    <div class="col-md-6 mb-3">
                        <strong><i class="bi bi-calendar ms-2"></i> Booking Date:</strong>
                        <p><%= booking.getBooking_date() %></p>
                    </div>

                    <!-- Main Address -->
                    <div class="col-md-6 mb-3">
                        <strong><i class="bi bi-geo-alt-fill ms-2"></i> Address:</strong>
                        <p><%= booking.getMain_address() %></p>
                    </div>

                    <!-- Service Name -->
                    <div class="col-md-6 mb-3">
                        <strong><i class="bi bi-briefcase-fill ms-2"></i> Service:</strong>
                        <p><%= booking.getServiceName() %></p>
                    </div>

                    <!-- Booking Period -->
                    <div class="col-md-6 mb-3">
                        <strong><i class="bi bi-clock-fill ms-2"></i> Booking Period:</strong>
                        <p><%= booking.getBookingPeriod() %></p>
                    </div>

                    <!-- Status -->
                    <div class="col-md-6 mb-3">
                        <strong><i class="bi bi-check-circle-fill ms-2"></i> Status:</strong>
                        <p><%= booking.getStatusName() %></p>
                    </div>
                </div>
                
                <!-- Back to List Button -->
                <div class="text-center mt-3">
                    <a href="bookingStatusClient.jsp" class="btn btn-secondary">
                        <i class="bi bi-arrow-left-circle"></i> Back to List
                    </a>
                </div>
                
                <% } else { %>
                <!-- Error message if no booking details are available -->
                <div class="alert alert-danger text-center">
                    <strong>Error:</strong> Booking details could not be loaded.
                </div>
                <% } %>
            </div>
        </div>
    </div>
    
    <!-- Include Footer -->
    <jsp:include page="footer.jsp" />

    <!-- Include Bootstrap 5 JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Models.Booking.Booking" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Bookings</title>
    
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Font Awesome for additional icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    
    <!-- Custom CSS -->
    <style>
        body {
            background-color: #f4f6f9;
        }
        .table-container {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin-top: 30px;
        }
        .status-badge {
            font-size: 0.9em;
            padding: 0.4em 0.6em;
        }
        .booking-header {
            color: #343a40;
            margin-bottom: 20px;
        }
    </style>
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

	<%
	// Forward to BookingStatusServlet to load the bookings
	RequestDispatcher bookingDispatcher = request.getRequestDispatcher("/BookingStatusServlet");
	bookingDispatcher.include(request, response);
	%>

	<%-- Include Navigation Bar --%>
    <jsp:include page="navbar.jsp" />

    <div class="container">
        <div class="table-container">
            <h1 class="text-center booking-header">
                <i class="fas fa-calendar-alt me-2"></i>My Bookings
            </h1>

            <%
            // Retrieve bookings from session
            List<Booking> bookings = (List<Booking>) session.getAttribute("bookings");
            
            // Date formatting
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            %>

            <% if (bookings != null && !bookings.isEmpty()) { %>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>Booking ID</th>
                                <th>Booking Date</th>
                                <th>Address</th>
                                <th>Postal Code</th>
                                <th>Service</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Booking booking : bookings) { %>
                                <tr>
                                    <td><%= booking.getBooking_id() %></td>
                                    <td><%= dateFormat.format(booking.getBooking_date()) %></td>
                                    <td><%= booking.getMain_address() %></td>
                                    <td><%= booking.getPostal_code() %></td>
                                    <td>
                                        <%-- You might want to add a method to get service name from ID --%>
                                        Service <%= booking.getService_id() %>
                                    </td>
                                    <td>
                                        <span class="badge status-badge 
                                            <%= booking.getStatusDescription().equalsIgnoreCase("Pending") ? "bg-warning" : 
                                                booking.getStatusDescription().equalsIgnoreCase("Confirmed") ? "bg-success" : 
                                                booking.getStatusDescription().equalsIgnoreCase("Cancelled") ? "bg-danger" : 
                                                "bg-secondary" %>">
                                            <%= booking.getStatusDescription() %>
                                        </span>
                                    </td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <button type="button" 
                                                    class="btn btn-sm btn-info view-details"
                                                    data-bs-toggle="modal" 
                                                    data-bs-target="#bookingDetailsModal"
                                                    data-booking-id="<%= booking.getBooking_id() %>">
                                                <i class="fas fa-eye me-1"></i>View
                                            </button>
                                            <% if (booking.getStatusDescription().equalsIgnoreCase("Pending")) { %>
                                                <button type="button" 
                                                        class="btn btn-sm btn-danger cancel-booking"
                                                        data-booking-id="<%= booking.getBooking_id() %>">
                                                    <i class="fas fa-times me-1"></i>Cancel
                                                </button>
                                            <% } %>
                                        </div>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            <% } else { %>
                <div class="alert alert-info text-center" role="alert">
                    <i class="fas fa-info-circle me-2"></i>
                    You have no bookings at the moment. 
                    <a href="booking.jsp" class="alert-link">Create a new booking</a>
                </div>
            <% } %>
        </div>
    </div>

    <!-- Booking Details Modal -->
    <div class="modal fade" id="bookingDetailsModal" tabindex="-1" aria-labelledby="bookingDetailsModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="bookingDetailsModalLabel">
                        <i class="fas fa-clipboard-list me-2"></i>Booking Details
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" id="bookingDetailsContent">
                    <!-- Dynamic content will be inserted here by JavaScript -->
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <%-- Include Footer --%>
    <jsp:include page="footer.jsp" />

    <!-- Bootstrap 5 JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Custom JavaScript -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // View Details Modal Population
            const viewDetailsButtons = document.querySelectorAll('.view-details');
            viewDetailsButtons.forEach(button => {
                button.addEventListener('click', function() {
                    const bookingId = this.getAttribute('data-booking-id');
                    const row = this.closest('tr');
                    const modalContent = document.getElementById('bookingDetailsContent');
                    
                    modalContent.innerHTML = `
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <strong><i class="fas fa-hashtag me-2"></i>Booking ID:</strong>
                                <p>${bookingId}</p>
                            </div>
                            <div class="col-md-6 mb-3">
                                <strong><i class="fas fa-calendar me-2"></i>Booking Date:</strong>
                                <p>${row.querySelector('td:nth-child(2)').textContent}</p>
                            </div>
                            <div class="col-12 mb-3">
                                <strong><i class="fas fa-map-marker-alt me-2"></i>Address:</strong>
                                <p>${row.querySelector('td:nth-child(3)').textContent}</p>
                            </div>
                            <div class="col-md-6 mb-3">
                                <strong><i class="fas fa-envelope me-2"></i>Postal Code:</strong>
                                <p>${row.querySelector('td:nth-child(4)').textContent}</p>
                            </div>
                            <div class="col-md-6 mb-3">
                                <strong><i class="fas fa-tag me-2"></i>Service:</strong>
                                <p>${row.querySelector('td:nth-child(5)').textContent}</p>
                            </div>
                            <div class="col-md-6 mb-3">
                                <strong><i class="fas fa-info-circle me-2"></i>Status:</strong>
                                <p>${row.querySelector('td:nth-child(6) .badge').textContent}</p>
                            </div>
                        </div>
                    `;
                });
            });

            // Booking Cancellation
            const cancelButtons = document.querySelectorAll('.cancel-booking');
            cancelButtons.forEach(button => {
                button.addEventListener('click', function() {
                    const bookingId = this.getAttribute('data-booking-id');
                    
                    // Confirm cancellation
                    if (confirm('Are you sure you want to cancel this booking?')) {
                        // AJAX call to cancel booking
                        fetch('BookingStatusServlet', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded',
                            },
                            body: `bookingId=${bookingId}&statusId=3` // Assuming 3 is the cancellation status
                        })
                        .then(response => response.text())
                        .then(result => {
                            alert(result);
                            // Reload the page to reflect changes
                            window.location.reload();
                        })
                        .catch(error => {
                            console.error('Error:', error);
                            alert('Failed to cancel booking');
                        });
                    }
                });
            });
        });
    </script>
</body>
</html>
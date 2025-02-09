<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Models.Booking.Booking" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Bookings</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    
    <style>
        .booking-table {
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .status-badge {
            padding: 0.5em 1em;
            border-radius: 20px;
            font-weight: 500;
        }
        .booking-row {
            transition: background-color 0.2s;
        }
        .booking-row:hover {
            background-color: #f8f9fa;
        }
    </style>
</head>
<body class="bg-light">
    <%-- Permission Check --%>
    <%
    {
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

    <%-- Include Navigation Bar --%>
    <jsp:include page="navbar.jsp" />

    <div class="container py-5">
        <div class="row mb-4">
            <div class="col">
                <h2 class="text-center">My Bookings</h2>
            </div>
        </div>

        <%
        List<Booking> bookings = (List<Booking>) session.getAttribute("bookings");
        if (bookings == null) {
            response.sendRedirect(request.getContextPath() + "/BookingStatusServlet");
        } else if (bookings.isEmpty()) {
        %>
            <div class="text-center py-5">
                <i class="fas fa-calendar-times fa-3x mb-3 text-muted"></i>
                <p class="lead mb-4">You don't have any bookings yet</p>
                <a href="booking.jsp" class="btn btn-primary">
                    <i class="fas fa-plus me-2"></i>Make a New Booking
                </a>
            </div>
        <% } else { %>
            <div class="table-responsive booking-table">
                <table class="table table-hover mb-0">
                    <thead class="table-dark">
                        <tr>
                            <th>Booking ID</th>
                            <th>Date</th>
                            <th>Address</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                        for (Booking booking : bookings) { 
                        %>
                            <tr class="booking-row">
                                <td>#<%= booking.getBooking_id() %></td>
                                <td><%= dateFormat.format(booking.getBooking_date()) %></td>
                                <td><%= booking.getMain_address() %></td>
                                <td>
                                    <span class="status-badge <%= 
                                        booking.getStatusDescription().equalsIgnoreCase("Pending") ? "bg-warning" :
                                        booking.getStatusDescription().equalsIgnoreCase("Confirmed") ? "bg-success" :
                                        booking.getStatusDescription().equalsIgnoreCase("Cancelled") ? "bg-danger" :
                                        "bg-secondary" %>">
                                        <%= booking.getStatusDescription() %>
                                    </span>
                                </td>
                                <td>
                                    <div class="btn-group">
                                        <button type="button" 
                                                class="btn btn-sm btn-outline-primary view-details"
                                                data-bs-toggle="modal" 
                                                data-bs-target="#bookingDetailsModal"
                                                data-booking-id="<%= booking.getBooking_id() %>">
                                            <i class="fas fa-eye"></i>
                                        </button>
                                        
                                        <% if (booking.getStatusDescription().equalsIgnoreCase("Pending")) { %>
                                            <button type="button" 
                                                    class="btn btn-sm btn-outline-danger cancel-booking"
                                                    data-booking-id="<%= booking.getBooking_id() %>">
                                                <i class="fas fa-times"></i>
                                            </button>
                                        <% } %>
                                    </div>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        <% } %>
    </div>

    <!-- Booking Details Modal -->
    <div class="modal fade" id="bookingDetailsModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">
                        <i class="fas fa-info-circle me-2"></i>Booking Details
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body" id="bookingDetailsContent">
                    <!-- Content will be populated by JavaScript -->
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap & Font Awesome -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Custom JavaScript -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // View Details
            document.querySelectorAll('.view-details').forEach(button => {
                button.addEventListener('click', function() {
                    const row = this.closest('tr');
                    const modalContent = document.getElementById('bookingDetailsContent');
                    
                    modalContent.innerHTML = `
                        <div class="p-3">
                            <div class="mb-3">
                                <strong><i class="fas fa-hashtag me-2"></i>Booking ID:</strong>
                                <p class="mb-0">${row.cells[0].textContent}</p>
                            </div>
                            <div class="mb-3">
                                <strong><i class="fas fa-calendar me-2"></i>Date:</strong>
                                <p class="mb-0">${row.cells[1].textContent}</p>
                            </div>
                            <div class="mb-3">
                                <strong><i class="fas fa-map-marker-alt me-2"></i>Address:</strong>
                                <p class="mb-0">${row.cells[2].textContent}</p>
                            </div>
                            <div class="mb-3">
                                <strong><i class="fas fa-info-circle me-2"></i>Status:</strong>
                                <p class="mb-0">${row.cells[3].textContent.trim()}</p>
                            </div>
                        </div>
                    `;
                });
            });

            // Cancel Booking
            document.querySelectorAll('.cancel-booking').forEach(button => {
                button.addEventListener('click', function() {
                    if (confirm('Are you sure you want to cancel this booking?')) {
                        const bookingId = this.getAttribute('data-booking-id');
                        
                        fetch('BookingStatusServlet', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded',
                            },
                            body: `bookingId=${bookingId}&statusId=3`  // Assuming 3 is cancelled status
                        })
                        .then(response => response.text())
                        .then(result => {
                            alert(result);
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

    <%-- Include Footer --%>
    <jsp:include page="footer.jsp" />
</body>
</html>
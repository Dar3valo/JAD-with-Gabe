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
        .btn-group form {
            display: inline-block;
            margin: 0 2px;
        }
    </style>
</head>
<body class="bg-light">
    <%-- Permission Check --%>
    <%
    {
        request.setAttribute("pageAccessLevel", "1");
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
                            <th>Service</th>
                            <th>Timing</th>
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
                                <td><%= booking.getServiceName() %></td>
                                <td><%= booking.getBookingPeriod() %></td>
                                <td>
                                    <span class="status-badge <%= 
                                        booking.getStatusDescription().equalsIgnoreCase("Pending") ? "bg-warning" :
                                        booking.getStatusDescription().equalsIgnoreCase("Incomplete") ? "bg-info" :
                                        booking.getStatusDescription().equalsIgnoreCase("Completed") ? "bg-success" :
                                        "bg-secondary" %>">
                                        <%= booking.getStatusDescription() %>
                                    </span>
                                </td>
                                <td>
                                    <div class="btn-group">
                                        <!-- View Details Button -->
                                        <form action="<%=request.getContextPath() %>/ViewBookingDetailsServlet" method="get">
                                            <input type="hidden" name="bookingId" value="<%= booking.getBooking_id() %>">
                                            <button type="submit" class="btn btn-sm btn-outline-primary" title="View Details">
                                                <i class="fas fa-eye"></i>
                                            </button>
                                        </form>
                                        
                                        <% if (booking.getStatusDescription().equalsIgnoreCase("Pending")) { %>
                                            <!-- Mark as Incomplete -->
                                            <form action="<%=request.getContextPath() %>/UpdateBookingStatusServlet" method="post">
                                                <input type="hidden" name="bookingId" value="<%= booking.getBooking_id() %>">
                                                <input type="hidden" name="statusId" value="2">
                                                <button type="submit" class="btn btn-sm btn-outline-warning" title="Mark as Incomplete">
                                                    <i class="fas fa-clock"></i>
                                                </button>
                                            </form>
                                            
                                            <!-- Mark as Completed -->
                                            <form action="<%=request.getContextPath() %>/UpdateBookingStatusServlet" method="post">
                                                <input type="hidden" name="bookingId" value="<%= booking.getBooking_id() %>">
                                                <input type="hidden" name="statusId" value="3">
                                                <button type="submit" class="btn btn-sm btn-outline-success" title="Mark as Completed">
                                                    <i class="fas fa-check"></i>
                                                </button>
                                            </form>
                                        <% } else if (booking.getStatusDescription().equalsIgnoreCase("Incomplete")) { %>
                                            <!-- Mark as Completed -->
                                            <form action="<%=request.getContextPath() %>/UpdateBookingStatusServlet" method="post">
                                                <input type="hidden" name="bookingId" value="<%= booking.getBooking_id() %>">
                                                <input type="hidden" name="statusId" value="3">
                                                <button type="submit" class="btn btn-sm btn-outline-success" title="Mark as Completed">
                                                    <i class="fas fa-check"></i>
                                                </button>
                                            </form>
                                        <% } %>
                                    </div>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        <% } %>

        <%-- Display Messages --%>
        <% if (request.getAttribute("message") != null) { %>
            <div class="alert alert-success mt-3">
                <%= request.getAttribute("message") %>
            </div>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger mt-3">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <%-- Include Footer --%>
    <jsp:include page="footer.jsp" />
</body>
</html>
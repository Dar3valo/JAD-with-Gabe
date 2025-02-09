<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Models.Booking.Booking" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cleaning Service Status</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    
    <style>
        .booking-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .status-badge {
            padding: 0.5em 1em;
            border-radius: 20px;
            font-weight: 500;
        }
        .time-notice {
            padding: 1rem;
            margin-bottom: 1rem;
            border-left: 4px solid;
        }
        .notice-info {
            background-color: #cfe2ff;
            border-left-color: #0d6efd;
        }
        .notice-warning {
            background-color: #fff3cd;
            border-left-color: #ffc107;
        }
        .notice-error {
            background-color: #f8d7da;
            border-left-color: #dc3545;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <% 
                Booking booking = (Booking) request.getAttribute("booking");
                Boolean isWithinTimeFrame = (Boolean) request.getAttribute("isWithinTimeFrame");
                Boolean isAfterTimeFrame = (Boolean) request.getAttribute("isAfterTimeFrame");
                String token = (String) request.getAttribute("token");
                
                if (booking != null) { 
                %>
                    <div class="booking-card p-4 mb-4">
                        <h2 class="text-center mb-4">Cleaning Service Details</h2>
                        
                        <!-- Time Frame Messages -->
                        <% if (!isWithinTimeFrame && !isAfterTimeFrame) { %>
                            <div class="time-notice notice-info">
                                <i class="fas fa-info-circle me-2"></i>
                                <strong>Preview Mode:</strong> The service is scheduled for <%= booking.getBookingPeriod() %>.
                                You can view the details now, but status updates will only be available during the scheduled time window.
                            </div>
                        <% } else if (isWithinTimeFrame) { %>
                            <div class="time-notice notice-warning">
                                <i class="fas fa-clock me-2"></i>
                                <strong>Service Time Active:</strong> You can now update the service status
                            </div>
                        <% } else if (isAfterTimeFrame) { %>
                            <div class="time-notice notice-error">
                                <i class="fas fa-exclamation-triangle me-2"></i>
                                <strong>Time Expired:</strong> The service window has ended. Status can no longer be updated.
                            </div>
                        <% } %>

                        <!-- Booking Details -->
                        <div class="row mb-4">
                            <div class="col-md-6">
                                <h5>Service Details</h5>
                                <p><strong>Service:</strong> <%= booking.getServiceName() %></p>
                                <p><strong>Date:</strong> <%= booking.getBooking_date() %></p>
                                <p><strong>Time:</strong> <%= booking.getBookingPeriod() %></p>
                            </div>
                            <div class="col-md-6">
                                <h5>Location</h5>
                                <p><strong>Address:</strong> <%= booking.getMain_address() %></p>
                                <p><strong>Postal Code:</strong> <%= booking.getPostal_code() %></p>
                            </div>
                        </div>

                        <!-- Special Instructions -->
                        <% if (booking.getSpecial_request() != null && !booking.getSpecial_request().isEmpty()) { %>
                            <div class="mb-4">
                                <h5>Special Instructions</h5>
                                <p class="mb-0"><%= booking.getSpecial_request() %></p>
                            </div>
                        <% } %>

                        <!-- Current Status -->
                        <div class="mb-4">
                            <h5>Current Status</h5>
                            <%
                            String statusDesc = booking.getStatusDescription();
                            String badgeClass = "bg-secondary text-white";
                            
                            if (statusDesc != null) {
                                if (statusDesc.equalsIgnoreCase("Incomplete")) {
                                    badgeClass = "bg-warning text-dark";
                                } else if (statusDesc.equalsIgnoreCase("In Progress")) {
                                    badgeClass = "bg-info text-white";
                                } else if (statusDesc.equalsIgnoreCase("Completed")) {
                                    badgeClass = "bg-success text-white";
                                }
                            }
                            %>
                            <span class="status-badge <%= badgeClass %>">
                                <%= statusDesc != null ? statusDesc : "Unknown" %>
                            </span>
                        </div>

                        <!-- Status Update Form - Only shown during active window -->
                        <% if (Boolean.TRUE.equals(isWithinTimeFrame)) { %>
                            <form action="<%=request.getContextPath()%>/UpdateServiceStatus" method="post" class="mt-4">
                                <input type="hidden" name="token" value="<%= token %>">
                                <div class="mb-3">
                                    <label class="form-label">Update Status</label>
                                    <select name="statusId" class="form-select">
                                        <option value="2" <%= booking.getStatus_id() == 2 ? "selected" : "" %>>In Progress</option>
                                        <option value="3" <%= booking.getStatus_id() == 3 ? "selected" : "" %>>Completed</option>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save me-2"></i>Update Status
                                </button>
                            </form>
                        <% } %>
                    </div>
                <% } else { %>
                    <div class="text-center py-5">
                        <i class="fas fa-exclamation-circle fa-3x text-danger mb-3"></i>
                        <h3>Invalid or Expired Link</h3>
                        <p class="text-muted">The booking information could not be found or this link is no longer valid.</p>
                    </div>
                <% } %>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
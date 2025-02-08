<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Models.Booking.Booking" %>
<%@ page import="Models.Status.Status" %>
<%@ page import="Models.Status.StatusDAO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Real Time Status</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous" />
</head>
<body>
    <jsp:include page="navbar.jsp" />

    <div class="container my-5">
        <h1 class="text-center">User Booking List</h1>

        <%
            // Retrieve the bookings list from the session
            List<Booking> bookings = (List<Booking>) session.getAttribute("bookings");

            if (bookings != null && !bookings.isEmpty()) {
        %>

        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th scope="col">Booking ID</th>
                    <th scope="col">Booking Date</th>
                    <th scope="col">Special Request</th>
                    <th scope="col">Main Address</th>
                    <th scope="col">Postal Code</th>
                    <th scope="col">Schedule ID</th>
                    <th scope="col">Service ID</th>
                    <th scope="col">Creation Date</th>
                    <th scope="col">Status</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Loop through the bookings and display each booking
                    for (Booking booking : bookings) {
                %>
                <tr>
                    <td><%= booking.getBooking_id() %></td>
                    <td><%= booking.getBooking_date() %></td>
                    <td><%= booking.getSpecial_request() != null ? booking.getSpecial_request() : "None" %></td>
                    <td><%= booking.getMain_address() %></td>
                    <td><%= booking.getPostal_code() %></td>
                    <td><%= booking.getSchedule_id() %></td>
                    <td><%= booking.getService_id() %></td>
                    <td><%= booking.getCreation_date() %></td>
                    <td>
                        <%
                            // Fetch the status based on the status_id
                            StatusDAO statusDAO = new StatusDAO(); // Ensure StatusDAO is properly instantiated
                            Status status = statusDAO.getStatus(booking.getStatus_id());
                            String statusDescription = (status != null) ? status.getName() : "Unknown";
                        %>
                        <span class="badge bg-primary"><%= statusDescription %></span>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <%
            } else {
        %>
        <div class="alert alert-warning text-center" role="alert">No bookings found for this user.</div>
        <%
            }
        %>
    </div>

    <jsp:include page="footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Models.Booking.Booking" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Invoice</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous" />
    <link rel="stylesheet" href="../CSS/invoice.css"/>
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


    <jsp:include page="navbar.jsp" />

    <section class="p-5">
        <div class="container invoice-container">
            <div class="invoice-header">
                <h1 class="h2 mb-0">Invoice Details</h1>
                <p class="text-muted">Generated on <%= new java.text.SimpleDateFormat("MMMM d, yyyy").format(new java.util.Date()) %></p>
            </div>

            <% 
            List<Booking> bookedItems = (List<Booking>) session.getAttribute("allBookedItems");

            if (bookedItems == null) { 
            	response.sendRedirect(request.getContextPath() + "/TransferServlet");
            }else if(bookedItems.isEmpty()){
            	out.println("<p>No Services Booked</p>");
            }
            else { %>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-light">
                            <tr>
                                <th>Booking ID</th>
                                <th>Booking Date</th>
                                <th>Service Name</th>
                                <th>Special Request</th>
                                <th>Main Address</th>
                                <th>Postal Code</th>
                                <th class="text-end">Service Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                                double total = 0;
                                for (Booking booking : bookedItems) {
                                    total += booking.getServicePrice();
                            %>
                            <tr>
                                <td><strong>#<%= booking.getBooking_id() %></strong></td>
                                <td><%= new java.text.SimpleDateFormat("MMM d, yyyy").format(booking.getBooking_date()) %></td>
                                <td><%= booking.getServiceName() %></td>
                                <td><%= booking.getSpecial_request().isEmpty() ? "-" : booking.getSpecial_request() %></td>
                                <td><%= booking.getMain_address() %></td>
                                <td><%= booking.getPostal_code() %></td>
                                <td class="text-end">$<%= String.format("%.2f", booking.getServicePrice()) %></td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>

                <div class="total-price">
                    <h4 class="mb-0">Total Amount: $<%= String.format("%.2f", total) %></h4>
                </div>
            <% } %>

            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="alert alert-danger">
                    <i class="bi bi-exclamation-triangle"></i> <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>

            <% if (session.getAttribute("successMessage") != null) { %>
                <div class="alert alert-success">
                    <i class="bi bi-check-circle"></i> <%= session.getAttribute("successMessage") %>
                </div>
            <% } %>

            <div class="action-buttons">
                <button onclick="window.print()" class="btn btn-outline-secondary">
                    <i class="bi bi-printer"></i> Print Invoice
                </button>
                <a href="<%= request.getContextPath() %>/public/HTML/checkOut.jsp" class="btn btn-secondary">
                    <i class="bi bi-arrow-left"></i> Back to Checkout
                </a>
                <a href="<%= request.getContextPath() %>/public/HTML/homePage.jsp" class="btn btn-primary">
                    <i class="bi bi-house"></i> Home
                </a>
            </div>
        </div>
    </section>

    <jsp:include page="footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</body>
</html>
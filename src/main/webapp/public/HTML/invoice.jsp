<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Booking" %>
<%@ page import="model.BookingDAO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AllClean Confirmation of Purchase Page</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
<link rel="stylesheet" href="../CSS/invoice.css" />
</head>
<body>

	<%
	request.getRequestDispatcher("/TransferServlet").include(request, response);
	%>

	<%-- Navbar --%>
	<jsp:include page="navbar.jsp" />

	<section class="p-5">
		<%-- Invoice Page --%>
		<div class="container mt-5">
			<h2 class="text-center mb-4">Receipt</h2>

			<!-- Error Message Display -->
			<%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
			<div class="alert alert-danger" role="alert">
				<%= errorMessage %>
			</div>
			<% } %>

			<!-- Booking Invoice Form -->
			<%
            Booking booking = (Booking) request.getAttribute("booking");
            if (booking != null) {
        %>
			<div class="row">
				<div class="col-12">
					<div class="card">
						<div class="card-header bg-primary text-white">
							<h4 class="mb-0">Booking Information</h4>
						</div>
						<div class="card-body">
							<form action="invoice.jsp" method="post">
								<div class="row mb-3">
									<label for="bookingId" class="col-sm-2 col-form-label">Booking ID</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="bookingId" value="<%= booking.getBooking_id() %>" readonly>
									</div>
								</div>

								<div class="row mb-3">
									<label for="bookingDate" class="col-sm-2 col-form-label">Booking Date</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="bookingDate" value="<%= booking.getBooking_date() %>" readonly>
									</div>
								</div>

								<div class="row mb-3">
									<label for="specialRequest" class="col-sm-2 col-form-label">Special Request</label>
									<div class="col-sm-10">
										<textarea class="form-control" id="specialRequest" rows="3" readonly><%= booking.getSpecial_request() %></textarea>
									</div>
								</div>

								<div class="row mb-3">
									<label for="mainAddress" class="col-sm-2 col-form-label">Main Address</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="mainAddress" value="<%= booking.getMain_address() %>" readonly>
									</div>
								</div>

								<div class="row mb-3">
									<label for="postalCode" class="col-sm-2 col-form-label">Postal Code</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="postalCode" value="<%= booking.getPostal_code() %>" readonly>
									</div>
								</div>

								<div class="row mb-3">
									<label for="scheduleId" class="col-sm-2 col-form-label">Schedule ID</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="scheduleId" value="<%= booking.getSchedule_id() %>" readonly>
									</div>
								</div>

								<div class="row mb-3">
									<label for="serviceId" class="col-sm-2 col-form-label">Service ID</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="serviceId" value="<%= booking.getService_id() %>" readonly>
									</div>
								</div>

								<div class="row mb-3">
									<div class="col-sm-12 text-center">
										<!-- Payment Button -->
										<button type="submit" class="btn btn-success">
											<i class="bi bi-cart"></i> Pay
										</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<% } %>
		</div>
	</section>

	<%-- Footer --%>
	<jsp:include page="footer.jsp" />

</body>
</html>

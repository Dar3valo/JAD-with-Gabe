<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
<link rel="stylesheet" href="../CSS/register.css" />
<link rel="stylesheet" href="../CSS/booking.css" />
<title>AllClean Booking</title>
</head>
<body>

	<%-- Navbar --%>
	<jsp:include page="navbar.jsp" />

	<%-- Booking Section with cart --%>
	<section class="p-5">
		<div class="container">
			<div>
				<h1>Book Your Appointment!</h1>
			</div>

			<form method="POST" action="confirmBooking.jsp">
				<div class="form-group my-3">
					<label for="dateRange">Select Date Range:</label> <select
						id="dateRange" id="dateRange" class="form-control">
						<%-- Dummy Data --%>
						<option value="week_1">1 January - 14 January</option>
						<option value="week_2">15 January - 21 January</option>
						<option value="week_3">21 January - 31 January</option>
					</select>
				</div>

				<div class="form-group my-3">
					<label for="availableSlots">Available Slots:</label>
					<div id="availableSlots" class="slot-container">
						<%-- Dummy data for testing --%>
						<ul>
							<li><input type="checkbox" name="slot" id="slot1"
								value="2024-1-01-10:00AM"> <label for="slot1"
								class="slot-box">January 1st, 10am</label></li>
							<li><input type="checkbox" name="slot" id="slot2"
								value="2024-1-02-10:00AM"> <label for="slot2"
								class="slot-box">January 2nd, 10am</label></li>
							<li><input type="checkbox" name="slot" id="slot3"
								value="2024-1-03-10:00AM"> <label for="slot3"
								class="slot-box">January 3rd, 10am</label></li>
							<li><input type="checkbox" name="slot" id="slot4"
								value="2024-1-04-10:00AM"> <label for="slot4"
								class="slot-box">January 4th, 10am</label></li>
						</ul>
					</div>
				</div>


				<div class="form-group my-3">
					<label for="cart">Your Cart:</label>
					<div id="cart" class="border p-3">
						<p>Your selected slots will appear here</p>
					</div>
				</div>

				<%-- Checkout Button --%>
				<button type="submit" class="btn btn-primary">Checkout</button>
			</form>
		</div>
	</section>


	<%-- Footer --%>
	<jsp:include page="footer.jsp" />
	
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
	
</body>
</html>
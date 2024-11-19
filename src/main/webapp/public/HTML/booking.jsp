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
<link rel="stylesheet" href="../CSS/booking.css" />
<link rel="stylesheet"
	href="https://fonts.google.com/share?selection.family=Montserrat:ital,wght@0,100..900;1,100..900|Raleway:ital,wght@0,100..900;1,100..900">
<title>AllClean Booking</title>
</head>
<body>

	<%-- Navbar --%>
	<jsp:include page="navbar.jsp" />

	<%-- Booking Section with cart --%>
	<section class="p-5">
		<div class="bf-container bg-white">
			<div class="bf-body">
				<div class="bf-head">
					<h1>Booking Form</h1>
					<p>Start Booking Now</p>
				</div>
				
				<form class="bf-body-box" action="#">
					<div class="bf-row">
						<div class="bf-col-6">
							<p>Your Name</p>
							<input type="text" name="fname" id="fname" placeholder="Enter Name">
						</div>
						<div class="bf-col-6">
							<p>Email Address</p>
							<input type="email" name="email" id="email" placeholder="Enter Email">
						</div>
					</div>
					
					<div class="bf-row">
						<div class="bf-col-6">
							<p>Select Date</p>
							<input type="date" name="date" id="date">
						</div>
						<div class="bf-col-6">
							<p>Select Number</p>
							<select name="s-select">
								<option>Select Number</option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
							</select>
						</div>
					</div>
					
					<div class="bf-row">
						<div class="bf-col-12">
							<p>Messages</p>
							<textarea name="messages" id="messages" cols="3" rows="10"></textarea>
						</div>
					</div>
					
					<div class="bf-row">
						<div class="bf-col-3">
							<input type="submit" value="Book">
						</div>
					</div>
					
					
				</form>
				
				<div class="bf-footer">
					<p>Designed by <a href="#">-The Codey Duo-</a></p>
				</div>
				
				
				
			</div>
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
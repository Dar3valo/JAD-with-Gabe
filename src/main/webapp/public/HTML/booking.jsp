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
		<div class="container my-2 bg-dark w-50 text-light p-2 rounded-3">
			<div class="container-fluid bg-dark text-light py-3">
				<header class="text-center">
					<h1 class="display-6">Book Your Services</h1>
				</header>
			</div>
			<form class="row g-3">

				<!-- Validation -->
				<div class="col-md-4">
					<label for="validationDefault01" class="form-label">First
						name</label> <input type="text" class="form-control"
						id="validationDefault01" value="Mark" required>
				</div>
				<div class="col-md-4">
					<label for="validationDefault02" class="form-label">Last
						name</label> <input type="text" class="form-control"
						id="validationDefault02" value="Otto" required>
				</div>
				<div class="col-md-4">
					<label for="validationDefaultUsername" class="form-label">Username</label>
					<div class="input-group">
						<span class="input-group-text" id="inputGroupPrepend2">@</span> <input
							type="text" class="form-control" id="validationDefaultUsername"
							aria-describedby="inputGroupPrepend2" required>
					</div>
				</div>


				<div class="col-md-6">
					<label for="inputEmail4" class="form-label">Email</label> <input
						type="email" class="form-control" id="inputEmail4">
				</div>
				<div class="col-md-6">
					<label for="inputPassword4" class="form-label">Password</label> <input
						type="password" class="form-control" id="inputPassword4">
				</div>
				<div class="col-12">
					<label for="inputAddress" class="form-label">Address</label> <input
						type="text" class="form-control" id="inputAddress"
						placeholder="1234 Main St">
				</div>
				<div class="col-12">
					<label for="inputAddress2" class="form-label">Address 2</label> <input
						type="text" class="form-control" id="inputAddress2"
						placeholder="Apartment, studio, or floor">
				</div>
				<div class="col-md-6">
					<label for="inputCity" class="form-label">City</label> <input
						type="text" class="form-control" id="inputCity">
				</div>
				<div class="col-md-4">
					<label for="inputState" class="form-label">State</label> <select
						id="inputState" class="form-select">
						<option selected>Choose...</option>
						<option>...</option>
					</select>
				</div>
				<div class="col-md-2">
					<label for="inputZip" class="form-label">Zip</label> <input
						type="text" class="form-control" id="inputZip">
				</div>
				<div class="col-12">
					<div class="form-check">
						<input class="form-check-input" type="checkbox" id="gridCheck">
						<label class="form-check-label" for="gridCheck"> Check me
							out </label>
					</div>
				</div>
				<div class="col-12">
					<button type="submit" class="btn btn-primary">Sign in</button>
				</div>
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
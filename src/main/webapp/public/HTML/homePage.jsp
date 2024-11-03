<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>CleanEverywhere</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
<link rel="stylesheet" href="../CSS/styles.css">
</head>
<body>
	<%--Navbar --%>
	<nav class="navbar navbar-expand-lg bg-dark navbar-dark py-3">
		<div class="container">
			<a href="#" class="navbar-brand">AllClean</a>

			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navmenu">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navmenu">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" href="#Services">Services</a>
					</li>
				</ul>
				<ul class="navbar-nav ms-auto">
					<li class="nav-item"><a class="nav-link" href="#login">Login</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#Register">Register</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#Booking">BookNow</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<%-- Showcase --%>
	<section class="bg-dark text-light p-5 text-center text-sm-start">
		<div class="container">
			<div class="d-sm-flex align-items-center justify-content-between">
				<div>
					<h1>Book Now For Professional Cleaning!</h1>
					<p class="lead my-4">Professional house cleaning services with
						flexible schedule and booking slots Sign up for our yearly
						subscription now!</p>
					<button class="btn btn-primary btn-lg rounded">Subscribe</button>
				</div>
				<img class="img-fluid w-50 d-none d-sm-block rounded" alt=""
					src="../Image/types-of-house-cleaning-services-to-offer.jpg">
			</div>
		</div>
	</section>

	<%--Boxes --%>
	<section class="p-5">
		<div class="container">
			<h1>
				<strong>Experience AllClean Benefits</strong>
			</h1>
			<br>
			<div class="row text-center d-flex align-items-stretch g-4">  <%--This makes the columns stretch to the same height --%>
				<div class="col-md">
					<div class="card bg-dark text-light h-100"> <%-- Set the height requirements of card to 100% and ensure card fills up the 100% space --%>
						<div class="card-body text-center d-flex flex-column">  <%-- Allow the contents of the card to stack vertically, improving readability --%>
							<div class="h1 mb-3">
								<i class="bi bi-calendar-week"> Flexible Scheduling</i>
							</div>
							<p class="card-text lead my-3">Our timeslot options fit into
								anyone's lifestyle. Get weekly part time maid with flexible
								terms, or last minute availability on ad hoc sessions when you
								book with AllClean.</p>
						</div>
					</div>
				</div>
				<div class="col-md">
					<div class="card bg-dark text-light h-100">
						<div class="card-body text-center d-flex flex-column">
							<div class="h1 mb-3">
								<i class="bi bi-bar-chart-line"> Expert Cleaners</i>
							</div>
							<p class="card-text lead my-3">Our customers love our
								cleaners! All our workers are full time AllClean employees, and
								they undergo training to ensure they deliver our signature
								quality.</p>
						</div>
					</div>
				</div>
				<div class="col-md">
					<div class="card bg-dark text-light h-100">
						<div class="card-body text-center d-flex flex-column">
							<div class="h1 mb-3">
								<i class="bi bi-hourglass-split"> Time Efficient</i>
							</div>
							<p class="card-text lead my-3">Get a spotless space without
								wasting your time. Our efficient, high-quality cleaning service
								fits your schedule, so you can focus on what matters most. Let
								us handle the cleaning, so you don’t have to.</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<%-- Carousel --%>
	<section class="p-5 bg-dark">
		
	</section>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
</body>
</html>
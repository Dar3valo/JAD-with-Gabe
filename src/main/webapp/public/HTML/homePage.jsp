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
	crossorigin="anonymous">
<link rel="stylesheet" href="../CSS/styles.css">
</head>
<body>
	<%--Navbar --%>
	<nav class="navbar navbar-expand-lg bg-dark navbar-dark py-3">
		<div class="container">
			<a href="#" class="navbar-brand">CleanEverywhere</a>

			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navmenu">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navmenu">
				<ul class="navbar-nav">
					<li class="nav-item">
						<a class="nav-link" href="#Services">Services</a>
					</li>
				</ul>
				<ul class="navbar-nav ms-auto">
					<li class="nav-item">
						<a class="nav-link" href="#login">Login</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="#Register">Register</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="#Booking">BookNow</a>
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
					<h1>Book Now For Professional Cleaning</h1>
					<p class="lead my-4">
						Professional house cleaning services with
						flexible schedule and booking slots
					</p>
					<button class="btn btn-primary btn-lg rounded">Book Now</button>
				</div>
				<img class="img-fluid w-50 d-none d-sm-block rounded" alt=""
					src="../Image/types-of-house-cleaning-services-to-offer.jpg">
			</div>
		</div>
	</section>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>AllClean User feedback form</title>
<link href="../CSS/star.css" rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
<style>
body:before {
	display: block;
	content: '';
	height: 60px;
}
</style>
</head>
<body>
	<%--Navbar --%>
	<nav class="navbar navbar-expand-lg bg-dark navbar-dark py-3 fixed-top">
		<div class="container">
			<a href="homePage.jsp" class="navbar-brand">AllClean</a>

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
					<li class="nav-item"><a id="logoutButton" href="#"
						class="nav-link d-none">Logout</a></li>
					<li class="nav-item"><a class="nav-link" href="loginv2.jsp">Login</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="register.jsp">Register</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#Booking">BookNow</a>
					</li>
					<li class="nav-item"><a class="nav-link"
						href="homePage.jsp#questions">Questions</a></li>
					<li class="nav-item"><a class="nav-link" href="contact.jsp">Contact
							Us</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<section class="p-5">
		<div class="container mt-4 shadow-lg"
			style="background-color: rgba(0, 0, 0, 0.7); border-radius: 8px; padding: 40px;">
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-6">
					<h2 class="text-white">Feedback Form</h2>
					<p class="text-white">We would love to hear from you!</p>
					<hr class="bg-light">
					<form>
						<div id="rating-parent" class="form-group">
							<div class="rating">
								<input type="radio" name="rating" id="r1"><label for="r1"></label>
								<input type="radio" name="rating" id="r2"><label for="r2"></label>
								<input type="radio" name="rating" id="r3"><label for="r3"></label>
								<input type="radio" name="rating" id="r4"><label for="r4"></label>
								<input type="radio" name="rating" id="r5"> <label for="r5"></label>
							</div>
						</div>
						
						<div class="form-group mb-2">
							<label class="form-label text-white">Comments on cleaning
								services:</label>
							<textarea rows="4" class="form-control" required></textarea>
							<br>
						</div>
						<div class="form-group mb-2">
							<label class="form-label text-white">Improvements that we
								can implement:</label>
							<textarea rows="4" class="form-control" required></textarea>
							<br>
						</div>
						
						<button type="submit" class="btn btn-primary mt-4">Submit</button>
					</form>
				</div>
			</div>
		</div>
	</section>




	<%-- Footer --%>
	<footer class="p-5 bg-dark text-white text-center position-relative">
		<div class="container">
			<p class="lead">Copyright &copy; 2024 AllClean Services</p>

			<a href="#" class="position-absolute bottom-0 end-0 p-5"> <i
				class="bi bi-arrow-up-circle h1"></i>
			</a>
		</div>
	</footer>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
</body>
</html>
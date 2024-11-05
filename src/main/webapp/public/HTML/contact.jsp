<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>AllClean Contact Us</title>
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
					<li class="nav-item"><a class="nav-link" href="loginv2.jsp">Login</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="register.jsp">Register</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#Booking">BookNow</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="homePage.jsp#questions">Questions</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="contact.jsp">Contact Us</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="feedbackForm.jsp">Feedback</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<%-- Contact and map (to be pasted in contact.jsp)--%>
	<section class="p-5">
		<div class="container">
			<div class="row g-4">
				<div class="col-md">
					<h2 class="text-center mb-4">Contact Info</h2>
					<ul class="list-group list-group-flush lead">
						<li class="list-group-item"><span class="fw-bold">Main
								Location:</span> Dover Avenue 12 Singapore</li>
						<li class="list-group-item"><span class="fw-bold">AllClean
								Customer Service:</span> +65 68704727</li>
						<li class="list-group-item"><span class="fw-bold">AllClean
								email:</span> vernon_tan@ichat.sp.edu.sg</li>
					</ul>
				</div>
				<%-- Map Implementation --%>
				<div class="col-md">
					<iframe class="rounded"
						src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3988.775867639408!2d103.774974574942!3d1.3097810617033891!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31da1a602ff17c15%3A0xa9545dd23993859e!2sSingapore%20Polytechnic!5e0!3m2!1sen!2ssg!4v1730737891172!5m2!1sen!2ssg"
						width="600" height="450" style="border: 0;" allowfullscreen=""
						loading="lazy" referrerpolicy="no-referrer-when-downgrade">
					</iframe>
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
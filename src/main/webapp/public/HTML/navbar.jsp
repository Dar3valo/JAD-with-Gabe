<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>AllCleanNavbar</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
<style>
body:before{
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
					<li class="nav-item"><a class="nav-link" href="services.jsp">Services</a>
					</li>
				</ul>
				<ul class="navbar-nav ms-auto">
					<li class="nav-item">
					<a id="logoutButton" href="#" class="nav-link d-none">Logout</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="loginv2.jsp">Login</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="register.jsp">Register</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="booking.jsp">BookNow</a>
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
</body>
</html>
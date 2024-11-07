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
<title>AllCleanRegister</title>
</head>
<body>

	<%-- Navbar --%>
	<jsp:include page="navbar.jsp" />

	<%-- Registration Section --%>
	<section
		class="d-flex justify-content-center align-items-center min-vh-100 p-5">
		<div
			class="container-flex justify-content-center align-items-center min-vh-100">
			<div class="row border rounded-5 p-3 bg-white shadow box-area">

				<div
					class="col-md-6 d-flex justify-content-center align-items-center flex-column left-box"
					style="background: #000; border-radius: 15px;">
					<div class="featured-image mb-3">
						<img src="../Image/noBGLogin.png" class="img-fluid"
							style="width: 250px;" alt="">
					</div>
					<p class="text-light fs-2"
						style="font-family: 'Courier New', Courier, monospace; font-weight: 600;">Join
						Us</p>
					<small class="text-light text-wrap text-center"
						style="width: 17rem; font-family: 'Courier New', Courier, monospace;">
						Create an account with AllClean for premium services! </small>
				</div>

				<div class="col-md-6 right-box">
					<div class="row align-items-center">
						<div class="header-text mb-4">
							<h2>Welcome!</h2>
							<p>Sign up to get started with AllClean.</p>
						</div>
						<div class="input-group mb-3">
							<input type="text"
								class="form-control form-control-lg bg-light fs-6"
								placeholder="Full Name">
						</div>
						<div class="input-group mb-3">
							<input type="text"
								class="form-control form-control-lg bg-light fs-6"
								placeholder="Email Address">
						</div>
						<div class="input-group mb-3">
							<input type="password"
								class="form-control form-control-lg bg-light fs-6"
								placeholder="Password">
						</div>
						<div class="input-group mb-3">
							<input type="password"
								class="form-control form-control-lg bg-light fs-6"
								placeholder="Confirm Password">
						</div>
						<div id="gender-parent" class="form-group my-2">
							<div class="gender d-flex justify-content-center">
								<div class="me-3">
									<input type="radio" name="gender" id="male"><label
										for="male">&nbsp;Male</label>
								</div>
								<div class="me-3">
									<input type="radio" name="gender" id="female"><label
										for="female">&nbsp;Female</label>
								</div>
								<div class="me-3">
									<input type="radio" name="gender" id="ratherNotSay"><label
										for="ratherNotSay">&nbsp;Rather not say</label>
								</div>
							</div>
						</div>
						<div class="input-group mb-3">
							<button class="btn btn-lg btn-primary w-100 fs-6">Sign
								Up</button>
						</div>
						<div class="row">
							<small>Already have an account? <a href="login.jsp">Login</a></small>
						</div>
					</div>
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
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
<link rel="stylesheet" href="../CSS/loginStylesV2.css" />
<title>AllCleanLogin</title>
</head>
<body>
	<%-- Navbar --%>
	<jsp:include page="navbar.jsp" />

	<%-- Login Section --%>
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
						style="font-family: 'Courier New', Courier, monospace; font-weight: 600;">Be
						Verified</p>
					<small class="text-light text-wrap text-center"
						style="width: 17rem; font-family: 'Courier New', Courier, monospace;">Join
						AllClean for premium services right now</small>
				</div>


				<div class="col-md-6 right-box">
					<div class="row align-items-center">
						<div class="header-text mb-4">
							<h2>Hello,Again</h2>
							<p>We are happy to have you back.</p>
						</div>
						 <!-- Start of form tag -->
                    <form method="post" action="loginBackend.jsp"> <!-- Form action points to the servlet for processing -->
                        <div class="input-group mb-3">
                            <input type="text" name="loginid" class="form-control form-control-lg bg-light fs-6"
                                placeholder="Email address" required>
                        </div>
                        <div class="input-group mb-1">
                            <input type="password" name="password" class="form-control form-control-lg bg-light fs-6"
                                placeholder="Password" required>
                        </div>
                        <div class="input-group mb-5 d-flex justify-content-between">
                            <div class="form-check">
                                <input type="checkbox" class="form-check-input" id="formCheck" name="rememberMe">
                                <label for="formCheck" class="form-check-label text-secondary"><small>Remember Me</small></label>
                            </div>
                            <div class="forgot">
                                <small><a href="#">Forgot Password?</a></small>
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <button class="btn btn-lg btn-primary w-100 fs-6" type="submit">Login</button>
                        </div>
                    </form>
                    <!-- End of form tag -->
						<div class="row">
							<small>Don't have account? <a href="register.jsp">Sign
									Up</a></small>
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
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
<link rel="stylesheet" href="../CSS/checkout.css" />
<link rel="stylesheet"
	href="https://fonts.google.com/share?selection.family=Montserrat:ital,wght@0,100..900;1,100..900|Raleway:ital,wght@0,100..900;1,100..900">
<title>AllClean Booking</title>
</head>
<body>

	<%-- Navbar --%>
	<jsp:include page="navbar.jsp" />
	
	<section class="p-5">
		<div class="wrapper">
			<h1>Shopping Cart</h1>
			<div class="project">
				<div class="shop">
					<div class="box">
						<img src="#" alt="#">
						<div class="content">
							<h3>Home Cleaning</h3>
							<h4>Price: $40</h4>
							<p class="unit">Quantity: <input value="2"></p>
							<p class="btn-area">
								<i class="bi bi-trash"></i>
								<span class="btn2">Remove</span>
							</p>
						</div>
					</div>
					<div class="box">
						<img src="#" alt="#">
						<div class="content">
							<h3>Office Cleaning</h3>
							<h4>Price: $40</h4>
							<p class="unit">Quantity: <input value="2"></p>
							<p class="btn-area">
								<i class="bi bi-trash"></i>
								<span class="btn2">Remove</span>
							</p>
						</div>
					</div>
					<div class="box">
						<img src="#" alt="#">
						<div class="content">
							<h3>Bathroom Cleaning</h3>
							<h4>Price: $40</h4>
							<p class="unit">Quantity: <input value="2"></p>
							<p class="btn-area">
								<i class="bi bi-trash"></i>
								<span class="btn2">Remove</span>
							</p>
						</div>
					</div>
				</div>
				
				<div class="right-bar">
					<p><span>Subtotal</span> <span>$120</span></p>
					<hr>
					<p><span>Tax (10%)</span> <span>$6</span></p>
					<hr>
					<p><span>Shipping</span> <span>$15</span></p>
					<hr>
					<p><span>Totak</span> <span>$141</span></p>
					
					<a href="#"><i class="bi bi-cart"></i>Pay</a>
					<hr>
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
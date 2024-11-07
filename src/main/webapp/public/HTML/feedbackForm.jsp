<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>AllClean User feedback form</title>
<link href="../CSS/star.css" rel="stylesheet">
<link href="../CSS/feedback.css" rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
</head>
<body>
	<%--Navbar --%>
	<jsp:include page="navbar.jsp" />

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
						
						<hr class="bg-light">

						<div class="form-check text-white">
							<%-- Social Media Checkbox --%>
							<input type="checkbox" class="form-check-input" id="socialMedia">
							<label for="socialMedia" class="form-check-label text-light">Social
								Media</label><br>

							<%-- Friends Checkbox --%>
							<input type="checkbox" class="form-check-input" id="friends">
							<label for="friends" class="form-check-label text-light">Friends</label><br>

							<%-- Internet Checkbox --%>
							<input type="checkbox" class="form-check-input" id="internet">
							<label for="internet" class="form-check-label text-light">Internet</label><br>

							<%-- Others Checkbox --%>
							<input type="checkbox" class="form-check-input" id="others">
							<label for="others" class="form-check-label text-light">Others:</label><br>

							<%-- Textarea for Other's Checkbox --%>
							<textarea id="otherSource" class="form-control mt-2"
								placeholder="Please specify"></textarea><br>
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
	<jsp:include page="footer.jsp" />
	
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
	
</body>
</html>
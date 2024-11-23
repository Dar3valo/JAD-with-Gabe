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
					<form method="POST" action="<%=request.getContextPath()%>/InsertFeedbackServlet">
						<div class="form-group mb-4" id="rating-parent">
                            <div class="rating">
                                <input type="radio" name="rating" id="star5" value="5">
                                <label for="star5" title="5 stars"></label>
                                
                                <input type="radio" name="rating" id="star4" value="4">
                                <label for="star4" title="4 stars"></label>
                                
                                <input type="radio" name="rating" id="star3" value="3">
                                <label for="star3" title="3 stars"></label>
                                
                                <input type="radio" name="rating" id="star2" value="2">
                                <label for="star2" title="2 stars"></label>
                                
                                <input type="radio" name="rating" id="star1" value="1">
                                <label for="star1" title="1 star"></label>
                            </div>
                        </div>
						
						<hr class="bg-light">

						<div class="form-check text-white">
							<%-- Social Media Checkbox --%>
							<input type="checkbox" name="sources" class="form-check-input" id="socialMedia" value="SocialMedia">
							<label for="socialMedia" class="form-check-label text-light">Social
								Media</label><br>

							<%-- Friends Checkbox --%>
							<input type="checkbox" name="sources" class="form-check-input" id="friends" value="Friends">
							<label for="friends" class="form-check-label text-light">Friends</label><br>

							<%-- Internet Checkbox --%>
							<input type="checkbox" name="sources" class="form-check-input" id="internet" value="Internet">
							<label for="internet" class="form-check-label text-light">Internet</label><br>

							<%-- Others Checkbox --%>
							<input type="checkbox" name="sources" class="form-check-input" id="others" value="Others">
							<label for="others" class="form-check-label text-light">Others:</label><br>

							<%-- Textarea for Other's Checkbox --%>
							<textarea id="otherSource" name="otherSources" class="form-control mt-2"
								placeholder="Please specify"></textarea><br>
						</div>

						<div class="form-group mb-2">
							<label class="form-label text-white">Comments on cleaning
								services:</label>
							<textarea rows="4" class="form-control" name="comments" required></textarea>
							<br>
						</div>
						<div class="form-group mb-2">
							<label class="form-label text-white">Improvements that we
								can implement:</label>
							<textarea rows="4" class="form-control" name="improvements" required></textarea>
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
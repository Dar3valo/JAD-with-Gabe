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
</head>
<body>
	<%
    { // check permission
    	request.setAttribute("pageAccessLevel", "3");
        RequestDispatcher rd = request.getRequestDispatcher("/checkAccessServlet");
        rd.include(request, response);
        
        Boolean hasAccess = (Boolean) session.getAttribute("accessCheckResult");
        
        if (hasAccess == null || !hasAccess) {
            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
            return;
        }
    }
	%>
>>>>>>> branch 'main' of https://github.com/Dar3valo/JAD-with-Gabe.git
	<%--Navbar --%>
	<jsp:include page="navbar.jsp" />

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
	<jsp:include page="footer.jsp" />
	
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>

</body>
</html>
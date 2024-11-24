<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Service"%>
<%@ page import="model.ServiceDAO"%>
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
<link rel="stylesheet" href="../CSS/service.css" />
<title>AllClean Services</title>
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

	<%-- Navbar --%>
	<jsp:include page="navbar.jsp" />

	<!-- Services Display Page -->
	<section class="p-5">
		<div class="container my-5">
			<h1 class="text-center">Our Services</h1>
			<div class="row">
				<%
                // Retrieve the list of services from the session
                List<Service> services = (List<Service>) session.getAttribute("allServices");
            
            	if (services == null) {
                    response.sendRedirect(request.getContextPath() + "/GetAllServicesServlet");
                    return;
           		    
            	} else if (services.isEmpty()) { %>
				<div class="col-12">
					<p class="text-center">No services available.</p>
				</div>

				<% } else {
                    for (Service service : services) { %>
				<div class="col-md-4">
					<div class="card mb-3">
						<div class="card-body">
							<h5 class="card-title"><%= service.getName() %></h5>
							<p class="card-text"><%= service.getDescription() %></p>
							<p class="card-text">
								Price: $<%= service.getPrice() %></p>
						</div>
					</div>
				</div>
				<%
                    }}
           	 	%>
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

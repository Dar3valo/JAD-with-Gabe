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
<title>AllCleanRegister</title>
</head>
<body>

	<%-- Navbar --%>
	<jsp:include page="navbar.jsp" />
	
	<!-- Services Display Page -->
	<section class="p-5">
		<div class="container">
			<%
        // Retrieve the list of services from the session
        List<Service> services = (List<Service>) session.getAttribute("services");

        if (services != null && !services.isEmpty()) {
    %>
        <table border="1">
            <thead>
                <tr>
                    <th>Service ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Image</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Service service : services) {
                %>
                    <tr>
                        <td><%= service.getService_id() %></td>
                        <td><%= service.getName() %></td>
                        <td><%= service.getDescription() %></td>
                        <td><%= service.getPrice() %></td>
                        <td>
                            <img src="<%= service.getService_photo_url() %>" alt="<%= service.getName() %>" width="100" />
                        </td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    <%
        } else {
    %>
        <p>No services available at the moment.</p>
    <%
        }
    %>
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
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Models.ServiceOrderByRating.ServiceByRating" %>
<%@ page import="Models.Service.Service" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="../CSS/inquiry.css" />
</head>
<body>
<%
    { // Check permission
        request.setAttribute("pageAccessLevel", "2");
        RequestDispatcher rd = request.getRequestDispatcher("/checkAccessServlet");
        rd.include(request, response);
        
        Boolean hasAccess = (Boolean) session.getAttribute("accessCheckResult");
        
        if (hasAccess == null || !hasAccess) {
            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
            return;
        }
    }
    %>
    
    <jsp:include page="navbar.jsp" />

	<%-- Section for inquiry results --%>
	<section class="p-5">
    <div class="container mt-4">
        <h2 class="mb-4">Service Filters</h2>
        <form id="filterForm"
              action="<%= request.getContextPath() %>/ServiceFilterServlet"
              method="get">
            <div class="form-check">
                <input class="form-check-input" type="radio" name="filterType"
                       id="serviceRating" value="rating" checked>
                <label class="form-check-label" for="serviceRating">Service Rating</label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="filterType"
                       id="serviceDemand" value="demand">
                <label class="form-check-label" for="serviceDemand">Service Demand</label>
            </div>
            <button type="submit" class="btn btn-primary mt-3">Filter</button>
        </form>

        <%-- Display results in a table format --%>
        <div class="mt-4">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Service ID</th>
                    <th>Service Name</th>
                    <th>Rating/Demand</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<?> serviceList = (List<?>) session.getAttribute("serviceRatingOrder");
                    if (serviceList == null) {
                        serviceList = (List<?>) session.getAttribute("bookingFrequency");
                    }

                    if (serviceList != null && !serviceList.isEmpty()) {
                        for (Object serviceObj : serviceList) {
                            if (serviceObj instanceof Models.ServiceOrderByRating.ServiceByRating) {
                                Models.ServiceOrderByRating.ServiceByRating service = (Models.ServiceOrderByRating.ServiceByRating) serviceObj;
                %>
                <tr>
                    <td><%= service.getId() %></td>
                    <td><%= service.getName() %></td>
                    <td><%= service.getAverageRating() %></td>
                </tr>
                <%
                            } else if (serviceObj instanceof Models.Service.Service) {
                                Models.Service.Service service = (Models.Service.Service) serviceObj;
                %>
                <tr>
                    <td><%= service.getService_id() %></td>
                    <td><%= service.getName() %></td>
                    <td><%= service.getBooking_count() %></td>
                </tr>
                <%
                            }
                        }
                    } else {
                %>
                <tr>
                    <td colspan="3" class="text-center">No data available.</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
</section>

	<jsp:include page="footer.jsp" />

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Service"%>
<%@ page import="model.ServiceCategory"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
<!-- Corrected Google Fonts link -->
<link
	href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&family=Raleway:ital,wght@0,100..900;1,100..900&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="../CSS/dashboard.css" />
</head>
<body>
	<%
    	List<Service> services = (List<Service>) session.getAttribute("services");
		List<ServiceCategory> categories = (List<ServiceCategory>) session.getAttribute("serviceCategories");
	
		if (services == null || categories == null) {
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/GetServiceInformationServlet?serviceCategory=0");
		    dispatcher.forward(request, response);
		}
	%>
	<%--Navbar --%>
	<%@ include file="navbar.jsp"%>

	<!-- Main content -->
	<div class="container-fluid d-flex flex-column vh-100 mx-0">
		<div class="row my-5 h-100">
			<%-- Filter Sidepanel --%>
			<div class="col-3">
				<section id="filter" class="border-end h-100">
					<h3 class="border-bottom mx-3 pb-5 mb-5 primaryFont">Filter By
						Category</h3>
					<div class="mx-3">
						<!-- Filter Categories Form Here -->
						<h4 class="secondaryFont">Categories</h4>
						<form class="lh-lg d-flex flex-column filterCategories"
							action="${pageContext.request.contextPath}/GetServiceInformationServlet"
							method="GET">
							<div class="overflow-auto h-75 mb-5 filters">
								<% for (ServiceCategory category : categories) { %>
								<input type="radio"
									id="category_<%= category.getService_category_id() %>"
									name="serviceCategory"
									value="<%= category.getService_category_id() %>"> <label
									for="category_<%= category.getService_category_id() %>">
									<%= category.getName() %>
								</label> <br>
								<% } %>
							</div>

							<div class="d-flex align-items-end mt-auto">
								<input type="submit" class="btn btn-primary"
									value="Search Filters">
							</div>
						</form>
					</div>
				</section>
			</div>

			<%-- Display Services --%>
			<div class="col-9 pr-5 ml-5">
				<section id="services" class="h-100">
					<h3 class="border-bottom mx-3 pb-5 mb-5 primaryFont">Services</h3>
					<div class="mx-3 servicesContainer overflow-auto">
						<!-- Services Offered Displayed Here -->
						<% if (services.size() == 0) { %>
						<p class="p-2 text-danger">No services in system. Please try
							again or add a new service.</p>
						<% } else { %>
						<% for (Service service : services) { %>
						<%-- Dummy Modal Service --%>
						<div class="serviceModal p-0">
							<!-- overall -->
							<!-- image -->
							<div class="serviceModalImageWrapper">
								<img class="serviceModalImageImg"
									src="../Image/carouselImage1.jpg" alt="...">
								<%-- still need to add img --%>
								<div class="serviceModalImageGradient h-100"></div>
								<h5
									class="serviceModalImagePrice text-end me-5 pt-2 pe-3 fw-bolder">$<%= service.getPrice() %></h5>
								<h6 class="serviceModalImageText mb-0 pb-0 ps-3 text-start"><%= service.getName() %></h6>
							</div>

							<!-- description -->
							<div class="d-flex flex-column">
								<p class="text-start h-75 overflow-auto"><%= service.getDescription() %></p>
								<div class="d-flex align-items-end justify-content-end mt-auto">
									<button class="btn-primary w-25 h-100" data-bs-toggle="modal"
										data-bs-target="#editService">Edit</button>
								</div>
							</div>
						</div>

						<%-- Edit Service Popup Modal --%>
						<div class="modal fade" id="editService" tabindex="-1"
							aria-labelledby="exampleModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<h1 class="modal-title fs-5" id="exampleModalLabel">Edit
											Service</h1>
										<button type="button" class="btn-close"
											data-bs-dismiss="modal" aria-label="Close"></button>
									</div>

									<!-- edit service modal contents -->
									<form>
										<div class="modal-body">
											<div class="row mb-3">
												<div class="col-3">
													<label for="serviceName">Name:</label>
												</div>
												<div class="col-9">
													<input type="text" class="form-control" id="serviceName"
														name="serviceName"
														placeholder="Write Name of Service Here"
														value="<%= service.getName() %>" required>
												</div>
											</div>

											<div class="row mb-3">
												<div class="col-3">
													<label for="servicePrice">Price:</label>
												</div>
												<div class="col-9">
													<input type="number" class="form-control" id="servicePrice"
														name="servicePrice" min="0" step="0.01" placeholder="0.00"
														value="<%= service.getPrice() %>" required>
												</div>
											</div>

											<div class="row mb-3">
												<div class="col-3">
													<label for="serviceDescription">Description:</label>
												</div>
												<div class="col-9">
													<textarea id="serviceDescription" name="serviceDescription"
														class="form-control" rows="4" cols="50"
														placeholder="Write Description of Service Here"><%= service.getDescription() %></textarea>
												</div>
											</div>

											<div class="row mb-3">
												<div class="col-3">
													<label>Categories:</label>
												</div>
												<div class="col-9">
													<div class="overflow-auto"
														style="max-height: 150px; border: 1px solid #ced4da; padding: 10px; border-radius: 4px;">
														<div>
															<input type="checkbox" id="home" name="serviceCategory"
																value="home"> <label for="home">Home
																Cleaning</label>
														</div>
														<div>
															<input type="checkbox" id="office" name="serviceCategory"
																value="office"> <label for="office">Office
																Cleaning</label>
														</div>
														<div>
															<input type="checkbox" id="tapestry"
																name="serviceCategory" value="tapestry"> <label
																for="tapestry">Tapestry Cleaning</label>
														</div>
													</div>
												</div>
											</div>
										</div>

										<div class="modal-footer">
											<button type="button" class="btn btn-danger"
												data-bs-dismiss="modal">Close</button>
											<input type="submit" class="btn btn-primary"
												data-bs-dismiss="modal" value="Save Changes">
										</div>
									</form>
								</div>
							</div>
						</div>
						<% }} %>

					</div>
				</section>
			</div>
		</div>
	</div>

	<%-- Footer --%>
	<footer>
		<jsp:include page="footer.jsp" />
	</footer>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
</body>
</html>
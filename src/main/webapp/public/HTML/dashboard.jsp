<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="Models.Service.Service"%>
<%@ page import="Models.ServiceCategory.ServiceCategory"%>
<%@ page import="Models.ServiceServiceCategory.ServiceServiceCategory"%>
<%@ page import="Models.User.User"%>
<%@ page import="Models.Role.Role"%>
<%@ page import="Models.ServiceReport.ServiceReport" %>
<%@ page import="Models.ServiceOrderByRating.ServiceByRating" %>
<%@ page import="Models.Booking.Booking" %>
<%@ page import="Models.Booking.BookingDAO" %>
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
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<!-- Corrected Google Fonts link -->
<link
	href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&family=Raleway:ital,wght@0,100..900;1,100..900&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="../CSS/dashboard.css" />
<script defer>
	document.addEventListener('DOMContentLoaded', () => {
	    const servicesbutton = document.getElementById('services-tab');
	    const usersbutton = document.getElementById('users-tab');
	    const bookingbutton = document.getElementById('booking-list-tab');
	    
        const servicesFilter = document.getElementById('services-filter');
        const usersFilter = document.getElementById('users-filter');
        const bookingFilter = document.getElementById('booking-filter');
        
        const filterOptions = document.querySelectorAll('.filterOption');
        
	    servicesbutton.addEventListener('click', () => {
            servicesFilter.classList.remove('d-none');
            
            filterOptions.forEach(option => {
                option.classList.add('d-none');
            });
            
            servicesFilter.classList.remove('d-none');
	    });
	    
	    usersbutton.addEventListener('click', () => {
	        usersFilter.classList.remove('d-none');
            
            filterOptions.forEach(option => {
                option.classList.add('d-none');
            });
            
            usersFilter.classList.remove('d-none');
	    });
	    
	    bookingbutton.addEventListener('click', () => {
	        bookingFilter.classList.remove('d-none');
            
            filterOptions.forEach(option => {
                option.classList.add('d-none');
            });
            
            bookingFilter.classList.remove('d-none');
	    });
	});
</script>
</head>
<body>
	<%
    { // check permission
    	request.setAttribute("pageAccessLevel", "1");
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
	// handle tab
	String dashboardCurrentFocus = (String) session.getAttribute("dashboardCurrentFocus");

	if (dashboardCurrentFocus == null) {
		session.setAttribute("dashboardCurrentFocus", "services-content");
		dashboardCurrentFocus = "services-content";
	}

	// handle data
	List<Service> services = (List<Service>) session.getAttribute("services");
	List<ServiceCategory> categories = (List<ServiceCategory>) session.getAttribute("serviceCategories");
	List<ServiceServiceCategory> relationships = (List<ServiceServiceCategory>) session.getAttribute("allServiceServiceCategories");
	List<User> users = (List<User>) session.getAttribute("users");
	List<Role> roles = (List<Role>) session.getAttribute("roles");
	ServiceCategory currentCategory = (ServiceCategory) session.getAttribute("currentCategory");
	Role currentRole = (Role) session.getAttribute("currentRole");
	List<Booking> bookings = (List<Booking>) session.getAttribute("bookings");

	if (users == null) {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/GetAllUsersServlet");
		dispatcher.forward(request, response);
		return;
	}

	if (roles == null) {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/GetAllRolesServlet");
		dispatcher.forward(request, response);
		return;
	}

	if (relationships == null) {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/GetAllServiceServiceCategoryServlet");
		dispatcher.forward(request, response);
		return;
	}

	if (services == null || categories == null) {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/GetServiceInformationServlet?serviceCategory=0");
		dispatcher.forward(request, response);
		return;
	}
	%>
	<%--Navbar --%>
	<%@ include file="navbar.jsp"%>

	<!-- Main content -->
	<div class="container-fluid d-flex flex-column vh-100 mx-0">
		<div class="row my-5 h-100">
			<%-- Filter Sidepanel --%>
			<div class="col-4">
				<section id="filter" class="border-end h-100">
					<div class="border-bottom mx-3 pb-5 mb-5">
						<ul class="nav nav-pills" id="managementTabs" role="tablist">
							<li class="nav-item" role="presentation">
								<button
									class="nav-link <%=dashboardCurrentFocus == "services-content" ? "active" : ""%>"
									id="services-tab" data-bs-toggle="pill"
									data-bs-target="#services-content" type="button" role="tab">
									<i class="bi bi-gear-fill me-2"></i>Services
								</button>
							</li>
							<li class="nav-item" role="presentation">
								<button
									class="nav-link <%=dashboardCurrentFocus == "users-content" ? "active" : ""%>"
									id="users-tab" data-bs-toggle="pill"
									data-bs-target="#users-content" type="button" role="tab">
									<i class="bi bi-people-fill me-2"></i>Users
								</button>
							</li>
							<li class="nav-item" role="presentation">
								<button class="nav-link" id="reporting-tab"
									data-bs-toggle="pill" data-bs-target="#reporting-content"
									type="button" role="tab">
									<i class="bi bi-bar-chart-line-fill me-2"></i>Service Reporting
								</button>
							</li>
							<li class="nav-item" role="presentation">
								<button class="nav-link" id="service-rating-tab"
									data-bs-toggle="pill" data-bs-target="#service-rating-content"
									type="button" role="tab">
									<i class="bi bi-bar-chart-line-fill me-2"></i>Service Ratings
								</button>
							</li>
							<li class="nav-item" role="presentation">
								<button class="nav-link" id="booking-frequency-tab"
									data-bs-toggle="pill" data-bs-target="#service-demand-content"
									type="button" role="tab">
									<i class="bi bi-bar-chart-line-fill me-2"></i>Service Demand
								</button>
							</li>
							<li class="nav-item" role="presentation">
								<button
									class="nav-link <%= dashboardCurrentFocus.equals("booking-content") ? "active" : "" %>"
									id="booking-list-tab" data-bs-toggle="pill"
									data-bs-target="#booking-content" type="button" role="tab">
									<i class="bi bi-bar-chart-line-fill me-2"></i>Booking List
								</button>
							</li>
						</ul>
					</div>

					<!-- this is container -->
					<div class="mx-3">
					
						<!-- Filter Categories Form Here -->
						<div id="services-filter" class="m-0 p-0 <%= dashboardCurrentFocus == "services-content" ? "d-block" : "d-none" %> filterOption">
							<h4 class="secondaryFont">Service Categories</h4>
							<form class="lh-lg d-flex flex-column filterCategories"
								action="${pageContext.request.contextPath}/GetServiceInformationServlet"
								method="GET">
								<div class="overflow-auto h-75 mb-5 filters">
									<!-- filter for service category -->
									<input type="radio" id="category_0" name="serviceCategory"
										value="0" <%= currentCategory == null ? "checked" : ""%>>
									<label for="category_0"> All Categories </label> <br>
	
									<%
									for (ServiceCategory category : categories) {
									%>
										<input type="radio"
											id="category_<%=category.getService_category_id()%>"
											name="serviceCategory"
											value="<%=category.getService_category_id()%>"
											<%= currentCategory != null && currentCategory.getService_category_id() == category.getService_category_id() ? "checked" : ""%>>
										<label for="category_<%=category.getService_category_id()%>">
											<%=category.getName()%>
										</label> <br>
										<%
									}
									%>
								</div>
	
								<div class="d-flex align-items-end mt-auto">
									<input type="submit" class="btn btn-primary"
										value="Search Filters">
									<button class="btn btn-secondary ms-2" type="button"
										data-bs-toggle="modal" data-bs-target="#categoryModel">
										Edit Category</button>
								</div>
							</form>
						</div>
						
						<!-- for user role -->
						<div id="users-filter" class="m-0 p-0 <%= dashboardCurrentFocus == "users-content" ? "d-block" : "d-none" %> filterOption">
							<h4 class="secondaryFont">User Roles</h4>
							<form class="lh-lg d-flex flex-column filterCategories"
								action="${pageContext.request.contextPath}/GetUsersByRoleServlet"
								method="GET">
								<div class="overflow-auto h-75 mb-5 filters">
									<!-- filter for user role -->
									<%
									for (Role role : roles) {
									%>
										<input type="radio"
											id="role_<%=role.getRole_id()%>"
											name="selectedRole"
											value="<%=role.getRole_id()%>"
											<%=currentRole != null && currentRole.getRole_id() == role.getRole_id() ? "checked" : ""%>>
										<label for="role_<%=role.getRole_id()%>">
											<%=role.getName()%>
										</label> <br>
									<%
									}
									%>
								</div>
	
								<div class="d-flex align-items-end mt-auto">
									<input type="submit" class="btn btn-primary"
										value="Search Filters">
								</div>
							</form>
						</div>
						<%-- 
						<!-- Filter Form (Visible Only When Booking Tab is Active) -->
						<div id="booking-filter"
							class="m-0 p-0 <%= dashboardCurrentFocus.equals("booking-content") ? "d-block" : "d-none" %> filterOption">
							<form method="GET"
								action="${pageContext.request.contextPath}/BookingFilterServlet">
								<label for="filterType">Filter By:</label> <select
									name="filterType" id="filterType">
									<option value="date">Booking Date</option>
									<option value="month">Booking Month</option>
								</select> <input type="text" name="filterValue"
									placeholder="Enter Date (YYYY-MM-DD) or Month (MM)" required>
								<input type="submit" class="btn btn-primary"
										value="Apply Filters">
							</form>
						</div>
						--%>
					</div>

					<%-- edit category information --%>
					<div class="modal fade" id="categoryModel" tabindex="-1"
						aria-labelledby="categoryModelLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title primaryFont" id="categoryModelLabel">Edit
										Category Information</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"
										aria-label="Close"></button>
								</div>

								<%-- Edit Category Modal --%>
								<div class="modal-body p-0">
									<div class="container-fluid"
										style="max-height: 70vh; overflow-y: auto;">
										<div class="mt-3 secondaryFont">Add Category</div>
										<!-- Add new category -->
										<form class="category-form py-3 border-bottom" method="POST">
											<!-- Category Name Row -->
											<div class="row align-items-center mb-3">
												<div class="col-1 d-flex justify-content-center"></div>
												<div class="col-10">
													<input type="text" class="form-control bg-light"
														id="categoryName0" name="categoryName"
														placeholder="Write Name of Category Here"
														value="Example Default Name" required>
												</div>
												<div class="col-1 d-flex justify-content-center">
													<button type="submit" name="action" value="update"
														class="btn btn-link p-0 add-btn"
														formaction="<%=request.getContextPath()%>/EditServiceCategoryServlet?action=create">
														<i class="bi bi-plus-circle text-primary fs-5"></i>
													</button>
												</div>
											</div>

											<!-- Category Description Row -->
											<div class="row">
												<div class="col-1"></div>
												<div class="col-10">
													<textarea class="form-control bg-light"
														id="categoryDescription0" name="categoryDescription"
														placeholder="Write Description Here" rows="4" required>Example Default Description</textarea>
												</div>
												<div class="col-1"></div>
											</div>
										</form>
										<!-- End of add category -->

										<div class="mt-3 secondaryFont">Edit Categories</div>
										<%-- List of categories available to edit --%>
										<%
										for (ServiceCategory category : categories) {
										%>
										<!-- Individual Category Form -->
										<form class="category-form py-3 border-bottom" method="POST">
											<input type="hidden"
												id="categoryId<%=category.getService_category_id()%>"
												name="categoryId"
												value="<%=category.getService_category_id()%>">
											<!-- Category Name Row -->
											<div class="row align-items-center mb-3">
												<div class="col-1 d-flex justify-content-center">
													<button type="submit" name="action" value="delete"
														class="btn btn-link p-0 delete-btn"
														formaction="<%=request.getContextPath()%>/EditServiceCategoryServlet?action=delete">
														<i class="bi bi-trash3-fill text-danger fs-5"></i>
													</button>
												</div>
												<div class="col-10">
													<input type="text" class="form-control"
														id="categoryName<%=category.getService_category_id()%>"
														name="categoryName"
														placeholder="Write Name of Category Here"
														value="<%=category.getName()%>" required>
												</div>
												<div class="col-1 d-flex justify-content-center">
													<button type="submit" name="action" value="update"
														class="btn btn-link p-0 save-btn"
														formaction="<%=request.getContextPath()%>/EditServiceCategoryServlet?action=update">
														<i class="bi bi-floppy-fill text-success fs-5"></i>
													</button>
												</div>
											</div>

											<!-- Category Description Row -->
											<div class="row">
												<div class="col-1"></div>
												<div class="col-10">
													<textarea class="form-control"
														id="categoryDescription<%=category.getService_category_id()%>"
														name="categoryDescription"
														placeholder="Write Description Here" rows="4" required><%=category.getDescription()%></textarea>
												</div>
												<div class="col-1"></div>
											</div>
										</form>
										<!-- End of Individual Category Form -->
										<%
										}
										%>
									</div>
								</div>

								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-bs-dismiss="modal">Close</button>
								</div>
							</div>
						</div>
					</div>

				</section>
			</div>

			<%-- Display Services --%>
			<div class="col-8 pr-5 ml-5 tab-content">
				<%-- =================
					Services Section 
					================== --%>
				<section
					class="h-100 tab-pane fade <%=dashboardCurrentFocus == "services-content" ? "show active" : ""%>"
					id="services-content" role="tabpanel">
					<!-- Service Title -->
					<div
						class="border-bottom mx-3 pb-5 mb-5 d-flex justify-content-between">
						<h3 class="m-0 p-0 primaryFont">
							All
							<%
						if (currentCategory != null) {
							out.print(currentCategory.getName() + " ");
						}
						%>
							Services
						</h3>

						<%-- Add Service Button --%>
						<button type="button"
							class="btn btn-primary d-flex align-items-center rounded"
							data-bs-toggle="modal" data-bs-target="#addServiceModal">
							Add Service <span class="ms-2"><i
								class="m-0 p-0 bi bi-plus-circle"></i></span>
						</button>

						<%-- Add Service Modal --%>
						<div class="modal fade" id="addServiceModal" tabindex="-1"
							aria-labelledby="addServiceModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title primaryFont" id="exampleModalLabel">Add
											Service</h5>
										<button type="button" class="btn-close"
											data-bs-dismiss="modal" aria-label="Close"></button>
									</div>

									<!-- Add Service Modal Body Here -->
									<form id="addServiceForm" method="POST"
										action="${pageContext.request.contextPath}/EditServiceServlet?action=create">
										<div class="modal-body">
											<div class="row mb-3">
												<div class="col-3">
													<label for="addServiceName">Name:</label>
												</div>
												<div class="col-9">
													<input type="text" class="form-control" id="addServiceName"
														name="addServiceName"
														placeholder="Write Name of Service Here"
														value="Example Default Name" required>
												</div>
											</div>

											<div class="row mb-3">
												<div class="col-3">
													<label for="addServicePrice">Price:</label>
												</div>
												<div class="col-9">
													<input type="number" class="form-control"
														id="addServicePrice" name="addServicePrice" min="0"
														step="0.01" placeholder="0.00" value="0.00" required>
												</div>
											</div>

											<div class="row mb-3">
												<div class="col-3">
													<label for="addServiceDescription">Description:</label>
												</div>
												<div class="col-9">
													<textarea id="addServiceDescription"
														name="addServiceDescription" class="form-control" rows="4"
														cols="50" placeholder="Write Description of Service Here">Example Default Description</textarea>
												</div>
											</div>

											<!-- add Service Form, select which categories to associate -->
											<div class="row mb-3">
												<div class="col-3">
													<label>Categories:</label>
												</div>
												<div class="col-9">
													<div class="overflow-auto"
														style="max-height: 150px; border: 1px solid #ced4da; padding: 10px; border-radius: 4px;">
														<%
														for (ServiceCategory category : categories) {
														%>
														<div>
															<input type="checkbox"
																id="addServiceCategory<%=category.getService_category_id()%>"
																name="addServiceCategory"
																value="<%=category.getService_category_id()%>">
															<label
																for="addServiceCategory<%=category.getService_category_id()%>"><%=category.getName()%></label>
														</div>
														<%
														}
														%>
													</div>
												</div>
											</div>
										</div>

										<div class="modal-footer">
											<button type="button" class="btn btn-secondary"
												data-bs-dismiss="modal">Close</button>
											<button type="submit" id="addServiceCreate"
												class="btn btn-primary">Create</button>
										</div>
									</form>
									<%-- End of add service form --%>

								</div>
							</div>
						</div>
					</div>

					<!-- Display Service -->
					<div class="mx-3 servicesContainer overflow-auto">
						<!-- Services Offered Displayed Here -->
						<%
						if (services.size() == 0) {
						%>
						<p class="p-2 text-danger">No services in system. Please try
							again or add a new service.</p>
						<%
						} else {
						%>
						<%
						for (Service service : services) {
						%>
						<%-- Dummy Modal Service --%>
						<div class="serviceModal p-0 rounded">
							<!-- overall -->
							<!-- image -->
							<div class="serviceModalImageWrapper">
								<img class="serviceModalImageImg"
									src="<%=service.getService_photo_url()%>" alt="...">
								<%-- still need to add img --%>
								<div class="serviceModalImageGradient h-100"></div>
								<h5
									class="serviceModalImagePrice text-end me-5 pt-2 pe-3 fw-bolder">
									$<%=String.format("%.2f", service.getPrice())%></h5>
								<h6 class="serviceModalImageText mb-0 pb-0 ps-3 text-start"><%=service.getName()%></h6>
							</div>

							<!-- description -->
							<div class="d-flex flex-column">
								<p class="text-start h-75 overflow-auto"><%=service.getDescription()%></p>
								<div class="d-flex align-items-end justify-content-end mt-auto">
									<button class="btn-primary w-25 h-100" data-bs-toggle="modal"
										data-bs-target="#editService<%=service.getService_id()%>">Edit</button>
								</div>
							</div>
						</div>

						<%-- Edit Service Popup Modal --%>
						<div class="modal fade"
							id="editService<%=service.getService_id()%>" tabindex="-1"
							aria-labelledby="exampleModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<h1 class="modal-title fs-5 primaryFont"
											id="exampleModalLabel">Edit Service</h1>
										<button type="button" class="btn-close"
											data-bs-dismiss="modal" aria-label="Close"></button>
									</div>

									<!-- edit service modal contents -->
									<form class="editServiceForm"
										action="<%=request.getContextPath()%>/EditServiceServlet?action=update"
										method="POST">
										<input type="hidden" name="serviceId"
											value="<%=service.getService_id()%>">
										<div class="modal-body">
											<div class="row mb-3">
												<div class="col-3">
													<label for="serviceName">Name:</label>
												</div>
												<div class="col-9">
													<input type="text" class="form-control"
														id="serviceName<%=service.getService_id()%>"
														name="serviceName"
														placeholder="Write Name of Service Here"
														value="<%=service.getName()%>" required>
												</div>
											</div>

											<div class="row mb-3">
												<div class="col-3">
													<label for="servicePrice">Price:</label>
												</div>
												<div class="col-9">
													<input type="number" class="form-control"
														id="servicePrice<%=service.getService_id()%>"
														name="servicePrice" min="0" step="0.01" placeholder="0.00"
														value="<%=service.getPrice()%>" required>
												</div>
											</div>

											<div class="row mb-3">
												<div class="col-3">
													<label for="serviceDescription">Description:</label>
												</div>
												<div class="col-9">
													<textarea
														id="serviceDescription<%=service.getService_id()%>"
														name="serviceDescription" class="form-control" rows="4"
														cols="50" placeholder="Write Description of Service Here"><%=service.getDescription()%></textarea>
												</div>
											</div>

											<!-- categories stuff -->
											<div class="row mb-3">
												<div class="col-3">
													<label>Categories:</label>
												</div>
												<div class="col-9">
													<div class="overflow-auto"
														style="max-height: 150px; border: 1px solid #ced4da; padding: 10px; border-radius: 4px;">
														<%
														for (ServiceCategory category : categories) {
														%>
														<%
														boolean isRelationship = false;
														for (ServiceServiceCategory relationship : relationships) {
															if (relationship.getService_id() == service.getService_id()
															&& relationship.getService_category_id() == category.getService_category_id()) {
																isRelationship = true;
															}
														}
														%>
														<%=isRelationship
		? "<input type='hidden' name='serviceCategoryRelationship' value='" + category.getService_category_id() + "'>"
		: ""%>
														<div>

															<input type="checkbox"
																id="serviceCategory<%=category.getService_category_id()%>"
																name="serviceCategory"
																value="<%=category.getService_category_id()%>"
																<%=isRelationship ? "checked" : ""%>> <label
																for="home"><%=category.getName()%></label>
														</div>
														<%
														}
														%>

													</div>
												</div>
											</div>
											<!-- end of categories stuff -->
										</div>

										<div class="modal-footer justify-content-between">
											<button type="submit"
												class="btn btn-danger d-flex text-center align-items-center rounded"
												formaction="<%=request.getContextPath()%>/EditServiceServlet?action=delete">
												<span class="me-2"><i
													class="m-0 p-0 bi bi-trash3-fill"></i></span> Delete
											</button>
											<input type="submit" class="btn btn-primary"
												data-bs-dismiss="modal" value="Save Changes">
										</div>
									</form>
									<!-- end of edit service modal contents -->
								</div>
							</div>
						</div>
						<%
						}
						}
						%>

					</div>
				</section>

				<%-- =================
					Users Section 
					================== --%>
				<section
					class="h-100 tab-pane fade <%=dashboardCurrentFocus == "users-content" ? "show active" : ""%>"
					id="users-content" role="tabpanel">
					<div
						class="border-bottom mx-3 pb-5 mb-5 d-flex justify-content-between">
						<h3 class="m-0 p-0 primaryFont">All Users</h3>

						<%-- Add User Button 
						<button class="btn btn-primary" data-bs-toggle="modal"
							data-bs-target="#addUserModal">
							<i class="bi bi-person-plus-fill me-2"></i>Add User
						</button> --%>
					</div>

					<!-- User Table -->
					<div class="table-container"
						style="height: 70vh; overflow-y: auto;">
						<table class="table table-striped table-hover">
							<thead class="sticky-top border">
								<tr>
									<th class="bg-white"
										style="position: sticky; top: 0; z-index: 1;">ID</th>
									<th class="bg-white"
										style="position: sticky; top: 0; z-index: 1;">Name</th>
									<th class="bg-white"
										style="position: sticky; top: 0; z-index: 1;">Email</th>
									<th class="bg-white"
										style="position: sticky; top: 0; z-index: 1;">Gender</th>
									<th class="bg-white"
										style="position: sticky; top: 0; z-index: 1;">Role</th>
									<th class="bg-white"
										style="position: sticky; top: 0; z-index: 1;">Status</th>
									<th class="bg-white"
										style="position: sticky; top: 0; z-index: 1;">Actions</th>
								</tr>
							</thead>
							<tbody>
								<!-- dummy row -->
								<%
								for (User user : users) {
								%>
								<tr>
									<td><%=user.getUser_id()%></td>
									<td><%=user.getName()%></td>
									<td><%=user.getEmail()%></td>
									<td><%=user.getGender()%></td>
									<td>
										<%
										boolean roleExists = false;
										for (Role role : roles) {
											if (role.getRole_id() == user.getRole_id()) {
												out.print(role.getName());
												roleExists = true;
											}
										}
										if (!roleExists) {
											out.print("<div class='text-danger'>Unassigned<div>");
										}
										%>
									</td>
									<td><span class="badge bg-success">Active</span></td>
									<td>
										<!-- view profile photo -->
										<button class="btn btn-sm btn-outline-secondary me-2"
											data-bs-toggle="modal"
											data-bs-target="#profilePhoto<%=user.getUser_id()%>">
											<i class="bi bi-person-bounding-box"></i>
										</button>
										<button class="btn btn-sm btn-outline-primary me-2"
											data-bs-toggle="modal"
											data-bs-target="#editUser<%=user.getUser_id()%>">
											<i class="bi bi-pencil-fill"></i>
										</button>
										<form class="m-0 d-inline" method="POST"
											action="<%=request.getContextPath()%>/DeleteUserServlet">
											<input type="hidden" name="input_user_id"
												value="<%=user.getUser_id()%>">
											<button type="submit" class="btn btn-sm btn-outline-danger">
												<i class="bi bi-trash-fill"></i>
											</button>
										</form>
									</td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>
					</div>

					<!-- User Content Modals -->
					<%
					for (User user : users) {
					%>
					<!-- profile photo modal -->
					<div class="modal fade" id="profilePhoto<%=user.getUser_id()%>"
						tabindex="-1" aria-labelledby="exampleModalLabel"
						aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel"><%=user.getName()%>'s
										Profile Photo
									</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"
										aria-label="Close"></button>
								</div>

								<div
									class="profile-photo-container my-4 d-flex justify-content-center">
									<img
										src="<%=user.getProfile_photo_url() != null ? user.getProfile_photo_url() : "../Image/defaultpic.png"%>"
										alt="Profile Photo"
										class="rounded-circle shadow-sm img-thumbnail"
										style="width: 200px; height: 200px; object-fit: cover;">
								</div>

								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-bs-dismiss="modal">Close</button>
								</div>
							</div>
						</div>
					</div>

					<!-- edit user modal -->
					<div class="modal fade" id="editUser<%=user.getUser_id()%>"
						tabindex="-1" aria-labelledby="exampleModalLabel"
						aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h1 class="modal-title fs-5 primaryFont">Edit User
										Information</h1>
									<button type="button" class="btn-close" data-bs-dismiss="modal"
										aria-label="Close"></button>
								</div>

								<!-- edit service modal contents -->
								<form class="editServiceForm"
									action="<%=request.getContextPath()%>/UpdateUserServlet"
									method="POST">
									<input type="hidden" name="input_user_id"
										value="<%=user.getUser_id()%>">
									<div class="modal-body">
										<!-- Name Field -->
										<div class="mb-3">
											<label for="input_name" class="form-label">Name</label> <input
												type="text" class="form-control" id="input_name"
												name="input_name" value="<%=user.getName()%>" required>
										</div>

										<!-- Email Field -->
										<div class="mb-3">
											<label for="input_email" class="form-label">Email</label> <input
												type="email" class="form-control" id="input_email"
												name="input_email" value="<%=user.getEmail()%>" required>
										</div>

										<!-- Gender Field -->
										<div class="mb-3">
											<label for="input_gender" class="form-label">Gender
												(M/F/N)</label> <input type="text" class="form-control"
												id="input_gender" name="input_gender"
												value="<%=user.getGender()%>" maxlength="1" pattern="[MFN]"
												required>
										</div>

										<!-- Role ID Field -->
										<div class="mb-3">
											<label for="input_role_id" class="form-label">Role ID</label>
											<select class="form-control" id="input_role_id"
												name="input_role_id" required>
												<%
												for (Role role : roles) {
												%>
												<option value="<%=role.getRole_id()%>"
													<%=user.getRole_id() == role.getRole_id() ? "selected" : ""%>><%=role.getName()%></option>
												<%
												}
												%>
											</select>
										</div>

										<!-- Profile Photo URL Field -->
										<div class="mb-3">
											<label for="input_profile_photo_url" class="form-label">Profile
												Photo URL</label> <input type="text" class="form-control"
												id="input_profile_photo_url" name="input_profile_photo_url"
												value="<%=user.getProfile_photo_url()%>">
										</div>
									</div>

									<div class="modal-footer justify-content-between">
										<button type="button" class="btn btn-secondary"
											data-bs-dismiss="modal">Close</button>
										<input type="submit" class="btn btn-primary"
											data-bs-dismiss="modal" value="Save Changes">
									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- end of edit user modal contents -->
					<%
					}
					%>
				</section>

				<section class="h-100 tab-pane fade"
					id="reporting-content" role="tabpanel">
					<div
						class="border-bottom mx-3 pb-5 mb-5 d-flex justify-content-between">
						<h3 class="m-0 p-0 primaryFont">Service Reporting</h3>
					</div>

					<form action="<%=request.getContextPath()%>/ServiceReportServlet"
						method="get">
						<div class="mb-4">
							<button type="submit" class="btn btn-primary">Fetch
								Service Reports
							</button>
						</div>
					</form>

					<div class="table-container"
						style="height: 70vh; overflow-y: auto;">
						<table class="table table-striped table-hover">
							<thead class="sticky-top border">
								<tr>
									<th class="bg-white">Service ID</th>
									<th class="bg-white">Service Name</th>
									<th class="bg-white">Total Bookings</th>
									<th class="bg-white">Total Revenue</th>
								</tr>
							</thead>
							<tbody>
								<%
								List<ServiceReport> serviceReports = (List<ServiceReport>) request.getSession().getAttribute("serviceReports");
								if (serviceReports != null && !serviceReports.isEmpty()) {
									for (ServiceReport report : serviceReports) {
								%>
								<tr>
									<td><%=report.getServiceId()%></td>
									<td><%=report.getServiceName()%></td>
									<td><%=report.getTotalBookings()%></td>
									<td>$<%=String.format("%.2f", report.getTotalRevenue())%></td>
								</tr>
								<%
								}
								} else {
								%>
								<tr>
									<td colspan="4" class="text-center">No report data
										available.</td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>
					</div>
				</section>

				<section class="h-100 tab-pane fade" id="service-rating-content" role="tabpanel">
					<div
						class="border-bottom mx-3 pb-5 mb-5 d-flex justify-content-between">
						<h3 class="m-0 p-0 primaryFont">Service Ratings</h3>
					</div>

					<form
						action="<%=request.getContextPath()%>/ServiceRatingOrderServlet"
						method="get">
						<div class="mb-4">
							<button type="submit" class="btn btn-primary">Fetch
								Service Ratings</button>
						</div>
					</form>

					<div class="table-container"
						style="height: 70vh; overflow-y: auto;">
						<table class="table table-striped table-hover">
							<thead class="sticky-top border">
								<tr>
									<th class="bg-white">Service ID</th>
									<th class="bg-white">Service Name</th>
									<th class="bg-white">Average Rating</th>
								</tr>
							</thead>
							<tbody>
								<%
								List<ServiceByRating> serviceRatings = (List<ServiceByRating>) session.getAttribute("serviceRatingOrder");
								if (serviceRatings != null && !serviceRatings.isEmpty()) {
									for (ServiceByRating rating : serviceRatings) {
								%>
								<tr>
									<td><%=rating.getId()%></td>
									<td><%=rating.getName()%></td>
									<td><%=String.format("%.2f", rating.getAverageRating())%></td>
								</tr>
								<%
								}
								} else {
								%>
								<tr>
									<td colspan="5" class="text-center">No report data
										available.</td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>
					</div>
				</section>
				
				<section class="h-100 tab-pane fade" id="service-demand-content" role="tabpanel">
					<div
						class="border-bottom mx-3 pb-5 mb-5 d-flex justify-content-between">
						<h3 class="m-0 p-0 primaryFont">Service Demand</h3>
					</div>

					<form
						action="<%=request.getContextPath()%>/ServiceDemandServlet"
						method="get">
						<div class="mb-4">
							<button type="submit" class="btn btn-primary">Fetch
								Service Demands</button>
						</div>
					</form>

					<div class="table-container"
						style="height: 70vh; overflow-y: auto;">
						<table class="table table-striped table-hover">
							<thead class="sticky-top border">
								<tr>
									<th class="bg-white">Service ID</th>
									<th class="bg-white">Service Name</th>
									<th class="bg-white">Booking Count</th>
								</tr>
							</thead>
							<tbody>
								<%
								List<Service> serviceDemands = (List<Service>) session.getAttribute("bookingFrequency");
								if (serviceDemands != null && !serviceDemands.isEmpty()) {
									for (Service demand : serviceDemands) {
								%>
								<tr>
									<td><%=demand.getService_id()%></td>
									<td><%=demand.getName()%></td>
									<td><%=demand.getBooking_count() %></td>
								</tr>
								<%
								}
								} else {
								%>
								<tr>
									<td colspan="5" class="text-center">No report data
										available.</td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>
					</div>
				</section>

				<%
				// Initialize page number and size for pagination
				int pageNumber = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
				int pageSize = 5;

				// Get filter values (if any)
				String filterType = request.getParameter("filterType");
				String filterValue = request.getParameter("filterValue");

				// Fetch booking details from the DAO
				BookingDAO bookingDAO = new BookingDAO();
				List<Booking> bookingList;
				int totalRecords;

				// Apply filters if provided, else get all bookings
				if (filterType != null && filterValue != null && !filterValue.isEmpty()) {
					bookingList = bookingDAO.getFilteredBookings(filterType, filterValue, pageNumber, pageSize);
					totalRecords = bookingDAO.getTotalFilteredBookings(filterType, filterValue);
				} else {
					bookingList = bookingDAO.getBookingDetailsAdmin(pageNumber, pageSize);
					totalRecords = bookingDAO.getTotalBookings();
				}

				// Calculate total pages
				int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

				// Calculate records range for display
				int startRecord = (pageNumber - 1) * pageSize + 1;
				int endRecord = Math.min(startRecord + pageSize - 1, totalRecords);
				%>

				<!-- Filter Section -->
				<div id="booking-filter"
					class="m-0 p-0 <%=dashboardCurrentFocus.equals("booking-content") ? "d-block" : "d-none"%> filterOption">
					<form method="GET"
						action="${pageContext.request.contextPath}/BookingFilterServlet">
						<label for="filterType">Filter By:</label> <select
							name="filterType" id="filterType">
							<option value="date"
								<%=filterType != null && filterType.equals("date") ? "selected" : ""%>>Booking
								Date</option>
							<option value="month"
								<%=filterType != null && filterType.equals("month") ? "selected" : ""%>>Booking
								Month</option>
						</select> <input type="text" name="filterValue"
							placeholder="Enter Date (YYYY-MM-DD) or Month (MM)"
							value="<%=filterValue != null ? filterValue : ""%>" required>
						<input type="submit" class="btn btn-primary" value="Apply Filters">
					</form>
				</div>

				<!-- Booking Table Section -->
				<section
					class="h-100 tab-pane fade <%=dashboardCurrentFocus.equals("booking-content") ? "show active" : ""%>"
					id="booking-content" role="tabpanel">
					<div class="table-container">
						<table class="table table-striped table-hover">
							<thead>
								<tr>
									<th>Customer Name</th>
									<th>Customer Email</th>
									<th>Booking Date</th>
									<th>Booking Period</th>
									<th>Service Name</th>
									<th>Service Price</th>
								</tr>
							</thead>
							<tbody>
								<%
								if (bookingList != null && !bookingList.isEmpty()) {
									for (Booking booking : bookingList) {
								%>
								<tr>
									<td><%=booking.getUsername()%></td>
									<td><%=booking.getUserEmail()%></td>
									<td><%=booking.getBooking_date()%></td>
									<td><%=booking.getBookingPeriod()%></td>
									<td><%=booking.getServiceName()%></td>
									<td>$<%=String.format("%.2f", booking.getServicePrice())%></td>
								</tr>
								<%
								}
								} else {
								%>
								<tr>
									<td colspan="6">No bookings found.</td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>
					</div>

					<!-- Pagination Section -->
					<div class="pagination-container">
						<div class="results-info">
							Showing
							<%=startRecord%>
							to
							<%=endRecord%>
							of
							<%=totalRecords%>
							entries
						</div>
						<nav aria-label="Page navigation">
							<ul class="pagination">
								<!-- Previous Page -->
								<li class="page-item <%=pageNumber == 1 ? "disabled" : ""%>">
									<a class="page-link"
									href="?page=<%=pageNumber - 1%>&filterType=<%=filterType%>&filterValue=<%=filterValue%>">&laquo;</a>
								</li>
								<%
								// Dynamic page links logic (ensure no index out of bounds)
								int startPage = Math.max(1, pageNumber - 2);
								int endPage = Math.min(startPage + 4, totalPages);
								for (int i = startPage; i <= endPage; i++) {
								%>
								<li class="page-item <%=pageNumber == i ? "active" : ""%>">
									<a class="page-link"
									href="?page=<%=i%>&filterType=<%=filterType%>&filterValue=<%=filterValue%>"><%=i%></a>
								</li>
								<%
								}
								%>
								<!-- Next Page -->
								<li
									class="page-item <%=pageNumber == totalPages ? "disabled" : ""%>">
									<a class="page-link"
									href="?page=<%=pageNumber + 1%>&filterType=<%=filterType%>&filterValue=<%=filterValue%>">&raquo;</a>
								</li>
							</ul>
						</nav>
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
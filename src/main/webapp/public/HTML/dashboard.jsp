<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Service"%>
<%@ page import="model.ServiceCategory"%>
<%@ page import="model.ServiceServiceCategory"%>
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
</head>
<body>
	<%
	List<Service> services = (List<Service>) session.getAttribute("services");
	List<ServiceCategory> categories = (List<ServiceCategory>) session.getAttribute("serviceCategories");
	List<ServiceServiceCategory> relationships = (List<ServiceServiceCategory>) session.getAttribute("allServiceServiceCategories");
	ServiceCategory currentCategory = (ServiceCategory) session.getAttribute("currentCategory");
	
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
								<input type="radio" id="category_0" name="serviceCategory"
									value="0" <%=currentCategory == null ? "checked" : ""%>>
								<label for="category_0"> All Categories </label> <br>

								<%
								for (ServiceCategory category : categories) {
								%>
								<input type="radio"
									id="category_<%=category.getService_category_id()%>"
									name="serviceCategory"
									value="<%=category.getService_category_id()%>"
									<%=currentCategory != null && currentCategory.getService_category_id() == category.getService_category_id()
		? "checked"
		: ""%>>
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
			<div class="col-9 pr-5 ml-5">
				<section id="services" class="h-100">
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
														<% for (ServiceCategory category: categories) { %>
														<div>
															<input type="checkbox" id="addServiceCategory<%= category.getService_category_id() %>"
																name="addServiceCategory" value="<%= category.getService_category_id() %>"> <label
																for="addServiceCategory<%= category.getService_category_id() %>"><%= category.getName() %></label>
														</div>
														<% } %>
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
									$<%= String.format("%.2f", service.getPrice()) %></h5>
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
									<form class="editServiceForm" action="<%=request.getContextPath()%>/EditServiceServlet?action=update" method="POST">
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
														<% for (ServiceCategory category: categories) { %>
														<%
															boolean isRelationship = false;
															for (ServiceServiceCategory relationship : relationships) {
																if (relationship.getService_id() == service.getService_id() && relationship.getService_category_id() == category.getService_category_id()) {
																	isRelationship = true;
																}
															}
														%>
														<%= isRelationship ? "<input type='hidden' name='serviceCategoryRelationship' value='" + category.getService_category_id() + "'>" : "" %>
														<div>
															
															<input type="checkbox" id="serviceCategory<%= category.getService_category_id() %>" name="serviceCategory"
																value="<%= category.getService_category_id() %>" <%= isRelationship ? "checked" : "" %>> <label for="home"><%= category.getName() %></label>
														</div>
														<% } %>
														
													</div>
												</div>
											</div>
											<!-- end of categories stuff -->
										</div>

										<div class="modal-footer">
											<button type="submit" class="btn btn-danger" formaction="<%=request.getContextPath()%>/EditServiceServlet?action=delete">Delete</button>
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
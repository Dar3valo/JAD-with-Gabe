<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Terms and Conditions - AllClean Cleaning Services</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
    <link rel="stylesheet" href="../CSS/termsAndCondition.css" />
</head>
<body>
	<%
	{ // check permission
		request.setAttribute("pageAccessLevel", "3");
		RequestDispatcher rd = application.getRequestDispatcher("/checkAccessServlet"); // Use absolute path
		rd.include(request, response);

		Boolean hasAccess = (Boolean) session.getAttribute("accessCheckResult");

		if (hasAccess == null || !hasAccess) {
			response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
			return;
		}
	}
	%>

	<jsp:include page="navbar.jsp" />

    <div class="container py-5">
        <div class="terms-wrapper bg-white rounded-3 shadow-sm">
            <div class="terms-header text-center py-4">
                <h1 class="display-5 fw-bold text-dark mb-3">Terms and Conditions</h1>
                <p class="lead text-muted">AllClean Cleaning Services</p>
            </div>

            <div class="terms-content px-4 px-md-5">
                <div class="row justify-content-center">
                    <div class="col-12 col-lg-10">
                        <div class="accordion mt-4" id="termsAccordion">
                            <!-- Services Provided -->
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="heading1">
                                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapse1" aria-expanded="true" aria-controls="collapse1">
                                        1. Services Provided
                                    </button>
                                </h2>
                                <div id="collapse1" class="accordion-collapse collapse show" aria-labelledby="heading1" data-bs-parent="#termsAccordion">
                                    <div class="accordion-body">
										<p class="lead">AllClean Cleaning Services offers various
											cleaning solutions, including residential and commercial
											cleaning services, window cleaning, deep cleaning, etc. The
											scope of services and specifics will be agreed upon during
											the booking process.
										</p>
									</div>
                                </div>
                            </div>

                            <!-- Booking and Payment -->
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="heading2">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse2" aria-expanded="false" aria-controls="collapse2">
                                        2. Booking and Payment
                                    </button>
                                </h2>
                                <div id="collapse2" class="accordion-collapse collapse" aria-labelledby="heading2" data-bs-parent="#termsAccordion">
                                    <div class="accordion-body">
                                        <h5 class="mb-3">Booking</h5>
                                        <p class="lead">Service bookings can be made through our website, mobile app, or by calling our customer service team. Bookings are subject to availability and confirmation.</p>
                                        <h5 class="mb-3">Payment</h5>
                                        <p class="lead">Full payment is required at the time of booking unless otherwise agreed upon. We accept multiple forms of payment, including credit/debit cards, online transfers, and cash payments (where applicable).</p>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- Service Delivery -->
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="heading3">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse3" aria-expanded="false" aria-controls="collapse3">
                                        3. Service Delivery
                                    </button>
                                </h2>
                                <div id="collapse3" class="accordion-collapse collapse" aria-labelledby="heading3" data-bs-parent="#termsAccordion">
                                    <div class="accordion-body">
										<p class="lead">Our professional cleaning team will arrive at the
											scheduled time and location. We maintain high standards of
											service delivery and ensure all cleaning tasks are performed
											according to industry best practices.
										</p>
									</div>
                                </div>
                            </div>
                            
                            <!-- Cancellation Policy -->
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="heading4">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse4" aria-expanded="false" aria-controls="collapse4">
                                        4. Cancellation Policy
                                    </button>
                                </h2>
                                <div id="collapse4" class="accordion-collapse collapse" aria-labelledby="heading4" data-bs-parent="#termsAccordion">
                                    <div class="accordion-body">
										<p class="lead">Cancellations must be made at least 24
											hours before the scheduled service. Late cancellations may
											incur a fee. Please refer to our cancellation policy for
											detailed information.
										</p>
									</div>
                                </div>
                            </div>

                        </div>

                        <div class="text-center mt-5">
                            <p class="text-muted small">Last updated: January 2025</p>
                            <div class="d-flex justify-content-center gap-3 mb-3">
                                <button class="btn btn-outline-dark" onclick="window.print()">
                                    <i class="bi bi-printer me-2"></i>Print
                                </button>
                            </div>
                        </div>	
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
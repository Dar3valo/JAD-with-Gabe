<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Models.Feedback.Feedback" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>AllClean</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
<link rel="stylesheet" href="../CSS/homeStyle.css">
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
	
	<!-- Add this at the start of your homePage.jsp to verify the servlet is being called -->
	<%
	request.getRequestDispatcher("/TopThreeFeedbackServlet").include(request, response);
	%>
	<%--Navbar --%>
	<jsp:include page="navbar.jsp" />

	<%-- Showcase --%>
	<section class="bg-dark text-light p-5 text-center text-sm-start">
		<div class="container">
			<div class="d-sm-flex align-items-center justify-content-between">
				<div>
					<h1>Book Now For Professional Cleaning!</h1>
					<p class="lead my-4">Professional house cleaning services with
						flexible schedule and booking slots Sign up to book our services now!</p>
					<a class="btn btn-primary btn-lg rounded" href="register.jsp">Register</a>
				</div>
				<img class="img-fluid w-50 d-none d-sm-block rounded" alt=""
					src="../Image/types-of-house-cleaning-services-to-offer.jpg">
			</div>
		</div>
	</section>

	<%--Boxes --%>
	<section class="p-5">
		<div class="container">
			<h1>
				<strong>Experience AllClean Benefits</strong>
			</h1>
			<br>
			<div class="row text-center d-flex align-items-stretch g-4">
				<%--This makes the columns stretch to the same height --%>
				<div class="col-md">
					<div class="card bg-dark text-light h-100">
						<%-- Set the height requirements of card to 100% and ensure card fills up the 100% space --%>
						<div class="card-body text-center d-flex flex-column">
							<%-- Allow the contents of the card to stack vertically, improving readability --%>
							<div class="h1 mb-3">
								<i class="bi bi-calendar-week"> Flexible Scheduling</i>
							</div>
							<p class="card-text lead my-3">Our timeslot options fit into
								anyone's lifestyle. Get weekly part time maid with flexible
								terms, or last minute availability on ad hoc sessions when you
								book with AllClean.</p>
						</div>
					</div>
				</div>
				<div class="col-md">
					<div class="card bg-dark text-light h-100">
						<div class="card-body text-center d-flex flex-column">
							<div class="h1 mb-3">
								<i class="bi bi-bar-chart-line"> Expert Cleaners</i>
							</div>
							<p class="card-text lead my-3">Our customers love our
								cleaners! All our workers are full time AllClean employees, and
								they undergo training to ensure they deliver our signature
								quality.</p>
						</div>
					</div>
				</div>
				<div class="col-md">
					<div class="card bg-dark text-light h-100">
						<div class="card-body text-center d-flex flex-column">
							<div class="h1 mb-3">
								<i class="bi bi-hourglass-split"> Time Efficient</i>
							</div>
							<p class="card-text lead my-3">Get a spotless space without
								wasting your time. Our efficient, high-quality cleaning service
								fits your schedule, so you can focus on what matters most. Let
								us handle the cleaning, so you don’t have to.</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<%-- Carousel --%>
	<section class="p-5 bg-dark">
		<div id="allCleanCarousel" class="carousel slide"
			data-bs-ride="carousel">
			<div class="carousel-indicators">
				<button type="button" data-bs-target="#allCleanCarousel"
					data-bs-slide-to="0" class="active" aria-current="true"
					aria-label="Slide 1"></button>
				<button type="button" data-bs-target="#allCleanCarousel"
					data-bs-slide-to="1" aria-label="Slide 2"></button>
				<button type="button" data-bs-target="#allCleanCarousel"
					data-bs-slide-to="2" aria-label="Slide 3"></button>
			</div>

			<div class="carousel-inner">
				<div class="carousel-item active c-item">
					<img src="../Image/carouselImage1.jpg" class="d-block w-100 c-img"
						alt="...">
					<div class="carousel-caption top-0 mt-4 d-none d-md-block">
						<h1 class="mt-5 fs-3">Kitchen Cleaning</h1>
						<p class="lead">
							- Washing dishes and cutlery <br> - Dusting and wiping all
							surfaces <br> - Vacuuming and mopping of floor <br> -
							Emptying and wiping sinks
						</p>
						<a href="services.jsp" class="btn btn-primary px-4 py-2 fs-5 mt-5">Services</a>
					</div>
				</div>
				<div class="carousel-item c-item">
					<img src="../Image/carouselImage2.jpg" class="d-block w-100 c-img"
						alt="...">
					<div class="carousel-caption top-0 mt-4 d-none d-md-block">
						<h1 class="mt-5 fs-3">Bathroom Cleaning</h1>
						<p class="lead">
							- Scrubbing toilets,showers and sinks <br> - Wiping down
							mirrors and glass <br> - Mopping and scrubbing floors
						</p>
						<a href="services.jsp" class="btn btn-primary px-4 py-2 fs-5 mt-5">Services</a>
					</div>
				</div>
				<div class="carousel-item c-item">
					<img src="../Image/1.jpg" class="d-block w-100 c-img" alt="...">
					<div class="carousel-caption top-0 mt-4 d-none d-md-block">
						<h1 class="mt-5 fs-3">Bedroom Cleaning</h1>
						<p class="lead">
							- Dusting bed surfaces <br> - Mopping of floors <br> -
							Wiping down cabinets and doors<br> - Sanitizing remotes and
							buttons
						</p>
						<a href="services.jsp" class="btn btn-primary px-4 py-2 fs-5 mt-5">Services</a>
					</div>
				</div>
			</div>
			<button class="carousel-control-prev" type="button"
				data-bs-target="#allCleanCarousel" data-bs-slide="prev">
				<span class="carousel-control-prev-icon" aria-hidden="true"></span>
				<span class="visually-hidden">Previous</span>
			</button>
			<button class="carousel-control-next" type="button"
				data-bs-target="#allCleanCarousel" data-bs-slide="next">
				<span class="carousel-control-next-icon" aria-hidden="true"></span>
				<span class="visually-hidden">Next</span>
			</button>
		</div>
	</section>

	<%-- Learn Sections --%>
	<section id="learn" class="p-5">
		<div class="container">
			<h1>
				<strong>How it works</strong>
			</h1>
			<br> <br>
			<div class="row align-items-center justify-content-between">
				<div class="col-md">
					<img src="../Image/service.jpg" class="img-fluid rounded"
						width="400" height="400" alt="">
				</div>
				<div class="col-md p-5">
					<h2>
						<strong>Book a service</strong>
					</h2>
					<p class="lead">Our platform simplifies the process of booking
						professional services, offering a streamlined experience from
						start to finish.</p>
					<p>Choose the type of service you need, select a convenient
						time, and let us handle the rest. Our vetted professionals are
						ready to deliver quality service tailored to your needs.</p>
					<a href="booking.jsp" class="btn btn-light mt-3"> <i
						class="bi bi-chevron-right">Book Now</i>
					</a>
				</div>
			</div>
		</div>
	</section>
	
	<section class="p-5 bg-dark" id="testimonialCarousel">
    <div class="container">
        <h1 class="section-header">
            Client Review <span>AllClean Services Feedback</span>
        </h1>

        <div class="testimonials text-light">
            <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <%
                    List<Feedback> feedbackList = (List<Feedback>) request.getAttribute("topFeedback");
                    System.out.println("JSP - Feedback List: " + (feedbackList != null ? feedbackList.size() : "null")); // Debug line
                    
                    if (feedbackList == null || feedbackList.isEmpty()) {
                        System.out.println("Feedback list is null or empty");
                    %>
                        <div class="carousel-item active">
                            <div class="single-item">
                                <div class="row">
                                    <div class="col-md-12">
                                        <p class="text-center">No testimonials available at this time.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <%
                    } else {
                        System.out.println("Number of feedback items: " + feedbackList.size());
                        boolean isFirst = true;
                        for (Feedback feedback : feedbackList) {
                            System.out.println("Processing feedback for user ID: " + feedback.getUser_id());
                    %>
                        <div class="carousel-item <%= isFirst ? "active" : "" %>">
                            <div class="single-item">
                                <div class="row">
                                    <div class="col-md-5">
                                        <div class="profile">
                                            <div class="img-area">
                                                <img src="${pageContext.request.contextPath}/public/Image/defaultpic.png" 
                                                     alt="Profile Picture" 
                                                     class="img-fluid rounded-circle">
                                            </div>
                                            <div class="bio">
                                                <h2>User ID: <%= feedback.getUser_id() %></h2>
                                                <h4>Rating: <%= feedback.getRating() %>/5</h4>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
										<div class="content">
											<p>
												<span><i class="bi bi-chat-right-quote-fill"></i></span>
												<%=feedback.getComments() != null ? feedback.getComments() : "No comments provided"%>
											</p>

											<%
											// Check if improvements are not null and not empty after trimming
											if (feedback.getImprovements() != null && !feedback.getImprovements().trim().isEmpty()) {
											%>
											<p class="suggestions">
												<small>Suggestions for improvement: <%=feedback.getImprovements()%></small>
											</p>
											<%
											}
											%>

											<%
											// Check if sources are not null and not empty after trimming
											if (feedback.getSources() != null && !feedback.getSources().trim().isEmpty()) {
											%>
											<p class="source">
												<small>How did you hear about us? <br> <%=feedback.getSources()%></small>
											</p>
											<% 
    										} 
   										 	%>
										</div>
									</div>
                                </div>
                            </div>
                        </div>
                    <%
                            isFirst = false;
                        }
                    }
                    %>
                </div>

                <!-- Carousel Controls -->
                <% if (feedbackList != null && feedbackList.size() > 1) { %>
                    <button class="carousel-control-prev" type="button" 
                            data-bs-target="#carouselExampleControls" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" 
                            data-bs-target="#carouselExampleControls" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                <% } %>
            </div>
        </div>
    </div>
</section>

	<%-- Question Accordion --%>
	<section id="questions" class="p-5">
		<div class="container">
			<h2 class="text-center mb-4">Frequently Asked Questions</h2>
			<div class="accordion accordion-flush" id="questions">
				<%-- Item 1 --%>
				<div class="accordion-item">
					<h2 class="accordion-header">
						<button class="accordion-button collapsed" type="button"
							data-bs-toggle="collapse" data-bs-target="#question-one">
							What types of cleaning services do you offer?</button>
					</h2>
					<div id="question-one" class="accordion-collapse collapse"
						data-bs-parent="#questions">
						<div class="accordion-body">We offer a variety of cleaning
							services, including regular home cleaning, deep cleaning,
							move-in/move-out cleaning, office cleaning, and post-construction
							cleaning. Contact us for any specialized cleaning needs!
						</div>
					</div>
				</div>

				<%-- Item 2 --%>
				<div class="accordion-item">
					<h2 class="accordion-header">
						<button class="accordion-button collapsed" type="button"
							data-bs-toggle="collapse" data-bs-target="#question-two">
							How can I book a cleaning service?</button>
					</h2>
					<div id="question-two" class="accordion-collapse collapse"
						data-bs-parent="#questions">
						<div class="accordion-body">Booking a cleaning service is
							simple! You can book directly through our website by selecting a
							service, choosing a date and time, and completing the booking
							form. Alternatively, you can contact us by phone.
						</div>
					</div>
				</div>

				<%-- Item 3 --%>
				<div class="accordion-item">
					<h2 class="accordion-header">
						<button class="accordion-button collapsed" type="button"
							data-bs-toggle="collapse" data-bs-target="#question-three">
							Do I need to provide cleaning supplies?</button>
					</h2>
					<div id="question-three" class="accordion-collapse collapse"
						data-bs-parent="#questions">
						<div class="accordion-body">No, our cleaning professionals
							bring all the necessary supplies and equipment. However, if you
							have specific products you prefer, feel free to let us know, and
							we can use them during the service.
						</div>
					</div>
				</div>

				<%-- Item 4 --%>
				<div class="accordion-item">
					<h2 class="accordion-header">
						<button class="accordion-button collapsed" type="button"
							data-bs-toggle="collapse" data-bs-target="#question-four">
							What is included in a deep cleaning?</button>
					</h2>
					<div id="question-four" class="accordion-collapse collapse"
						data-bs-parent="#questions">
						<div class="accordion-body">Deep cleaning includes all the
							services in a standard cleaning, plus thorough scrubbing and
							sanitizing of hard-to-reach areas, cleaning inside appliances,
							and addressing grime buildup. It’s perfect for homes needing an
							intensive refresh.
						</div>
					</div>
				</div>

				<%-- Item 5 --%>
				<div class="accordion-item">
					<h2 class="accordion-header">
						<button class="accordion-button collapsed" type="button"
							data-bs-toggle="collapse" data-bs-target="#question-five">
							What if I need to reschedule or cancel my appointment?</button>
					</h2>
					<div id="question-five" class="accordion-collapse collapse"
						data-bs-parent="#questions">
						<div class="accordion-body">We understand that plans can
							change. You can reschedule or cancel your appointment by
							contacting us at least 24 hours in advance to avoid a
							cancellation fee. We're here to make the process as flexible as
							possible.
						</div>
					</div>
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
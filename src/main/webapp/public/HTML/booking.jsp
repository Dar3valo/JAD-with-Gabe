<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Service" %>
<%@ page import="model.ServiceDAO" %>
<%@ page import="model.Schedule" %>
<%@ page import="model.ScheduleDAO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
    <link rel="stylesheet" href="../CSS/booking.css" />
    <link rel="stylesheet" href="https://fonts.google.com/share?selection.family=Montserrat:ital,wght@0,100..900;1,100..900|Raleway:ital,wght@0,100..900;1,100..900">
    <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.15/index.global.min.js'></script>
    <title>AllClean Booking</title>
</head>
<body>

	<%
    { // check permission
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

    <%-- Navbar --%>
    <jsp:include page="navbar.jsp" />

    <%-- Booking Section with appointment form --%>
<section class="p-5">
        <div class="container my-2 bg-dark w-50 text-light p-2 rounded-3">
            <div class="container-fluid bg-dark text-light py-3">
                <header class="text-center">
                    <h1 class="display-6">Book Your Appointment</h1>
                </header>
            </div>
            <form class="row g-3" action="<%=request.getContextPath()%>/BookingServlet" method="POST">

                <!-- Appointment Details -->
                <div class="col-md-6">
                    <label for="appointmentDate" class="form-label">Appointment Date</label>
                    <input type="date" class="form-control" id="appointmentDate" name="bookingDate" required>
                </div>

                <div class="col-md-6">
                    <label for="schedule" class="form-label">Select Schedule</label>
                    <select id="schedule" name="schedule_id" class="form-select" required>
                        <option selected disabled>Choose...</option>
                        <%
                        	List<Schedule> scheduleDropdown = (List<Schedule>) session.getAttribute("scheduleTimings");
                    		
                    		if (scheduleDropdown == null) {
                                response.sendRedirect(request.getContextPath() + "/ScheduleDropdownServlet");
                                return;
                        	}else if(scheduleDropdown.isEmpty()){
                        %>
                        		<option disabled>No Services Available</option>
                        <%
                        	}else{
                    			for(Schedule schedule: scheduleDropdown){
                    	%>
                    				<option value="<%= schedule.getSchedule_id() %>"><%=schedule.getStart_time() %> - <%=schedule.getEnd_time() %></option>
                    	<%
                    			}
                        	}
                        %>
                    </select>
                </div>

                <div class="col-md-6">
                    <label for="serviceType" class="form-label">Service Type</label>
                    <select id="serviceType" name="serviceType" class="form-select" required>
                        <option selected disabled>Choose...</option>
                        <%
                        	List<Service> serviceDropdown = (List<Service>) session.getAttribute("serviceTypes");
                    		
                    		if (serviceDropdown == null) {
                                response.sendRedirect(request.getContextPath() + "/ServicesBookingDropdownServlet");
                                return;
                        	}else if(serviceDropdown.isEmpty()){
                        %>
                        		<option disabled>No Services Available</option>
                        <%
                        	}else{
                    			for(Service service: serviceDropdown){
                    	%>
                    				<option value="<%= service.getService_id() %>"><%=service.getName() %></option>
                    	<%
                    			}
                        	}
                        %>
                    </select>
                </div>

                <!-- Address Information -->
                <div class="col-12">
                    <label for="main_address" class="form-label">Address</label>
                    <input type="text" class="form-control" id="main_address" name="main_address" placeholder="1234 Main St" required>
                </div>
                <div class="col-6">
                    <label for="postal_code" class="form-label">Postal Code</label>
                    <input type="text" class="form-control" id="postal_code" name="postal_code" maxlength='6' placeholder="123456" required>
                </div>
                <div>
                    <label for="specialRequest" class="form-label">Special Request</label>
                    <textarea class="form-control" id="specialRequest" name="special_request" rows="4" placeholder="Enter your special request here" required></textarea>
                </div>

                <!-- Terms and Conditions -->
                <div class="col-12">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="termsCheck" name="terms" required>
                        <label class="form-check-label" for="termsCheck">I agree to the terms and conditions</label>
                    </div>
                </div>

                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Book Appointment</button>
                </div>
            </form>
            
            <div id="errorMessage" class="alert alert-danger mt-3" style="display:none;">
                Sorry, this appointment slot is already booked. Please select another date or time.
            </div>
        </div>
    </section>

    <%-- Footer --%>
    <jsp:include page="footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</body>
</html>

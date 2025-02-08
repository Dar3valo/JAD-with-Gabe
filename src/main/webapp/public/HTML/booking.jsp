<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Models.Service.Service" %>
<%@ page import="Models.Service.ServiceDAO" %>
<%@ page import="Models.Schedule.Schedule" %>
<%@ page import="Models.Schedule.ScheduleDAO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
    <link rel="stylesheet" href="../CSS/booking.css" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap">
    <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.15/index.global.min.js'></script>
    <title>AllClean - Book Your Service</title>
</head>
<body>
    <%-- Permission Check --%>
    <%
    { 
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

    <%-- Booking Section --%>
    <section class="booking-section">
        <div class="booking-container">
            <div class="booking-header">
                <h1>Book Your Appointment</h1>
                <p class="subtitle">Schedule your cleaning service in just a few steps</p>
            </div>

            <form class="booking-form" action="<%=request.getContextPath()%>/BookingServlet" method="POST">
                <div class="form-section">
                    <h2 class="section-title">
                        <i class="bi bi-calendar-check"></i>
                        Appointment Details
                    </h2>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="appointmentDate">Appointment Date</label>
                            <input type="date" class="form-control" id="appointmentDate" name="bookingDate" required>
                        </div>

                        <div class="form-group">
                            <label for="schedule">Preferred Time</label>
                            <select id="schedule" name="schedule_id" class="form-select" required>
                                <option selected disabled>Select time slot...</option>
                                <%
                                List<Schedule> scheduleDropdown = (List<Schedule>) session.getAttribute("scheduleTimings");
                                if (scheduleDropdown == null) {
                                    response.sendRedirect(request.getContextPath() + "/ScheduleDropdownServlet");
                                    return;
                                } else if(scheduleDropdown.isEmpty()) {
                                %>
                                    <option disabled>No time slots available</option>
                                <%
                                } else {
                                    for(Schedule schedule: scheduleDropdown) {
                                %>
                                    <option value="<%= schedule.getSchedule_id() %>">
                                        <%=schedule.getStart_time() %> - <%=schedule.getEnd_time() %>
                                    </option>
                                <%
                                    }
                                }
                                %>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="serviceType">Service Type</label>
                        <select id="serviceType" name="serviceType" class="form-select" required>
                            <option selected disabled>Select service...</option>
                            <%
                            List<Service> serviceDropdown = (List<Service>) session.getAttribute("serviceTypes");
                            if (serviceDropdown == null) {
                                response.sendRedirect(request.getContextPath() + "/ServicesBookingDropdownServlet");
                                return;
                            } else if(serviceDropdown.isEmpty()) {
                            %>
                                <option disabled>No services available</option>
                            <%
                            } else {
                                for(Service service: serviceDropdown) {
                            %>
                                <option value="<%= service.getService_id() %>"><%=service.getName() %></option>
                            <%
                                }
                            }
                            %>
                        </select>
                    </div>
                </div>

                <div class="form-section">
                    <h2 class="section-title">
                        <i class="bi bi-geo-alt"></i>
                        Location Details
                    </h2>
                    <div class="form-group">
                        <label for="main_address">Address</label>
                        <input type="text" class="form-control" id="main_address" name="main_address" 
                               placeholder="Enter your full address" required>
                    </div>
                    <div class="form-group">
                        <label for="postal_code">Postal Code</label>
                        <input type="text" class="form-control" id="postal_code" name="postal_code" 
                               maxlength='6' placeholder="Enter postal code" required>
                    </div>
                </div>

                <div class="form-section">
                    <h2 class="section-title">
                        <i class="bi bi-chat-dots"></i>
                        Additional Information
                    </h2>
                    <div class="form-group">
                        <label for="specialRequest">Special Requests (Optional)</label>
                        <textarea class="form-control" id="specialRequest" name="special_request" 
                                  rows="4" placeholder="Any specific requirements or instructions..."></textarea>
                    </div>
                </div>

                <div class="terms-section">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="termsCheck" name="terms" required>
                        <label class="form-check-label" for="termsCheck">
                            I agree to the <a href="termsAndCondition.jsp" class="terms-link">terms and conditions</a>
                        </label>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary booking-btn">
                        <i class="bi bi-calendar-check"></i> Confirm Booking
                    </button>
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
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous">
    </script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <title>AllClean Booking</title>
</head>
<body>

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
            <form class="row g-3" action="#" method="POST">

                <!-- Personal Information -->
                <div class="col-md-6">
                    <label for="firstName" class="form-label">First Name</label>
                    <input type="text" class="form-control" id="firstName" required>
                </div>
                <div class="col-md-6">
                    <label for="lastName" class="form-label">Last Name</label>
                    <input type="text" class="form-control" id="lastName" required>
                </div>
                <div class="col-md-6">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" required>
                </div>
                <div class="col-md-6">
                    <label for="phone" class="form-label">Phone Number</label>
                    <input type="text" class="form-control" id="phone" required>
                </div>

                <!-- Appointment Details -->
                <div class="col-md-6">
                    <label for="appointmentDate" class="form-label">Appointment Date</label>
                    <input type="date" class="form-control" id="appointmentDate" required>
                </div>
                <div class="col-md-6">
                    <label for="appointmentTime" class="form-label">Appointment Time</label>
                    <input type="time" class="form-control" id="appointmentTime" required>
                </div>

                <div class="col-md-6">
                    <label for="serviceType" class="form-label">Service Type</label>
                    <select id="serviceType" class="form-select" required>
                        <option selected>Choose...</option>
                        <option value="cleaning">Cleaning</option>
                        <option value="maintenance">Maintenance</option>
                        <option value="repair">Repair</option>
                    </select>
                </div>

                <!-- Address Information -->
                <div class="col-12">
                    <label for="address" class="form-label">Address</label>
                    <input type="text" class="form-control" id="address" placeholder="1234 Main St" required>
                </div>
                <div class="col-12">
                    <label for="address2" class="form-label">Address 2</label>
                    <input type="text" class="form-control" id="address2" placeholder="Apartment, studio, or floor">
                </div>
                <div class="col-md-6">
                    <label for="city" class="form-label">City</label>
                    <input type="text" class="form-control" id="city" required>
                </div>
                <div class="col-md-4">
                    <label for="state" class="form-label">State</label>
                    <select id="state" class="form-select" required>
                        <option selected>Choose...</option>
                        <option>State 1</option>
                        <option>State 2</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <label for="zip" class="form-label">Zip</label>
                    <input type="text" class="form-control" id="zip" required>
                </div>
                <div>
                	<label for="specialRequest" class="form-label">Special Request</label>
                	<textarea class="form-control" id="specialRequest" rows="4" placeholder="Enter your special request here" required></textarea>
                </div>

                <!-- Terms and Conditions -->
                <div class="col-12">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="termsCheck" required>
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

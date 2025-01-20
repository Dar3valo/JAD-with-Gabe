<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Models.Cart.CartItem" %>
<%@ page import="Models.Schedule.Schedule" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AllClean Checkout</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="../CSS/checkout.css">
</head>
<body class="bg-light">
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

    <%-- Include Navbar --%>
    <jsp:include page="navbar.jsp" />

    <main class="container py-5">
        <div class="checkout-wrapper">
            <h1 class="text-center mb-5">Your Shopping Cart</h1>
            
            <div class="row g-4">
                <!-- Cart Items Section -->
                <div class="col-lg-8">
                    <% 
                    List<CartItem> allCartItems = (List<CartItem>) session.getAttribute("allCartItems");
                    if (allCartItems == null) {
                        response.sendRedirect(request.getContextPath() + "/GetBookingServlet");
                    } else if(allCartItems.isEmpty()) {
                    %>
                        <div class="empty-cart">
                            <i class="bi bi-cart-x display-1"></i>
                            <p class="lead mt-3">Your cart is empty</p>
                            <a href="services.jsp" class="btn btn-primary mt-3">Browse Services</a>
                        </div>
                    <% 
                    } else {
                        double subtotal = 0;
                        for (CartItem item : allCartItems) {
                            subtotal += item.getServicePrice();
                    %>
                        <div class="cart-item">
                            <div class="card mb-3 shadow-sm">
                                <div class="row g-0">
                                    <div class="col-md-4">
                                        <img src="<%= item.getServiceImg() != null ? item.getServiceImg() : "../Image/carouselImage1.jpg" %>"
                                             class="img-fluid rounded-start" alt="<%= item.getServiceName() %>">
                                    </div>
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <div class="d-flex justify-content-between">
                                                <h3 class="card-title"><%= item.getServiceName() %></h3>
                                                <h4 class="price">$<%= String.format("%.2f", item.getServicePrice()) %></h4>
                                            </div>
                                            
                                            <div class="schedule-select mt-3">
                                                <label class="form-label">Select Schedule</label>
                                                <select class="form-select" name="schedule_id" required>
                                                    <option selected disabled>Choose time slot...</option>
												<%
												List<Schedule> scheduleDropdown = (List<Schedule>) session.getAttribute("scheduleTimings");

													if (scheduleDropdown == null) {
														response.sendRedirect(request.getContextPath() + "/ScheduleDropdownCartServlet");
														return;
													} else if (scheduleDropdown.isEmpty()) {
												%>
												<option disabled>No Services Available</option>
												<%
												} else {
												for (Schedule schedule : scheduleDropdown) {
												%>
												<option value="<%=schedule.getSchedule_id()%>"><%=schedule.getStart_time()%>
													-
													<%=schedule.getEnd_time()%></option>
												<%
												}
												}
												%>
											</select>
                                            </div>
                                            
                                            <form action="<%= request.getContextPath() %>/DeleteCartItemServlet"
                                                  method="POST" class="mt-3">
                                                <input type="hidden" name="cart_item_id" value="<%= item.getCart_item_id() %>">
                                                <button type="submit" class="btn btn-outline-danger">
                                                    <i class="bi bi-trash"></i> Remove
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <%
                        }
                        double tax = subtotal * 0.10;
                        double shipping = 15;
                        double total = subtotal + tax + shipping;
                    %>
                </div>

                <!-- Order Summary Section -->
                <div class="col-lg-4">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h3 class="card-title mb-4">Order Summary</h3>
                            <div class="summary-item d-flex justify-content-between mb-3">
                                <span>Subtotal</span>
                                <span>$<%= String.format("%.2f", subtotal) %></span>
                            </div>
                            <div class="summary-item d-flex justify-content-between mb-3">
                                <span>Tax (10%)</span>
                                <span>$<%= String.format("%.2f", tax) %></span>
                            </div>
                            <div class="summary-item d-flex justify-content-between mb-3">
                                <span>Shipping</span>
                                <span>$<%= String.format("%.2f", shipping) %></span>
                            </div>
                            <hr>
                            <div class="summary-item d-flex justify-content-between mb-4">
                                <strong>Total</strong>
                                <strong>$<%= String.format("%.2f", total) %></strong>
                            </div>
                            
                            <form action="<%= request.getContextPath() %>/TransferServlet" method="post">
                                <button type="submit" class="btn btn-primary w-100">
                                    <i class="bi bi-credit-card me-2"></i>Proceed to Payment
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
    </main>

    <%-- Footer --%>
    <jsp:include page="footer.jsp" />

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
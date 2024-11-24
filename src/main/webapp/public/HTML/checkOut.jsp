<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.CartItem" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous" />
    <link rel="stylesheet" href="../CSS/checkout.css" />
    <title>AllClean Checkout</title>
</head>
<body>

	<%
	request.getRequestDispatcher("/GetBookingServlet").include(request, response);
	%>

    <%-- Include Navbar --%>
    <jsp:include page="navbar.jsp" />

    <section class="p-5">
        <div class="wrapper">
            <h1>Shopping Cart</h1>
            <div class="project">
                <div class="shop">
                    <%-- Iterate over cart items and display them dynamically --%>
                    <% 
                        List<CartItem> allCartItems = (List<CartItem>) session.getAttribute("allCartItems");
                        double subtotal = 0;
                        for (CartItem item : allCartItems) {
                            subtotal += item.getServicePrice();
                    %>
                    <div class="box">
                        <img src="#" alt="Service Image">
                        <div class="content">
                            <h3><%= item.getServiceName() %></h3>
                            <h4>Price: $<%= item.getServicePrice() %></h4>
                            <p class="btn-area">
                                <i class="bi bi-trash"></i>
                                <span class="btn2">Remove</span>
                            </p>
                        </div>
                    </div>
                    <% } %>
                </div>

                <div class="right-bar">
                    <%-- Calculate tax (10%) and shipping costs (fixed at $15) --%>
                    <% 
                        double tax = subtotal * 0.10;
                        double shipping = 15;
                        double total = subtotal + tax + shipping;
                    %>

                    <p><span>Subtotal</span> <span>$<%= String.format("%.2f", subtotal) %></span></p>
                    <hr>
                    <p><span>Tax (10%)</span> <span>$<%= String.format("%.2f", tax) %></span></p>
                    <hr>
                    <p><span>Shipping</span> <span>$<%= String.format("%.2f", shipping) %></span></p>
                    <hr>
                    <p><span>Total</span> <span>$<%= String.format("%.2f", total) %></span></p>

					<form action="<%=request.getContextPath() %>/TransferServlet" method="post">
						<button type="submit" class="btn btn-success">
							<i class="bi bi-cart"></i> Pay
						</button>
					</form>

					<hr>
                </div>
            </div>
        </div>
    </section>

    <%-- Footer --%>
    <jsp:include page="footer.jsp" />

</body>
</html>

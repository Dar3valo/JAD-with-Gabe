<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="CartModel.CartItem" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="../CSS/checkout.css" />
    <title>AllClean Checkout</title>
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

    <%-- Include Navbar --%>
    <jsp:include page="navbar.jsp" />

    <section class="p-5">
        <div class="wrapper">
            <h1>Shopping Cart</h1>
            <div class="project">
                <div class="shop">
                    <% 
                        List<CartItem> allCartItems = (List<CartItem>) session.getAttribute("allCartItems");
                        if (allCartItems == null) {
                            response.sendRedirect(request.getContextPath() + "/GetBookingServlet");
                        }else if(allCartItems.isEmpty()) {
                        	out.println("<p>Your cart is empty. Please add some items.</p>");
                        }
                        else {
                            double subtotal = 0;
                            
                            for (CartItem item : allCartItems) {
                                subtotal += item.getServicePrice();
                    %>
                    <div class="box">
                        <img src="<%= item.getServiceImg() != null ? item.getServiceImg() : "../Image/carouselImage1.jpg" %>" alt="<%= item.getServiceName() %>">
                        <div class="content">
                            <h3><%= item.getServiceName() %></h3>
                            <h4>Price: $<%= item.getServicePrice() %></h4>
							<form
								action="<%=request.getContextPath()%>/DeleteCartItemServlet"
								method="POST">
								<input type="hidden" name="cart_item_id"
									value="<%=item.getCart_item_id()%>">
								<button type="submit" class="btn-area">
									<i class="bi bi-trash"></i> <span class="btn2">Remove</span>
								</button>
							</form>
						</div>
                    </div>
                    <% 
                            } // End for loop
                            double tax = subtotal * 0.10;
                            double shipping = 15;
                            double total = subtotal + tax + shipping;
                    %>
                </div>

                <div class="right-bar">
                    <p><span>Subtotal</span> <span>$<%= String.format("%.2f", subtotal) %></span></p>
                    <hr>
                    <p><span>Tax (10%)</span> <span>$<%= String.format("%.2f", tax) %></span></p>
                    <hr>
                    <p><span>Shipping</span> <span>$<%= String.format("%.2f", shipping) %></span></p>
                    <hr>
                    <p><span>Total</span> <span>$<%= String.format("%.2f", total) %></span></p>

                    <form action="<%= request.getContextPath() %>/TransferServlet" method="post">
                        <button type="submit" class="btn btn-success">
                            <i class="bi bi-cart"></i> Pay
                        </button>
                    </form>
                    <% } // End if-else %>
                    <hr>
                </div>
            </div>
        </div>
    </section>

    <%-- Footer --%>
    <jsp:include page="footer.jsp" />
</body>
</html>
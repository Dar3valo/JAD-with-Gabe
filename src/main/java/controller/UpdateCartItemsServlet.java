package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CartItemDAO;

import java.io.IOException;
import java.sql.Date;

/**
 * Servlet implementation class UpdateCartItemServlet
 */
@WebServlet("/UpdateCartItemsServlet")
public class UpdateCartItemsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateCartItemsServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Parse form data
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            Date bookingDate = Date.valueOf(request.getParameter("bookingDate"));
            String specialRequest = request.getParameter("specialRequest");
            String mainAddress = request.getParameter("mainAddress");
            int postalCode = Integer.parseInt(request.getParameter("postalCode"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            String serviceName = request.getParameter("serviceName");
            double servicePrice = Double.parseDouble(request.getParameter("servicePrice"));

            // Update cart item
            CartItemDAO updateDAO = new CartItemDAO();
            boolean isUpdated = updateDAO.updateCartItem(cartItemId, bookingDate, specialRequest, mainAddress, postalCode,
                                                         userId, scheduleId, serviceId, serviceName, servicePrice);

            if (isUpdated) {
                // Redirect to checkOut.jsp with success message
                request.setAttribute("successMessage", "Cart item updated successfully!");
            } else {
                // Redirect with error message
                request.setAttribute("errorMessage", "Failed to update cart item. Please try again.");
            }
            
            RequestDispatcher rd = request.getRequestDispatcher("/public/HTML/checkOut.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("/public/HTML/error.jsp").forward(request, response);
        }
    }
}

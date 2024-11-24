package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DeleteCartItemDAO;
import model.CartItem;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class DeleteCartItemServlet
 */
@WebServlet("/DeleteCartItemServlet")
public class DeleteCartItemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCartItemServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
HttpSession session = request.getSession();
        
        try {
            // Get cart_item_id from request parameter and validate
            String cartItemIdStr = request.getParameter("cart_item_id");
            
            if (cartItemIdStr == null || cartItemIdStr.trim().isEmpty()) {
                // Handle null or empty cart_item_id
                session.setAttribute("errorMessage", "Cart item ID is missing");
                response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp");
                return;
            }
            
            // Parse the cart_item_id
            int cart_item_id = Integer.parseInt(cartItemIdStr.trim());
            
            // Validate cart_item_id is positive
            if (cart_item_id <= 0) {
                session.setAttribute("errorMessage", "Invalid cart item ID");
                response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp");
                return;
            }
            
            // Create DAO instance and delete the cart item
            DeleteCartItemDAO dao = new DeleteCartItemDAO();
            dao.deleteCartItem(cart_item_id);
            
            // Update the session cart items
            @SuppressWarnings("unchecked")
            List<CartItem> allCartItems = (List<CartItem>) session.getAttribute("allCartItems");
            if (allCartItems != null) {
                allCartItems.removeIf(item -> item.getCart_item_id() == cart_item_id);
                session.setAttribute("allCartItems", allCartItems);
            }
            
            // Set success message
            session.setAttribute("successMessage", "Item successfully removed from cart");
            
            // Redirect back to the cart page
            response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp");
            
        } catch (NumberFormatException e) {
            // Log the error
            e.printStackTrace();
            session.setAttribute("errorMessage", "Invalid cart item ID format");
            response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp");
            
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            session.setAttribute("errorMessage", "Error removing item from cart");
            response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

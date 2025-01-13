package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import CartModel.CartItem;
import CartModel.CartItemDAO;
import UserModel.User;

/**
 * Servlet implementation class DeleteCartItemServlet
 */
@WebServlet("/DeleteCartItemServlet")
public class DeleteCartItemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeleteCartItemServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            // Get cart_item_id from request parameter and validate
            String cartItemIdStr = request.getParameter("cart_item_id");
            
            if (cartItemIdStr == null || cartItemIdStr.trim().isEmpty()) {
                session.setAttribute("errorMessage", "Cart item ID is missing");
                response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp");
                return;
            }
            
            // Parse the cart_item_id
            int cart_item_id = Integer.parseInt(cartItemIdStr.trim());
            
            if (cart_item_id <= 0) {
                session.setAttribute("errorMessage", "Invalid cart item ID");
                response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp");
                return;
            }
            
            // Create DAO instance and delete the cart item
            CartItemDAO.deleteCartItem(cart_item_id);
            
            // Refresh the cart items in the session
            List<CartItem> updatedCartItems = CartItemDAO.getCartItems(((User) session.getAttribute("loggedInUser")).getUser_id());
            session.setAttribute("allCartItems", updatedCartItems);
            
            // Set success message
            session.setAttribute("successMessage", "Item successfully removed from cart");
            response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Invalid cart item ID format");
            response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Error removing item from cart");
            response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model.CartItem;
import model.GetCartItemDAO;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class GetBookingServlet
 */
@WebServlet("/GetBookingServlet")
public class GetBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GetBookingServlet() {
        super();
    }

    /**
     * Handles GET requests to fetch cart items for a logged-in user.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false); // Avoid creating a new session unnecessarily
            if (session == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            int userId = loggedInUser.getUser_id();

            GetCartItemDAO getItemsDAO = new GetCartItemDAO();
            List<CartItem> cartItems = getItemsDAO.getCartItems(userId); // Fetch items for the specific user

            session.setAttribute("allCartItems", cartItems);
            
            if (cartItems == null || cartItems.isEmpty()) {
                request.setAttribute("errorMessage", "Your cart is empty.");
                request.getRequestDispatcher("/public/HTML/checkOut.jsp").forward(request, response);
                return;
            } // Store cart items in session
            response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBookingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
            GetCartItemDAO getItemsDAO = new GetCartItemDAO();
            List<CartItem> cartItems = getItemsDAO.getCartItems();
            
            // Check if cartItems is empty
            if (cartItems.isEmpty()) {
                request.setAttribute("errorMessage", "Your cart is empty.");
                request.getRequestDispatcher("/public/HTML/checkOut.jsp").forward(request, response);
                return;
            }

            HttpSession session = request.getSession();  // Creates a session if it does not exist
            session.setAttribute("allCartItems", cartItems);
            
            response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp");
        } catch (Exception e) {
            e.printStackTrace();  // You might want to log this in a proper log file
            request.setAttribute("errorMessage", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

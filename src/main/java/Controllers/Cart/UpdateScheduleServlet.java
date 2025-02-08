package Controllers.Cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import Models.Cart.CartItemDAO;

/**
 * Servlet implementation class UpdateScheduleServlet
 */
@WebServlet("/UpdateScheduleServlet")
public class UpdateScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Get parameters from the form
        String cartItemIdParam = request.getParameter("cart_item_id");
        String scheduleIdParam = request.getParameter("schedule_id");

        // Validate input
        if (cartItemIdParam == null || scheduleIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp?error=invalidInput");
            return;
        }

        try {
            int cartItemId = Integer.parseInt(cartItemIdParam);
            int scheduleId = Integer.parseInt(scheduleIdParam);

            // Call DAO to update the cart item's schedule
            CartItemDAO cartItemDAO = new CartItemDAO();
            boolean isUpdated = cartItemDAO.updateCartItemSchedule(cartItemId, scheduleId);

            if (isUpdated) {
                // Redirect to the checkout page with success message
                response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp?success=scheduleUpdated");
            } else {
                // Redirect to the checkout page with failure message
                response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp?error=updateFailed");
            }
        } catch (NumberFormatException e) {
            // Redirect to the checkout page with error message for invalid input
            response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp?error=invalidInput");
        } catch (Exception e) {
            e.printStackTrace();
            // Redirect to the checkout page with a general error message
            response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp?error=unexpectedError");
        }
	}

}

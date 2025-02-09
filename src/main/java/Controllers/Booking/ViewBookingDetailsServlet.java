package Controllers.Booking;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import Models.Booking.Booking;
import Models.Booking.BookingDAO;
import Models.User.User;

/**
 * Servlet implementation class ViewBookingDetailsServlet
 */
@WebServlet("/ViewBookingDetailsServlet")
public class ViewBookingDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewBookingDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            HttpSession session = request.getSession();
            
            // Get the logged in user's role
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
                return;
            }

            // Get and set booking details
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            BookingDAO bookingDAO = new BookingDAO();
            Booking booking = bookingDAO.getBookingDetailsByBookingId(bookingId);
            session.setAttribute("bookingDetails", booking);
            
            // Redirect based on role
            if (loggedInUser.getRole_id() == 1) {
                response.sendRedirect(request.getContextPath() + "/public/HTML/bookingDetails.jsp");
            } else if (loggedInUser.getRole_id() == 2) {
                response.sendRedirect(request.getContextPath() + "/public/HTML/bookingDetailsClient.jsp");
            } else {
                // Handle unexpected role
                response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
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

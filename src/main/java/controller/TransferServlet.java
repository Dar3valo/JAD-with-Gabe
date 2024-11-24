package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Booking;
import model.BookingDAO;
import model.User;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class TransferServlet
 */
@WebServlet("/TransferServlet")
public class TransferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransferServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			BookingDAO bookedDAO = new BookingDAO();
			List<Booking> bookedItems = bookedDAO.getBookingInfo();
			
			if(bookedItems.isEmpty()) {
				request.setAttribute("errorMessage", "Your payment is unsuccessful.");
                request.getRequestDispatcher("/public/HTML/checkOut.jsp").forward(request, response);
                return;
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("allBookedItems", bookedItems);
			
			response.sendRedirect(request.getContextPath() + "/public/HTML/invoice.jsp");

		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "An unexpected error occured! Please try again later.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            // Redirect to login page if user is not logged in
            request.setAttribute("errorMessage", "You must be logged in to perform this action.");
            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
            return;
        }

        try {
        // Get user ID from the logged-in user object
        int userId = loggedInUser.getUser_id();
        
        BookingDAO bookDAO = new BookingDAO();
        Booking newBooking = bookDAO.transferCartData(userId);
        
        if(newBooking != null) {
        	session.setAttribute("successMessage", "Services Booked Successfully");
        	response.sendRedirect(request.getContextPath() + "/public/HTML/invoice.jsp");
        }else {
        	request.setAttribute("errorMessage", "No Items in Cart");
        	request.getRequestDispatcher("/public/HTML/checkOut.jsp").forward(request, response);
        }
        }catch(Exception e) {
        	e.printStackTrace();
        }
	}
}

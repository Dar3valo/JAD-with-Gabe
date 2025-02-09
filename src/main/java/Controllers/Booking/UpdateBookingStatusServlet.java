package Controllers.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.Booking.Booking;
import Models.Booking.BookingDAO;
import Models.Status.Status;
import Models.Status.StatusDAO;
import Models.User.User;

/**
 * Servlet implementation class UpdateBookingStatusServlet
 */
@WebServlet("/UpdateBookingStatusServlet")
public class UpdateBookingStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateBookingStatusServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int bookingId = Integer.parseInt(request.getParameter("bookingId"));
			int statusId = Integer.parseInt(request.getParameter("statusId"));

			// Validate status exists
			Status status = StatusDAO.getStatus(statusId);
			if (status == null) {
				request.setAttribute("error", "Invalid status ID");
			} else {
				// Update booking status
				boolean updated = BookingDAO.updateBookingStatus(bookingId, statusId);

				if (updated) {
					// Get fresh bookings list immediately after update
					HttpSession session = request.getSession();
					User loggedInUser = (User) session.getAttribute("loggedInUser");
					List<Booking> bookings;

					if (loggedInUser.getRole_id() == 1) {
						bookings = BookingDAO.getAllBookingsForAdmin();
					} else {
						bookings = BookingDAO.getBookingsByUserId(loggedInUser.getUser_id());
					}
		            
		            Map<Integer, Status> statusMap = new HashMap<>();
		            List<Status> allStatuses = new StatusDAO().getAllStatuses();
		            for (Status temp_status : allStatuses) {
		                statusMap.put(temp_status.getStatus_id(), temp_status);
		            }

		            for (Booking booking : bookings) {
		                int temp_statusId = booking.getStatus_id();
		                Status temp_status = statusMap.get(temp_statusId);
		                booking.setStatusDescription(temp_status != null ? temp_status.getName() : "Unknown");
		            }

					session.setAttribute("bookings", bookings);
					request.setAttribute("message", "Status updated successfully to " + status.getName());
				} else {
					request.setAttribute("error", "Failed to update status");
				}
			}
		} catch (Exception e) {
			request.setAttribute("error", "An error occurred while updating status");
		}

		// Direct forward to JSP instead of going through another servlet
		response.sendRedirect(request.getContextPath() + "/public/HTML/bookingStatus.jsp");
	}
}

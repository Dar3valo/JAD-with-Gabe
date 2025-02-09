package Controllers.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.Booking.Booking;
import Models.Booking.BookingDAO;
import Models.Status.Status;
import Models.Status.StatusDAO;
import Models.User.User;

/**
 * Servlet implementation class BookingStatusServlet
 */
@WebServlet("/BookingStatusServlet")
public class BookingStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BookingStatusServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false); // Don't create new session if none exists
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        
        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
            return;
        }

        try {
            // Get bookings based on role
            List<Booking> bookings;
            if(loggedInUser.getRole_id() == 1) {
                bookings = BookingDAO.getAllBookingsForAdmin();
            } else {
                bookings = BookingDAO.getBookingsByUserId(loggedInUser.getUser_id());
            }
            
            // First get all statuses at once
            Map<Integer, Status> statusMap = new HashMap<>();
            List<Status> allStatuses = new StatusDAO().getAllStatuses();
            for (Status status : allStatuses) {
                statusMap.put(status.getStatus_id(), status);
            }

            // Update booking descriptions using the status map
            for (Booking booking : bookings) {
                int statusId = booking.getStatus_id();
                Status status = statusMap.get(statusId);
                booking.setStatusDescription(status != null ? status.getName() : "Unknown");
            }
            
            // Store in session and forward (don't redirect)
            session.setAttribute("bookings", bookings);
            response.sendRedirect(request.getContextPath() + "/public/HTML/bookingStatus.jsp");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

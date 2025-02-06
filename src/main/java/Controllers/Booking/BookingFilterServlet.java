package Controllers.Booking;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import Models.Booking.Booking;
import Models.Booking.BookingDAO;

@WebServlet("/BookingFilterServlet")
public class BookingFilterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BookingFilterServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 String filterType = request.getParameter("filterType");
         String filterValue = request.getParameter("filterValue");
         int pageNumber = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
         int pageSize = 5;
         HttpSession session = request.getSession();

         BookingDAO bookingDAO = new BookingDAO();
         List<Booking> bookingList = null;
         int totalFilteredRecords;

         if (filterType != null && filterValue != null && !filterValue.isEmpty()) {
             // Handle top customers filter
             if ("top-customers".equals(filterType)) {
                 // Retrieve top 10 customers by service price
                 bookingList = bookingDAO.getTopCustomersByServicePrice(pageNumber, pageSize);
                 totalFilteredRecords = bookingDAO.getTotalTopCustomersByServicePrice();
             } else {
                 // Handle other filters like date or month
                 bookingList = bookingDAO.getFilteredBookings(filterType, filterValue, pageNumber, pageSize);
                 totalFilteredRecords = bookingDAO.getTotalFilteredBookings(filterType, filterValue);
             }
         } else {
             bookingList = bookingDAO.getBookingDetailsAdmin(pageNumber, pageSize);
             totalFilteredRecords = bookingDAO.getTotalBookings();
         }

         session.setAttribute("bookings", bookingList);

         // Set session attribute for dashboard tab
         session.setAttribute("dashboardCurrentFocus", "booking-content");

         // Build redirect URL without null filterType or filterValue
         String redirectUrl = request.getContextPath() + "/public/HTML/dashboard.jsp";
         
         // Append filterType and filterValue if provided
         if (filterType != null && !filterType.isEmpty() && filterValue != null && !filterValue.isEmpty()) {
             redirectUrl += "?filterType=" + filterType + "&filterValue=" + filterValue;
         }
         
         else if(filterType != null && !filterType.isEmpty()) {
        	 redirectUrl += "?filterType=" +filterType;
         }

         response.sendRedirect(redirectUrl);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
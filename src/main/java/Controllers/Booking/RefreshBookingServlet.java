package Controllers.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RefreshBookingServlet")
public class RefreshBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public RefreshBookingServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Remove bookings from session
        request.getSession().removeAttribute("bookings");
        
        // Redirect back to the bookings page
        response.sendRedirect(request.getContextPath() + "/public/HTML/bookingStatus.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
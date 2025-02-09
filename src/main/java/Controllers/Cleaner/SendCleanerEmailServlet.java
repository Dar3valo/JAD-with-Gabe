package Controllers.Cleaner;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import Models.Booking.Booking;
import Models.Booking.BookingDAO;
import Utils.JWT;
import Utils.Mail;

@WebServlet("/SendCleanerEmailServlet")
public class SendCleanerEmailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String JWT_SECRET = "your-secret-key"; // Store this securely
    private final BookingDAO bookingDAO = new BookingDAO();
       
    public SendCleanerEmailServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Get parameters from request
            String bookingId = request.getParameter("input_booking_id");
            String cleanerEmail = request.getParameter("input_email");
            
            if (bookingId == null || bookingId.trim().isEmpty()) {
                throw new ServletException("Booking ID is required");
            }
            if (cleanerEmail == null || cleanerEmail.trim().isEmpty()) {
                throw new ServletException("Cleaner email is required");
            }

            // Get booking details using BookingDAO
            Booking booking = bookingDAO.getBookingDetailsByBookingId(Integer.parseInt(bookingId));
            if (booking == null) {
                throw new ServletException("Booking not found");
            }

            // ============================
            // allow date based on booking
            // ============================
            
            String[] times = booking.getBookingPeriod().split(" - ");
            LocalTime startTime = LocalTime.parse(times[0], DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime endTime = LocalTime.parse(times[1], DateTimeFormatter.ofPattern("HH:mm"));

            LocalDateTime bookingDate = booking.getBooking_date().toLocalDate().atTime(startTime);
            LocalDateTime allowedStart = bookingDate.minusHours(1);
            LocalDateTime allowedEnd = booking.getBooking_date().toLocalDate().atTime(endTime).plusHours(1);
            
            // =======================================
            // always going to allow access (test)
            // =======================================
            
//            LocalDateTime startDateTime = LocalDateTime.of(2025, 2, 9, 13, 0); // February 9, 2025 1:00 PM
//            LocalDateTime endDateTime = LocalDateTime.of(2027, 2, 20, 14, 0);  // February 20, 2027 2:00 PM
//            LocalDateTime allowedStart = startDateTime.minusHours(1);
//            LocalDateTime allowedEnd = endDateTime.plusHours(1);
            
            // ==========================
            // never allow access (test)
            // ==========================
            
//            LocalDateTime startDateTime = LocalDateTime.of(2024, 2, 9, 13, 0); // February 9, 2024 1:00 PM
//            LocalDateTime endDateTime = LocalDateTime.of(2024, 2, 20, 14, 0);  // February 20, 2027 2:00 PM
//            LocalDateTime allowedStart = startDateTime.minusHours(1);
//            LocalDateTime allowedEnd = endDateTime.plusHours(1);
            
            // create jwt
            String jwt = JWT.createJWT(JWT_SECRET, bookingId, allowedStart, allowedEnd);

            // Create email content
            String subject = "New Cleaning Assignment";
            String baseUrl = request.getScheme() + "://" + request.getServerName() + 
                           ":" + request.getServerPort() + request.getContextPath();
            // Point directly to the UpdateServiceStatus servlet
            String bookingUrl = baseUrl + "/UpdateServiceStatus?token=" + jwt;
            
            String emailBody = String.format("""
                Hello,
                
                You have been assigned a new cleaning job.
                
                Booking Details:
                - Date: %s
                - Time: %s
                - Service: %s
                - Address: %s
                - Status: %s
                
                Please click the link below to view and update the booking details:
                %s
                
                This link will be accessible before your scheduled time, but you can only update the status during the booking period.
                
                Best regards,
                AllClean
                """,
                booking.getBooking_date(),
                booking.getBookingPeriod(),
                booking.getServiceName(),
                booking.getMain_address(),
                booking.getStatusName(),
                bookingUrl
            );

            // Send email
            boolean emailSent = Mail.sendEmail(cleanerEmail, subject, emailBody);
            
            if (!emailSent) {
                throw new ServletException("Failed to send email");
            }

            // Send success response
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true}");

        } catch (Exception e) {
            // Log the error
            getServletContext().log("Error in SendCleanerEmailServlet", e);
            
            // Send error response
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"error\": \"" + 
                e.getMessage().replace("\"", "'") + "\"}");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
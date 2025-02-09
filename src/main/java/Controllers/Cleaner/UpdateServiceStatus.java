package Controllers.Cleaner;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import Models.Booking.Booking;
import Models.Booking.BookingDAO;
import Utils.JWT;

@WebServlet("/UpdateServiceStatus")
public class UpdateServiceStatus extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String JWT_SECRET = "your-secret-key"; 
    private final BookingDAO bookingDAO = new BookingDAO();
       
    public UpdateServiceStatus() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            System.out.println("Starting UpdateServiceStatus GET request");
            
            String token = request.getParameter("token");
            System.out.println("Received token: " + token);
            
            if (token == null || token.trim().isEmpty()) {
                System.out.println("Token is null or empty");
                response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
                return;
            }

            try {
                // Decode JWT
                Map<String, Object> claims = JWT.decodeJWT(token, JWT_SECRET);
                System.out.println("Successfully decoded JWT");
                
                String bookingId = (String) claims.get("bookingId");
                System.out.println("Booking ID from token: " + bookingId);
                
                Booking booking = bookingDAO.getBookingDetailsByBookingId(Integer.parseInt(bookingId));
                System.out.println("Retrieved booking: " + (booking != null));
                
                if (booking == null) {
                    System.out.println("Booking not found");
                    response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
                    return;
                }

                // Check time window
                LocalDateTime currentTime = LocalDateTime.now();
                LocalDateTime allowedStart = LocalDateTime.parse((String)claims.get("allowedStart"), 
                                                              DateTimeFormatter.ISO_DATE_TIME);
                LocalDateTime allowedEnd = LocalDateTime.parse((String)claims.get("allowedEnd"), 
                                                            DateTimeFormatter.ISO_DATE_TIME);
                
                System.out.println("Current time: " + currentTime);
                System.out.println("Allowed start: " + allowedStart);
                System.out.println("Allowed end: " + allowedEnd);

                // For GET requests, determine the time window status
                request.setAttribute("booking", booking);
                request.setAttribute("isWithinTimeFrame", 
                    currentTime.isAfter(allowedStart) && currentTime.isBefore(allowedEnd));
                request.setAttribute("isAfterTimeFrame", currentTime.isAfter(allowedEnd));
                request.setAttribute("token", token); // Ensure token is available for form submission

                System.out.println("Forwarding to cleanerStatus.jsp");
                request.getRequestDispatcher("/public/HTML/cleanerStatus.jsp").forward(request, response);

            } catch (Exception e) {
                System.out.println("Error processing JWT: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

        } catch (Exception e) {
            System.out.println("Final error catch: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String token = request.getParameter("token");
            if (token == null || token.trim().isEmpty()) {
                throw new ServletException("Token is required");
            }

            // Decode JWT and validate time window
            Map<String, Object> claims = JWT.decodeJWT(token, JWT_SECRET);
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime allowedStart = LocalDateTime.parse((String)claims.get("allowedStart"), 
                                                          DateTimeFormatter.ISO_DATE_TIME);
            LocalDateTime allowedEnd = LocalDateTime.parse((String)claims.get("allowedEnd"), 
                                                        DateTimeFormatter.ISO_DATE_TIME);

            // Only allow updates within the time window
            if (currentTime.isBefore(allowedStart) || currentTime.isAfter(allowedEnd)) {
                throw new ServletException("Status can only be updated during the scheduled time window");
            }

            String bookingId = (String) claims.get("bookingId");
            String statusId = request.getParameter("statusId");
            
            BookingDAO.updateBookingStatus(Integer.parseInt(bookingId), Integer.parseInt(statusId));
            
            response.sendRedirect(request.getContextPath() + "/UpdateServiceStatus?token=" + token);

        } catch (Exception e) {
            System.out.println("Error in doPost: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
        }
    }
}
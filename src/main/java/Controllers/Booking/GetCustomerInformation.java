package Controllers.Booking;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import org.json.JSONObject;
import Models.Booking.Booking;
import Models.Booking.BookingDAO;
import Models.User.User;

@WebServlet("/GetCustomerInformation")
public class GetCustomerInformation extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final HttpClient httpClient = HttpClient.newHttpClient();
       
    public GetCustomerInformation() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        // Check access level
        request.setAttribute("pageAccessLevel", "1");
        RequestDispatcher rd = request.getRequestDispatcher("/checkAccessServlet");
        rd.include(request, response);
        
        Boolean hasAccess = (Boolean) session.getAttribute("accessCheckResult");
        if (hasAccess == null || !hasAccess) {
            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
            return;
        }

        try {
            // Verify user is logged in
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
                return;
            }

            // Get all bookings
            List<Booking> bookings = BookingDAO.getBookingsWithUserDetails();
            
            // Store original booking list in session
            session.setAttribute("allBookingInformation", bookings);
            
            // Group bookings by location
            Map<String, List<Booking>> groupedBookings = groupBookingsByLocation(bookings);
            
            // Store grouped bookings in session as Map
            session.setAttribute("allBookingInformationFormatted", groupedBookings);
            
            // Redirect to dashboard
            response.sendRedirect(request.getContextPath() + "/public/HTML/dashboard.jsp");
            
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("/public/HTML/error.jsp").forward(request, response);
        }
    }

    private Map<String, List<Booking>> groupBookingsByLocation(List<Booking> bookings) {
        Map<String, List<Booking>> groupedBookings = new HashMap<>();
        Map<Integer, String> postalCodeCache = new HashMap<>();

        for (Booking booking : bookings) {
            try {
                int postalCode = booking.getPostal_code();
                String area = postalCodeCache.get(postalCode);

                // If area not in cache, fetch from API
                if (area == null) {
                    area = getAreaFromPostalCode(postalCode);
                    postalCodeCache.put(postalCode, area);
                }

                // Add booking to the appropriate area list
                groupedBookings.computeIfAbsent(area, k -> new ArrayList<>()).add(booking);

            } catch (Exception e) {
                e.printStackTrace();
                // Continue processing other bookings if one fails
            }
        }

        return groupedBookings;
    }

    private String getAreaFromPostalCode(int postalCode) {
        try {
            String apiUrl = String.format("http://localhost:8081/api/location/area?postalCode=%d", postalCode);
            HttpRequest apiRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(apiRequest, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                JSONObject locationResponse = new JSONObject(response.body());
                return locationResponse.getString("PlanningArea");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Return postal code as string if API call fails
        return String.valueOf(postalCode);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
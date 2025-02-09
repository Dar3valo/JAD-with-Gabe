package Controllers.Booking;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import Models.Booking.Booking;
import Models.Booking.BookingDAO;
import Models.Cart.CartItem;
import Models.User.User;

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
        { // check permission
        	request.setAttribute("pageAccessLevel", "2");
	        RequestDispatcher rd = request.getRequestDispatcher("/checkAccessServlet");
	        rd.include(request, response);
	        
	        HttpSession session = request.getSession();
	        Boolean hasAccess = (Boolean) session.getAttribute("accessCheckResult");
	        
	        if (hasAccess == null || !hasAccess) {
	            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
	            return;
	        }
        }
        
        HttpSession session = request.getSession();
        Boolean hasAccess = (Boolean) session.getAttribute("accessCheckResult");

        if (hasAccess == null || !hasAccess) {
            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
            return;
        }

        try {
            session = request.getSession(false); // Avoid creating a new session unnecessarily
            if (session == null) {
                response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
                return;
            }

            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
                return;
            }

            int userId = loggedInUser.getUser_id();

            BookingDAO bookedDAO = new BookingDAO();
            List<Booking> bookedItems = bookedDAO.getBookingInfo(userId);

            if (bookedItems.isEmpty()) {
                request.setAttribute("errorMessage", "Your payment is unsuccessful.");
                response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp");
                return;
            } else {
            	session.removeAttribute("allCartItems");
                session.setAttribute("allBookedItems", bookedItems);
                response.sendRedirect(request.getContextPath() + "/public/HTML/bookingList.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred! Please try again later.");
            request.getRequestDispatcher("/public/HTML/checkOut.jsp").forward(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        { // check permission
        	request.setAttribute("pageAccessLevel", "2");
	        RequestDispatcher rd = request.getRequestDispatcher("/checkAccessServlet");
	        rd.include(request, response);
	        
	        HttpSession session = request.getSession();
	        Boolean hasAccess = (Boolean) session.getAttribute("accessCheckResult");
	        
	        if (hasAccess == null || !hasAccess) {
	            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
	            return;
	        }
        }
        
        HttpSession session = request.getSession();
        Boolean hasAccess = (Boolean) session.getAttribute("accessCheckResult");

        if (hasAccess == null || !hasAccess) {
            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
            return;
        }

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
            HttpURLConnection conn = null;

            // i am working here this is my working space
            List<CartItem> allCartItems = (List<CartItem>) session.getAttribute("allCartItems");
            List<JSONObject> purchaseItems = new ArrayList<>();
            
            for (CartItem item : allCartItems) {
            	// create dummy item for stripe to receive
            	JSONObject singleItem = new JSONObject();
            	singleItem.put("name", item.getCartItemName());
            	singleItem.put("amount", item.getServicePrice() * 100); // format to lowest possible value for currency
            	singleItem.put("quantity", 1);
            	
            	purchaseItems.add(singleItem);
            }

            JSONObject requestBody = new JSONObject();
            requestBody.put("items", purchaseItems);
            requestBody.put("currency", "SGD");
            requestBody.put("successUrl", "http://localhost:8080" + request.getContextPath() + "/TransactionServlet");
            requestBody.put("cancelUrl", "http://localhost:8080" + request.getContextPath() + "/public/HTML/checkOut.jsp");
            
            URL url = new URL("http://localhost:8081/api/product/v1/checkout");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            // create request body
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            // handle response
            int responseCode = conn.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder result = new StringBuilder();
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        result.append(responseLine.trim());
                    }
                }

                JSONObject jsonResponse = new JSONObject(result.toString());
                
                if (jsonResponse.getString("status").equals("SUCCESS")) {
                	// redirect to payment processing
                    String checkoutUrl = jsonResponse.getString("sessionUrl");
                    response.sendRedirect(checkoutUrl);
                
                } else {
                    // error I: the phantom error
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Checkout failed: " + jsonResponse.getString("message"));
                }
            } else {
                // error II: attack of the errors
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error during checkout. Status code: " + responseCode);
            }
            
            // Close the connection
            conn.disconnect();
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred! Please try again later.");
            request.getRequestDispatcher("/public/HTML/checkOut.jsp").forward(request, response);
        }
	}
}

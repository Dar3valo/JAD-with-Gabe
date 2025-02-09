package Controllers.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import Models.ForgetPassword.ForgetPasswordDAO;
import Models.User.User;
import Utils.Mail;

/**
 * Servlet implementation class HandleForgetPasswordServlet
 */
@WebServlet("/HandleForgetPasswordServlet")
public class HandleForgetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleForgetPasswordServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
String token = request.getParameter("token");
        
        if (token == null || token.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
            return;
        }

        ForgetPasswordDAO dao = new ForgetPasswordDAO();
        User userWithToken = dao.checkTokenValidity(token);

        if (userWithToken == null) {
            // Token is invalid or expired
            response.sendRedirect(request.getContextPath() + "/public/HTML/tokenExpired.jsp");
            return;
        }

        // Token is valid, proceed to reset password page
        response.sendRedirect(request.getContextPath() + "/public/HTML/resetPassword.jsp?token=" + token);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String email = request.getParameter("email");

        ForgetPasswordDAO dao = new ForgetPasswordDAO();
        try {
            // Check if email exists in the database
            User checkExistingEmail = dao.checkExistingEmail(email);
            if (checkExistingEmail == null) {
                response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
                return;
            }

            // Generate reset token
            User userWithToken = dao.generateResetToken(email);
            if (userWithToken != null) {
                // Send the reset token to the user's email
                String resetToken = userWithToken.getReset_token(); // Ensure the User model includes this field
                Timestamp tokenExpiryTime = userWithToken.getTokenExpiryTime();
                SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy 'at' hh:mm a z"); // Using 12-hour format with AM/PM
                String formattedTime = formatter.format(tokenExpiryTime);
                String resetLink = request.getRequestURL().toString().replace("HandleForgetPasswordServlet", "public/HTML/resetPassword.jsp") 
                        + "?token=" + resetToken;

                String subject = "Password Reset Request";
                String message = "Hello " + userWithToken.getName() + ",\n\n" + 
                		"This link will expire at: " + formattedTime + "\n\n"
                        + "We received a request to reset your password. Click the link below to reset it:\n"
                        + resetLink + "\n\n"
                        + "If you did not request this, please ignore this email.";

                boolean emailSent = Mail.sendEmail(email, subject, message); // Mail utility method
                if (emailSent) {
                    response.sendRedirect(request.getContextPath() + "/public/HTML/forgetPasswordSuccess.jsp");
                } else {
                    response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
        }
    }
}

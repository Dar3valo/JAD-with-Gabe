package Controllers.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String email = request.getParameter("email");
//
//        ForgetPasswordDAO dao = new ForgetPasswordDAO();
//        try {
//            // Check if email exists in the database
//            User checkExistingEmail = dao.checkExistingEmail(email);
//            if (checkExistingEmail == null) {
//            	response.sendRedirect(request.getContextPath()+"/public/HTML/error.jsp");
//                return;
//            }
//
//            // Generate reset token
//            User userWithToken = dao.generateResetToken(email);
//            if (userWithToken != null) {
//                response.sendRedirect(request.getContextPath()+"/public/HTML/forgetPasswordSuccess.jsp");
//            } else {
////                request.setAttribute("errorMessage", "Failed to generate reset token.");
////                RequestDispatcher rd = request.getRequestDispatcher("/public/HTML/error.jsp");
////                rd.forward(request, response);
//            	response.sendRedirect(request.getContextPath()+"/public/HTML/error.jsp");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.sendRedirect(request.getContextPath()+"/public/HTML/error.jsp");
//        }
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
                String resetLink = request.getRequestURL().toString().replace("HandleForgetPasswordServlet", "public/HTML/resetPassword.jsp") 
                        + "?token=" + resetToken;

                String subject = "Password Reset Request";
                String message = "Hello " + userWithToken.getName() + ",\n\n"
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

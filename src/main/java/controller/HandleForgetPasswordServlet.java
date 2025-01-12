package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ForgetPasswordDAO;
import model.User;

import java.io.IOException;

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
        String email = request.getParameter("email");

        ForgetPasswordDAO dao = new ForgetPasswordDAO();
        try {
            // Check if email exists in the database
            User checkExistingEmail = dao.checkExistingEmail(email);
            if (checkExistingEmail == null) {
                request.setAttribute("errorMessage", "Email not found!");
                RequestDispatcher rd = request.getRequestDispatcher("/public/HTML/error.jsp");
                rd.forward(request, response);
                return;
            }

            // Generate reset token
            User userWithToken = dao.generateResetToken(email);
            if (userWithToken != null) {
                request.setAttribute("successMessage", "Reset token successfully generated.");
                RequestDispatcher rd = request.getRequestDispatcher("/public/HTML/forgetPasswordSuccess.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to generate reset token.");
                RequestDispatcher rd = request.getRequestDispatcher("/public/HTML/error.jsp");
                rd.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An internal server error occurred.");
            RequestDispatcher rd = request.getRequestDispatcher("/public/HTML/error.jsp");
            rd.forward(request, response);
        }
    }
}

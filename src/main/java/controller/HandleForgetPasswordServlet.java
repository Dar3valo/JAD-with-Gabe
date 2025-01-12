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
            	response.sendRedirect(request.getContextPath()+"/public/HTML/error.jsp");
                return;
            }

            // Generate reset token
            User userWithToken = dao.generateResetToken(email);
            if (userWithToken != null) {
                response.sendRedirect(request.getContextPath()+"/public/HTML/forgetPasswordSuccess.jsp");
            } else {
//                request.setAttribute("errorMessage", "Failed to generate reset token.");
//                RequestDispatcher rd = request.getRequestDispatcher("/public/HTML/error.jsp");
//                rd.forward(request, response);
            	response.sendRedirect(request.getContextPath()+"/public/HTML/error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath()+"/public/HTML/error.jsp");
        }
    }
}

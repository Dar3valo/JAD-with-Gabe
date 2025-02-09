package Controllers.Feedback;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import Models.Feedback.FeedbackDAO;

/**
 * Servlet implementation class DeleteFeedbackServlet
 */
@WebServlet("/DeleteFeedbackServlet")
public class DeleteFeedbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteFeedbackServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		{ // check permission
        	request.setAttribute("pageAccessLevel", "1");
	        RequestDispatcher rd = request.getRequestDispatcher("/checkAccessServlet");
	        rd.include(request, response);
	        
	        Boolean hasAccess = (Boolean) session.getAttribute("accessCheckResult");
	        
	        if (hasAccess == null || !hasAccess) {
	            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
	            return;
	        }
        }
		
        try {
            // Get feedback_id from request parameter and validate
            String feedbackIdStr = request.getParameter("feedback_id");

            if (feedbackIdStr == null || feedbackIdStr.trim().isEmpty()) {
                session.setAttribute("errorMessage", "Feedback ID is missing.");
	            session.setAttribute("dashboardCurrentFocus", "feedback-report-content");
                response.sendRedirect(request.getContextPath() + "/public/HTML/dashboard.jsp");
                return;
            }

            // Parse the feedback_id
            int feedbackId = Integer.parseInt(feedbackIdStr.trim());

            if (feedbackId <= 0) {
                session.setAttribute("errorMessage", "Invalid feedback ID.");
	            session.setAttribute("dashboardCurrentFocus", "feedback-report-content");
                response.sendRedirect(request.getContextPath() + "/public/HTML/dashboard.jsp");
                return;
            }

            // Create DAO instance and attempt to delete the feedback
            FeedbackDAO feedbackDAO = new FeedbackDAO();
            boolean isDeleted = feedbackDAO.deleteFeedback(feedbackId);

            if (isDeleted) {
                session.setAttribute("successMessage", "Feedback successfully deleted.");
            } else {
                session.setAttribute("errorMessage", "Failed to delete feedback.");
            }

            session.setAttribute("dashboardCurrentFocus", "feedback-report-content");
            response.sendRedirect(request.getContextPath() + "/public/HTML/dashboard.jsp");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Invalid feedback ID format.");
            response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "An error occurred while deleting feedback.");
            response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

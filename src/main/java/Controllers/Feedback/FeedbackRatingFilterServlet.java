package Controllers.Feedback;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import Models.Feedback.Feedback;
import Models.Feedback.FeedbackDAO;

/**
 * Servlet implementation class FeedbackRatingFilterServlet
 */
@WebServlet("/FeedbackRatingFilterServlet")
public class FeedbackRatingFilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeedbackRatingFilterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    
	    try {
	        FeedbackDAO feedbackDAO = new FeedbackDAO();
	        List<Feedback> feedbackList;
	        
	        // Get the rating parameter from the request
	        String ratingParam = request.getParameter("rating");
	        
	        if (ratingParam != null && !ratingParam.isEmpty()) {
	            // If rating is specified, get filtered feedback
	            int rating = Integer.parseInt(ratingParam);
	            feedbackList = feedbackDAO.getFeedbackByRating(rating);
	            // Redirect with the rating parameter
	            session.setAttribute("feedbackList", feedbackList);
	            session.setAttribute("dashboardCurrentFocus", "feedback-report-content");
	            response.sendRedirect(request.getContextPath() + "/public/HTML/dashboard.jsp?rating=" + rating);
	        } else {
	            // If no rating specified, get all feedback
	            feedbackList = feedbackDAO.getAllFeedback();
	            session.setAttribute("feedbackList", feedbackList);
	            session.setAttribute("dashboardCurrentFocus", "feedback-report-content");
	            response.sendRedirect(request.getContextPath() + "/public/HTML/dashboard.jsp");
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("error", "An error occurred while processing your request.");
	        request.getRequestDispatcher("/public/HTML/error.jsp").forward(request, response);
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

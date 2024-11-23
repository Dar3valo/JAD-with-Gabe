package controller;

import jakarta.servlet.ServletException; 
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.FeedbackGetDAO;
import model.Feedback;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class TopThreeFeedbackServlet
 */
@WebServlet("/TopThreeFeedbackServlet")
public class TopThreeFeedbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopThreeFeedbackServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			FeedbackGetDAO feedbackDAO = new FeedbackGetDAO();
			List<Feedback> feedbackList = feedbackDAO.getFeedbackReviews();
			
			request.setAttribute("topFeedback", feedbackList);
			response.sendRedirect(request.getContextPath() + "/public/HTML/homePage.jsp");
		}catch(Exception e) {
			e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
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

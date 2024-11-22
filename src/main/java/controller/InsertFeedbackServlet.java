package controller;

import jakarta.servlet.ServletException; 
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Feedback;
import model.FeedbackDAO;


import java.io.IOException;

/**
 * Servlet implementation class InsertFeedbackServlet
 */
@WebServlet("/InsertFeedbackServlet")
public class InsertFeedbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertFeedbackServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 HttpSession session = request.getSession();
	        Object userIdObj = session.getAttribute("userId");

	        if (userIdObj == null) {
	            request.setAttribute("errorMessage", "You must be logged in to submit feedback.");
	            response.sendRedirect(request.getContextPath() + "/public/HTML/feedbackForm.jsp");
	            return;
	        }
		
		int userId = Integer.parseInt(request.getSession().getAttribute("loggedInUser").toString()); // Assuming logged-in user
		int rating = Integer.parseInt(request.getParameter("rating"));
		String[] sourcesArray = request.getParameterValues("sources");
		String sources = sourcesArray != null ? String.join(", ", sourcesArray) : "";
		String otherSources = request.getParameter("otherSources");
		String comments = request.getParameter("comments");
		String improvements = request.getParameter("improvements");
	        
		try {
			FeedbackDAO feedbackDAO = new FeedbackDAO();
			
				Feedback insertFeedback = feedbackDAO.insertUserFeedback(userId, rating, otherSources, sources, comments, improvements);
				
				if(insertFeedback != null) {
					response.sendRedirect(request.getContextPath() + "/public/HTML/feedbackForm.jsp");
				}else {
					request.setAttribute("errorMessage", "Feedback save failed.");
					response.sendRedirect(request.getContextPath() + "/public/HTML/feedbackForm.jsp");
				}
			
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "An unexpected error occured ;-;");
			response.sendRedirect(request.getContextPath() + "/public/HTML/feedbackForm.jsp");
		}
	}

}

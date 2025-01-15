package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException; 
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import Models.Feedback.Feedback;
import Models.Feedback.FeedbackDAO;
import Models.User.User;

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
        User userIdObj = (User) session.getAttribute("loggedInUser");

        if (userIdObj == null) {
            request.setAttribute("errorMessage", "You must be logged in to submit feedback.");
            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
            return;
        }
	
		int userId = userIdObj.getUser_id(); // Assuming logged-in user
		int rating = Integer.parseInt(request.getParameter("rating"));
		String[] sourcesArray = request.getParameterValues("sources");
		String sources = sourcesArray != null ? String.join(", ", sourcesArray) : "";
		String otherSources = request.getParameter("otherSources");
		String comments = request.getParameter("comments");
		String improvements = request.getParameter("improvements");
	        
		try {
			FeedbackDAO feedbackDAO = new FeedbackDAO();
			
				Feedback insertFeedback = feedbackDAO.insertUserFeedback(userId, rating, sources, otherSources, comments, improvements);
				
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

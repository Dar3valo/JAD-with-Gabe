package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import FeedbackModel.Feedback;
import FeedbackModel.FeedbackDAO;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        { // check permission
        	request.setAttribute("pageAccessLevel", "3");
	        RequestDispatcher rd = request.getRequestDispatcher("/checkAccessServlet");
	        rd.include(request, response);
	        
	        HttpSession session = request.getSession();
	        Boolean hasAccess = (Boolean) session.getAttribute("accessCheckResult");
	        
	        if (hasAccess == null || !hasAccess) {
	            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
	            return;
	        }
        }
        
		System.out.println("Servlet is being called"); // Debug line

		try {
			FeedbackDAO feedbackDAO = new FeedbackDAO();
			List<Feedback> feedbackList = feedbackDAO.getFeedbackReviews();

			// Debug lines
			if (feedbackList == null) {
				System.out.println("feedbackList is null");
			} else {
				System.out.println("Number of feedback items: " + feedbackList.size());
			}

			request.setAttribute("topFeedback", feedbackList);
			// request.getRequestDispatcher("/public/HTML/homePage.jsp").forward(request, response);
			response.sendRedirect(request.getContextPath() + "/public/HTML/homePage.jsp");

		} catch (Exception e) {
			System.out.println("Error in servlet: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", "An unexpected error occurred. Please try again.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

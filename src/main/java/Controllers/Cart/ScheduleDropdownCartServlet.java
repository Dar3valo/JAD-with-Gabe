package Controllers.Cart;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import Models.Schedule.Schedule;
import Models.Schedule.ScheduleDAO;

/**
 * Servlet implementation class ScheduleDropdownCartServlet
 */
@WebServlet("/ScheduleDropdownCartServlet")
public class ScheduleDropdownCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScheduleDropdownCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        
		try {
			List<Schedule> scheduleDropdown = ScheduleDAO.getAllSchedule();
			
			// Store services in session
            HttpSession session = request.getSession();
            session.setAttribute("scheduleTimings", scheduleDropdown);
            
            response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp");
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "An unexpected error occurred. Please try again.");
			response.sendRedirect(request.getContextPath() + "error.jsp");
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

package Controllers.Authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import Models.Role.RoleDAO;
import Models.User.User;

/**
 * Servlet implementation class checkAccessServlet
 */
@WebServlet("/checkAccessServlet")
public class checkAccessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checkAccessServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        
        HttpSession session = request.getSession();
        String input_pageAccessLevel = (String) request.getAttribute("pageAccessLevel");
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        
        if (input_pageAccessLevel == null) {
            session.setAttribute("accessCheckResult", false);
            return;
        }
        
        int pageAccessLevel = Integer.parseInt(input_pageAccessLevel);
        if (loggedInUser == null && pageAccessLevel != 3) {
            session.setAttribute("accessCheckResult", false);
            return;
        }
        
        boolean hasAccess = RoleDAO.userRoleAccess(loggedInUser, pageAccessLevel);
        session.setAttribute("accessCheckResult", hasAccess);
    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

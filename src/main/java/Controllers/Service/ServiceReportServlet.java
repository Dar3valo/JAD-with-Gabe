package Controllers.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import Models.ServiceReport.ServiceReport;
import Models.Service.ServiceDAO;

/**
 * Servlet implementation class ServiceReportServlet
 */
@WebServlet("/ServiceReportServlet")
public class ServiceReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServiceReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
            ServiceDAO serviceDAO = new ServiceDAO();
            List<ServiceReport> reports = serviceDAO.getServiceReport();

            HttpSession session = request.getSession();
            if (reports != null && !reports.isEmpty()) {
                session.setAttribute("serviceReports", reports);
            } else {
                session.setAttribute("error", "No service reports found.");
            }

            response.sendRedirect(request.getContextPath() + "/public/HTML/dashboard.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "An unexpected error occurred. Please try again.");
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

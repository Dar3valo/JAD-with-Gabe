package Controllers.Service;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import Models.ServiceCategory.ServiceCategoryDAO;

/**
 * Servlet implementation class EditServiceCategoryServlet
 */
@WebServlet("/EditServiceCategoryServlet")
public class EditServiceCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditServiceCategoryServlet() {
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
        	request.setAttribute("pageAccessLevel", "1");
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
			// read request parameters
			String formAction = request.getParameter("action");
			
			String category_id = request.getParameter("categoryId");
			int input_category_id = category_id != null ? Integer.parseInt(category_id) : 0;
			String input_category_name = request.getParameter("categoryName");
			String input_category_description = request.getParameter("categoryDescription");
			
			// general config
			int rowsAffected = 0;
			
			if (formAction.equals("update")) {
				rowsAffected = ServiceCategoryDAO.updateServiceCategoryById(input_category_id, input_category_name, input_category_description);
				
			} else if (formAction.equals("delete")) {
				rowsAffected = ServiceCategoryDAO.deleteServiceCategoryById(input_category_id);
				
			} else if (formAction.equals("create")) {
				rowsAffected = ServiceCategoryDAO.createServiceCategory(input_category_name, input_category_description);
				
			} else {
                throw new Exception("No appropriate associated form action found in parameter. \nform action given: " + formAction);
                
			}
			
			if (rowsAffected == 0) {
				throw new Exception("For some reason no rows changed :| maybe theres no data associated with the id? here is the information given in request:" +
			"\ncategoryId: " + input_category_id +
			"\ncategoryName: " + input_category_name +
			"\ncategoryDescription: " + input_category_description + "\n");
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/GetServiceInformationServlet?serviceCategory=0");
			dispatcher.forward(request, response);
			return;
			
		} catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher dispatcher = request.getRequestDispatcher("/GetServiceInformationServlet?serviceCategory=0");
    		dispatcher.forward(request, response);
    		return;
		}
	}

}
package Controllers.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import Models.User.User;
import Models.User.UserDAO;
import jakarta.servlet.annotation.MultipartConfig;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50 // 50MB
)

/**
 * Servlet implementation class UpdateProfilePicServlet
 */
@WebServlet("/UpdateProfilePicServlet")
public class UpdateProfilePicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateProfilePicServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
			return;
		}

		try {
			// Get the file from the form
			Part filePart = request.getPart("profileImage");
			if (filePart == null || filePart.getSize() == 0) {
				throw new ServletException("No file uploaded");
			}

			// Extract the file name from the Part object
			String originalFilename = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

			// Save the file to the server
			String uploadPath = getServletContext().getRealPath("/") + "Image/";
			File uploadDir = new File(uploadPath);

			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			String filename = originalFilename; // Use the original filename
			File file = new File(uploadDir + File.separator + filename);
			try (FileOutputStream fos = new FileOutputStream(file);
					InputStream fileInputStream = filePart.getInputStream()) {

				int bytesRead;
				byte[] buffer = new byte[1024];

				while ((bytesRead = fileInputStream.read(buffer)) != -1) {
					fos.write(buffer, 0, bytesRead);
				}

				// Update the user profile picture URL in the database
				UserDAO pfpDao = new UserDAO();
				String filePath = request.getContextPath() + "/Image/" + filename; // Add contextPath here
				User updatedUser = pfpDao.updateUserPfp(loggedInUser.getUser_id(), filePath);

				if (updatedUser != null) {
					loggedInUser.setProfile_photo_url(filePath);
					session.setAttribute("loggedInUser", loggedInUser);
					response.sendRedirect(request.getContextPath() + "/public/HTML/UserCRUD.jsp");
				} else {
					request.setAttribute("error", "Error updating profile picture.");
					request.getRequestDispatcher("/public/HTML/error.jsp").forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("error", "An error occurred during file processing: " + e.getMessage());
				request.getRequestDispatcher("/public/HTML/error.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "An error occurred: " + e.getMessage());
			request.getRequestDispatcher("/public/HTML/error.jsp").forward(request, response);
		}
	}
}

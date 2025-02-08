package Controllers.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet("/UploadServiceImageServlet")
@MultipartConfig
public class UploadServiceImageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UploadServiceImageServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Get the file part
            Part filePart = request.getPart("file");
            
            // Create multipart/form-data request to Spring Boot
            String springBootUrl = "http://localhost:8081/api/upload";
            URL url = new URL(springBootUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String boundary = Long.toHexString(System.currentTimeMillis());
            
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            
            try (OutputStream output = conn.getOutputStream();
                 PrintWriter writer = new PrintWriter(new OutputStreamWriter(output), true)) {
                
                writer.append("--" + boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"")
                      .append(filePart.getSubmittedFileName()).append("\"").append("\r\n");
                writer.append("Content-Type: ").append(filePart.getContentType()).append("\r\n");
                writer.append("\r\n");
                writer.flush();
                
                // Copy file data
                try (InputStream input = filePart.getInputStream()) {
                    byte[] buffer = new byte[4096];
                    int length;
                    while ((length = input.read(buffer)) > 0) {
                        output.write(buffer, 0, length);
                    }
                    output.flush();
                }
                
                writer.append("\r\n");
                writer.append("--" + boundary + "--").append("\r\n");
            }
            
            // Get response from Spring Boot
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                    StringBuilder responseContent = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseContent.append(line);
                    }
                    
                    // Parse URL from response (format: {"url":"http://..."})
                    String response_str = responseContent.toString();
                    String imageUrl = response_str.substring(
                        response_str.indexOf("\"url\":\"") + 7,
                        response_str.lastIndexOf("\"")
                    );
                    
                    // Send success response
                    response.setContentType("application/json");
                    response.getWriter().write("{\"url\":\"" + imageUrl + "\"}");
                    return;
                }
            }
            
            // If we get here, something went wrong
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Failed to upload service image\"}");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.getWriter().append("This servlet only handles POST requests for file uploads.");
    }
}
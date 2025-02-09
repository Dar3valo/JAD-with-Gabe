<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Models.Feedback.FeedbackDAO" %>
<%@ page import="Models.Feedback.Feedback" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Feedback List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .feedback-table {
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .rating-badge {
            padding: 6px 12px;
            border-radius: 20px;
            font-weight: 500;
        }
        .filter-section {
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 20px;
            padding: 20px;
        }
    </style>
</head>
<body class="bg-light">
    <%-- Navbar --%>
    <jsp:include page="navbar.jsp" />
    
    <div class="container py-5">
        <h2 class="text-center mb-4">Feedback List</h2>
        
        <!-- Filter Section -->
        <div class="filter-section">
            <form action="${pageContext.request.contextPath}/FeedbackRatingFilterServlet" method="get" class="row g-3 align-items-end">
                <div class="col-md-4">
                    <label for="rating" class="form-label">Filter by Rating</label>
                    <select name="rating" id="rating" class="form-select">
                        <option value="">All Ratings</option>
                        <option value="1" ${param.rating == '1' ? 'selected' : ''}>1 Star</option>
                        <option value="2" ${param.rating == '2' ? 'selected' : ''}>2 Stars</option>
                        <option value="3" ${param.rating == '3' ? 'selected' : ''}>3 Stars</option>
                        <option value="4" ${param.rating == '4' ? 'selected' : ''}>4 Stars</option>
                        <option value="5" ${param.rating == '5' ? 'selected' : ''}>5 Stars</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary">Apply Filter</button>
                </div>
            </form>
        </div>
        
        <div class="feedback-table p-3">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>User</th>
                            <th>Service</th>
                            <th>Rating</th>
                            <th>Comments</th>
                            <th>Date</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        // Get feedback list from session
                        List<Feedback> feedbackList = (List<Feedback>) session.getAttribute("feedbackList");
                        
                        // If feedbackList is null (first page load), get all feedback
                        if (feedbackList == null) {
                            FeedbackDAO feedbackDAO = new FeedbackDAO();
                            feedbackList = feedbackDAO.getAllFeedback();
                        }
                        
                        // Date formatter
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
                        
                        // Loop through feedback
                        if (feedbackList != null && !feedbackList.isEmpty()) {
                            for(Feedback feedback : feedbackList) {
                        %>
                            <tr>
                                <td>#<%=feedback.getFeedback_id()%></td>
                                <td><%=feedback.getUsername()%></td>
                                <td><%=feedback.getService_name()%></td>
                                <td>
                                    <span class="rating-badge text-white bg-<%= 
                                        feedback.getRating() >= 4 ? "success" : 
                                        feedback.getRating() >= 3 ? "warning" : 
                                        "danger" %>">
                                        <%=feedback.getRating()%> ★
                                    </span>
                                </td>
                                <td><%=feedback.getComments()%></td>
                                <td><%=dateFormat.format(feedback.getCreated_at())%></td>
                                <td>
                                    <!-- Delete Feedback Button -->
                                    <form action="${pageContext.request.contextPath}/DeleteFeedbackServlet" method="get" style="display:inline;">
                                        <input type="hidden" name="feedback_id" value="<%=feedback.getFeedback_id()%>">
                                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this feedback?');">
                                            Delete
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        <%
                            }
                        } else {
                        %>
                            <tr>
                                <td colspan="7" class="text-center py-4 text-muted">
                                    No feedback records found
                                </td>
                            </tr>
                        <%
                        }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    
    <%-- Footer --%>
    <jsp:include page="footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

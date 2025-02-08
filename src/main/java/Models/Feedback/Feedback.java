package Models.Feedback;

import java.sql.*;

public class Feedback {
	private int feedback_id;
	private int user_id;
	private int rating;
	private String sources;
	private String other_sources;
	private String comments;
	private String improvements;
	private int service_id;
	private String service_name;
	private Timestamp created_at;
	private String username;

	public Feedback(int user_id, int rating, String sources, String other_sources, String comments,
			String improvements, int service_id) {
		super();
		this.user_id = user_id;
		this.rating = rating;
		this.sources = sources;
		this.other_sources = other_sources;
		this.comments = comments;
		this.improvements = improvements;
		this.service_id = service_id;
	}
	
	public Feedback(int user_id, int rating, String sources, String other_sources, String comments,
			String improvements) {
		super();
		this.user_id = user_id;
		this.rating = rating;
		this.sources = sources;
		this.other_sources = other_sources;
		this.comments = comments;
		this.improvements = improvements;
	}
	
	public Feedback() {
		
	}

	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}
	
	public int getFeedback_id() {
		return feedback_id;
	}

	public void setFeedback_id(int feedback_id) {
		this.feedback_id = feedback_id;
	}
	
	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getSources() {
		return sources;
	}

	public void setSources(String sources) {
		this.sources = sources;
	}

	public String getOther_sources() {
		return other_sources;
	}

	public void setOther_sources(String other_sources) {
		this.other_sources = other_sources;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getImprovements() {
		return improvements;
	}

	public void setImprovements(String improvements) {
		this.improvements = improvements;
	}
	
	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}

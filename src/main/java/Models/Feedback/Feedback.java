package Models.Feedback;

public class Feedback {
	private int user_id;
	private int rating;
	private String sources;
	private String other_sources;
	private String comments;
	private String improvements;

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
}

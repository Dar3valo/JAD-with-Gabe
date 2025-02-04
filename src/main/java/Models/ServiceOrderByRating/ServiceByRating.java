package Models.ServiceOrderByRating;

public class ServiceByRating {
	private int id;
    private String name;
    private double averageRating;
    
    public ServiceByRating(int id, String name, double averageRating) {
		super();
		this.id = id;
		this.name = name;
		this.averageRating = averageRating;
	}
    
    //Default Constructor
    public ServiceByRating() {
    	
    }
    
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getAverageRating() {
		return averageRating;
	}
	
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
}

package model;
import java.util.List;
import java.util.ArrayList;

public class ServiceDAO {
	public static List<Service> getAllServices(){
		List<Service> services = new ArrayList<>();
		services.add(new Service(1, "Home"));
		services.add(new Service(2, "Office"));
		services.add(new Service(3, "Shower"));
		
		return services;
	}
}

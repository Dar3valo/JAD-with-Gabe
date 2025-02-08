package Models.ServiceReport;

import java.sql.Date;

public class ServiceReport {
	private int serviceId;
    private String serviceName;
    private double totalRevenue;
    private int totalBookings;
	
    public ServiceReport(int serviceId, String serviceName, double totalRevenue, int totalBookings) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.totalRevenue = totalRevenue;
        this.totalBookings = totalBookings;
    }

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public double getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public int getTotalBookings() {
		return totalBookings;
	}

	public void setTotalBookings(int totalBookings) {
		this.totalBookings = totalBookings;
	}
}
	
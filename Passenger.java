import java.util.ArrayList;

class Passenger {
	private String firstName;
	private String lastName;
	private ArrayList<String> alerts = new ArrayList<String>();
	private ArrayList<Flight> bFlights = new ArrayList<Flight>();
	private ArrayList<Flight> sFlights = new ArrayList<Flight>();
	public Passenger(String firstName, String lastName) {
		if(firstName == null)
			throw new RuntimeException("No null names allowed!");
		else if(firstName.trim().equals(""))
		{
			throw new RuntimeException("The name cannot be empty!");
		}
		else 
		{
			this.firstName = firstName;
		}
		
		if(lastName == null)
			throw new RuntimeException("No null names allowed!");
		else if(lastName.trim().equals(""))
		{
			throw new RuntimeException("The name cannot be empty!");
		}
		else 
		{
			this.lastName = lastName;
		}
	}
	public String getFirstName() {
		return this.firstName;
	}
	public String getLastName() {
		return this.lastName;
	}
	public ArrayList<String> getAlerts() {
		return this.alerts;
	}
	public ArrayList<Flight> getBookedFlights() {
		return this.bFlights;
	}
	public ArrayList<Flight> getStandbyFlights() {
		return this.sFlights;
	}
	public boolean bookFlight(Flight f) {
		if(f == null)
		{
			throw new RuntimeException("No null flights!");
		}
		else if(f.getBookedPassengers().size() >= f.getCapacity())
		{
			return false;
		}
		else
		{
			this.bFlights.add(f);
			f.addPassenger(this);
			return true;
		}
	}
	public boolean addStandbyFlight(Flight f) {
		this.sFlights.add(f);
		f.addStandbyPassenger(this);
		return true;
	}
	public void clearAlerts() {
		this.alerts.clear();
	}
	public void cancelFlight(Flight f) {
		this.bFlights.remove(f);
		this.sFlights.remove(f);
		f.removePassenger(this);
		f.removeStandbyPassenger(this);
		
	}
	public void cancelAll() {
		for(int i = 0; i < bFlights.size(); i++)
		{
			Flight f = bFlights.get(i);
			f.removePassenger(this);
		}
		for(int i = 0; i < bFlights.size(); i++)
		{
			Flight f = sFlights.get(i);
			f.removeStandbyPassenger(this);
		}
		this.bFlights.clear();
		this.sFlights.clear();
	}
	void addAlert(String s) {
		if(s == null)
		{
			throw new RuntimeException("No null alerts!");
		}
		else
		{
			this.alerts.add(s);
		}
	}
}
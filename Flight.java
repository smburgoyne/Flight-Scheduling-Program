import java.util.ArrayList;

class Flight {
	private String src;
	private String dest;
	private int takeoffTime;
	private int landingTime;
	private int capacity;
	private ArrayList<Passenger> bPassengers = new ArrayList<Passenger>();
	private ArrayList<Passenger> sPassengers = new ArrayList<Passenger>();
	
	public Flight(String src, String dest, int takeoffTime, int landingTime, int capacity) {
		
		int time1 = takeoffTime%1000;
		time1 = time1%100;
		int time2 = landingTime%1000;
		time2 = time2%100;
		
		if(src == null)
		{
			throw new RuntimeException("No null names allowed!");
		}
		else if(src.trim().equals(""))
		{
			throw new RuntimeException("The name cannot be empty!");
		}
		else 
		{	
			this.src = src;
		}
		if(dest == null)
		{
			throw new RuntimeException("No null names allowed!");
		}
		else if(dest.trim().equals(""))
		{
			throw new RuntimeException("The destination cannot be empty!");
		}
		else 
		{
			this.dest = dest;
		}
		if(takeoffTime < 0 || landingTime < 0 || takeoffTime > 2400 || landingTime > 2400)
		{
			throw new RuntimeException("Invalid Time.");
		}
		else if(time1 > 59)
		{
			throw new RuntimeException("Invalid Time");
		}
		else if(time2 > 59) 
		{
			throw new RuntimeException("Invalid Time");
		}
		else if(takeoffTime > landingTime)
		{
			throw new RuntimeException("Takeoff time must come before landing time!");
		}
		else 
		{
			this.takeoffTime = takeoffTime;
			this.landingTime = landingTime;
		}
		if(capacity <= 0)
		{
			throw new RuntimeException("Capacity cannot be negative or zero!");
		}
		else 
		{
			this.capacity = capacity;
		}
	}
	
	public String getSourceAirport() {
		return this.src;
	} 
	public String getDestinationAirport() {
		return this.dest;
	}
	public int getCapacity() {
		return this.capacity;
	}
	public int getTakeoffTime() {
		return this.takeoffTime;
	}
	public int getLandingTime() {
		return this.landingTime;
	}
	public ArrayList<Passenger> getBookedPassengers() {
		return this.bPassengers;
	}
	public ArrayList<Passenger> getStandbyPassengers() {
		return this.sPassengers;
	}
	
	public boolean addPassenger(Passenger p) {
		if(p == null)
		{
			throw new RuntimeException("No null passengers");
		}
		else if(this.getBookedPassengers().size() >= this.capacity)
		{
			return false;
		}
		else
		{
			this.bPassengers.add(p);
			return true;
		}
	}
	 public boolean addStandbyPassenger(Passenger p) {
			this.sPassengers.add(p);
			return true;
	 }
	 public void removePassenger(Passenger p) {
		 this.bPassengers.remove(p);
	 }
	 public void removeStandbyPassenger(Passenger p) {
		 this.sPassengers.remove(p);
	 }
	 public void cancel() {
		 while( this.bPassengers.size() > 0)
			{
				Passenger p = bPassengers.get(0);
				p.cancelFlight(this);
				p.addAlert(this.toString());
				this.bPassengers.remove(p);
			}
			while(sPassengers.size() > 0)
			{
				Passenger p = sPassengers.get(0);
				p.cancelFlight(this);
				p.addAlert(this.toString());
				this.sPassengers.remove(p);
			}
	 }
	 public int promotePassengers() {
		 int promoted = 0;
		 while(this.getBookedPassengers().size() < capacity && sPassengers.size() > 0)
		 {
				Passenger p = sPassengers.get(0);
				p.cancelFlight(this);
				p.bookFlight(this);
				p.addAlert("Your flight " + this.toString() + " was promoted");
				promoted++;
		 }
		 return promoted;
	 }

}
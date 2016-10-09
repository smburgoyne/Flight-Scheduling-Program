import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.*;
public class DataManager {
	public static void exportData(String filename, ArrayList<Passenger> passengers, ArrayList<Flight> flights) {
		
		PrintWriter outFile;
				
				try 
				{
					outFile = new PrintWriter(filename);
				} 
				catch (FileNotFoundException e) 
				{
					System.err.println("Cannot create the file.");
					return;
				}
				outFile.println("#flightCount " + flights.size());
				for(int h = 0; h < flights.size(); h++) 
				{
					outFile.println("#newFlight");
					Flight f = flights.get(h);
					outFile.println(f.getSourceAirport() + " , " + f.getDestinationAirport() + " , " + f.getTakeoffTime() + " , " + f.getLandingTime());
					outFile.println(f.getCapacity());
				}
				outFile.println("#passCount " + passengers.size());
				for(int i = 0; i < passengers.size(); i++)
				{
					outFile.println("#newPass");
					Passenger p = passengers.get(i);
					outFile.println(p.getFirstName() + " , " + p.getLastName());
					outFile.println(p.getAlerts().size());
					for(int j = 0; j < p.getAlerts().size(); j++)
					{
						outFile.println(p.getAlerts().get(j));
					}
					outFile.println(p.getBookedFlights().size());
					for(int k = 0; k < p.getBookedFlights().size(); k++)
					{
						Flight pf = p.getBookedFlights().get(k);
						outFile.println(pf.getSourceAirport() + " , " + pf.getDestinationAirport() + " , " + pf.getTakeoffTime() + " , " + pf.getLandingTime());
					}
					outFile.println(p.getStandbyFlights().size());
					for(int l = 0; l < p.getStandbyFlights().size(); l++)
					{
						Flight pf = p.getStandbyFlights().get(l);
						outFile.println(pf.getSourceAirport() + " , " + pf.getDestinationAirport() + " , " + pf.getTakeoffTime() + " , " + pf.getLandingTime());
					}
				}
				outFile.close();
	}
	 public static ImportData importData(String filename) {
		 ArrayList<Flight> flights = new ArrayList<Flight>();
		 ArrayList<Passenger> passengers = new ArrayList<Passenger>();
		 
		 Scanner input;
		try
		{
			input = new Scanner(new File(filename));
		} 
		catch (FileNotFoundException e)
		{
			System.err.println("File does not exist");
			return new ImportData(new ArrayList<Passenger>(), new ArrayList<Flight>());
		}
		 
			input.next();
			int numFlights = input.nextInt();
			input.nextLine();
			for(int i = 0; i < numFlights; i++)
			{
				input.nextLine();
				String Parameters = input.nextLine();
				String [] parameters = Parameters.split(" , ");
				String srcCode = parameters[0];
				String dstCode = parameters[1];
				int takeoff = Integer.parseInt(parameters[2]);
				int landing = Integer.parseInt(parameters[3]);
				int capacity = input.nextInt();
				input.nextLine();
				flights.add(new Flight(srcCode, dstCode, takeoff, landing, capacity));
			}
			input.next();
			int numPassengers = input.nextInt();
			input.nextLine();
			for(int i = 0; i < numPassengers; i++)
			{
				input.nextLine();
				String Parameters = input.nextLine();
				String [] parameters = Parameters.split(" , ");
				String firstName = parameters[0];
				String lastName = parameters[1];
				
				Passenger p = new Passenger(firstName, lastName);
				
				int numAlerts = input.nextInt();
				input.nextLine();
				for(int j = 0; j < numAlerts; j++) 
				{
					String alert = input.nextLine();
					p.addAlert(alert);
				}
				
				int numBooked = input.nextInt();
				input.nextLine();
		
				for(int k = 0; k < numBooked; k++)
				{
					String Ps = input.nextLine();
					String [] ps = Ps.split(" , ");
					String srcCode = ps[0];
					String dstCode = ps[1];
					int takeoff = Integer.parseInt(ps[2]);
					int landing = Integer.parseInt(ps[3]);
			
					for(int l = 0; l < flights.size(); l++)
					{
						if(srcCode.equals(flights.get(l).getSourceAirport()) && dstCode.equals(flights.get(l).getDestinationAirport()) && takeoff == flights.get(l).getTakeoffTime() && landing == flights.get(l).getLandingTime())
						{
							p.bookFlight(flights.get(l));
						}
					}
				}
				
				int numStandby = input.nextInt();
				input.nextLine();
	
				for(int l = 0; l < numStandby; l++)
				{
					String Pa = input.nextLine();
					String [] pa = Pa.split(" , ");
					String srcCode = pa[0];
					String dstCode = pa[1];
					int takeoff = Integer.parseInt(pa[2]);
					int landing = Integer.parseInt(pa[3]);
					for(int m = 0; m < flights.size(); m++)
					{
						if(srcCode.equals(flights.get(m).getSourceAirport()) && dstCode.equals(flights.get(m).getDestinationAirport()) && takeoff == flights.get(m).getTakeoffTime() && landing == flights.get(m).getLandingTime())
						{
							p.addStandbyFlight(flights.get(m));
						}
					}
				}
				passengers.add(p);
			}
			input.close();
			ImportData newData = new ImportData(passengers, flights);
			return newData;
	 }
}

import java.util.*;


public class Person {
	private String name;
	private String occupation;
	private String company;
	private String address;
	private ArrayList<Person> connections;
	
	public Person(String name, String occupation, String company, String address) {
		this.name = name;
		this.occupation = occupation;
		this.company = company;
		this.address = address;
		connections = new ArrayList<Person>();
	}

	public Person addConnection(Person p, HashMap<Person, ArrayList<Person>> network){
		connections.add(p);
		network.get(this).add(p);
		return this;
	}
	
	public String getName() {
		return name;
	}

	public String getOccupation() {
		return occupation;
	}

	public String getCompany() {
		return company;
	}


	public String getAddress() {
		return address;
	}


	public ArrayList<Person> getConnections() {
		return connections;
	}
	
	public void printPerson(){
		System.out.println(getName() + ", "+ getOccupation()+", "+getCompany()+", "+ getAddress());
		if (!connections.isEmpty()) {
			System.out.println("Connections");
			for (Person p : connections) {
				System.out.println(p.name);
			}
		}else{
			System.out.println(getName() + " has no connections.");
		}
	}
	
	public String toString(){
		return this.name;
	}
}


public class Node {
	public String name, company, city, position;	// Each person holds these attributes
	public int id;	// An ID to define each person
	
	public Node(String name, String position, String company, String city,  int id){
		this.name = name;
		this.company = company;
		this.city = city;
		this.position = position;
		this.id = id;
	}
	
	public String toString(){
		return "ID: " + Integer.toString(this.id) 
				+ ", name: " + this.name
				+ ", company: " + this.company
				+ ", city: " + this.city
				+ ", position: " + this.position;
	}
}

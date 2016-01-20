import java.util.*;

public abstract class Network {
	public ArrayList<Person> peopleInNetwork;
	public HashMap<Person, ArrayList<Person>> network;

	// adding a node in the graph
	public abstract void addPerson(Person person); 

	// adding an edge in the graph between two persons
	public abstract void addConnection(Person p1, Person p2);

	// get the neighbors of each node
	public abstract ArrayList<Person> getConnections(Person person); 
																	
	public abstract Person searchPerson(String name);

	public abstract int getNumberOfMutualFriends(String name1, String name2);

	public abstract void showPeopleInShortestLink(String name1, String name2);

	public abstract ArrayList<String> showSuggestedFriends(String name);

	public abstract void categorize();
	
	public void printNetwork(){
		for (Person p : network.keySet()) {
			p.printPerson();
		}
	}

}

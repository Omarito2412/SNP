import java.util.*;

public class SocialNetwork extends Network {

	public SocialNetwork() {
		network = new HashMap<>();
		networkCategory1 = new HashMap<>();
		networkCategory2 = new HashMap<>();
		peopleInNetwork = new ArrayList<Person>();
	}

	public void addPerson(Person person) {
		ArrayList<Person> connections = new ArrayList<Person>();
		network.put(person, connections);
		peopleInNetwork.add(person);
	}

	public void addConnection(Person p1, Person p2) {
		p1.addConnection(p2, network);
	}
	
	// Get all instances of Person in the network
	public ArrayList<Person> getPeopleInNetwork(){
		return this.peopleInNetwork;
	}
	public ArrayList<Person> getConnections(Person person) {
		return new ArrayList<>(network.get(person));
	}

	public Person searchPerson(String nameOfPerson) {
		Person person = binarySearch(nameOfPerson, network.keySet());
		return person;
	}

	public int getNumberOfMutualFriends(String name1, String name2) {
		// TODO Auto-generated method stub
		return 0;
	}

	public ArrayList<String> showPeopleInShortestLink(String name1, String name2) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<String> showSuggestedFriends(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void categorize() {
		// TODO Auto-generated method stub
		LoadBalancer Balancer = new LoadBalancer(this);

	}

	public void showPeopleInEachCategory() {
		// TODO Auto-generated method stub

	}

	private Person binarySearch(String nameOfPerson, Set<Person> keySet) {
		// sort the keySet of network based on name
		Person[] keyArray = sort(keySet);
		
		// search the sorted array
		int low = 0, high = keyArray.length - 1, mid = 0;
		while (low <= high) {
			mid = (high + low) / 2;
			int compare = nameOfPerson.compareTo(keyArray[mid].getName());
			if (compare < 0) {
				high = mid - 1;
			} else if (compare > 0) {
				low = mid + 1;
			} else {
				return keyArray[mid];
			}
		}
		return null;
	}

	private Person[] sort(Set<Person> keySet) {
		Person[] keyArray = new Person[keySet.size()];
		Arrays.sort((keySet.toArray(keyArray)), new Comparator<Person>() {
			public int compare(Person o1, Person o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		return keyArray;
	}
}

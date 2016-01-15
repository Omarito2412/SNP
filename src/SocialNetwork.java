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
		// Find the two persons
		Person person1 = this.searchPerson(name1);
		Person person2 = this.searchPerson(name2);
		
		// If any of which doesn't exist, return 0
		if(person1 == null || person2 == null){
			return 0;
		}
		
		// Get each person's connections
		ArrayList<Person> connections1 = network.get(person1);
		ArrayList<Person> connections2 = network.get(person2);
		
		// Get their intersection
		ArrayList<Person> common = connections1;
		common.retainAll(connections2);
		
		// Return their number
		return common.size();
	}

	public void showPeopleInShortestLink(String name1, String name2) {
		Person source = searchPerson(name1);
		Person target = searchPerson(name2);
		HashMap<Person, Person> path;
		path = searchBFS(source, target);

		// print the shortest link
		printPath(path, source, target);
	}

	public ArrayList<String> showSuggestedFriends(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void categorize() {
		// TODO Auto-generated method stub
		LoadBalancer Balancer = new LoadBalancer(this);
		System.out.println(Balancer.getCluster1());
		System.out.println(Balancer.getCluster2());
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


	private HashMap<Person, Person> searchBFS(Person person1, Person person2) {
		HashSet<Person> visited = new HashSet<>();
		HashMap<Person, Person> parent = new HashMap<Person, Person>();
		Queue<Person> queue = new LinkedList<Person>();
		queue.add(person1);
		visited.add(person1);
		while (!queue.isEmpty()) {
			Person cur = queue.remove();
			if (cur.getName().equals(person2.getName())) {
				return parent;
			}
			for (Person n : cur.getConnections()) {
				if (!visited.contains(n)) {
					visited.add(n);
					parent.put(n, cur);
					queue.add(n);
				}
			}

		}
		return null;
	}

	private void printPath(HashMap<Person, Person> path, Person source,
			Person target) {
		Stack<Person> stack = new Stack<>();
		stack.add(target);
		while (path.get(target) != null && path.get(target) != source) {
			stack.add(path.get(target));
			target = path.get(target);
		}

		while (!stack.empty()) {
			System.out.println(stack.pop().getName());
		}
}

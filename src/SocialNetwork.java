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
			System.out.println("One of the inputs could not be located in the network.");
			return -1;
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
		// find the source
		Person source = searchPerson(name1);
		
		// find the target
		Person target = searchPerson(name2);
		
		// print a message if neither the source or target is found in the network
		if(source == null || target == null) {
			System.out.println("One of the inputs could not be located in the network.");
			return;
		}
		
		// get path from source and target
		HashMap<Person, Person> path;
		path = searchBFS(source, target);

		// print a message if no path exists between the source and target 
		if(path == null){
			System.out.println("No connection is found between " + name1 + " and "+ name2);
			return;
		}
		
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
		
		// sort the names alphabetically 
		Arrays.sort((keySet.toArray(keyArray)), new Comparator<Person>() {
			public int compare(Person o1, Person o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		// return the sorted array
		return keyArray;
	}


	private HashMap<Person, Person> searchBFS(Person source, Person target) {
		
		// create a HashSet of the visited nodes in the network
		HashSet<Person> visited = new HashSet<>();
		
		// create a HashMap of the current node and its parent in the path
		HashMap<Person, Person> parent = new HashMap<Person, Person>();
		
		// create a queue for the bfs
		Queue<Person> queue = new LinkedList<Person>();
		
		// add source to the queue
		queue.add(source);
		
		// mark source as visited
		visited.add(source);
		
		// loop through the network till the target is reached
		while (!queue.isEmpty()) {
			
			// remove current node from queue
			Person cur = queue.remove();
			
			// return the parent HashMap if the current name matches the target name
			if (cur.getName().equals(target.getName())) {
				return parent;
			}
			
			// loop through the connections of the current node
			for (Person connection : cur.getConnections()) {
				if (!visited.contains(connection)) {
					
					// mark as visited if connection is not visited 
					visited.add(connection);
					
					// add current node as the parent of this connection
					parent.put(connection, cur);
					
					// add this connection to the queue
					queue.add(connection);
				}
			}

		}
		return null;
	}

	private void printPath(HashMap<Person, Person> path, Person source,
			Person target) {
		
		// create a stack to print the path in reverse order
		Stack<Person> stack = new Stack<>();
		
		// add target to stack 
		stack.add(target);
		
		// loop till the source is reached and the target is null
		while (path.get(target) != null && path.get(target) != source) {
			
			// add target to stack
			stack.add(path.get(target));
			
			// change the current target to the parent 
			target = path.get(target);
		}

		// print stack 
		while (!stack.empty()) {
			System.out.println(stack.pop().getName());
		}
	}
}
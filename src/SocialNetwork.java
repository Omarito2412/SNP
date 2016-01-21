import java.util.*;

public class SocialNetwork extends Network {

	public SocialNetwork() {
		network = new HashMap<>();
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

	public void showSuggestedFriends(String name) {
		Person source=searchPerson(name);

		Stack<String> suggestions;
		suggestions=suggestedBFS(source);

		printSuggestions(suggestions, source);
	}
	
	public void showSuggestedFriendsByCompany(String name) {
		Person source=searchPerson(name);

		Stack<String> suggestions;
		suggestions=suggestedByCompany(source);

		printSuggestions(suggestions, source);
	}

	public void categorize() {
		// TODO Auto-generated method stub
		LoadBalancer Balancer = new LoadBalancer(this);

		// print people in each cluster
		showPeopleInEachCategory(Balancer);
	}

	private void showPeopleInEachCategory(LoadBalancer Balancer) {
		// get cluster 1 and cluster 2
		ArrayList<Person> cluster1 = Balancer.getCluster1();
		ArrayList<Person> cluster2 = Balancer.getCluster2();

		// print the name of each person in cluster 1
		System.out.println("Cluster 1");
		for (Person person : cluster1) {
			System.out.print(person.getName() + ", ");
		}
		System.out.println("\n");

		// print the name of each person in cluster 2
		System.out.println("Cluster 2");
		for (Person person : cluster2) {
			System.out.print(person.getName() + ", ");
		}

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

	//Traverses network from a person and returns suggested friends
	//Within 5 links of reach
	private Stack<String> suggestedBFS(Person source) {

		// range of search
		int currentDepth = 0, 
				elementsToDepthIncrease = 1, 
				nextElementsToDepthIncrease = 0,
				maxDepth=5;

		// create a HashSet of the visited nodes in the network
		HashSet<Person> visited = new HashSet<>();

		// Keep a stack of all nodes
		Stack<String> suggestedFriends=new Stack<String>();

		// create a queue for the BFS
		Queue<Person> queue = new LinkedList<Person>();


		// add source to the queue
		queue.add(source);		

		// mark source as visited
		visited.add(source);

		// loop through all five links within range of the source
		while (!queue.isEmpty()) {

			// remove current node from queue
			Person current = queue.remove();

			nextElementsToDepthIncrease += current.getNumberOfChildren();

			//Check if depth has exceeded maxDepth and
			//return the parent HashMap if it has
			if (--elementsToDepthIncrease == 0) {
				if (++currentDepth > maxDepth)
					return suggestedFriends;
				elementsToDepthIncrease = nextElementsToDepthIncrease;
				nextElementsToDepthIncrease = 0;
			}

			// loop through the connections of the current node
			for (Person connection : current.getConnections()) {
				if (!visited.contains(connection)) {

					// mark as visited if connection is not visited 
					visited.add(connection);

					// add current node as the parent of this connection
					suggestedFriends.push(connection.getName());

					// add this connection to the queue
					queue.add(connection);
				}
			}

		}
		return suggestedFriends;
	}

	private Stack<String> suggestedByCompany(Person source) {
		// create a HashSet of the visited nodes in the network
		HashSet<Person> visited = new HashSet<>();

		// Keep a stack of all nodes
		Stack<String> suggestedFriends=new Stack<String>();

		// create a queue for the BFS
		Queue<Person> queue = new LinkedList<Person>();


		// add source to the queue
		queue.add(source);		

		// mark source as visited
		visited.add(source);

		// loop through all five links within range of the source
		while (!queue.isEmpty()) {

			// remove current node from queue
			Person current = queue.remove();

			// loop through the connections of the current node
			for (Person connection : current.getConnections()) {
				if (!visited.contains(connection)) {

					// mark as visited if connection is not visited 
					visited.add(connection);

					// add current node if the company name is the same
					if(connection.getCompany().equals(source.getCompany())){
						suggestedFriends.push(connection.getName());
					}

					// add this connection to the queue
					queue.add(connection);
				}
			}

		}
		return suggestedFriends;
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

	private void printSuggestions(Stack<String> suggestedFriends, Person source){
		for(Person connection: source.getConnections()){
			if(suggestedFriends.contains(connection.getName())){
				suggestedFriends.remove(connection.getName());
			}
		}

		while(!suggestedFriends.empty()){
			System.out.println(suggestedFriends.pop());
		}

	}
}
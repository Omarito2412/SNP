import java.util.*;

/*
 * This class is responsible for splitting and
 * balancing the network onto two clusters
 * Algorithm used: Karger's Minimum
 * Cut.
 */
public class LoadBalancer {
	private HashMap<Person, ArrayList<Person>> originalNet;
	private HashMap<Person, ArrayList<Person>> Cluster1;
	private HashMap<Person, ArrayList<Person>> Cluster2;
	
	public LoadBalancer(Network n) {
		this.originalNet = n.network;
		Contract();
	}
	
	public HashMap<Person, ArrayList<Person>> getCluster1(){
		return this.Cluster1;
	}
	
	public HashMap<Person, ArrayList<Person>> getCluster2(){
		return this.Cluster2;
	}
	// Contract the supplied Network
	public void Contract(){
		// Number of persons in the network and number of edges for that person
		int nodesCount=0, edgesCount=0;
		// Initialize a Random number generator
		Random RandomGen = new Random();
		/* The Algorithm will need to merge nodes together, to do this
		   we can use an ArrayList, where each list represents a new
		   super node 
		*/
		HashMap<ArrayList<Person>, ArrayList<Person>> Cut = new HashMap<ArrayList<Person>, ArrayList<Person>>();
		
		nodesCount = originalNet.size();
		Iterator i = originalNet.keySet().iterator();
		// This loop will map the original graph to the new cut graph
		while(i.hasNext()){
			// Construct an empty list
			ArrayList<Person> key = new ArrayList<Person>();
			// Get the next Person from the Keyset
			Person temp = (Person) i.next();
			// Add it to the list
			key.add(temp);
			// Now insert the list to the cut
			Cut.put(key, originalNet.get(temp));
		}
		
		int PersonPos, EdgePos;
		// Now to the main Algorithm
		while(nodesCount > 2){
			// Random nodes selection
			PersonPos = RandomGen.nextInt(nodesCount);
			EdgePos = RandomGen.nextInt(Cut.get(PersonPos).size());
			
			// The first node, a supernode
			ArrayList<Person> first = Cut.get(PersonPos);
			// The second node, a person
			Person secondPerson = Cut.get(PersonPos).get(EdgePos);
			
			// Now we need the second node's super node
			
			// Holder for the super node
			ArrayList<Person> second = null;
			i = Cut.keySet().iterator();
			while(i.hasNext()){
				if(Cut.get(i.next()).contains(secondPerson)){
					second = (ArrayList<Person>) i.next();
					break;
				}
			}
			
			// Now we have the two super nodes, MERGE THEM!
			// Make a set to keep unique persons from the two super nodes
			HashSet<Person> SuperNode = new HashSet<Person>();
			SuperNode.addAll(first);
			SuperNode.addAll(second);
			
			// A Set to keep unique edges
			ArrayList<Person> SuperEdge = new ArrayList<Person>();
			SuperEdge.addAll(Cut.get(first));
			SuperEdge.addAll(Cut.get(second));
			
			// Remove all the self loops
			SuperEdge.removeAll(SuperNode);

			// Turn the set to a list
			ArrayList<Person> temp = new ArrayList<Person>();
			temp.addAll(SuperNode);
			Cut.put(temp, SuperEdge);
			Cut.remove(first);
			Cut.remove(second);
			nodesCount--;
		}
		System.out.println("DONE");
		for(ArrayList<Person> k: Cut.keySet()){
			i = k.iterator();
			while(i.hasNext()){
				System.out.print(i.next() + " - ");
			}
			System.out.print("\n");
		}
	}
	
	

}

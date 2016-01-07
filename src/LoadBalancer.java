import java.util.*;
import java.math.*;

/*
 * This class is responsible for splitting and
 * balancing the network onto two clusters
 * Algorithm used: Karger's Minimum
 * Cut.
 */
public class LoadBalancer {
	private HashMap<Person, ArrayList<Person>> originalNet;
	private ArrayList<Person> Cluster1;
	private ArrayList<Person> Cluster2;
	private HashMap<ArrayList<Person>, ArrayList<Person>> Solution;
	
	public LoadBalancer(Network n) {
		// Retrieve network from the passed argument
		this.originalNet = n.network;
		// Initialize cost to a very high integer
		int cost = 9999999;
		// Integer Holder
		int temp = 0;
		// Keep the best solution
		HashMap<ArrayList<Person>, ArrayList<Person>> TempSolution = null;
		// Do i iterations and retrieve the best solution from them
		for(int i = 0; i < 10; i++){
			// Contract the network and store its cost
			temp = Contract();
			// If this cost is the minimum so far, save it
			if(temp < cost){
				cost = temp;
				TempSolution = this.Solution;
			}
		}
		// Minimum solution we found
		this.Solution = TempSolution;
//		System.out.println("Solution found with a cost of: " + cost);
		Iterator i = this.Solution.keySet().iterator();
		this.Cluster1 = (ArrayList<Person>) i.next();
		this.Cluster2 = (ArrayList<Person>) i.next();
		}
	
	public ArrayList<Person> getCluster1(){
		return this.Cluster1;
	}
	
	public ArrayList<Person> getCluster2(){
		return this.Cluster2;
	}
	// Contract the supplied Network
	public int Contract(){
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
		
		int PersonPos, EdgePos, cursor = 0;
		// Now to the main Algorithm
		while(nodesCount > 2){
			// Random node selection
			PersonPos = RandomGen.nextInt(nodesCount);
			
			
			// The first node, a supernode
			ArrayList<Person> first = null;
			
			i = Cut.keySet().iterator();
			cursor = 0;
			// Retrieve the random super node
			while(i.hasNext()){
				ArrayList<Person> temp = (ArrayList<Person>) i.next();
				if(cursor == PersonPos){
					first = temp;
					break;
				}
				cursor++;
			}
			// The second node, a person
			Person secondPerson = null;
			
			// Random edge selected
			EdgePos = RandomGen.nextInt(Cut.get(first).size());
			
			i = Cut.get(first).iterator();
			cursor = 0;
			// Pick a random person from the list
			while(i.hasNext()){
				Person temp = (Person) i.next();
				if(cursor == EdgePos){
					secondPerson = temp;
					break;
				}
				cursor++;
			}
			
			// Now we need the second node's super node
			
			// Holder for the super node
			ArrayList<Person> second = null;
			i = Cut.keySet().iterator();
			while(i.hasNext()){
				ArrayList<Person> temp = (ArrayList<Person>) i.next();
				
				if(temp.contains(secondPerson)){
					second = temp;
					break;
				}
			}
			
			// Now we have the two super nodes, MERGE THEM!
			// Make a set to keep unique persons from the two super nodes
			HashSet<Person> SuperNode = new HashSet<Person>();
			SuperNode.addAll(first);
			
			SuperNode.addAll(second);
			
			// A Set to keep unique edges
			HashSet<Person> SuperEdge = new HashSet<Person>();
			SuperEdge.addAll(Cut.get(first));
			SuperEdge.addAll(Cut.get(second));
			
			// Remove all the self loops
			SuperEdge.removeAll(SuperNode);

			// Turn the super node to a list
			ArrayList<Person> tempNode = new ArrayList<Person>();
			
			// Turn the super edge to a list
			ArrayList<Person> tempEdge = new ArrayList<Person>();
			
			tempNode.addAll(SuperNode);
			tempEdge.addAll(SuperEdge);
			Cut.remove(first);
			Cut.remove(second);
			Cut.put(tempNode, tempEdge);
			nodesCount--;
		}
//		for(ArrayList<Person> k: Cut.keySet()){
//			i = k.iterator();
//			while(i.hasNext()){
//				System.out.print(i.next() + " - ");
//			}
//			System.out.print("\n");
//		}
		this.Solution = Cut;
		return this.costFunction(Cut);
	}
	
	/* 
	 * Returns the "Cost" of this computed cut.
	 * This is to be used for minimizing the
	 * difference between the two clusters
	 * thus balancing the load of the
	 * network.
	 */
	
	private int costFunction(HashMap<ArrayList<Person>, ArrayList<Person>> Cut){
		Iterator i = Cut.keySet().iterator();
		int cost = 0;
		while(i.hasNext()){
			if(cost == 0){
				cost = ((ArrayList<Person>) i.next()).size();
			} else {
				cost -= ((ArrayList<Person>) i.next()).size();
			}
		}
		
		return Math.abs(cost);
	}
	

}

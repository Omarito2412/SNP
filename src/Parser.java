import java.io.*;
import java.util.*;

public class Parser {
	public Parser(String filePath){
		Scanner input = null;
		ArrayList<String> lines = new ArrayList<String>();	// All the file lines after iteration
		HashMap<Node, ArrayList<Node>> Graph = new HashMap<Node, ArrayList<Node>>();	// The Graph
		try {
			input = new Scanner(new FileReader(filePath));	// Init a file scanner
			String line = null;		// Line placeholder
			while(input.hasNextLine()){		// Iterate the whole file
				line = input.nextLine();	// hold the current line
				lines.add(line);			// Add line to the list
			}
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't load file: "+filePath); // File failed to load.
		}
		
		
		int nodesCount = Integer.parseInt(lines.get(0));	// The number of nodes from the input
		
		String holder = null;		// String holder for the node
		String[] properties = null; // The properties extracted from the line
		for(int i = 2; i < nodesCount + 2; i++){	// Start parsing nodesCount nodes from the lines
			// Each Node's ID will be the value of i, the line number it was defined on.
			holder = lines.get(i);	// Get a line from the Node definitons
			properties = holder.split(",");	// Split on the commas
			// Line is structured as: Name, Position, Company, City
			Node node = new Node(properties[0], properties[1], properties[2], properties[3], i);
			// Save to the Graph nodes and initialize empty connections
			Graph.put(node, new ArrayList<Node>());
		}
	}
}

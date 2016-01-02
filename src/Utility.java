import java.io.*;
import java.util.ArrayList;

public class Utility {

	// Method to load graph from a file
	public static SocialNetwork loadGraph(File file) throws IOException {

		// array to store each person information
		String[] personInfo = new String[4];

		String line = null;

		// array to store text file lines
		ArrayList<String> lines = new ArrayList<>();

		SocialNetwork socialNetwork = new SocialNetwork();
		BufferedReader bReader = new BufferedReader(new FileReader(file));

		// loop through the text file and add each line to the list of lines
		while ((line = bReader.readLine()) != null) {
			if (!line.isEmpty()) {
				lines.add(line);
			}
		}
		bReader.close();
		int numOfPeople = Integer.parseInt(lines.get(0));
		for (int i = 1; i <= numOfPeople; i++) {
			for (int j = 0; j < personInfo.length; j++) {
				personInfo[j] = lines.get(i).split(", ")[j];
			}

			// create a new person with the information stored in personInfo
			Person person = new Person(personInfo[0], personInfo[1],
					personInfo[2], personInfo[3]);
			socialNetwork.addPerson(person);

		}

		// add connections to the social network
		String[] connections;
		int indexOfPerson = 0;
		for (int i = numOfPeople + 1; i < lines.size(); i++) {
			connections = new String[lines.get(i).split(", ").length];
			connections = lines.get(i).split(", ");
			try {

				for (int j = 0; j < connections.length; j++) {
					socialNetwork.addConnection(
							socialNetwork.peopleInNetwork.get(indexOfPerson),
							socialNetwork.searchPerson(connections[j]));
				}
			} catch (NullPointerException e) {
				// throws a null pointer exception if the search method
				// returns null
				System.out
						.println("Can't find a specific person in the network.");
			}
			indexOfPerson++;
		}

		return socialNetwork;
	}
}

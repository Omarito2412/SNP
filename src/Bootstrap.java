import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Bootstrap {

	public static void main(String[] args) throws IOException {
		SocialNetwork socialNetwork = new SocialNetwork();
		socialNetwork = Utility.loadGraph(new File("input/data2.txt"));
//		System.out.println(socialNetwork.getNumberOfMutualFriends("C", "G"));
//		socialNetwork.showPeopleInShortestLink("Omar Essam", "Bill Gates");
//		socialNetwork.printNetwork();
//		socialNetwork.categorize();
		System.out.println("Welcome to Social Network Prototype");
		// Scanner to hold the input
		Scanner input = new Scanner(System.in);
		// A holder for the integer input
		int menu;
		// Holders for string inputs
		String first, second;
		// Start the interactive loop
		while(true){
			// Show instructions
			System.out.println(
					"To proceed please enter one of the following inputs:\n" +
					"0 To search for a person\n" +
					"1 To get mutual friends between two people\n" +
					"2 To show people on the shortest link between two people\n" +
					"3 To split the network onto two groups\n" +
					"4 To exit.\n"
					);
			// Read input
			menu = input.nextInt();
			input.nextLine();
			// Activate menu
			switch(menu){
			// Search for person
			case 0:
				System.out.println("Please enter the person's name");
				first = input.nextLine();
				Person result = socialNetwork.searchPerson(first);
				if(result == null){
					System.out.println("Sorry, An entry with the name " + first + " couldn't be found.");
				} else {
					result.printPerson();
				}
				input.nextLine();
				break;
			// Get Mutual friends
			case 1:
				System.out.println("Please enter the first person's name: ");
				first = input.nextLine();
				System.out.println("Please enter the second person's name: ");
				second = input.nextLine();
				if(socialNetwork.getNumberOfMutualFriends(first, second) != -1)
					System.out.println("They have: " + socialNetwork.getNumberOfMutualFriends(first, second) + " Mutual Friends");
				input.nextLine();
				break;
			// Show people on shortest link
			case 2:
				System.out.println("Please enter the first person's name: ");
				first = input.nextLine();
				System.out.println("Please enter the second person's name: ");
				second = input.nextLine();
				socialNetwork.showPeopleInShortestLink(first, second);
				input.nextLine();
				break;
			// Categorize
			case 3:
				socialNetwork.categorize();
				input.nextLine();
				break;
			case 4:
				System.out.println("It's sad to see you go :(");
				
				return;
			
			}
		}
	}

}
	
import java.io.File;
import java.io.IOException;


public class Bootstrap {

	public static void main(String[] args) throws IOException {
		SocialNetwork socialNetwork = new SocialNetwork();
		socialNetwork = Utility.loadGraph(new File("input/data2.txt"));
//		System.out.println(socialNetwork.getNumberOfMutualFriends("C", "G"));
		socialNetwork.showPeopleInShortestLink("Omar Essam", "Bill gates");
//		socialNetwork.printNetwork();
//		socialNetwork.categorize();
	}

}

import java.io.File;
import java.io.IOException;


public class Bootstrap {

	public static void main(String[] args) throws IOException {
		SocialNetwork socialNetwork = new SocialNetwork();
		socialNetwork = Utility.loadGraph(new File("input/data.txt"));
//		System.out.println(socialNetwork.getNumberOfMutualFriends("C", "G"));
//		socialNetwork.printNetwork();
//		socialNetwork.categorize();
	}

}

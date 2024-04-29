import java.util.Scanner;

public class Main {

    public static Scanner scan = new Scanner(System.in);

     // Prompt the user for a word.
     public static String PromptUserWord() {
        System.out.print("\nPlease input a word (ALL CAPS ONLY!): ");
        return scan.nextLine();
    }

    // Prompt the user for a choice on the type of graph
    public static boolean PromptUserChoice() {
        System.out.println("\nMap the BST into:");
        System.out.println("1.\t Directed Graph");
        System.out.println("2.\t Undirected Graph");
        System.out.print("Enter the Number choice here: "); 
        int choice = scan.nextInt();
        scan.nextLine();

        switch (choice) {
            case 1: return true;
            case 2: return false;
            default: return false;
        }
    }

    // Display options, used in the main loop of the program
    public static void DisplayOptions() {
		System.out.println("\n\n1. Display the BST in a tree format.");
		System.out.println("2. Display the Vertex array.");
		System.out.println("3. Display the Adjacency Matrix");
		System.out.println("4. Given a vertex: Display ALL possible separate paths starting with that vertex in a Depth First Search pattern: ");
		System.out.println("5. Given a vertex: Display ALL its adjacent vertices (one edge apart) ");
		System.out.println("6. Given a vertex: Display ALL the vertices that are two edges away from it: ");
		System.out.println("7. Exit");
	}

    // execute the choices that were listed in DisplayOptions()
    public static void ExecuteOption(Tree tree, Graph graph, int option) {
		
		switch(option) {
			case 1:
				tree.displayTree(); // ONLY TIME TREE CAN BE USED
				break;
			case 2:
			    System.out.println("\nVertex List:");	
                graph.displayVertexList();
				break;
			case 3:
                System.out.println("\nAdjacency Matrix:");
				graph.displayAdjMatrix();
				break;
			case 4:
                System.out.print("Enter the letter: ");
                graph.myDFS(getChar());
				break;
			case 5:
                System.out.print("Enter the letter: ");
                graph.getAdjacentVertecies(getChar());
				break;
			case 6:
                System.out.print("Enter the letter: ");
                graph.getVerteciesTwoEdgesAway(getChar());
				break;
			case 7:
				System.out.println("\nExiting...\n\n");
				break;
			default:
				System.out.println("Invalid entry");
		}
	}

    public static char getChar() {
		return scan.nextLine().charAt(0);
	}

    // Recursion to save the day. populates the graph in a recursive mannor using Nodes from the tree.
    public static void BSTtoGraph(Node node, Graph graph, int parentIndex) {
        if (node != null) {
            int index = graph.addVertex(node.iData);
            if (parentIndex != -1) {
                graph.addEdge(parentIndex, index); // index is the current vertex just added.
            }
            BSTtoGraph(node.leftChild, graph, index);
            BSTtoGraph(node.rightChild, graph, index);
         }
    }


    public static void main(String[] args) {
        Tree myTree = new Tree(); // instantiate the tree object
        
        String word = PromptUserWord(); // get the word from the user.
        
        // Formulate the tree object based on the word from user.
        for (int i = 0; i < word.length(); i++) {
            myTree.insert(word.charAt(i));
        }

        // prompt user what type of graph they want and instantiate the object
        Graph myGraph = new Graph(PromptUserChoice()); 

        // "map" the Tree into a graph.
        BSTtoGraph(myTree.getRoot(), myGraph, -1);
        
        // Main Program loop
        int value = 0;
		do {
			DisplayOptions();
			System.out.print("\nPlease pick a number corresponding to the options above: ");
			value = scan.nextInt();
			scan.nextLine();
			ExecuteOption(myTree, myGraph, value);
		} while (value != 7);



        scan.close();
    }
}
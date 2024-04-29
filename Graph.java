// Base code from the book: Data Structures & Algorithms in JAVA

// mst.java
// demonstrates minimum spanning tree
// to run this program: C>java MSTApp


// Edited by Edward Cruz, 
// Most edits or functions made by me can be found using ctrl-f then search "PA4"
// functions should be labled with thier corresponding Option, sorry if I forgot to label any in advance

// I used this line to compile (windows): javac .\Main.java .\Graph.java .\Tree.java .\Vertex.java .\Node.java
// Run: java Main
// StackX wasnt used anywhere else so I left it in this file, should create a .class with compilation of Graph.java


//////////////////////////////////////////////////////////////

class StackX {
    private final int SIZE = 20;
    private int[] st;
    private int top;

    // -------------------------------------------------------------
    public StackX() { // constructor
        st = new int[SIZE]; // make array
        top = -1;
    }

    // -------------------------------------------------------------
    public void push(int j) { // put item on stack
        st[++top] = j;
    }

    // -------------------------------------------------------------
    public int pop() { // take item off stack
        return st[top--];
    }

    // -------------------------------------------------------------
    public int peek() { // peek at top of stack
        return st[top];
    }

    // -------------------------------------------------------------
    public boolean isEmpty() { // true if nothing on stack
        return (top == -1);
    }
    // -------------------------------------------------------------
} // end class StackX


//////////////////////////////////////////////////////////////
public class Graph {
    private final int MAX_VERTS = 20;
    private Vertex vertexList[]; // list of vertices
    private int adjMat[][]; // adjacency matrix
    private int nVerts; // current number of vertices
    private StackX theStack;
    public boolean isDirected;

    // -------------------------------------------------------------
    public Graph() { // constructor
        vertexList = new Vertex[MAX_VERTS];
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        for (int j = 0; j < MAX_VERTS; j++) // set adjacency
            for (int k = 0; k < MAX_VERTS; k++) // matrix to 0
                adjMat[j][k] = 0;
        theStack = new StackX();
        isDirected = false;
    }
    
    // Made to allow the graph to be eiter directed or non-directed
    public Graph(boolean directed) { // my constructor
        vertexList = new Vertex[MAX_VERTS];
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        for (int j = 0; j < MAX_VERTS; j++) // set adjacency
            for (int k = 0; k < MAX_VERTS; k++) // matrix to 0
                adjMat[j][k] = 0;
        theStack = new StackX();
        isDirected = directed;
    }

    // -------------------------------------------------------------
    // edited this to be able to get O(1) edge mapping on the BST to Graph portion.
    public int addVertex(char lab) {
        vertexList[nVerts++] = new Vertex(lab);
        return nVerts-1; // just subtracted 1 to not modify the book's code much.
    }

    // -------------------------------------------------------------
    // edited to conform with whether or not its a directed graph
    public void addEdge(int start, int end) { 
        if (isDirected) { // added logic for the directed graph, also a snippet from the book.
            adjMat[start][end] = 1;
        } else { // default logic for undirected graph
            adjMat[start][end] = 1;
            adjMat[end][start] = 1;
        }
        
    }

    // -------------------------------------------------------------
    public void displayVertex(int v) {
        System.out.print(vertexList[v].label);
    }

    // -------------------------------------------------------------
    public void mst() { // minimum spanning tree (depth first)
        // start at 0
        vertexList[0].wasVisited = true; // mark it
        theStack.push(0); // push it

        while (!theStack.isEmpty()) { // until stack empty
            int currentVertex = theStack.peek(); // get next unvisited neighbor
            int v = getAdjUnvisitedVertex(currentVertex);
            if (v == -1) { // if no more neighbors
                theStack.pop(); // pop it away
            } else { // got a neighbor
                vertexList[v].wasVisited = true; // mark it
                theStack.push(v); // push it
                // display edge
                displayVertex(currentVertex); // from currentV
                displayVertex(v); // to v
                System.out.print(" ");
            }
        }
        // stack is empty, so we’re done
        for (int j = 0; j < nVerts; j++) // reset flags
            vertexList[j].wasVisited = false;
    }
    // -------------------------------------------------------------
    
    // Grabbed this from the book serperatly from the rest of program
    // might endup redundent, if so version used for assignment is under this function.
    public void dfs() { // depth-first search
        // begin at vertex 0
        vertexList[0].wasVisited = true; // mark it
        displayVertex(0); // display it
        theStack.push(0); // push it
    
        while (!theStack.isEmpty()) { // until stack empty,
            // get an unvisited vertex adjacent to stack top
            int v = getAdjUnvisitedVertex(theStack.peek());
            if (v == -1) { // if no such vertex,
                theStack.pop(); // pop a new one
            } else { // if it exists,
                vertexList[v].wasVisited = true; // mark it
                displayVertex(v); // display it
                theStack.push(v); // push it
            }
        } // end while
    
        // stack is empty, so we’re done
        for (int j = 0; j < nVerts; j++) { // reset flags
            vertexList[j].wasVisited = false;
        }
    } // end dfs

    // Programming Assignment 4 - PA4 - Option 4
    // should do the same as the one above from the book, but allows user to specify starting vertex.
    public void myDFS(char label) {
        int startingIndex = getVertexIndex(label); // get starting index
        if (startingIndex == -1) { // error check
            System.out.println("Letter could not be found!");
            return;
        }
        
        char[] path = new char[nVerts]; // No String built in functions allowed, back to C :(
        int pathLength = 0; // to keep track of how many vertex's are relevant
        
        vertexList[startingIndex].wasVisited = true; // mark it
        path[pathLength++] = vertexList[startingIndex].label; // starting vertex
        //displayVertex(startingIndex); // display it
        theStack.push(startingIndex); // push it

        
        while (!theStack.isEmpty()) { // until stack empty,
            // get an unvisited vertex adjacent to stack top
            int v = getAdjUnvisitedVertex(theStack.peek());
            if (v == -1) { // if no such vertex,
                theStack.pop(); // pop a new one
                pathLength--; // reduce path length for backtracking
            } else { // if it exists,
                vertexList[v].wasVisited = true; // mark it
                path[pathLength++] = vertexList[v].label; // Extend the path
                //displayVertex(v); // display it
                theStack.push(v); // push it

                // next vertex is unvisited, print line
                if (getAdjUnvisitedVertex(v) == -1) { 
                    for (int i = 0; i < pathLength; i++) {
                        System.out.print(path[i]); // Output the current path
                    }
                    System.out.println(); // New line for next path iteration
                }
                
            }
        } // end while
    
        // stack is empty, so we’re done
        for (int j = 0; j < nVerts; j++) { // reset flags
            vertexList[j].wasVisited = false;
        }
    }

    // -------------------------------------------------------------
    public int getAdjUnvisitedVertex(int v) {
        for (int j = 0; j < nVerts; j++)
            if (adjMat[v][j] == 1 && !vertexList[j].wasVisited)
                return j;
        return -1;
    }
    // -------------------------------------------------------------


    // Programming Assignment 4 - PA4

    // Option 2 - PA4
    public void displayVertexList() {
        for (int i = 0; i < nVerts; i++) {
            displayVertex(i); 
            System.out.print(" ");
        }
        System.out.println("");
    }

    // Option 3 - PA4
    public void displayAdjMatrix() {
        System.out.print("  ");
        displayVertexList(); // header row 
        for (int i = 0; i < nVerts; i++) {
            displayVertex(i); // displays the letter mapping for current row.
            System.out.print(" ");
            for (int j = 0; j < nVerts; j++) {
                System.out.print(adjMat[i][j] + " ");
            }
            System.out.println(); // Move to the next line after printing all columns of a row
        }
    }

    // Utility function for finding index of an existing vertex.
    private int getVertexIndex(char label) {
        for (int i = 0; i < nVerts; i++) {
            if (label == vertexList[i].label) {
                return i;
            }
        }
        return -1;
    }

    // Option 5 - PA4
    // Ended up being the dame as dfs, just specified a starting vertex and 
    // didnt push to stack to only see surrounding vertecies.
    public void getAdjacentVertecies(char label) {
        System.out.print("Adjacent Vertecies: ");
        
        int index = getVertexIndex(label);
        vertexList[index].wasVisited = true; // mark it
        theStack.push(index); // push it
        // no need to display the fist vertex, we just want the adjancents
        
        while (!theStack.isEmpty()) { // until stack empty,
            // get an unvisited vertex adjacent to stack top
            int v = getAdjUnvisitedVertex(theStack.peek());
            if (v == -1) { // if no such vertex,
                theStack.pop(); // pop a new one
            } else { // if it exists,
                vertexList[v].wasVisited = true; // mark it
                displayVertex(v); // display it
                // same as dfs but we dont push to stack
            }
        } // end while

        // stack is empty, so we’re done
        for (int j = 0; j < nVerts; j++) { // reset flags
            vertexList[j].wasVisited = false;
        }
    }


    // Option 6 - PA4
    // Pretty much the same as option 5, just added a counter and flag check.
    // Counter keeps track of edges passed, only displays if = 2
    // Flag displays message to user if none were found. Only happened on directed graphs in my testing.
    public void getVerteciesTwoEdgesAway(char label) {
        System.out.print("Vertecies Two Edges away: ");
        
        int index = getVertexIndex(label);
        vertexList[index].wasVisited = true; // mark it
        theStack.push(index); // push it
        int edgeCounter = 0; // edge counter.
        
        boolean criteriaMet = false; // flag for message if neccsary   
        while (!theStack.isEmpty()) { // until stack empty,
            // get an unvisited vertex adjacent to stack top
            int v = getAdjUnvisitedVertex(theStack.peek());
            if (v == -1) { // if no such vertex,
                theStack.pop(); // pop a new one
                edgeCounter--; // went back an edge
            } else { // if it exists,
                vertexList[v].wasVisited = true; // mark it
                edgeCounter++; // edge passed
                if (edgeCounter == 2) {
                    displayVertex(v); // display it
                    criteriaMet = true; // there was a vertex 2 edges away.
                }
                theStack.push(v); // push it
                
            }
        } // end while

        // flag to display according message
        if (criteriaMet == false) {
            System.out.print("Criteria was not met.");
        }

        // stack is empty, so we’re done
        for (int j = 0; j < nVerts; j++) { // reset flags
            vertexList[j].wasVisited = false;
        }
    }


} // end class Graph

//////////////////////////////////////////////////////////////
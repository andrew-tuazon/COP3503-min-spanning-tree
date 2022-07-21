import java.util.*;
import java.lang.Math;

public class RodBinding
{
	private static final String EMPTY = null;
	//Number of rods, columns, and rows respectively
	public static int n, c, r;
	//Final answer in double form
	public static double ans;
	
	//Edge class for undirected graph
	public static class Edge implements Comparable<Edge>
	{
		int dest;
		double weight;
		
		//Edge constructor
		Edge(int dest, double weight)
		{
			this.dest = dest;
			this.weight = weight;
		}
		
		@Override //Sorts in ascending
		public int compareTo(Edge o)
		{
			return Double.compare(weight, o.weight);
		}
	}
	
	//Rod class for weight calculation
    public static class Rod implements Comparable<Rod> 
    {
        int x, y;
        
        //Rod constructor
        Rod(int x, int y)
        {
        	this.x = x;
        	this.y = y;
        }
        
        @Override //Sorts in ascending
        public int compareTo(Rod o) {
            if (x == o.x)
                return Integer.compare(y, o.y);
            return Integer.compare(x, o.x);
        }
    } 
	
    //Get the weight between two rods using distance formula
	public static double getWeight(Rod r1, Rod r2)
	{
		return Math.sqrt(Math.pow((r1.x - r2.x), 2) + Math.pow((r1.y - r2.y), 2));
		
	}
	
	//Minimum spanning tree based off Prim's Algorithm
	public static void MST(ArrayList<Edge>[] edgeList)
	{
        // Create the set of connected nodes containing the node 0
        TreeSet<Integer> connected = new TreeSet<Integer>();
        connected.add(0);

        // Add all the initial edges to the edge heap
        PriorityQueue<Edge> edgeHeap = new PriorityQueue<Edge>();
        for (Edge e : edgeList[0]) {
            edgeHeap.add(e);
        }

        // Initialize the answer to 0
        ans = 0.0;

        // Loop until everything is connected
        while (connected.size() != n)
        {
        	// Get the potential edge
        	Edge poEdge = edgeHeap.poll();

            // Skip if edge is redundant
            if (connected.contains(poEdge.dest))
            {
            	continue;
            }

            // Update the answer
            ans += poEdge.weight;

            // Add the edge to our set and update the edge heap
            connected.add(poEdge.dest);
            for (Edge e : edgeList[poEdge.dest])
            {
                if (!connected.contains(e.dest))
                {
                    edgeHeap.add(e);
                }
            }
        }
    }
	
	public static void main(String[] args)
	{
		//Read in the input
		Scanner sc =  new Scanner(System.in);
		n = sc.nextInt();
		c = sc.nextInt();
		r = sc.nextInt();
		
		//Get x and y placement of rods
		int[] xPos = new int[n];
		int[] yPos = new int[n];
		
		ArrayList<Rod> rodList = new ArrayList<>(n);
		for(int i = 0; i < n; i++)
		{
			xPos[i] = sc.nextInt() - 1;
			yPos[i] = sc.nextInt() - 1;
			
			rodList.add(new Rod(xPos[i], yPos[i]));
		}
		
		//Sort rods in ascending order
		Collections.sort(rodList);
		
		//Create placeholder memory for edge list
		ArrayList<Edge>[] edgeList = new ArrayList[n]; 
        for (int i = 0; i < n; i++)
        {
            edgeList[i] = new ArrayList<Edge>(n - 1);
        }
        
        //Update edges and weights based on rods
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < n; j++)
			{
				if(j == i)
				{
					continue;
				}
				edgeList[i].add(new Edge(j , getWeight(rodList.get(i), rodList.get(j))));
				edgeList[j].add(new Edge(i , getWeight(rodList.get(i), rodList.get(j))));
			}
		}
		
		//Find MST of fixed rods
		MST(edgeList);
		
		//Make adjacency list
		ArrayList<Edge>[] adjList = new ArrayList[c];
		for(int i = 0; i < c; i++)
		{
			adjList[i] = new ArrayList<Edge>(r);
		}
		
		for(int i = 0; i < c; i++)
		{
			for(int j = 0; j < r; j++)
			{
				
			}
		}
		
		//Try all possible rod placements
		for(int i = 0; i < c; i++)
		{
			for(int j = 0; j < r; j++)
			{
				MST(adjList);
			}
		}
			
		System.out.println(ans);
		
		sc.close();
	}

	
}
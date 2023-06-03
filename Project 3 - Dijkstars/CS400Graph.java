
//--== CS400 File Header Information ==--
//Name: Kruthiventi Shyama Subrahmanya Nikhil
//Email: skruthiventi@wisc.edu
//Team: DO
//TA: Rahul Choudhary
//Lecturer: Florian Heimerl
//Notes to Grader: <optional extra notes>
//import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.NoSuchElementException;

public class CS400Graph<NodeType,EdgeType extends Number> implements BusStopGraphInterface<NodeType,EdgeType> {

 /**
  * Vertex objects group a data field with an adjacency list of weighted
  * directed edges that lead away from them.
  */
 protected class Vertex {
     public NodeType data; // vertex label or application specific data
     public LinkedList<Edge> edgesLeaving;

     public Vertex(NodeType data) {
         this.data = data;
         this.edgesLeaving = new LinkedList<>();
     }
 }

 /**
  * Edge objects are stored within their source vertex, and group together
  * their target destination vertex, along with an integer weight.
  */
 protected class Edge implements Comparable<Edge>{
     public Vertex target;
     public EdgeType weight;

     public Edge(Vertex target, EdgeType weight) {
         this.target = target;
         this.weight = weight;
     }

     /**
      * Allows the natural ordering of Edges to be increasing with Edge Weights.
      * When Edge Weights are equal, the string comparison of end vertex data is used to break ties.
      * @param other is the other Edge that is being compared to this one
      * @return -1 when this Edge has a smaller weight than the other,
      *         +1 when this Edge has a larger weight that the other,
      *         and the comparison of end vertex data in string form when these distances are tied
      */
 
     public int compareTo(Edge other) 
     {
         int cmp = Integer.compare((int)this.weight, (int)other.weight);
         if(cmp != 0) return cmp; // use Edge weight as the natural ordering
         // when Edge weights are equal, break ties by comparing the string
         // representation of data in the end vertex of each Edge
         return this.target.data.toString().compareTo(other.target.data.toString());
     }
 }

 protected Hashtable<NodeType, Vertex> vertices; // holds graph verticies, key=data
 public CS400Graph() { vertices = new Hashtable<>(); }

 /**
  * Insert a new vertex into the graph.
  * 
  * @param data the data item stored in the new vertex
  * @return true if the data can be inserted as a new vertex, false if it is 
  *     already in the graph
  * @throws NullPointerException if data is null
  */
 public boolean insertVertex(NodeType data) {
     if(data == null) 
         throw new NullPointerException("Cannot add null vertex");
     if(vertices.containsKey(data)) return false; // duplicate values are not allowed
     vertices.put(data, new Vertex(data));
     return true;
 }
 
 /**
  * Remove a vertex from the graph.
  * Also removes all edges adjacent to the vertex from the graph (all edges 
  * that have the vertex as a source or a destination vertex).
  * 
  * @param data the data item stored in the vertex to remove
  * @return true if a vertex with *data* has been removed, false if it was not in the graph
  * @throws NullPointerException if data is null
  */
 public boolean removeVertex(NodeType data) {
     if(data == null) throw new NullPointerException("Cannot remove null vertex");
     Vertex removeVertex = vertices.get(data);
     if(removeVertex == null) return false; // vertex not found within graph
     // search all vertices for edges targeting removeVertex 
     for(Vertex v : vertices.values()) {
         Edge removeEdge = null;
         for(Edge e : v.edgesLeaving)
             if(e.target == removeVertex)
                 removeEdge = e;
         // and remove any such edges that are found
         if(removeEdge != null) v.edgesLeaving.remove(removeEdge);
     }
     // finally remove the vertex and all edges contained within it
     return vertices.remove(data) != null;
 }
 
 /**
  * Insert a new directed edge with a positive edge weight into the graph.
  * 
  * @param source the data item contained in the source vertex for the edge
  * @param target the data item contained in the target vertex for the edge
  * @param weight the weight for the edge (has to be a positive integer)
  * @return true if the edge could be inserted or its weight updated, false 
  *     if the edge with the same weight was already in the graph
  * @throws IllegalArgumentException if either source or target or both are not in the graph, 
  *     or if its weight is < 0
  * @throws NullPointerException if either source or target or both are null
  */
 public boolean insertEdge(NodeType source, NodeType target, EdgeType weight) {
     if(source == null || target == null) 
         throw new NullPointerException("Cannot add edge with null source or target");
     Vertex sourceVertex = this.vertices.get(source);
     Vertex targetVertex = this.vertices.get(target);
     if(sourceVertex == null || targetVertex == null) 
         throw new IllegalArgumentException("Cannot add edge with vertices that do not exist");
     if(weight.doubleValue() < 0) 
         throw new IllegalArgumentException("Cannot add edge with negative weight");
     // handle cases where edge already exists between these verticies
     for(Edge e : sourceVertex.edgesLeaving)
         if(e.target == targetVertex) {
             if(e.weight.doubleValue() == weight.doubleValue()) return false; // edge already exists
             else e.weight = weight; // otherwise update weight of existing edge
             return true;
         }
     // otherwise add new edge to sourceVertex
     sourceVertex.edgesLeaving.add(new Edge(targetVertex,weight));
     return true;
 }    
 
 /**
  * Remove an edge from the graph.
  * 
  * @param source the data item contained in the source vertex for the edge
  * @param target the data item contained in the target vertex for the edge
  * @return true if the edge could be removed, false if it was not in the graph
  * @throws IllegalArgumentException if either source or target or both are not in the graph
  * @throws NullPointerException if either source or target or both are null
  */
 public boolean removeEdge(NodeType source, NodeType target) {
     if(source == null || target == null) throw new NullPointerException("Cannot remove edge with null source or target");
     Vertex sourceVertex = this.vertices.get(source);
     Vertex targetVertex = this.vertices.get(target);
     if(sourceVertex == null || targetVertex == null) throw new IllegalArgumentException("Cannot remove edge with vertices that do not exist");
     // find edge to remove
     Edge removeEdge = null;
     for(Edge e : sourceVertex.edgesLeaving)
         if(e.target == targetVertex) 
             removeEdge = e;
     if(removeEdge != null) { // remove edge that is successfully found                
         sourceVertex.edgesLeaving.remove(removeEdge);
         return true;
     }
     return false; // otherwise return false to indicate failure to find
 }
 
 /**
  * Check if the graph contains a vertex with data item *data*.
  * 
  * @param data the data item to check for
  * @return true if data item is stored in a vertex of the graph, false otherwise
  * @throws NullPointerException if *data* is null
  */
 public boolean containsVertex(NodeType data) {
     if(data == null) throw new NullPointerException("Cannot contain null data vertex");
     return vertices.containsKey(data);
 }
 
 /**
  * Check if edge is in the graph.
  * 
  * @param source the data item contained in the source vertex for the edge
  * @param target the data item contained in the target vertex for the edge
  * @return true if the edge is in the graph, false if it is not in the graph
  * @throws NullPointerException if either source or target or both are null
  */
 public boolean containsEdge(NodeType source, NodeType target) {
     if(source == null || target == null) throw new NullPointerException("Cannot contain edge adjacent to null data"); 
     Vertex sourceVertex = vertices.get(source);
     Vertex targetVertex = vertices.get(target);
     if(sourceVertex == null) return false;
     for(Edge e : sourceVertex.edgesLeaving)
         if(e.target == targetVertex)
             return true;
     return false;
 }
 
 /**
  * Return the weight of an edge.
  * 
  * @param source the data item contained in the source vertex for the edge
  * @param target the data item contained in the target vertex for the edge
  * @return the weight of the edge (a Number that represents 0 or a positive value)
  * @throws IllegalArgumentException if either sourceVertex or targetVertex or both are not in the graph
  * @throws NullPointerException if either sourceVertex or targetVertex or both are null
  * @throws NoSuchElementException if edge is not in the graph
  */
 public EdgeType getWeight(NodeType source, NodeType target) {
     if(source == null || target == null) throw new NullPointerException("Cannot contain weighted edge adjacent to null data"); 
     Vertex sourceVertex = vertices.get(source);
     Vertex targetVertex = vertices.get(target);
     if(sourceVertex == null || targetVertex == null) throw new IllegalArgumentException("Cannot retrieve weight of edge between vertices that do not exist");
     for(Edge e : sourceVertex.edgesLeaving)
         if(e.target == targetVertex)
             return e.weight;
     throw new NoSuchElementException("No directed edge found between these vertices");
 }
 
 /**
  * Return the number of edges in the graph.
  * 
  * @return the number of edges in the graph
  */
 public int getEdgeCount() {
     int edgeCount = 0;
     for(Vertex v : vertices.values())
         edgeCount += v.edgesLeaving.size();
     return edgeCount;
 }
 
 /**
  * Return the number of vertices in the graph
  * 
  * @return the number of vertices in the graph
  */
 public int getVertexCount() {
     return vertices.size();
 }

 /**
  * Check if the graph is empty (does not contain any vertices or edges).
  * 
  * @return true if the graph does not contain any vertices or edges, false otherwise
  */
 public boolean isEmpty() {
     return vertices.size() == 0;
 }

 /**
  * Path objects store a discovered path of vertices and the overal distance of cost
  * of the weighted directed edges along this path. Path objects can be copied and extended
  * to include new edges and verticies using the extend constructor. In comparison to a
  * predecessor table which is sometimes used to implement Dijkstra's algorithm, this
  * eliminates the need for tracing paths backwards from the destination vertex to the
  * starting vertex at the end of the algorithm.
  */
 protected class Path implements Comparable<Path> 
 {
     public Vertex start; // first vertex within path
     public double distance; // sumed weight of all edges in path
     public List<NodeType> dataSequence; // ordered sequence of data from vertices in path
     public Vertex end; // last vertex within path

     /**
      * Creates a new path containing a single vertex.  Since this vertex is both
      * the start and end of the path, it's initial distance is zero.
      * @param start is the first vertex on this path
      */
     public Path(Vertex start) {
         this.start = start;
         this.distance = 0.0D;
         this.dataSequence = new LinkedList<>();
         this.dataSequence.add(start.data);
         this.end = start;
     }

     /**
      * This extension constructor makes a copy of the path passed into it as an argument
      * without affecting the original path object (copyPath). The path is then extended
      * by the Edge object extendBy. Use the doubleValue() method of extendBy's weight field
      * to get a double representation of the edge's weight.
      * @param copyPath is the path that is being copied
      * @param extendBy is the edge the copied path is extended by
      */
     public Path(Path copyPath, Edge extendBy) 
     {
         //Initializing the start Vertex of the new Path with the copyPath's Start Vertex
         this.start = copyPath.start; 
         //Adding the edge weight to the Path distance 
         this.distance = extendBy.weight.doubleValue() + copyPath.distance;

         //Creating a new Linkedlist to store the new Path's DataSequence 
         this.dataSequence = new LinkedList<>();

         //Copying the CopyPath's DataSequence into the new DataSequence variable 
         for(int size=0; size<copyPath.dataSequence.size();size++)
         {
             this.dataSequence.add(copyPath.dataSequence.get(size));
         }

         this.dataSequence.add(extendBy.target.data); //Adding Target of the edge to the DataSequence

         //Equating the end of this Path to the Target of the extendBy edge
         this.end = extendBy.target;

         // TODO: Implement this constructor in Step 5.
     }

     /**
      * Allows the natural ordering of paths to be increasing with path distance.
      * When path distance is equal, the string comparison of end vertex data is used to break ties.
      * @param other is the other path that is being compared to this one
      * @return -1 when this path has a smaller distance than the other,
      *         +1 when this path has a larger distance that the other,
      *         and the comparison of end vertex data in string form when these distances are tied
      */
     public int compareTo(Path other) {
         int cmp = Double.compare(this.distance, other.distance);
         if(cmp != 0) return cmp; // use path distance as the natural ordering
         // when path distances are equal, break ties by comparing the string
         // representation of data in the end vertex of each path
         return this.end.data.toString().compareTo(other.end.data.toString());
     }
 }

 /**
  * Uses Dijkstra's shortest path algorithm to find and return the shortest path 
  * between two vertices in this graph: start and end. This path contains an ordered list
  * of the data within each node on this path, and also the distance or cost of all edges
  * that are a part of this path.
  * @param start data item within first node in path
  * @param end data item within last node in path
  * @return the shortest path from start to end, as computed by Dijkstra's algorithm
  * @throws NoSuchElementException when no path from start to end can be found,
  *     including when no vertex containing start or end can be found
  */
 protected Path dijkstrasShortestPath(NodeType start, NodeType end) 
 {
     //This condition checks if the start and the end vertex are present in the graph
     if(!containsVertex(start)||!containsVertex(end))
     {
        throw new NoSuchElementException("No such start or end vertex is present in the graph");
     }
     //This condition checks if the parameters start and end vertices are null
     if (start == null || end == null) {
         throw new NoSuchElementException("Vertices cannot have null data");
     }
     
     /**This condition checks if the start and the end vertex are the same
      * if they are the same then the path just consists that vertex
      */
     if(start.equals(end))
     {
         return new Path(vertices.get(start));
     }
     
     //As instructed, this priority queue stores all the paths that were discovered
     PriorityQueue<Path> discoveredpath = new PriorityQueue<>();
     // A linkedlist which is used to store all the visited nodes/vertices
     LinkedList<NodeType> visitednodes = new LinkedList<>();

     Path shortestpath = null;//A variable that stores the shortest path
     
     discoveredpath.add(new Path(vertices.get(start)));//Initializing the path with the start node and adding it to the priority queue

     while(discoveredpath.size()!=0)
     {
         Path tempPath = discoveredpath.remove(); //Assigning the first path to tempPath 

         visitednodes.add(tempPath.end.data);//Adding the visited vertex to the linked list

         List<Edge> Leavingedges = tempPath.end.edgesLeaving;// Stores the leaving edges from the end vertex 
         //Traversing through the list
         for(int i =0; i<Leavingedges.size();i++)
         {
             Edge a = Leavingedges.get(i);
             NodeType b = a.target.data;
             Path tempPath2 = new Path(tempPath,a); //Using the constructor to create a new path with the leaving edges 
             
             //checks if the node is aldready visited.If yes, then continue
             if(visitednodes.contains(b))
             {
                 continue;
             }

             //Adding the path to the Priority Queue
             discoveredpath.add(tempPath2);

             if(b.equals(end))
             {
                 if(shortestpath==null)//checks if the Shortest Path is null
                 {
                     shortestpath=tempPath2;
                 }
                 //Compares the current path and the shortest path. If current path is less than the Shortest path
                 //Then the current path becomes the shortest Path
                 else if(tempPath2.compareTo(shortestpath)<=0)
                 {
                     shortestpath = tempPath2;
                 }
             }

         }

     }
     //If the shortest path is 0, then Throw an exception to indicate that a shortest Path does not exist
     if(shortestpath == null)
     {
         throw new NoSuchElementException("No shortest path exists from Start to end node");
     }
     else
     {
         return shortestpath;// returns the shortest path
     } 
     // TODO: Implement this method in Step 7.
 }
 
 /**
  * Returns the shortest path between start and end.
  * Uses Dijkstra's shortest path algorithm to find the shortest path.
  * 
  * @param start the data item in the starting vertex for the path
  * @param end the data item in the destination vertex for the path
  * @return list of data item in vertices in order on the shortest path between vertex 
  * with data item start and vertex with data item end, including both start and end 
  * @throws NoSuchElementException when no path from start to end can be found
  *     including when no vertex containing start or end can be found
  */
 public List<NodeType> shortestPath(NodeType start, NodeType end) {
     return dijkstrasShortestPath(start,end).dataSequence;
 }
 
 /**
  * Returns the cost of the path (sum over edge weights) between start and end.
  * Uses Dijkstra's shortest path algorithm to find the shortest path.
  * 
  * @param start the data item in the starting vertex for the path
  * @param end the data item in the end vertex for the path
  * @return the cost of the shortest path between vertex with data item start 
  * and vertex with data item end, including all edges between start and end
  * @throws NoSuchElementException when no path from start to end can be found
  *     including when no vertex containing start or end can be found
  */
 public double getPathCost(NodeType start, NodeType end) {
     return dijkstrasShortestPath(start, end).distance;
 }
 /**
	 * implements Prim's algorithm for the minimum spanning tree
	 * 
	 * @param start is the Node that the minimum spanning tree algorithm with begin at
	 * @return list of Edges that represents the MST
 */
 @Override
 public List<EdgeType> MST(NodeType start) 
 {
     List<EdgeType> edgesweight = new LinkedList<>();// To store the edge weights
     List<Edge> edges = new LinkedList<>();//To store the edges
     List<Vertex> visited = new LinkedList<>(); // To store the visited vetices
     Vertex d = vertices.get(start); // Stores the starting vertex
     visited.add(d); //Add the starting vertex to visited list
     for(int i=0;i<vertices.size()-1;i++)// This runs the loop V-1 times 
     {
         // To store the edges in the Priority Queue to find the edge with the minimum weight
         PriorityQueue<Edge> pq = new PriorityQueue<>();
         //This is to iterate through the visited nodes to find the edges which could be part of the MST
         for(int j=0;j<visited.size();j++)
         {
             List<Edge> temp = visited.get(j).edgesLeaving;// A list of all the edges leaving that vertex
             for(int k=0;k<temp.size();k++)
             {
                 Edge temp2 = temp.get(k);
                 //If the target vertex of the edge is aldready visited, skip it
                 if(visited.contains(temp2.target))
                 {
                     continue;
                 }
                 //Else add it to the priority queue
                 else
                 {
                     pq.add(temp2);
                 }
             }
         }
         Edge minimumEdge = pq.remove();//Stroing the minimum edge
         visited.add(minimumEdge.target);// Adding the Target of the minimum edge to visted list
         edges.add(minimumEdge); // Add the minimum edge to the Edge list
         edgesweight.add(minimumEdge.weight);//Add the minimum edge weight to the edge weight list
         
         
     }
     return edgesweight;
 }
 /**
	 * Takes a list of edges and returns the total weight of all edges in the list
	 * 
	 * @param edges list of edges
	 * @return total edge weight in list
 */
 @Override
 public double getEdgeWeightsSum(List<EdgeType> edges) 
 {
     double sum = 0; // To store the sum
     for(int i=0;i<edges.size();i++)
     {
         sum = sum + edges.get(i).doubleValue();
     }   
     return sum;
 }	
 
 
 
}
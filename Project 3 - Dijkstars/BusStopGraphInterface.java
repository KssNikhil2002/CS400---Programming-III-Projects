
import java.util.List;

public interface BusStopGraphInterface<NodeType,EdgeType extends Number>  extends GraphADT<NodeType,EdgeType> {

	/**
	 * implements Prim's algorithm for the minimum spanning tree
	 * 
	 * @param start is the Node that the minimum spanning tree algorithm with begin at
	 * @return list of Edges that represents the MST
	 */
	public List<EdgeType> MST(NodeType start);
	
	/**
	 * Takes a list of edges and returns the total weight of all edges in the list
	 * 
	 * @param edges list of edges
	 * @return total edge weight in list
	 */
	public double getEdgeWeightsSum(List<EdgeType> edges);
	
}


import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AlgorithmEngineerTests 
{
	public BusStopGraphInterface<String,Integer> graph;
    
    /**
     * Instantiate graph.
     */
	//Here this function creates a hardcoded graph 
    
    public void createGraph() 
    {
        graph = new CS400Graph<>();
        // inserting different bustops which are represented as vertices
        graph.insertVertex("University Avenue and Ridge");
        graph.insertVertex("Union South");
        graph.insertVertex("East Campus Mall");
        graph.insertVertex("College Library");
        graph.insertVertex("Lark At Khol");
        graph.insertVertex("RegentSt");
        graph.insertVertex("University Hospital");
        graph.insertVertex("West Feilds");


        // inserting different paths betweeen busstops which are represented as edges
        graph.insertEdge("University Avenue and Ridge","Union South",10);
        graph.insertEdge("Union South","East Campus Mall",25);
        graph.insertEdge("East Campus Mall","Lark At Khol",22);
        graph.insertEdge("Lark At Khol","RegentSt",12);
        graph.insertEdge("RegentSt","University Hospital",16);
        graph.insertEdge("University Hospital","West Feilds",14); 
        graph.insertEdge("West Feilds","University Avenue and Ridge",24);
        graph.insertEdge("East Campus Mall","College Library",11);
        graph.insertEdge("College Library","East Campus Mall",15);
    }
    
    /**This Test1 checks for the functionality of insertVertex() and insertEdge(). Furthermore, this test
     * also checks for the functionality of getVertextCount() and getEdgeCount() by observing if the count increases
     * by 1 after insertion of vertext and Edge
    */
    @Test
    public void IndividualRoletest1()
    {
    	createGraph();
    	assertEquals(graph.getVertexCount(),8);
    	graph.insertVertex("State Street");
    	assertEquals(graph.getVertexCount(),9);
    	assertEquals(graph.getEdgeCount(),9);
    	graph.insertEdge("College Library","State Street", 23);
    	assertEquals(graph.getEdgeCount(),10);
    }
    
    /**The Test2 checks for the functionality of removeVertex() and removeEdge(). Furthermore, this test
     * uses the functionality of getVertextCount() and getEdgeCount() by observing if the count decreases
     * by 1 after deletion of vertext and Edge to check the the correctness.
    */
    @Test
    public void IndividualRoletest2()
    {
    	createGraph();
    	assertEquals(graph.getEdgeCount(),9);
    	assertEquals(graph.getVertexCount(),8);
    	graph.removeEdge("College Library","East Campus Mall");
    	graph.removeVertex("College Library");
    	assertEquals(graph.getEdgeCount(),7);
    	assertEquals(graph.getVertexCount(),7);
    }
    
    /** The Test3 checks the functionality of the implemented MST() function and checks if it returns the correct array 
     *  of edge weights of the edges part of the minimum spanning tree when the starting vertex is passed as a parameter.
     */
    
    @Test
    public void IndividualRoletest3()
    {
    	createGraph();
    	assertEquals(graph.MST("University Avenue and Ridge").toString(),
                "[10, 25, 11, 22, 12, 16, 14]");
    }
    
    
    /**
     * This Test4 checks for the functionality of the implemented getEdgeWeightsSum() function and checks if 
     * it returns the correct sum of edge weights when a list of edge weights is passed as a parameter.
     */
    @Test
    public void IndividualRoletest4()
    {
    	createGraph();
    	assertEquals(graph.MST("College Library").toString(),
                "[15, 22, 12, 16, 14, 24, 10]");
    	assertEquals(graph.getEdgeWeightsSum(graph.MST("College Library")),113);
    }
    
    /**
     * The Test5 checks for the functionality of the functions containsVertex() when a vertex is passed as a parameter, 
     * containsEdge() when an edge is passed as a parameter and getWeight() when the edge is passes as a parameter.
     * The functions containsVertex() and containsEdge() returns true when a vertex present in the graph is passed as a parameter
     * and function getWeight() returns the weight of the edge. 
     */
    
    @Test
    public void IndividualRoletest5()
    {
    	createGraph();
    	
    	assertEquals(graph.containsVertex("Lark At Khol"),true);
    	assertEquals(graph.containsEdge("Lark At Khol","RegentSt"),true);
    	assertEquals(graph.getWeight("Lark At Khol","RegentSt"),12);
    	
    }
    
    /**
     * This test checks the functionality of the LoadData() function of the DataWrangler.
     * Here it checks if the 2D array returned by the LoadData() function is the correct adjacency matrix 
     * of the graph created in the .gv file by DataWrangler. 
     */
    @Test
    public void CodeReviewOfDataWranglerTest1()
    {
    	File gvFile = new File("route1.gv");
        IDataLoader dataLoader = new DataLoader();
        float[][] adjmatrix = dataLoader.LoadData(gvFile);
        assertEquals(adjmatrix[0][1],3f);
        assertEquals(adjmatrix[0][2],7f);
        assertEquals(adjmatrix[0][3],1f);
        assertEquals(adjmatrix[1][4],2f);
        assertEquals(adjmatrix[1][5],4f);
        assertEquals(adjmatrix[2][5],5f);
        assertEquals(adjmatrix[3][4],6f);
    }
    
    /**
     * This tests checks if the adjmatrix returned by LoadData() is null 
     * when an invalid file is provded as a parameter for the function 
     */
    @Test
    public void CodeReviewOfDataWranglerTest2()
    {
    	try {
    		
    	}catch(Exception e)
    	{
    		File gvFile = new File("invalid.gv");
            IDataLoader dataLoader = new DataLoader();
            float[][] adjmatrix = dataLoader.LoadData(gvFile);
            assertNull(adjmatrix);
    	}
        
    }
    
    /**
     * This test checks for the functionality of the MST() function and the getEdgeWeightsSum() function
     * after integrating with the DataWrangler. 
     * It checks if the MST() outputs the correct list of edge weights present in the minimum spanning tree generated
     * when "A" is provided as the starting vertex and also checks if  getEdgeWeightsSum() outputs the correct minimum 
     * cost of 17
     */
    @Test
    public void Integrationtest1()
    {
        File gvFile = new File("route1.gv");
        DataLoader dataLoader = new DataLoader();
        float[][] edgeWidths = dataLoader.LoadData(gvFile);
        graph = dataLoader.helperGraph(edgeWidths); 
        assertEquals(graph.MST("A").toString(),"[1, 3, 2, 4, 7]");
        assertEquals(graph.getEdgeWeightsSum(graph.MST("A")),17);
    }
    
    
    /**
     * This function checks for the functionality of insertVertex( and insertEdge() after integrating
     * with the Data Wrangler. Furthermore, this test also checks for the functionality of getVertextCount() and getEdgeCount() 
     * by observing if the count increases by 1 after insertion of vertext and Edge.
     * Then Checks for the shortest path and the path cost of that path when the starting vertex is "A" and target vertex
     * is "G" 
     */
    @Test
    public void Integrationtest2()
    {
        File gvFile = new File("route1.gv");
        DataLoader dataLoader = new DataLoader();
        float[][] edgeWidths = dataLoader.LoadData(gvFile);
        graph = dataLoader.helperGraph(edgeWidths);
        assertEquals(graph.getVertexCount(),6);
        assertEquals(graph.getEdgeCount(),7);
        graph.insertVertex("G");
        assertEquals(graph.getVertexCount(),7);
        graph.insertEdge("F","G", 10);
        assertEquals(graph.getEdgeCount(),8);
        assertTrue(graph.shortestPath("A", "G").toString().equals("[A, B, F, G]"));
        assertTrue(graph.getPathCost("A", "G") == 15);
    }
    
    
    
    
    
    

}

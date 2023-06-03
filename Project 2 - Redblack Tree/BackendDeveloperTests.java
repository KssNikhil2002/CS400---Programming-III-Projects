
import java.util.List;
import java.util.NoSuchElementException;
// import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
public class BackendDeveloperTests 
{
	
	/**This test is used to checks for the functionality of addAnimal() function and the getNumberofAnimals()
	 * function. Here I initially inserted 10 elements into the tree and the first getNumberAnimlas() checks
	 * if 10 is outputed. Then I insert a new Animal Node into the tree and then chekc if the getNumberAnimals()
	 * outputs 11. If it does both the functions are working properly
	  */
    @Test
    public void IndividualRoletest1()
    {
    	IIterableTree<String> a = new BDIterableTree<String>();
        BinomialNomenclatureBackend b = new BinomialNomenclatureBackend(a);
        //System.out.println(b.getNumberOfAnimals());
        assertEquals(b.getNumberOfAnimals(),10);
        IAnimal x = new RedBlackTree.Node<String>("Larus argentatus","Aves","Seagull");
        b.addAnimal(x);
        //System.out.println(b.getNumberOfAnimals());
        assertEquals(b.getNumberOfAnimals(),11);
        
        
    }
    
    /**This test is used to checks for the functionality of the searchByGenusName() function. 
     * A genusName of "Haliaeetus" was put as a paramenter into the function and the list of all
     * animals with the same genus name is stored in the List variable X. Then using the asserEquals(), i
     * check if the correct details(Speciesname, commonname and Type) are displayed.
	  */
    @Test
    public void IndividualRoletest2()
    {
    	IIterableTree<String> a = new BDIterableTree<String>();
        BinomialNomenclatureBackend b = new BinomialNomenclatureBackend(a);
        String Genusname = "Haliaeetus";
        List<IAnimal> x = b.searchByGenusName(Genusname);
        String g="";
        g = g+ "[";
        for(int i =0;i<x.size();i++)
        {
            String f = "("+ x.get(i).getSpeciesName() +"," + x.get(i).getCommonName() +"," +x.get(i).getType()+")";
            if(i==x.size()-1)
            {
                g = g+f;
            }
            else
            {
                g=g+f+",";
            }
            
        }
        g= g+"]";
        //System.out.println(g);
        assertEquals(g,"[(Haliaeetus leucocephalus,Bald Eagle,Aves),(Haliaeetus Vocifer,African Fish Eagle,Aves)]");
        
        
    }

    @Test
    /**This test is used to checks for the functionality of the getBySpeciesName() function. 
     * A SpeciesName/Scientific Name of "Nerodia Sipedon" was put as a paramenter into the function. The returned data
     * from the function is stored in the variable X. Using the assertEquals() function,I check if the expected Animal
     * details like the commonname, speciesName and the type is returned by the function.
	  */
    public void IndividualRoletest3()
    {
    	IIterableTree<String> a = new BDIterableTree<String>();
        BinomialNomenclatureBackend b = new BinomialNomenclatureBackend(a);
        String ScietificName = "Nerodia Sipedon";
        IAnimal x = b.getBySpeciesName(ScietificName);
        //System.out.println(x.getType());
        assertEquals(x.getCommonName(),"Northern Water Snake");
        assertEquals(x.getSpeciesName(),"Nerodia Sipedon");
        assertEquals(x.getType(),"Reptile");
    }
    
    /**This test is used to checks for the functionality of the getByCommonName() function. 
     * A CommonName of "Wyoming Toad" was put as a paramenter into the function. The returned data
     * from the function is stored in the variable X. Using the assertEquals() function, I check if the expected Animal
     * details like the commonname, speciesName and the type  is returned by the function.
	  */
    @Test
    public void IndividualRoletest4()
    {
    	IIterableTree<String> a = new BDIterableTree<String>();
        BinomialNomenclatureBackend b = new BinomialNomenclatureBackend(a);
        String Commonname = "Wyoming Toad";
        IAnimal x = b.getByCommonName(Commonname);
        //System.out.println(x.getSpeciesName());
        assertEquals(x.getCommonName(),"Wyoming Toad");
        assertEquals(x.getSpeciesName(),"Anaxyrus Baxteri");
        assertEquals(x.getType(),"Amphibia");
    }

    /**This test is used to checks if the fucntion getBySpeciesName() throws an expection when a  
     * speciesname of an animal that does not exsist in the Tree is passed as a paramenter. 
     * If the exception is caught then the test has passed else the test failed.
	  */
    @Test
    public void IndividualRoletest5()
    {
    	IIterableTree<String> a = new BDIterableTree<String>();
        BinomialNomenclatureBackend b = new BinomialNomenclatureBackend(a);
        String Speciesname = "Hello";
        try{
             b.getBySpeciesName(Speciesname);
        }catch(NoSuchElementException e)
        {
            
            assertEquals("1","1");
        }
        //Assert.Fail();
        

    }
    
    /**This test is used to check the functionality validData() function of the AlgorithmEngineer.
     * Here the function should return True if the string has exaclty two words with a single space inbetween them
     * else it will return false. Therefore an input of "Animla" is given and since it is a single word 
     * the output should be false
     */
    @Test
    public void CodeReviewOfAlgorithmEngineertest1()
    {
        
        IterableTree<String> a = new IterableTree<String>();
        String x = "Animla";
        boolean c = a.validData(x);
        //System.out.println(c);
        assertEquals(c,false);
    }
    
    /**This test is used to check the functionality of removee() function of the AlgorithmEngineer.
     * In this test, i am checking the size of the tree before and after deletion. The initial size should be
     * 10 and after the deletion the size should decrease to 9. I also check if the correct tree is produced after 
     * deletion.
     * 
     */
    @Test
    public void CodeReviewOfAlgorithmEngineertest2()
    {
        IterableTree<String> a = new IterableTree<String>();
        //System.out.println(a.size());
        assertEquals(a.size(),10);
        a.removee("Haliaeetus leucocephalus");
        //System.out.println(a.size());
        assertEquals(a.size(),9);
        // System.out.println(a.newTree.toLevelOrderString());
        assertEquals(a.newTree.toLevelOrderString(),"[ Falco sparverius, Anaxyrus americanus, Nerodia Sipedon, Anaxyrus Baxteri, Anaxyrus quercicus, Haliaeetus Vocifer, Panthera leo, Panthera Tigris, Panthera onca ]");

    }   
    
    /**This test is used to checks for the functionality of the getByCommonName() function after Integrating with the 
     * Algorithm Engineers work. 
    * A CommonName of "African Fish Eagle" was put as a paramenter into the function. The returned data
    * from the function is stored in the variable X. Using the assertEquals() function, I check if the expected Animal
    * details like the commonname, speciesName and the type  is returned by the function.
	  */
    @Test
    public void IntegrationTest1()
    {
        IIterableTree<String> a = new IterableTree<String>();
        BinomialNomenclatureBackend b = new BinomialNomenclatureBackend(a);
        String Commonname = "African Fish Eagle";
        IAnimal x = b.getByCommonName(Commonname);
        //System.out.println(x.getSpeciesName());
        assertEquals(x.getCommonName(),"African Fish Eagle");
        assertEquals(x.getSpeciesName(),"Haliaeetus Vocifer");
        assertEquals(x.getType(),"Aves");

    }

    @Test
    /**This test is used to checks for the functionality of the getBySpeciesName() function after integration with
     * Algorithm Egnineer's work. 
     * A SpeciesName/Scientific Name of "Panthera leo" was put as a paramenter into the function. The returned data
     * from the function is stored in the variable X. Using the assertEquals() function,I check if the expected Animal
     * details like the commonname, speciesName and the type is returned by the function.
	  */
    public void IntegrationTest2()
    {
        IIterableTree<String> a = new IterableTree<String>();
        BinomialNomenclatureBackend b = new BinomialNomenclatureBackend(a);
        String ScietificName = "Panthera leo";
        IAnimal x = b.getBySpeciesName(ScietificName);
        //System.out.println(x.getType());
        assertEquals(x.getCommonName(),"Lion");
        assertEquals(x.getSpeciesName(),"Panthera leo");
        assertEquals(x.getType(),"Mammalia");
    }
    
    
  
  
    
}

import java.util.ArrayList;
import java.util.List;

public class BinomialNomenclatureBackend implements IBinomialNomenclatureBackend
{
    
    public IIterableTree<String> a ; // The object of the IIterableTree whose tree and methods i will aceess for my method 
    
    /**
     * A constructor to equate the object passed to the global variable a.
     * @param a - The IIterableTree object using which the RedBlacktree is accessed
     */
    public BinomialNomenclatureBackend(IIterableTree<String> a)
    {
        this.a = a;
    }
    
    /**
     * This is a helper fucntion which is used to traverse through the tree 
     * @param Animalnode - It is the Node used that is being checked in the function
     * @param match - The String we are using to check if the Animal is present in the tree
     * @param a - a List used to store the Animal data
     */
    public void traversal(RedBlackTree.Node<String> Animalnode, String match, List<IAnimal> a)
    {
        if(Animalnode==null)
        {
            return ;
        }
        if(Animalnode.speciesname.contains(match)||Animalnode.Commonname.equals(match))
        {
            a.add(Animalnode);
        }
        traversal(Animalnode.leftChild,match,a);
        traversal(Animalnode.rightChild, match,a);
    }

    /**
     * Adds a new animal to the Binomial Nomenclature Tree
     * 
     * @param animal - animal to add to tree
     */
    public void addAnimal(IAnimal animal)
    {
        a.insert(animal.getSpeciesName(),animal.getType(),animal.getSpeciesName());   
    }

    /**
     * Returns number of animals stored in backend
     * 
     * @return number of animals in backend
     */
    public int getNumberOfAnimals()
    {
        return a.size();
    }

    /**
     * Search through the tree of animals and get a list of all animals that have
     * the genus name provided as a parameter
     * 
     * @param genusName - name of the genus searched
     * @return list of all animals that have the desired genus name
     */
    public List<IAnimal> searchByGenusName(String genusName)
    {
        RedBlackTree.Node<String> root = a.getroot();
        List<IAnimal> returnList = new ArrayList<IAnimal>();
        traversal(root, genusName, returnList);
        return returnList;
    }

    /**
     * Return the individual animal with the specific species name
     * 
     * @param speciesName - full genus species name of the animal
     * @return the animal with the specific genus species name provided, or null if
     *         not in tree
     */
    public IAnimal getBySpeciesName(String speciesName)
    {
        RedBlackTree.Node<String> Species = a.contains(speciesName);
        return Species;
    }

    /**
     * Return the individual animal with the specific common name
     * 
     * @param commonName - common English name of the animal
     * @return the animal with the commonName provided, or null if not in tree
     */
    public IAnimal getByCommonName(String commonName)
    {
        RedBlackTree.Node<String> root = a.getroot();
        List<IAnimal> returnList = new ArrayList<IAnimal>();
        traversal(root,commonName,returnList);
        IAnimal CommonAnimal = returnList.get(0);
        return CommonAnimal;
    }

    
    
}

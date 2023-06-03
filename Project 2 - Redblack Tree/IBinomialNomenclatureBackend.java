
//Name: Kruthiventi Shyama Subrahmanya Nikhil
//CSL Username: shyama
//Email: skruthiventi@wisc.edu
//Lecture #: < 003 @2:25pm>
//Notes to Grader: Made this Interface along with Ruilin Yan
//import java.util.LinkedList;
//import java.util.List;
//import java.util.NoSuchElementException;

public interface IBinomialNomenclatureBackend {

 /**
  * Adds a new animal to the Binomial Nomenclature Tree
  * 
  * @param animal - animal to add to tree
  */
 public void addAnimal(IAnimal animal);

 /**
  * Returns number of animals stored in backend
  * 
  * @return number of animals in backend
  */
 public int getNumberOfAnimals();

 /**
  * Search through the tree of animals and get a list of all animals that have
  * the genus name provided as a parameter
  * 
  * @param genusName - name of the genus searched
  * @return list of all animals that have the desired genus name
  */
 //public List<IAnimal> searchByGenusName(String genusName);

 /**
  * Return the individual animal with the specific species name
  * 
  * @param speciesName - full genus species name of the animal
  * @return the animal with the specific genus species name provided, or null if
  *         not in tree
  */
 public IAnimal getBySpeciesName(String speciesName);

 /**
  * Return the individual animal with the specific common name
  * 
  * @param commonName - common English name of the animal
  * @return the animal with the commonName provided, or null if not in tree
  */
 //public IAnimal getByCommonName(String commonName);
}

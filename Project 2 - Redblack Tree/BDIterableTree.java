
//--== CS400 Fall 2022 File Header Information ==--
//--== CS400 Fall 2022 File Header Information ==--
//Name: Kruthiventi Shyama Subrahmanya Nikhil
//Email: skruthiventi@wisc.edu
//Team: DO
//TA: Rahul 
//Lecturer: Florian Heimerl
//Notes to Grader: This is a place holder class to make the integration better 
public class BDIterableTree<T> extends RedBlackTree<String> implements IIterableTree<T>  
{
  

  RedBlackTree<String> newTree; 
 
 
  public BDIterableTree()
  {
      this.newTree = new RedBlackTree<>();  
      IAnimalLoader k = new BDAnimalLoader();
      k.LoadAnimals(newTree);
      
  
  }
  /**Since it is a placeholder class, the Algorithm engineer has to implement this function
   * @param data - The data to be validated
   */
  @Override
  public boolean validData(T data) {
      // TODO Auto-generated method stub
      return true;
  }
  /**Since it is a placeholder class, the Algorithm engineer has to implement this function
   * @param data - The species to be deleted from the tree
   */
  @Override
  public boolean removee(T data) {
      // TODO Auto-generated method stub
      return true;
  }

  /**This function inserts the data into the tree by calling the insert() function
   * of the RedBlackTree Class 
   * @param data - The Speciesname
   * @param a - Type
   * @param b - Commonname
   * 
   */
  @Override
  public boolean insert(String data,String a ,String b) throws NullPointerException, IllegalArgumentException 
  {
      newTree.insert(data, a, b);
      return true;
  }
  
  /**This function is used to check if a particular data is in the Tree by
   * calling the contains function of the RedBlackTree class.
   * @param data - the data whose we have to check
   * @return the node containing the data
   */
  @Override
  public RedBlackTree.Node<String> contains(T data) 
  {
      return newTree.contains((String)data);
  }

  /**This is a helper function which returns the root of the Tree
   *
   */
  public RedBlackTree.Node<String> getroot()
  {
      return newTree.root;
  }

  /**This method checks if the RedBlack Tree is empty by calling
   * the isEmpty() function of the class RedBlackTree
   * 
   */
  @Override
  public boolean isEmpty(){
      return newTree.isEmpty(); 
  }

  /**This method returns the size of the tree by using the size() method of the class
   * RedBlackTree
   */

  @Override
  public int size(){
      return newTree.size(); 
  }
  
}



//--== CS400 Fall 2022 File Header Information ==--
//--== CS400 Fall 2022 File Header Information ==--
//Name: Kruthiventi Shyama Subrahmanya Nikhil
//Email: skruthiventi@wisc.edu
//Team: DO
//TA: Rahul 
//Lecturer: Florian Heimerl
//Notes to Grader: <optional extra notes>
import java.util.LinkedList;
import java.util.NoSuchElementException;


/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. 
 */
public class RedBlackTree<T extends Comparable<T>> {

    /**
     * This class represents a node holding a single value within a binary tree
     * the parent, left, and right child references are always maintained.
     */
    public static class Node<T> implements IAnimal {
        public T speciesname;
        public String Type;
        public String Commonname;
        public Node<T> parent; // null for root node
        public Node<T> leftChild;
        public Node<T> rightChild;
        public int blackHeight; // 0 = red , 1 = black and 2 = double black
       

        public Node(T c,String a,String b) {
            this.speciesname = c;
            this.Type=a;
            this.Commonname=b;
            
        }

        public String getSpeciesName() 
        {
            return (String)speciesname;
        }

        /**
         * get the common name of the animal
         * 
         * @return common name of animal
         */
        public String getCommonName() 
        {
            return Commonname;
        }

        /**
         * get the type of animal
         * 
         * @return type of animal
         */
        public String getType() {
            return Type;
        }

        /**
         * @return true when this node has a parent and is the left child of
         *         that parent, otherwise return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }

    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree

    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * speciesname value to a new node in a leaf position within the tree. After
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate speciesname values.
     * 
     * @param speciesname to be added into this binary search tree
     * @param Type the type of the species
     * @param common the common name of the species
     * @return true if the value was inserted, false if not
     * @throws NullPointerException     when the provided speciesname argument is null
     * @throws IllegalArgumentException when the newNode and subtree contain
     *                                  equal speciesname references
     */
    public boolean insert(T species, String Type, String common ) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if (species == null)
            throw new NullPointerException(
                    "This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(species,Type,common);
        if (root == null) {
            root = newNode;
            size++;
            root.blackHeight = 1;
            return true;
        } // add first node to an empty tree
        else {
            boolean returnValue = insertHelper(newNode, root); // recursively insert into subtree
            if (returnValue)
                size++;
            else
                throw new IllegalArgumentException(
                        "This RedBlackTree already contains that value.");
            root.blackHeight = 1;
            return returnValue;
        }
    }

    /**
     * Recursive helper method to find the subtree with a null reference in the
     * position that the newNode should be inserted, and then extend this tree
     * by the newNode in that position.
     * 
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the
     *                newNode should be inserted as a descenedent beneath
     * @return true is the value was inserted in subtree, false if not
     */
    private boolean insertHelper(Node<T> newNode, Node<T> subtree) {
        int compare = newNode.speciesname.compareTo(subtree.speciesname);
        // do not allow duplicate values to be stored within this tree
        if (compare == 0)
            return false;

        // store newNode within left subtree of subtree
        else if (compare < 0) {
            if (subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // otherwise continue recursive search for location to insert
            } else
                return insertHelper(newNode, subtree.leftChild);
        }

        // store newNode within the right subtree of subtree
        else {
            if (subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // otherwise continue recursive search for location to insert
            } else
                return insertHelper(newNode, subtree.rightChild);
        }
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * rightChild of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * 
     * @param child  is the node being rotated from child to parent position
     *               (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *               (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *                                  node references are not initially
     *                                  (pre-rotation) related that way
     */

    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
		if(parent.rightChild != null && parent.rightChild.speciesname.equals(child.speciesname)) {
			rotateleft(parent);
		}else if(parent.leftChild != null && parent.leftChild.speciesname.equals(child.speciesname)) {
			roateRight(parent);
		}
	}
	/**
	 * helper method for rotating left
	 * @param node - parent that is being entered in
	 */
	private void rotateleft(Node<T> node) {
		Node<T> parent = node.parent;
		Node<T> child  = node.rightChild;
		node.rightChild = child.leftChild;
		if(child.leftChild != null) {
			child.leftChild.parent = node;

		}
		child.leftChild = node;

		node.parent = child;
		setParent(parent, node, child);

	}
	/**
	 * helper method for rotating right
	 * @param node - parent that is being entered in
	 */
	private void roateRight(Node<T> node) {
		Node<T> parent = node.parent;
		Node<T> child  = node.leftChild;
		node.leftChild = child.rightChild;
		if(child.rightChild != null) {
			child.rightChild.parent = node;

		}
		child.rightChild = node;

		node.parent = child;
		setParent(parent, node, child);
	}
	private void setParent(Node<T> parent, Node<T> oldChild, Node<T> newChild) {
		if(parent == null) {
			root = newChild;
		}else if(parent.leftChild == oldChild) {
			parent.leftChild = newChild;
		}else if(parent.rightChild == oldChild) {
			parent.rightChild = newChild;
		}else {
			throw new IllegalArgumentException("Error");
		}
		if(newChild != null) {
			newChild.parent = parent;
		}
	}

    protected void enforceRBTreePropertiesAfterInsert(Node<T> newNode) {
        Node<T> parent = newNode.parent;
        if (newNode == root || newNode.parent.blackHeight == 1) {
            return;
        }

        Node<T> grandparent = parent.parent;
        if (grandparent == null) {
            parent.blackHeight = 1;
            return;
        }
        Node<T> parentsib = null;

        if (grandparent.rightChild == parent) {
            parentsib = grandparent.leftChild;
        } else if (grandparent.leftChild == parent) {
            parentsib = grandparent.rightChild;
        }

        if ((parentsib == null) || (parentsib.blackHeight == 1)) {
            if (parent == grandparent.leftChild) {
                if (newNode == parent.rightChild) {
                    rotate(newNode, parent);
                    parent = newNode;
                }

                rotate(parent, grandparent);
                parent.blackHeight = 1;
                grandparent.blackHeight = 0;
            } else {
                if (newNode == parent.leftChild) {
                    rotate(newNode, parent);
                    parent = newNode;
                }

                rotate(parent, grandparent);
                parent.blackHeight = 1;
                grandparent.blackHeight = 0;
            }
        } else if (parentsib.blackHeight != 1) {
            parent.blackHeight = 1;
            grandparent.blackHeight = 0;
            parentsib.blackHeight = 1;
            enforceRBTreePropertiesAfterInsert(grandparent);
        }

    }

    /**
     * Get the size of the tree (its number of nodes).
     * 
     * @return the number of nodes in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * 
     * @return true of this.size() return 0, false if this.size() > 0
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the tree contains the value *speciesname*.
     * 
     * @param speciesname the speciesname value to test for
     * @return the node containing the species name
     */
    public Node<T> contains(T speciesname) {
        // null references will not be stored within this tree
        if (speciesname == null)
            throw new NullPointerException(
                    "This RedBlackTree cannot store null references.");
        return this.containsHelper(speciesname, root);
    }

    /**
     * Recursive helper method that recurses through the tree and looks
     * for the value *speciesname*.
     * 
     * @param speciesname    the speciesname value to look for
     * @param subtree the subtree to search through
     * @return subtree 
     */
    private Node<T> containsHelper(T speciesname, Node<T> subtree) {
        if (subtree == null) {
            // we are at a null child, value is not in tree
            throw new NoSuchElementException("No element present");
        } else {
            int compare = speciesname.compareTo(subtree.speciesname);
            if (compare < 0) {
                // go left in the tree
                return containsHelper(speciesname, subtree.leftChild);
            } else if (compare > 0) {
                // go right in the tree
                return containsHelper(speciesname, subtree.rightChild);
            } else {
                // we found it :)
                return subtree;
            }
        }
    }

    /**
     * This method performs an inorder traversal of the tree. The string
     * representations of each speciesname value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * Note that this RedBlackTree class implementation of toString generates an
     * inorder traversal. The toString of the Node class class above
     * produces a level order traversal of the nodes / values of the tree.
     * 
     * @return string containing the ordered values of this tree (in-order
     *         traversal)
     */
    public String toInOrderString() {
        // generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        sb.append(toInOrderStringHelper("", this.root));
        if (this.root != null) {
            sb.setLength(sb.length() - 2);
        }
        sb.append(" ]");
        return sb.toString();
    }

    private String toInOrderStringHelper(String str, Node<T> node) {
        if (node == null) {
            return str;
        }
        str = toInOrderStringHelper(str, node.leftChild);
        str += (node.speciesname.toString() + ", ");
        str = toInOrderStringHelper(str, node.rightChild);
        return str;
    }

    /**
     * This method performs a level order traversal of the tree rooted
     * at the current node. The string representations of each speciesname value
     * within this tree are assembled into a comma separated string within
     * brackets (similar to many implementations of java.util.Collection).
     * Note that the Node's implementation of toString generates a level
     * order traversal. The toString of the RedBlackTree class below
     * produces an inorder traversal of the nodes / values of the tree.
     * This method will be helpful as a helper for the debugging and testing
     * of your rotation implementation.
     * 
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString() {
        String output = "[ ";
        if (this.root != null) {
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this.root);
            while (!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if (next.leftChild != null)
                    q.add(next.leftChild);
                if (next.rightChild != null)
                    q.add(next.rightChild);
                output += next.speciesname.toString();
                if (!q.isEmpty())
                    output += ", ";
            }
        }
        return output + " ]";
    }

    public String toString() {
        return "level order: " + this.toLevelOrderString() +
                "\nin order: " + this.toInOrderString();
    }
    public Node<T> minimum(Node<T> node) 
    {
        while (node.leftChild != null) {
          node = node.leftChild;
        }
        return node;
    }
    private void rbTransplant(Node<T> u, Node<T> v) {
        if (u.parent == null) {
          root = v;
        } else if (u == u.parent.leftChild) {
          u.parent.leftChild = v;
        } else {
          u.parent.rightChild = v;
        }
        if(u.parent != null && v != null)
          v.parent = u.parent;
    }
    private boolean deleteNodeHelper(Node<T> node, String key) {
        Node<T> z = null;
        Node<T> x, y;
        while (node != null) {
          if (((String)node.speciesname).equals(key)) {
            z = node;
          }
    
          if (((String)node.speciesname).compareTo(key) < 0) {
            node = node.rightChild;
          } else {
            node = node.leftChild;
          }
        }
    
        if (z == null) {
          System.out.println("Couldn't find key in the tree");
          return false;
        }
    
        y = z;
        int yOriginalColor = y.blackHeight;
        if (z.leftChild == null) {
          x = z.rightChild;
          rbTransplant(z, z.rightChild);
        } else if (z.rightChild == null) {
          x = z.leftChild;
          rbTransplant(z, z.leftChild);
        } else {
          y = minimum(z.rightChild);
          yOriginalColor = y.blackHeight;
          x = y.rightChild;
          if (y.parent == z) {
            x.parent = y;
          } else {
            rbTransplant(y, y.rightChild);
            y.rightChild = z.rightChild;
            y.rightChild.parent = y;
          }
    
          rbTransplant(z, y);
          y.leftChild = z.leftChild;
          y.leftChild.parent = y;
          y.blackHeight = z.blackHeight;
        }
        if (yOriginalColor == 0) {
          fixDelete(x);
        }
        return true;
    }

      
    private void fixDelete(Node<T> x) {
        Node<T> s;
        if(x == null){
            return; 
        }
        while (x != root && x.blackHeight == 0) {
          if (x == x.parent.leftChild) {
            s = x.parent.rightChild;
            if (s.blackHeight == 1) {
              s.blackHeight = 0;
              x.parent.blackHeight = 1;
              rotate(x, x.parent);
              s = x.parent.rightChild;
            }
    
            if (s.leftChild.blackHeight == 0 && s.rightChild.blackHeight == 0) {
              s.blackHeight = 1;
              x = x.parent;
            } else {
              if (s.rightChild.blackHeight == 0) {
                s.leftChild.blackHeight = 0;
                s.blackHeight = 1;
                rotate(s.rightChild, s);
                s = x.parent.rightChild;
              }
    
              s.blackHeight = x.parent.blackHeight;
              x.parent.blackHeight = 0;
              s.rightChild.blackHeight = 0;
              rotate(x,x.parent);
              x = root;
            }
          } else {
            s = x.parent.leftChild;
            if (s.blackHeight == 1) {
              s.blackHeight = 0;
              x.parent.blackHeight = 1;
              rotate(x,x.parent);
              s = x.parent.leftChild;
            }
    
            if (s.rightChild.blackHeight == 0 && s.rightChild.blackHeight == 0) {
              s.blackHeight = 1;
              x = x.parent;
            } else {
              if (s.leftChild.blackHeight == 0) {
                s.rightChild.blackHeight = 0;
                s.blackHeight = 1;
                rotate(s.leftChild, s);
                s = x.parent.leftChild;
              }
    
              s.blackHeight = x.parent.blackHeight;
              x.parent.blackHeight = 0;
              s.leftChild.blackHeight = 0;
              rotate(x, x.parent);
              x = root;
            }
          }
        }
        x.blackHeight = 0;
    }

    public boolean remove(String data) {
        if(deleteNodeHelper(this.root, data)){
          size--; 
          return true;
        }
         return false; 
        
    }
    
}

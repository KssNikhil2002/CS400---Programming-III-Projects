// --== CS400 Fall 2022 File Header Information ==--
// Name: Kruthiventi Shyama Subrahmanya Nikhil
// Email: skruthiventi@wisc.edu
// Team: DO
// TA: Rahul 
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. 
 */
public class RedBlackTree<T extends Comparable<T>> {

    /**
     * This class represents a node holding a single value within a binary tree
     * the parent, left, and right child references are always maintained.
     */
    protected static class Node<T> {
        public T data;
        public Node<T> parent; // null for root node
        public Node<T> leftChild;
        public Node<T> rightChild;
        public int blackHeight; // 0 = red , 1 = black and 2 = double black

        public Node(T data) {
            this.data = data;
            this.blackHeight = 0;
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
     * data value to a new node in a leaf position within the tree. After
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     * 
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException     when the provided data argument is null
     * @throws IllegalArgumentException when the newNode and subtree contain
     *                                  equal data references
     */
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if (data == null)
            throw new NullPointerException(
                    "This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
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
        int compare = newNode.data.compareTo(subtree.data);
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
        if (parent.leftChild == child) {
            rightrotate(child, parent);

        } else if (parent.rightChild == child) {
            leftrotate(child, parent);
        } else {
            throw new IllegalArgumentException("No relation exits");
        }
        // TODO: Implement this method.
    }

    public void rightrotate(Node<T> child, Node<T> parent) {

        Node<T> t1 = child.rightChild;
        if (parent.parent == null) {
            child.rightChild = parent;
            parent.leftChild = t1;
            root = child;
        } else {
            // Node<T> t2 = parent.parent;
            child.rightChild = parent;
            parent.leftChild = t1;
            if (parent.parent.leftChild == parent) {
                parent.parent.leftChild = child;
            } else {
                parent.parent.rightChild = child;
            }

        }

    }

    public void leftrotate(Node<T> child, Node<T> parent) {

        Node<T> t1 = child.leftChild;
        if (parent.parent == null) {
            child.leftChild = parent;
            parent.rightChild = t1;
            root = child;
        } else {
            child.leftChild = parent;
            parent.rightChild = t1;
            if (parent.parent.leftChild == parent) {
                parent.parent.leftChild = child;
            } else {
                parent.parent.rightChild = child;
            }
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
     * Checks whether the tree contains the value *data*.
     * 
     * @param data the data value to test for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    public boolean contains(T data) {
        // null references will not be stored within this tree
        if (data == null)
            throw new NullPointerException(
                    "This RedBlackTree cannot store null references.");
        return this.containsHelper(data, root);
    }

    /**
     * Recursive helper method that recurses through the tree and looks
     * for the value *data*.
     * 
     * @param data    the data value to look for
     * @param subtree the subtree to search through
     * @return true of the value is in the subtree, false if not
     */
    private boolean containsHelper(T data, Node<T> subtree) {
        if (subtree == null) {
            // we are at a null child, value is not in tree
            return false;
        } else {
            int compare = data.compareTo(subtree.data);
            if (compare < 0) {
                // go left in the tree
                return containsHelper(data, subtree.leftChild);
            } else if (compare > 0) {
                // go right in the tree
                return containsHelper(data, subtree.rightChild);
            } else {
                // we found it :)
                return true;
            }
        }
    }

    /**
     * This method performs an inorder traversal of the tree. The string
     * representations of each data value within this tree are assembled into a
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
        str += (node.data.toString() + ", ");
        str = toInOrderStringHelper(str, node.rightChild);
        return str;
    }

    /**
     * This method performs a level order traversal of the tree rooted
     * at the current node. The string representations of each data value
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
                output += next.data.toString();
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

    //The following are the 3 test methods implemented using JUnit.

    @Test
    /**
     * This test checks for the case when initially 7,14 and 23 is inserted, if the
     * correct rotation
     * the recoloring of the nodes takes place. Then when 21 is inserted it checks
     * if it is inserted correctly and
     * also checks for the situation when a node is inserted and the uncle is red.
     * In this situation only color-swap
     * of the nodes should take place. So it checks if the proper color-swap takes
     * places after 21 is inserted.
     */
    public void test1() {
        RedBlackTree<Integer> a = new RedBlackTree<Integer>();
        a.insert(7);
        a.insert(14);
        a.insert(18);
        a.insert(23);
        assertEquals(a.root.blackHeight, 1);
        assertEquals(a.root.leftChild.blackHeight, 1);
        assertEquals(a.root.rightChild.blackHeight, 1);
        assertEquals(a.root.rightChild.rightChild.blackHeight, 0);

    }

    @Test
    /**
     * This test checks for the case when the both rotation and color-swap is
     * required since the uncle is null when
     * 25 is inserted.In the below case, only a single rotation is required.
     * Therefore, this test checks if the left rotation
     * is executed correctly when 25 is inserted and the appropriate color Swap is
     * performed.
     */
    public void test2() {
        RedBlackTree<Integer> a = new RedBlackTree<Integer>();
        a.insert(7);
        a.insert(14);
        a.insert(18);
        a.insert(23);
        a.insert(1);
        a.insert(11);
        a.insert(25);
        assertEquals(a.toString(),
                "level order: [ 14, 7, 23, 1, 11, 18, 25 ]" + "\nin order: [ 1, 7, 11, 14, 18, 23, 25 ]");
        assertEquals(a.root.blackHeight, 1);
        assertEquals(a.root.leftChild.blackHeight, 1);
        assertEquals(a.root.rightChild.blackHeight, 1);
        assertEquals(a.root.rightChild.rightChild.blackHeight, 0);
        assertEquals(a.root.rightChild.leftChild.blackHeight, 0);
        assertEquals(a.root.leftChild.rightChild.blackHeight, 0);
        assertEquals(a.root.leftChild.leftChild.blackHeight, 0);

    }

    @Test
    /**
     * This test checks for the case when the both rotation and color-swap is
     * required since the uncle is null when
     * 18 is inserted.In the below case, double rotation(right and left) is
     * required. Therefore, this test checks if
     * code is able to decide which rotation to perform and executes the right and
     * left rotation correctly when 18 is
     * inserted and the appropriate color Swap is performed.
     */
    public void test3() {
        RedBlackTree<Integer> a = new RedBlackTree<Integer>();
        a.insert(6);
        a.insert(13);
        a.insert(16);
        a.insert(22);
        a.insert(1);
        a.insert(10);
        a.insert(18);
        assertEquals(a.toString(),
                "level order: [ 13, 6, 18, 1, 10, 16, 22 ]" + "\nin order: [ 1, 6, 10, 13, 16, 18, 22 ]");
        assertEquals(a.root.blackHeight, 1);
        assertEquals(a.root.leftChild.blackHeight, 1);
        assertEquals(a.root.rightChild.blackHeight, 1);
        assertEquals(a.root.rightChild.rightChild.blackHeight, 0);
        assertEquals(a.root.rightChild.leftChild.blackHeight, 0);
        assertEquals(a.root.leftChild.rightChild.blackHeight, 0);
        assertEquals(a.root.leftChild.leftChild.blackHeight, 0);
    }

}
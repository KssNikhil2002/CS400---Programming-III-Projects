
// --== CS400 Project One File Header ==--
// Name: Kruthiventi Shyama Subrahmanya Nikhil
// CSL Username: shyama
// Email: skruthiventi@wisc.edu
// Lecture #: <003 @2:25pm>
// Notes to Grader: <any optional extra notes to your grader>
import java.util.NoSuchElementException;
import java.util.LinkedList;

public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

    protected LinkedList<HashNode<KeyType, ValueType>>[] list;
    public int elements = 0;
    public int capacity;
    public int defaultcapacity = 15;
    public float lf = 0.7f;

     /**
      This is a parameterized constructor which takes in the value of capacity 
     */

    @SuppressWarnings("unchecked")
    public HashtableMap(int capacity) {
        this.capacity = capacity;
        list = new LinkedList[capacity];
    }

    /**
      This is a default constructor which sets value of the capacity to 15
     */
    @SuppressWarnings("unchecked")
    public HashtableMap() {
        capacity = defaultcapacity;
        list = new LinkedList[capacity];
    }

    public int hashfunction(KeyType key) 
    {
        int a;
        if (key instanceof String) 
        {
            a = (Math.abs((((String) key)).charAt(0)) % capacity);
        }
        else
        {
            a = (Math.abs(key.hashCode()) % capacity);
        }
        
        return a;

    }

    /**
     * Returns the value mapped to a key if the map contains such a mapping.
     * 
     * @param key the key for which to look up the value
     * @return the value mapped to the key
     * @throws NoSuchElementException if the map does not contain a mapping
     *                                for the key
     */

    public ValueType get(KeyType key) throws NoSuchElementException {
        int a = hashfunction(key);
        if (list[a] != null) {
            for (int i = 0; i < list[a].size(); i++) {
                if (list[a].get(i).getkey().equals(key)) {
                    return list[a].get(i).getvalue();
                }
            }
        }
        throw new NoSuchElementException();

    }

    /**
     * Inserts a new (key, value) pair into the map if the map does not
     * contain a value mapped to key yet.
     * 
     * @param key   the key of the (key, value) pair to store
     * @param value the value that the key will map to
     * @return true if the (key, value) pair was inserted into the map,
     *         false if a mapping for key already exists and the
     *         new (key, value) pair could not be inserted
     */

    public boolean put(KeyType key, ValueType value) {
        if (key == null) {
            return false;
        } else {
            HashNode<KeyType, ValueType> ele = new HashNode<KeyType, ValueType>(key, value);
            int a = hashfunction(key);
            if (list[a] == null) {
                list[a] = new LinkedList<HashNode<KeyType, ValueType>>();
                list[a].push(ele);
                elements++;
                double m = (1.0 * elements) / capacity;
                if (m >= lf) {
                    rehash();
                }

            } else {
                for (int i = 0; i < list[a].size(); i++) {
                    if (list[a].get(i).getkey().equals(key)) {
                        return false;
                    }
                }

                list[a].push(ele);
                elements++;
                double m = (1.0 * elements) / capacity;
                if (m >= lf) {
                    rehash();
                }

            }
            return true;
        }
    }

    /**
     * Removes a key and its value from the map.
     * 
     * @param key the key for the (key, value) pair to remove
     * @return the value for the (key, value) pair that was removed,
     *         or null if the map did not contain a mapping for key
     */

    public ValueType remove(KeyType key) {
        int a = hashfunction(key);
        if (list[a] != null) {
            for (int i = 0; i < list[a].size(); i++) {
                if (list[a].get(i).getkey().equals(key)) {
                    ValueType val = list[a].get(i).getvalue();
                    list[a].remove(i);
                    elements--;
                    return val;
                }
            }
        }
        return null;

    }

    /**
     * Checks if a key is stored in the map.
     * 
     * @param key the key to check for
     * @return true if the key is stored (mapped to a value) by the map
     *         and false otherwise
     */

    public boolean containsKey(KeyType key) {
        int a = hashfunction(key);
        for (int i = 0; i < list[a].size(); i++) {
            if (list[a].get(i).getkey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of (key, value) pairs stored in the map.
     * 
     * @return the number of (key, value) pairs stored in the map
     */
    public int size() {
        return elements;
    }

    /**
     * Removes all (key, value) pairs from the map.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        elements = 0;
        list = new LinkedList[capacity];

    }
    
    /**
      The rehash function is used to resize the Hashmap.
      A duplicate array of the original Hashmap is created. Then the capacity is doubled and the elements are inserted
      again back into the original array
     */


    @SuppressWarnings("unchecked")
    private void rehash() {
        LinkedList<HashNode<KeyType, ValueType>>[] list2 = list;
        capacity = capacity * 2;
        list = new LinkedList[capacity];
        HashNode<KeyType, ValueType> temp1;

        for (int i = 0; i < list2.length; i++) {
            if (list2[i] != null) {

                for (int j = 0; j < list2[i].size(); j++) {

                    int a = hashfunction(list2[i].get(j).getkey());
                    temp1 = new HashNode<KeyType, ValueType>(list2[i].get(j).getkey(), list2[i].get(j).getvalue());
                    if (list[a] == null) {
                        list[a] = new LinkedList<HashNode<KeyType, ValueType>>();
                        list[a].push(temp1);

                    } else {
                        list[a].push(temp1);

                    }

                }
            }
        }

    }
}
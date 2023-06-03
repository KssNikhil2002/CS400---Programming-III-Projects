package test;

public interface IIterableTree<T> {

    public boolean removee(T data);

    public boolean validData(T data);

    public boolean insert(String data, String a, String b) throws NullPointerException, IllegalArgumentException;

    public RedBlackTree.Node<String> contains(T data);

    public int size();

    public boolean isEmpty();

    public RedBlackTree.Node<String> getroot();

}

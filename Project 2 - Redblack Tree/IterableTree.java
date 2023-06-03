package test;

public class IterableTree<T> extends RedBlackTree<String> implements IIterableTree<T>
{
    RedBlackTree<String> newTree; 
    public IterableTree(){
        this.newTree = new RedBlackTree<>();  
        IAnimalLoader k = new BDAnimalLoader();
        k.LoadAnimals(newTree);
        
    
    }
    
    @Override
    public RedBlackTree.Node<String> contains(T data) 
    {
        return newTree.contains((String)data);
    }

    @Override
    public boolean removee(T data) 
    {
        newTree.remove((String)data);
        return true;
        
    }

    @Override
    public int size(){
        return newTree.size(); 
    }

    @Override
    public boolean validData(T data) {
        int i = 0; 
        boolean found = false; 
        String a[] = ((String) data).split(" ");
        if(a.length>2)
        {
            return found;
        }
        else
        {
            while(!(((String)data).trim().substring(i,i+1).equals(" ")) && i < ((String)data).length()-1)
            {
                i++;
                if(((String)data).trim().substring(i,i+1).equals(" "))
                {
                    found = true; 
                }
            }
            return found;

        }
    }

    @Override
    public boolean isEmpty(){
        return newTree.isEmpty(); 
    }

    @Override
    public boolean insert(String data,String a ,String b) throws NullPointerException, IllegalArgumentException 
    {
        newTree.insert(data, a, b);
        return true;
    }

    public RedBlackTree.Node<String> getroot()
    {
        return newTree.root;
    }

    

    
}

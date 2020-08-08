import java.util.ArrayList;

public interface MapInterface <K, V> {

    public ArrayList<K> keys();
    public ArrayList<V> values();
    
    public V hashGet(K v);
    public V hashPut(K k, V v);
    public V hashRemove (K k);
    public int size();
    public boolean isEmpty();

}		//Map Interface
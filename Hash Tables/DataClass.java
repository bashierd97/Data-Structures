public class DataClass<K, V> {

    K key;
    V value;

   
    public DataClass(K key, V value) {

        this.key = key;
        this.value = value;
    }

   //returns hashCode
    public int hashFunction() {

        return hashCode();
    }

    
    public int hashCode() {

        return (key == null   ? 0 : key.hashCode()) ^
                (value == null ? 0 : value.hashCode());
    }

    //toString method to display my value and keys
    public String toString(){

        return "[" + value.toString() + "," + key.toString() + "]";
    }
}	//DataClass
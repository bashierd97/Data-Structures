import java.nio.CharBuffer;

public class Key {

    String key;

    //constructor for my Key class
    public Key(String key) {

        this.key = key;
    }

    //hashCode function
    @Override
    public int hashCode(){

        int h = 0;
        
        for (int i = 0; i < key.length(); i++){
        	//5-bit cyclic shift of the running sum
            h = (h << 5) | (h >>> 27);
            //and in next character
            h = h + (int) key.charAt(i);
        }
        return h;
    }
    
    public String toString(){

        return key;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null){

            return false;
        }
        else if (obj.getClass() != this.getClass()){

            return false;
        }
        Key other = (Key)obj;

        return other.key.equals(this.key);
    }
}	//class
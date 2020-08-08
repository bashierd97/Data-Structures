import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ChainingHashTable<K,V> implements MapInterface<K,V> {

	//creating a linked list for my chaining table
	LinkedList<DataClass<K, V>> HashChainTable[];

	//creating an empty table of size 50
	@SuppressWarnings("unchecked")
	public ChainingHashTable() {

		HashChainTable = new LinkedList[50];
	}

	@SuppressWarnings("unchecked")
	public ChainingHashTable(int max) {

		HashChainTable = new LinkedList[max];
	}



	//hashfunction which will use the MAD function (Multiple, Add, Divide)
	public int MADhashFunction(int ind){

		int MAD_a;
		int MAD_b;
		int MAD_p;

		java.util.Random randomNum = new java.util.Random();
		MAD_p = 53;	//prime number larger than our table
		MAD_a = randomNum.nextInt(MAD_p-1) + 1;
		MAD_b = randomNum.nextInt(MAD_p);

//		int x = 31 * hash /2;

		return (int) ( ( Math.abs( MAD_a*ind + MAD_b ) % MAD_p ) % HashChainTable.length);
	}
	
//	simple division compresson function
//	maps the integer into an index
	public int hashFunction(int ind){
		return ind % HashChainTable.length;
	}
	
	//	
	// Create get method for Keys
	@Override
	public V hashGet(K k) {


		int i = 0;

		i = hashFunction(k.hashCode());

		if(i < 0) {
			i = i + HashChainTable.length;
		}
		return bucketGet(i, k);
	}


	//hashPut method that inserts values into hashTable
	@Override
	public V hashPut(K key, V value) {

		int index = 0;

		index = hashFunction(key.hashCode());

		if(index < 0){

			index = index + HashChainTable.length;
		}
		return bucketPut(index, key, value);
	}

	//Remove method that removes values from the table
	public V hashRemove(K k){

		int index = 0;

		index = hashFunction(k.hashCode());

		if(index < 0) {

			index = index + HashChainTable.length;
		}
		return bucketRemove(index, k);
	}

	//returns values in the table or can return null
	protected V bucketGet(int h, K k) {

		LinkedList<DataClass<K,V>> bucket = HashChainTable[h];

		if (bucket == null)

			return null;

		for(int i = 0; i < bucket.size(); i++){

			if (bucket.get(i).key.equals(k)){

				return bucket.get(i).value;
			}else {
				return null;
			}
		}
		return null;
	}

	//bucketPut method that inserts value in a bucket (using linked lists)
	protected V bucketPut(int h, K k, V v) {

		LinkedList<DataClass<K,V>> bucket = HashChainTable[h];

		if (bucket == null) {

			HashChainTable[h] = new LinkedList<>();
			HashChainTable[h].add(new DataClass<>(k, v));

			return v;
		}
		for(int i = 0; i < bucket.size(); i++){

			if (bucket.get(i).key.equals(k)){

				V oldValue = bucket.get(i).value;
				bucket.get(i).value = v;

				return oldValue;
			}
		}
		bucket.add(new DataClass<>(k, v));
		return null;
	}

	//removes a value from the bucket
	protected V bucketRemove(int h, K k) {

		LinkedList<DataClass<K, V>> bucket = HashChainTable[h];

		if (bucket == null) {

			return null;
		}
		for(int i = 0; i< bucket.size(); i++) {

			if (bucket.get(i).key.equals(k)) {
				V oldValue = bucket.get(i).value;
				bucket.remove(bucket.get(i));
				return oldValue;
			}
		}
		return null;
	}

	//size method
	@Override
	public int size() {

		int count = 0;
		for(int i = 0; i < HashChainTable.length; i++){

			if (HashChainTable[i] != null) {

				count = count + HashChainTable[i].size();
			}
		}
		return count;
	}

	//method for checking if hash table is empty
	@Override
	public boolean isEmpty() {

		for(int i = 0; i < HashChainTable.length; i++){

			if (HashChainTable[i] !=null) {

				return false;
			}
		}
		return true;
	}

	
    public void displayKeyIndex() {
		for(int index = 0; index < HashChainTable.length; index++){

			if(HashChainTable[index] != null && !HashChainTable[index].isEmpty()){

				System.out.print("[" +index + "]" + "           ");

				for(int j = 0; j < HashChainTable[index].size(); j++){

					System.out.print(HashChainTable[index].get(j).key + " ");
				}
				System.out.println();
				}	
				}
			
			}
				
	    public void displayValIndex() {
			for(int index = 0; index < HashChainTable.length; index++){

				if(HashChainTable[index] != null && !HashChainTable[index].isEmpty()){

					System.out.print("[" +index + "]" + "           ");

					for(int j = 0; j < HashChainTable[index].size(); j++){

						System.out.print(HashChainTable[index].get(j).value + " ");
					}
				System.out.println();
				}
				}		
            
    }
	//display method for table
	public void displayTable(){

		for(int index = 0; index < HashChainTable.length; index++){

			if(HashChainTable[index] != null && !HashChainTable[index].isEmpty()){

				System.out.print("[" +index + "]" + "           ");

				for(int j = 0; j < HashChainTable[index].size(); j++){

					System.out.print(HashChainTable[index].get(j) + " ");
				}
				System.out.println();
			}
			else{

				System.out.println("[" +index + "]" + "           " + "null value ");
			}
		} 
	}

	//Creating an ArrayList for the hash table
	@Override
	public ArrayList<K> keys() {

		ArrayList<K> keys = new ArrayList<>();

		for (int h = 0; h < HashChainTable.length; h++) {

			if (HashChainTable[h] != null) {

				for (DataClass<K, V> entry : HashChainTable[h]) {

					keys.add(entry.key);
				}
			}
		}
		return keys;
	}

	//creating an array list for the values
	@Override
	public ArrayList<V> values() {

		ArrayList<K> Keys = keys();
		ArrayList<V> Values = new ArrayList<>();

		for (int i = 0; i < Keys.size(); i++) {

			Values.add(hashGet(Keys.get(i)));
		}

		return Values;
	}

	public static void main(String[] args) {

		String filePath = "C:\\Users\\bashi\\Desktop\\BashProg3.txt";
		Scanner file = null;

		Scanner sc = new Scanner(System.in);

		System.out.println("========Chaining Hash Table Program========");

		//try to see if file is in system, if not throw exception
		try {

			file = new Scanner(new File(filePath));

		} catch (FileNotFoundException e) {

			System.out.println("No such file exists, or entered incorrectly");


		} 

		//creating a ChainingHashTable object
		ChainingHashTable<Key, String> hashT = new ChainingHashTable();

		//traverse through the file inserting the key into an object and putting them
		//into a hashtable
		int current = 0;
		while (file.hasNext()) {

			String text = file.next();

			// Read the Key Value
			String keyColumn = file.next();

			Key keyObj = new Key(keyColumn);

			hashT.hashPut(keyObj, text);

			current++;
		} 

		//creating arraylists for my keys and values
		ArrayList<Key> keys = hashT.keys();
		ArrayList<String> values = hashT.values();

		int choice;

		String userInput = "";

		//user menu
		do {
			
			System.out.println();
			System.out.println("(Please select from the following options, NUMBERS ONLY)");
			System.out.println();
			System.out.println("1. Display Chaining Hash Table: ");
			System.out.println("2. Search for a Key Value Pair: ");
			System.out.println("3. Delete a value using a Key: ");
			System.out.println("4. Exit Program ");
			
			
			choice = sc.nextInt();
			sc.nextLine();

			//menu for my choices
			switch (choice) {

			case 1:
				//created another menu to prompt the user whether they want to display
				//only the keys, values, or both
				int choiceDisplay;

				System.out.println("Chaining Hash Table display. Please select from the following options (NUMBERS ONLY)");
				System.out.println();
				System.out.println("1. Display KEYS only");
				System.out.println("2. Display VALUES only");
				System.out.println("3. Display entire table (KEYS and VALUES)");
				System.out.println("4. Back to main menu");

				choiceDisplay = sc.nextInt();
				sc.nextLine();


				switch(choiceDisplay) {

				//keys display only
				case 1:

					System.out.println("Listing only keys: ");

//					for (int i = 0; i <hashT.size(); i++) {
//						System.out.println("Key: " + hashT.keys().get(i));
//
//					}
//					System.out.println("AND IN ORDER FROM TOP TO BOTTOM, THEIR CORRESPONDING INDEX");
					 hashT.displayKeyIndex();
					break;

					//value display only
				case 2:

					System.out.println("Listing only values: ");

//					for (int i =0; i<hashT.size(); i++) {
//						
//						System.out.println("Value: " + values.get(i));
//					}
//					System.out.println("AND IN ORDER FROM TOP TO BOTTOM, THEIR CORRESPONDING INDEX");
					 hashT.displayValIndex();

					break;
					//entire table display
				case 3:


					System.out.println();
					System.out.println("Display Chaining Hash Table: ");
					System.out.println();
					System.out.print("Heap Index[] " +  "  Value    Key: ");
					System.out.println();

					hashT.displayTable();
					break;

				case 4:

					break;
				}
				break;
				//searching case
			case 2:

				System.out.println("KEY NEEDED FOR SEARCH: ");

				userInput = sc.nextLine();

				Key search = new Key(userInput);

				String value = hashT.hashGet(search);

				if(value == null){

					System.out.println("Could not find key, please try again");

				} else{

					System.out.println("Value associated with key is " + value);
				}

				break;

				//deletion case
			case 3:

				System.out.println("KEY NEEDED FOR DELETION: ");

				userInput = sc.nextLine();

				Key delete = new Key(userInput);

				hashT.hashRemove(delete);

				System.out.println("Key " + userInput + " removed (if found)");


				break;

				//exiting case
			default:

				System.out.println("Exiting program...");                   
				System.exit(0);
				break;
			}
		}
		while (choice != 4);
		sc.close();
		
	}	//main
}	//class
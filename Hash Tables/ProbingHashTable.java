import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ProbingHashTable<K,V> implements MapInterface<K,V> {

	private DataClass<K,V>[ ] hashTable; 
	private DataClass<K,V> NULL = new DataClass<>(null, null); 

	@SuppressWarnings("unchecked")
	public ProbingHashTable() {

		hashTable = new DataClass[50];
	}

	@SuppressWarnings("unchecked")
	public ProbingHashTable(int max) {

		hashTable = new DataClass[max];
	}


	private boolean isAvailable(int j) {

		return (hashTable[j] == null || hashTable[j] == NULL);
	}

	
	private int findSlot(int h, K k) {

		int available = -1; 
		int j = h; // index while scanning table

		do {
			if (isAvailable(j)) { 

				if (available == -1) {
					available = j; 
				}
				//check if the table is empty
				if (hashTable[j] == null) {
					break; 
				}
				//matched slot
			} else if (hashTable[j].key.equals(k)) {

				return j; 
			}
			j = (j+1) %hashTable.length; 

		}
		while (j != h); 

		return -(available+1); // search has failed
	}
	
	//get method for my buckets
	protected V bucketGet(int x, K k) {

		int j = findSlot(x, k);

		if (j < 0){

			return null; // no match found
		}
		return hashTable[j].value;
	}

	//Removes entries inside my bucket
	protected V bucketRemove(int h, K k) {

		int j = findSlot(h, k);
		if (j < 0){

			return null;
		}
		V answer = hashTable[j].value;
		hashTable[j] = NULL; 
		j--;
		return answer;
	}

	public int hashFunction(int hash){

		return hash%hashTable.length;
	}
	//put method for my buckets
	protected V bucketPut(int x, K k, V v) {

		int j = findSlot(x, k);
		if (j >= 0) { // this key has an existing entry

			if(keyExist(k)){

				V oldValue = hashTable[j].value;
				hashTable[j].value = v;
				return oldValue;

			}
			System.out.println("\nTable is Full");
			return null;
		}

		hashTable[-(j+1)] = new DataClass<>(k, v);
		j++;
		return null;
	}

	private boolean keyExist(K key){

		for(int i = 0; i < hashTable.length; i++){

			if(hashTable[i].key.equals(key)){

				return true;
			}
		}
		return false;
	}


	@Override
	public V hashGet(K k) {

		int index = 0;
		index = hashFunction(k.hashCode());

		if(index < 0) {
			index = index + hashTable.length;
		}

		return bucketGet(index, k);
	}

	@Override
	public V hashRemove(K k) {

		int index = 0;
		index = hashFunction(k.hashCode());

		if(index < 0) {

			index = index + hashTable.length;
		}
		return bucketRemove(index, k);
	}

	@Override
	public V hashPut(K key, V value) {

		int index = 0;
		index = hashFunction(key.hashCode());

		if(index < 0) {

			index = index + hashTable.length;

		}
		return bucketPut(index, key, value);
	}


	@Override
	public ArrayList<K> keys() {

		ArrayList<K> keys = new ArrayList();

		for (int i = 0; i < hashTable.length; i++) {

			if (hashTable[i] != null && hashTable[i] != NULL){

				keys.add(hashTable[i].key);
			}
		}
		return keys;
	}

	@Override
	public ArrayList<V> values() {

		ArrayList<K> keys = keys();
		ArrayList<V> values = new ArrayList<>();

		for (int i = 0; i < keys.size(); i++) {

			values.add(hashGet(keys.get(i)));
		}

		return values;
	}

	@Override
	public int size() {

		int count = 0;

		for (int i = 0; i < hashTable.length; i++) {

			if (hashTable[i] != null && hashTable[i] != NULL){

				count++;
			}
		}
		return count;
	}

	@Override
	public boolean isEmpty() {

		for (int i = 0; i < hashTable.length; i++) {

			if (hashTable[i] != null && hashTable[i] != NULL){

				return false;

			}
		}
		return true;
	}

	//    //to display indexes with my values / keys
	//    public void displayIndex() {
	//    	for(int index =0; index< hashTable.length; index++) {
	//            if(hashTable[index] != null && hashTable[index].value != null && hashTable[index].key != null){
	//            	System.out.println("[" + index + "]");
	//            } 
	//    	}
	//            
	//    }

	//displaying my key indexes
	public void displayKeyIndex() {
		for(int index = 0; index < hashTable.length; index++){

			if(hashTable[index] != null && hashTable[index].value != null && hashTable[index].key != null){

				System.out.print("[" +index + "]" + " ");

				System.out.print(hashTable[index].key + " ");
			}
			System.out.println();
		}	
	}


	//displaying my value indexes
	public void displayValIndex() {
		for(int index = 0; index < hashTable.length; index++){

			if(hashTable[index] != null && hashTable[index].value != null && hashTable[index].key != null){

				System.out.print("[" +index + "]" + " ");

				System.out.print(hashTable[index].value + " ");
			}
			System.out.println();
		}
	}		


	public void displayTable(){

		for(int index = 0; index < hashTable.length; index++){

			if(hashTable[index] != null && hashTable[index].value != null && hashTable[index].key != null){

				System.out.print("[" +index + "]" + "           ");
				System.out.println(hashTable[index] + " ");

			} 
			else{

				System.out.println("[" +index + "]" + "            " + "null value ");
			}
		}


	}

//	
	//MAIN METHOD
	public static void main(String[] args) {

		// please change text file path to where you have yours
		String fileName ="C:\\Users\\bashi\\Desktop\\BashProg3.txt";
		Scanner file = null;
		Scanner sc = new Scanner(System.in);

		int count;       

		System.out.println("========Probing Hash Table Program========");


		//try to see if file is in system, if not throw exception
		try {


			file = new Scanner(new File(fileName));

		} catch (FileNotFoundException e) {

			System.out.println("No such file exists, or entered incorrectly");

		}
		// creating a ProbingHashTable object
		ProbingHashTable<Key, String> hashT = new ProbingHashTable();

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

		//ArrayLists of keys and strings
		ArrayList<Key> keys = hashT.keys();
		ArrayList<String> values = hashT.values();


		int choice;


		String userInput = "";


		Scanner scan = new Scanner(System.in);

		//prompt user with menu until exit
		do {            
			System.out.println();
			System.out.println("(Please select from the following options, NUMBERS ONLY)");
			System.out.println();

			System.out.println("1. Display Probing Hash Table: ");
			System.out.println("2. Search for a Value using a Key: ");
			System.out.println("3. Delete a Value using a Key: ");
			System.out.println("4. Exit Program: ");

			choice = scan.nextInt();
			scan.nextLine();

			// Switch on choice
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

				choiceDisplay = scan.nextInt();
				scan.nextLine();


				switch(choiceDisplay) {

				case 1:

					System.out.println("Listing only keys: ");

					//    				
					//    					for (int i =0; i <hashT.size(); i++) {
					//    						
					//    						
					//    						System.out.println("Key: " + hashT.keys().get(i));
					//    					}
					//    					System.out.println("AND IN ORDER FROM TOP TO BOTTOM, THEIR CORRESPONDING INDEX");
					hashT.displayKeyIndex();
					break;
				case 2:

					//    					

					//    					System.out.println("Listing only values: ");
					//    					
					//    					for (int i =0; i<hashT.size(); i++) {
					//    						System.out.println("Value: "+ hashT.values().get(i));
					//    					}
					//    					System.out.println("AND IN ORDER FROM TOP TO BOTTOM, THEIR CORRESPONDING INDEX");
					hashT.displayValIndex();
					break;

				case 3:
					System.out.println();
					System.out.println("Display Probing Hash Table: ");
					System.out.println();
					System.out.println("Heap Index[]    " + "Value, Key");
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

				userInput = scan.nextLine();

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

				userInput = scan.nextLine();

				Key delete = new Key(userInput);

				hashT.hashRemove(delete);

				System.out.println("Key " + userInput + " removed (if found)");


				break;

			default:

				System.out.println("Exiting program... ");
				System.exit(0);

				break;
			}
		}
		while (choice != 4);
		sc.close();
	}	//main
} //class
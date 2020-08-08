## Hash Table Programs

Consists of two programs who demonstate a Hash Table Data Structure
- One will use CHAINING as a technique to avoid collisions (ChainingHashTable.java)
- One will use PROBING as a technique to avoid collisions (ProbingHashTable.java)

These programs will read from a text file in the format [VALUE KEY], be sure to change the path to the .txt file in main() (In both ChainingHastTable.java & ProbingHashTable.java)

My hash code function was a Cyclic-Shift Hash Code, it essentially shifts the bit by 5 bytes. It recieves the 5 most left bits and places them on the rightmost side. Resulting in a variety of calculations. I scanned through my file recieving the 5 ASCII charachters and used my hashCode function to convert into an integer.

The compression method on the hashCode I used was a division method.

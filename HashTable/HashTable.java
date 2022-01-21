package il.ac.telhai.ds.hash;

import il.ac.telhai.ds.linkedlist.DLinkedList;

import java.util.LinkedList;

public class HashTable<V> {
	private DLinkedList<V>[] hashTable;
	public static final int DEF_MAX_HASH_SIZE = 10;

	/**
	 * c'tor
	 * construct a hash-table of max-size "DEF_MAX_HASH_SIZE".
	 */
	public HashTable() {
		this(DEF_MAX_HASH_SIZE);
	}
	
	/**
	 * construct a hash-table of size 'hashSize'.
	 * @param hashSize, the size of the constructed hash-table.
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	public HashTable (int hashSize) {
		this.hashTable = new DLinkedList[hashSize];
		for(int i = 0; i < hashSize; i++)
		{
			this.hashTable[i] = new DLinkedList<>();
		}
	}
	
	/**
	 * @param val
	 * @return true if the hash-table contains val, otherwise return false
	 */
	public boolean contains(V val) {
		int index = Math.abs(val.hashCode()) % hashTable.length;
		if(this.hashTable[index].isEmpty())
		{
			return false;
		}
		hashTable[index].goToBeginning();
		while(this.hashTable[index].getCursor() != null)
		{
			if(this.hashTable[index].getCursor().equals(val))
			{
				return true;
			}
			else if(!this.hashTable[index].hasNext())
			{
				break;
			}
			this.hashTable[index].getNext();
		}
		return false;
	}

	/**
	 * Add val to the hash-table.
	 * 
	 * @param val
	 * @return If the val has already exist in the the hash-table, it will not be
	 *         added again. Return true if the val was added successfully. Otherwise
	 *         return false.
	 */
	public boolean add(V val) {
		int index = Math.abs(val.hashCode()) % hashTable.length;
		if(!contains(val))
		{

			this.hashTable[index].insert(val);
			return true;
		}
		return false;
	}

	/**
	 * Remove val from the hash-table.
	 * 
	 * @param val
	 * @return Return true if the val was removed successfully. Otherwise return
	 *         false.
	 */
	public boolean remove(V val) {
		int index = Math.abs(val.hashCode()) % hashTable.length;
		if(!contains(val))
		{
			return false;
		}
		else if(!hashTable[index].isEmpty())
		{
			hashTable[index].remove(val);
			return true;
		}
		return false;
	}

	/**
	 * clear al the data in the hash-table
	 */
	public void clear() {
		for(int i = 0; i < this.hashTable.length; i++)
		{
			this.hashTable[i] = new DLinkedList<>();
		}
	}

	/**
	 * @return true if the hase-table is empty, otherwise return false.
	 */
	public boolean isEmpty() {
		for(int i = 0; i < this.hashTable.length; i++)
		{
			if(!this.hashTable[i].isEmpty())
			{
				return false;
			}
		}
		return true;
	}
}
package il.ac.telhai.ds.heap;

public class MinHeap<T extends Comparable<T>> {
	private T[] data;
	private int counterElements;

	@SuppressWarnings({"unchecked","rawtypes"})
	public MinHeap(int length) {
		data = (T[]) new Comparable[length];
		counterElements = 0;
	}
	
	@SuppressWarnings({"unchecked","rawtypes"})
	public MinHeap(T[] arr) {
		data = (T[]) new Comparable[arr.length];
		counterElements = 0;
		for(int i = data.length-1; i >= 0; i--)
		{
			data[i] = arr[i];
			counterElements++;
		}
		for(int i = data.length-1; i >= 0; i--)
		{
			heapify(i);
		}
	}

	public boolean isFull() {
		return counterElements == data.length;
	}

	public boolean isEmpty() {
		return counterElements == 0;
	}

	public void insert(T element) {
		if(isFull())
		{
			throw new RuntimeException();
		}
		data[counterElements] = element;
		siftUp(counterElements);
		counterElements++;
	}

	public T getMin() {
		if(isEmpty())
		{
			throw new RuntimeException();
		}
		return this.data[0];
	}

	public T deleteMin() {
		if(isEmpty())
		{
			return null;
		}
		T temp = data[0];
		data[0] = data[counterElements-1];
		data[counterElements-1] = null;
		--counterElements;
		heapify(0);
		return temp;
	}

	/**
	 * return a string represents the heap. The order of the elements are according
	 * to The string starts with "[", ends with "]", and the values are seperated
	 * with a comma
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < counterElements; i++)
		{
			sb.append(data[i].toString());
			if(i < counterElements-1)
			{
				sb.append(',');
			}
		}
		sb.append("]");
		return sb.toString();
	}

	private void siftUp(int i){
		while((i > 0) && (data[parent(i)].compareTo(data[i]) > 0))
		{
			T temp = data[parent(i)];
			data[parent(i)] = data[i];
			data[i] = temp;
			i = parent(i);
		}
	}
	private void heapify(int i){ // siftDown
		int smallest = i;
		if((left(i) <= data.length-1) && (data[left(i)] != null) && (data[left(i)].compareTo(data[i]) < 0))
		{
			smallest = left(i);
		}
		if((right(i) <= data.length-1) && (data[right(i)] != null) && (data[right(i)].compareTo(data[smallest]) < 0))
		{
			smallest = right(i);
		}
		if(smallest != i)
		{
			T temp = data[i];
			data[i] = data[smallest];
			data[smallest] = temp;
			heapify(smallest);
		}
	}

	private int left(int i){return (2*(i+1))-1;}
	private int right(int i){return (2*(i+1));}
	private int parent(int i){
		if(i==0)
		{
			return 0;
		}
		return ((i+1)/2)-1;
	}
}

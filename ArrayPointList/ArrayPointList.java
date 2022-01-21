import java.awt.Point;

public class ArrayPointList implements PointList {
	private Point[] points;
	private int size;
	private int cursor;
	
	public ArrayPointList() {
		this.points = new Point[MAX_SIZE];
		this.size = 0;
		this.cursor = 0;
	}

	public ArrayPointList(int maxSize) {
		this.points = new Point[maxSize];
		size = cursor = 0;
	}
	
	@Override
	public void append(Point newPoint) {
		if(isFull()){return;}
		points[size] = newPoint;
		cursor = size;
		++size;
	}

	@Override
	public void clear() {
		this.cursor = 0;
		this.size = 0;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public boolean isFull() {
		return  size == points.length;
	}
	@Override
	public boolean goToBeginning() {
		if(isEmpty()){return  false;}
		this.cursor = 0;
		return true;
	}

	@Override
	public boolean goToEnd() {
		if(isEmpty()){
			return  false;
		}
		cursor = size-1;
		return true;
	}

	@Override
	public boolean goToNext() {
		// If the cursor is not in the last element of the list,
		if(cursor+1 < size){
		cursor++;
		return  true;
		}
		return false;
	}

	@Override
	public boolean goToPrior() {
		if(isEmpty()){
			return  false;
		}
		cursor--;
		return true;
	}

	@Override
	public Point getCursor() {
		if(!isEmpty())
		return new Point(points[cursor]);
		return null;
	}

	@Override
	public String toString() {
		return points.toString();
	}

}

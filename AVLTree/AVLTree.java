package il.ac.telhai.ds.trees;

public class AVLTree<T extends Comparable<T>>{
	
	
	AVLTree<T> left;
	AVLTree<T> right;
	T value;
	int height;
	
	public AVLTree(T value) {
		this.value=value;
		this.left = null;
		this.right = null;
		this.height = 1;

	}

	public int getBalance(AVLTree<T> n) {
		if (n != null) {
			return (getHeight(n.left) - getHeight(n.right));
		}
		return 0;
	}
	
	
	public int getHeight(AVLTree<T> n) {
		if (n != null) {
			return n.height;
		}
		return 0;
	}
	
	
	public AVLTree<T> rightRotate(AVLTree<T> y) {
		AVLTree<T> x = y.left;
		AVLTree<T> T2 = x.right;

		x.right = y;
		y.left = T2;

		x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
		y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

		return x;
	}
	
	public AVLTree<T> leftRotate(AVLTree<T> x) {
		AVLTree<T> y = x.right;
		AVLTree<T> T2 = y.left;

		y.left = x;
		x.right = T2;

		x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
		y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

		return y;
	}
	
	public AVLTree<T> add(T data){
		
		if (this.value.compareTo(data) > 0) {
			if(this.left == null) {
				this.left = new AVLTree<T>(data);
			}
			else {
				this.left = this.left.add(data);
			}
		}
			
		else if(this.value.compareTo(data) < 0){
			if(this.right == null) {
				this.right = new AVLTree<T>(data);
			}
			else {
				this.right = this.right.add(data);
			}
		}
			
		else {
			throw new RuntimeException();
		}

		
		
		this.height = Math.max(getHeight(this.left), getHeight(this.right)) + 1;

		int balDiff = getBalance(this);

		// L Rotate
		if(balDiff >1 && this.left.value.compareTo(data) > 0){
			return rightRotate(this);
		}

		// R Rotate
		if(balDiff < -1 && this.right.value.compareTo(data) < 0){
			return leftRotate(this);
		}

		// LR Rotate
		if(balDiff > 1 && this.left.value.compareTo(data) < 0) {
			this.left = leftRotate(this.left);
			return rightRotate(this);
		}

		// RL Rotate
		if(balDiff <-1 && this.right.value.compareTo(data) > 0) {
			this.right = rightRotate(this.right);
			return leftRotate(this);
		}

		return this;
		
		
	}

	public T getValue() {
		return this.value;
		
	}

	public AVLTree<T> getLeft(){
		if(this.left == null) {
			return null;
		}
		return this.left;
		
	}

	public AVLTree<T> getRight(){
		if(this.right == null) {
			return null;
		}
		return this.right;
	}

}

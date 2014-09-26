import java.util.*;
import java.lang.reflect.Array;

public class PriorityQ<E extends Comparable<E>> {

    private E heap[];
    private Comparator<? super E> c;
    private int elementNo;
    private TreeMap<E, Integer> locator;
    
    public PriorityQ() {
	this(11);
    }

    public PriorityQ(Collection<? extends E> coll) {
	elementNo = 0;
	locator = new TreeMap<E, Integer>();
	heap = alloc(coll.size());
	batchInsert(coll);
    }

    public PriorityQ(int initCapacity) {
	this(initCapacity, null);
    }

    public PriorityQ(int initCapacity, Comparator<? super E> comparator) {
	elementNo = 0;
	locator = new TreeMap<E, Integer>();
	heap = alloc(initCapacity);
	c = comparator;
    }

    public void offer(E element) {
	if (elementNo >= heap.length) {
	    resize(elementNo+1);
	}
	heap[elementNo] = element;
	bubbleUp(elementNo);
	elementNo++;
    }

    public void batchInsert(Collection<? extends E> coll) {
	while (elementNo+coll.size() > heap.length) {
	    resize(elementNo+coll.size());
	}
	int heapIndex = elementNo;
	for (E element : coll) {
	    heap[heapIndex] = element;
	    heapIndex++;
	}
	elementNo = heapIndex;
	for (int i = ((elementNo-2)/2); i >= 0; i--) {
	    siftDown(i);
	}
    }

    public E poll() {
	E result = heap[0];
	remove(result);
	return result;
    }

    public void remove(E element) {
	int index = locator.get(element);
	locator.remove(heap[index]);
	if (index == --elementNo) return;
	heap[index] = heap[elementNo];
	locator.remove(heap[elementNo]);
	forceToTop(index);
	siftDown(0);
    }

    public int size() {
	return elementNo;
    }

    private <E> E[] alloc(int length, E ... base) {
	return Arrays.copyOf(base, length);
    }

    private void resize(int minLength) {
	int newLength = heap.length;
	while (newLength < minLength) {
	    newLength *= 2;
	}
	E temp[] = alloc(newLength);
	for (int i = 0; i < heap.length; i++) {
	    temp[i] = heap[i];
	}
	heap = temp;
    }

    private void forceToTop(int index) {
	if (index == 0) {
	    locator.remove(heap[index]);
	    locator.put(heap[index], index);
	    return;
	}
	int parent = (index-1)/2;
	E temp = heap[parent];
	heap[parent] = heap[index];
	heap[index] = temp;
	locator.remove(heap[index]);
	locator.put(heap[index], index);
	forceToTop(parent);
    }

    private void siftDown(int index) {
	int leftChild = (2*index)+1;
	int rightChild = (2*index)+2;
	if (leftChild >= elementNo || rightChild >= elementNo) {
	    locator.remove(heap[index]);
	    locator.put(heap[index], index);
	    return;
	}
	if (compare(heap[index], heap[leftChild])>0 || compare(heap[index], heap[rightChild])>0) {
	    E temp = heap[index];
	    int smallerChild = leftChild;
	    if (compare(heap[leftChild], heap[rightChild])>0) {
		smallerChild = rightChild;
	    }
	    heap[index] = heap[smallerChild];
	    heap[smallerChild] = temp;
	    locator.remove(heap[index]);
	    locator.put(heap[index], index);
	    siftDown(smallerChild);
	}
	else {
	    locator.remove(heap[index]);
	    locator.put(heap[index], index);
	}
    }

    private void bubbleUp(int index) {
	if (index == 0) {
	    locator.remove(heap[index]);
	    locator.put(heap[index], index);
	    return;
	}
	int parent = (index-1)/2;
	if (compare(heap[index], heap[parent])<0) {
	    E temp = heap[parent];
	    heap[parent] = heap[index];
	    heap[index] = temp;
	    locator.remove(heap[index]);
	    locator.put(heap[index], index);
	    bubbleUp(parent);
	}
	else {
	    locator.remove(heap[index]);
	    locator.put(heap[index], index);
	}
    }

    private int compare(E element1, E element2) {
	if (c != null) {
	    return c.compare(element1, element2);
	}
	else {
	    return element1.compareTo(element2);
	}
    }

    @Override
    public String toString() {
	String result = "[";
	for (int i = 0; i < elementNo; i++) {
	    result = result + (i!=0?", ":"") + heap[i].toString();
	}
	return result + "]";
    }

}

package Labor4.stack;


public class SynchronizedStack<E> {

    private final E[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public SynchronizedStack(int capacity) {
        if(capacity <= 0){
            throw new IllegalArgumentException("capacity must be bigger then 0");
        }
        elements = (E[]) new Object[capacity];
        this.size = 0;
    }

    public synchronized void push(E e) throws InterruptedException {
        while(size >= elements.length)
            wait();
        elements[size] = e;
        size++;
        notify();
    }

    public synchronized E pop() throws InterruptedException {
        while (size <= 0)
            wait();
        size--;
        notify();
        return elements[size];
    }

    public synchronized int size() {
        return size;
    }
}

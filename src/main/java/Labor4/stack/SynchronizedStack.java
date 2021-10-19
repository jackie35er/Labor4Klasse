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

    public synchronized void push(E e) {
        while(size >= elements.length){
            try {
                wait();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        elements[size] = e;
        size++;
        notify();
    }

    public synchronized E pop() {
        while (size <= 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        size--;
        var output = elements[size];
        notify();
        return output;
    }

    public int size() {
        return size;
    }
}

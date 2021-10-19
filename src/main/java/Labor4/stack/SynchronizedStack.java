package Labor4.stack;

public class SynchronizedStack<E> {

    private final E[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public SynchronizedStack(int capacity) {
        elements = (E[]) new Object[capacity];
    }

    public void push(E e) {
        throw new UnsupportedOperationException("Setzt das übergebene Element als Top-of-Stack");
    }

    public E pop() {
        throw new UnsupportedOperationException("Liefert Top-of-Stack");
    }

    public int size() {
        return size;
    }
}

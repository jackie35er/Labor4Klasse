package Labor4.storage;


import Labor4.storage.logging.Log;
import Labor4.storage.logging.LogImpl;
import Labor4.storage.logging.NoOpLog;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Storage {

    private Log log;
    private final int capacity;
    private int currentAmount;
    private List<Supplier> suppliers;
    private List<Thread> supplierThreads;

    public Storage(int capacity) {
        this(capacity,new LogImpl(System.out));
    }

    public Storage(int capacity,@NotNull Log log) {
        if(capacity <= 0){
            throw new IllegalArgumentException("capacity must be bigger then 0");
        }
        this.capacity = capacity;
        this.log = log;
        this.currentAmount = 0;
        this.suppliers = new ArrayList<>();
    }

    public synchronized Good get(int amount) throws InterruptedException {
        if(amount < 0){
            throw new IllegalArgumentException("amount must be bigger than 0");
        }
        if(amount > capacity){
            throw new IllegalArgumentException("amount must be smaller than capacity");
        }

        while(size() - amount < 0){
            wait();
        }
        log.info("amount wird abgeholt: " + amount);
        currentAmount -= amount;
        notify();
        return new Good(amount);
    }

    public synchronized void supply(@NotNull Good good) throws InterruptedException {
        if(good.getAmount() > capacity)
            throw new IllegalArgumentException("amount exceeds capacity:" + good.getAmount());

        while (good.getAmount() + currentAmount > capacity){
            wait();
        }
        log.info("amount wird hinzugefügt: " + good.getAmount());
        currentAmount += good.getAmount();
        notify();
    }

    public synchronized int size() {
        notify();
        return currentAmount;
    }

    public void register(@NotNull Supplier supplier)  {
        suppliers.add(supplier);
        new Thread(supplier).start();
    }

    protected synchronized void notifySupplierDone(Supplier supplier) {
        suppliers.remove(supplier);
    }

    public synchronized boolean hasSupplier(){
        return !suppliers.isEmpty();
    }

}

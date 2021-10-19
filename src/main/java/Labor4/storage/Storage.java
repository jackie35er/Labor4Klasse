package Labor4.storage;


import Labor4.storage.logging.Log;
import Labor4.storage.logging.NoOpLog;

public class Storage {

    private Log log;

    public Storage(int capacity) {
        log = new NoOpLog();
    }

    public Storage(int capacity, Log log) {

    }

    public Good get(int amount) {
        throw new UnsupportedOperationException("Fordert so viele Goods an");
    }

    public void deliver(Good good) {
        throw new UnsupportedOperationException("Fügt eine Good-Lieferung den vorhandenen hinzu");
    }

    public int size() {
        throw new UnsupportedOperationException("Returnt die Anzahl an Goods");
    }

    public void register(Supplier supplier) {
        throw new UnsupportedOperationException("Registriert den Supplier. " +
                "Wird benötigt, damit der Consumer weiß, ob Storage noch befüllt wird, oder nicht");
    }
}

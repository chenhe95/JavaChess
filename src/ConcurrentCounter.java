/**
 * thread safe counter
 * @author He
 */
public class ConcurrentCounter {
    private int count = 0;
    private int initialValue = 0;
    
    public ConcurrentCounter(int initial) {
        count = initial;
        initialValue = initial;
    }
    
    public ConcurrentCounter() {
        this(0);
    }
    
    public synchronized void increment() {
        count++;
    }
    
    public synchronized void reset() {
        count = initialValue;
    }
    
    public synchronized int getCount() {
        return count;
    }
    
    public ConcurrentCounter cloneNew() {
        ConcurrentCounter newCounter = new ConcurrentCounter(initialValue);
        newCounter.count = count;
        return newCounter;
    }
}

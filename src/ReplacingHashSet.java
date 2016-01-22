
import java.util.HashSet;
import java.util.Iterator;

/**
 * ReplacingHashSet assumes that equal objects may have different properties not
 * considered in equals() or hashCode() Therefore, when an add is called, it
 * will replace the current element with a new element rather than simply not
 * add said element 
 * This was specifically designed to handle Piece objects. 
 *
 * @author He
 */
public class ReplacingHashSet extends HashSet<Piece> implements Cloneable {

    @Override
    public synchronized boolean isEmpty() {
        return super.isEmpty();
    }
    
    public synchronized boolean remove(Piece item) {
        //System.out.println("removing " + item + " id " + id);
        return super.remove(item);
    }

    @Override
    public synchronized boolean add(Piece item) {
        if (contains(item)) {
            remove(item);
        }
        return super.add(item);
    }

    public synchronized Piece removeReturnItem(Piece item) {
        Piece removed = null;
        for (Piece t : this) {
            if (t.equals(item)) {
                removed = t;
                remove(t);
                break;
            }
        }
        return removed;
    }

    public synchronized Piece addReturnReplacedItem(Piece item) {
        Piece removed = removeReturnItem(item);
        super.add(item);
        return removed;
    }

    @Override
    public synchronized boolean contains(Object item) {
        return super.contains(item);
    }

    @Override
    public synchronized Iterator<Piece> iterator() {
        return super.iterator();
    }

    @Override
    public synchronized void clear() {
        super.clear();
    }

    @Override
    public ReplacingHashSet clone() {
        ReplacingHashSet newHashSet = new ReplacingHashSet();
        for (Piece item : this) {
            newHashSet.add(item.clone());
        }
        return newHashSet;
    }

}

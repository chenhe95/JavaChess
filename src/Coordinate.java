
/**
 *
 * @author He
 */
public class Coordinate {

    protected int x;
    protected int y;

    public Coordinate(int i, int j) {
        setX(i);
        setY(j);
    }

    public final synchronized int getX() {
        return x;
    }

    public final synchronized int getY() {
        return y;
    }
    
    /**
     * bounds an int to [lower, upper]
     * @param x
     * @param lower
     * @param upper
     * @return 
     */
    private int boundInt(int x, int lower, int upper) {
        return Math.max(lower, Math.min(upper, x));
    }

    public final void setX(int newx) {
        //x = boundInt(newx, 0, Grid.CELL_COUNT);
        x = newx;
    }

    public final void setY(int newy) {
        //y = boundInt(newy, 0, Grid.CELL_COUNT);
        y = newy;
    }

    public final synchronized double distanceTo(Coordinate c) {
        int deltai = getX() - c.getX();
        int deltaj = getY() - c.getY();
        return Math.sqrt(deltai * deltai + deltaj * deltaj);
    }

    /**
     * returns a distinct integer for every unique integer pair (i, j)
     *
     * @param i
     * @param j
     * @return
     */
    public final static int keyCode(int i, int j) {
        return (i + j) * (i + j + 1) / 2 + j;
    }

    @Override
    public synchronized boolean equals(Object o) {
        if (o instanceof Coordinate) {
            Coordinate otherCoord = (Coordinate) o;
            return getX() == otherCoord.getX() && getY() == otherCoord.getY();
        }
        return false;
    }

    @Override
    public synchronized int hashCode() {
        return keyCode(getX(), getY());
    }

    @Override
    public synchronized String toString() {
        return new StringBuilder("(").append(getX()).append(", ").append(getY()).append(
                ")").toString();
    }
    
    
}

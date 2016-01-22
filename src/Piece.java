
import java.awt.Image;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class Piece extends Coordinate implements Cloneable {

    private Team team;
    private String name = null;
    protected int x;
    protected int y;

    protected Piece(int x, int y, String name, Team team) {
        super(x, y);
        this.name = name;
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public boolean isWhite() {
        return team.equals(Team.WHITE);
    }

    public boolean isBlack() {
        return team.equals(Team.BLACK);
    }

    public synchronized void setTeam(Team t) {
        team = t;
    }

    public synchronized void setName(String newName) {
        name = newName;
    }

    protected boolean differentTeamCells(int x, int y, Grid gameGrid) {
        synchronized (gameGrid) {
            Piece p2 = gameGrid.getPiece(x, y);
            if (p2 != null) {
                return !p2.getTeam().equals(getTeam());
            }
        }
        return false;
    }

    /**
     * computes the raw moves that the unit can do unhindered
     *
     * @param gameGrid
     * @return
     */
    public abstract List<Coordinate> computeRawMoves(Grid gameGrid);

    public synchronized List<Coordinate> processFirstStage(Grid gameGrid) {
        return computeRawMoves(gameGrid);
    }

    /**
     * puts restrictions or adds extra opportunities for movement such as
     * castling and moving a king into situations that would result in a check
     *
     * @param gameGrid
     * @return
     */
    public synchronized final List<Coordinate> getValidMoveCells(Grid gameGrid) {
        List<Coordinate> rawMoves = processFirstStage(gameGrid);
        //System.out.println(Arrays.toString(rawMoves.toArray()));
        if (gameGrid.isCheckingRecursively()) {
            synchronized (rawMoves) {
                Iterator<Coordinate> rawMoveIter = rawMoves.iterator();
                while (rawMoveIter.hasNext()) {
                    Coordinate rawMove = rawMoveIter.next();
                    Grid simulation = gameGrid.clone();
                    simulation.movePiece(simulation.getPiece(getX(), getY()),
                            rawMove, true);
                    if (simulation.getKing(getTeam()).isChecked(simulation)) {
                        rawMoves.remove(rawMove);
                        rawMoveIter = rawMoves.iterator(); // reset iterator
                        // so it doesn't think about weird stuff
                        // when we change the array it's iterating on
                        // while we are iterating through it
                    }
                }
            }
        }
        return rawMoves;
    }

    public void moveToCell(Coordinate c) {
        moveToCell(c.getX(), c.getY());
    }

    public void moveToCell(int newx, int newy) {
        //System.out.println("moving to " + new Coordinate(newx, newy));
        setX(newx);
        setY(newy);
        //System.out.println(this);
    }

    public Image getImage() {
        return ImageLibrary.getResizedImage(getName());
    }

    @Override
    public synchronized String toString() {
        return super.toString() + " " + getName();
    }

    public synchronized String getName() {
        return team + " " + name;
    }
    
    public synchronized String getNameOfPiece() {
        return name;
    }

    @Override
    public abstract Piece clone();
}

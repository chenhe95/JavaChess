
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author He
 */
public class King extends SpecialInteractionsPiece {

    private static final int[][] KING_DIRECTIONS = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}, {1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public King(int x, int y, Team team) {
        super(x, y, "King", team);
    }
    
    public King() {
        this(0, 0, null);
    }

    public synchronized boolean hasNoOwnMovesLeft(Grid gameGrid) {
        synchronized (gameGrid) {
            return isChecked(gameGrid) && getValidMoveCells(gameGrid).isEmpty();
        }
    }

    @Override
    public synchronized List<Coordinate> computeRawMoves(Grid gameGrid) {
        List<Coordinate> movesByOwnPiece = new CopyOnWriteArrayList<>();

        for (int[] dir : KING_DIRECTIONS) {
            int tryX = dir[0] + getX();
            int tryY = dir[1] + getY();
            if (Grid.isInGrid(tryX, tryY) && (!gameGrid.containsPiece(
                    tryX, tryY) || differentTeamCells(tryX, tryY,
                            gameGrid))) {
                movesByOwnPiece.add(gameGrid.getCoordinate(tryX, tryY));
            }
        }
        return movesByOwnPiece;
    }

    @Override
    public synchronized List<Coordinate> processFirstStage(Grid gameGrid) {
        List<Coordinate> movesByOwnPiece = super.processFirstStage(gameGrid);
        if (!hasMoved()) {
            /*
             * castling
             */
            Piece pieceR = gameGrid.getPiece(getX() + 3, getY());
            Piece pieceL = gameGrid.getPiece(getX() - 4, getY());
            Rook rookR = null;
            Rook rookL = null;
            if (pieceR != null && pieceR instanceof Rook) {
                rookR = (Rook) pieceR;
            }
            if (pieceL != null && pieceL instanceof Rook) {
                rookL = (Rook) pieceL;
            }
            if (rookR != null && !rookR.hasMoved() && (!gameGrid.containsPiece(
                    getX() + 1,
                    getY()) && !gameGrid.containsPiece(getX() + 2, getY()))) {
                movesByOwnPiece.add(gameGrid.getCoordinate(getX() + 2, getY()));
            }
            if (rookL != null && !rookL.hasMoved() && (!gameGrid.containsPiece(
                    getX() - 1,
                    getY()) && !gameGrid.containsPiece(getX() - 2, getY()) && !gameGrid.containsPiece(
                            getX() - 3, getY()))) {
                movesByOwnPiece.add(gameGrid.getCoordinate(getX() - 2, getY()));
            }
        }
        List<Piece> enemyPieces = isWhite() ? gameGrid.getBlackCells()
                : gameGrid.getWhiteCells();
        for (Piece enemy : enemyPieces) {
            List<Coordinate> enemyMoveCells = enemy.computeRawMoves(gameGrid);
            for (Coordinate ownMove : movesByOwnPiece) {
                if (enemy instanceof King) {
                    if (ownMove.distanceTo(enemy) < 1.5) {
                        // this means that it is within 1 unit of an enemy
                        // king
                        // we will not allow two kings to be
                        // right next to each other 
                        movesByOwnPiece.remove(ownMove);
                    }
                } else {
                    if (enemyMoveCells.contains(ownMove)) {
                        // it is illegal to move into a check 
                        movesByOwnPiece.remove(ownMove);
                    } else if (enemy instanceof Rook || enemy instanceof Queen) {
                        if (noUnitsInBetween(ownMove, enemy, gameGrid, -1) && (ownMove.getX() == enemy.getX() || ownMove.getY() == enemy.getY()) && ownMove.distanceTo(
                                enemy) > 1.001) {
                            movesByOwnPiece.remove(ownMove);
                        }
                    }
                    if (enemy instanceof Bishop || enemy instanceof Queen) {
                        int deltax = ownMove.getX() - enemy.getX();
                        int deltay = ownMove.getY() - enemy.getY();
                        if (noUnitsInBetween(ownMove, enemy, gameGrid, -1) && (deltax == deltay) && ownMove.distanceTo(
                                enemy) > 1.5) {
                            movesByOwnPiece.remove(ownMove);
                        }
                    }
                }
            }
        }
        return movesByOwnPiece;
    }

    public synchronized boolean isChecked(Grid gameGrid) {
        synchronized (gameGrid) {
            for (Piece enemy : gameGrid.getPiecesOfTeam(getTeam().getEnemyTeam())) {
                if (enemy.getValidMoveCells(gameGrid).contains(this)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean noUnitsInBetween(Coordinate c1, Coordinate c2, Grid gameGrid, int direction) {
        Coordinate closestAdjacent = null;
        double closestDistance = 15;
        int directionFound = -1;
        if (direction == -1) {
            for (int i = 0; i < KING_DIRECTIONS.length; i++) {
                int[] dir = KING_DIRECTIONS[i];
                int tryX = c1.getX() + dir[0];
                int tryY = c1.getY() + dir[1];
                if (Grid.isInGrid(tryX, tryY)) {
                    Coordinate tryCoord = gameGrid.getCoordinate(tryX, tryY);
                    if (closestAdjacent == null) {
                        closestAdjacent = tryCoord;
                        closestDistance = tryCoord.distanceTo(c2);
                    } else {
                        if (closestAdjacent.distanceTo(c2) > tryCoord.distanceTo(
                                c2)) {
                            closestDistance = tryCoord.distanceTo(c2);
                            closestAdjacent = tryCoord;
                            directionFound = i;
                        }
                    }
                }
            }
        } else {
            closestAdjacent = gameGrid.getCoordinate(
                    KING_DIRECTIONS[direction][0] + c1.getX(),
                    KING_DIRECTIONS[direction][1] + c1.getY());

        }
        // closestAdjacent == null would imply that we have moved off of the chess
        // board so we are done searching
        if (closestAdjacent != null && !closestAdjacent.equals(c2)) {
            return (!gameGrid.containsPiece(closestAdjacent.getX(),
                    closestAdjacent.getY()) || gameGrid.getPiece(
                            closestAdjacent.getX(), closestAdjacent.getY()) instanceof King) && noUnitsInBetween(
                            closestAdjacent, c2, gameGrid, directionFound);
        } else {
            return true;
        }
    }

    @Override
    public synchronized Piece clone() {
        King k = new King(getX(), getY(), getTeam());
        k.moved = moved;
        return k;
    }

}

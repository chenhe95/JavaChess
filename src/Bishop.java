
import java.util.List;
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
public class Bishop extends Piece {

    public Bishop(int x, int y, Team team) {
        super(x, y, "Bishop", team);
    }
    
    public Bishop() {
        this(0, 0, null);
    }

    @Override
    public synchronized List<Coordinate> computeRawMoves(Grid gameGrid) {
        List<Coordinate> moves = new CopyOnWriteArrayList<>();
        boolean[] checkDirections = {true, true, true, true};
        for (int ij = 1; ij < 3 * Grid.CELL_COUNT / 2; ij++) {
            int moveX = getX() + ij;
            int moveY = getY() + ij;
            if (checkDirections[0] && Grid.isInGrid(moveX, moveY)) {
                if (!gameGrid.containsPiece(moveX, moveY)) {
                    moves.add(gameGrid.getCoordinate(moveX, moveY));
                } else if (differentTeamCells(moveX, moveY, gameGrid)) {
                    moves.add(gameGrid.getCoordinate(moveX, moveY));
                    checkDirections[0] = false;
                } else {
                    checkDirections[0] = false;
                }
            }
            moveX = getX() - ij;
            if (checkDirections[1] && Grid.isInGrid(moveX, moveY)) {
                if (!gameGrid.containsPiece(moveX, moveY)) {
                    moves.add(gameGrid.getCoordinate(moveX, moveY));
                } else if (differentTeamCells(moveX, moveY, gameGrid)) {
                    moves.add(gameGrid.getCoordinate(moveX, moveY));
                    checkDirections[1] = false;
                } else {
                    checkDirections[1] = false;
                }
            }
            moveY = getY() - ij;
            if (checkDirections[2] && Grid.isInGrid(moveX, moveY)) {
                if (!gameGrid.containsPiece(moveX, moveY)) {
                    moves.add(gameGrid.getCoordinate(moveX, moveY));
                } else if (differentTeamCells(moveX, moveY, gameGrid)) {
                    moves.add(gameGrid.getCoordinate(moveX, moveY));
                    checkDirections[2] = false;
                } else {
                    checkDirections[2] = false;
                }
            }
            moveX = getX() + ij;
            if (checkDirections[3] && Grid.isInGrid(moveX, moveY)) {
                if (!gameGrid.containsPiece(moveX, moveY)) {
                    moves.add(gameGrid.getCoordinate(moveX, moveY));
                } else if (differentTeamCells(moveX, moveY, gameGrid)) {
                    moves.add(gameGrid.getCoordinate(moveX, moveY));
                    checkDirections[3] = false;
                } else {
                    checkDirections[3] = false;
                }
            }
        }
        return moves;
    }

    @Override
    public synchronized Piece clone() {
        return new Bishop(getX(), getY(), getTeam());
    }
}


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
public class Knight extends Piece {

    public Knight(int x, int y, Team team) {
        super(x, y, "Knight", team);
    }
    
    public Knight() {
        this(0, 0, null);
    }

    @Override
    public synchronized List<Coordinate> computeRawMoves(Grid gameGrid) {
        List<Coordinate> moves = new CopyOnWriteArrayList<>();
        int lowerXSearchBound = Math.max(0, getX() - 2);
        int upperXSearchBound = Math.min(Grid.CELL_COUNT,
                getX() + 3);
        int lowerYSearchBound = Math.max(0, getY() - 2);
        int upperYSearchBound = Math.min(Grid.CELL_COUNT,
                getY() + 3);
        for (int i = lowerXSearchBound; i < upperXSearchBound; i++) {
            for (int j = lowerYSearchBound; j < upperYSearchBound; j++) {
                if (((Math.abs(i - getX()) == 2 && Math.abs(
                        j - getY()) == 1) || (Math.abs(
                                i - getX()) == 1 && Math.abs(
                                j - getY()) == 2)) && (!gameGrid.containsPiece(
                                i, j) || differentTeamCells(i, j,
                                gameGrid))) {
                    moves.add(gameGrid.getCoordinate(i, j));
                }
            }
        } 
        return moves;
    }

    
        @Override
    public synchronized Piece clone() {
        return new Knight(getX(), getY(), getTeam());
    }
}

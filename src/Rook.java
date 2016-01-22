
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author He
 */
public class Rook extends SpecialInteractionsPiece {

    public Rook(int x, int y, Team team) {
        super(x, y, "Rook", team);
    }
    
    public Rook() {
        this(0, 0, null);
    }

    @Override
    public synchronized List<Coordinate> computeRawMoves(Grid gameGrid) {
        List<Coordinate> moves = Collections.synchronizedList(new ArrayList<Coordinate>());
        for (int i = getX() - 1; i >= 0; i--) {
            if (!gameGrid.containsPiece(i, getY())) {
                moves.add(gameGrid.getCoordinate(i, getY()));
            } else if (differentTeamCells(i, getY(), gameGrid)) {
                moves.add(gameGrid.getCoordinate(i, getY()));
                break;
            } else {
                break;
            }
        }
        for (int i = getX() + 1; i < Grid.CELL_COUNT; i++) {
            if (!gameGrid.containsPiece(i, getY())) {
                moves.add(gameGrid.getCoordinate(i, getY()));
            } else if (differentTeamCells(i, getY(), gameGrid)) {
                moves.add(gameGrid.getCoordinate(i, getY()));
                break;
            } else {
                break;
            }
        }
        for (int j = getY() + 1; j < Grid.CELL_COUNT; j++) {
            if (!gameGrid.containsPiece(getX(), j)) {
                moves.add(gameGrid.getCoordinate(getX(), j));
            } else if (differentTeamCells(getX(), j, gameGrid)) {
                moves.add(gameGrid.getCoordinate(getX(), j));
                break;
            } else {
                break;
            }
        }
        for (int j = getY() - 1; j >= 0; j--) {
            if (!gameGrid.containsPiece(getX(), j)) {
                moves.add(gameGrid.getCoordinate(getX(), j));
            } else if (differentTeamCells(getX(), j, gameGrid)) {
                moves.add(gameGrid.getCoordinate(getX(), j));
                break;
            } else {
                break;
            }
        }
        return moves;
    }

    @Override
    public synchronized Piece clone() {
        Rook r = new Rook(getX(), getY(), getTeam());
        r.moved = moved;
        return r;
    }
}

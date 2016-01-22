
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
public class Pawn extends SpecialInteractionsPiece {

    public Pawn(int x, int y, Team team) {
        super(x, y, "Pawn", team);
    }
    
    public Pawn() {
        this(0, 0, null);
    }

    @Override
    public synchronized List<Coordinate> computeRawMoves(Grid gameGrid) {
        List<Coordinate> moves = new CopyOnWriteArrayList<>();
        switch (getTeam()) {
            case BLACK:
                if (Grid.isInGrid(getX(), getY() + 1) && !gameGrid.containsPiece(
                        getX(), getY() + 1)) {
                    moves.add(gameGrid.getCoordinate(getX(), getY() + 1));
                }
                if (Grid.isInGrid(getX() - 1, getY() + 1) && gameGrid.containsPiece(
                        getX() - 1, getY() + 1) && differentTeamCells(
                                getX() - 1, getY() + 1, gameGrid)) {
                    moves.add(gameGrid.getCoordinate(getX() - 1, getY() + 1));
                }
                if (Grid.isInGrid(getX() + 1, getY() + 1) && gameGrid.containsPiece(
                        getX() + 1, getY() + 1) && differentTeamCells(
                                getX() + 1, getY() + 1, gameGrid)) {
                    moves.add(gameGrid.getCoordinate(getX() + 1, getY() + 1));
                }
                break;
            case WHITE:
                if (Grid.isInGrid(getX(), getY() - 1) && !gameGrid.containsPiece(
                        getX(), getY() - 1)) {
                    moves.add(gameGrid.getCoordinate(getX(), getY() - 1));
                }
                if (Grid.isInGrid(getX() - 1, getY() - 1) && gameGrid.containsPiece(
                        getX() - 1, getY() - 1) && differentTeamCells(
                                getX() - 1, getY() - 1, gameGrid)) {
                    moves.add(gameGrid.getCoordinate(getX() - 1, getY() - 1));
                }
                if (Grid.isInGrid(getX() + 1, getY() - 1) && gameGrid.containsPiece(
                        getX() + 1, getY() - 1) && differentTeamCells(
                                getX() + 1, getY() - 1, gameGrid)) {
                    moves.add(gameGrid.getCoordinate(getX() + 1, getY() - 1));
                }
                break;
        }
        return moves;
    }

    @Override
    public List<Coordinate> processFirstStage(Grid gameGrid) {
        List<Coordinate> moves = super.processFirstStage(gameGrid);
        if (!hasMoved()) {
            switch (getTeam()) {
                case BLACK:
                    if (moves.contains(gameGrid.getCoordinate(getX(),
                            getY() + 1))) {
                        if (Grid.isInGrid(getX(), getY() + 2) && !gameGrid.containsPiece(
                                getX(), getY() + 2)) {
                            moves.add(gameGrid.getCoordinate(getX(),
                                    getY() + 2));
                        }
                    }
                    break;
                case WHITE:
                    if (moves.contains(gameGrid.getCoordinate(getX(),
                            getY() - 1))) {
                        if (Grid.isInGrid(getX(), getY() - 2) && !gameGrid.containsPiece(
                                getX(), getY() - 2)) {
                            moves.add(gameGrid.getCoordinate(getX(),
                                    getY() - 2));
                        }
                    }
                    break;
            }
        } else {
            Pawn enPassant = gameGrid.getEnPassant();
            if (enPassant != null) {
                if (Math.abs(enPassant.getX() - getX()) == 1) {
                    Coordinate enPassantCoord = null;
                    if (enPassant.getTeam().equals(Team.WHITE)) {
                        enPassantCoord = gameGrid.getCoordinate(enPassant.getX(),
                                enPassant.getY() + 1);
                    } else {
                        enPassantCoord = gameGrid.getCoordinate(enPassant.getX(),
                                enPassant.getY() - 1);
                    }
                    if (!gameGrid.containsPiece(enPassantCoord.getX(),
                            enPassantCoord.getY())) {
                        moves.add(enPassantCoord);
                    }
                }
            }
        }
        return moves;
    }

    @Override
    public synchronized Piece clone() {
        Pawn p = new Pawn(getX(), getY(), getTeam());
        p.moved = moved;
        return p;
    }

}

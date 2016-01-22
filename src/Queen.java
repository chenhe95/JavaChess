
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
public class Queen extends Piece {

    public Queen(int x, int y, Team team) {
        super(x, y, "Queen", team);
    }
    
    public Queen() {
        this(0, 0, null);
    }

    private synchronized void addRookMoves(List<Coordinate> moves, Grid gameGrid) {
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
    }

    private synchronized void addBishopMoves(List<Coordinate> moves, Grid gameGrid) {
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
    }

    @Override
    public synchronized List<Coordinate> computeRawMoves(Grid gameGrid) {
        List<Coordinate> moves = new CopyOnWriteArrayList<>();
        addRookMoves(moves, gameGrid);
        addBishopMoves(moves, gameGrid);
        return moves;
    }

    @Override
    public synchronized Piece clone() {
        return new Queen(getX(), getY(), getTeam());
    }
}

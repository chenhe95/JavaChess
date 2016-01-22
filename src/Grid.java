
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * implement checkmate conditions when the units can kill the attacking units
 * en-passe
 *
 * @author He
 */
public class Grid implements Cloneable {

    private Coordinate[][] cells = new Coordinate[CELL_COUNT][CELL_COUNT];

    private boolean checkRecursively = false;
    private ReplacingHashSet pieces = new ReplacingHashSet();
    private List<Piece> whiteGraveyard = new CopyOnWriteArrayList<>();
    private List<Piece> blackGraveyard = new CopyOnWriteArrayList<>();
    public static final int CELL_COUNT = 8;
    public static final int CELL_SIZE = 30;
    public static final int GRID_SIZE = CELL_COUNT * CELL_SIZE;
    private Pawn enPassant = null;

    /**
     * there is a rule in chess where after 50 consecutive turns of no pawn
     * moved or piece taken, the game must be declared a draw
     *
     * this counter keeps track of the number of consecutive turns with such
     * conditions
     */
    private ConcurrentCounter inactivity = new ConcurrentCounter();

    private Grid(boolean initialize, boolean recursive) {
        for (int i = 0; i < CELL_COUNT; i++) {
            for (int j = 0; j < CELL_COUNT; j++) {
                cells[i][j] = new Coordinate(i, j);
            }
        }
        if (initialize) {
            reset();
        }
        checkRecursively = recursive;
    }

    public Grid() {
        this(true, true);
    }

    public boolean isDraw() {
        return inactivity.getCount() >= 50;
    }

    public boolean isCheckingRecursively() {
        return checkRecursively;
    }

    public synchronized Pawn getEnPassant() {
        return enPassant;
    }

    public synchronized void resetEnPassant() {
        enPassant = null;
    }

    public synchronized void saveGrid(String file, Game game) {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
                    new File(file))));
            for (Piece p : whiteGraveyard) {
                StringBuilder sb = new StringBuilder("X").append("White")
                        .append(":").append(p.getX()).append(":").append(
                                p.getY()).append(":").append(p.getNameOfPiece());
                if (p instanceof SpecialInteractionsPiece) {
                    sb.append(":").append(
                            ((SpecialInteractionsPiece) p).hasMoved());
                }
                pw.println(sb.toString());
            }
            for (Piece p : blackGraveyard) {
                StringBuilder sb = new StringBuilder("X").append("Black")
                        .append(":").append(p.getX()).append(":").append(
                                p.getY()).append(":").append(p.getNameOfPiece());
                if (p instanceof SpecialInteractionsPiece) {
                    sb.append(":").append(
                            ((SpecialInteractionsPiece) p).hasMoved());
                }
                pw.println(sb.toString());
            }
            for (Piece p : pieces) {
                StringBuilder sb = new StringBuilder().append(
                        p.getTeam().toString()).append(
                                ":").append(p.getX()).append(":").append(
                                p.getY()).append(":").append(p.getNameOfPiece());
                if (p instanceof SpecialInteractionsPiece) {
                    sb.append(":").append(
                            ((SpecialInteractionsPiece) p).hasMoved());
                }
                pw.println(sb.toString());
            }
            pw.println("T:" + game.getTurn());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * loads this save file to this specific grid and game
     *
     * @param file
     * @param game
     */
    public synchronized void loadGrid(String file, Game game) {
        pieces.clear();
        blackGraveyard.clear();
        whiteGraveyard.clear();
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(new File(file)));
            String line;
            try {
                while ((line = in.readLine()) != null) {
                    String[] tokens = line.split(":");
                    if (line.startsWith("T")) {
                        if (tokens[1].contains("hite")) {
                            if (game.getTurn().equals(Team.BLACK)) {
                                game.nextTurn();
                            }
                        } else {
                            if (game.getTurn().equals(Team.WHITE)) {
                                game.nextTurn();
                            }
                        }
                        // adjusts the turn for the game too
                    } else {
                        Team team = tokens[0].contains("hite") ? Team.WHITE : Team.BLACK;
                        int x = Integer.parseInt(tokens[1]);
                        int y = Integer.parseInt(tokens[2]);
                        Class obj = Class.forName(tokens[3]);
                        Piece refPiece = (Piece) obj.newInstance();
                        refPiece.setX(x);
                        refPiece.setY(y);
                        refPiece.setTeam(team);
                        if (refPiece instanceof SpecialInteractionsPiece) {
                            ((SpecialInteractionsPiece) refPiece).setMoved(
                                    Boolean.parseBoolean(tokens[4]));
                        }
                        if (line.startsWith("X")) {
                            if (team.equals(Team.WHITE)) {
                                whiteGraveyard.add(refPiece);
                            } else {
                                blackGraveyard.add(refPiece);
                            }
                        } else {
                            pieces.add(refPiece);
                        }
                    }
                }
            } catch (RuntimeException e) {
                if (!(e instanceof NullPointerException)) {
                    System.out.println("Incorrectly formated file: " + file);
                    e.printStackTrace();
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * is this team checkmated?
     *
     * @param team
     * @return
     */
    public synchronized boolean isCheckmated(Team team) {
        King king = getKing(team);
        if (king == null) {
            return true;
        }
        boolean kingNoMoves = king.hasNoOwnMovesLeft(this);
        if (kingNoMoves) {
            if (getCompleteSetOfMoves(team).isEmpty()) {
                return true;
            }
            for (Piece ally : getPiecesOfTeam(team)) {
                for (Coordinate possibleCoordinate : ally.getValidMoveCells(this)) {
                    Grid simulation = clone();
                    simulation.movePiece(simulation.getPiece(ally.getX(),
                            ally.getY()), possibleCoordinate, false);
                    if (!simulation.getKing(team).hasNoOwnMovesLeft(
                            simulation)) {
                        // if there exists a move such that the king is not in
                        // check anymore, then it is not over yet
                        return false;
                    }
                }
            }
        }
        return kingNoMoves;
    }

    public synchronized King getKing(Team team) {
        for (Piece piece : pieces) {
            if (piece instanceof King) {
                King king = (King) piece;
                if (king.getTeam().equals(team)) {
                    return king;
                }
            }
        }
        return null;
    }

    public synchronized Set<Coordinate> getCompleteSetOfMoves(Team team) {
        List<Piece> piecesOfCurrentPlayer = team.equals(Team.WHITE) ? getWhiteCells() : getBlackCells();
        Set<Coordinate> moves = new HashSet<>();
        for (Piece p : piecesOfCurrentPlayer) {
            //synchronized (p) {
            List<Coordinate> legalMoves = p.getValidMoveCells(this);
            for (Coordinate moveLocation : legalMoves) {
                moves.add(moveLocation);
            }
            //}
        }
        return moves;
    }

    public List<Piece> getWhiteGraveyard() {
        return whiteGraveyard;
    }

    public List<Piece> getBlackGraveyard() {
        return blackGraveyard;
    }

    public synchronized void reset() {
        pieces.clear();
        blackGraveyard.clear();
        whiteGraveyard.clear();
        for (int i = 0; i < CELL_COUNT; i++) {
            pieces.add(new Pawn(i, 1, Team.BLACK));
            pieces.add(new Pawn(i, CELL_COUNT - 2, Team.WHITE));
        }
        pieces.add(new Rook(0, 0, Team.BLACK));
        pieces.add(new Rook(CELL_COUNT - 1, 0, Team.BLACK));
        pieces.add(new Rook(CELL_COUNT - 1, CELL_COUNT - 1, Team.WHITE));
        pieces.add(new Rook(0, CELL_COUNT - 1, Team.WHITE));

        pieces.add(new Knight(1, 0, Team.BLACK));
        pieces.add(new Knight(CELL_COUNT - 2, 0, Team.BLACK));
        pieces.add(new Knight(CELL_COUNT - 2, CELL_COUNT - 1, Team.WHITE));
        pieces.add(new Knight(1, CELL_COUNT - 1, Team.WHITE));

        pieces.add(new Bishop(2, 0, Team.BLACK));
        pieces.add(new Bishop(CELL_COUNT - 3, 0, Team.BLACK));
        pieces.add(new Bishop(CELL_COUNT - 3, CELL_COUNT - 1, Team.WHITE));
        pieces.add(new Bishop(2, CELL_COUNT - 1, Team.WHITE));

        pieces.add(new Queen(3, 0, Team.BLACK));
        pieces.add(new Queen(3, CELL_COUNT - 1, Team.WHITE));

        pieces.add(new King(4, 0, Team.BLACK));
        pieces.add(new King(4, CELL_COUNT - 1, Team.WHITE));
    }

    public synchronized Set<Piece> getPieces() {
        return pieces;
    }

    /**
     * move the piece at c1 to c2
     *
     * @param pieceSelected
     * @param targetLocation
     */
    public synchronized void movePiece(Piece pieceSelected, Coordinate targetLocation, boolean addGraveyard) {
        if (targetLocation != null && pieceSelected != null) {
            if (pieceSelected instanceof Pawn) {
                inactivity.reset();
            }
            if (pieceSelected instanceof King && targetLocation.distanceTo(
                    pieceSelected) > 1.5) {
                // the only instance where a king is allowed to
                // move more than sqrt(2) is when it is castling
                // therefore we must move the associated rook as well
                Piece rookR = getPiece(targetLocation.getX() + 1,
                        targetLocation.getY());
                Piece rookL = getPiece(targetLocation.getX() - 2,
                        targetLocation.getY());
                if (rookR != null) {
                    movePiece(rookR, getCoordinate(targetLocation.getX() - 1,
                            targetLocation.getY()),
                            addGraveyard);
                } else if (rookL != null) {
                    movePiece(rookL, getCoordinate(targetLocation.getX() + 1,
                            targetLocation.getY()),
                            addGraveyard);
                }
            }
            // adds a double check to see if it is doing an enpasse move
            boolean checkEnPasse = pieceSelected instanceof Pawn && targetLocation.distanceTo(
                    pieceSelected) > 1.5;

            // the pawn must be doing an enpasse attack if it is moving diagonally
            // into an empty unit
            boolean executeEnPasseAttack = pieceSelected instanceof Pawn && targetLocation.distanceTo(
                    pieceSelected) > 1.3 && !containsPiece(targetLocation.getX(),
                            targetLocation.getY());
            pieces.remove(pieceSelected);
            pieceSelected.moveToCell(targetLocation);
            Piece removed = pieces.addReturnReplacedItem(pieceSelected);
            if (removed != null) {
                if (addGraveyard) {
                    if (removed.isBlack()) {
                        blackGraveyard.add(removed);
                    } else {
                        whiteGraveyard.add(removed);
                    }
                }
                inactivity.reset();
            } else {
                inactivity.increment();
            }
            if (checkEnPasse) {
                Pawn pawn = (Pawn) pieceSelected;
                Piece right = getPiece(pieceSelected.getX() + 1,
                        pieceSelected.getY());
                Piece left = getPiece(pieceSelected.getX() - 1,
                        pieceSelected.getY());
                if ((right != null && right instanceof Pawn && !right.getTeam().equals(
                        pieceSelected.getTeam())) || (left != null && left instanceof Pawn && !left.getTeam().equals(
                                pieceSelected.getTeam()))) {
                    enPassant = pawn;
                }
            } else if (executeEnPasseAttack) {
                Piece victim = null;
                if (pieceSelected.getTeam().equals(Team.BLACK)) {
                    victim = getPiece(pieceSelected.getX(),
                            pieceSelected.getY() - 1);
                    whiteGraveyard.add(victim);
                } else {
                    victim = getPiece(pieceSelected.getX(),
                            pieceSelected.getY() + 1);
                    blackGraveyard.add(victim);
                }
                pieces.remove(victim);
            }
        }
    }

    /**
     * gets the cells containing white pieces
     *
     * @return
     */
    public synchronized List<Piece> getWhiteCells() {
        return getPiecesOfTeam(Team.WHITE);
    }

    /**
     * gets the cells containing black pieces
     *
     * @return
     */
    public synchronized List<Piece> getBlackCells() {
        return getPiecesOfTeam(Team.BLACK);
    }

    public synchronized List<Piece> getPiecesOfTeam(Team t) {
        List<Piece> piecesOfTeam = new ArrayList<>();
        for (Piece p : pieces) {
            if (p.getTeam().equals(t)) {
                piecesOfTeam.add(p);
            }
        }
        return piecesOfTeam;
    }

    /**
     * is there a piece at (i, j)?
     *
     * @param i
     * @param j
     * @return
     */
    public synchronized boolean containsPiece(int i, int j) {
        return isInGrid(i, j) && pieces.contains(getCoordinate(i, j));
    }

    /**
     * gets the cell at (i, j)
     *
     * @param i
     * @param j
     * @return
     */
    public synchronized Piece getPiece(int i, int j) {
        if (containsPiece(i, j)) {
            for (Piece p : pieces) {
                if (p.getX() == i && p.getY() == j) {
                    return p;
                }
            }
        }
        return null;
    }

    public Coordinate getCoordinate(int i, int j) {
        return isInGrid(i, j) ? cells[i][j] : null;
    }

    /**
     * is index (i, j) a valid index to access cells in the grid?
     *
     * @param i
     * @param j
     * @return
     */
    public static boolean isInGrid(int i, int j) {
        return i >= 0 && i < CELL_COUNT && j
                >= 0 && j < CELL_COUNT;
    }

    private List<Piece> copyList(List<Piece> target) {
        ArrayList<Piece> list = new ArrayList<>();
        for (Piece item : target) {
            list.add(item.clone());
        }
        return list;
    }

    @Override
    public synchronized Grid clone() {
        Grid grid = new Grid(false, false);
        synchronized (grid) {
            grid.pieces = pieces.clone();
            grid.whiteGraveyard = copyList(whiteGraveyard);
            grid.blackGraveyard = copyList(whiteGraveyard);
            grid.inactivity = inactivity.cloneNew();
        }
        return grid;
    }

    @Override
    public synchronized boolean equals(Object o) {
        if (o instanceof Grid) {
            Grid grid = (Grid) o;
            return grid.pieces.equals(pieces) && grid.blackGraveyard.equals(
                    blackGraveyard) && grid.whiteGraveyard.equals(whiteGraveyard);
        }
        return false;
    }
}

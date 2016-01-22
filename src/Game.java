
import java.io.File;
import java.io.FileWriter;
import javax.swing.SwingUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author He
 */
public class Game implements Runnable {

    public static final String ROOT_DIRECTORY = "src/";
    public static final String NAME = "Not Chess Again!";
    private Grid gameGrid = null;
    private Team turn = Team.WHITE;

    public Game() {
        gameGrid = new Grid();
    }

    public Grid getGrid() {
        return gameGrid;
    }

    public synchronized Team getTurn() {
        synchronized (turn) {
            return turn;
        }
    }

    public synchronized void nextTurn() {
        turn = turn.getEnemyTeam();
        if (gameGrid.getEnPassant() != null && gameGrid.getEnPassant().getTeam().equals(
                turn)) {
            // the en passant mechanic can only work for the turn
            // immediately after, so this will make it expired
            gameGrid.resetEnPassant();
        }
    }

    @Override
    public void run() {
        ChessUI ui = new ChessUI(this);
        ui.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Game());
    }
}

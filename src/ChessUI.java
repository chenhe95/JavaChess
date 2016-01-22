
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Chess UI
 *
 * @author He
 */
public class ChessUI extends javax.swing.JFrame {

    private InstructionUI ui2 = new InstructionUI();
    private LoadUI uiLoad;
    private Game game = null;
    private Coordinate hoveredCoordinate = null;
    private Piece firstSelectedPiece = null;
    private List<Coordinate> validMoves = Collections.<Coordinate>emptyList();
    private Team checkmate = null;
    private Team promotionTeam = null;
    private GameState state = GameState.PLAY;
    private int hoveredPromotion = -1;

    public static final String SAVE_DIRECTORY = "save/";
    public static final int IMAGE_OFFSET = 3;
    private final int CENTERED_INNER_PROMOTION_BOX_X;
    private final int CENTERED_INNER_PROMOTION_BOX_Y;
    private final int GRID_X_OFFSET;
    private final int GRID_Y_OFFSET;
    private final int DISPLAY_WIDTH;
    private final int DISPLAY_HEIGHT;

    public ChessUI(Game game) {
        this.game = game;
        uiLoad = new LoadUI(game);
        initComponents();
        GRID_X_OFFSET = ((DISPLAY_WIDTH = displayPanel.getWidth()) - Grid.GRID_SIZE) / 2;
        GRID_Y_OFFSET = ((DISPLAY_HEIGHT = displayPanel.getHeight()) - Grid.GRID_SIZE) / 2;
        CENTERED_INNER_PROMOTION_BOX_Y = GRID_Y_OFFSET + Grid.GRID_SIZE / 2;
        CENTERED_INNER_PROMOTION_BOX_X = GRID_X_OFFSET + Grid.GRID_SIZE / 2;
        ((Canvas) displayPanel).setBKG();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backGroundPanel = new javax.swing.JPanel();
        displayPanel = new Canvas();
        startButton = new javax.swing.JButton();
        save = new javax.swing.JButton();
        load = new javax.swing.JButton();
        viewInstructions = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chess");
        setResizable(false);

        displayPanel.setBackground(new java.awt.Color(255, 255, 255));
        displayPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                displayPanelMouseMoved(evt);
            }
        });
        displayPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                displayPanelMousePressed(evt);
            }
        });

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 529, Short.MAX_VALUE)
        );

        startButton.setText("Start / Reset");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        save.setText("Save");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        load.setText("Load");
        load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadActionPerformed(evt);
            }
        });

        viewInstructions.setText("View Instructions");
        viewInstructions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewInstructionsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backGroundPanelLayout = new javax.swing.GroupLayout(backGroundPanel);
        backGroundPanel.setLayout(backGroundPanelLayout);
        backGroundPanelLayout.setHorizontalGroup(
            backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backGroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(startButton)
                .addGap(534, 534, 534)
                .addGroup(backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backGroundPanelLayout.createSequentialGroup()
                        .addComponent(load, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewInstructions)
                .addContainerGap())
        );
        backGroundPanelLayout.setVerticalGroup(
            backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backGroundPanelLayout.createSequentialGroup()
                .addComponent(displayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backGroundPanelLayout.createSequentialGroup()
                        .addGroup(backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(save)
                            .addComponent(viewInstructions))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(load)
                        .addGap(0, 13, Short.MAX_VALUE))
                    .addComponent(startButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backGroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(backGroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        synchronized (this) {
            game.getGrid().reset();
            hoveredCoordinate = null;
            firstSelectedPiece = null;
            validMoves = Collections.<Coordinate>emptyList();
            checkmate = null;
            state = GameState.PLAY;
            if (game.getTurn().equals(Team.BLACK)) {
                game.nextTurn();
            }
        }
        displayPanel.repaint();
    }//GEN-LAST:event_startButtonActionPerformed

    private void displayPanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayPanelMouseMoved
        handleDisplayMoved(evt);
    }//GEN-LAST:event_displayPanelMouseMoved

    private synchronized void handleDisplayMoved(MouseEvent evt) {
        if (state.equals(GameState.PLAY)) {
            Coordinate ijProj = getIndexProjection(evt.getX(), evt.getY());
            if (Grid.isInGrid(ijProj.getX(), ijProj.getY()) && (validMoves.isEmpty() || validMoves.contains(
                    ijProj) || (game.getGrid().containsPiece(ijProj.getX(),
                            ijProj.getY()) && game.getGrid().getPiece(
                            ijProj.getX(),
                            ijProj.getY()).getTeam().equals(
                            firstSelectedPiece.getTeam())))) {
                hoveredCoordinate = ijProj;
            } else {
                hoveredCoordinate = null;
            }
        } else if (state.equals(GameState.PROMOTE)) {
            int indexSelected = (evt.getX() - CENTERED_INNER_PROMOTION_BOX_X + 2 * Grid.CELL_SIZE) / Grid.CELL_SIZE;
            if (indexSelected >= 0 && indexSelected < 4
                    && evt.getY() >= CENTERED_INNER_PROMOTION_BOX_Y - Grid.CELL_SIZE / 2
                    && evt.getY() <= CENTERED_INNER_PROMOTION_BOX_Y + Grid.CELL_SIZE / 2) {
                hoveredPromotion = indexSelected;
            } else {
                hoveredPromotion = -1;
            }
        }
        displayPanel.repaint();
    }

    private void displayPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayPanelMousePressed
        handleDisplayPanelMousePressed(evt);
    }//GEN-LAST:event_displayPanelMousePressed

    private synchronized void handleDisplayPanelMousePressed(MouseEvent evt) {
        if (state.equals(GameState.PLAY)) {
            Coordinate ijProj = getIndexProjection(evt.getX(), evt.getY());
            if (Grid.isInGrid(ijProj.getX(), ijProj.getY())) {
                // only allow the user to select a cell if it has a piece in it
                Piece selectedPiece = game.getGrid().getPiece(ijProj.getX(),
                        ijProj.getY());
                if (selectedPiece != null && selectedPiece.getTeam().equals(
                        game.getTurn())) {
                    // no piece currently selected
                    // must be selecting a piece to do an action with 
                    firstSelectedPiece = game.getGrid().getPiece(ijProj.getX(),
                            ijProj.getY());
                    validMoves = firstSelectedPiece.getValidMoveCells(
                            game.getGrid());
                } else {
                    if (validMoves.contains(ijProj)) {
                        game.getGrid().movePiece(firstSelectedPiece, ijProj,
                                true);
                        if (firstSelectedPiece instanceof Pawn && (ijProj.getY() == 0 || ijProj.getY() == Grid.CELL_COUNT - 1)) {
                            setState(GameState.PROMOTE, game.getTurn());
                        } else {
                            firstSelectedPiece = null;
                        }
                        validMoves = Collections.<Coordinate>emptyList();
                        game.nextTurn();
                    }
                }
            }
        } else if (state.equals(GameState.PROMOTE)) {
            int indexSelected = (evt.getX() - CENTERED_INNER_PROMOTION_BOX_X + 2 * Grid.CELL_SIZE) / Grid.CELL_SIZE;
            if (indexSelected >= 0 && indexSelected < 4) {
                Piece replacement = null;
                switch (indexSelected) {
                    case 0:
                        replacement = new Queen(firstSelectedPiece.getX(),
                                firstSelectedPiece.getY(),
                                game.getTurn().getEnemyTeam());
                        break;
                    case 1:
                        replacement = new Knight(firstSelectedPiece.getX(),
                                firstSelectedPiece.getY(),
                                game.getTurn().getEnemyTeam());
                        break;
                    case 2:
                        replacement = new Rook(firstSelectedPiece.getX(),
                                firstSelectedPiece.getY(),
                                game.getTurn().getEnemyTeam());
                        break;
                    case 3:
                        replacement = new Bishop(firstSelectedPiece.getX(),
                                firstSelectedPiece.getY(),
                                game.getTurn().getEnemyTeam());
                        break;
                }
                game.getGrid().movePiece(replacement, firstSelectedPiece, false);
                firstSelectedPiece = null;
                setState(GameState.PLAY, null);
            }
        }
        synchronized (this) {
            if (game.getGrid().isDraw()) {
                setState(GameState.DRAW, null);
            } else {
                if (game.getGrid().isCheckmated(Team.WHITE)) {
                    setState(GameState.CHECKMATE, Team.WHITE);
                } else if (game.getGrid().isCheckmated(Team.BLACK)) {
                    setState(GameState.CHECKMATE, Team.BLACK);
                }
            }
        }
        displayPanel.repaint();
    }

    private synchronized void setState(GameState newState, Team team) {
        state = newState;
        switch (newState) {
            case PROMOTE:
                promotionTeam = team;
                break;
            case CHECKMATE:
                checkmate = team;
        }
    }

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        switch (state) {
            case PLAY:
                String inputValue = JOptionPane.showInputDialog(
                        "Please input the name of the file to save");
                if (inputValue != null && !inputValue.equals("")) {
                    inputValue = inputValue.replaceAll("\\.", "");
                    game.getGrid().saveGrid(
                            SAVE_DIRECTORY + inputValue + ".save",
                            game);
                    uiLoad.refresh();
                }
                break;
            case PROMOTE:
                System.out.println(
                        "Please finish whatever you are doing before saving!");
                break;
            case CHECKMATE:
            case DRAW:
                System.out.println("The game is already over!");
                break;
        }
    }//GEN-LAST:event_saveActionPerformed

    private void loadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadActionPerformed
        uiLoad.setVisible(true);
    }//GEN-LAST:event_loadActionPerformed

    private void viewInstructionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewInstructionsActionPerformed
        ui2.setVisible(true);
    }//GEN-LAST:event_viewInstructionsActionPerformed

    private Coordinate getIndexProjection(int xOnPanel, int yOnPanel) {
        int iProjection = (xOnPanel - GRID_X_OFFSET) / Grid.CELL_SIZE;
        int jProjection = (yOnPanel - GRID_Y_OFFSET) / Grid.CELL_SIZE;
        return new Coordinate(iProjection, jProjection);
    }

    private String getDisplayText() {
        switch (state) {
            case CHECKMATE:
                return checkmate + " is checkmated; " + checkmate.getEnemyTeam() + " wins!";
            case DRAW:
                return "This game is a draw!";
            case PROMOTE:
                return "Choose any of these pieces to promote to!";
            default:
                return null;
        }
    }

    /**
     * This class handles all of the graphics involved with the JFrame
     */
    private class Canvas extends JPanel {

        private Canvas() {
            super();
            Thread update = new Thread(new Runnable() {
                @Override
                public synchronized void run() {
                    while (true) {
                        try {
                            repaint();
                            wait(25);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            });
            update.setDaemon(true);
            update.start();
        }

        private Image bkgImage = null;

        private final float MIX_FACTOR = 0.5f;
        private final Color HOVER_COLOR = Color.YELLOW;
        private final Color SELECT_COLOR = Color.GREEN;
        private final Color ATTACK_COLOR = Color.RED;
        private final Color TRANSPARENT_BLACK = new Color(25, 25, 25, 150);

        private final int DISPLAY_BOX_LENGTH = 344;
        private final int DISPLAY_BOX_HEIGHT = 122;

        private final int END_INNERBOX_XOFFSET = 6;
        private final int END_INNERBOX_YOFFSET = 3;

        private final int GRAVEYARD_UNIT_WIDTH = 4; //4 cells wide
        private final int GRAVEYARD_BREATHING_ROOM = Grid.CELL_SIZE / 2;
        private final int GRAVEYARD_BOX_LENGTH = GRAVEYARD_UNIT_WIDTH * Grid.CELL_SIZE + 2 * GRAVEYARD_BREATHING_ROOM;

        private final Image[] PROMOTION_IMAGE_SET = {ImageLibrary.getResizedImage(
            "White Queen"), ImageLibrary.getResizedImage(
            "White Knight"), ImageLibrary.getResizedImage(
            "White Rook"), ImageLibrary.getResizedImage("White Bishop"), ImageLibrary.getResizedImage(
            "Black Queen"), ImageLibrary.getResizedImage(
            "Black Knight"), ImageLibrary.getResizedImage(
            "Black Rook"), ImageLibrary.getResizedImage("Black Bishop")};

        private final int WAIT_THRESHOLD = 50;
        private final int[] orangeM = {0, 0, 100, 100};
        private Paint orangePaint = null;
        private long startMS = -1;

        public void setBKG() {
            bkgImage = ImageLibrary.getCustomResizedImage("carbonbkg",
                    DISPLAY_WIDTH, DISPLAY_HEIGHT);
        }

        private Color taintColor(Color base, Color influence) {
            return new Color(
                    base.getRed() + ((int) ((influence.getRed() - base.getRed()) * MIX_FACTOR)),
                    base.getBlue() + ((int) ((influence.getGreen() - base.getGreen()) * MIX_FACTOR)),
                    base.getBlue() + ((int) ((influence.getBlue() - base.getBlue()) * MIX_FACTOR)));
        }

        private int getGraveyardHeight(int size) {
            return ((int) Math.ceil(
                    ((double) size) / GRAVEYARD_UNIT_WIDTH))
                    * Grid.CELL_SIZE + 2 * GRAVEYARD_BREATHING_ROOM;
        }

        private void drawColorfulBox(Graphics2D g, int x, int y, int w, int h) {
            Color previousColor = g.getColor();
            Paint previousPaint = g.getPaint();
            g.setPaint(orangePaint);
            g.fillRect(x, y, w, h);
            g.setColor(Color.ORANGE);
            g.drawRect(x, y, w, h);
            g.setPaint(previousPaint);
            g.setColor(previousColor);
        }

        private void drawGraveyard(Graphics2D g, List<Piece> pieces, Team team) {
            int x = (GRID_X_OFFSET - GRAVEYARD_BOX_LENGTH) / 2;
            List<Piece> graveyard = null;
            if (team.equals(Team.WHITE)) {
                x += GRID_X_OFFSET + Grid.GRID_SIZE;
                graveyard = game.getGrid().getWhiteGraveyard();
            } else {
                graveyard = game.getGrid().getBlackGraveyard();
            }
            int height = getGraveyardHeight(Math.max(1, graveyard.size()));
            int y = (DISPLAY_HEIGHT - height) / 2;
            FontMetrics fontMetrics = g.getFontMetrics();
            int textWidth = fontMetrics.stringWidth(team.toString());
            int textHeight = fontMetrics.getHeight();
            int yextension = textHeight + 8;
            g.setColor(TRANSPARENT_BLACK);
            g.fillRect(x, y - yextension, GRAVEYARD_BOX_LENGTH,
                    height + yextension);
            drawColorfulBox(g, x, y, GRAVEYARD_BOX_LENGTH, height);
            g.setColor(Color.ORANGE);
            g.drawRect(x, y - yextension, GRAVEYARD_BOX_LENGTH,
                    height + yextension);
            g.drawString(team.toString(),
                    (x + GRAVEYARD_BOX_LENGTH / 2) - textWidth / 2,
                    y - textHeight + 8);
            for (int i = 0; i < graveyard.size(); i++) {
                Piece draw = graveyard.get(i);
                int xProjection = (i % GRAVEYARD_UNIT_WIDTH)
                        * Grid.CELL_SIZE + x + GRAVEYARD_BREATHING_ROOM;
                int yProjection = (i / GRAVEYARD_UNIT_WIDTH)
                        * Grid.CELL_SIZE + y + GRAVEYARD_BREATHING_ROOM;
                g.setColor(TRANSPARENT_BLACK);
                g.fillRect(xProjection, yProjection, Grid.CELL_SIZE,
                        Grid.CELL_SIZE);
                g.drawImage(draw.getImage(), xProjection + IMAGE_OFFSET,
                        yProjection + IMAGE_OFFSET, null);
                g.setColor(Color.RED);
                g.drawRect(xProjection, yProjection, Grid.CELL_SIZE,
                        Grid.CELL_SIZE);
                g.drawLine(xProjection, yProjection,
                        xProjection + Grid.CELL_SIZE,
                        yProjection + Grid.CELL_SIZE);
                g.drawLine(xProjection, yProjection + Grid.CELL_SIZE,
                        xProjection + Grid.CELL_SIZE, yProjection);
            }
        }

        private void paintGrid(Graphics2D g, Color c) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.ORANGE);
            FontMetrics fontMetrics = g.getFontMetrics();
            String turnText = "It is currently " + game.getTurn() + "'s turn";
            int nameLength = fontMetrics.stringWidth(Game.NAME);
            int turnLength = fontMetrics.stringWidth(turnText);
            int nameHeight = fontMetrics.getHeight();
            g.setColor(TRANSPARENT_BLACK);
            g.fillRect(GRID_X_OFFSET - 3, GRID_Y_OFFSET - nameHeight - 3 - 8,
                    Grid.GRID_SIZE + 6,
                    Grid.GRID_SIZE + 2 * (nameHeight + 14));
            g.setColor(Color.ORANGE);
            g.drawRect(GRID_X_OFFSET - 3, GRID_Y_OFFSET - 3, Grid.GRID_SIZE + 6,
                    Grid.GRID_SIZE + 6);
            g.drawRect(GRID_X_OFFSET - 3, GRID_Y_OFFSET - nameHeight - 3 - 8,
                    Grid.GRID_SIZE + 6,
                    Grid.GRID_SIZE + 2 * (nameHeight + 14));
            g.drawString(Game.NAME,
                    CENTERED_INNER_PROMOTION_BOX_X - nameLength / 2,
                    GRID_Y_OFFSET - nameHeight + 6);
            g.drawString(turnText,
                    CENTERED_INNER_PROMOTION_BOX_X - turnLength / 2,
                    GRID_Y_OFFSET - nameHeight - 3 - 8 + Grid.GRID_SIZE + 2 * (nameHeight + 14) - 11);
            g.setColor(Color.WHITE);
            g.fillRect(GRID_X_OFFSET, GRID_Y_OFFSET, Grid.GRID_SIZE,
                    Grid.GRID_SIZE);
            Color prev = g.getColor();
            g.setColor(c);
            for (int i = 0; i < Grid.CELL_COUNT; i++) {
                for (int j = 0; j < Grid.CELL_COUNT; j++) {
                    int xProjection = GRID_X_OFFSET + i * Grid.CELL_SIZE;
                    int yProjection = GRID_Y_OFFSET + j * Grid.CELL_SIZE;
                    Coordinate location = new Coordinate(i, j);
                    Color fillColor = (i - j) % 2 != 0 ? Color.LIGHT_GRAY : Color.WHITE;
                    if (state.equals(GameState.PLAY)) {
                        if (firstSelectedPiece != null) {
                            if (location.equals(firstSelectedPiece)) {
                                fillColor = taintColor(fillColor, SELECT_COLOR);
                            }
                        }
                        if (validMoves.contains(location)) {
                            fillColor = game.getGrid().containsPiece(i, j) ? taintColor(
                                    fillColor, ATTACK_COLOR) : taintColor(
                                            fillColor,
                                            SELECT_COLOR);
                        }
                        if (hoveredCoordinate != null && (firstSelectedPiece == null || !firstSelectedPiece.equals(
                                hoveredCoordinate))) {
                            if (location.equals(hoveredCoordinate)) {
                                fillColor = taintColor(fillColor, HOVER_COLOR);
                            }
                        }
                    }
                    g.setColor(fillColor);
                    g.fillRect(xProjection, yProjection, Grid.CELL_SIZE,
                            Grid.CELL_SIZE);
                    g.setColor(c);
                    g.drawRect(xProjection, yProjection, Grid.CELL_SIZE,
                            Grid.CELL_SIZE);
                    Piece pieceAt = game.getGrid().getPiece(i, j);
                    if (pieceAt != null) {
                        g.drawImage(pieceAt.getImage(),
                                xProjection + ChessUI.IMAGE_OFFSET,
                                yProjection + ChessUI.IMAGE_OFFSET, null);
                    }
                }
            }
            g.setColor(prev);
        }

        public void paintInterfaces(Graphics2D g) {
            String endText = getDisplayText();
            if (endText != null) {
                Paint previousPaint = g.getPaint();
                FontMetrics fontMetric = g.getFontMetrics();
                int lengthX = 2 * END_INNERBOX_XOFFSET + fontMetric.stringWidth(
                        endText); // some breathing room 
                int heightY = fontMetric.getHeight() + 2 * END_INNERBOX_YOFFSET;
                int outerBoxLengthX = lengthX + 150;
                int outerBoxHeightY = heightY + 100;
                int centerLocationOuterBoxX = GRID_X_OFFSET + Grid.GRID_SIZE / 2 - outerBoxLengthX / 2;
                int centerLocationOuterBoxY = GRID_Y_OFFSET + Grid.GRID_SIZE / 2 - outerBoxHeightY / 2;
                drawColorfulBox(g,
                        CENTERED_INNER_PROMOTION_BOX_X - DISPLAY_BOX_LENGTH / 2,
                        CENTERED_INNER_PROMOTION_BOX_Y - DISPLAY_BOX_HEIGHT / 2,
                        DISPLAY_BOX_LENGTH, DISPLAY_BOX_HEIGHT);
                if (!state.equals(GameState.PROMOTE)) {
                    int centerLocationInnerBoxX = GRID_X_OFFSET + Grid.GRID_SIZE / 2 - lengthX / 2;
                    int centerLocationInnerBoxY = GRID_Y_OFFSET + Grid.GRID_SIZE / 2 - heightY / 2;
                    g.setColor(TRANSPARENT_BLACK);
                    g.fillRect(centerLocationInnerBoxX,
                            centerLocationInnerBoxY,
                            lengthX, heightY);
                    g.setColor(Color.ORANGE);
                    g.drawRect(centerLocationInnerBoxX,
                            centerLocationInnerBoxY,
                            lengthX, heightY);
                    g.drawString(endText,
                            centerLocationInnerBoxX + END_INNERBOX_XOFFSET,
                            centerLocationInnerBoxY + END_INNERBOX_YOFFSET + fontMetric.getHeight() - 2);
                } else {
                    int starti = promotionTeam.equals(Team.WHITE) ? 0 : 4;
                    for (int i = starti; i < PROMOTION_IMAGE_SET.length / 2 + starti; i++) {
                        int xProjection = (i - starti - 2) * Grid.CELL_SIZE + CENTERED_INNER_PROMOTION_BOX_X;
                        int yProjection = CENTERED_INNER_PROMOTION_BOX_Y - Grid.CELL_SIZE / 2;
                        Color boxColor = TRANSPARENT_BLACK;
                        if (hoveredPromotion == i - starti) {
                            boxColor = SELECT_COLOR.brighter();
                        }
                        g.setColor(boxColor);
                        g.fillRect(xProjection, yProjection, Grid.CELL_SIZE,
                                Grid.CELL_SIZE);
                        g.setColor(Color.ORANGE);
                        g.drawRect(xProjection, yProjection, Grid.CELL_SIZE,
                                Grid.CELL_SIZE);
                        if (hoveredPromotion == i - starti) {
                            g.drawRect(xProjection + 2, yProjection + 2,
                                    Grid.CELL_SIZE - 4,
                                    Grid.CELL_SIZE - 4);
                        }
                        g.drawImage(PROMOTION_IMAGE_SET[i],
                                xProjection + ChessUI.IMAGE_OFFSET,
                                yProjection + ChessUI.IMAGE_OFFSET, null);
                    }
                    int textBoxX = GRID_X_OFFSET + Grid.GRID_SIZE / 2 - lengthX / 2;
                    int textBoxY = GRID_Y_OFFSET + Grid.GRID_SIZE / 2 - heightY / 2 - Grid.CELL_SIZE;
                    g.setColor(TRANSPARENT_BLACK);
                    g.fillRect(textBoxX, textBoxY, lengthX, heightY);
                    g.setColor(Color.ORANGE);
                    g.drawRect(textBoxX, textBoxY, lengthX, heightY);

                    g.drawString(endText, textBoxX + END_INNERBOX_XOFFSET,
                            textBoxY + END_INNERBOX_YOFFSET + fontMetric.getHeight() - 2);
                }
            }
        }

        private void paintBKG(Graphics2D g) {
            g.drawImage(bkgImage, 0, 0, null);
        }

        @Override
        public void paintComponent(Graphics g2) {
            Graphics2D g = (Graphics2D) g2;
            super.paintComponent(g);
            paintBKG(g);
            if (startMS == -1) {
                startMS = System.currentTimeMillis();
            } else if (System.currentTimeMillis() - startMS >= WAIT_THRESHOLD) {
                for (int i = 0; i < orangeM.length; i++) {
                    orangeM[i] += 2;
                    orangeM[i] %= 200;
                }
                startMS = System.currentTimeMillis();
            }
            orangePaint = new GradientPaint(orangeM[0], orangeM[1], Color.RED,
                    orangeM[2], orangeM[3], Color.YELLOW, true);
            paintGrid(g, Color.BLACK);
            paintInterfaces(g);
            drawGraveyard(g, game.getGrid().getBlackGraveyard(), Team.BLACK);
            drawGraveyard(g, game.getGrid().getWhiteGraveyard(), Team.WHITE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backGroundPanel;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JButton load;
    private javax.swing.JButton save;
    private javax.swing.JButton startButton;
    private javax.swing.JButton viewInstructions;
    // End of variables declaration//GEN-END:variables
}

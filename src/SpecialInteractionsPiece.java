/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * these kinds of pieces do special things when they have never moved before
 * more specifically, this is designed for Pawn (move 2 steps forward on first
 * move) King (check conditions/castling) Knight (castling)
 *
 * @author He
 */
public abstract class SpecialInteractionsPiece extends Piece {

    protected SpecialInteractionsPiece(int x, int y, String name, Team team) {
        super(x, y, name, team);
    }

    protected boolean moved = false;

    @Override
    public synchronized void moveToCell(int x, int y) {
        super.moveToCell(x, y);
        moved = true;
    }

    public synchronized boolean hasMoved() {
        return moved;
    }

    public synchronized void setMoved(boolean b) {
        moved = b;
    }
}

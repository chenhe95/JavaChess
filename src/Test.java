/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author He
 */
public class Test {
    public static void main(String[] a) {
    ReplacingHashSet x = new ReplacingHashSet();
    x.add(new Pawn(1, 1, Team.BLACK));
    Object o = x.addReturnReplacedItem(new Knight(1, 1, Team.WHITE));
    System.out.println(x.contains(new Coordinate(1, 1)));
    System.out.println(o);
    }
}

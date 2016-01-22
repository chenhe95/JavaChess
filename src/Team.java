
/**
 *
 * @author He
 */
public enum Team {

    WHITE, BLACK;

    public Team getEnemyTeam() {
        if (this == WHITE) {
            return BLACK;
        }
        return WHITE;
    }
    
    @Override
    public String toString() {
        if(this == WHITE) {
            return "White";
        }
        return "Black";
    }
}

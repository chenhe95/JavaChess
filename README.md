# JavaChess

Hi! Welcome to my game! 

The rules are the exact same as regular chess.
* A pawn, on its first turn, can move two turns. 
** A pawn may only move forwads 1 unit or diagonally forwards 1 unit if there is an enemy piece there. 
** If a pawn is in a position of en-passe, the attacking piece may also move diagonally forwards 1 sqaure into an empty slot.
* A knight can only move to positions in Ls consisting of 3 tiles in 1 way and 1 tile orthogonal.
* A bishop can move diagonally.
* A rook may only move forward and sideways.
* A queen can both diagonally and forward/sideways but not both at the same time. 
* A king may move only to places 1 square away. However, a king may not move into a check. 
** However, under castling conditions (neither king nor castling rook have previously moved and there are no pieces in between the two), castling is allowed, for which a king will move two paces.

* A piece may attack another piece if that piece is within its move range. 

* No units are allowed to move if that move places their own king into a check. 
* A checkmate is defined as when a king is checked and there exists no legal moves to place the king out of check. 
* A pawn that makes it to the end of the board may be promoted to any piece they wish that is not a king or pawn. 
* Games will automatically end in a draw if there are 50 consecutive turns such that no piece was captured or no pawn was moved during that turn.

* To select a unit, you simply click on it and the valid moves will be colored.
* Click on one of the valid moves given and the unit will move to that tile.

* The game lasts until there is a win/loss/draw. 
* Not having any legal moves left to use is an instant-loss.

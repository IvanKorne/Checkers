/* CheckersBoard Assignemnt.
 * Ivan Korneychuk
 * Used to be able to play a basic version of checkers.
 */

public class OOPAssign2{
  public static void main(String[] args){
    CheckersBoard board = new CheckersBoard();
    System.out.println(board.count(CheckersBoard.BLACK));
    board.display();
  }
}

class CheckersBoard{
  private String horiz = "+---+---+---+---+---+---+---+---+"; //Used in Display().
  public static final int BLACK = 1;
  public static final int RED = 2;
  private boolean outOfB(int i){ //Checks for out of bounds.
    if(i < 0 || 7 < i){
      return false;
    }
    return true;
  }  
  private int counter;
  /* 0 = Free space.
   * 1 = BLACK piece.
   * 2 = RED piece.
   * 3 = Space that can never have a piece on it. */
  private int[][] board ={
    {2,3,2,3,2,3,2,3},
    {3,2,3,2,3,2,3,2},
    {2,3,2,3,2,3,2,3},
    {3,0,3,0,3,0,3,0},
    {0,3,0,3,0,3,0,3},
    {3,1,3,1,3,1,3,1},
    {1,3,1,3,1,3,1,3},
    {3,1,3,1,3,1,3,1},
  };  
  public CheckersBoard(){ //Constructor.
    this.board = board;
  }
  public int count(int colour){ 
    if(colour == 1){ //Specified colour is BLACK.
      for(int y = 0; y < board.length; y++){
        for(int x = 0; x < board[y].length; x++){
          if(board[y][x] == 1){ //Checks board for the coloured piece, then adds to counter.
            counter += 1; //counter wil be the # of pieces of the specified colour.
          }
        }
      } 
    }
    if(colour == 2){ //specified colour is RED.
      for(int y = 0; y < board.length; y++){
        for(int x = 0; x < board[y].length; x++){
          if(board[y][x] == 2){
            counter += 1;
          }
        }
      } 
    }return counter;
  }
  public void display(){
    for(int y = 0; y < board.length; y++){
      System.out.println(horiz); //Prints out the "horiz" line to seperate pieces.
      System.out.print("");
      for(int x = 0; x < board[y].length; x++){
        if(board[y][x] == 0 || board[y][x] == 3){ //If place on board is a space/unreachable space.
          System.out.print(" | |"); //Nothing in space.
        }
        if(board[y][x] == 1){ //If place on board is BLACK.
          System.out.print(" |B|"); //B (BLACK = B) in space.
        }
        if(board[y][x] == 2){ //If place on board is RED.
          System.out.print(" |R|"); //R (RED = R) in space.
        }
      }
      System.out.println(""); //Moves to new line.
    }
    System.out.println(horiz); //Prints out the "horiz" at the end.
  }
  
  public boolean move(int y1, int x1, int y2, int x2){
    int col = board[y1][x1]; //Current Colour
    int otherCol = 3 - col; //Other Colour
    if(col == 0 || col == 3){
      System.out.println("You are not moving a piece."); //If not moving a valid piece/
      return false;
    }
    if(y1 == y2 && x1 == x2){ 
      System.out.println("You are not moving."); //If piece isn't moving.
      return false;
    }
    if(outOfB(y1) == false|| outOfB(x1) == false || outOfB(y2) == false || outOfB(x2) == false){ //Checks for out of bounds.
      System.out.println("You are out of bounds.");
      return false;
    }
    else if(board[y2][x2] == col){ //If own piece is in the way.
      System.out.println("Your piece is in the way.");
      return false;
    }
    else if(col == 1 && (x2-x1 == 1 || x2-x1 == -1) && (y2-y1 == -1) && board[y2][x2] == 0){ //Valid Basic move
      board[y2][x2] = 1; //Changes destination to piece.
      board[y1][x1] = 0; //Turns original place into a free square.
      System.out.println("This move is valid.");
      return true;
    }    
    else if(col == 2 && (x2-x1 == 1 || x2-x1 == -1) &&  (y2-y1 == 1) && board[y2][x2] == 0){
      board[y2][x2] = 2;
      board[y1][x1] = 0;  
      System.out.println("This move is valid.");
      return true; 
    }
    else if(col == 1 && board[y1-1][x1+1] == otherCol){ //Capture for the 4 possiblities.
      int vert = -2; //Place it would go to if it captures
      int horz = 2; //Place it would go to if it captures.
      if(outOfB(y1 - 2) == false || outOfB(x1 + 2) == false){ //Checks if piece will be out of bounds.
        return false;
      }
      if(board[y1 - 2][x1 + 2] != 0){ //Checks if space jumping too is empty.
        return false;
      }
      board[y1 - 2][x1 + 2] = col; //Makes the new position the colour.
      board[y1 - 1][x1 + 1] = 0; //Makes the piece the got jumped over free.
      board[y1][x1] = 0; //Makes starting pos free.
      if(y1-2 == y2 && x1+2 == x2){ //If it gets to its destination, it stays there.
        return true;
      }
      else{ //If not, redoes the actions.
        board[y1 - 2][x1 + 2] = 0;
        board[y1 - 1][x1 + 1] = otherCol;
        board[y1][x1] = col;
        return false;
      }
    }
    if(col == 1 && board[y1-1][x1-1] == otherCol){ //Repeats 4 times (Up and left, Up and right, Down and left, Down and right)
      int vert = -2;
      int horz = -2;
      if(outOfB(y1 - 2) == false || outOfB(x1 - 2) == false){
        return false;
      }
      if(board[y1 - 2][x1 - 2] != 0){
        return false;
      }
      board[y1 - 2][x1 - 2] = col;
      board[y1 - 1][x1 - 1] = 0;
      board[y1][x1] = 0;
      if(y1-2 == y2 && x1-2 == x2){
        return true;
      }
      else{
        board[y1 - 2][x1 - 2] = 0;
        board[y1 - 1][x1 - 1] = otherCol;
        board[y1][x1] = col;
        return false;
      }
    }
    if(col == 2 && board[y1+1][x1+1] == otherCol){
      int vert = 2;
      int horz = 2;
      if(outOfB(y1 + 2) == false || outOfB(x1 + 2) == false){
        return false;
      }
      if(board[y1 + 2][x1 + 2] != 0){
        return false;
      }
      board[y1 + 2][x1 + 2] = col;
      board[y1 + 1][x1 + 1] = 0;
      board[y1][x1] = 0;
      if(y1+2 == y2 && x1+2 == x2){
        return true;
      }
      else{
        board[y1 + 2][x1 + 2] = 0;
        board[y1 + 1][x1 + 1] = otherCol;
        board[y1][x1] = col;
        return false;
      }
    }
    if(col == 2 && board[y1+1][x1-1] == otherCol){
      int vert = 2;
      int horz = -2;
      if(outOfB(y1 + 2) == false || outOfB(x1 - 2) == false){
        return false;
      }
      if(board[y1 + 2][x1 - 2] != 0){
        return false;
      }
      board[y1 + 2][x1 - 2] = col;
      board[y1 + 1][x1 - 1] = 0;
      board[y1][x1] = 0;
      if(y1+2 == y2 && x1-2 == x2){
        return true;
      }
      else{
        board[y1 + 2][x1 - 2] = 0;
        board[y1 + 1][x1 - 1] = otherCol;
        board[y1][x1] = col;
        return false;
      }
    }return false; //Return false if it doesn't pass any statements.
  }
}
      


         
      
  
import java.util.*;

/*
 * DropToken plays a game of connect four in the console between two users 
 * alternating turns using the commands PUT <column> to place a move, GET to 
 * get a list of moves placed, BOARD to get the board, and EXIT to exit the 
 * program. A player wins by placing four of their pieces in a row vertically, 
 * horizontally, or diagonally over a 4X4 grid.
 */
public class DropToken {

	public static final int BOARD_LENGTH = 4; 
	private static final String PUT = "PUT";
	private static final String GET = "GET";
	private static final String BOARD = "BOARD";
	private static final String EXIT = "EXIT";
	private static final String ERROR = "ERROR";
	
	private int[][] board;
	private List<Integer> moves;
	private boolean gameOver;
	
	public DropToken() {
		board = new int[BOARD_LENGTH][BOARD_LENGTH];
		moves = new ArrayList<Integer>();
	}
	
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		DropToken dropToken = new DropToken();
        String results = "";
        while (!EXIT.equals(results)) {
        		System.out.print("> ");
        		results = dropToken.playTurn(console.nextLine());
			if(!results.equals(EXIT)) {
				System.out.println(results);
			}
        }
	}
	
	// Plays a turn by processing a String command and returning a String response
	protected String playTurn(String command) {
		String[] commandList = command.split(" "); 
		// Put command must be in format "PUT" space integer, and can not be called
		// when game is over. If invalid input returns "ERROR"
		if(commandList[0].equals(PUT) && commandList.length == 2 && !this.gameOver) { 
			return processMove(commandList);
		} else if(command.equals(GET)) {
			return getMoves();
		} else if(command.equals(BOARD)) {
			return getBoard();
		} else if(command.equals(EXIT)) {
			return "EXIT";
		} else {
			return ERROR;
		}
	}
	
	// Processes put command by checking for valid input and returning
	// "OK", "WIN", "DRAW", or "ERROR"
	private String processMove(String[] commandList) {
		int column;
		try {
			column = Integer.parseInt(commandList[1]);
		} catch (NumberFormatException e) {
			return ERROR;
		}
		if (column > 0 && column <= 4) {
			column = column - 1; // grid is 0 based
			int row = addMoveToBoard(column);
			if(row == -1) {
				return ERROR;
			} else {
				boolean hasWon = checkWinner(row, column);
				if(hasWon) {
					gameOver = true;
					return "WIN";
				} else if(moves.size() == (BOARD_LENGTH * BOARD_LENGTH)) { // all spaces full
					gameOver = true;
					return "DRAW";
				} else {
					return "OK";
				}
			}
		} else {
			return ERROR;
		}
	}
	
	// Returns true if game was won on previous move of (int row, int column)
	private boolean checkWinner(int row, int column) {
		boolean firstDiagonol = false;
		if(row == column) { 
			// move placed on left to right diagonal
			firstDiagonol = firstDiagonalWin();
		} 
		boolean secondDiagonol = false;
		if(row + column == BOARD_LENGTH - 1) {
			// move placed on right to left diagonal
			secondDiagonol = secondDiagonalWin();
		}
		return firstDiagonol || secondDiagonol || 
				horizontalWin(row) || verticalWin(column);
	}
	
	// returns true if vertical win in int column
	private boolean verticalWin(int column) {
		// Gets player # to compare to other spots
		int player = board[0][column];
		for(int i = 1; i < BOARD_LENGTH; i++) {
			if(board[i][column] != player) {
				return false;
			}
		}
		return true;
	}
	
	// returns true if horizontal win in int row
	private boolean horizontalWin(int row) {
		// Gets player # to compare to other spots
		int player = board[row][0]; 
		for(int i = 1; i < BOARD_LENGTH; i++) {
			if(board[row][i] != player) {
				return false;
			}
		}
		return true;
	}
	
	// returns true for left to right diagonal win
	private boolean firstDiagonalWin() {
		int player = board[0][0];
		if(player == 0) {
			return false;
		}
		for(int i = 1; i < BOARD_LENGTH; i++) {
			if(board[i][i] != player) {
				return false;
			}
		}
		return true;
	}
	
	// returns true for right to left diagonal win
	private boolean secondDiagonalWin() {
		int player = board[0][BOARD_LENGTH - 1];
		if (player == 0) {
			return false;
		}
		for(int i = 1; i < BOARD_LENGTH; i++) {
			if(board[i][BOARD_LENGTH - 1 - i] != player) {
				return false;
			}
		}
		return true;
	}

	// If open spot available in column places move and returns row number.
	// Otherwise returns -1.
	private int addMoveToBoard(int column) {
		for(int i = 0; i < BOARD_LENGTH; i++) {  
			if(board[i][column] == 0) { 
				// open spot found, add to moves list
				moves.add(column + 1); 
				// alternate between player 1 and 2 starting with 1
				int player = 2 - moves.size() % 2; 
				board[i][column] = player;
				return i;
			}
		} 	
		return -1;
	}
	
	// returns String of column numbers played by both players
	private String getMoves() {
		String result = "";
		for(int i = 0; i < moves.size(); i++) {
			result += moves.get(i) + "\n";
		}
		return result;
	}

	// returns String version of board
	private String getBoard() {
		String result = "";
		for(int i = BOARD_LENGTH - 1; i >= 0; i--) {
			result += "|";
			for(int j = 0; j < BOARD_LENGTH; j++) {
				result += " " + board[i][j];
			}
			result += "\n";
		}
		result += "+--------\n";
		result += "  1 2 3 4\n";
		return result;
	}
	
}

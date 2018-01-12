import java.util.*;

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
        		results = dropToken.runGame(console.nextLine());
			if(!results.equals(EXIT)) {
				System.out.println(results);
			}
        }
	}

	private String runGame(String command) {
		String[] commandList = command.split(" "); 
		if(commandList[0].equals(PUT) && commandList.length == 2 && !this.gameOver) { 
			int column;
			try {
				column = Integer.parseInt(commandList[1]);
			} catch (NumberFormatException e) {
				return ERROR;
			}
			if (column > 0 && column <= 4) {
				int row = addMoveToBoard(column);
				if(row == -1) {
					return ERROR;
				} else {
					boolean hasWon = checkWinner(row, column);
					if(hasWon) {
						gameOver = true;
						return "WIN";
					} else if(moves.size() == 16) {
						gameOver = true;
						return "DRAW";
					} else {
						return "OK";
					}
				}
			} else {
				return ERROR;
			}
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
	
	private boolean checkWinner(int row, int column) {
		boolean firstDiagonol = false;
		if(row == column) { // 
			firstDiagonol = firstDiagonalWin();
		} 
		boolean secondDiagonol = false;
		if(row + column == BOARD_LENGTH) {
			secondDiagonol = secondDiagonalWin();
		}
		return firstDiagonol || secondDiagonol || 
				horizontalWin(row) || verticalWin(column);
	}

	private boolean verticalWin(int column) {
		int player = board[0][column - 1];
		for(int i = 1; i < BOARD_LENGTH; i++) {
			if(board[i][column - 1] != player) {
				return false;
			}
		}
		return true;
	}

	private boolean horizontalWin(int row) {
		int player = board[row][0];
		for(int i = 1; i < BOARD_LENGTH; i++) {
			if(board[row][i] != player) {
				return false;
			}
		}
		return true;
	}

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

	private int addMoveToBoard(int column) {
		for(int i = 0; i < BOARD_LENGTH; i++) {  
			if(board[i][column - 1] == 0) { // if enters finds open spot
				moves.add(column); // add to moves list
				int player = 2 - moves.size() % 2;
				board[i][column - 1] = player;
				return i;
			}
		} 	
		return -1;
	}

	private String getMoves() {
		// TODO Auto-generated method stub
		String result = "";
		for(int i = 0; i < moves.size(); i++) {
			result += moves.get(i) + "\n";
		}
		return result;
	}


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

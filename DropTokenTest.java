// Tests for DropToken.java
public class DropTokenTest {

	public static void main(String[] args) {
		DropToken dropToken = new DropToken();
		
		System.out.println("".equals(dropToken.playTurn("GET")));
		
		System.out.println("PUT tests:");
		checkPutCommand(dropToken);

		String board = "| 0 0 0 2\n| 0 0 0 1\n| 0 0 0 2\n| 1 2 1 1\n+--------\n  1 2 3 4\n";
		System.out.println(dropToken.playTurn("BOARD"));
		System.out.println("board correct: " + board.equals(dropToken.playTurn("BOARD")));
		
		System.out.println(dropToken.playTurn("GET"));
		String moves = "4\n4\n4\n4\n1\n2\n3\n";
		System.out.println("GET command: " + moves.equals(dropToken.playTurn("GET")));
		
		testDraw(dropToken);
		
		DropToken dropToken2 = new DropToken();
		testVericalWin(dropToken2);
		
		DropToken dropToken3 = new DropToken();
		testHorizontalWin(dropToken3);
		
		DropToken dropToken4 = new DropToken();
		testDiagonalWin1(dropToken4);
		
		DropToken dropToken5 = new DropToken();
		testDiagonalWin2(dropToken5);
	}
	
	private static void testDiagonalWin2(DropToken dropToken) {
		dropToken.playTurn("PUT 1");
		dropToken.playTurn("PUT 1");
		dropToken.playTurn("PUT 2");
		dropToken.playTurn("PUT 3");
		dropToken.playTurn("PUT 2");
		dropToken.playTurn("PUT 3");
		dropToken.playTurn("PUT 3");
		dropToken.playTurn("PUT 4");
		dropToken.playTurn("PUT 1");
		dropToken.playTurn("PUT 2");
		dropToken.playTurn("PUT 4");
		System.out.println("diagonal Win2: " + "WIN".equals(dropToken.playTurn("PUT 1")));
		System.out.println(dropToken.playTurn("PUT 4"));
	}

	private static void testDiagonalWin1(DropToken dropToken) {
		dropToken.playTurn("PUT 1");
		dropToken.playTurn("PUT 1");
		dropToken.playTurn("PUT 2");
		dropToken.playTurn("PUT 3");
		dropToken.playTurn("PUT 2");
		dropToken.playTurn("PUT 3");
		dropToken.playTurn("PUT 3");
		dropToken.playTurn("PUT 4");
		dropToken.playTurn("PUT 4");
		dropToken.playTurn("PUT 4");
		System.out.println("diagonal Win1: " + "WIN".equals(dropToken.playTurn("PUT 4")));
		System.out.println(dropToken.playTurn("PUT 1"));
	}

	private static void testHorizontalWin(DropToken dropToken) {
		dropToken.playTurn("PUT 1");
		dropToken.playTurn("PUT 1");
		dropToken.playTurn("PUT 2");
		dropToken.playTurn("PUT 2");
		dropToken.playTurn("PUT 3");
		dropToken.playTurn("PUT 3");
		System.out.println("Horiz. Win: " + "WIN".equals(dropToken.playTurn("PUT 4")));
	}

	private static void testVericalWin(DropToken dropToken) {
		dropToken.playTurn("PUT 1");
		dropToken.playTurn("PUT 1");
		dropToken.playTurn("PUT 1");
		dropToken.playTurn("PUT 2");
		dropToken.playTurn("PUT 3");
		dropToken.playTurn("PUT 2");
		dropToken.playTurn("PUT 3");
		dropToken.playTurn("PUT 2");
		dropToken.playTurn("PUT 3");
		dropToken.playTurn("PUT 1");
		System.out.println("Vert Win: " + "WIN".equals(dropToken.playTurn("PUT 3")));
		
	}

	private static void testDraw(DropToken dropToken) {
		dropToken.playTurn("PUT 1");
		dropToken.playTurn("PUT 1");
		dropToken.playTurn("PUT 1");
		dropToken.playTurn("PUT 2");
		dropToken.playTurn("PUT 3");
		dropToken.playTurn("PUT 2");
		dropToken.playTurn("PUT 3");
		dropToken.playTurn("PUT 2");
		System.out.println(dropToken.playTurn("BOARD"));
		System.out.println("Draw: " + "DRAW".equals(dropToken.playTurn("PUT 3")));
		System.out.println("Error: " + "ERROR".equals(dropToken.playTurn("PUT 2")));
		System.out.println(dropToken.playTurn("GET"));
		String movesList = "4\n4\n4\n4\n1\n2\n3\n1\n1\n1\n2\n3\n2\n3\n2\n3\n";
		System.out.println("moves: " + movesList.equals(dropToken.playTurn("GET")));	
	}

	private static void checkPutCommand(DropToken dropToken) {
		String response = dropToken.playTurn("PUT 4");
		System.out.println("first: " + "OK".equals(response));
		
		response = dropToken.playTurn("PUT34");
		System.out.println("ERROR".equals(response));
		
		response = dropToken.playTurn("PUT 45");
		System.out.println("ERROR".equals(response));
		
		response = dropToken.playTurn("PUT PUT");
		System.out.println("ERROR".equals(response));
		
		response = dropToken.playTurn("PUT");
		System.out.println("ERROR".equals(response));
		
		response = dropToken.playTurn("PUT .4");
		System.out.println("ERROR".equals(response));
		
		response = dropToken.playTurn("PUT");
		System.out.println("ERROR".equals(response));
		
		response = dropToken.playTurn("PUT 4");
		System.out.println("OK".equals(response));
		
		response = dropToken.playTurn("PUT 4");
		System.out.println("OK".equals(response));
		
		response = dropToken.playTurn("PUT 4");
		System.out.println("OK".equals(response));
		
		// column full
		response = dropToken.playTurn("PUT 4");
		System.out.println("ERROR".equals(response));
		
		response = dropToken.playTurn("PUT 1");
		System.out.println("OK".equals(response));
		
		response = dropToken.playTurn("PUT 2");
		System.out.println("OK".equals(response));
		
		response = dropToken.playTurn("PUT 3");
		System.out.println("OK".equals(response));
		
		// check bounds
		response = dropToken.playTurn("PUT 5");
		System.out.println("ERROR".equals(response));
		response = dropToken.playTurn("PUT 0");
		System.out.println("ERROR".equals(response));
		
	}

}

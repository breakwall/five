package algorithm;

import static org.junit.Assert.assertEquals;
import model.AIPlayer;
import model.Board;
import model.BoardHelper;
import model.Stone;

import org.junit.Test;

public class TestAI {

	@Test
	public void test() {
		System.out.println("test");
		Board board = new Board();
		BoardHelper helper = new BoardHelper(board);
		helper.move(Stone.BLACK, board.get(2, 3));
		helper.move(Stone.BLACK, board.get(3, 6));
		helper.move(Stone.BLACK, board.get(5, 5));
		helper.move(Stone.BLACK, board.get(6, 6));
		helper.move(Stone.BLACK, board.get(7, 6));
		helper.move(Stone.BLACK, board.get(8, 5));
		helper.move(Stone.BLACK, board.get(8, 6));
		helper.move(Stone.BLACK, board.get(7, 8));
		helper.move(Stone.BLACK, board.get(9, 6));
		
		helper.move(Stone.WHITE, board.get(3, 3));
		helper.move(Stone.WHITE, board.get(3, 4));
		helper.move(Stone.WHITE, board.get(3, 5));
		helper.move(Stone.WHITE, board.get(4, 5));
		helper.move(Stone.WHITE, board.get(6, 5));
		helper.move(Stone.WHITE, board.get(5, 6));
		helper.move(Stone.WHITE, board.get(6, 7));
		helper.move(Stone.WHITE, board.get(10, 6));
		
		AIPlayer player = new AIPlayer(Stone.WHITE, helper);
		assertEquals(board.get(8, 7), player.getMove());
	}

}

package algorithm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.Stone;

import org.junit.Test;

public class TestPattern {

	@Test
	public void test5() {
		Pattern pattern = new Pattern("11111", null);
		boolean isMatch = pattern.isMatch(Stone.BLACK, "BBBBB");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "WWWWW");
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.WHITE, "WWWWW");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.WHITE, "BBBBB");
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "WBBBBB");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "BBBBBW");
		assertTrue(isMatch);
	}

	@Test
	public void test4() {
		Pattern pattern = new Pattern("011110", null);

		boolean isMatch = pattern.isMatch(Stone.BLACK, "BBBBB");
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "WWWWW");
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.WHITE, "WWWW");
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "-BBBBW");
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "-BBBB-");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "W-BBBB-");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "W-BBBB-W");
		assertTrue(isMatch);
	}

	@Test
	public void testBlock4() {
		Pattern pattern = new Pattern("011112", null);

		boolean isMatch = pattern.isMatch(Stone.BLACK, "-BBBBW");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "-BBBB");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "BBBB-");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "-BBBBB");
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "BBBBB");
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "W-BBBBW");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "WBBBB-W");
		assertTrue(isMatch);
	}

	@Test
	public void test3() {
		Pattern pattern = new Pattern("011100", null);
		boolean isMatch = pattern.isMatch(Stone.BLACK, "-BBB-");
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "--BBB-");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "-BBB--");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "--BBB--");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "W-BBB--");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "--BBB-W");
		assertTrue(isMatch);
	}

	@Test
	public void testBlock3() {
		Pattern pattern = new Pattern("001112", null);
		boolean isMatch = pattern.isMatch(Stone.BLACK, "--BBBW");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "--BBB");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "BBB--");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "WBBB---");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "---BBBW");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "BBB");
		assertFalse(isMatch);
	}

	@Test
	public void test2() {
		Pattern pattern = new Pattern("001100", null);
		boolean isMatch = pattern.isMatch(Stone.BLACK, "--BB--");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "W--BB--");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "--BB--W");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "--BBB---");
		assertFalse(isMatch);
	}

	@Test
	public void testBlock2() {
		Pattern pattern = new Pattern("211000", null);
		boolean isMatch = pattern.isMatch(Stone.BLACK, "BB---");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "WBB---");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "---BBW");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "---BB");
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "-BB---");
		assertFalse(isMatch);
	}
}

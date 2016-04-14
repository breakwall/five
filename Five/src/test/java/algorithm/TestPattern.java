package algorithm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map.Entry;

import model.Stone;

import org.junit.Test;

public class TestPattern {

	@Test
	public void test5() {
		Pattern pattern = new Pattern("11111", null);
		boolean isMatch = pattern.isMatch(Stone.BLACK, "BBBBB", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "WWWWW", 0);
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.WHITE, "WWWWW", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.WHITE, "BBBBB", 0);
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "WBBBBB", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "BBBBBW", 0);
		assertTrue(isMatch);
	}

	@Test
	public void test4() {
		Pattern pattern = new Pattern("011110", null);

		boolean isMatch = pattern.isMatch(Stone.BLACK, "BBBBB", 0);
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "WWWWW", 0);
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.WHITE, "WWWW", 0);
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "-BBBBW", 0);
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "-BBBB-", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "W-BBBB-", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "W-BBBB-W", 0);
		assertTrue(isMatch);
	}

	@Test
	public void testBlock4() {
		Pattern pattern = new Pattern("011112", null);

		boolean isMatch = pattern.isMatch(Stone.BLACK, "-BBBBW", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "-BBBB", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "BBBB-", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "-BBBBB", 0);
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "BBBBB", 0);
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "W-BBBBW", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "WBBBB-W", 0);
		assertTrue(isMatch);
	}

	@Test
	public void test3() {
		Pattern pattern = new Pattern("011100", null);
		boolean isMatch = pattern.isMatch(Stone.BLACK, "-BBB-", 0);
		assertFalse(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "--BBB-", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "-BBB--", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "--BBB--", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "W-BBB--", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "--BBB-W", 0);
		assertTrue(isMatch);
	}

	@Test
	public void testBlock3() {
		Pattern pattern = new Pattern("001112", null);
		boolean isMatch = pattern.isMatch(Stone.BLACK, "--BBBW", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "--BBB", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "BBB--", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "WBBB---", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "---BBBW", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "BBB", 0);
		assertFalse(isMatch);
	}

	@Test
	public void test2() {
		Pattern pattern = new Pattern("001100", null);
		boolean isMatch = pattern.isMatch(Stone.BLACK, "--BB--", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "W--BB--", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "--BB--W", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "--BBB---", 0);
		assertFalse(isMatch);
	}

	@Test
	public void testBlock2() {
		Pattern pattern = new Pattern("211000", null);
		boolean isMatch = pattern.isMatch(Stone.BLACK, "BB---", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "WBB---", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "---BBW", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "---BB", 0);
		assertTrue(isMatch);

		isMatch = pattern.isMatch(Stone.BLACK, "-BB---", 0);
		assertFalse(isMatch);
	}

	@Test
	public void testParsePattern() {
		for(Entry<Type, Pattern[]> e : PatternMap.getPatterns().entrySet()) {
			for(Pattern pattern : e.getValue()) {
				switch (pattern.toString()) {
				case "11111":
					assertEquals(0, pattern.getBlockNumber());
					assertEquals(5, pattern.getConsecutiveCount());
					assertEquals(5, pattern.getCount());
					break;
				case "011110":
					assertEquals(0, pattern.getBlockNumber());
					assertEquals(4, pattern.getConsecutiveCount());
					assertEquals(4, pattern.getCount());
					break;
				case "011112":
					assertEquals(1, pattern.getBlockNumber());
					assertEquals(4, pattern.getConsecutiveCount());
					assertEquals(4, pattern.getCount());
					break;
				case "10111":
					assertEquals(0, pattern.getBlockNumber());
					assertEquals(3, pattern.getConsecutiveCount());
					assertEquals(4, pattern.getCount());
					break;
				case "11011":
					assertEquals(0, pattern.getBlockNumber());
					assertEquals(2, pattern.getConsecutiveCount());
					assertEquals(4, pattern.getCount());
					break;
				case "011100":
					assertEquals(0, pattern.getBlockNumber());
					assertEquals(3, pattern.getConsecutiveCount());
					assertEquals(3, pattern.getCount());
					break;
				case "010110":
					assertEquals(0, pattern.getBlockNumber());
					assertEquals(2, pattern.getConsecutiveCount());
					assertEquals(3, pattern.getCount());
					break;
				case "001112":
					assertEquals(1, pattern.getBlockNumber());
					assertEquals(3, pattern.getConsecutiveCount());
					assertEquals(3, pattern.getCount());
					break;
				case "010112":
					assertEquals(1, pattern.getBlockNumber());
					assertEquals(2, pattern.getConsecutiveCount());
					assertEquals(3, pattern.getCount());
					break;
				case "011012":
					assertEquals(1, pattern.getBlockNumber());
					assertEquals(2, pattern.getConsecutiveCount());
					assertEquals(3, pattern.getCount());
					break;
				case "11001":
					assertEquals(0, pattern.getBlockNumber());
					assertEquals(2, pattern.getConsecutiveCount());
					assertEquals(3, pattern.getCount());
					break;
				case "10101":
					assertEquals(0, pattern.getBlockNumber());
					assertEquals(1, pattern.getConsecutiveCount());
					assertEquals(3, pattern.getCount());
					break;
				case "2011102":
					assertEquals(2, pattern.getBlockNumber());
					assertEquals(3, pattern.getConsecutiveCount());
					assertEquals(3, pattern.getCount());
					break;
				case "001100":
					assertEquals(0, pattern.getBlockNumber());
					assertEquals(2, pattern.getConsecutiveCount());
					assertEquals(2, pattern.getCount());
					break;
				case "001010":
					assertEquals(0, pattern.getBlockNumber());
					assertEquals(1, pattern.getConsecutiveCount());
					assertEquals(2, pattern.getCount());
					break;
				case "010010":
					assertEquals(0, pattern.getBlockNumber());
					assertEquals(1, pattern.getConsecutiveCount());
					assertEquals(2, pattern.getCount());
					break;
				case "000112":
					assertEquals(1, pattern.getBlockNumber());
					assertEquals(2, pattern.getConsecutiveCount());
					assertEquals(2, pattern.getCount());
					break;
				case "210100":
					assertEquals(1, pattern.getBlockNumber());
					assertEquals(1, pattern.getConsecutiveCount());
					assertEquals(2, pattern.getCount());
					break;
				case "210010":
					assertEquals(1, pattern.getBlockNumber());
					assertEquals(1, pattern.getConsecutiveCount());
					assertEquals(2, pattern.getCount());
					break;
				case "10001":
					assertEquals(0, pattern.getBlockNumber());
					assertEquals(1, pattern.getConsecutiveCount());
					assertEquals(2, pattern.getCount());
					break;
				default:
					assertFalse(false);
					break;
				}
			}
		}
	}
 }

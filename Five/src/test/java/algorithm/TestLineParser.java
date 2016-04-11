package algorithm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.Stone;

import org.junit.Test;

public class TestLineParser {

	@Test
	public void testParse() {
		boolean b = LineParser.parse("-------W--------", Stone.WHITE);
		assertFalse(b);

		b = LineParser.parse("---W---", Stone.WHITE);
		assertFalse(b);

		b = LineParser.parse("-------", Stone.WHITE);
		assertFalse(b);

		b = LineParser.parse("--W----", Stone.WHITE);
		assertFalse(b);

		b = LineParser.parse("--W-", Stone.WHITE);
		assertFalse(b);

		b = LineParser.parse("-B--WWB---", Stone.WHITE);
		assertFalse(b);

		b = LineParser.parse("-WBWWBW--", Stone.WHITE);
		assertFalse(b);

		b = LineParser.parse("-------WW-------", Stone.WHITE);
		assertTrue(b);
		assertEquals(4, LineParser.beginIndex);
		assertEquals(2, LineParser.focusCount);
		assertEquals(2, LineParser.maxConsCount);

		b = LineParser.parse("-------WW-", Stone.WHITE);
		assertTrue(b);
		assertEquals(4, LineParser.beginIndex);
		assertEquals(2, LineParser.focusCount);
		assertEquals(2, LineParser.maxConsCount);

		b = LineParser.parse("-B---WWB---", Stone.WHITE);
		assertTrue(b);
		assertEquals(1, LineParser.beginIndex);
		assertEquals(2, LineParser.focusCount);
		assertEquals(2, LineParser.maxConsCount);

		b = LineParser.parse("-WBWWBW----", Stone.WHITE);
		assertTrue(b);
		assertEquals(0, LineParser.beginIndex);
		assertEquals(4, LineParser.focusCount);
		assertEquals(2, LineParser.maxConsCount);

		b = LineParser.parse("-------WB--B---", Stone.BLACK);
		assertTrue(b);
		assertEquals(7, LineParser.beginIndex);
		assertEquals(2, LineParser.focusCount);
		assertEquals(1, LineParser.maxConsCount);
	}

}

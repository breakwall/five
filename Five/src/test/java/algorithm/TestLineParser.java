package algorithm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import model.Stone;

import org.junit.Test;

import algorithm.LineParser.ParseInfo;

public class TestLineParser {

	@Test
	public void testParse() {
		ParseInfo parseInfo = LineParser.parse("-------W--------", Stone.WHITE);
		assertNull(parseInfo);

		parseInfo = LineParser.parse("---W---", Stone.WHITE);
		assertNull(parseInfo);

		parseInfo = LineParser.parse("-------", Stone.WHITE);
		assertNull(parseInfo);

		parseInfo = LineParser.parse("--W----", Stone.WHITE);
		assertNull(parseInfo);

		parseInfo = LineParser.parse("--W-", Stone.WHITE);
		assertNull(parseInfo);

		parseInfo = LineParser.parse("-B--WWB---", Stone.WHITE);
		assertNull(parseInfo);

		parseInfo = LineParser.parse("-WBWWBW--", Stone.WHITE);
		assertNull(parseInfo);

		parseInfo = LineParser.parse("-------WW-------", Stone.WHITE);
		assertNotNull(parseInfo);
		assertEquals(4, parseInfo.beginIndex);
		assertEquals(2, parseInfo.focusCount);
		assertEquals(2, parseInfo.maxConsCount);

		parseInfo = LineParser.parse("-------WW-", Stone.WHITE);
		assertNotNull(parseInfo);
		assertEquals(4, parseInfo.beginIndex);
		assertEquals(2, parseInfo.focusCount);
		assertEquals(2, parseInfo.maxConsCount);

		parseInfo = LineParser.parse("-B---WWB---", Stone.WHITE);
		assertNotNull(parseInfo);
		assertEquals(1, parseInfo.beginIndex);
		assertEquals(2, parseInfo.focusCount);
		assertEquals(2, parseInfo.maxConsCount);

		parseInfo = LineParser.parse("-WBWWBW----", Stone.WHITE);
		assertNotNull(parseInfo);
		assertEquals(0, parseInfo.beginIndex);
		assertEquals(4, parseInfo.focusCount);
		assertEquals(2, parseInfo.maxConsCount);

		parseInfo = LineParser.parse("-------WB--B---", Stone.BLACK);
		assertNotNull(parseInfo);
		assertEquals(7, parseInfo.beginIndex);
		assertEquals(2, parseInfo.focusCount);
		assertEquals(1, parseInfo.maxConsCount);
	}

}

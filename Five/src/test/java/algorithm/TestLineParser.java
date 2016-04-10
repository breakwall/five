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

//		b = LineParser.parse("-------WW-------", Stone.WHITE);
//		assertTrue(b);
//		assertEquals("---WW---", LineParser.getSubString());
//
//		b = LineParser.parse("-------WW-", Stone.WHITE);
//		assertTrue(b);
//		assertEquals("---WW-", LineParser.getSubString());
//
//		b = LineParser.parse("---W---", Stone.WHITE);
//		assertFalse(b);
//
//		b = LineParser.parse("-------", Stone.WHITE);
//		assertFalse(b);
//
//		b = LineParser.parse("--W----", Stone.WHITE);
//		assertFalse(b);
//
//		b = LineParser.parse("--W-", Stone.WHITE);
//		assertFalse(b);
//
//		b = LineParser.parse("-B--WWB---", Stone.WHITE);
//		assertFalse(b);
//
//		b = LineParser.parse("-B---WWB---", Stone.WHITE);
//		assertTrue(b);
//		assertEquals("B---WWB", LineParser.getSubString());
//
//		b = LineParser.parse("-WBWWBW--", Stone.WHITE);
//		assertFalse(b);
//
//		b = LineParser.parse("-WBWWBW----", Stone.WHITE);
//		assertTrue(b);
//		assertEquals("-WBWWBW---", LineParser.getSubString());
		
		b = LineParser.parse("-------WB--B---", Stone.BLACK);
		assertTrue(b);
		System.out.println(LineParser.maxConsCount);
	}

}

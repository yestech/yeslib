package org.yestech.lib.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class PassPhraseUnitTest {

	/*
	 * Class under test for String getNext()
	 */
	@Test
	public void testGetNext() {
		String s = PassPhrase.getNext();
		System.out.println(s);
		assertNotNull(s);
		assertEquals(PassPhrase.MIN_LENGTH, s.length());
	}

	/*
	 * Class under test for String getNext(int)
	 */
	@Test
	public void testGetNextint() {
		final int[] lengths = { 1, 5, 20, 200 };
		for (int i = 0; i < lengths.length; i++) {
			int j = lengths[i];
			String s = PassPhrase.getNext(j);
			System.out.println(s);
			assertNotNull(s);
			assertEquals(j, s.length());
		}
	}

	@Test
	public void testGetNextZero() {
		try {
			PassPhrase.getNext(0);
			fail("Did not trap length zero request");
		} catch (IllegalArgumentException ex) {
			System.out.println("OK: Caught expected exception");

		}
	}

}

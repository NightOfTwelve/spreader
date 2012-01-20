/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.spreader.test.assertion;

import junit.framework.Assert;

/**
 * @author gavin 
 * Created on 2010-8-31
 */
public class StringAssert extends Assert{

	public static void assertNotEmpty(String s) {
		Assert.assertTrue(s != null && !s.isEmpty());
	}
}


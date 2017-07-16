package com.valygard.test;

import org.junit.Assert;
import org.junit.Test;

import com.valygard.utils.StringUtils;

public class StringUtilsTest {

	@Test
	public void testReverse() {
		Assert.assertEquals("abcdef", StringUtils.reverse("fedcba"));
		Assert.assertEquals("aaaa", StringUtils.reverse("aaaa"));
		Assert.assertEquals("", StringUtils.reverse(""));
	}
}

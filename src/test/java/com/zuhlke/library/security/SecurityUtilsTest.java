package com.zuhlke.library.security;

import org.junit.Assert;
import org.junit.Test;

public class SecurityUtilsTest {

	public SecurityUtils securityUtils = new SecurityUtils();
	
	@Test
	public void shouldHashPassword() throws Exception {
		String hash = securityUtils.hash("password", "admin@zuhlke.com");
		Assert.assertEquals("7Le61jX4m+a+1TNd/ltaD7ee+jyqsgYV0Mfd0EYS18RqxBX+9YfVbkXZRd9/VdZAJ7l/SDcQn03NicBe5vnj8A==", hash);
	}
	
}

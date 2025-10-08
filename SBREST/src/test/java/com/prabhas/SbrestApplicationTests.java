package com.prabhas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class SbrestApplicationTests {
	
	private Calculator c=new Calculator();
	@Test
	void contextLoads() {
	
	}
	@Test
	void testSum() {
		int expected=17;
		int actual=c.doSum(12,2);
		assertEquals(expected, actual);
		assertThat(actual).isEqualTo(expected);
		//assertThat(actual).isGreaterThan(expected);
		//assertThat(actual).isLessThan(actual);
	}

}

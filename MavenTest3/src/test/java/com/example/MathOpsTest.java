package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MathOpsTest {
	
	@Test
	public void testAdd() {
		int i = 2;
		int j = 3;
		MathOperation obj = new MathOperation();
		assertEquals(5, obj.add(i, j)); 
	}
	
	@Test
	public void testMultiply() {
		int i = 2;
		int j = 3;
		MathOperation obj = new MathOperation();
		assertEquals(6, obj.multiply(i, j)); 
	}
	
	@Test
	public void testValidateName() {
		String custname = "James";
		MathOperation obj = new MathOperation();
		assertTrue(obj.validateName(custname)); 
	}

	@Test
	public void testNameNotNull() {
		String name = "Sonal";
		assertNotNull(name);
	}

	@Test
	public void testValidation() {
		boolean status = true;
		assertTrue(status);
	}
}

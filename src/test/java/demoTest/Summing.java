package demoTest;

import org.testng.annotations.Test;

import m1.Sum;

public class Summing {
	
	@Test
	public void mSumming() {
		Sum s=new Sum();
		int result=s.add(1, 2);
		System.out.println("Sum is " +result);
		
	}

}

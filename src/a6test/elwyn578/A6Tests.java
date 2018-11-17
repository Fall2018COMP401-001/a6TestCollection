package a6test.elwyn578;

import static org.junit.Assert.*;

import a6.*;

import org.junit.Test;

public class A6Tests {

	@Test
	public void regionNegativeTest() {
		try {
			Region testRegion = new RegionImpl(-1, -2, -3, -4);
		}catch(IllegalArgumentException e) {
			fail("Negative numbers are allowed as values in Region objects");
		}	
	}

}

package a6test.rajg;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.Region;
import a6.RegionImpl;

public class A6Tests {

	@Test
	public void regionConstructorTest() {
		try {
			Region region = new RegionImpl(5, 0, 0, 1);
			fail("The left should not be greater than the right");
		} catch (IllegalArgumentException e) {
			
		}
		
		try {
			Region region = new RegionImpl(3, 4, 4, 3);
			fail("The top cannot be larger than the bottom");
		} catch (IllegalArgumentException e) {
			
		}
	}

}

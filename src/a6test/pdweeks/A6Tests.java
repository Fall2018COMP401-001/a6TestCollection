package a6test.pdweeks;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import a6.*;

class A6Tests {
	
	public static String[] getTestNames() {
		String[] testNames = new String[1];
		
		testNames[0]="testRegionImplConstructor";
		
		return testNames;
		
	}

	@Test
	void testRegionImplConstructor() {
		
		try {
			Region leftGreaterThanRight = new RegionImpl(2,2,1,3);
			fail("Left greater than right");
		} catch (IllegalArgumentException e) {
		}
	    try {
			Region topGreaterThanBottom = new RegionImpl(1,3,2,2);
			fail("Top greater than bottom");
		} catch (IllegalArgumentException e) {
		}
	    
		RegionImpl r = new RegionImpl(6,9,9,10);
	    assertEquals(6, r.getLeft());
	    assertEquals(9, r.getTop());
	    assertEquals(9, r.getRight());
	    assertEquals(10, r.getBottom());
	     
	}

}

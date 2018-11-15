package a6test.Gabeo;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.NoIntersectionException;
import a6.Region;
import a6.RegionImpl;

public class a6Tests {
 // My Intersection test.
	@Test
	public void testMyIntersect() {
		Region one = new RegionImpl(1, 1, 4, 4);
		Region two = new RegionImpl(3, 3, 5, 5);
		Region three = new RegionImpl(5, 5, 10, 10);
		
		
		try {
			Region intersect = one.intersect(two);
			assertEquals(intersect.getLeft(), 3);
			assertEquals(intersect.getTop(), 3);
			assertEquals(intersect.getRight(), 4);
			assertEquals(intersect.getBottom(), 4);
		}
		catch(Exception e) { 
			fail("Error");
		}
		
		try {
			Region intersectB = one.intersect(three);
			fail("No Intersection Exception");
		}
		catch(NoIntersectionException e) {
		}
	}
} 
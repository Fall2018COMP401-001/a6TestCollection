/**
 * 
 */
/**
 * @author caseyhansen
 *
 */
package a6test.caseyhan;

import org.junit.Test;
import static org.junit.Assert.*;
import a6.*;

public class A6Tests {
	Region region1 = new RegionImpl(0, 0, 3, 3);
	Region region2 = new RegionImpl(2, 2, 4, 4);
	
	Region region3 = new RegionImpl(0, 0, 1, 1);
	Region region4 = new RegionImpl(4, 4, 6, 6);
	
	@Test
	public void testIntersect() {
		try {
			Region overlapRegion = region1.intersect(region2);
		} catch (NoIntersectionException e) {
			fail("False NoIntersectionException Thrown");
			e.printStackTrace();
		}
		
		try {
			Region overlapRegion = region1.intersect(region2);
			assertEquals(overlapRegion.getTop(), 2);
			assertEquals(overlapRegion.getLeft(), 2);
			assertEquals(overlapRegion.getBottom(), 3);
			assertEquals(overlapRegion.getRight(), 3);
		} catch(Exception e) {
			fail("Incorrect Intersection Coordinates");
		}
	}
	
	@Test
	public void testUnion() {
		try {
			Region union = region3.union(region4);
		} catch (IllegalArgumentException e) {
			fail("False IllegalArgumentException Thrown");
			e.printStackTrace();
		}
		
		try {
			Region union = region3.union(region4);
			assertEquals(union.getTop(), 0);
			assertEquals(union.getLeft(), 0);
			assertEquals(union.getBottom(), 6);
			assertEquals(union.getRight(), 6);
		} catch(Exception e) {
			fail("Incorrect Union Coordinates");
		}
	}
}

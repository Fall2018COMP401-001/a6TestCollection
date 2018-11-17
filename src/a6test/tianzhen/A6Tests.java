package a6test.tianzhen;

import static org.junit.Assert.*;

import org.junit.Test;
import a6.*;

public class A6Tests {

	@Test
	public void testRegionImplConstructor() {
		try {
			Region testRegion = new RegionImpl(88, 66, 77, 66);
			fail("left is greater than right");
		} catch (IllegalArgumentException e) {
		}
		
		try {
			Region testRegion = new RegionImpl(55, 77, 77, 66);
			fail("top is greater than right");
		} catch (IllegalArgumentException e) {
		}
		
		try {
			Region testRegion = new RegionImpl(88, 77, 77, 66);
			fail("left is greater than right and top is greater than right");
		} catch (IllegalArgumentException e) {
		}
		
	}
	
	@Test
	public void testRegionImplGetters() {
		Region testRegion = new RegionImpl(4, 3, 5, 6);
		assertEquals(testRegion.getLeft(), 4);
		assertEquals(testRegion.getTop(), 3);
		assertEquals(testRegion.getRight(), 5);
		assertEquals(testRegion.getBottom(), 6);
	}
	
	@Test
	public void testRegionImplIntersectMethod() throws NoIntersectionException {
		Region testRegion = new RegionImpl(1, 2, 5, 6);
		Region testRegion1 = new RegionImpl(3, 5, 6, 8);
		Region testRegion2 = new RegionImpl(9, 8, 10, 11);
		Region intersect = testRegion.intersect(testRegion1);
		
		assertEquals(intersect.getLeft(), 3);
		assertEquals(intersect.getTop(), 5);
		assertEquals(intersect.getRight(), 5);
		assertEquals(intersect.getBottom(), 6);
		
		try {
			testRegion.intersect(testRegion2);
			fail("NoIntersection");
		} catch (NoIntersectionException e) {
		}
		try {
			testRegion1.intersect(testRegion2);
			fail("NoIntersection");
		} catch (NoIntersectionException e) {
		}
	}
	
	@Test
	public void testRegionImplUnionMethod() {
		Region testRegion = new RegionImpl(1, 2, 5, 6);
		Region testRegion1 = new RegionImpl(3, 5, 6, 8);
		Region testRegion2 = new RegionImpl(9, 8, 10, 11);
		Region unionRegion1 = testRegion.union(testRegion1);
		Region unionRegion2 = testRegion.union(testRegion2);
		Region unionRegion3 = testRegion1.union(testRegion2);
		
		assertEquals(unionRegion1.getLeft(), 1);
		assertEquals(unionRegion1.getTop(), 2);
		assertEquals(unionRegion1.getRight(), 6);
		assertEquals(unionRegion1.getBottom(), 8);
		
		assertEquals(unionRegion2.getLeft(), 1);
		assertEquals(unionRegion2.getTop(), 2);
		assertEquals(unionRegion2.getRight(), 10);
		assertEquals(unionRegion2.getBottom(), 11);
		
		assertEquals(unionRegion3.getLeft(), 3);
		assertEquals(unionRegion3.getTop(), 5);
		assertEquals(unionRegion3.getRight(), 10);
		assertEquals(unionRegion3.getBottom(), 11);
	}
}

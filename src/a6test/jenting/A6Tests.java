package a6test.jenting;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;

import a6.*;

import org.junit.Test;

public class A6Tests {

	@Test
	public void testIntersect() throws NoIntersectionException {
		Region region0022 = new RegionImpl(0,0,2,2);
		Region region1122 = new RegionImpl(1,1,2,2);
		Region region1133 = new RegionImpl(1,1,3,3);

		Region intersection = region1133.intersect(region0022);

		assertEquals(1, intersection.getLeft());
		assertEquals(1, intersection.getTop());
		assertEquals(2, intersection.getRight());
		assertEquals(2, intersection.getBottom());
	}

	@Test
	public void testIntersectTouchingRegions() throws NoIntersectionException {
		Region region0022 = new RegionImpl(0,0,2,2);
		Region region2042 = new RegionImpl(2,0,4,2);

		Region intersection = region0022.intersect(region2042);

		assertEquals(2, intersection.getLeft());
		assertEquals(0, intersection.getTop());
		assertEquals(2, intersection.getRight());
		assertEquals(2, intersection.getBottom());
	}

	@Test
	public void testRegionConstructor(){
		try {
			Region regionLeft = new RegionImpl(1,2,0,1);
			fail("right is less than left");
		} catch (IllegalArgumentException e){
		}
		try {
			Region regionTop = new RegionImpl(1,1,1,0);
			fail("bot is less than top");
		} catch (IllegalArgumentException e){
		}
	}
	
	@Test
	public void testUnion() {
		Region region0022 = new RegionImpl(0,0,2,2);
		Region region3344 = new RegionImpl(3,3,4,4);
		
		Region union = region0022.union(region3344);
		
		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(4, union.getRight());
		assertEquals(4, union.getBottom());
	}
	
	@Test
	public void testVoidUnion() {
		Region region0022 = new RegionImpl(0,0,2,2);
		
		Region union = region0022.union(null);
		
		assertEquals(region0022, union);
	}
}
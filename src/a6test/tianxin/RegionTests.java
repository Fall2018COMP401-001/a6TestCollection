package a6test.tianxin;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

class RegionTests {
	Region a = new RegionImpl(5,7,5,7);
	Region b = new RegionImpl(0,7,4,7);
	Region c = new RegionImpl(5,4,7,0);
	Region d = new RegionImpl(1,6,6,5);
	
	@Test
	public void regionIMplConstructorTests() {

		try {		
			Region illegalWidthRegion = new RegionImpl(6,7,5,7);
			fail("Region has illegal geometry.");
		} catch (IllegalArgumentException e) {
		}
		try {		
			Region illegalHeightRegion = new RegionImpl(5,7,5,2);
			fail("Region has illegal geometry.");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void RegionIntersectionTests() {
		try {
			Region null_intersection = a.intersect(null);
			fail("Region cannot be null");
		} catch (NoIntersectionException e) {
		}

		try {
			Region null_intersection = a.intersect(b);
			fail("Regions don't intersect");
		} catch (NoIntersectionException e) {
		}
		
		try {
			Region null_intersection = a.intersect(c);
			fail("Regions don't intersect");
		} catch (NoIntersectionException e) {
		}

		try {
			Region intersection = c.intersect(d);

			assertEquals(5, intersection.getLeft());
			assertEquals(4, intersection.getTop());
			assertEquals(6, intersection.getRight());
			assertEquals(3, intersection.getBottom());

			intersection = d.intersect(c);

			assertEquals(5, intersection.getLeft());
			assertEquals(4, intersection.getTop());
			assertEquals(6, intersection.getRight());
			assertEquals(3, intersection.getBottom());
		} catch (NoIntersectionException e) {
			fail("Should not have thrown NoIntersectionException");
		}
	}
}

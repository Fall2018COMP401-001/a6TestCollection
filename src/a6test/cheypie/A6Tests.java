package a6test.cheypie;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

public class A6Tests {

	@Test
	public void basicRegionIntersectiontest() {
		Region a = new RegionImpl(0, 0, 4, 4);
		Region b = new RegionImpl(3, 3, 6, 6);
		Region c = new RegionImpl(7,7,10,10);

		try {
			Region null_intersection = a.intersect(null);
			fail("Region cannot be null");
		} catch (NoIntersectionException e) {
		}

		try {
			Region null_intersection = a.intersect(c);
			fail("Regions don't intersect");
		} catch (NoIntersectionException e) {
		}

		try {
			Region intersection = a.intersect(b);

			assertEquals(3, intersection.getLeft());
			assertEquals(3, intersection.getTop());
			assertEquals(4, intersection.getRight());
			assertEquals(4, intersection.getBottom());

			intersection = b.intersect(a);

			assertEquals(3, intersection.getLeft());
			assertEquals(3, intersection.getTop());
			assertEquals(4, intersection.getRight());
			assertEquals(4, intersection.getBottom());
		} catch (NoIntersectionException e) {
			fail("Should not have thrown NoIntersectionException");
		}
	}

}

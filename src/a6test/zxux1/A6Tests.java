package a6test.driftclouds;

import a6.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class A6Tests {

	@Test
	public void testRegion() {
		Region region11_55 = new RegionImpl(1, 1, 5, 5);
		assertEquals(1, region11_55.getTop());
		assertEquals(5, region11_55.getBottom());
		assertEquals(1, region11_55.getLeft());
		assertEquals(5, region11_55.getRight());

		Region region00_33 = new RegionImpl(0, 0, 3, 3);
		assertEquals(0, region00_33.getTop());
		assertEquals(3, region00_33.getBottom());
		assertEquals(0, region00_33.getLeft());
		assertEquals(3, region00_33.getRight());

		Region region22_77 = new RegionImpl(2, 2, 7, 7);
		assertEquals(2, region22_77.getTop());
		assertEquals(7, region22_77.getBottom());
		assertEquals(2, region22_77.getLeft());
		assertEquals(7, region22_77.getRight());
	}

	@Test
	public void testIntersect() throws NoIntersectionException {
		Region region11_55 = new RegionImpl(1, 1, 5, 5);
		Region region00_33 = new RegionImpl(0, 0, 3, 3);
		Region region22_77 = new RegionImpl(2, 2, 7, 7);

		Region r1 = region11_55.intersect(region00_33);
		assertEquals(1, r1.getTop());
		assertEquals(3, r1.getBottom());
		assertEquals(1, r1.getLeft());
		assertEquals(3, r1.getRight());

		Region r2 = region22_77.intersect(region00_33);
		assertEquals(2, r2.getTop());
		assertEquals(3, r2.getBottom());
		assertEquals(2, r2.getLeft());
		assertEquals(3, r2.getRight());

		Region a = new RegionImpl(0, 0, 4, 4);
		Region b = new RegionImpl(2, 2, 6, 6);
		Region c = a.intersect(b);

		assertEquals(b.getTop(), c.getTop());
		assertEquals(a.getBottom(), c.getBottom());
		assertEquals(b.getLeft(), c.getLeft());
		assertEquals(a.getRight(), c.getRight());
	}
}
package a6tests;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

class A6Tests {

	Region r1 = new RegionImpl(2, 1, 3, 3);
	Region r2 = new RegionImpl(1, 1, 2, 2);
	Region r3 = new RegionImpl(2, 1, 2, 2);
	Region r4 = new RegionImpl(1, 1, 3, 3);
	Region r5 = new RegionImpl(3, 0, 3, 1);

	@Test
	void testRegionImplConstructor() {
		try {
			Region invalidRegion = new RegionImpl(3, 1, 1, 3);
			fail("Left cannot be greater than right.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Region invalidRegion = new RegionImpl(1, 3, 3, 1);
			fail("Top cannot be greater than bottom.");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	void testRegionImplMethods() throws NoIntersectionException {
		assertEquals(2, r1.getLeft());
		assertEquals(1, r1.getTop());
		assertEquals(3, r1.getRight());
		assertEquals(3, r1.getBottom());
		assertEquals(r2.intersect(r1), r3);
		assertEquals(r2.union(r1), r4);
	}

	@Test
	public void nullRegionUnionTest() {
		Region a = new RegionImpl(1, 1, 6, 5);
		Region union = a.union(null);

		assertEquals(a.getLeft(), union.getLeft());
		assertEquals(a.getTop(), union.getTop());
		assertEquals(a.getRight(), union.getRight());
		assertEquals(a.getBottom(), union.getBottom());
		assertEquals(a, union);
	}
}

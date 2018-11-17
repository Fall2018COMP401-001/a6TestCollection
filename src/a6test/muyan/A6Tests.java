package a6test.muyan;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

public class A6Tests {

	@Test
	public void regionUnionTest() {
		Region region1 = new RegionImpl(0, 0, 5, 5);
		Region region2 = new RegionImpl(1, 1, 4, 4);

		Region union = region1.union(region2);

		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(5, union.getRight());
		assertEquals(5, union.getBottom());

		union = region2.union(region1);

		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(5, union.getRight());
		assertEquals(5, union.getBottom());
	}

	@Test
	public void regionIntersectionTest() throws NoIntersectionException {
		Region region1 = new RegionImpl(2, 2, 4, 4);
		Region region2 = new RegionImpl(3, 3, 5, 5);

		Region intersect = region1.intersect(region2);

		assertEquals(3, intersect.getLeft());
		assertEquals(3, intersect.getTop());
		assertEquals(4, intersect.getRight());
		assertEquals(4, intersect.getBottom());

		intersect = region2.intersect(region1);

		assertEquals(3, intersect.getLeft());
		assertEquals(3, intersect.getTop());
		assertEquals(4, intersect.getRight());
		assertEquals(4, intersect.getBottom());
	}

	@Test
	public void nullUnionTest() {
		Region region1 = null;
		Region region2 = new RegionImpl(1, 1, 5, 5);

		Region union = region2.union(region1);

		assertEquals(1, union.getLeft());
		assertEquals(1, union.getTop());
		assertEquals(5, union.getRight());
		assertEquals(5, union.getBottom());
	}

	@Test
	public void nullIntersection() throws NoIntersectionException {
		Region region1 = null;
		Region region2 = new RegionImpl(1, 1, 5, 5);

		try {
			Region intersect = region2.intersect(region1);
			fail("paramater region can't be zero");
		} catch (NoIntersectionException e) {
		}
	}

	@Test
	public void specialIntersection() throws NoIntersectionException {
		Region region1 = new RegionImpl(1, 1, 5, 5);
		Region region2 = new RegionImpl(1, 5, 5, 10);

		Region intersect = region2.intersect(region1);

		assertEquals(1, intersect.getLeft());
		assertEquals(5, intersect.getTop());
		assertEquals(5, intersect.getRight());
		assertEquals(5, intersect.getBottom());
	}
}

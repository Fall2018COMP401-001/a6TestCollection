package a6test.samzhu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import a6.NoIntersectionException;
import a6.Region;
import a6.RegionImpl;

public class A6Tests {
	@Test
	void testRegionImplConstructor() {
		try {
			Region a = new RegionImpl(5,1,1,2);
			fail("Left should not be greater than right");
		} catch (IllegalArgumentException e) {
		}
		try {
			Region b = new RegionImpl(1,5,2,1);
			fail("Top should not be greater than bottom");
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void testUnion() {
		Region a = new RegionImpl(1,2,3,5);
		Region b = new RegionImpl(2,4,7,6);
		Region union = a.union(b);
		assertEquals(1, union.getLeft());
		assertEquals(2, union.getTop());
		assertEquals(7, union.getRight());
		assertEquals(6, union.getBottom());
	}
	
	@Test
	public void testIntersection() throws NoIntersectionException {
		Region a = new RegionImpl(1,2,3,5);
		Region b = new RegionImpl(2,4,7,6);
		Region intersec = a.intersect(b);
		assertEquals(2,intersec.getLeft());
		assertEquals(4,intersec.getTop());
		assertEquals(3,intersec.getRight());
		assertEquals(5,intersec.getBottom());
	}
	
	@Test
	public void testNullIntersection() {
		Region a = new RegionImpl(1,2,3,5);
		Region b = null;
		try {
			Region intersec = a.intersect(b);
			fail("illegal intersection");
		} catch (NoIntersectionException e) {
		}
	}
	
	@Test
	public void testNoIntersection() {
		Region a = new RegionImpl(1,2,3,5);
		Region b = new RegionImpl(4,2,5,7);
		try {
			Region intersec = a.intersect(b);
			fail("illegal intersection");
		} catch (NoIntersectionException e) {
		}
	}
}

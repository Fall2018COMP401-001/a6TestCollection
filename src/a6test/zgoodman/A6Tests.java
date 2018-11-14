package a6test.zgoodman;

import a6.*;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class A6Tests {

	@Test
	public void testIntersect() throws NoIntersectionException {
		Region a = new RegionImpl(0, 0, 4, 4);
		Region b = new RegionImpl(2, 2, 6, 6);
		Region c = a.intersect(b);
		
		assertEquals(b.getTop(), c.getTop());
		assertEquals(a.getBottom(), c.getBottom());
		assertEquals(b.getLeft(), c.getLeft());
		assertEquals(a.getRight(), c.getRight());
	}
	
	@Test
	public void testIntersectBackwards() throws NoIntersectionException {
		Region a = new RegionImpl(0, 0, 4, 4);
		Region b = new RegionImpl(2, 2, 6, 6);
		Region c = b.intersect(a);
		
		assertEquals(b.getTop(), c.getTop());
		assertEquals(a.getBottom(), c.getBottom());
		assertEquals(b.getLeft(), c.getLeft());
		assertEquals(a.getRight(), c.getRight());
	}
}

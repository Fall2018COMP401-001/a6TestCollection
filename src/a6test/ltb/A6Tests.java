package a6test.ltb;

import org.junit.Test;

public class A6Tests {

	@Test
	public void RegionImplConstructorTest() {
		RegionImpl r = new RegionImpl(0,5,5,0);
	}
	
	@Test
	public void RegionGettersTest() {
		RegionImpl r = new RegionImpl(0,5,5,0);
		assertEquals(r.getleft, 0);
		assertEquals(r.getTop, 5);
		assertEquals(r.getRight, 5);
		assertEquals(r.getBottom, 0);
	}
	
	@Test
	public void IntersectTest() {
		RegionImpl a = new RegionImpl(0,5,5,0);
		RegionImpl b = new RegionImpl(3,8,8,3);
		a.intersect(b);
		assertEquals(a.getleft, 3);
		assertEquals(a.getTop, 5);
		assertEquals(a.getRight, 5);
		assertEquals(a.getBottom, 3);
	}
	
	@Test
	public void UnionTest() {
		RegionImpl a = new RegionImpl(0,5,5,0);
		RegionImpl b = new RegionImpl(3,8,8,3);
		a.union(b);
		assertEquals(a.getleft, 0);
		assertEquals(a.getTop, 8);
		assertEquals(a.getRight, 8);
		assertEquals(a.getBottom, 0);
	}
	
}

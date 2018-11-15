package a6test.jaredunc;

import a6;



public class A6Tests {
	
	public void testIntersect() throws NoIntersectionException {
		Region a = new RegionImpl(0, 0, 4, 4);
		Region b = new RegionImpl(0, 0, 7, 7);

		Region c = b.intersect(a);

		assertEquals(0, a.getLeft());
		assertEquals(0, a.getTop());
		assertEquals(4, a.getRight());
		assertEquals(4, a.getBottom());


	}
	
	public void testUnion() {
		
		Region a = new RegionImpl(0, 0, 4, 4);
		Region b = new RegionImpl(0, 0, 7, 7);
		
		Region c = b.union(a);
		
		assertEquals(0, a.getLeft());
		assertEquals(0, a.getTop());
		assertEquals(7, a.getRight());
		assertEquals(7, a.getBottom());
		
	}
}

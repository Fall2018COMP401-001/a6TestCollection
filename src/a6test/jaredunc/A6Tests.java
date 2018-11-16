package a6test.jaredunc;




public class A6Tests {
	
	public void Intersectiontestbasic() throws NoIntersectionException {
		Region a = new RegionImpl(1, 1, 5, 6);
		Region b = new RegionImpl(0, 0, 10, 10);

		Region c = b.intersect(a);

		assertEquals(1, a.getLeft());
		assertEquals(1, a.getTop());
		assertEquals(5, a.getRight());
		assertEquals(6, a.getBottom());


	}
	
	public void Intersectiontestbasic() {
		
		Region a = new RegionImpl(1, 1, 5, 6);
		Region b = new RegionImpl(0, 0, 2, 2);
		
		Region c = b.union(a);
		
		assertEquals(0, a.getLeft());
		assertEquals(0, a.getTop());
		assertEquals(5, a.getRight());
		assertEquals(6, a.getBottom());
		
	}
}

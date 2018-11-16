package a6test.jaredunc;




public class A6Tests {
	
	public void Intersectiontestbasic() throws NoIntersectionException {
		Region first = new RegionImpl(1, 1, 5, 6);
		Region second = new RegionImpl(0, 0, 10, 10);

		Region third = first.intersect(second);

		assertEquals(1, third.getLeft());
		assertEquals(1, third.getTop());
		assertEquals(5, third.getRight());
		assertEquals(6, third.getBottom());


	}
	
	public void uniontestbasic() {
		
		Region first = new RegionImpl(1, 1, 5, 6);
		Region second = new RegionImpl(0, 0, 2, 2);
		
		Region third = first.union(second);
		
		assertEquals(0, third.getLeft());
		assertEquals(0, third.getTop());
		assertEquals(5, third.getRight());
		assertEquals(6, third.getBottom());
		
	}
	
	public void throwerrorintersection() throws NoIntersectionException {
		Region first = new RegionImpl(1, 1, 5, 6);
		Region second = new RegionImpl(0, 0, 0, 0);
		
		try {
			Region third = first.union(second);
			
		} catch (NoIntersectionException badness) {
			
		}
	}
}

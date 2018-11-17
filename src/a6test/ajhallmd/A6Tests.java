package a6test.ajhallmd;

import static org.junit.Assert.*;


public class A6Tests {

	@Test
	public void myRegionUnionTest() {
		Region a = new RegionImpl(3,8,8,11);
		Region b = new RegionImpl(5,10,9,14);
		
		Region union = a.union(b);
		
		assertEquals(3, union.getLeft());
		assertEquals(8, union.getTop());
		assertEquals(9, union.getRight());
		assertEquals(14, union.getBottom());
		
		//other way
		
		union = b.union(a);
		
		assertEquals(3, union.getLeft());
		assertEquals(8, union.getTop());
		assertEquals(9, union.getRight());
		assertEquals(14, union.getBottom());
		
	}
}

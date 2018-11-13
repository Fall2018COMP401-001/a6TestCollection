package a6test.kmp;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

public class A6Tests {

	@Test
	public void basicRegionUnionTest() {
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = new RegionImpl(6, 6, 10, 10);
		
		Region union = a.union(b);
		
		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(10, union.getRight());
		assertEquals(10, union.getBottom());
		
		// Try the other way also.
		
		union = b.union(a);
		
		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(10, union.getRight());
		assertEquals(10, union.getBottom());
		
	}

}

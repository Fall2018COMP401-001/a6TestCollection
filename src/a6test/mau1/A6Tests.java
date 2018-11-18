package a6test.mau1;

import static org.junit.Assert.*;

import org.junit.Test;

public class A6Tests {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	import static org.junit.Assert.*;

	import org.junit.Test;

	import a6.Region;

public class A6Tests {

		
	@Test
	public void intersectionTest () {
		Region r = new RegionImpl(5, 5, 15, 15);
		Region s = new RegionImpl(10, 10, 20, 20);
			
		Region intersection = r.intersect(s);
			
		assertEquals (10, intersection.getLeft());
		assertEquals (10, intersection.getTop());
		assertEquals (15, intersection.getRight());
		assertEquals (15, intersection.getBottom());

	}

	@Test
	public void unionTest () {
		Region r = new RegionImpl(5, 5, 15, 15);
		Region s = new RegionImpl(10 , 10, 20, 20);
			
		Region union = r.union(s);
			
		assertEquals (5, union.getLeft());
		assertEquals (5, union.getTop());
		assertEquals (20, union.getRight());
		assertEquals (20, union.getBottom());
			
	}
		
		
}

	


package a6test.rmenon;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

public class A6Tests {

	Region r1 = new RegionImpl(1, 1, 3, 3);
	Region r2 = new RegionImpl(2, 2, 4, 4);
	Region r3 = new RegionImpl(3, 3, 5, 5);
	Region r4 = new RegionImpl(4, 4, 6, 6);
	Region rnull = null;
	
	Region r1nr2 = new RegionImpl(2, 2, 3, 3);
	Region r2nr3 = new RegionImpl(3, 3, 4, 4);
	Region r3nr4 = new RegionImpl(4, 4, 5, 5);
	
	Region r1ur2 = new RegionImpl(1, 1, 4, 4);
	Region r2ur3 = new RegionImpl(2, 2, 5, 5);
	Region r3ur4 = new RegionImpl(3, 3, 6, 6);
	
	@Test
	public void TestRegionImplConstructor() {
		try {
			Region rTopGreaterThanBottom = new RegionImpl(2, 2, 3, 1);
		} catch (IllegalArgumentException e) {
		}
		try {
			Region rLeftGreaterThanRight = new RegionImpl(3, 2, 2, 3);
		} catch (IllegalArgumentException e) {
		}
		try {
			Region rLeftAndTopGreater = new RegionImpl(2, 2, 1, 1);
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void TestRegionImplGetters() {
		Region r = new RegionImpl(1, 2, 3, 4);
		
		assertEquals(r.getLeft(), 1);
		assertEquals(r.getTop(), 2);
		assertEquals(r.getRight(), 3);
		assertEquals(r.getBottom(), 4);
	}
	
	@Test
	public void RegionIntersectionTest() throws NoIntersectionException {
		assertTrue(checkEqualRegions(r1.intersect(r2), r1nr2));
		assertTrue(checkEqualRegions(r2.intersect(r3), r2nr3));
		assertTrue(checkEqualRegions(r3.intersect(r4), r3nr4));
		
	}
	
	@Test
	public void RegionUnionTest() {
		assertTrue(checkEqualRegions(r1.union(r2), r1ur2));
		assertTrue(checkEqualRegions(r2.union(r3), r2ur3));
		assertTrue(checkEqualRegions(r3.union(r4), r3ur4));
	}
	
	
	@Test
	public void NullIntersectionTest() throws NoIntersectionException {
		try {
			r1.intersect(rnull);
		} catch (NoIntersectionException e) {
		}
	}
	
	@Test
	public void NullUnionTest() {
		assertTrue(checkEqualRegions(r1.union(rnull), r1));
	}
	
	
	
	
	private static boolean checkEqualRegions(Region r1, Region r2) {
		return (r1.getLeft() == r2.getLeft() && r1.getTop() == r2.getTop() 
				&& r1.getBottom() == r2.getBottom() && r1.getRight() == r2.getRight());
	}
}
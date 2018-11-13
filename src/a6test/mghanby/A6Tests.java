package a6test.mghanby;

import static org.junit.Assert.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import a6.*;

class A6Tests {

	@Test
	public void nullRegionIntersectionTest() throws NoIntersectionException {
		Region a = new RegionImpl(0, 0, 2, 2);
		try {
			Region intersect = a.intersect(null);
			
			throw new NoIntersectionException();
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void basicRegionIntersectionTest() throws NoIntersectionException {
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = new RegionImpl(2, 2, 8, 8);
		
		Region intersect = a.intersect(b);
		
		assertEquals(2, intersect.getLeft());
		assertEquals(2, intersect.getTop());
		assertEquals(5, intersect.getRight());
		assertEquals(5, intersect.getBottom());
		
		// Switched
		
		intersect = b.intersect(a);
		
		assertEquals(2, intersect.getLeft());
		assertEquals(2, intersect.getTop());
		assertEquals(5, intersect.getRight());
		assertEquals(5, intersect.getBottom());
	}

}

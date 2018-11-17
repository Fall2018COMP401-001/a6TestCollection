package a6test.merisb;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

public class A6Tests {
	@Test
	public void basicRegionTest() {
		RegionImpl x = new RegionImpl(1,2,3,4);
		assertEquals(1, x.getLeft());
		assertEquals(2, x.getTop());
		assertEquals(3, x.getRight());
		assertEquals(4, x.getBottom());
	}
	
	@Test
	public void basicIntersectionTest() throws NoIntersectionException {
		Region x = new RegionImpl(0, 0, 0, 0);
		try {
			x.intersect(null);
			
			throw new NoIntersectionException();
		} catch (Exception e) {
			}
		
		Region y = new RegionImpl(0,0,0,0);
		try {
			x.intersect(y);
		}catch (Exception e) {
			
		}
	}
	
	
}


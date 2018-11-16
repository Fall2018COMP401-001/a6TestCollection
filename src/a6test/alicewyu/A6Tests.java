package a6test.alicewyu;

import org.junit.Test;

import a6.*;
import javafx.scene.layout.Region;

public class A6Tests {
	
	@Test 
	public void testRegionGeometry() {
		
		try {
			Region region1 = new RegionImpl(3, 2, 1, 0);
			fail("Left cannot be greater than right"); 
			
		} catch (IllegalArgumentException e) {
			
		}
		
		try {
			Region region 2 = new RegionImpl(2, 5, 5, 3);
			fail("Top cannot be greater than bottom"); 
		} catch (IllegalArgumentException e) {
			
		}
	}
	
	
	@Test 
	public void testIntersect() {
		Region region1 = new RegionImpl(0, 1, 2, 4); 
		Region region2 = new RegionImpl(1, 3, 4, 5); 
		Region regionIntersect = new RegionImpl(1, 3, 2, 4); 
		
		assertEquals(region1.intersect(region2), regionIntersect); 
	}
	
	@Test 
	public void testUnion() {
		Region region1 = new RegionImpl(0, 1, 2, 4); 
		Region region2 = new RegionImpl(1, 3, 4, 5); 
		Region unionRegion = new RegionImpl(0, 1, 4, 5); 
		
		assertEquals(region1.union(region2), unionRegion); 
	}
}

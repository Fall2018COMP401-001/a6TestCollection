package a6test.ishaan14;

import static org.junit.Assert.*;

import org.junit.Test;

import a6test.susanshi.Region;
import a6test.susanshi.RegionImpl;

public class A6Tests {


	@Test
	void testRegionImplConstructorGeometry() {
		Region region1 = new RegionImpl(0, 1, 2, 3);
		Region region2 = new RegionImpl(1, 1, 2, 2);
		Region region3 = new RegionImpl(2, 1, 2, 1);
		try {
			Region illegalRegion = new RegionImpl(2, 1, 1, 2);
			fail("left is greater than right.");
			} 
		catch (IllegalArgumentException e) {
		}
		try {
			Region illegalRegion = new RegionImpl(1, 2, 2, 1);
			fail("top is greater than bottom.");
		} 
		catch (IllegalArgumentException e) {
		}
	}
	@Test
	public void testUnionOverlap() {
		Region region1 = new RegionImpl(2, 2, 3, 3);
		Region region2 = new RegionImpl(1, 1, 2, 2);
		
		Region regionUnion = region1.union(region2);
		assertEquals(2, regionUnion.getLeft());
		assertEquals(2, regionUnion.getTop());
		assertEquals(2, regionUnion.getRight());
		assertEquals(2, regionUnion.getBottom());
	}
}
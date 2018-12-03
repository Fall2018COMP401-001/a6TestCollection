package a6test.kass;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

class A6Tests {
	
	// Regions for no intersection
	Region region1122 = new RegionImpl(1, 1, 2, 2);
	Region region3344 = new RegionImpl(3, 3, 4, 4);
	
	//Regions for valid intersections and unions
	Region region0033 = new RegionImpl(0, 0, 3, 3);
	Region region1155 = new RegionImpl(1, 1, 5, 5);
	Region region7799 = new RegionImpl(7, 7, 9, 9);
	// result for intersection
	Region region1133 = new RegionImpl(1, 1, 3, 3);
	// results for union
	Region region0055 = new RegionImpl(0, 0, 5, 5);
	Region region0099 = new RegionImpl(0, 0, 9, 9);
	
	

	@Test
	void testRegionImplConstructor() {
		try {
			Region illegalRegionLR = new RegionImpl(3, 3, 2, 5);
		} catch (IllegalArgumentException e) {
		}
		
		try {
			Region illegalRegionTB = new RegionImpl(2, 5, 3, 3);
		} catch (IllegalArgumentException e) {
		}
		
		
		
	}
	
	@Test
	void testRegionIntersect() throws NoIntersectionException {
		try {
			region1122.intersect(region3344);
		} catch (NoIntersectionException e) {
		}
		
		assertTrue(check_Region_equality(region1155.intersect(region0033), region1133));
		assertTrue(check_Region_equality(region0033.intersect(region0055), region0033));
		
	}
	
	@Test
	void testRegionUnion() {
		assertTrue(check_Region_equality(region0033.union(region1155), region0055));
		assertTrue(check_Region_equality(region0055.union(region7799), region0099));
	}
	
	
	
	
	

public static boolean check_Region_equality(Region a, Region b) {
	if ((a.getTop() == b.getTop()) && (a.getLeft() == b.getLeft()) &&
			(a.getBottom() == b.getBottom()) && (a.getRight() == b.getRight())) {
		return true;
	} 
	return false;
}

	
}

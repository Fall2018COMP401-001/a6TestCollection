package a6test.tony11;

import a6.*;
import javafx.scene.layout.Region;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/*Some basic tests for checking regions
 * Constructors, Union, Intersection
 * and erroneous cases that should be dealt with properly
 */
class A6Tests {
	
	@Test
	void regionTest() {
		//basic test to see if regionimpl constructor checks for bad coords
		try {
			Region r1 = new RegionImpl(0,0,3,3);
		} catch (Exception e) {
			fail("no exception here");
		}
		try {
			Region r2 = new RegionImpl(3,3,0,0);
			fail("invalid coords");
		} catch (Exception e) {}
		
		//testing getters
		assertEquals(r1.getLeft(),0);
		assertEquals(r1.getTop(),0);
		assertEquals(r1.getRight(),3);
		assertEquals(r1.getBottom(),3);
	}
	
	@Test
	void unionTest() {
		Region r1 = new RegionImpl(0,0,3,3);
		Region r2 = null;
		Region r3 = new RegionImpl(1,1,5,5);
		//check to make sure it'll allow null union
		try {
			r1.union(r2);
		} catch (Exception e) {
			fail("You can union with null region");
		}
		
		//check if the union of r1 and r3 has the proper top, bottom, left, and right
		Region unionedR = r1.union(r3);
		assertEquals(unionedR.getTop(),r1.getTop());
		assertEquals(unionedR.getLeft(),r1.getLeft());
		assertEquals(unionedR.getBottom(),r2.getBottom());
		assertEquals(unionedR.getRight(),r2.getRight());
	}
	
	@Test
	void intersectTest() throws NoIntersectionException{
		Region r1 = new RegionImpl(0,0,2,2);
		Region r2 = new RegionImpl(3,3,5,5);
		Region r3 = new RegionImpl(1,1,3,3);
		
		//basic tests to see if intersect properly checks for intersection or no intersection
		try {
			r1.intersect(r2);
			fail("these regions don't intersect")
		} catch (NoIntersectionException e) {}
		try {
			r1.intersects(r3);
		} catch (NoIntersectionException e) {
			fail("these regions do intersect");
		}
	}
}

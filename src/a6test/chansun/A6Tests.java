package a6test.chansun;

import static org.junit.Assert.*;

import org.junit.Test;
import a6.*;

public class A6Tests {

	
	
	@Test
	public void testRegionImpleConstructor() {

		try {
			Region region1 = new RegionImpl(-1, 1, 3, 3);
			fail("Wrong parameter passed in");
		} catch (IllegalArgumentException e) {
		}
		
		try {
			Region region1 = new RegionImpl(0, 1, -3, 3);
			fail("Wrong parameter passed in");
		} catch (IllegalArgumentException e) {
		}
		
		try {
			Region region1 = new RegionImpl(0, -1, 3, 3);
			fail("Wrong parameter passed in");
		} catch (IllegalArgumentException e) {
		}
		
		try {
			Region region1 = new RegionImpl(0, 1, 3, -3);
			fail("Wrong parameter passed in");
		} catch (IllegalArgumentException e) {
		}
		
		try {
			Region region1 = new RegionImpl(3, 1, 2, 2);
			fail("Wrong parameter passed in");
		} catch (IllegalArgumentException e) {
		}
		try {
			Region region1 = new RegionImpl(3, 2, 4, 1);
			fail("Wrong parameter passed in");
		} catch (IllegalArgumentException e) {
		}
		
		try {
			Region region1 = new RegionImpl(1, 1, 1, 3);
		} catch (IllegalArgumentException e) {
		}
		
		try {
			Region region1 = new RegionImpl(1, 1, 3, 1);
		} catch (IllegalArgumentException e) {
		}
		
		try {
			Region region1 = new RegionImpl(1, 1, 1, 1);
		} catch (IllegalArgumentException e) {
		}
		
	}
	
	@Test
	public void testIntersect() {
		RegionImpl regionTester = new RegionImpl(1,1,5,5);
		RegionImpl regionTester2 = new RegionImpl(5,5,8,8);
		RegionImpl regionTester3 = new RegionImpl(4,6,8,8);
		RegionImpl regionTester4 = new RegionImpl(4,5,8,8);
		RegionImpl regionTester5 = null;
		
		try {
			regionTester.intersect(regionTester2);
		} catch (NoIntersectionException e) {
		}
		
		try {
			regionTester.intersect(regionTester3);
			fail("you should've thrown NoIntersectionException here");
		} catch (NoIntersectionException e) {
		}
		
		try {
			regionTester.intersect(regionTester4);
		} catch (NoIntersectionException e) {
		}
		
		try {
			regionTester.intersect(regionTester5);
			fail("you should've thrown NoIntersectionException here");
		} catch (NoIntersectionException e) {
		}
	}
	
	@Test
	public void testUnion() {
		RegionImpl regionTester = new RegionImpl(1,1,5,5);
		RegionImpl regionTester2 = new RegionImpl(2,2,6,6);
		RegionImpl regionTester3 = null;
		
		try {
			regionTester.union(regionTester2);
		} catch (IllegalArgumentException e) {
			fail("you should not throw IllegalArgumentException here");
		} 
		
		RegionImpl regionTester4 = (RegionImpl) regionTester.union(regionTester3);
		assertEquals(regionTester, regionTester4);
		
	}
	
}

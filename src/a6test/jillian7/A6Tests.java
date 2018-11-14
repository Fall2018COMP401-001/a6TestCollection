package a6test.jillian7;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;

import a6.*;

public class A6Tests {

	@Test
	public void nullRegionIntersectTest() {
		Region first = new RegionImpl(1,1,7,7);
		Region second = null;

		try {
			first.intersect(second);
			fail("Intersect parameter cannot be null");
		} catch (NoIntersectionException e) {
		}

	}

	@Test
	public void invalidIntersectTest() {
		Region first;
		Region second;

		try {
			first = new RegionImpl(1,1,3,7);
			second = new RegionImpl(5,1,7,7);
			first.intersect(second);
			fail("No intersection of these two regions");
		} catch (NoIntersectionException e) {
		}

		try {	
			first = new RegionImpl(5,1,5,7);
			second = new RegionImpl(1,1,3,7);
			first.intersect(second);
			fail("No intersection of these two regions");
		} catch (NoIntersectionException e) {
		}
		try {	
			first = new RegionImpl(5,1,5,1);
			second = new RegionImpl(1,7,3,7);
			first.intersect(second);
			fail("No intersection of these two regions");
		} catch (NoIntersectionException e) {
		}
		try {	
			first = new RegionImpl(5,7,5,7);
			second = new RegionImpl(1,1,3,4);
			first.intersect(second);
			fail("No intersection of these two regions");
		} catch (NoIntersectionException e) {
		}

	}

	@Test
	public void gettersTest() {
		Region test = new RegionImpl(3,5,3,5);

		assertEquals(test.getBottom(), 5);
		assertEquals(test.getTop(), 5);
		assertEquals(test.getRight(), 3);
		assertEquals(test.getLeft(), 3);

	}

	@Test
	public void regionImplConstructorTest() {

		try {		
			Region test = new RegionImpl(5,7,3,7);
		} catch (IllegalArgumentException e) {
		}
		try {		
			Region test = new RegionImpl(5,6,5,3);
		} catch (IllegalArgumentException e) {
		}


	}



}

package a6test.zasweq;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

public class A6Tests {

	@Test
	public void basicRegionIntersectionTest() {
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = new RegionImpl(3, 3, 10, 10);
		
		Region intersection = null;
		try {
			intersection = a.intersect(b);
		} catch (NoIntersectionException e) {
			fail("Threw an exception when you should not have");
		}
		assertEquals(3, intersection.getLeft());
		assertEquals(3, intersection.getTop());
		assertEquals(5, intersection.getRight());
		assertEquals(5, intersection.getBottom());
		
		//other way
		try {
			intersection = b.intersect(a);
		} catch (NoIntersectionException e) {
			fail("Threw an exception when you should not have");
		}
		assertEquals(3, intersection.getLeft());
		assertEquals(3, intersection.getTop());
		assertEquals(5, intersection.getRight());
		assertEquals(5, intersection.getBottom());
	}
	
	@Test
	public void anotherBasicRegionIntersectionTest() {
		Region a = new RegionImpl(0, 0, 10, 10);
		Region b = new RegionImpl(5, 5, 15, 15);
		
		Region intersection = null;
		try {
			intersection = a.intersect(b);
		} catch (NoIntersectionException e) {
			fail("Threw an exception when you should not have");
		}
		assertEquals(5, intersection.getLeft());
		assertEquals(5, intersection.getTop());
		assertEquals(10, intersection.getRight());
		assertEquals(10, intersection.getBottom());
		
		//other way
		try {
			intersection = b.intersect(a);
		} catch (NoIntersectionException e) {
			fail("Threw an exception when you should not have");
		}
		assertEquals(5, intersection.getLeft());
		assertEquals(5, intersection.getTop());
		assertEquals(10, intersection.getRight());
		assertEquals(10, intersection.getBottom());
	}

	@Test
	public void nullRegionIntersectionTest() {
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = null;
		Region intersection;
		try {
			intersection = a.intersect(b);
			fail("You need to throw a no intersection exception.");
		} catch (NoIntersectionException e) {
			//To make sure you throw an exception, simply continues
		}
	}
	
	@Test
	public void anotherNullRegionIntersectionTest() {
		Region a = null;
		Region b = new RegionImpl(0, 0, 20, 20);
		Region intersection;
		try {
			intersection = b.intersect(a);
			fail("You need to throw a no intersection exception.");
		} catch (NoIntersectionException e) {
			//To make sure you throw an exception, simply continues
		}
	}
	
	@Test
	public void noIntersectionTest() {
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = new RegionImpl(10, 10, 15, 15);
		Region intersection;

		try {
			intersection = a.intersect(b);
			fail("You need to throw a no intersection exception");
		} catch (NoIntersectionException e) {
			//To make sure you throw an exception, simply continues
		}
		
		//other way
		try {
			intersection = b.intersect(a);
			fail("You need to throw a no intersection exception");
		} catch (NoIntersectionException e) {
			//To make sure you throw an exception, simply continues
		}
	}
	
	@Test
	public void anotherNoIntersectionTest() {
		Region a = new RegionImpl(0, 0, 1, 1); //to make sure logic behind <= or < is correct
		Region b = new RegionImpl(2, 2, 3, 3);
		
		Region intersection;
		try {
			intersection = a.intersect(b);
			fail("You need to throw a no intersection exception");
		} catch (NoIntersectionException e) {
			//To make sure you throw an exception, simply continues
		}
		
		//other way
		try {
			intersection = b.intersect(a);
			fail("You need to throw a no intersection exception");
		} catch (NoIntersectionException e) {
			//To make sure you throw an exception, simply continues
		}
	}	
}

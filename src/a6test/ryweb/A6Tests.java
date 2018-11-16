package a6test.ryweb;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;

import a6.*;

public class A6Tests {
	
	@Test
	public void NullIntersectionTest() {
		Region test = new RegionImpl(1,1,7,7);
		Region empty = null;

		try {
			test.intersect(empty);
			fail("Cannnot pass null Region into intersect");
		} catch (NoIntersectionException a) {
		}
	}

	@Test
	public void InvalidIntersectionTest() {
		Region test;
		Region invalid;

		try {
			test = new RegionImpl(1,1,3,3);
			invalid = new RegionImpl(5,5,7,7);
			test.intersect(invalid);
			fail("These Regions do not have overlap");
		} catch (NoIntersectionException a) {
		}
		try {	
			test = new RegionImpl(5,1,5,7);
			invalid = new RegionImpl(1,1,3,7);
			test.intersect(invalid);
			fail("These Regions do not have overlap");
		} catch (NoIntersectionException a) {
		}
		try {	
			test = new RegionImpl(5,1,5,1);
			invalid = new RegionImpl(1,7,3,7);
			test.intersect(invalid);
			fail("These Regions do not have overlap");
		} catch (NoIntersectionException a) {
		}
		try {	
			test = new RegionImpl(5,7,5,7);
			invalid = new RegionImpl(1,1,3,4);
			test.intersect(invalid);
			fail("These Regions do not have overlap");
		} catch (NoIntersectionException a) {
		}
	}
}

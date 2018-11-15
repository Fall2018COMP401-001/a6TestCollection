package a6test.morgan24;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.NoIntersectionException;
import a6.Region;
import a6.RegionImpl;

public class A6Tests {

	@Test
	public void basicIntersectionTest() throws NoIntersectionException {
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = new RegionImpl(4, 4, 8, 8);
		
		Region intersect = a.intersect(b);
		
		assertEquals(4, intersect.getLeft());
		assertEquals(4, intersect.getTop());
		assertEquals(5, intersect.getRight());
		assertEquals(5, intersect.getBottom());
		
		intersect = b.intersect(a);
		
		assertEquals(4, intersect.getLeft());
		assertEquals(4, intersect.getTop());
		assertEquals(5, intersect.getRight());
		assertEquals(5, intersect.getBottom());
		
	}

}

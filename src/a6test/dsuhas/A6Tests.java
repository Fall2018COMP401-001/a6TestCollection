package a6test.dsuhas;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

public class A6Tests {

	@Test
	public void unionTest() {
		Region reg1 = new RegionImpl(0, 0, 2, 2);
		Region reg2 = new RegionImpl(1, 1, 3, 3);
		Region union = reg1.union(reg2);
		
		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(3, union.getRight());
		assertEquals(3, union.getBottom());
		
		Region union2 = reg2.union(reg1);
		
		assertEquals(0, union2.getLeft());
		assertEquals(0, union2.getTop());
		assertEquals(3, union2.getRight());
		assertEquals(3, union2.getBottom());
		
		
	}
	
	@Test
	public void intersectionTest() throws NoIntersectionException {
		Region reg1 = new RegionImpl(0, 0, 2, 2);
		Region reg2 = new RegionImpl(1, 1, 3, 3);
		Region intersect = reg1.intersect(reg2);
		
		assertEquals(1, intersect.getLeft());
		assertEquals(1, intersect.getTop());
		assertEquals(2, intersect.getRight());
		assertEquals(2, intersect.getBottom());
		
		Region intersect2 = reg2.intersect(reg1);
		
		assertEquals(1, intersect2.getLeft());
		assertEquals(1, intersect2.getTop());
		assertEquals(2, intersect2.getRight());
		assertEquals(2, intersect2.getBottom());
		
		
	}

}

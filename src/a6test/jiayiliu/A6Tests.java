package a6test.jiayiliu;
import a6.*;
import static org.junit.Assert.*;
import org.junit.Test;

import a6.NoIntersectionException;
import a6.Region;
import a6.RegionImpl;


public class A6Tests {
	 	@Test
		public void basicIntersectionTest() throws NoIntersectionException {
			Region a = new RegionImpl(0, 0, 6, 8);
			Region b = new RegionImpl(0, 5, 3, 12);
			
			Region intersect = a.intersect(b);
			
			assertEquals(0, intersect.getLeft());
			assertEquals(5, intersect.getTop());
			assertEquals(3, intersect.getRight());
			assertEquals(8, intersect.getBottom());
			
		}
	 	
	 	@Test
	 	public void basicUnionTest() {
	 		Region a = new RegionImpl(0, 0, 10, 10);
	 		Region b = new RegionImpl(7, 7, 11, 15);
	 		
	 		Region union = a.union(b);
	 		
	 		assertEquals(0, union.getLeft());
	 		assertEquals(0, union.getTop());
	 		assertEquals(11, union.getRight());
	 		assertEquals(15, union.getBottom());
	 	}

}

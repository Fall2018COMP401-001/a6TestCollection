package a6test.prasjain;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import java.util.ArrayList;
public class A6Tests{
	@Test
	public void basicRegionUnionTest() {
		Region a = new RegionImpl(2, 2, 7, 0);
		Region b = new RegionImpl(3, 3, 5, 10);

		Region union = a.union(b);

		assertEquals(2, union.getLeft());
		assertEquals(2, union.getTop());
		assertEquals(7, union.getRight());
		assertEquals(10, union.getBottom());

		// Try the other way also.

		union = b.union(a);

		assertEquals(2, union.getLeft());
		assertEquals(2, union.getTop());
		assertEquals(7, union.getRight());
		assertEquals(10, union.getBottom());
	} 
}
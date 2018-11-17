package a6.ma101521;

import static org.junit.Assert.assertEquals;
import a6.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;



class A6Tests {

	@Test
	public void testRegionGets(){
		Region a = new RegionImpl(0, 0, 4, 4);
		
		assertEquals(0, a.getLeft());
		assertEquals(0, a.getTop());
		assertEquals(4, a.getRight());
		assertEquals(4, a.getBottom());
	}
	
	@Test
	public void testIntersection() throws NoIntersectionException{
		Region top = new RegionImpl(0, 0, 4, 4);
		Region bottom = new RegionImpl(2, 2, 6, 6);
		try {
			top.intersect(null);
			fail("Other region cannot be null");
		} catch (NoIntersectionException e) {
		}
		try {
			bottom.intersect(null);
			fail("Other region cannot be null");
		} catch (NoIntersectionException e) {
		}
		Region intersected = new RegionImpl(2, 2, 4, 4);
		Region test = top.intersect(bottom);
		assertEquals(intersected.getLeft(), test.getLeft());
		assertEquals(intersected.getTop(), test.getTop());
		assertEquals(intersected.getRight(), test.getRight());
		assertEquals(intersected.getBottom(), test.getBottom());
	}
	
	@Test
	public void testUnion() {
		Region top = new RegionImpl(0, 0, 4, 4);
		Region bottom = new RegionImpl(2, 2, 6, 6);
		Region far = new RegionImpl(6, 6, 12, 12);
		
		Region union_top_bottom = new RegionImpl(0, 0, 6, 6);
		Region test_top_bottom = top.union(bottom);
		assertEquals(union_top_bottom.getLeft(), test_top_bottom.getLeft());
		assertEquals(union_top_bottom.getTop(), test_top_bottom.getTop());
		assertEquals(union_top_bottom.getRight(), test_top_bottom.getRight());
		assertEquals(union_top_bottom.getBottom(), test_top_bottom.getBottom());
		
		Region union_far_top = new RegionImpl(0, 0, 12, 12);
		Region test_far_top = top.union(far);
		assertEquals(union_far_top.getLeft(), test_far_top.getLeft());
		assertEquals(union_far_top.getTop(), test_far_top.getTop());
		assertEquals(union_far_top.getRight(), test_far_top.getRight());
		assertEquals(union_far_top.getBottom(), test_far_top.getBottom());
		
		assertTrue(union_far_top.union(null) == union_far_top);
	}
	
	@Test 
	public void testObservableConstructor() {
		Pixel[][] red_array=new Pixel [12][12];
		for (int i=0;i<red_array.length;i++) {
			for (int j=0;j<red_array[i].length;j++) {
				red_array[i][j]=red;
			}
		}
		Picture mutable_red=new MutablePixelArrayPicture(red_array, "mutable red picture");
		ObservablePicture observable=new ObservablePictureImpl(mutable_red);
		
		ObservablePicture test = new ObservablePictureImpl(observable);
		try {
			ObservablePicture failed = new ObservablePictureImpl(null);
			fail("input cannot be null");
		}catch(IllegalArgumentException e) {
		}
	}
	
	
	
	

}

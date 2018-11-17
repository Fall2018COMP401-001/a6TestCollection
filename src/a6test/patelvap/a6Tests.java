package a6test.patelvap;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*

public class A6Tests {

	Pixel white = new ColorPixel(1, 1, 1);
	Pixel black = new ColorPixel(0, 0, 0);
	Pixel red = new ColorPixel(1, 0, 0);
	Pixel blue = new ColorPixel(0, 0, 1);
	Pixel green = new ColorPixel(0, 1, 0);
	
	Pixel[][] allRed10x10Picture = { 


			{red, red, red, red, red, red, red, red, red, red} ,
			{red, red, red, red, red, red, red, red, red, red} ,
			{red, red, red, red, red, red, red, red, red, red} ,
			{red, red, red, red, red, red, red, red, red, red} ,
			{red, red, red, red, red, red, red, red, red, red} ,
			{red, red, red, red, red, red, red, red, red, red} ,
			{red, red, red, red, red, red, red, red, red, red} ,
			{red, red, red, red, red, red, red, red, red, red} ,
			{red, red, red, red, red, red, red, red, red, red} ,
			{red, red, red, red, red, red, red, red, red, red} 
	};//

	Picture redTenByTen = new MutablePixelArrayPicture(allRed10x10Picture, "10x10 Red");
	
	@Test
	public void testRegionConstructor() {
		try {
			Region r1 = new RegionImpl(2,1,0,2);
			fail("left allowed to be greater than right");
		}
		catch (IllegalArgumentException e) {
		}
		try {
			Region r1 = new RegionImpl(2,1,1,0);
			fail("top allowed to be greater than bottom");
		}
		catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void testIntersection() throws NoIntersectionException {
		Region r1 = new RegionImpl(0,0,5,5);
		Region r2 = new RegionImpl(2,2,7,7);
		
		Region intersect1 = r1.intersect(r2);
		
		assertEquals(2, intersect1.getLeft());
		assertEquals(2, intersect1.getTop());
		assertEquals(5, intersect1.getRight());
		assertEquals(5, intersect1.getBottom());
		
		Region intersect2 = r2.intersect(r1);
		
		assertEquals(2, intersect2.getLeft());
		assertEquals(2, intersect2.getTop());
		assertEquals(5, intersect2.getRight());
		assertEquals(5, intersect2.getBottom());
	}
	
	public void testROIObserverRegisterandFindandRemoveMethods() {
		ROIObserver o1 = new ROIObserverImpl();
		ROIObserver o2 = new ROIObserverImpl();
		ROIObserver o3 = new ROIObserverImpl();
		ROIObserver o4 = new ROIObserverImpl();
		ROIObserver o5 = new ROIObserverImpl();
		
		Region r1 = new RegionImpl(0,1,4,4);
		Region r2 = new RegionImpl(1,2,6,6);
		
		ObservablePicture o = new ObservablePictureImpl(redTenByTen);
		
		o.registerROIObserver(o1,r1);
		o.registerROIObserver(o2,r1);
		o.registerROIObserver(o3,r1);
		o.registerROIObserver(o4,r1);
		o.registerROIObserver(o5,r1);
		
		o.registerROIObserver(o1,r2);
		o.registerROIObserver(o2,r2);
		o.registerROIObserver(o3,r2);
		o.registerROIObserver(o4,r2);
		o.registerROIObserver(o5,r2);
		
		assertEquals(5, o.findROIObservers(r1).length);
		assertEquals(5, o.findROIObservers(r2).length);
		
		ROIObserver[] r1Array = o.findROIObservers(r1);
		
		assertEquals(r1Array[0], o1);
		assertEquals(r1Array[1], o2);
		assertEquals(r1Array[2], o3);
		assertEquals(r1Array[3], o4);
		assertEquals(r1Array[4], o5);
		
		o.unregisterROIObserver(o1);
		
		assertEquals(4, o.findROIObservers(r1).length);
		assertEquals(4, o.findROIObservers(r2).length);
		
		o.unregisterROIObservers(r1);
		
		assertEquals(0, o.findROIObservers(r1).length);
		assertEquals(4, o.findROIObservers(r2).length);
	}//
	
	public void testUnionRegion() {
		Region r1 = new RegionImpl(0,2,4,6);
		Region r2 = new RegionImpl(8,10,12,14);
		
		Region u = r1.union.r2;
		
		assertEquals(0,u.getLeft());
		assertEquals(2,u.getTop());
		assertEquals(12,u.getRight());
		assertEquals(14,u.getBottom());
	}
}
package a6test.haliechm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import org.junit.Test;


import a6.*;

public class A6Tests {

	// Initialize different pixel amounts
	Pixel white = new ColorPixel(1, 1, 1);
	Pixel black = new ColorPixel(0, 0, 0);
	Pixel red = new ColorPixel(1, 0, 0);
	Pixel blue = new ColorPixel(0, 0, 1);
	Pixel green = new ColorPixel(0, 1, 0);


	// 2D Pixel Arrays
	Pixel[][] allRed4x6Picture = { { red, red, red, red, red, red }, { red, red, red, red, red, red },
			{ red, red, red, red, red, red }, { red, red, red, red, red, red } };

	Pixel[][] allBlue4x6Picture = { { blue, blue, blue, blue, blue, blue }, { blue, blue, blue, blue, blue, blue },
			{ blue, blue, blue, blue, blue, blue }, { blue, blue, blue, blue, blue, blue } };

	Pixel[][] allBlue5x5Picture = { { blue, blue, blue, blue, blue }, { blue, blue, blue, blue, blue },
			{ blue, blue, blue, blue, blue }, { blue, blue, blue, blue, blue }, { blue, blue, blue, blue, blue } };

	Pixel[][] allBlue2x2Picture = { { blue, blue }, { blue, blue } };

	
	
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
	
	
	};

	Picture blueFourBySix = new MutablePixelArrayPicture(allBlue4x6Picture, "4x6 Blue");
	Picture redFourBySix = new MutablePixelArrayPicture(allRed4x6Picture, "4x6 Red");
	Picture blueTwoByTwo = new MutablePixelArrayPicture(allBlue2x2Picture, "2x2 Blue");
	Picture redTenByTen = new MutablePixelArrayPicture(allRed10x10Picture, "10x10 Red");

	
	// tests intersection method
	@Test
	public void testIntersect() throws NoIntersectionException {
		Region one = new RegionImpl(0, 0, 4, 4);
		Region two = new RegionImpl(3, 3, 7, 7);

		Region intersection = one.intersect(two);

		assertEquals(3, intersection.getLeft());
		assertEquals(3, intersection.getTop());
		assertEquals(4, intersection.getRight());
		assertEquals(4, intersection.getBottom());

		// trying other way as well

		intersection = two.intersect(one);

		assertEquals(3, intersection.getLeft());
		assertEquals(3, intersection.getTop());
		assertEquals(4, intersection.getRight());
		assertEquals(4, intersection.getBottom());

	}
	
	// tests intersection method when only one pixel intersects
	@Test 
	public void testOnePixelIntersection() throws NoIntersectionException {
		
		Region one = new RegionImpl(0, 0, 4, 4);
		Region two = new RegionImpl(4, 4, 7, 7);

		Region intersection = one.intersect(two);

		assertEquals(4, intersection.getLeft());
		assertEquals(4, intersection.getTop());
		assertEquals(4, intersection.getRight());
		assertEquals(4, intersection.getBottom());

		// trying other way as well

		intersection = two.intersect(one);

		assertEquals(4, intersection.getLeft());
		assertEquals(4, intersection.getTop());
		assertEquals(4, intersection.getRight());
		assertEquals(4, intersection.getBottom());

		
	}

	// tests if ROIObservers are being registered properly
	@Test
	public void testRegisterROIObserver() {
		ROIObserver obs1 = new ROIObserverImpl();
		ROIObserver obs2 = new ROIObserverImpl();
		ROIObserver obs3 = new ROIObserverImpl();
		ROIObserver obs4 = new ROIObserverImpl();
		
		Region reg1 = new RegionImpl(0, 0, 2, 3);
		Region reg2 = new RegionImpl(2, 2, 5, 5);
		Region reg3 = new RegionImpl(1, 2, 2, 3);
		ObservablePicture pic = new ObservablePictureImpl(redFourBySix);
		
		pic.registerROIObserver(obs1, reg1);
		pic.registerROIObserver(obs2, reg1);
		pic.registerROIObserver(obs1, reg1);
		pic.registerROIObserver(obs2, reg2);
		pic.registerROIObserver(obs1, reg3);
		pic.registerROIObserver(obs3, reg1);
		
		ROIObserver[] rarray = pic.findROIObservers(reg1);
		
		assertEquals(rarray[0], obs1);
		assertEquals(rarray[1], obs2);
		assertEquals(rarray[2], obs1);
		assertEquals(rarray[3], obs2);
		assertEquals(rarray[4], obs1);
		assertEquals(rarray[5], obs3);
		
		// registers more ROIObservers onto original
		pic.registerROIObserver(obs4, reg1);
		pic.registerROIObserver(obs3, reg2);
		
		ROIObserver[] rarrayAdded = pic.findROIObservers(reg1);
		
		assertEquals(rarrayAdded[6], obs4);
		assertEquals(rarrayAdded[7], obs3);
	
	}
	
	// tests if ROIObservers are properly being unregistered
	@Test 
	public void testUnregisterROIObserver() {
		ROIObserver obs1 = new ROIObserverImpl();
		ROIObserver obs2 = new ROIObserverImpl();
		ROIObserver obs3 = new ROIObserverImpl();
		
		Region reg1 = new RegionImpl(0, 0, 2, 3);
		Region reg2 = new RegionImpl(2, 2, 5, 5);
		Region reg3 = new RegionImpl(1, 2, 2, 3);
		ObservablePicture pic = new ObservablePictureImpl(redFourBySix);
		
		pic.registerROIObserver(obs1, reg1);
		pic.registerROIObserver(obs2, reg1);
		pic.registerROIObserver(obs1, reg1);
		pic.registerROIObserver(obs2, reg2);
		pic.registerROIObserver(obs1, reg3);
		pic.registerROIObserver(obs3, reg1);
		
		ROIObserver[] rarray = pic.findROIObservers(reg1);
		
		assertEquals(rarray[0], obs1);
		assertEquals(rarray[1], obs2);
		assertEquals(rarray[2], obs1);
		assertEquals(rarray[3], obs2);
		assertEquals(rarray[4], obs1);
		assertEquals(rarray[5], obs3);
		
		// unregisters obs1 
		pic.unregisterROIObserver(obs1);
		
		// tests to make sure no obs1 are associated with pic
		ROIObserver[] observers = pic.findROIObservers(reg1);
		assertEquals(observers[0], obs2);
		assertEquals(observers[1], obs2);
		assertEquals(observers[2], obs3);
		
		// now unregisters obs2
		pic.unregisterROIObserver(obs2);
		
		// only observers left are obs3
		ROIObserver[] observersNew = pic.findROIObservers(reg1);
		assertEquals(observersNew[0], obs3);
		
	}
	
	// test if proper ROIObserver array is returned
	@Test
	public void testfindROIObservers() {
		
		
		ROIObserver obs1 = new ROIObserverImpl();
		ROIObserver obs2 = new ROIObserverImpl();
		ROIObserver obs3 = new ROIObserverImpl();
		ROIObserver obs4 = new ROIObserverImpl();
		
		Region reg1 = new RegionImpl(0, 0, 9, 9);
		Region reg2 = new RegionImpl(5, 6, 7, 7);
		Region reg3 = new RegionImpl(2, 2, 4, 4);
		Region reg4 = new RegionImpl(5, 5, 8, 8);
		ObservablePicture pic = new ObservablePictureImpl(redTenByTen);
		
		pic.registerROIObserver(obs1, reg1);
		pic.registerROIObserver(obs2, reg1);
		pic.registerROIObserver(obs3, reg1);
		pic.registerROIObserver(obs4, reg1);
		
		pic.registerROIObserver(obs4, reg2);
		pic.registerROIObserver(obs2, reg2);
		pic.registerROIObserver(obs1, reg2);
		pic.registerROIObserver(obs4, reg2);
		
		pic.registerROIObserver(obs4, reg3);
		pic.registerROIObserver(obs3, reg3);
		pic.registerROIObserver(obs2, reg3);
		pic.registerROIObserver(obs4, reg3);
		
		pic.registerROIObserver(obs3, reg4);
		pic.registerROIObserver(obs2, reg4);
		pic.registerROIObserver(obs3, reg4);
		pic.registerROIObserver(obs3, reg4);
		
		// removes observers that regions intersect with reg3
		ROIObserver[] observers = pic.findROIObservers(reg3);
		
		assertEquals(observers[0], obs1);
		assertEquals(observers[1], obs2);
		assertEquals(observers[2], obs3);
		assertEquals(observers[3], obs4); 
		
		assertEquals(observers[4], obs4);
		assertEquals(observers[5], obs3);
		assertEquals(observers[6], obs2);
		assertEquals(observers[7], obs4);
		
		
	}
	
	// Tests if caption can be retrieved from an ObservablePictureImpl object
	@Test
	public void testCaption() {
		ObservablePicture obsPic = new ObservablePictureImpl(blueTwoByTwo);
		
		String cap = obsPic.getCaption();
		
		assertEquals(cap, "2x2 Blue");
		assertFalse(cap.equals("3x3 Green"));
		
	}
	
	// Tests union method when the regions are exactly the same
	@Test 
	public void testUnionNoDifferenceBetweenPictures() {
		
		Region one = new RegionImpl(0, 0, 20, 20);
		Region two = new RegionImpl(0, 0, 20, 20);
		
		Region union = one.union(two);
		
		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(20, union.getRight());
		assertEquals(20, union.getBottom());

		// Try the other way also.

		union = two.union(one);

		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(20, union.getRight());
		assertEquals(20, union.getBottom());
		
		
	}
	
	// Tests if exception is thrown in illegal arguments 
	// are passed into the constructor of RegionImpl
	@Test
	public void testIllegalArgumentRegionImpl() {
		try {
			Region reg = new RegionImpl(5, 3, 4, 4);
			fail("Left cannot be bigger than right");
		} catch (IllegalArgumentException e) {
		}
		
		try {
			Region reg2 = new RegionImpl(3, 5, 4, 4);
			fail("Top cannot be bigger than bottom");
		} catch (IllegalArgumentException e) {
		}
		
		try {
			Region reg3 = new RegionImpl(4, 5, 4, 4);
			fail("Top  cannot be bigger than bottom AND Left cannot be biggern than right");
		} catch (IllegalArgumentException e) {
		}
		
	}

	// KMP TEST
	@Test
	public void nullRegionUnionTest() {
		Region a = new RegionImpl(1, 1, 6, 5);
		Region union = a.union(null);

		assertEquals(a.getLeft(), union.getLeft());
		assertEquals(a.getTop(), union.getTop());
		assertEquals(a.getRight(), union.getRight());
		assertEquals(a.getBottom(), union.getBottom());
		assertEquals(a, union);

	}

	
	// KMP TEST
	@Test
	public void basicRegionUnionTest() {
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = new RegionImpl(6, 6, 10, 10);

		Region union = a.union(b);

		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(10, union.getRight());
		assertEquals(10, union.getBottom());

		// Try the other way also.

		union = b.union(a);

		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(10, union.getRight());
		assertEquals(10, union.getBottom());

	}

}


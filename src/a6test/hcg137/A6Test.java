package a6test.hcg137;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;


public class A6Test {
	
	Region r1 = new RegionImpl(1, 2, 6, 4);
	Region r2 = new RegionImpl(0, 5, 7, 7);
	Region r3 = new RegionImpl(0, 3, 4, 5);
	Region r4 = new RegionImpl(7, 1, 8, 5);
	Region r5 = new RegionImpl(0, -3, 4, 5);
	Region r6 = new RegionImpl(-2, 0, 5, 6);
	Region r7 = new RegionImpl(0, 5, 3, 6);
	
	ROIObserver o1 = new ROIObserverImpl();
	ROIObserver o2 = new ROIObserverImpl();
	ROIObserver o3 = new ROIObserverImpl();
	ROIObserver o4 = new ROIObserverImpl();
	
	Pixel red = new ColorPixel(1, 0, 0);
	Pixel green = new ColorPixel(0, 1, 0);
	Pixel blue = new ColorPixel(0, 0, 1);
	Pixel orange = new ColorPixel(0.9, 0.6, 0.1);
	Pixel yellow = new ColorPixel(0.9, 1, 0.1);
	Pixel randomColor = new ColorPixel(0.545, 0.65, 0.332);
	
	Pixel[][] randomPicture = { { randomColor, red, blue }, { randomColor, yellow, orange },
			{ randomColor, randomColor, green } };
	Pixel[][] rainbowPicture = { { red, red, red, red, red }, { green, green, green, green, green },
			{ blue, blue, blue, blue, blue }, { orange, orange, orange, orange, orange },
			{ yellow, yellow, yellow, yellow, yellow } };
	Pixel[][] redPicture = { { red, red, red }, { red, red, red } };
	Pixel[][] bluePicture = { { blue, blue, blue, blue, blue, blue, blue, blue, blue}, 
			{ blue, blue, blue, blue, blue, blue, blue, blue, blue},  
			{blue, blue, blue, blue, blue, blue, blue, blue, blue},  
			{blue, blue, blue, blue, blue, blue, blue, blue, blue},  
			{blue, blue, blue, blue, blue, blue, blue, blue, blue} };
	Pixel[][] greenPicture = { { green, green, green, green, green, green, green, green, green}, 
			{ green, green, green, green, green, green, green, green, green}, 
			{ green, green, green, green, green, green, green, green, green}, 
			{ green, green, green, green, green, green, green, green, green}, 
			{ green, green, green, green, green, green, green, green, green} };
	
	Picture rnbwPicture = new MutablePixelArrayPicture(rainbowPicture, "rainbow picture");
	Picture rdPicture = new MutablePixelArrayPicture(redPicture, "red picture");
	Picture rndmPicture = new MutablePixelArrayPicture(randomPicture, "random picture");
	Picture blPicture = new MutablePixelArrayPicture(bluePicture, "blue picture");
	Picture grnPicture = new MutablePixelArrayPicture(greenPicture, "green picture");
	Picture im_rnbwPicture = new ImmutablePixelArrayPicture(rainbowPicture, "immutable rainbow picture");
	Picture im_rdPicture = new ImmutablePixelArrayPicture(redPicture, "immutable red picture");
	Picture im_rndmPicture = new ImmutablePixelArrayPicture(randomPicture, "immutable random picture");
	Picture im_blPicture = new ImmutablePixelArrayPicture(bluePicture, "immutable blue picture");
	Picture im_grnPicture = new ImmutablePixelArrayPicture(greenPicture, "immutable green picture");

	
	@Test
	public void testRegionIntersect() {
		try {
			Region r = r1.intersect(r2);
			fail("no intersection should cause exception");
		}
		catch (NoIntersectionException e) {
		}
		try {
			Region r = r1.intersect(r4);
			fail("no intersection should cause exception");
		}
		catch (NoIntersectionException e) {
		}
		try {
			Region r = r1.intersect(null);
			fail("null input should cause exception");
		}
		catch (NoIntersectionException e) {
		}
		try {
			Region intersect = r1.intersect(r3);
			Region test = new RegionImpl(1, 3, 4, 4);
			assertEquals(intersect.getTop(), test.getTop());
		}catch(NoIntersectionException e){
			
		}
		try {
			Region intersect = r2.intersect(r4);
			Region test = new RegionImpl(7, 5, 7, 5);
			assertEquals(intersect.getTop(), test.getTop());
		}catch(NoIntersectionException e){
			
		}
		try {
			Region intersect = r5.intersect(r4);
			Region test = new RegionImpl(7, 1, 4, 5);
			assertEquals(intersect.getTop(), test.getTop());
		}catch(NoIntersectionException e){
			
		}
	}
	@Test
	public void testRegisterObserver() {
		ObservablePicture op1 = new ObservablePictureImpl(rnbwPicture);
		System.out.println("r1 r2: "+ObservablePictureImpl.hasIntersect(r1,r2));
		System.out.println("r1 r3: "+ObservablePictureImpl.hasIntersect(r1,r3));
		System.out.println("r1 r4: "+ObservablePictureImpl.hasIntersect(r1,r4));
		
		op1.registerROIObserver(o1, r1);
		op1.registerROIObserver(o2, r1);
		op1.registerROIObserver(o3, r2);
		op1.registerROIObserver(o4, r3);
		op1.registerROIObserver(o1, r4);
		op1.registerROIObserver(o1, r1);
		
		ROIObserver[] found_observers = op1.findROIObservers(r1);
		assertEquals(4,found_observers.length);
		assertEquals(found_observers[0], o1);
		assertEquals(found_observers[1], o2);
		assertEquals(found_observers[2], o4);
		assertEquals(found_observers[3], o1);
		
		
	}
	
	@Test 
	public void testUnregisterObserver() {
		
		ObservablePicture op1 = new ObservablePictureImpl(rnbwPicture);
		System.out.println("r1 r2: "+ObservablePictureImpl.hasIntersect(r1,r2));
		System.out.println("r1 r3: "+ObservablePictureImpl.hasIntersect(r1,r3));
		System.out.println("r1 r4: "+ObservablePictureImpl.hasIntersect(r1,r4));
		op1.registerROIObserver(o1, r1);
		op1.registerROIObserver(o2, r1);
		op1.registerROIObserver(o3, r1);
		op1.registerROIObserver(o4, r3);
		op1.registerROIObserver(o1, r4);
		op1.registerROIObserver(o1, r1);
		op1.registerROIObserver(o4, r7);
		
		
		op1.unregisterROIObserver(o1);
		
		ROIObserver[] found_observers = op1.findROIObservers(r1);
		assertEquals(3,found_observers.length);
		assertEquals(found_observers[0], o2);
		assertEquals(found_observers[1], o3);
		assertEquals(found_observers[2], o4);
		
		op1.unregisterROIObservers(r1);
		
		ROIObserver[] found_observers2 = op1.findROIObservers(r7);
		assertEquals(1,found_observers2.length);
		assertEquals(found_observers2[0], o4);
		

	}
	}




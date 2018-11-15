package a6test.saumil98;

import static org.junit.Assert.*;

import a6test.saumil98.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import a6.*;

public class A6Tests {
	
	@Test
	public void regionIntersectTest() throws NoIntersectionException {
		
		Region a = new RegionImpl(0,0,20,20);
		Region b = new RegionImpl(0,0,15,15);
		Region c = new RegionImpl(0,0,10,10);
		Region d = new RegionImpl(0,0,5,5);
		Region e = new RegionImpl(0,0,2,2);
		Region f = new RegionImpl(5,2,8,5);
		Region g = new RegionImpl(1,0,3,2);
		Region h = new RegionImpl(4,1,7,8);
		Region i = new RegionImpl(1,2,7,4);
		Region r1 = new RegionImpl(0, 0, 5, 5);
		Region r2 = new RegionImpl(2, 2, 6, 6);
		
		Region intersectOne = a.intersect(b);
		
		assertEquals(0, intersectOne.getLeft());
		assertEquals(0, intersectOne.getTop());
		assertEquals(15, intersectOne.getRight());
		assertEquals(15, intersectOne.getBottom());
		
		Region intersectTwo = e.intersect(c);
		
		assertEquals(0, intersectTwo.getLeft());
		assertEquals(0, intersectTwo.getTop());
		assertEquals(2, intersectTwo.getRight());
		assertEquals(2, intersectTwo.getBottom());
		
		Region intersectThree = d.intersect(b);
		
		assertEquals(0, intersectThree.getLeft());
		assertEquals(0, intersectThree.getTop());
		assertEquals(5, intersectThree.getRight());
		assertEquals(5, intersectThree.getBottom());
		
		Region intersectFour = f.intersect(d);
		
		assertEquals(5, intersectFour.getLeft());
		assertEquals(2, intersectFour.getTop());
		assertEquals(5, intersectFour.getRight());
		assertEquals(5, intersectFour.getBottom());
		
		Region intersectFive = e.intersect(g);
		
		assertEquals(1, intersectFive.getLeft());
		assertEquals(0, intersectFive.getTop());
		assertEquals(2, intersectFive.getRight());
		assertEquals(2, intersectFive.getBottom());
		
		Region intersectSix = f.intersect(h); 
		
		assertEquals(5, intersectSix.getLeft());
		assertEquals(2, intersectSix.getTop());
		assertEquals(7, intersectSix.getRight());
		assertEquals(5, intersectSix.getBottom());
		
		Region intersectSeven = g.intersect(i);
		
		assertEquals(1, intersectSeven.getLeft());
		assertEquals(2, intersectSeven.getTop());
		assertEquals(3, intersectSeven.getRight());
		assertEquals(2, intersectSeven.getBottom());
		
		Region intersectEigth = r1.intersect(r2);
		
		assertEquals(2, intersectEigth.getLeft());
		assertEquals(2, intersectEigth.getTop());
		assertEquals(5, intersectEigth.getRight());
		assertEquals(5, intersectEigth.getBottom());
		
	}
	
	@Test
	public void regionUnionTest() {
		
		Region a = new RegionImpl(0,0,20,20);
		Region b = new RegionImpl(0,0,15,15);
		Region c = new RegionImpl(0,0,10,10);
		Region d = new RegionImpl(0,0,5,5);
		Region e = new RegionImpl(0,0,2,2);
		Region f = new RegionImpl(5,2,8,5);
		Region g = new RegionImpl(1,0,3,2);
		Region h = new RegionImpl(4,1,7,8);
		Region i = new RegionImpl(1,2,7,4);
		
		
		Region intersectOne = a.union(b);
		
		assertEquals(0, intersectOne.getLeft());
		assertEquals(0, intersectOne.getTop());
		assertEquals(20, intersectOne.getRight());
		assertEquals(20, intersectOne.getBottom());
		
		Region intersectTwo = e.union(c);
		
		assertEquals(0, intersectTwo.getLeft());
		assertEquals(0, intersectTwo.getTop());
		assertEquals(10, intersectTwo.getRight());
		assertEquals(10, intersectTwo.getBottom());
		
		Region intersectThree = d.union(b);
		
		assertEquals(0, intersectThree.getLeft());
		assertEquals(0, intersectThree.getTop());
		assertEquals(15, intersectThree.getRight());
		assertEquals(15, intersectThree.getBottom());
		
		Region intersectFour = f.union(d);
		
		assertEquals(0, intersectFour.getLeft());
		assertEquals(0, intersectFour.getTop());
		assertEquals(8, intersectFour.getRight());
		assertEquals(5, intersectFour.getBottom());
		
		Region intersectFive = e.union(g);
		
		assertEquals(0, intersectFive.getLeft());
		assertEquals(0, intersectFive.getTop());
		assertEquals(3, intersectFive.getRight());
		assertEquals(2, intersectFive.getBottom());
		
		Region intersectSix = f.union(h); 
		
		assertEquals(4, intersectSix.getLeft());
		assertEquals(1, intersectSix.getTop());
		assertEquals(8, intersectSix.getRight());
		assertEquals(8, intersectSix.getBottom());
		
		Region intersectSeven = g.union(i);
		
		assertEquals(1, intersectSeven.getLeft());
		assertEquals(0, intersectSeven.getTop());
		assertEquals(7, intersectSeven.getRight());
		assertEquals(4, intersectSeven.getBottom());
	}
	
	@Test
	public void testObservablePictureFindROIObservers() {
		
		Pixel pixelArr[][] = new Pixel[20][20];
		
		for (int w = 0; w < 20; w++) {
			for (int h = 0; h < 20; h++) {
				pixelArr[w][h] = new ColorPixel(0.0, 0.0, 1.0);
			}
		}
		
		Picture picture = new MutablePixelArrayPicture(pixelArr, "20x20 Blue Picture");	
	
		ObservablePicture picOfInterest = new ObservablePictureImpl(picture);
		
		ROIObserver observer1 = new ROIObserverImpl();
		ROIObserver observer2 = new ROIObserverImpl();
		ROIObserver observer3 = new ROIObserverImpl();
		ROIObserver observer4 = new ROIObserverImpl();
		ROIObserver observer5 = new ROIObserverImpl();
		
		Region one = new RegionImpl(0,0,20,20);
		Region two = new RegionImpl(10,10,20,20);	
		Region three = new RegionImpl(10,10,15,15);	
		Region four = new RegionImpl(5,5,8,8);
		
		picOfInterest.registerROIObserver(observer1, one);
		picOfInterest.registerROIObserver(observer2, two);
		picOfInterest.registerROIObserver(observer2, three);
		picOfInterest.registerROIObserver(observer3, four);
		
		assertEquals(3, picOfInterest.findROIObservers(new RegionImpl(10,10,10,10)).length);
		assertEquals(1, picOfInterest.findROIObservers(new RegionImpl(0,0,0,0)).length);
		assertEquals(2, picOfInterest.findROIObservers(new RegionImpl(8,8,8,8)).length);
		assertEquals(3, picOfInterest.findROIObservers(new RegionImpl(15,15,15,15)).length);
		assertEquals(4, picOfInterest.findROIObservers(new RegionImpl(0,0,20,20)).length);

		picOfInterest.unregisterROIObserver(observer2);
		
		assertEquals(1, picOfInterest.findROIObservers(new RegionImpl(15,15,15,15)).length);
		assertEquals(1, picOfInterest.findROIObservers(new RegionImpl(10,10,10,10)).length);
		assertEquals(2, picOfInterest.findROIObservers(new RegionImpl(5,5,5,5)).length);
		assertEquals(1, picOfInterest.findROIObservers(new RegionImpl(12,11,13,12)).length);
		assertEquals(1, picOfInterest.findROIObservers(new RegionImpl(18,19,19,20)).length);
		
		picOfInterest.registerROIObserver(observer2, two);
		picOfInterest.registerROIObserver(observer2, three);
		
		assertEquals(3, picOfInterest.findROIObservers(new RegionImpl(15,15,15,15)).length);
		assertEquals(3, picOfInterest.findROIObservers(new RegionImpl(10,10,10,10)).length);
		assertEquals(2, picOfInterest.findROIObservers(new RegionImpl(5,5,5,5)).length);
		assertEquals(3, picOfInterest.findROIObservers(new RegionImpl(12,11,13,12)).length);
		assertEquals(2, picOfInterest.findROIObservers(new RegionImpl(18,19,19,20)).length);
		
		picOfInterest.unregisterROIObservers(one);
		
		assertEquals(0, picOfInterest.findROIObservers(new RegionImpl(0,0,0,0)).length);
		assertEquals(0, picOfInterest.findROIObservers(new RegionImpl(10,10,10,10)).length);
		assertEquals(0, picOfInterest.findROIObservers(new RegionImpl(5,5,5,5)).length);
		assertEquals(0, picOfInterest.findROIObservers(new RegionImpl(12,11,13,12)).length);
		assertEquals(0, picOfInterest.findROIObservers(new RegionImpl(18,19,19,20)).length);
		
		picOfInterest.registerROIObserver(observer1, one);
		picOfInterest.registerROIObserver(observer2, two);
		picOfInterest.registerROIObserver(observer2, three);
		picOfInterest.registerROIObserver(observer3, four);
		
		picOfInterest.unregisterROIObservers(new RegionImpl(0,0,0,0));
		
		assertEquals(0, picOfInterest.findROIObservers(new RegionImpl(0,0,0,0)).length);
		assertEquals(2, picOfInterest.findROIObservers(new RegionImpl(10,10,10,10)).length);
		assertEquals(1, picOfInterest.findROIObservers(new RegionImpl(5,5,5,5)).length);
		assertEquals(2, picOfInterest.findROIObservers(new RegionImpl(12,11,13,12)).length);
		assertEquals(1, picOfInterest.findROIObservers(new RegionImpl(18,19,19,20)).length);

		picOfInterest.registerROIObserver(observer1, one);
		picOfInterest.registerROIObserver(observer2, one);
		
		assertEquals(2, picOfInterest.findROIObservers(new RegionImpl(0,0,0,0)).length);
		assertEquals(4, picOfInterest.findROIObservers(new RegionImpl(10,10,10,10)).length);
		assertEquals(3, picOfInterest.findROIObservers(new RegionImpl(5,5,5,5)).length);
		assertEquals(4, picOfInterest.findROIObservers(new RegionImpl(12,11,13,12)).length);
		assertEquals(3, picOfInterest.findROIObservers(new RegionImpl(18,19,19,20)).length);
		
		picOfInterest.registerROIObserver(observer3, two);
		
		assertEquals(5, picOfInterest.findROIObservers(new RegionImpl(10,10,10,10)).length);
		assertEquals(2, picOfInterest.findROIObservers(new RegionImpl(0,0,0,0)).length);
		assertEquals(3, picOfInterest.findROIObservers(new RegionImpl(8,8,8,8)).length);
		assertEquals(5, picOfInterest.findROIObservers(new RegionImpl(15,15,15,15)).length);
		assertEquals(6, picOfInterest.findROIObservers(new RegionImpl(0,0,20,20)).length);
		
		Region five = new RegionImpl(2,3,7,7);
		Region six = new RegionImpl(0,10,6,20);
		
		picOfInterest.registerROIObserver(observer4, five);
		picOfInterest.registerROIObserver(observer5, six);
		
		assertEquals(8, picOfInterest.findROIObservers(new RegionImpl(5,5,10,10)).length);
		assertEquals(2, picOfInterest.findROIObservers(new RegionImpl(0,0,1,1)).length);
		assertEquals(4, picOfInterest.findROIObservers(new RegionImpl(4,5,8,8)).length);
		assertEquals(5, picOfInterest.findROIObservers(new RegionImpl(12,10,15,15)).length);
		assertEquals(2, picOfInterest.findROIObservers(new RegionImpl(16,5,20,5)).length);
		
	}
	
	@Test
	public void testSuspendResume() {
		
		// New Test 1
		
		Pixel pixelArr[][] = new Pixel[15][15];
		
		for (int w = 0; w < 15; w++) {
			for (int h = 0; h < 15; h++) {
				pixelArr[w][h] = new ColorPixel(0.0, 0.0, 1.0);
			}
		}
		
		Picture picture = new MutablePixelArrayPicture(pixelArr, "15x15 Blue Picture");	
	
		ObservablePicture picOfInterest = new ObservablePictureImpl(picture);
		
		ROIObserverImpl observer1 = new ROIObserverImpl();
		ROIObserverImpl observer2 = new ROIObserverImpl();
		ROIObserverImpl observer3 = new ROIObserverImpl();
		
		Region one = new RegionImpl(0,0,20,20);
		Region two = new RegionImpl(10,10,20,20);	
		Region three = new RegionImpl(10,10,15,15);	
		Region four = new RegionImpl(5,5,8,8);
		
		picOfInterest.registerROIObserver(observer1, one);
		picOfInterest.registerROIObserver(observer2, two);
		picOfInterest.registerROIObserver(observer2, three);
		picOfInterest.registerROIObserver(observer3, four);
		
		picOfInterest.suspendObservable();
		
		picOfInterest.paint(0, 0, new ColorPixel(0.0,1.0,0.0));
		picOfInterest.paint(5, 5, new ColorPixel(0.0,1.0,0.0));
		picOfInterest.paint(10, 10, new ColorPixel(0.0,1.0,0.0));
		picOfInterest.paint(14, 14, new ColorPixel(0.0,1.0,0.0));
		picOfInterest.paint(4, 4, new ColorPixel(0.0,1.0,0.0));
		
		assertEquals(0, observer1.getNotifiedCounter());
		assertEquals(0, observer2.getNotifiedCounter());
		assertEquals(0, observer2.getNotifiedCounter());
		assertEquals(0, observer3.getNotifiedCounter());
		
		picOfInterest.resumeObservable();
			
		assertEquals(1, observer1.getNotifiedCounter());
		assertEquals(2, observer2.getNotifiedCounter());
		assertEquals(1, observer3.getNotifiedCounter());
		
		observer1.resetCounter();
		observer2.resetCounter();
		observer3.resetCounter();
		
		picOfInterest.paint(2, 2, new ColorPixel(0.0,1.0,0.0));
		
		assertEquals(1, observer1.getNotifiedCounter());
		assertEquals(0, observer2.getNotifiedCounter());
		assertEquals(0, observer3.getNotifiedCounter());
		
		observer1.resetCounter();
		observer2.resetCounter();
		observer3.resetCounter();
		
		picOfInterest.paint(5, 5, new ColorPixel(0.0,1.0,0.0));

		assertEquals(1, observer1.getNotifiedCounter());
		assertEquals(0, observer2.getNotifiedCounter());
		assertEquals(1, observer3.getNotifiedCounter());
		
		observer1.resetCounter();
		observer2.resetCounter();
		observer3.resetCounter();
		
		picOfInterest.paint(14, 2, new ColorPixel(0.0,1.0,0.0));

		assertEquals(1, observer1.getNotifiedCounter());
		assertEquals(0, observer2.getNotifiedCounter());
		assertEquals(0, observer3.getNotifiedCounter());
		
		observer1.resetCounter();
		observer2.resetCounter();
		observer3.resetCounter();
		
		// New Test 2
		
		Pixel red = new ColorPixel(1, 0, 0);
		Pixel green = new ColorPixel(0, 1, 0);
		
		Pixel[][] redPicture = { { red, red, red, red }, { red, red, red, red }, 
				                 { red, red, red, red }, { red, red, red, red } };
		
		Picture redMutablePicture = new MutablePixelArrayPicture(redPicture, "4x4 Red Picture");

		Region region0000 = new RegionImpl(0, 0, 0, 0);
		Region region2133 = new RegionImpl(2, 1, 3, 3);
		Region region2222 = new RegionImpl(2, 2, 2, 2);
		Region region3031 = new RegionImpl(3, 0, 3, 1);
		
		ObservablePicture observableRedMutablePicture = new ObservablePictureImpl(redMutablePicture);
		
		ROIObserverImpl observer1a = new ROIObserverImpl();
		ROIObserverImpl observer2b = new ROIObserverImpl();
		ROIObserverImpl observer3c = new ROIObserverImpl();

		observableRedMutablePicture.registerROIObserver(observer1a, region0000);
		observableRedMutablePicture.registerROIObserver(observer2b, region2222);
		observableRedMutablePicture.registerROIObserver(observer2b, region2222);
		observableRedMutablePicture.registerROIObserver(observer2b, region2133);
		observableRedMutablePicture.registerROIObserver(observer3c, region3031);

		
		observableRedMutablePicture.suspendObservable();
		
		observableRedMutablePicture.paint(0, 0, green);		
		observableRedMutablePicture.paint(1, 1, green);
		
		assertEquals(0, observer1a.getNotifiedCounter());
		assertEquals(0, observer2b.getNotifiedCounter());
		assertEquals(0, observer3c.getNotifiedCounter());
		
		observableRedMutablePicture.resumeObservable();
		
		assertEquals(1, observer1a.getNotifiedCounter());
		assertEquals(0, observer2b.getNotifiedCounter());
		assertEquals(0, observer3c.getNotifiedCounter());
		
		observer1a.resetCounter();
		observer2b.resetCounter();
		observer3c.resetCounter();
		
		observableRedMutablePicture.suspendObservable();
		
		observableRedMutablePicture.paint(2, 2, green);
		
		assertEquals(0, observer1a.getNotifiedCounter());
		assertEquals(0, observer2b.getNotifiedCounter());
		assertEquals(0, observer3c.getNotifiedCounter());
		
		observableRedMutablePicture.resumeObservable();
		
		assertEquals(0, observer1a.getNotifiedCounter());
		assertEquals(3, observer2b.getNotifiedCounter());
		assertEquals(0, observer3c.getNotifiedCounter());
		
		// New Test 3
		
		Pixel redTwo = new ColorPixel(1, 0, 0);
		Pixel blue = new ColorPixel(0, 0, 1);

		Picture blueMutable4by4 = new MutablePixelArrayPicture(new Pixel[][] { { blue, blue, blue, blue },
				{ blue, blue, blue, blue }, { blue, blue, blue, blue }, { blue, blue, blue, blue } }, "Blue");
		
		Region region2122 = new RegionImpl(2, 1, 2, 2);
		Region region3333 = new RegionImpl(3, 3, 3, 3);
		
		ROIObserverImpl observer1aa = new ROIObserverImpl();
		ROIObserverImpl observer2bb = new ROIObserverImpl();
		ROIObserverImpl observer3cc = new ROIObserverImpl();
		
		ObservablePicture observableBlueMutable4by4 = new ObservablePictureImpl(blueMutable4by4);

		observableBlueMutable4by4.registerROIObserver(observer1aa, region0000);
		observableBlueMutable4by4.registerROIObserver(observer1aa, region3333);
		observableBlueMutable4by4.registerROIObserver(observer2bb, region2122);
		observableBlueMutable4by4.registerROIObserver(observer3cc, region3031);

		observableBlueMutable4by4.suspendObservable();

		observableBlueMutable4by4.paint(0, 0, redTwo);
		observableBlueMutable4by4.paint(1, 1, redTwo);
		
		assertEquals(0, observer1aa.getNotifiedCounter());
		assertEquals(0, observer2bb.getNotifiedCounter());
		assertEquals(0, observer3cc.getNotifiedCounter());

		observableBlueMutable4by4.resumeObservable();
		
		assertEquals(1, observer1aa.getNotifiedCounter());
		assertEquals(0, observer2bb.getNotifiedCounter());
		assertEquals(0, observer3cc.getNotifiedCounter());
		
		observer1aa.resetCounter();
		observer2bb.resetCounter();
		observer3cc.resetCounter();
		
		observableBlueMutable4by4.suspendObservable();
		observableBlueMutable4by4.suspendObservable();

		observableBlueMutable4by4.paint(0, 0, redTwo);
		observableBlueMutable4by4.paint(2, 0, redTwo);
		observableBlueMutable4by4.paint(0, 3, redTwo);

		observableBlueMutable4by4.resumeObservable();
		
		assertEquals(1, observer1aa.getNotifiedCounter());
		assertEquals(1, observer2bb.getNotifiedCounter());
		assertEquals(0, observer3cc.getNotifiedCounter());

		observableBlueMutable4by4.resumeObservable();
		assertEquals(2, observer1aa.getNotifiedCounter());
		assertEquals(2, observer2bb.getNotifiedCounter());
		assertEquals(0, observer3cc.getNotifiedCounter());
		
		
	}
	
}

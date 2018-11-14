package a6test.itai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.jupiter.api.Test;

import a6.ColorPixel;
import a6.MutablePixelArrayPicture;
import a6.NoIntersectionException;
import a6.ObservablePicture;
import a6.ObservablePictureImpl;
import a6.Picture;
import a6.ROIObserver;
import a6.Region;
import a6.RegionImpl;

class A6Tests {

	@Test
	void testRegionIntersectAndUnion() {
		//Regional Intersect
		Region r1 = new RegionImpl(0, 0, 5, 5);
		Region r2 = new RegionImpl(2, 2, 6, 6);
		Region r3 = new RegionImpl(1, 2, 3, 4);
		Region r4 = new RegionImpl(3, 3, 5, 4);
		Region r5 = new RegionImpl(0, 0, 10, 10);
		Region r6 = new RegionImpl(3, 3, 4, 7);
		Region r8 = new RegionImpl(6, 6, 8, 8);
		
		try {
			assertEquals((RegionImpl) r1.intersect(r2), new RegionImpl(2, 2, 5 ,5));
			assertEquals((RegionImpl) r1.intersect(r3), new RegionImpl(1, 2, 3 ,4));
			assertEquals((RegionImpl) r5.intersect(r6), r6);
			assertEquals((RegionImpl) r6.intersect(r4), new RegionImpl(3, 3, 4 ,4));
		} catch (NoIntersectionException e) {
			fail("");
		}
		
		try {
			r8.intersect(r1);
			fail();
		}catch (NoIntersectionException e) {
			
		}
		//Union
		assertEquals((RegionImpl) r1.union(r2), new RegionImpl(0, 0, 6, 6));
		assertEquals((RegionImpl) r5.union(r2), r5);
		assertEquals((RegionImpl) r8.union(r6), new RegionImpl(3, 3, 8, 8));
	}
	@Test
	void testROIObserver() {
		Random rGen = new Random();
		ColorPixel[][] pixels = new ColorPixel[10][10];
		ColorPixel paintbrush = new ColorPixel(0.5, 0, 0);
		for(int x = 0; x < pixels.length; x++) {
			for(int y = 0; y < pixels[x].length; y++) {
				pixels[x][y] = new ColorPixel(rGen.nextDouble(), rGen.nextDouble(), rGen.nextDouble());
			}
		}
		
		Picture testPic = new MutablePixelArrayPicture(pixels, "test");
		ObservablePicture observablePic = new ObservablePictureImpl(testPic);
		ROIObserverImpl obs1 = new ROIObserverImpl();
		
		observablePic.registerROIObserver(obs1, new RegionImpl(0, 0, 3, 3));
		observablePic.registerROIObserver(obs1, new RegionImpl(7, 7, 9, 9));
		
		//paint outside region, make sure not notified
		observablePic.paint(4, 5, paintbrush);
		obs1.assertNotNotified();
		//paint inside region, make sure notified
		observablePic.paint(6, 6, 7, 7, paintbrush);
		obs1.assertNotified();
		//suspend observable, make sure not notified
		observablePic.suspendObservable();
		observablePic.paint(6, 6, 7, 7, paintbrush);
		obs1.assertNotNotified();
		observablePic.paint(0, 0, 7, 7, paintbrush);
		obs1.assertNotNotified();
		//resume observable, make sure notified
		observablePic.resumeObservable();
		obs1.assertNotified();
		//make sure suspending and resuming does not automatically notify
		observablePic.suspendObservable();
		observablePic.paint(4, 4, paintbrush);
		observablePic.resumeObservable();
		obs1.assertNotNotified();
		//make sure it finds correct observers
		assertEquals(observablePic.findROIObservers(new RegionImpl(0, 0, 1, 1))[0], obs1);
		assertEquals(observablePic.findROIObservers(new RegionImpl(4, 4, 6, 6)).length, 0);
		//make sure it correctly removes observer in area, but keeps other one
		observablePic.unregisterROIObservers(new RegionImpl(3, 3, 5, 6));
		assertEquals(observablePic.findROIObservers(new RegionImpl(0, 0, 1, 1)).length, 0);
		assertEquals(observablePic.findROIObservers(new RegionImpl(4, 4, 8, 8))[0], obs1);

		
	}
	private class ROIObserverImpl implements ROIObserver{
		private boolean hasBeenNotified;
		
		public ROIObserverImpl() {
			this.hasBeenNotified = false;
		}
		@Override
		public void notify(ObservablePicture picture, Region changed_region) {
			this.hasBeenNotified = true;
		}
		
		public void assertNotified() {
			assertTrue(hasBeenNotified);
			hasBeenNotified = false;
		}
		
		public void assertNotNotified() {
			assertFalse(hasBeenNotified);
		}
		
	}

}

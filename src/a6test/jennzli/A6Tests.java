package a6test.jennzli;

import static org.junit.Assert.*;
import a6.*;

import org.junit.Test;


public class A6Tests {

	Pixel red = new ColorPixel(1, 0, 0);
	Pixel green = new ColorPixel(0, 1, 0);
	Pixel blue = new ColorPixel(0, 0, 1);
	Pixel yellow = new ColorPixel(0.9, 1, 0.1);
	Pixel white = new ColorPixel(1, 1, 1);
	Pixel black = new ColorPixel(0, 0, 0);
	
	@Test
	public void testNotify() {
		Pixel[][] parray01 = new Pixel[10][10];
		Pixel[][] parray02 = new Pixel[10][10];

		for(int x = 0; x < parray01.length; x++) {
			for (int y = 0; y < parray01[0].length; y++) {
				parray01[x][y] = black;
			}
		}
		
		for(int x = 0; x < parray02.length; x++) {
			for (int y = 0; y < parray02[0].length; y++) {
				parray02[x][y] = white;
			}
		}
		
		Picture mutImage = new MutablePixelArrayPicture(parray01, "Black Picture");
		Picture immutImage = new ImmutablePixelArrayPicture(parray02, "White Picture");
		
		ObservablePicture mutable = new ObservablePictureImpl(mutImage);
		ObservablePicture immutable = new ObservablePictureImpl(immutImage);
		
		Region region01 = new RegionImpl(0, 0, 6, 6);
		Region region02 = new RegionImpl(2, 2, 4, 4);
		Region region03 = new RegionImpl(1, 1, 7, 7);
		
		ROIObserverImpl observer01 = new ROIObserverImpl();
		ROIObserverImpl observer02 = new ROIObserverImpl();
		ROIObserverImpl observer03 = new ROIObserverImpl();
		
		mutable.registerROIObserver(observer01, region01);
		mutable.registerROIObserver(observer02, region02);
		mutable.registerROIObserver(observer03, region03);
		
		immutable.registerROIObserver(observer01, region01);
		immutable.registerROIObserver(observer02, region02);
		immutable.registerROIObserver(observer03, region03);

		mutable.paint(0, 0, yellow);
		assertEquals(1, observer01.getCount());
		observer01.clearCount();


		immutable.paint(0, 0, blue);
		assertEquals(1, observer01.getCount());
		observer01.clearCount();
		
		mutable.paint(0, 0, yellow);
		mutable.paint(1, 1, blue);
		assertEquals(2, observer01.getCount());
		assertEquals(0, observer02.getCount());
		assertEquals(1, observer03.getCount());
		observer01.clearCount();
		observer03.clearCount();
		
		immutable.paint(0, 0, yellow);
		immutable.paint(1, 1, blue);
		assertEquals(2, observer01.getCount());
		assertEquals(0, observer02.getCount());
		assertEquals(1, observer03.getCount());
		observer01.clearCount();
		observer03.clearCount();
		
		mutable.paint(0, 0, yellow);
		mutable.paint(1, 1, blue);
		mutable.paint(4, 4, green);
		assertEquals(3, observer01.getCount());
		assertEquals(1, observer02.getCount());
		assertEquals(2, observer03.getCount());
		observer01.clearCount();
		observer02.clearCount();
		observer03.clearCount();
		
		mutable.suspendObservable();
		mutable.paint(0, 0, yellow);
		mutable.paint(1, 1, blue);
		mutable.paint(4, 4, green);
		mutable.resumeObservable();
		assertEquals(1, observer01.getCount());
		assertEquals(1, observer02.getCount());
		assertEquals(1, observer03.getCount());
		observer01.clearCount();
		observer02.clearCount();
		observer03.clearCount();
		
		mutable.suspendObservable();
		mutable.paint(0, 0, yellow);
		mutable.paint(1, 1, blue);
		mutable.paint(4, 4, green);
		assertEquals(0, observer01.getCount());
		assertEquals(0, observer02.getCount());
		assertEquals(0, observer03.getCount());
		mutable.resumeObservable();
		assertEquals(1, observer01.getCount());
		assertEquals(1, observer02.getCount());
		assertEquals(1, observer03.getCount());
		observer01.clearCount();
		observer02.clearCount();
		observer03.clearCount(); 
		
		mutable.paint(0, 0, 5, 5, yellow);
		mutable.paint(1, 1, 2, 2, blue);
		mutable.paint(7, 7, 8, 8, green);
		assertEquals(2, observer01.getCount());
		assertEquals(2, observer02.getCount());
		assertEquals(3, observer03.getCount());
		observer01.clearCount();
		observer02.clearCount();
		observer03.clearCount();
		
		immutable.paint(0, 0, 5, 5, yellow);
		immutable.paint(1, 1, 2, 2, blue);
		immutable.paint(7, 7, 8, 8, green);
		assertEquals(2, observer01.getCount());
		assertEquals(2, observer02.getCount());
		assertEquals(3, observer03.getCount());
		observer01.clearCount();
		observer02.clearCount();
		observer03.clearCount();
	}

}

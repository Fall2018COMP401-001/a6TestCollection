package a6test.iftekhar;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.ColorPixel;
import a6.MutablePixelArrayPicture;
import a6.NoIntersectionException;
import a6.ObservablePicture;
import a6.ObservablePictureImpl;
import a6.Picture;
import a6.PictureImpl;
import a6.Pixel;
import a6.ROIObserver;
import a6.Region;
import a6.RegionImpl;

public class A6Tests {
	Region a = new RegionImpl(0, 0, 5, 5);
	Region b = new RegionImpl(6, 6, 10, 10);
	Region c = new RegionImpl(3, 3, 7, 7);

	// pixel colors
	Pixel RED = new ColorPixel(1, 0, 0);
	Pixel GREEN = new ColorPixel(0, 1, 0);
	Pixel BLUE = new ColorPixel(0, 0, 1);

	Picture REDp = new MutablePixelArrayPicture(makeSolidPixelArray(10,10, RED), "Red boi");
	Picture GREENp = new MutablePixelArrayPicture(makeSolidPixelArray(10,10, GREEN), "Green boi");
	
	
	@Test
	public void basicRegionIntersectTest() {
		try {
			Region failReg = a.intersect(b);
			fail("a and b should not intersect!");
		} catch (NoIntersectionException e) {
		}
		
		Region intersect = null;
		try {
			intersect = a.intersect(c);
		} catch (NoIntersectionException e) {
			fail("a and c should intersect.");
		}
		
		// check the passReg
		assertEquals(3, intersect.getLeft());
		assertEquals(3, intersect.getTop());
		assertEquals(5, intersect.getRight());
		assertEquals(5, intersect.getBottom());
	}

	@Test
	public void basicROIObserverNotificationTest() {
		ROIObserver observer = new ROIObserverImpl();
		ObservablePicture observablePic = new ObservablePictureImpl(REDp);
		observablePic.registerROIObserver(observer, a);
		observablePic.paint(0, 0, GREEN);
	}

	private static Pixel[][] makeSolidPixelArray(int width, int height, Pixel p) {
		Pixel[][] parray = new Pixel[width][height];
		for (int x=0; x<width; x++) {
			for (int y=0; y<height; y++) {
				parray[x][y] = p;
			}
		}
		return parray;
	}
}

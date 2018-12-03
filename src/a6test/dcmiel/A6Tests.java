package a6test.dcmiel;

import static org.junit.Assert.*;

import org.junit.Test;
import a6.*;

public class A6Tests {
	
	Region a = new RegionImpl(1, 1, 3, 3);
	Region b = new RegionImpl(2, 2, 4, 4);

	@Test
	public void testRegionImpl() throws NoIntersectionException {
		Region intersection = new RegionImpl(2, 2, 3, 3);
		Region tester = a.intersect(b);
		
		assertEquals(intersection.getBottom(), tester.getBottom());
		assertEquals(intersection.getTop(), tester.getTop());
		assertEquals(intersection.getLeft(), tester.getLeft());
		assertEquals(intersection.getRight(), tester.getRight());
	}
	
	@Test
	public void testUnregisterMethods() {
		
		Pixel red = new ColorPixel(1, 0, 0);
		Pixel[][] redArray = new Pixel[5][5];
		
		for(int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				redArray[x][y] = red;
			}
		}
		
		Picture redPicture = new MutablePixelArrayPicture(redArray, "red picture");
		ObservablePictureImpl observe = new ObservablePictureImpl(redPicture);
		ROIObserver observer1 = new ROIObserverImpl();
	
		observe.registerROIObserver(observer1, a);
		assertEquals(observe.getObserverArray().size(), observe.getRegionArray().size());
		assertEquals(observe.getObserverArray().size(), 1);
		assertEquals(observe.getRegionArray().size(), 1);
		
		observe.unregisterROIObserver(observer1);
		assertEquals(observe.getObserverArray().size(), 0);
		assertEquals(observe.getRegionArray().size(), 0);

	}
}

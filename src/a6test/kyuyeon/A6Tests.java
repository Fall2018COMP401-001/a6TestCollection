package a6test.kyuyeon;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.ColorPixel;
import a6.MutablePixelArrayPicture;
import a6.Picture;
import a6.Pixel;
import a6.*;

public class A6Tests{



	@Test
	public void kyu_notifyCountTest() {
		Pixel[][] parray = new Pixel[8][8];

		for (int x =0; x <8; x ++) {
			for (int y =0; y<8; y++) {
				parray[x][y] = new ColorPixel(0,0,0);
			}
		}

		Picture picture = new MutablePixelArrayPicture(parray, "My caption");
		ObservablePictureImpl as = new ObservablePictureImpl(picture);
		ROIObserverImpl observer1 = new ROIObserverImpl();
		ROIObserverImpl observer2 = new ROIObserverImpl();
		ROIObserverImpl observer3 = new ROIObserverImpl();
		ROIObserverImpl observer4 = new ROIObserverImpl();
		Region a = new RegionImpl(0, 0, 2, 2);
		Region b = new RegionImpl(1, 1, 3, 3);
		Region c = new RegionImpl(3, 3, 5, 5);
		Region d = new RegionImpl(6, 6, 7, 7);
		as.registerROIObserver(observer1, a);
		as.registerROIObserver(observer2, b);
		as.registerROIObserver(observer3, c);
		as.registerROIObserver(observer4, d);

		as.paint(2, 2, 4, 4, new ColorPixel(0.3,0.4,0.5));
		as.paint(3, 3, new ColorPixel(0.4,0.5,0.6));
		as.paint(5, 5, new ColorPixel(0.1,0.2,0.3));
		assertEquals(1, observer1.getCount());
		assertEquals(2, observer2.getCount());
		assertEquals(3, observer3.getCount());
		assertEquals(0, observer4.getCount());
	}

	@Test
	public void kyu_unregisterObservTest() {
		Pixel[][] parray = new Pixel[8][8];

		for (int x =0; x <8; x ++) {
			for (int y =0; y<8; y++) {
				parray[x][y] = new ColorPixel(0,0,0);
			}
		}

		Picture picture = new MutablePixelArrayPicture(parray, "My caption");
		ObservablePictureImpl as = new ObservablePictureImpl(picture);
		ROIObserverImpl observer1 = new ROIObserverImpl();
		ROIObserverImpl observer2 = new ROIObserverImpl();
		ROIObserverImpl observer3 = new ROIObserverImpl();
		ROIObserverImpl observer4 = new ROIObserverImpl();
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = new RegionImpl(6, 6, 7, 7);
		Region c = new RegionImpl(3, 3, 5, 5);
		Region d = new RegionImpl(2, 2, 3, 3);
		as.registerROIObserver(observer1, a);
		as.registerROIObserver(observer3, c);
		as.registerROIObserver(observer4, d);

		as.unregisterROIObserver(observer2);
		assertEquals(0, as.findROIObservers(b).length);
		as.registerROIObserver(observer2, b);
		assertEquals(observer2, as.findROIObservers(b)[0]);
		assertEquals(1, as.findROIObservers(b).length);

	}

	@Test
	public void kyu_unregisterRegionTest() {
		Pixel[][] parray = new Pixel[8][8];

		for (int x =0; x <8; x ++) {
			for (int y =0; y<8; y++) {
				parray[x][y] = new ColorPixel(0,0,0);
			}
		}

		Picture picture = new MutablePixelArrayPicture(parray, "My caption");
		ObservablePictureImpl as = new ObservablePictureImpl(picture);
		ROIObserverImpl observer1 = new ROIObserverImpl();
		ROIObserverImpl observer2 = new ROIObserverImpl();
		ROIObserverImpl observer3 = new ROIObserverImpl();
		ROIObserverImpl observer4 = new ROIObserverImpl();
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = new RegionImpl(6, 6, 7, 7);
		Region c = new RegionImpl(3, 3, 5, 5);
		Region d = new RegionImpl(2, 2, 3, 3);
		as.registerROIObserver(observer1, a);
		as.registerROIObserver(observer2, b);
		as.registerROIObserver(observer3, c);
		as.registerROIObserver(observer4, d);

		assertEquals(observer2, as.findROIObservers(b)[0]);
		assertEquals(1, as.findROIObservers(b).length);
		as.unregisterROIObservers(b);
		assertEquals(0, as.findROIObservers(b).length);
	}

	@Test
	public void kyu_suspendTest() {
		Pixel[][] parray = new Pixel[8][8];

		for (int x =0; x <8; x ++) {
			for (int y =0; y<8; y++) {
				parray[x][y] = new ColorPixel(0,0,0);
			}
		}

		Picture picture = new MutablePixelArrayPicture(parray, "My caption");
		ObservablePictureImpl as = new ObservablePictureImpl(picture);
		ROIObserverImpl observer1 = new ROIObserverImpl();
		ROIObserverImpl observer2 = new ROIObserverImpl();

		Region a = new RegionImpl(0, 0, 7, 7);
		as.registerROIObserver(observer1, a);

		as.paint(2, 2, 4, 4, new ColorPixel(0.3,0.4,0.5));
		as.paint(3, 3, new ColorPixel(0.4,0.5,0.6));
		as.paint(5, 5, new ColorPixel(0.1,0.2,0.3));
		assertEquals(3, observer1.getCount());

		Region b = new RegionImpl(0, 0, 7, 7);
		as.registerROIObserver(observer2, b);
		as.paint(2, 2, 4, 4, new ColorPixel(0.3,0.4,0.5));
		as.paint(3, 3, new ColorPixel(0.4,0.5,0.6));
		as.suspendObservable();
		as.paint(5, 5, new ColorPixel(0.1,0.2,0.3));
		assertEquals(2, observer2.getCount());
		as.resumeObservable();
		as.paint(5, 5, new ColorPixel(0.1,0.2,0.3));
		assertEquals(3, observer2.getCount());
	}
}


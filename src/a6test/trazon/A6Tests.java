package a6test.trazon;

import org.junit.Test;

import a6.*;
import a6test.trazon.ROIObserverImpl;

import static org.junit.Assert.*;


public class A6Tests {
	@Test
	public void testRegionImplConstructor() {
		try {
			new RegionImpl(3, 2, 2, 3);
			fail("Left cannot be greater than right");
		} catch (IllegalArgumentException e) {
		}
		try {
			new RegionImpl(5, 6, 6, 5);
			fail("Top cannot be greater than bottom");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testRegisterROIObserver() {
		ROIObserver o1 = new ROIObserverImpl(), o2 = new ROIObserverImpl(), o3 = new ROIObserverImpl(),
				o4 = new ROIObserverImpl();
		Region reg1 = new RegionImpl(0, 0, 2, 3), reg2 = new RegionImpl(2, 2, 5, 5), reg3 = new RegionImpl(1, 2, 2, 3);
		ObservablePicture pic = new ObservablePictureImpl(NewMutablePictureWithCaption(767, 362, "test"));

		pic.registerROIObserver(o1, reg1);
		pic.registerROIObserver(o2, reg1);
		pic.registerROIObserver(o1, reg1);
		pic.registerROIObserver(o2, reg2);
		pic.registerROIObserver(o1, reg3);
		pic.registerROIObserver(o3, reg1);

		ROIObserver[] ROIOarray = pic.findROIObservers(reg1);

		assertEquals(ROIOarray[0], o1);
		assertEquals(ROIOarray[1], o2);
		assertEquals(ROIOarray[2], o1);
		assertEquals(ROIOarray[3], o2);
		assertEquals(ROIOarray[4], o1);
		assertEquals(ROIOarray[5], o3);

		pic.registerROIObserver(o4, reg1);
		pic.registerROIObserver(o3, reg2);

		ROIObserver[] rarrayAdded = pic.findROIObservers(reg1);

		assertEquals(rarrayAdded[6], o4);
		assertEquals(rarrayAdded[7], o3);

	}

	@Test
	public void testNullRegionIntersection() {
		Region reg1 = new RegionImpl(10, 10, 20, 20), reg2 = null;
		try {
			reg1.intersect(reg2);
			fail("Intersection cannot be null");
		} catch (NoIntersectionException e) {
		}

	}

	@Test
	public void testNotify() {
		Picture pic = new MutablePixelArrayPicture(MutablePictureArray(10, 10), "test");

		ObservablePicture obspic = new ObservablePictureImpl(pic);

		ROIObserverImpl a = new ROIObserverImpl(), b = new ROIObserverImpl(), c = new ROIObserverImpl(),
				d = new ROIObserverImpl();
		obspic.registerROIObserver(a, new RegionImpl(0, 0, 1, 1));
		obspic.registerROIObserver(b, new RegionImpl(2, 0, 3, 1));
		obspic.registerROIObserver(c, new RegionImpl(0, 2, 1, 3));
		obspic.registerROIObserver(d, new RegionImpl(2, 2, 3, 3));

		assertEquals(a.TimesNotified(), 0);
		assertEquals(b.TimesNotified(), 0);
		assertEquals(c.TimesNotified(), 0);
		assertEquals(d.TimesNotified(), 0);

		obspic.paint(0, 0, NewPixel());

		assertEquals(a.TimesNotified(), 1);
		assertEquals(b.TimesNotified(), 0);
		assertEquals(c.TimesNotified(), 0);
		assertEquals(d.TimesNotified(), 0);
	}

	@Test
	public void testRegionUnionMethod() {
		Region reg1 = new RegionImpl(0, 0, 10, 10), reg2 = new RegionImpl(12, 12, 18, 18), union = reg1.union(reg2);
		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(18, union.getRight());
		assertEquals(18, union.getBottom());

		union = reg2.union(reg1);

		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(18, union.getRight());
		assertEquals(18, union.getBottom());

	}
	

	 static Pixel NewPixel() {
		return new ColorPixel(Math.random(), Math.random(), Math.random());
	}

	 static Pixel[][] MutablePictureArray(int x, int y) {
		Pixel[][] pixel_array = new Pixel[x][y];
		for (int i = 0; i < x; ++i)
			for (int j = 0; j < y; ++j)
				pixel_array[i][j] = NewPixel();
		return pixel_array;
	}

	 static Picture NewMutablePictureWithCaption(int x, int y, String caption) {
		return new MutablePixelArrayPicture(MutablePictureArray(x, y), caption);
	}

}
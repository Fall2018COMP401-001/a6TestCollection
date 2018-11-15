package a6test.jingyz;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

public class A6Tests {
	// Initialize pixel
	Pixel red = new ColorPixel(1, 0, 0);
	Pixel green = new ColorPixel(0, 1, 0);
	Pixel blue = new ColorPixel(0, 0, 1);
	Pixel randomColor = new ColorPixel(0.545, 0.65, 0.332);
	
	// Initialize Pixel 2d Arrays
	Pixel[][] rgbPicture = { { red, red, red }, { green, green, green }, { blue, blue, blue } };
	Pixel[][] greenPicture = { { green, green, green }, { green, green, green }, { green, green, green } };
	Pixel[][] bluePicture = { { blue, blue, blue }, { blue, blue, blue }, { blue, blue, blue } };

	// Initialize Mutable Pixel Array Picture
	Picture rgbMuPic = new MutablePixelArrayPicture(rgbPicture, "Mutable RGB");
	Picture greenMuPic = new MutablePixelArrayPicture(greenPicture, "Mutable green");
	Picture blueMuPic = new MutablePixelArrayPicture(bluePicture, "Mutable blue");
	
	// Initialize Immutable Pixel Array Picture
	Picture rgbImPic = new ImmutablePixelArrayPicture(rgbPicture, "Immutable RGB");
	Picture greenImPic = new ImmutablePixelArrayPicture(greenPicture, "Immutable green");
	Picture blueImPic = new ImmutablePixelArrayPicture(bluePicture, "Immutable blue");
	
	// Initialize region
	Region a = new RegionImpl(1,1,1,1);
	Region b = new RegionImpl(-5,-5,-1,-1);
	Region c = new RegionImpl(0,0,2,2);
	Region d = new RegionImpl(0,0,1,1);
	Region e = new RegionImpl(2,2,2,2);

	// Initialize observable picture
	ObservablePicture rgbMuOb = new ObservablePictureImpl(rgbMuPic);
	ObservablePicture greenMuOb = new ObservablePictureImpl(greenMuPic);
	ObservablePicture blueMuOb = new ObservablePictureImpl(blueMuPic);

	ObservablePicture rgbImOb = new ObservablePictureImpl(rgbImPic);
	ObservablePicture greenImOb = new ObservablePictureImpl(greenImPic);
	ObservablePicture blueImOb = new ObservablePictureImpl(blueImPic);
	
	// Initialize observer
	ROIObserver o1 = new ROIObserverImpl();
	ROIObserver o2 = new ROIObserverImpl();
	ROIObserver o3 = new ROIObserverImpl();
	ROIObserver o4 = new ROIObserverImpl();
	
	@Test
	public void regionConstructorTest() {

		// test when left equals right, top equals bottom
		assertEquals(a.getLeft(), 1);
		assertEquals(a.getTop(), 1);
		assertEquals(a.getRight(), 1);
		assertEquals(a.getBottom(), 1);
		
		// test negative values of four sides
		assertEquals(b.getLeft(), -5);
		assertEquals(b.getTop(), -5);
		assertEquals(b.getRight(), -1);
		assertEquals(b.getBottom(), -1);
		
	}
	
	@Test
	public void noIntersectionTest() {
		try {
			a.intersect(b);
		} catch (NoIntersectionException e) {
		}
	}
	
	@Test
	public void intersectionTest() throws NoIntersectionException {
		Region temp = a.intersect(c);
		assertEquals(temp.getLeft(), 1);
		assertEquals(temp.getTop(), 1);
		assertEquals(temp.getRight(), 1);
		assertEquals(temp.getBottom(), 1);
	}
	
	@Test
	public void mutableObservablePicturePaint() {
		Picture greenMuOb1 = new ObservablePictureImpl(greenMuPic);
		greenMuOb1.paint(0, 0, red);
		check_for_component_equality(greenMuOb1.getPixel(0, 0), red);
		greenMuOb1.paint(0, 0, 2, 2, blue);
		check_for_picture_equality(greenMuOb1,blueMuOb);
	}
	
	@Test
	public void immutableObservablePicturePaint() {
		Picture greenImOb2 = new ObservablePictureImpl(greenImPic);
		greenImOb2.paint(0, 0, red);
		check_for_component_equality(greenImOb2.getPixel(0, 0), red);
		greenImOb2.paint(0, 0, 2, 2, blue);
		check_for_picture_equality(greenImOb2,blueMuOb);
	}
	
	@Test
	public void notifyTest() {
		rgbMuOb.registerROIObserver(o1, d);
		assertEquals(((ROIObserverImpl) o1).getCount(), 0);
		rgbMuOb.paint(0, 0, randomColor);
		assertEquals(((ROIObserverImpl) o1).getCount(), 1);
		rgbMuOb.paint(1, 1, randomColor);
		assertEquals(((ROIObserverImpl) o1).getCount(), 2);
		rgbMuOb.paint(2, 2, randomColor);
		assertEquals(((ROIObserverImpl) o1).getCount(), 2);
	}
	
	@Test
	public void suspendResumeTest() {
		blueMuOb.registerROIObserver(o2, d);
		blueMuOb.suspendObservable();
		assertEquals(((ROIObserverImpl) o2).getCount(), 0);
		blueMuOb.paint(0, 0, randomColor);
		assertEquals(((ROIObserverImpl) o2).getCount(), 0);
		blueMuOb.paint(1, 1, randomColor);
		assertEquals(((ROIObserverImpl) o2).getCount(), 0);
		blueMuOb.resumeObservable();
		assertEquals(((ROIObserverImpl) o2).getCount(), 1);
	}
	
	@Test
	public void registerAndUnregisterTest() {
		greenMuOb.registerROIObserver(o3, a);
		greenMuOb.registerROIObserver(o3, c);
		greenMuOb.registerROIObserver(o3, d);
		greenMuOb.registerROIObserver(o4, a);
		greenMuOb.registerROIObserver(o4, c);
		greenMuOb.registerROIObserver(o4, d);
		assertEquals(greenMuOb.findROIObservers(a).length, 6);
		assertEquals(greenMuOb.findROIObservers(e).length, 2);
		greenMuOb.unregisterROIObservers(e);
		assertEquals(greenMuOb.findROIObservers(e).length, 0);
		greenMuOb.unregisterROIObserver(o3);
		assertEquals(greenMuOb.findROIObservers(a).length, 2);
	}
	
	
	private static boolean check_for_component_equality(Pixel a, Pixel b) {
		assertEquals(a.getRed(), b.getRed(), 0.001);
		assertEquals(a.getGreen(), b.getGreen(), 0.001);
		assertEquals(a.getBlue(), b.getBlue(), 0.001);

		return true;
	}
	
	private static boolean check_for_picture_equality(Picture a, Picture b) {
		if(a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight()) {
			throw new IllegalArgumentException("cannot compare");
		}
		for(int i=0;i<a.getWidth();i++) {
			for(int j=0; j<a.getHeight(); j++) {
				Pixel a1 = a.getPixel(i, j);
				Pixel b1 = b.getPixel(i, j);
				check_for_component_equality(a1, b1);
			}
		}
		return true;
	}
}

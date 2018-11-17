package a6test.koumi;

import static org.junit.Assert.*;

import org.junit.Test;


import a6.*;

public class A6Tests {
	
	// test pixels

	Pixel red = new ColorPixel(1,0,0);
	Pixel blue = new ColorPixel(0,0,1);
	Pixel green = new ColorPixel(0,1,0);
	
	// test rows
	
	Pixel [] redRow = new Pixel[] {red,red,red,red};
	Pixel [] blueRow = new Pixel[] {blue,blue,blue};
	Pixel[] firstRow = new Pixel [] { blue, blue, red, red};
	Pixel[] secondRow = new Pixel [] {blue, blue, red, red};
	Pixel[] thirdRow = new Pixel [] {green, green, red, red};
	Pixel[] fourthRow = new Pixel [] {green, green, red, red};
	Pixel[] onegreenRow = new Pixel[] {green};

			
	// test arrays
	Pixel[][] multicoloredPixels = new Pixel[][] {firstRow, secondRow, thirdRow, fourthRow};

	
	Pixel[][] redPixels = new Pixel[][] {redRow,redRow,redRow};	
	
	Pixel[][] bluerowarray = new Pixel[][] {blueRow};
	
	Pixel[][] greenpixelarray = new Pixel[][] {onegreenRow};
	
	
	
	// test pictures
	Picture multicoloredPicture = new MutablePixelArrayPicture(multicoloredPixels, "first things first rest in peace uncle phil");
	
	Picture monochromeRedMut = new MutablePixelArrayPicture(redPixels, "suh dude");
	
	Picture colorfulImut = new ImmutablePixelArrayPicture(multicoloredPixels, "illuminati confirmed");
	
	Picture monochromeRedImut = new ImmutablePixelArrayPicture(redPixels, "J.Cole went double platinum with no features");
	
	Picture onerowBluepic = new MutablePixelArrayPicture(bluerowarray, "Gucci Mane is the goat");
	
	Picture greenPixelPic = new MutablePixelArrayPicture(greenpixelarray, "new phone, who dis");
	
	Picture blueVertPicImut = new ImmutablePixelArrayPicture(createbluevert(),"Martin Luthor King woulda been on dreamville");
	
	Picture blueVertPictMut = new MutablePixelArrayPicture(createbluevert(), "don't save her she don't wanna be saved");
	
	Picture nullPicture = null;
	
	
	// this test is going to test each paint method that I have to be working
	
	@Test
	public void ROIObserverConstructorTest() {
		try {
		ObservablePicture nullpicshouldntwork = new ObservablePictureImpl(nullPicture);
		fail("null picture should have caused an exception");
		}catch(IllegalArgumentException e) {
		}
		
	}
	@Test
	public void ROIObserverPaintTest() {
		ObservablePicture observableMulticoloredPicture = new ObservablePictureImpl(multicoloredPicture);
		ObservablePicture b = new ObservablePictureImpl(multicoloredPicture);
		ObservablePicture c = new ObservablePictureImpl(multicoloredPicture);
		
		observableMulticoloredPicture.paint(2, 3, blue);
		colorCompare(observableMulticoloredPicture.getPixel(2, 3), blue);
		
		
		b.paint(0, 0, 3, 3, blue);
		colorCompare(observableMulticoloredPicture.getPixel(1, 1), blue);
		colorCompare(observableMulticoloredPicture.getPixel(2, 2), blue);
		colorCompare(observableMulticoloredPicture.getPixel(2, 1), blue);
		colorCompare(observableMulticoloredPicture.getPixel(1, 2), blue);
		
		c.paint(2, 2, 1, green);
		colorCompare(observableMulticoloredPicture.getPixel(2, 3), green);
		colorCompare(observableMulticoloredPicture.getPixel(2, 1), green);
		colorCompare(observableMulticoloredPicture.getPixel(1, 2), green);
		colorCompare(observableMulticoloredPicture.getPixel(3, 2), green);

		
	}
	
	
	// This makes sure that nothing is being notified when their are paint methods working but the observers are suspended
	@Test
	public void ROIObserverSuspendTest() {
		ObservablePicture observableMonochromeRed = new ObservablePictureImpl(monochromeRedMut);
		ROIObserver a = new ROIObserverImpl();
		ROIObserver b = new ROIObserverImpl();
		ROIObserver c = new ROIObserverImpl();
		Region a1 = new RegionImpl(0,0,1,1);
		Region b1 = new RegionImpl(1,1,2,2);
		Region c1 = new RegionImpl(2,0,3,2);
		observableMonochromeRed.registerROIObserver(a, a1);
		observableMonochromeRed.registerROIObserver(b, b1);
		observableMonochromeRed.registerROIObserver(c, c1);
		observableMonochromeRed.paint(0, 0, blue);
		assertEquals(1, a.getCount());
		
		observableMonochromeRed.suspendObservable();
		observableMonochromeRed.paint(0,0,onerowBluepic);
		observableMonochromeRed.paint(1,1,3,3,blue);
		assertEquals(a.getCount(),1);
		assertEquals(b.getCount(),0);
		assertEquals(c.getCount(),0);
		
		observableMonochromeRed.resumeObservable();
		assertEquals(2,a.getCount());
		assertEquals(1,b.getCount());
		assertEquals(1,c.getCount());
		
		
	}
	
	// tests the intersection method
	@Test
	public void RegionImplIntersectionTest() {
		Region a = new RegionImpl(0,0,1,1);
		Region b = new RegionImpl(3,3,4,4);
		try {
		Region c = a.intersect(b);
		fail("should throw exception that doesn't intersect");
		}catch(NoIntersectionException e) {
		}
		
		Region c = new RegionImpl(0,0,4,4);
		Region d = new RegionImpl(1,1,5,5);
		try {
		Region f = c.intersect(d);
		assertEquals(f.getTop(),d.getTop());
		assertEquals(f.getLeft(),d.getLeft());
		assertEquals(f.getRight(),c.getRight());
		assertEquals(f.getBottom(),c.getBottom());
		}catch(NoIntersectionException e) {
			fail("shouldn't through an exception when they intersect");
		}
		
		
	}
	// tests the union method
	@Test
	public void RegionImplUnionTest() {
		Region a = new RegionImpl(0,0,1,1);
		Region b = new RegionImpl(3,3,4,4);
		Region c = a.union(b);
		assertEquals(a.getTop(), c.getTop());
		assertEquals(a.getLeft(),c.getLeft());
		assertEquals(b.getBottom(),c.getBottom());
		assertEquals(b.getRight(),c.getRight());
	}
	
	public Pixel[][] createbluevert() {
		Pixel[][] verticalbluearray = new Pixel[1][5];
		for(int i = 0; i < 5; i++) {
			verticalbluearray[0][i] = blue;
		}
		return verticalbluearray;
	}
	
	private void colorCompare(Pixel a, Pixel b) {
		assertEquals(a.getRed(), b.getRed(), 0.001);
		assertEquals(a.getGreen(), b.getGreen(), 0.001);
		assertEquals(a.getBlue(), b.getBlue(), 0.001);
	}
	
	private boolean equals (Picture test1 , Picture test2) {
		int width1 = test1.getWidth();
		int height1 = test1.getHeight();
		int width2 = test2.getWidth();
		int height2 = test2.getHeight();
		if (width1 != width2 || height1 != height2) {
			return false;
		}
		Pixel[][] pixelarray1 = new Pixel[width1][height1];
		Pixel[][] pixelarray2 = new Pixel[width2][height2];
		 
		for (int width = 0 ; width < width1; width ++) {
			for (int height = 0; height < height1; height++) {
				pixelarray1[width][height] = test1.getPixel(width, height);
				pixelarray2[width][height] = test2.getPixel(width, height);
				
				if (!pixelarray1[width][height].equals(pixelarray2[width][height])) {
					return false;
				}
			}
		}
		return true;
	}
}

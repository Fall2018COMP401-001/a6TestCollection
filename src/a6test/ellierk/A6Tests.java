package a6test.ellierk;

import org.junit.Test;

import a6.*;
import a6tests.ROIObserverImpl;

import static org.junit.Assert.*;


public class A6Tests {
	
	@Test
	public void testCirclePaint() {
		Pixel[][] parray = new Pixel[5][5];
		
		for (int x=0; x< 5; x++) {
			for (int y=0; y<5; y++) {
				parray[x][y] = new ColorPixel(0, 0, 0);
			}
		}
		Pixel[][] parray2 = new Pixel[5][5];
		
		for (int x=0; x< 5; x++) {
			for (int y=0; y<5; y++) {
				parray2[x][y] = new ColorPixel(.5, .5, .5);
			}
		}
		ImmutablePixelArrayPicture to_change = new ImmutablePixelArrayPicture(parray, "change");
		MutablePixelArrayPicture to_ref = new MutablePixelArrayPicture(parray2, "paint from");
		
		ObservablePicture changed = new ObservablePictureImpl(to_change);
		
		assertTrue(colorCompare(changed.getPixel(0, 0), to_change.getPixel(0, 0)));
		assertFalse(colorCompare(changed.getPixel(0, 0), to_ref.getPixel(0, 0)));
		
		changed.paint(0, 0, to_ref);
		
		assertFalse(colorCompare(to_change.getPixel(0, 0), changed.getPixel(0, 0)));
		assertTrue(colorCompare(to_ref.getPixel(1, 1), changed.getPixel(0, 0)));
	}
	
	private boolean colorCompare(Pixel c1, Pixel c2) {
		if (c1.getRed() == c2.getRed() && c1.getGreen() == c2.getGreen()
				&& c1.getBlue() == c2.getBlue()) {
			return true;
		} else return false;
	}
}
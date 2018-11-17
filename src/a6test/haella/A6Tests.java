package a6test.haella;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import a6.*;

class A6Tests {
	
	private static Pixel RED = new ColorPixel(1.0, 0, 0);
	private static Pixel GREEN = new ColorPixel(0, 1.0, 0);
	private static Pixel BLUE = new ColorPixel(0, 0, 1.0);
	
	Picture multicolor_mutable = new MutablePixelArrayPicture(makeSolidPixelArray(10, 8, RED), "caption");
	Picture multicolor_immutable = new ImmutablePixelArrayPicture(makeSolidPixelArray(10, 8, RED), "caption");
	
	Picture small_red = new MutablePixelArrayPicture(makeSolidPixelArray(2, 2, RED), "my caption");
	Picture smaller_red = new MutablePixelArrayPicture(makeSolidPixelArray(1, 1, RED), "my caption");
	
	
	
	
	

	
	@Test
	public void ObservablePictureMutabilityTest() {
		for (int x=0; x<multicolor_mutable.getWidth(); x++) {
			for (int y=0; y<multicolor_mutable.getHeight(); y++) {
				double fx = ((double) x) / ((double) (multicolor_mutable.getWidth()-1));
				double fy = ((double) y) / ((double) (multicolor_mutable.getHeight()-1));
				multicolor_mutable.paint(x, y, RED.blend(BLUE, fx).blend(GREEN, fy));
			}
		}
		
		//test for mutability will all paint methods on observable with mutable source picture
		Picture multicolor_immutable_observable = new ObservablePictureImpl(multicolor_immutable);
		Picture multicolor_mutable_observable = new ObservablePictureImpl(multicolor_mutable);
		
		Picture painted_pixel_mutable = multicolor_mutable_observable.paint(0, 0, BLUE);
		assertTrue(painted_pixel_mutable == multicolor_mutable_observable);
		
		Picture painted_radius_mutable = multicolor_mutable_observable.paint(2, 2, 2.1, BLUE);
		assertTrue(painted_radius_mutable == multicolor_mutable_observable);
		
		Picture painted_rectangle_mutable = multicolor_mutable_observable.paint(1, 0, 4, 5, GREEN);
		assertTrue(painted_rectangle_mutable == multicolor_mutable_observable);
		
		Picture painted_picture_mutable = multicolor_mutable_observable.paint(1, 1, small_red);
		assertTrue(painted_picture_mutable == multicolor_mutable_observable);
		
		//test for mutability will all paint methods on observable with immutable source picture
		Picture painted_pixel_immutable = multicolor_immutable_observable.paint(0, 0, BLUE);
		assertTrue(painted_pixel_immutable == multicolor_immutable_observable);
		
		Picture painted_radius_immutable = multicolor_immutable_observable.paint(2, 2, 2.1, BLUE);
		assertTrue(painted_radius_immutable == multicolor_immutable_observable);
		
		Picture painted_rectangle_immutable = multicolor_immutable_observable.paint(1, 0, 4, 5, GREEN);
		assertTrue(painted_rectangle_immutable == multicolor_immutable_observable);
		
		Picture painted_picture_immutable = multicolor_immutable_observable.paint(1, 1, small_red);
		assertTrue(painted_picture_immutable == multicolor_immutable_observable);
		
	}
	
	@Test
	public void ObservablePicturePaintAndNotifyTest() {
		for (int x=0; x<multicolor_mutable.getWidth(); x++) {
			for (int y=0; y<multicolor_mutable.getHeight(); y++) {
				double fx = ((double) x) / ((double) (multicolor_mutable.getWidth()-1));
				double fy = ((double) y) / ((double) (multicolor_mutable.getHeight()-1));
				multicolor_mutable.paint(x, y, RED.blend(BLUE, fx).blend(GREEN, fy));
			}
		}
		
		ObservablePictureImpl multicolor_immutable_observable = new ObservablePictureImpl(multicolor_immutable);
		ObservablePictureImpl multicolor_mutable_observable = new ObservablePictureImpl(multicolor_mutable);
		
		ROIObserver generic_roi_observer = new GenericROIObserver();
		Region region_to_observe = new RegionImpl(1, 0, 3, 4);
		
		multicolor_immutable_observable.registerROIObserver(generic_roi_observer, region_to_observe);
		multicolor_mutable_observable.registerROIObserver(generic_roi_observer, region_to_observe);
		
		multicolor_immutable_observable.paint(1, 1, GREEN);
		assertEquals(multicolor_immutable_observable.getNotifyCount(), 1);
		
		
		//paint using each paint method then check if notify count increases correctly
		//one pixel paint
		multicolor_mutable_observable.paint(1, 1, GREEN);
		assertEquals(multicolor_mutable_observable.getNotifyCount(), 1);
		multicolor_immutable_observable.paint(1, 1, GREEN);
		assertEquals(multicolor_immutable_observable.getNotifyCount(), 1); //changed from 1 to 2 here
		
		//radius paint
		multicolor_mutable_observable.paint(2, 2, 2.1, BLUE);
		assertEquals(multicolor_mutable_observable.getNotifyCount(), 2);
		multicolor_immutable_observable.paint(2, 2, 2.1, BLUE);
		assertEquals(multicolor_immutable_observable.getNotifyCount(), 2);
		
		//rectangle paint
		multicolor_mutable_observable.paint(1, 0, 4, 5, GREEN);
		assertEquals(multicolor_mutable_observable.getNotifyCount(), 3);
		multicolor_immutable_observable.paint(1, 0, 4, 5, GREEN);
		assertEquals(multicolor_immutable_observable.getNotifyCount(), 3);
		
		//picture paint
		multicolor_mutable_observable.paint(1, 1, small_red);
		assertEquals(multicolor_mutable_observable.getNotifyCount(), 4);
		multicolor_immutable_observable.paint(1, 1, small_red);
		assertEquals(multicolor_immutable_observable.getNotifyCount(), 4);
		
		
		//now paint stuff  out of bounds and see if it notifies
		//one pixel paint
				multicolor_mutable_observable.paint(5, 4, GREEN);
				assertEquals(multicolor_mutable_observable.getNotifyCount(), 4);
				multicolor_immutable_observable.paint(5, 4, GREEN);
				assertEquals(multicolor_immutable_observable.getNotifyCount(), 4);
				
				//radius paint
				multicolor_mutable_observable.paint(6, 4, 1.0, BLUE);
				assertEquals(multicolor_mutable_observable.getNotifyCount(), 4);
				multicolor_immutable_observable.paint(6, 4, 1.0, BLUE);
				assertEquals(multicolor_immutable_observable.getNotifyCount(), 4);
				
				//rectangle paint
				multicolor_mutable_observable.paint(4, 4, 6, 4, GREEN);
				assertEquals(multicolor_mutable_observable.getNotifyCount(), 4);
				multicolor_immutable_observable.paint(4, 4, 6, 4, GREEN);
				assertEquals(multicolor_immutable_observable.getNotifyCount(), 4);
				
				//picture paint
				multicolor_mutable_observable.paint(1, 1, smaller_red);
				assertEquals(multicolor_mutable_observable.getNotifyCount(), 4);
				multicolor_immutable_observable.paint(1, 1, smaller_red);
				assertEquals(multicolor_immutable_observable.getNotifyCount(), 4);
		
		//so, when you get back, your ROI observer is only an interface, so you need to make some test child class for it
		//multicolor_immutable_observable.registerROIObserver(, new RegionImpl(1, 1, 4, 3));
		
		
	}
	
	@Test
	public void ObservablePictureSuspendAndResumeTest() {
		for (int x=0; x<multicolor_mutable.getWidth(); x++) {
			for (int y=0; y<multicolor_mutable.getHeight(); y++) {
				double fx = ((double) x) / ((double) (multicolor_mutable.getWidth()-1));
				double fy = ((double) y) / ((double) (multicolor_mutable.getHeight()-1));
				multicolor_mutable.paint(x, y, RED.blend(BLUE, fx).blend(GREEN, fy));
			}
		}
		
		ObservablePictureImpl multicolor_immutable_observable = new ObservablePictureImpl(multicolor_immutable);
		ObservablePictureImpl multicolor_mutable_observable = new ObservablePictureImpl(multicolor_mutable);
		
		ROIObserver generic_roi_observer = new GenericROIObserver();
		Region region_to_observe = new RegionImpl(1, 0, 3, 4);
		
		multicolor_immutable_observable.registerROIObserver(generic_roi_observer, region_to_observe);
		multicolor_mutable_observable.registerROIObserver(generic_roi_observer, region_to_observe);
		
		multicolor_immutable_observable.suspendObservable();
		multicolor_mutable_observable.suspendObservable();
		
		//tests paint methods
		multicolor_mutable_observable.paint(1, 1, GREEN);
		assertEquals(multicolor_mutable_observable.getNotifyCount(), 0);
		multicolor_immutable_observable.paint(1, 1, GREEN);
		assertEquals(multicolor_immutable_observable.getNotifyCount(), 0);
		
		//radius paint
		multicolor_mutable_observable.paint(2, 2, 2.1, BLUE);
		assertEquals(multicolor_mutable_observable.getNotifyCount(), 0);
		multicolor_immutable_observable.paint(2, 2, 2.1, BLUE);
		assertEquals(multicolor_immutable_observable.getNotifyCount(), 0);
		
		//rectangle paint
		multicolor_mutable_observable.paint(1, 0, 4, 5, GREEN);
		assertEquals(multicolor_mutable_observable.getNotifyCount(), 0);
		multicolor_immutable_observable.paint(1, 0, 4, 5, GREEN);
		assertEquals(multicolor_immutable_observable.getNotifyCount(), 0);
		
		//picture paint
		multicolor_mutable_observable.paint(1, 1, small_red);
		assertEquals(multicolor_mutable_observable.getNotifyCount(), 0);
		multicolor_immutable_observable.paint(1, 1, small_red);
		assertEquals(multicolor_immutable_observable.getNotifyCount(), 0);
		
		//resumes and tests for all the notifications
		multicolor_immutable_observable.resumeObservable();
		multicolor_mutable_observable.resumeObservable();
		assertEquals(multicolor_immutable_observable.getNotifyCount(), 1);  //pretty sure this should only notify once so changed from 4 to 1
		assertEquals(multicolor_mutable_observable.getNotifyCount(), 1);
	}
	
	@Test
	public void FindROIObserversTest() {
		//make a bunch of regions to observe checking as you go the length of the found observers returned and checking region bounds for them
		
		for (int x=0; x<multicolor_mutable.getWidth(); x++) {
			for (int y=0; y<multicolor_mutable.getHeight(); y++) {
				double fx = ((double) x) / ((double) (multicolor_mutable.getWidth()-1));
				double fy = ((double) y) / ((double) (multicolor_mutable.getHeight()-1));
				multicolor_mutable.paint(x, y, RED.blend(BLUE, fx).blend(GREEN, fy));
			}
		}
		
		ObservablePictureImpl multicolor_immutable_observable = new ObservablePictureImpl(multicolor_immutable);
		ObservablePictureImpl multicolor_mutable_observable = new ObservablePictureImpl(multicolor_mutable);
		
		ROIObserver generic_roi_observer = new GenericROIObserver();
		Region region_to_observe = new RegionImpl(0, 0, 3, 4);
		ROIObserver[] observers;
		
		multicolor_immutable_observable.registerROIObserver(generic_roi_observer, region_to_observe);
		multicolor_mutable_observable.registerROIObserver(generic_roi_observer, region_to_observe);
		
		observers = multicolor_immutable_observable.findROIObservers(new RegionImpl(3, 3, 6, 6));
		System.out.println(observers.length); //this is 0 here. That is not right. Fix this somehow. 
		assertEquals(observers.length, 1);
		multicolor_mutable_observable.findROIObservers(new RegionImpl(3, 3, 6, 6));
		assertEquals(observers.length, 1);
		
		observers = multicolor_immutable_observable.findROIObservers(new RegionImpl(0, 0, 6, 6));
		assertEquals(observers.length, 1);
		observers = multicolor_mutable_observable.findROIObservers(new RegionImpl(0, 0, 6, 6));
		assertEquals(observers.length, 1);
		
		observers = multicolor_immutable_observable.findROIObservers(new RegionImpl(4, 5, 6, 6));
		assertEquals(observers.length, 0);
		observers = multicolor_immutable_observable.findROIObservers(new RegionImpl(4, 5, 6, 6));
		assertEquals(observers.length, 0);
		
		//test two
		multicolor_immutable_observable.registerROIObserver(generic_roi_observer, new RegionImpl(2, 2, 6, 6));
		multicolor_mutable_observable.registerROIObserver(generic_roi_observer, new RegionImpl(2, 2, 6, 6));
		//if in region(2, 2, 3, 4) then 2. If in regions(0, 0, 1, 1) or (4, 5, 6, 6) then 1. Otherwise, nada
		
		observers = multicolor_immutable_observable.findROIObservers(new RegionImpl(3, 3, 6, 6));
		assertEquals(observers.length, 2);
		observers = multicolor_mutable_observable.findROIObservers(new RegionImpl(3, 3, 6, 6));
		assertEquals(observers.length, 2);
		
		observers = multicolor_immutable_observable.findROIObservers(new RegionImpl(1, 1, 7, 7));
		assertEquals(observers.length, 2);
		observers = multicolor_mutable_observable.findROIObservers(new RegionImpl(1, 1, 7, 7));
		assertEquals(observers.length, 2);
		
		observers = multicolor_immutable_observable.findROIObservers(new RegionImpl(1, 1, 2, 2));
		assertEquals(observers.length, 2);
		observers = multicolor_mutable_observable.findROIObservers(new RegionImpl(1, 1, 2, 2));
		assertEquals(observers.length, 2);
		
		observers = multicolor_immutable_observable.findROIObservers(new RegionImpl(4, 1, 5, 7));
		assertEquals(observers.length, 1);
		observers = multicolor_mutable_observable.findROIObservers(new RegionImpl(4, 1, 5, 7));
		assertEquals(observers.length, 1);
		
		observers = multicolor_immutable_observable.findROIObservers(new RegionImpl(0, 0, 1, 1));
		assertEquals(observers.length, 1);
		observers = multicolor_mutable_observable.findROIObservers(new RegionImpl(0, 0, 1, 1));
		assertEquals(observers.length, 1);
		
		observers = multicolor_mutable_observable.findROIObservers(new RegionImpl(8, 7, 9, 7));
		assertEquals(observers.length, 0);
		observers = multicolor_immutable_observable.findROIObservers(new RegionImpl(8, 7, 9, 7));
		assertEquals(observers.length, 0);
		
		//later you need to check that the regions found are correct as well
	}
	
	@Test
	public void RegisterROIObserverTest() {
		for (int x=0; x<multicolor_mutable.getWidth(); x++) {
			for (int y=0; y<multicolor_mutable.getHeight(); y++) {
				double fx = ((double) x) / ((double) (multicolor_mutable.getWidth()-1));
				double fy = ((double) y) / ((double) (multicolor_mutable.getHeight()-1));
				multicolor_mutable.paint(x, y, RED.blend(BLUE, fx).blend(GREEN, fy));
			}
		}
		
		ObservablePictureImpl multicolor_immutable_observable = new ObservablePictureImpl(multicolor_immutable);
		ObservablePictureImpl multicolor_mutable_observable = new ObservablePictureImpl(multicolor_mutable);
		
		ROIObserver generic_roi_observer = new GenericROIObserver();
		Region region_to_observe = new RegionImpl(0, 0, 3, 4);
		ROIObserver[] observers;
		
		
		multicolor_immutable_observable.registerROIObserver(generic_roi_observer, region_to_observe);
		multicolor_mutable_observable.registerROIObserver(generic_roi_observer, region_to_observe);
		assertEquals(multicolor_immutable_observable.getROIObservers().size(), 1);
		assertEquals(multicolor_mutable_observable.getROIObservers().size(), 1);
		assertEquals(multicolor_immutable_observable.getObservedRegions().size(), 1);
		assertEquals(multicolor_immutable_observable.getObservedRegions().size(), 1); 
		
		multicolor_immutable_observable.registerROIObserver(generic_roi_observer, new RegionImpl(2, 2, 6, 6));
		multicolor_mutable_observable.registerROIObserver(generic_roi_observer, new RegionImpl(2, 2, 6, 6));
		assertEquals(multicolor_immutable_observable.getROIObservers().size(), 2);
		assertEquals(multicolor_mutable_observable.getROIObservers().size(), 2);
		assertEquals(multicolor_immutable_observable.getObservedRegions().size(), 2);
		assertEquals(multicolor_immutable_observable.getObservedRegions().size(), 2);
		
		//need to check the values of these as well
	}
	
	@Test
	public void UnregisterROIObserverTest() {
		for (int x=0; x<multicolor_mutable.getWidth(); x++) {
			for (int y=0; y<multicolor_mutable.getHeight(); y++) {
				double fx = ((double) x) / ((double) (multicolor_mutable.getWidth()-1));
				double fy = ((double) y) / ((double) (multicolor_mutable.getHeight()-1));
				multicolor_mutable.paint(x, y, RED.blend(BLUE, fx).blend(GREEN, fy));
			}
		}
		
		ObservablePictureImpl multicolor_immutable_observable = new ObservablePictureImpl(multicolor_immutable);
		ObservablePictureImpl multicolor_mutable_observable = new ObservablePictureImpl(multicolor_mutable);
		
		ROIObserver generic_roi_observer = new GenericROIObserver();
		ROIObserver second_generic_roi_observer = new GenericROIObserver();
		Region region_to_observe = new RegionImpl(0, 0, 3, 4);
		
		multicolor_immutable_observable.registerROIObserver(generic_roi_observer, region_to_observe);
		multicolor_mutable_observable.registerROIObserver(generic_roi_observer, region_to_observe);
		multicolor_immutable_observable.registerROIObserver(second_generic_roi_observer, region_to_observe);
		multicolor_mutable_observable.registerROIObserver(second_generic_roi_observer, region_to_observe);
		multicolor_immutable_observable.registerROIObserver(generic_roi_observer, new RegionImpl(2, 2, 6, 6));
		multicolor_mutable_observable.registerROIObserver(generic_roi_observer, new RegionImpl(2, 2, 6, 6));
		
		
		multicolor_immutable_observable.unregisterROIObserver(generic_roi_observer);
		multicolor_mutable_observable.unregisterROIObserver(generic_roi_observer);
		
		assertEquals(multicolor_immutable_observable.getROIObservers().size(), 1);
		assertEquals(multicolor_mutable_observable.getROIObservers().size(), 1);
		assertEquals(multicolor_immutable_observable.getObservedRegions().size(), 1);
		assertEquals(multicolor_immutable_observable.getObservedRegions().size(), 1);
		
		
		multicolor_immutable_observable.unregisterROIObservers(region_to_observe);
		multicolor_mutable_observable.unregisterROIObservers(region_to_observe); //should come up with more test cases with this and the previous one
		
		assertEquals(multicolor_immutable_observable.getROIObservers().size(), 0);
		assertEquals(multicolor_mutable_observable.getROIObservers().size(), 0);
		assertEquals(multicolor_immutable_observable.getObservedRegions().size(), 0);
		assertEquals(multicolor_immutable_observable.getObservedRegions().size(), 0);
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



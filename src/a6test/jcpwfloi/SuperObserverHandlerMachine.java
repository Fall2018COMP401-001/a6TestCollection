package a6test.jcpwfloi;

import static org.junit.Assert.*;

import a6.*;

public class SuperObserverHandlerMachine {
	private ROIObserver[] observers;
	private Region[] regions;
	private boolean[] marked;
	
	private ObservablePicture picture;
	private int testSize;
	
	private boolean isSimple;
	
	public SuperObserverHandlerMachine(ObservablePicture p, int testSize, boolean isSimple) {
		picture = p;
		this.testSize = testSize;
		this.isSimple = isSimple;
	}
	
	private static int randInt(int l, int r) {
		return (int)(Math.random() * (r - l + 1)) + l;
	}
	
	private static Region generateRandomRegion(int width, int height) {
		int left = randInt(0, width - 1);
		int right = randInt(left, width - 1);
		int top = randInt(0, height - 1);
		int bottom = randInt(top, height - 1);
		return new RegionImpl(left, top, right, bottom);
	}
	
	public void generateObservers(int n) {
		observers = new ROIObserver[n];
		regions = new Region[n];
		marked = new boolean[n];
		for (int i = 0; i < n; ++ i) {
			observers[i] = new ChensObserver(this);
			regions[i] = generateRandomRegion(picture.getWidth(), picture.getHeight());
			picture.registerROIObserver(observers[i], regions[i]);
		}
	}
	
	public void test() {
		generateObservers(testSize);
		
		for (int i = 0; i < testSize; ++ i) testPixelPaint();
		
		if (!isSimple) {
			// advancedTests
		}
	}
	
	public void testPixelPaint() {
		Pixel pixel = generateRandomPixel();
		
		int x = randInt(0, picture.getWidth() - 1);
		int y = randInt(0, picture.getHeight() - 1);
		
		Region changedRegion = new RegionImpl(x, y, x, y);
		
		for (int i = 0; i < testSize; ++ i) {
			try {
				Region ans = changedRegion.intersect(regions[i]);
				marked[i] = true;
			} catch (NoIntersectionException e) {
				marked[i] = false;
			}
		}
		
		picture.paint(x, y, pixel);
		
		for (int i = 0; i < testSize; ++ i) {
			if (marked[i] == true)
				fail("wrong answer");
		}
	}
	
	public void handler(ROIObserver observer, ObservablePicture target, Region r) {
		for (int i = 0; i < testSize; ++ i)
			if (observers[i] == observer) marked[i] = false;
		
		if (target != picture)
			throw new RuntimeException("invalid observable target");
	}
	
	private static Pixel generateRandomPixel() {
		return new ColorPixel(Math.random(), Math.random(), Math.random());
	}
}

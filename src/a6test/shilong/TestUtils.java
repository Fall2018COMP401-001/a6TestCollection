package a6test.shilong;

public final class TestUtils {

	private TestUtils() {
	}

	public static boolean regionEquals(A6Tests a, A6Tests b) {
		return a.getTop() == b.getTop() &&
			a.getBottom() == b.getBottom() &&
			a.getLeft() == b.getLeft() &&
			a.getRight() == b.getRight();
	}

	public static ObservablePicture getObservablePicture(int width, int height) {
		Picture srcPic = new MutablePixelArrayPicture(randColorRectangle(width, height), "Ipsum Lorem");
		return new ObservablePictureImpl(srcPic);
	}

	public static Pixel[][] randColorRectangle(int width, int height) {
		Pixel[][] rectanglePixels = new Pixel[width][height];
		for (Pixel[] array : rectanglePixels) {
			for (int i = 0; i < array.length; ++i) {
				array[i] = randColorPixel();
			}
		}
		return rectanglePixels;
	}

	public static Pixel randColorPixel() {
		Pixel pixel = new ColorPixel(Math.random(), Math.random(), Math.random());
		return pixel;
	}

}

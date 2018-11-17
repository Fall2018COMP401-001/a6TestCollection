package a6test.vallmeister;

import a6.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class runs unit tests against {@link ObservablePictureImpl}.
 *
 * @author Christian
 */
public class ObservablePictureImplTest {
  private ROIObserverTestImpl testObserver_1;
  private ROIObserverTestImpl testObserver_2;
  private ROIObserverTestImpl testObserver_3;

  /**
   * Initializes three new observers for every test.
   */
  @BeforeEach
  void init_observers() {
    testObserver_1 = new ROIObserverTestImpl();
    testObserver_2 = new ROIObserverTestImpl();
    testObserver_3 = new ROIObserverTestImpl();
  }

  /**
   * Testing the constructor with valid and invalid inputs.
   */
  @Test
  void test_constructor_and_delegations() {
    Picture immutable_testPicture = create_validImmutablePicture();
    assertThrows(IllegalArgumentException.class, () -> new ObservablePictureImpl(null));

    Picture observable_picture = new ObservablePictureImpl(immutable_testPicture);
    assertEquals(immutable_testPicture.getWidth(), observable_picture.getWidth());
    assertEquals(immutable_testPicture.getHeight(), observable_picture.getHeight());
    assertEquals(immutable_testPicture.getCaption(), observable_picture.getCaption());

    for (int i = 0; i < immutable_testPicture.getWidth(); ++i) {
      for (int j = 0; j < immutable_testPicture.getHeight(); ++j) {
        assertEquals(immutable_testPicture.getPixel(i, j), observable_picture.getPixel(i, j));
      }
    }
  }

  /**
   * Creates a monochrome 3x3 picture.
   * @return Mutable monochrome picture.
   */
  private Picture create_validMutablePicture() {
    Pixel[][] pixels = new Pixel[3][3];
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        pixels[i][j] = new GrayPixel(Math.random());
      }
    }
    return new MutablePixelArrayPicture(pixels, "Mutable test picture");
  }

  /**
   * Tests a single point paint operation on an observable picture.
   */
  @Test
  void test_paint_point() {
    // Test pictures
    ObservablePicture observable_1 = new ObservablePictureImpl(create_validImmutablePicture());
    ObservablePicture observable_2 = new ObservablePictureImpl(create_validMutablePicture());

    // Test regions
    Region region0033 = new RegionImpl(0, 0, 3, 3);
    Region region0011 = new RegionImpl(0, 0, 1, 1);
    Region region1223 = new RegionImpl(1, 2, 2, 3);
    Region region2233 = new RegionImpl(2, 2, 3, 3);

    // Picture one registrations
    observable_1.registerROIObserver(testObserver_1, region0033);
    testObserver_1.register(observable_1);
    observable_1.registerROIObserver(testObserver_2, region0011);
    testObserver_2.register(observable_1);

    // Picture two registrations
    observable_2.registerROIObserver(testObserver_2, region0011);
    testObserver_2.register(observable_2);
    observable_2.registerROIObserver(testObserver_2, region1223);
    observable_2.registerROIObserver(testObserver_3, region2233);
    testObserver_3.register(observable_2);
    observable_2.registerROIObserver(testObserver_3, region0011);

    // Different paint options
    observable_1.paint(0, 0, new GrayPixel(0.5));
    assertEquals(1, (int) testObserver_1.getNotifications(observable_1));
    assertEquals(1, (int) testObserver_2.getNotifications(observable_1));

    observable_1.paint(2, 2, Pixel.BLACK);
    assertEquals(2, (int) testObserver_1.getNotifications(observable_1));
    assertEquals(1, (int) testObserver_2.getNotifications(observable_1));

    observable_2.paint(0, 0, Pixel.WHITE);
    assertEquals(1, (int) testObserver_2.getNotifications(observable_2));
    assertEquals(1, (int) testObserver_3.getNotifications(observable_2));

    observable_2.paint(2, 2, Pixel.BLACK);
    assertEquals(2, (int) testObserver_2.getNotifications(observable_2));
    assertEquals(2, (int) testObserver_3.getNotifications(observable_2));
  }

  /**
   * Testing the notifications when painting a rectangle.
   */
  @Test
  void test_paint_rectangular_method() {

  }

  @Test
  void test_simple_suspend_resume() {
    Region region0011 = new RegionImpl(0, 0, 1, 1);

    ObservablePicture observablePicture_1 = new ObservablePictureImpl(create_validMutablePicture());
    observablePicture_1.registerROIObserver(testObserver_1, region0011);
    testObserver_1.register(observablePicture_1);

    observablePicture_1.suspendObservable();
    observablePicture_1.paint(0, 0, Pixel.WHITE);
    assertEquals(0, (int) testObserver_1.getNotifications(observablePicture_1));
    observablePicture_1.resumeObservable();
    assertEquals(1, (int) testObserver_1.getNotifications(observablePicture_1));

    observablePicture_1.paint(2, 2, Pixel.BLACK);
    assertEquals(1, (int) testObserver_1.getNotifications(observablePicture_1));

    observablePicture_1.suspendObservable();
    observablePicture_1.paint(2,2, Pixel.WHITE);
    observablePicture_1.paint(1, 2, Pixel.WHITE);
    observablePicture_1.resumeObservable();
    assertEquals(1, (int) testObserver_1.getNotifications(observablePicture_1));

    observablePicture_1.paint(0, 0, Pixel.WHITE);
    observablePicture_1.paint(0, 1, Pixel.BLACK);
    assertEquals(3, (int) testObserver_1.getNotifications(observablePicture_1));

    observablePicture_1.suspendObservable();
  }

  /**
   * Creates a picture for tests.
   * @return Immutable monochrome 3x3 picture.
   */
  private Picture create_validImmutablePicture() {
    Pixel[][] pixels = new Pixel[3][3];
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        pixels[i][j] = new GrayPixel(Math.random());
      }
    }
    return new ImmutablePixelArrayPicture(pixels, "Immutable test picture");
  }
}

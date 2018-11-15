package a6test.grant4e;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.junit.jupiter.api.Test;

import a6.*;

public class A6Tests {

	Pixel red = new ColorPixel(1, 0, 0);
	Pixel[][] redPixelArray = { { red, red, red, red }, { red, red, red, red }, { red, red, red, red },
			{ red, red, red, red } };
	Picture redPicture = new MutablePixelArrayPicture(redPixelArray, "red");
	ObservablePicture obsRedPicture = new ObservablePictureImpl(redPicture);
	ROIObserver observer = new ROIObserverImpl();

	// This test creates two threads which are then gated behind a CyclicBarrier.
	// The point of this is to simultaneously run methods which modify the
	// collection of observers and regions (in theory at least) in order to evoke
	// ConcurrentModificationException. To pass this test,
	// appropriate iterators should be used.
	//
	// PS I don't know how to incorporate this kind of exception-based stress-test
	// into Junit, but check console output for any errors.
	@Test
	public void testConcurrentModification() throws InterruptedException, BrokenBarrierException {

		for (int i = 0; i < 1000; i++) {
			obsRedPicture.registerROIObserver(observer, new RegionImpl(0, 0, 0, 0));
		}

		// A gate is created which waits for three calls to await() before allowing any
		// queued threads to continue.
		final CyclicBarrier gate = new CyclicBarrier(3);

		Thread t1 = new Thread() {
			public void run() {

				try {
					gate.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
				obsRedPicture.unregisterROIObservers(new RegionImpl(0, 0, 0, 0));
			}
		};

		Thread t2 = new Thread() {
			public void run() {

				try {
					gate.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}

				obsRedPicture.unregisterROIObserver(observer);
			}
		};

		t1.start();
		t2.start();

		// The third call to await() which will allow both threads to run.
		gate.await();

	}

}

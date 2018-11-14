
package a6test.corwinc;
import a6.*;

/**
 * @author Cory
 * This class is only a helper for testing with JUnit
 */
public class ROIObserverImpl implements ROIObserver {
	
	public ROIObserverImpl() {
		
	}

	/* 
	 * Prints for the notified region
	 */
	@Override
	public void notify(ObservablePicture picture, Region re) {
		String l = String.valueOf(re.getLeft());
		String t = String.valueOf(re.getTop());
		String r = String.valueOf(re.getRight());
		String b = String.valueOf(re.getBottom());
		System.out.println(l + ',' + t + ',' + r + ',' + b);
	}
	
}

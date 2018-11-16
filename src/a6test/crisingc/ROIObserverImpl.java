package a6test.crisingc;
// small changes
import a6.*;

public class ROIObserverImpl implements ROIObserver {
	String caption;
	
	public ROIObserverImpl(String caption) {
		this.caption = caption;
	}
	
	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
//		System.out.println("i am notified");

	}

}

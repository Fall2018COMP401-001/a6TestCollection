package a6;

public class ROIObserverImpl implements ROIObserver {

	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
		System.out.println("The region " + changed_region + 
				" in the picture " + picture + " has changed");
	}
	
}

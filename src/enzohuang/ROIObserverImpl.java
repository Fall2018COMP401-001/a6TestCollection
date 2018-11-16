package enzohuang;

public class ROIObserverImpl implements ROIObserver {

	
	private int notifyCount;
	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
		System.out.println("The region " + changed_region + 
				" in the picture " + picture + " has changed");
		notifyCount ++;
	}
	
	public int getNotifyCount() {
		return notifyCount;
	}
	
	public void clearNotifications() {
		notifyCount = 0;
	}
	
}

package a6test.bentontc;

import java.util.ArrayList;
import java.util.List;

import a6.ObservablePicture;
import a6.ROIObserver;
import a6.Region;

public class ROIObserverImpl implements ROIObserver {
	
	private ROIObserver observer;
	private List<Region> region;
	private int timesNotified;
	private int notifyCount;
	
	
	public ROIObserverImpl(ROIObserver o) {
		this.observer = o;
		this.region = new ArrayList<Region>();

	}
	
	public void notify(ObservablePicture picture, Region changed_region) {
		timesNotified++;
	}

	public ROIObserverImpl() {
		timesNotified = 0;
	}
	
	public int getTimesNotified() {
		return timesNotified;
	}
	public int getNotifyCount() {
		return timesNotified;
	}
	
	public void clearNotifications() {
		timesNotified = 0;
	}
}

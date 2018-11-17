package a6test.chenyy;

import a6.ObservablePicture;
import a6.ROIObserver;
import a6.Region;

public class ROIObserverImpl implements ROIObserver {

	@Override
	public void notify(ObservablePicture picture, Region re) {
		// TODO Auto-generated method stub

		String l = String.valueOf(re.getLeft());
		String t = String.valueOf(re.getTop());
		String r = String.valueOf(re.getRight());
		String b = String.valueOf(re.getBottom());
		System.out.println(l + ',' + t + ',' + r + ',' + b);
	}
}

package a6test.tianxin;

import a6.NoIntersectionException;

public class RegionImpl implements Region {
	private int top_y;
	private int bottom_y;
	private int left_x;
	private int right_x;
	
	public RegionImpl(int left, int top, int right, int bottom)throws IllegalArgumentException {
		if(left > right|| bottom < top) {
			throw new IllegalArgumentException("illegal region geometry");
		}
		this.top_y = top;
		this.bottom_y = bottom;
		this.left_x = left;
		this.right_x = right;
	}

	@Override
	public int getTop() {
		// method getTop() return the y coordinate of the upper left pixel
		return top_y;
	}

	@Override
	public int getBottom() {
		// method getBottom() return the y coordinate of the lower right pixel
		return bottom_y;
	}

	@Override
	public int getLeft() {
		// method getLeft() return the x coordinate of the lower right pixel
		return left_x;
	}

	@Override
	public int getRight() {
		// // method getRight() return the x coordinate of the lower right pixel
		return right_x;
	}

	@Override
	public Region intersect(Region other) throws NoIntersectionException {
	// method intersect(Region other) will return the intersected region with Region other.
	// In particular, the larger top will the top of intersection, the smaller bottom will 
	// be the bottom of the intersection. The larger left will be the left of the intersection.
	// The larger left will be the left of the intersection. If there is no intersection, then 
	// either the larger top will be larger than the smaller bottom (and thus illegal), or the 
	// larger left will be larger than the smaller right (and thus illegal). 
		//TODO: other this not intersect throws
		if(right_x < other.getLeft() || left_x > other.getRight() || 
				bottom_y < other.getTop()|| top_y > other.getBottom()) {
			throw new NoIntersectionException();
		}else {
			int new_top = (other.getTop()>top_y)?other.getTop():this.top_y;
			int new_bottom = (other.getBottom()<bottom_y)?other.getBottom():this.bottom_y;
			int new_left = (other.getLeft()>left_x)?other.getLeft():this.left_x;
			int new_right = (other.getRight()<right_x)?other.getRight():this.right_x;
			return new RegionImpl(new_left, new_top, new_right, new_bottom);
		}
}

	@Override
	public Region union(Region other) {
	// method union(Region other) will form the union region of Region other and
	// this region. In particular,  the smaller top (i.e., y coordinate of the upper
	// left pixel) between A and B and this will be the top of the union. The larger
	// bottom between A and B will be the bottom of the union. The smaller left 
	// between A and B will be the left of the union. The larger right between A and 
	// B will be the right of the union.
		if(other==null) {
			return this;
		}else {
			int new_top = (other.getTop()<top_y)?other.getTop():this.top_y;
			int new_bottom = (other.getBottom()>bottom_y)?other.getBottom():this.bottom_y;
			int new_left = (other.getLeft()<left_x)?other.getLeft():this.left_x;
			int new_right = (other.getRight()>right_x)?other.getRight():this.right_x;
		return new RegionImpl(new_left, new_top, new_right, new_bottom);
		}
	}
}

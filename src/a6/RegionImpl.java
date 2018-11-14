package a6;

public class RegionImpl implements Region {
	
	private int _left;
	private int _top;
	private int _right;
	private int _bottom;
	
	public RegionImpl(int left, int top, int right, int bottom) {
		if (left > right) {
			throw new IllegalArgumentException("Invalid geometry.");
		}
		if (top > bottom) {
			throw new IllegalArgumentException("Invalid geometry.");
		}
		_left = left;
		_top = top;
		_right = right;
		_bottom = bottom;
	}

	@Override
	public int getTop() {
		return _top;
	}

	@Override
	public int getBottom() {
		return _bottom;
	}

	@Override
	public int getLeft() {
		return _left;
	}

	@Override
	public int getRight() {
		return _right;
	}

	@Override
	public Region intersect(Region other) throws NoIntersectionException {
		if (other == null) {
			throw new NoIntersectionException();
		}
		if (other.getLeft() > this.getRight() || other.getRight() < this.getLeft() || other.getTop() > this.getBottom() || other.getBottom() < this.getTop()) {
			throw new NoIntersectionException();
		}
		
		int new_left = Math.max(other.getLeft(), this.getLeft());
		int new_top = Math.max(other.getTop(), this.getTop());
		int new_right = Math.min(other.getRight(), this.getRight());
		int new_bottom = Math.min(other.getBottom(), this.getBottom());
		Region intersection = new RegionImpl(new_left, new_top, new_right, new_bottom);
		
		return intersection;
	}

	@Override
	public Region union(Region other) {
		if (other == null) {
			return this;
		}
		
		int new_left = Math.min(other.getLeft(), this.getLeft());
		int new_top = Math.min(other.getTop(), this.getTop());
		int new_right = Math.max(other.getRight(), this.getRight());
		int new_bottom = Math.max(other.getBottom(), this.getBottom());
		Region union = new RegionImpl(new_left, new_top, new_right, new_bottom);
		
		return union;
	}

}

package a6test.cturgeon;

public class RegionImpl implements Region {

	private int left;
	private int top;
	private int right;
	private int bottom;
	protected Region region;

	public RegionImpl(int left, int top, int right, int bottom) {
		if (left > right || top > bottom) {
			throw new IllegalArgumentException("Invalid Dims");
		}
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	@Override
	public int getTop() {
		return top;
	}

	@Override
	public int getBottom() {
		return bottom;
	}

	@Override
	public int getLeft() {
		return left;
	}

	@Override
	public int getRight() {
		return right;
	}

	@Override
	public Region intersect(Region other) throws NoIntersectionException {
		if (other == null) {
			throw new NoIntersectionException();
		}
		int newTop = top;
		int newBottom = bottom;
		int newRight = right;
		int newLeft = left;

		if (top < other.getTop()) {
			newTop = other.getTop();
		}
		if (bottom > other.getBottom()) {
			newBottom = other.getBottom();
		}
		if (left < other.getLeft()) {
			newLeft = other.getLeft();
		}
		if (right > other.getRight()) {
			newRight = other.getRight();
		}
		if (newTop > newBottom || newRight < newLeft) {
			throw new NoIntersectionException();
		}
		return new RegionImpl(newLeft, newTop, newRight, newBottom);
	}

	@Override
	public Region union(Region other) {
		if (other == null) {
			return this;
		}
		int newTop = top;
		int newBottom = bottom;
		int newRight = right;
		int newLeft = left;

		if (top > other.getTop()) {
			newTop = other.getTop();
		}
		if (bottom < other.getBottom()) {
			newBottom = other.getBottom();
		}
		if (left > other.getLeft()) {
			newLeft = other.getLeft();
		}
		if (right < other.getRight()) {
			newRight = other.getRight();
		}
		return new RegionImpl(newLeft, newTop, newRight, newBottom);
	}
}

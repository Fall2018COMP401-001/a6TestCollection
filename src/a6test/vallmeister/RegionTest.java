package a6_test;

import a6.NoIntersectionException;
import a6.Region;
import a6.RegionImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class is for unit testing {@link RegionImpl}.
 *
 * @author Christian
 */
class RegionTest {

  /**
   * Tries to create a bad geometry by using left > right, top > bottom or both.
   */
  @Test
  void test_badGeometryRegion() {
    assertThrows(IllegalArgumentException.class, () -> new RegionImpl(5, 5, 4, 6));
    assertThrows(IllegalArgumentException.class, () -> new RegionImpl(0, 4, 1, 3));
    assertThrows(IllegalArgumentException.class, () -> new RegionImpl(1, 1, 0, 0));
  }

  /**
   * Creates a new {@link RegionImpl} and tests all getter methods.
   */
  @Test
  void test_getters() {
   Region valid_region = new RegionImpl(0, 0, 5, 5);
   assertEquals(0, valid_region.getLeft());
   assertEquals(0, valid_region.getTop());
   assertEquals(5, valid_region.getRight());
   assertEquals(5, valid_region.getBottom());
  }

  /**
   * Tests the intersection method with valid and invalid inputs.
   */
  @Test
  void test_intersection() {
    Region first = new RegionImpl(0, 0, 4, 4);
    assertThrows(NoIntersectionException.class, () -> first.intersect(null));

    Region second = new RegionImpl(1, 1, 5, 5);
    Region outside_first = new RegionImpl(5, 5, 6, 7);
    assertThrows(NoIntersectionException.class, () -> first.intersect(outside_first));
    Region third;
    try {
      third = first.intersect(second);
      assertEquals(1, third.getLeft());
      assertEquals(1, third.getTop());
      assertEquals(4, third.getBottom());
      assertEquals(4, third.getRight());
    } catch (NoIntersectionException except) {
      System.out.println("Something went wrong with intersection!");
    }
  }

  /**
   * Testing the union method with valid and invalid inputs.
   */
  @Test
  void test_union() {
    Region first = new RegionImpl(0, 0, 1, 1);
    Region second = new RegionImpl(0, 0, 2, 2);
    Region third = new RegionImpl(3, 3, 5, 5);
    Region fourth = new RegionImpl(1, 1, 3, 3);

    Region null_test = first.union(null);
    assertEquals(first, null_test);

    Region containing = second.union(first);
    assertEquals(0, containing.getLeft());
    assertEquals(0, containing.getTop());
    assertEquals(2, containing.getRight());
    assertEquals(2, containing.getBottom());

    Region distinct = second.union(third);
    assertEquals(0, distinct.getLeft());
    assertEquals(0, distinct.getTop());
    assertEquals(5, distinct.getRight());
    assertEquals(5, distinct.getBottom());

    Region overlapping = second.union(fourth);
    assertEquals(0, overlapping.getLeft());
    assertEquals(0, overlapping.getTop());
    assertEquals(3, overlapping.getRight());
    assertEquals(3, overlapping.getBottom());
  }
}

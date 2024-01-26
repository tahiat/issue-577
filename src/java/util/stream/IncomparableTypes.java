package java.util.stream;

import java.util.Arrays;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

class IncomparableTypes extends SpinedBuffer.OfPrimitive<Integer, int[], IntConsumer> {
  @Override
  public void forEach(Consumer<? super Integer> consumer) {
    spliterator().forEachRemaining(consumer);
  }

  @Override
  protected int[][] newArrayArray(int size) {
    return new int[size][];
  }

  @Override
  public int[] newArray(int size) {
    return new int[size];
  }

  @Override
  protected int arrayLength(int[] array) {
    return array.length;
  }

  @Override
  protected void arrayForEach(int[] array,
      int from, int to,
      IntConsumer consumer) {
    for (int i = from; i < to; i++)
      consumer.accept(array[i]);
  }

  @Override
  public PrimitiveIterator.OfInt iterator() {
    return Spliterators.iterator(spliterator());
  }

  public Spliterator.OfInt spliterator() {
    return new Splitr(0, spineIndex, 0, elementIndex);
  }

  class Splitr extends BaseSpliterator<Spliterator.OfInt> implements Spliterator.OfInt {
    Splitr(int firstSpineIndex, int lastSpineIndex,
        int firstSpineElementIndex, int lastSpineElementFence) {
      super(firstSpineIndex, lastSpineIndex,
          firstSpineElementIndex, lastSpineElementFence);
    }

    @Override
    Splitr newSpliterator(int firstSpineIndex, int lastSpineIndex,
        int firstSpineElementIndex, int lastSpineElementFence) {
      return new Splitr(firstSpineIndex, lastSpineIndex,
          firstSpineElementIndex, lastSpineElementFence);
    }

    @Override
    void arrayForOne(int[] array, int index, IntConsumer consumer) {
      consumer.accept(array[index]);
    }

    @Override
    Spliterator.OfInt arraySpliterator(int[] array, int offset, int len) {
      return Arrays.spliterator(array, offset, offset+len);
    }
  }
}
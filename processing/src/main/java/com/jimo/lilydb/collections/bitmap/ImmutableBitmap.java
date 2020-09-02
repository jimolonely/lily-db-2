package com.jimo.lilydb.collections.bitmap;

import org.roaringbitmap.BatchIterator;
import org.roaringbitmap.IntIterator;
import org.roaringbitmap.PeekableIntIterator;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 22:14
 */
public interface ImmutableBitmap {

    IntIterator iterator();

    default PeekableIntIterator peekableIterator() {
        return new PeekableIteratorAdapter<>(iterator());
    }

    default BatchIterator batchIterator() {
        return null;
    }

    int size();

    byte[] toBytes();

    boolean isEmpty();

    boolean get(int value);

    ImmutableBitmap intersection(ImmutableBitmap otherBitmap);
}

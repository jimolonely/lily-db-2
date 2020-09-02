package com.jimo.lilydb.collections.bitmap;

import org.roaringbitmap.IntIterator;
import org.roaringbitmap.PeekableIntIterator;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 22:23
 */
public class PeekableIteratorAdapter<TIntIterator extends IntIterator> implements PeekableIntIterator {

    public PeekableIteratorAdapter(TIntIterator iterator) {

    }

    @Override
    public void advanceIfNeeded(int i) {

    }

    @Override
    public int peekNext() {
        return 0;
    }

    @Override
    public PeekableIntIterator clone() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public int next() {
        return 0;
    }
}

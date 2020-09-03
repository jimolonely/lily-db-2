package com.jimo.lilydb.segment.vector;

import com.sun.istack.internal.Nullable;
import org.roaringbitmap.PeekableIntIterator;

import java.util.Arrays;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/3 8:58
 */
public class VectorSelectorUtils {

    public static boolean[] populateNullVector(@Nullable final boolean[] nullVector,
                                               final ReadableVectorOffset offset,
                                               final PeekableIntIterator nullIterator) {
        if (!nullIterator.hasNext()) {
            return null;
        }

        final boolean[] retVal;

        if (nullVector != null) {
            retVal = nullVector;
        } else {
            retVal = new boolean[offset.getMaxVectorSize()];
        }

        if (offset.isContiguous()) {
            final int startOffset = offset.getStartOffset();
            nullIterator.advanceIfNeeded(startOffset);
            if (!nullIterator.hasNext()) {
                return null;
            }
            for (int i = 0; i < offset.getCurrentVectorSize(); i++) {
                final int row = i + startOffset;
                nullIterator.advanceIfNeeded(row);
                if (!nullIterator.hasNext()) {
                    Arrays.fill(retVal, i, offset.getCurrentVectorSize(), false);
                    break;
                }
                retVal[i] = row == nullIterator.peekNext();
            }
        } else {
            final int[] currentOffsets = offset.getOffsets();
            nullIterator.advanceIfNeeded(currentOffsets[0]);
            if (!nullIterator.hasNext()) {
                return null;
            }
            for (int i = 0; i < offset.getCurrentVectorSize(); i++) {
                final int row = currentOffsets[i];
                nullIterator.advanceIfNeeded(row);
                if (!nullIterator.hasNext()) {
                    Arrays.fill(retVal, i, offset.getCurrentVectorSize(), false);
                    break;
                }
                retVal[i] = row == nullIterator.peekNext();
            }
        }
        return retVal;
    }
}

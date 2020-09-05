package com.jimo.lilydb.segment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public class VirtualColumns {

    public static final VirtualColumns EMPTY = new VirtualColumns(
            ImmutableList.of(),
            ImmutableMap.of(),
            ImmutableMap.of()
    );

    private VirtualColumns(
            List<VirtualColumn> virtualColumns,
            Map<String, VirtualColumn> withDotSupport,
            Map<String, VirtualColumn> withoutDotSupport
    ) {

    }

    public static VirtualColumns nullToEmpty(VirtualColumns virtualColumns) {
        return virtualColumns == null ? EMPTY : virtualColumns;
    }
}

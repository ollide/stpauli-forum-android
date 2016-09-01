package org.ollide.stpauliforum.di;

final class Modules {
    static Object[] list() {
        return new Object[]{
                new AndroidModule()
        };
    }

    private Modules() {
        // No instances.
    }
}

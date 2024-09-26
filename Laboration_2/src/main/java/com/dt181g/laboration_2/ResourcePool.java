package com.dt181g.laboration_2;

import java.util.concurrent.atomic.AtomicInteger;

public enum ResourcePool {
    INSTANCE;
    private final AtomicInteger resources = new AtomicInteger(50);

    public synchronized void modifyResources(int resource) {
        if (!(resources.get() + resource < 0)) {
            this.resources.addAndGet(resource);
        }
    }

    public int getResources() {
        return this.resources.get();
    }
}

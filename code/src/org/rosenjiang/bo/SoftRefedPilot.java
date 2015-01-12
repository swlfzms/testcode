package org.rosenjiang.bo;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class SoftRefedPilot extends SoftReference<Pilot> {
    public int key;
    
    public SoftRefedPilot(int key, Pilot referent, ReferenceQueue<Pilot> q) {
        super(referent, q);
        this.key = key;
    }
}
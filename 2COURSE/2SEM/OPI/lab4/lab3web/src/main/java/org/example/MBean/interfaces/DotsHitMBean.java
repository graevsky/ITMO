package org.example.MBean.interfaces;

import javax.management.MXBean;

@MXBean
public interface DotsHitMBean {
    int getTotalPoints();
    int getTotalHits();
    void resetCounters();


}

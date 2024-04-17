package org.example.MBean.interfaces;

import javax.management.MXBean;

@MXBean
public interface DotsHitMBean {
    int getTotalDots();
    int getTotalHits();
    void resetCounters();


}

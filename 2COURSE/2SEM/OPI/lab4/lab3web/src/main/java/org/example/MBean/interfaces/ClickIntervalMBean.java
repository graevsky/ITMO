package org.example.MBean.interfaces;

import javax.management.MXBean;

@MXBean
public interface ClickIntervalMBean {
    double getAverageInterval();
    void reset();
}

package org.example.MBean.classes;

import org.example.MBean.interfaces.ClickIntervalMBean;

import java.util.LinkedList;
import java.util.Queue;

public class ClickInterval implements ClickIntervalMBean {
    private final Queue<Long> clickTimes = new LinkedList<>();
    private final static int MAX_HISTORY = 100;

    @Override
    public double getAverageInterval() {
        if (clickTimes.size() < 2) {
            return 0;
        }
        long totalInterval = 0;
        Long prevTime = clickTimes.peek();
        for (Long time : clickTimes) {
            if (prevTime != time) {
                totalInterval += (time - prevTime);
                prevTime = time;
            }
        }
        return totalInterval / (double) (clickTimes.size() - 1);
    }

    @Override
    public void reset() {
        clickTimes.clear();
    }

    public void recordClick() {
        long now = System.currentTimeMillis();
        clickTimes.add(now);
        if (clickTimes.size() > MAX_HISTORY) {
            clickTimes.poll();
        }
    }

}

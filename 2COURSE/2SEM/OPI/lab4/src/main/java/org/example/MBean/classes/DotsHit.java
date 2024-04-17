package org.example.MBean.classes;

import org.example.MBean.interfaces.DotsHitMBean;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class DotsHit extends NotificationBroadcasterSupport implements DotsHitMBean {
    private int totalDots = 0;
    private int totalHits = 0;
    private long counter = 0;

    @Override
    public int getTotalDots(){
        return totalDots;
    }

    @Override
    public int getTotalHits(){
        return totalHits;
    }

    @Override
    public void resetCounters(){
        totalDots = 0;
        totalHits = 0;
    }



    public void addPoint(boolean isHit) {
        totalDots++;
        if (isHit) {
            totalHits++;
        }
        if (totalDots % 5 == 0) {
            Notification notification = new Notification(
                    "org.example.points.notification", this, counter++,
                    System.currentTimeMillis(), "Total points reached multiple of 5.");
            sendNotification(notification);
        }
    }


}

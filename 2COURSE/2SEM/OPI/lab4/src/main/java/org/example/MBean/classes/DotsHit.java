package org.example.MBean.classes;

import org.example.MBean.interfaces.DotsHitMBean;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class DotsHit extends NotificationBroadcasterSupport implements DotsHitMBean {
    private int totalPoints = 0;
    private int totalHits = 0;
    private long counter = 0;

    @Override
    public int getTotalPoints(){
        return totalPoints;
    }

    @Override
    public int getTotalHits(){
        return totalHits;
    }

    @Override
    public void resetCounters(){
        totalPoints = 0;
        totalHits = 0;
    }



    public void addPoint(boolean isHit) {
        totalPoints++;
        if (isHit) {
            totalHits++;
        }
        if (totalPoints % 5 == 0) {
            Notification notification = new Notification(
                    "org.example.points.notification", this, counter++,
                    System.currentTimeMillis(), "Total points reached multiple of 5.");
            sendNotification(notification);
        }
    }


}

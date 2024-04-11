package org.example.MBean;

import javax.management.Notification;
import javax.management.NotificationListener;

public class DotsHitListener implements NotificationListener {
    @Override
    public void handleNotification(Notification notification, Object handback){
        System.out.println("!!!Notification!!! " + notification.getMessage());
    }
}

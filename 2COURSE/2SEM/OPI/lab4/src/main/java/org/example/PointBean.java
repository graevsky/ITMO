package org.example;


import org.example.MBean.classes.ClickInterval;
import org.example.MBean.classes.DotsHit;
import org.example.MBean.DotsHitListener;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.management.*;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.lang.management.ManagementFactory;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;



@ManagedBean(name = "bean")
@ApplicationScoped
public class PointBean {

    private double x;

    private double y;

    private double r;
    private int timezoneOffset;

    private DotsHit dotsHit;
    private ClickInterval clickInterval;



    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("lab3");

    @PostConstruct
    public void init(){
        registerMBeans();
        addNotificationListener();
    }

    private void registerMBeans(){
        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();


            ObjectName dotsHitN     ame = new ObjectName("org.example.MBean.classes:name=dotsHit");
            dotsHit = new DotsHit();
            try {
                mBeanServer.registerMBean(dotsHit, dotsHitName);
            }catch (MBeanRegistrationException | InstanceAlreadyExistsException | NotCompliantMBeanException e){
                System.err.println("Error registering bean " + e.getMessage());
            }

            ObjectName clickIntervalName = new ObjectName("org.example.MBean.classes:name=clickInterval");
            clickInterval = new ClickInterval();
            try {
                mBeanServer.registerMBean(clickInterval,clickIntervalName);
            }catch (MBeanRegistrationException | InstanceAlreadyExistsException | NotCompliantMBeanException e){
                System.err.println("Error registering bean " + e.getMessage());
            }
        }catch (Exception e){
            System.err.println("Error creating MBean server " + e.getMessage());
        }
    }

    private void addNotificationListener() {
        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName dotsHitName = new ObjectName("org.example.MBean.classes:name=dotsHit");
            NotificationListener listener = new DotsHitListener();
            NotificationFilter filter = new NotificationFilterSupport();
            mBeanServer.addNotificationListener(dotsHitName, listener, filter, null);
        } catch (Exception e) {
            System.err.println("Error adding notification listener: " + e.getMessage());
        }
    }



    public int getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(int timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    private List<Result> results;


    public PointBean() {

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    @Transactional
    public String checkPoint() {
        EntityManager entityManager = EMF.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            long startTime = System.nanoTime();
            Utils service = new Utils(x,y,r);
            boolean hit = service.checkHit();
            dotsHit.addPoint(hit);
            long endTime = System.nanoTime();

            long duration = endTime - startTime;
            double durationTime = (double) duration / 1_000_000.0;
            String formattedTime = String.format("%.6f ms", durationTime);

            LocalDateTime currentTimeUtc = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
            LocalDateTime adjustedCurrentTime = currentTimeUtc.minusMinutes(timezoneOffset);
            String formattedSubmitTime = adjustedCurrentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            Result result = new Result(x, y, r, hit, formattedTime, formattedSubmitTime);

            boolean isValid = service.validate();
            result.setIsValid(isValid);
            entityManager.persist(result);
            transaction.commit();
            updateResults();

        } catch (PersistenceException e) {
            Logger.getLogger(PointBean.class.getName()).log(Level.SEVERE, "Ошибка при работе с базой данных ", e.getMessage());
        } catch (IllegalArgumentException e) {
            Logger.getLogger(PointBean.class.getName()).log(Level.WARNING, "Неверные входные параметры ", e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(PointBean.class.getName()).log(Level.SEVERE, "Неизвестная ошибка ", e.getMessage());
        } finally {
            entityManager.close();
        }

        return "main";
    }

    public List<Result> getResults() {
        EntityManager entityManager = EMF.createEntityManager();
        try {
            return entityManager.createQuery("SELECT r FROM Result r WHERE r.isValid = true ORDER BY r.id DESC", Result.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }




    private void updateResults() {
        results = getResults();
    }
    public void updateGraph(javax.faces.event.AjaxBehaviorEvent event) {
        //no server action performed
    }
    public void handleCanvasClick() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        x = Double.parseDouble(params.get("xValue"));
        y = Double.parseDouble(params.get("yValue"));
        r = Double.parseDouble(params.get("rValue"));
        timezoneOffset = Integer.parseInt(params.get("timezoneOffset"));

        clickInterval.recordClick();

        checkPoint();
    }


}

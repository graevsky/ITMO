package org.example.point;

import org.example.users.User;
import org.example.utils.Utils;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;


@Stateless
public class PointBean {

    @PersistenceContext(unitName = "lab4")
    private EntityManager entityManager;

    public PointBean() {
    }

    @Transactional
    public Response checkPoint(double x, double y, double r, int timezoneOffset, String username) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        long startTime = System.nanoTime();
        Utils service = new Utils(x, y, r);
        boolean hit = service.checkHit();
        long endTime = System.nanoTime();

        long duration = endTime - startTime;
        double durationTime = (double) duration / 1_000_000.0;
        String formattedTime = String.format("%.6f ms", durationTime);

        boolean isValid = service.validate();

        LocalDateTime currentTimeUtc = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime adjustedCurrentTime = currentTimeUtc.minusMinutes(timezoneOffset);
        String formattedSubmitTime = adjustedCurrentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        User user = entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                .setParameter("login", username)
                .getSingleResult();
        Result result = new Result(x, y, r, hit, formattedTime, formattedSubmitTime, user);
        result.setIs_valid(isValid);


        try {
            entityManager.persist(result);
            transaction.commit();

        } catch (PersistenceException e) {
            Logger.getLogger(PointBean.class.getName()).log(Level.SEVERE, "Ошибка при работе с базой данных ", e.getMessage());
        }

        return Response.ok(result).build();

    }

    public Response getResults(String username) {
        User user = entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                .setParameter("login", username)
                .getSingleResult();
        List<Result> results = entityManager.createQuery("SELECT r FROM Result r WHERE r.user = :user and r.is_valid = true", Result.class)
                .setParameter("user", user)
                .getResultList();
        return Response.ok(results).build();
    }


}
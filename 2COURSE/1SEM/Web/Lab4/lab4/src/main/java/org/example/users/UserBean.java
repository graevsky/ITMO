package org.example.users;

import org.example.utils.Utils;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Stateless
public class UserBean {

    @PersistenceContext(unitName = "lab4")
    private EntityManager entityManager;

    @Transactional
    public User createUser(String login, String password) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        User user = new User();
        user.setLogin(login);
        user.setPassword(Utils.hashPassword(password));
        entityManager.persist(user);
        transaction.commit();
        return user;
    }

    public User findUserByLogin(String login) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}

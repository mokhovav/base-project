package com.mokhovav.base;

import com.mokhovav.base.databases.sql.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BaseApplication.class })
@Transactional
class BaseApplicationTests {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private MainController controller;


    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void whenBootstrapHibernateSession_thenNoException() {
        Session session = sessionFactory.getCurrentSession();
        User newEntity = new User();
        long newEntityID = (long) session.save(newEntity);
        User searchEntity = session.find(User.class, newEntityID);
        assertNotNull(searchEntity);
    }

    @Test
    public void whenProgrammaticTransactionCommit_thenEntityIsInDatabase() {
        assertTrue(TestTransaction.isActive());
        //Save an entity and commit.
        Session session = sessionFactory.getCurrentSession();
        User newEntity = new User();
        long newEntityId = (long) session.save(newEntity);
        User searchEntity = session.find(User.class, newEntityId);
        assertNotNull(searchEntity);
        //Determine whether the current test-managed transaction has been flagged for rollback or flagged for commit.
        assertTrue(TestTransaction.isFlaggedForRollback());
        //Flag the current test-managed transaction for commit.
        TestTransaction.flagForCommit();
        TestTransaction.end();
        assertFalse(TestTransaction.isFlaggedForRollback());
        assertFalse(TestTransaction.isActive());

        //Check that the entity is still there in a new transaction,
        //then delete it, but don't commit.
        TestTransaction.start();
        assertTrue(TestTransaction.isFlaggedForRollback());
        assertTrue(TestTransaction.isActive());
        session = sessionFactory.getCurrentSession();
        searchEntity = session.find(User.class, newEntityId);
        assertNotNull(searchEntity);
        session.delete(searchEntity);
        session.flush();
        TestTransaction.end();
        assertFalse(TestTransaction.isActive());

        //Check that the entity is still there in a new transaction,
        //then delete it and commit.
        TestTransaction.start();
        session = sessionFactory.getCurrentSession();
        searchEntity = session.find(User.class, newEntityId);
        assertNotNull(searchEntity);
        session.delete(searchEntity);
        session.flush();
        assertTrue(TestTransaction.isActive());
        TestTransaction.flagForCommit();
        TestTransaction.end();
        assertFalse(TestTransaction.isActive());

        //Check that the entity is no longer there in a new transaction.
        TestTransaction.start();
        assertTrue(TestTransaction.isActive());
        session = sessionFactory.getCurrentSession();
        searchEntity = session.find(User.class, newEntityId);
        Assert.assertNull(searchEntity);
    }

    @Test
    @Commit
    public void givenTransactionCommitDefault_whenProgrammaticTransactionCommit_thenEntityIsInDatabase() {
        assertTrue(TestTransaction.isActive());
        //Save an entity and commit.
        Session session = sessionFactory.getCurrentSession();
        User newEntity = new User();
        long newEntityId = (long) session.save(newEntity);
        User searchEntity = session.find(User.class, newEntityId);
        assertNotNull(searchEntity);
        assertFalse(TestTransaction.isFlaggedForRollback());
        TestTransaction.end();
        assertFalse(TestTransaction.isFlaggedForRollback());
        assertFalse(TestTransaction.isActive());

        //Check that the entity is still there in a new transaction,
        //then delete it, but don't commit.
        TestTransaction.start();
        assertFalse(TestTransaction.isFlaggedForRollback());
        assertTrue(TestTransaction.isActive());
        session = sessionFactory.getCurrentSession();
        searchEntity = session.find(User.class, newEntityId);
        assertNotNull(searchEntity);
        session.delete(searchEntity);
        session.flush();
        TestTransaction.flagForRollback();
        TestTransaction.end();
        assertFalse(TestTransaction.isActive());

        //Check that the entity is still there in a new transaction,
        //then delete it and commit.
        TestTransaction.start();
        session = sessionFactory.getCurrentSession();
        searchEntity = session.find(User.class, newEntityId);
        assertNotNull(searchEntity);
        session.delete(searchEntity);
        session.flush();
        assertTrue(TestTransaction.isActive());
        TestTransaction.end();
        assertFalse(TestTransaction.isActive());

        //Check that the entity is no longer there in a new transaction.
        TestTransaction.start();
        assertTrue(TestTransaction.isActive());
        session = sessionFactory.getCurrentSession();
        searchEntity = session.find(User.class, newEntityId);
        Assert.assertNull(searchEntity);
    }


}

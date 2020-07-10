package com.mokhovav.base;

import com.mokhovav.base.JUnit.TestEntity;
import com.mokhovav.base.annotations.TestCondition;
import com.mokhovav.base.annotations.TestConditionPrefix;

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
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BaseApplication.class })
@TestConditionPrefix(prefix = "project.config")
@Transactional
class BaseApplicationTests {

    @Autowired private SessionFactory sessionFactory;
    @Autowired private MainController controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    @TestCondition(property = "first")
    public void testFirst() {
        assertTrue(true);
    }

    @Test
    @TestCondition(property = "second")
    public void testSecond() {
        fail();
    }


    @Test
    public void whenHibernateSession_thenNoException() {
        if(!TestTransaction.isActive()) TestTransaction.start();
        Session session = sessionFactory.getCurrentSession();
        TestEntity newEntity = new TestEntity("test");
        long newEntityID = (long) session.save(newEntity);
        TestEntity searchEntity = session.find(TestEntity.class, newEntityID);
        assertNotNull(searchEntity);
        TestTransaction.end();
    }

    @Test
    public void whenTransactionCommit_thenEntityIsInDatabase() {
        if(!TestTransaction.isActive()) TestTransaction.start();
        assertTrue(TestTransaction.isActive());
        //Save an entity and commit.
        Session session = sessionFactory.getCurrentSession();
        TestEntity newEntity = new TestEntity("test");
        long newEntityId = (long) session.save(newEntity);
        TestEntity searchEntity = session.find(TestEntity.class, newEntityId);
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
        searchEntity = session.find(TestEntity.class, newEntityId);
        assertNotNull(searchEntity);
        session.delete(searchEntity);
        session.flush();
        TestTransaction.end();
        assertFalse(TestTransaction.isActive());

        //Check that the entity is still there in a new transaction,
        //then delete it and commit.
        TestTransaction.start();
        session = sessionFactory.getCurrentSession();
        searchEntity = session.find(TestEntity.class, newEntityId);
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
        searchEntity = session.find(TestEntity.class, newEntityId);
        Assert.assertNull(searchEntity);
        TestTransaction.end();
    }

    @Test
    @Commit
    public void givenTransactionCommitDefault_whenTransactionCommit_thenEntityIsInDatabase() {
        if(!TestTransaction.isActive()) TestTransaction.start();
        assertTrue(TestTransaction.isActive());
        //Save an entity and commit.
        Session session = sessionFactory.getCurrentSession();
        TestEntity newEntity = new TestEntity("test");
        long newEntityId = (long) session.save(newEntity);
        TestEntity searchEntity = session.find(TestEntity.class, newEntityId);
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
        searchEntity = session.find(TestEntity.class, newEntityId);
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
        searchEntity = session.find(TestEntity.class, newEntityId);
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
        searchEntity = session.find(TestEntity.class, newEntityId);
        Assert.assertNull(searchEntity);
        TestTransaction.end();
    }



    //TODO: add tests of exceptions
    //TODO: add tests of validations
}

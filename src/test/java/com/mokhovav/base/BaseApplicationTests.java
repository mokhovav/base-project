package com.mokhovav.base;

import com.mokhovav.base.annotations.TestCondition;
import com.mokhovav.base.annotations.TestConditionPrefix;
import com.mokhovav.base.databases.SQL.JUnit.TestSQLEntity;
import com.mokhovav.base.databases.SQL.SQLService;
import com.mokhovav.base.databases.noSQL.JUnit.TestNoSQLEntity;
import com.mokhovav.base.databases.noSQL.NoSQLService;
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

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BaseApplication.class })
@TestConditionPrefix(prefix = "project.config")
@Transactional
class BaseApplicationTests {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private SQLService sqlService;

    @Autowired
    private NoSQLService noSQLService;

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
        TestSQLEntity newEntity = new TestSQLEntity("test");
        long newEntityID = (long) session.save(newEntity);
        TestSQLEntity searchEntity = session.find(TestSQLEntity.class, newEntityID);
        assertNotNull(searchEntity);
        TestTransaction.end();
    }

    @Test
    public void whenTransactionCommit_thenEntityIsInDatabase() {
        if(!TestTransaction.isActive()) TestTransaction.start();
        assertTrue(TestTransaction.isActive());
        //Save an entity and commit.
        Session session = sessionFactory.getCurrentSession();
        TestSQLEntity newEntity = new TestSQLEntity("test");
        long newEntityId = (long) session.save(newEntity);
        TestSQLEntity searchEntity = session.find(TestSQLEntity.class, newEntityId);
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
        searchEntity = session.find(TestSQLEntity.class, newEntityId);
        assertNotNull(searchEntity);
        session.delete(searchEntity);
        session.flush();
        TestTransaction.end();
        assertFalse(TestTransaction.isActive());

        //Check that the entity is still there in a new transaction,
        //then delete it and commit.
        TestTransaction.start();
        session = sessionFactory.getCurrentSession();
        searchEntity = session.find(TestSQLEntity.class, newEntityId);
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
        searchEntity = session.find(TestSQLEntity.class, newEntityId);
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
        TestSQLEntity newEntity = new TestSQLEntity("test");
        long newEntityId = (long) session.save(newEntity);
        TestSQLEntity searchEntity = session.find(TestSQLEntity.class, newEntityId);
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
        searchEntity = session.find(TestSQLEntity.class, newEntityId);
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
        searchEntity = session.find(TestSQLEntity.class, newEntityId);
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
        searchEntity = session.find(TestSQLEntity.class, newEntityId);
        Assert.assertNull(searchEntity);
        TestTransaction.end();
    }

    @Test
    void hibernateTest(){
        TestSQLEntity entity1 = new TestSQLEntity("entity1");
        TestSQLEntity entity2 = new TestSQLEntity("entity2");
        Long id1 = sqlService.save(entity1);
        Long id2 = sqlService.save(entity2);
        List<TestSQLEntity> entities = sqlService.findAll(TestSQLEntity.class);
        assertEquals(2, entities.size());
        sqlService.delete(entity1);
        sqlService.delete(entity2);
    }

    @Test
    void mongoDBTest(){
        String id = noSQLService.save(new TestNoSQLEntity("mongo"));
        TestNoSQLEntity dbEntity = (TestNoSQLEntity) noSQLService.findById(id, TestNoSQLEntity.class);
        dbEntity.setName(id);
        noSQLService.update(dbEntity);

        for (TestNoSQLEntity e : (List <TestNoSQLEntity>)noSQLService.findAll(TestNoSQLEntity.class)) {
            assertEquals(id, e.getName());
            noSQLService.delete(e);
        }
    }

    //TODO: add tests of exceptions
    //TODO: add tests of validations
}
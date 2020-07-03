package com.mokhovav.base;

import com.mokhovav.base.databases.BaseSQLEntity;
import com.mokhovav.base.databases.sql.User;
import com.mokhovav.base.exceptions.BaseRestException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @Autowired
    private SessionFactory sessionFactory;

    @GetMapping
    public String mainMapping() throws BaseRestException {
        Session session = sessionFactory.getCurrentSession();
        User newEntity = new User();
        newEntity.setId((long)1);
        session.save(newEntity);

        User searchEntity = session.find(User.class, (long)1);
        throw new BaseRestException("1","2","3");
    }
}

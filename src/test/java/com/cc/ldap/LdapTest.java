package com.cc.ldap;

import com.cc.ldap.domain.User;
import com.cc.ldap.domain.UserRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author carlosxiao
 * @classname LdapTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LdapApplication.class)
public class LdapTest {

    @Resource
    private UserRepo userRepo;


    @Test
    public void findAll() {
        Iterable<User> all = userRepo.findAll();
        all.forEach(p -> System.out.println(p));
    }
}

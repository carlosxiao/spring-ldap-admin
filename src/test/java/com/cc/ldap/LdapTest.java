package com.cc.ldap;

import com.cc.ldap.domain.PersonRepository;
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
    private PersonRepository personRepository;


    @Test
    public void findAll() throws Exception {
        personRepository.findAll().forEach(p -> {
            System.out.println(p);
        });
    }
}

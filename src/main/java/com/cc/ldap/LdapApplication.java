package com.cc.ldap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;

/**
 * @author carlosxiao
 */
@SpringBootApplication
@EnableLdapRepositories("com.cc.ldap.domain")
public class LdapApplication {

    public static void main(String[] args) {
        SpringApplication.run(LdapApplication.class);
    }
}

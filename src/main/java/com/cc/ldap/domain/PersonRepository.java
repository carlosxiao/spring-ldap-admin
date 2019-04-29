package com.cc.ldap.domain;

import org.springframework.data.repository.CrudRepository;

import javax.naming.Name;

/**
 * @author carlosxiao
 * @classname PersonRepository
 */
public interface PersonRepository extends CrudRepository<Person, Name> {
}

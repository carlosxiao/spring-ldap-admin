/*
 * Copyright 2005-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cc.ldap.web;

import com.cc.ldap.domain.Group;
import com.cc.ldap.domain.GroupRepo;
import com.cc.ldap.domain.User;
import com.cc.ldap.service.UserService;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Mattias Hellborg Arthursson
 */
@Controller
public class GroupController {

    @Resource
    private GroupRepo groupRepo;

    @Resource
    private UserService userService;

    @RequestMapping(value = "/groups", method = GET)
    public String listGroups(ModelMap map) {
        map.put("groups", groupRepo.getAllGroupNames());
        return "listGroups";
    }

    @RequestMapping(value = "/newGroup", method = GET)
    public String initNewGroup() {
        return "newGroup";
    }

    @RequestMapping(value = "/groups", method = POST)
    public String newGroup(Group group) {
        groupRepo.create(group);

        return "redirect:groups/" + group.getName();
    }

    @RequestMapping(value = "/groups/{name}", method = GET)
    public String editGroup(@PathVariable String name, ModelMap map) {
        Group foundGroup = groupRepo.findByName(name);
        map.put("group", foundGroup);

        final Set<User> groupMembers = userService.findAllMembers(foundGroup.getMembers());
        map.put("members", groupMembers);

        Iterable<User> otherUsers = Iterables.filter(userService.findAll(), new Predicate<User>() {
            @Override
            public boolean apply(User user) {
                return !groupMembers.contains(user);
            }
        });
        map.put("nonMembers", Lists.newLinkedList(otherUsers));

        return "editGroup";
    }

    @RequestMapping(value = "/groups/{name}/members", method = POST)
    public String addUserToGroup(@PathVariable String name, @RequestParam String userId) {
        Group group = groupRepo.findByName(name);
        group.addMember(userService.toAbsoluteDn(LdapUtils.newLdapName(userId)));

        groupRepo.save(group);

        return "redirect:/groups/" + name;
    }

    @RequestMapping(value = "/groups/{name}/members", method = DELETE)
    public String removeUserFromGroup(@PathVariable String name, @RequestParam String userId) {
        Group group = groupRepo.findByName(name);
        group.removeMember(userService.toAbsoluteDn(LdapUtils.newLdapName(userId)));

        groupRepo.save(group);

        return "redirect:/groups/" + name;
    }
}

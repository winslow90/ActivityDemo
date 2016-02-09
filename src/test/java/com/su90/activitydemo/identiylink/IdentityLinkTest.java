/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.su90.activitydemo.identiylink;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.junit.Test;

/**
 * 
 * @author superman90
 */
public class IdentityLinkTest {
	@Test
	public void test(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		Group group = new GroupEntity();
		group.setName("咨询部");
		User user1 = new UserEntity();
		user1.setFirstName("aaa");
		User user2 = new UserEntity();
		user2.setFirstName("bbb");
		processEngine.getIdentityService()
		.saveUser(user1);
		processEngine.getIdentityService().saveUser(user2);
		processEngine.getIdentityService()
		.saveGroup(group);
		processEngine.getIdentityService()
		.createMembership(user1.getId(),group.getId());
		processEngine.getIdentityService()
		.createMembership(user2.getId(), group.getId());
		
	}
}
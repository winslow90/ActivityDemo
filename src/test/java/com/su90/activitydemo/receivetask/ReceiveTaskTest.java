/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.su90.activitydemo.receivetask;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

/**
 * 不需要人工参与
 *    例如：备份档案    短信通知信息等
 * @author zd
 *
 */
public class ReceiveTaskTest {
	@Test
	public void testDeploy(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRepositoryService()
		.createDeployment()
		.addClasspathResource("com/itheima11/activiti/receivetask/receive.bpmn")
		.deploy();
	}
	
	@Test
	public void testStartPI(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRuntimeService()
		.startProcessInstanceById("myProcess:1:3904");
	}
	@Test
	public void testFinishApplicator(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getTaskService()
		.complete("4004");
	}
	
	
	@Test
	public void testSignal(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRuntimeService()
		/**
		 * 让token往下执行
		 */
		.signal("4001");
	}
}
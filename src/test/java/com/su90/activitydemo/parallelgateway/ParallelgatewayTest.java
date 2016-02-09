/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.su90.activitydemo.parallelgateway;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ParallelgatewayTest {
	@Test
	public void testDeploy(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRepositoryService()
		.createDeployment()
		.addClasspathResource("com/su90/activitydemo/parallelgateway/parallelgateway.bpmn")
		.deploy();
	}
	
	@Test
	public void testStartPI(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRuntimeService()
		.startProcessInstanceById("parallel:1:1204");
	}
	
	/**
	 * 存在并发情况，查询出来的是两个或者两个以上的结果
	 */
	@Test
	public void testQueryTaskByPiid(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<Task> tasks = processEngine.getTaskService()
		.createTaskQuery()
		.processInstanceId("4401")
		.list();
		for (Task task : tasks) {
			System.out.println(task.getId());
		}
	}
	
	/**
	 * 存在并发情况，查询出来的是一个结果
	 */
	@Test
	public void testQueryTaskByExecutionId(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		Task task = processEngine.getTaskService()
		.createTaskQuery()
		.executionId("4404")
		.singleResult();
		System.out.println(task.getName());
	}
}
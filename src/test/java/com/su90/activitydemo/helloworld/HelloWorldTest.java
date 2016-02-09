/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.su90.activitydemo.helloworld;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 1、部署流程
 * 2、启动流程实例
 * 3、请假人发出请假申请
 * 4、经纪人查看任务
 * 5、经纪人审批
 * 6、最终的boss审批
 * @author zd
 *
 */
public class HelloWorldTest {
	/**
	 * 部署
	 */
	@Test
	public void testDeploy(){
		//得到了流程引擎
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRepositoryService()
		.createDeployment()
		.addClasspathResource("qingjia.bpmn")
		.addClasspathResource("qingjia.png")
		.deploy();
	}
	
	/**
	 * 启动流程实例
	 */
	@Test
	public void testStartProcessInstance(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRuntimeService()
		.startProcessInstanceById("qingjia:1:4");
	}
	
	/**
	 * 完成请假申请
	 */
	@Test
	public void testQingjia(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getTaskService()
		.complete("104");
	}
	
	/**
	 * 范冰冰的经纪人查询当前正在执行任务
	 */
	@Test
	public void testQueryTask(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<Task> tasks = processEngine.getTaskService()
		.createTaskQuery()
		.taskAssignee("范冰冰的经纪人")
		.list();
		for (Task task : tasks) {
			System.out.println(task.getName());
		}
	}
	
	/**
	 * 范冰冰的经纪人完成任务
	 */
	@Test
	public void testFinishTask_manager(){
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		engine.getTaskService()
		.complete("202");
	}
	
	/**
	 * 完成终极boss的任务
	 */
	@Test
	public void testFinishTask_Boss(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getTaskService()
		.complete("302");
	}
}
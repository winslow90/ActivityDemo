/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.su90.activitydemo.task;

/**
 *
 * @author superman90
 */
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

/**
 * 任务
 *   1、概念
 *         需要有人进行审批或者申请的为任务
 *   2、任务的执行人的情况
 *       1、当没有进入该节点之前，就可以确定任务的执行人
 *          <userTask id="请假申请" name="请假申请" activiti:assignee="#{userId}"></userTask>
 *          在进入该节点之前，必须通过流程变量给 userId赋值
 *       2、有可能一个任务节点的执行人是固定的
 *       3、如果当前的流程实例正在执行自荐信审批，这个时候，自荐信审批没有任务执行人，只有当咨询员登录系统以后才能给该任务赋值执行人
 *       4、一个任务节点有n多人能够执行该任务，但是只要有一个人执行完毕就完成该任务了：组任务
 * @author zd
 *
 */
public class Task1Test {
	@Test
	public void testDeploy(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRepositoryService()
		.createDeployment()
		.addClasspathResource("com/su90/activitydemo/task/qingjia.bpmn")
		.deploy();
	}
	
	/**
	 * 启动流程实例
	 *    可以设置一个流程变量
	 */
	@Test
	public void testStartPI(){
		/**
		 * 流程变量
		 *   给<userTask id="请假申请" name="请假申请" activiti:assignee="#{userId}"></userTask>
		 *     的userId赋值
		 */
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("userId", "狗蛋");
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRuntimeService()
		.startProcessInstanceById("qingjia:1:604",variables);
	}
	
	/**
	 * 在完成请假申请的任务的时候，给部门经理审批的节点赋值任务的执行人
	 */
	@Test
	public void testFinishTask_Manager(){
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("manager", "狗蛋爹");
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getTaskService()
		.complete("705", variables); //完成任务的同时设置流程变量
	}
	
	/**
	 * 在完成部门经理审批的情况下，给总经理节点赋值
	 */
	@Test
	public void testFinishTask_Boss(){
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("boss", "狗蛋爷");
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getTaskService()
		.complete("803", variables); //完成任务的同时设置流程变量
	}
	
	/**
	 * 结束流程实例
	 */
	@Test
	public void testFinishTask(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getTaskService()
		.complete("903");
	}
}
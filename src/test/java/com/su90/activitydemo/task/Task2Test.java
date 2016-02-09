/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.su90.activitydemo.task;

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
 *          <userTask id="部门经理审批" name="部门经理审批">
		      <extensionElements>
		        <activiti:taskListener event="create" class="com.itheima11.activiti.task.MyTaskListener"></activiti:taskListener>
		      </extensionElements>
		    </userTask>
		             当流程实例进入该userTask节点的时候，立刻执行MyTaskListener的notify方法
		             
		           也可以通过程序的方式来进行设置
		    processEngine.getTaskService()
		      .setAssignee("2402", "狗蛋爷"); //给taskId为"2402"的任务赋值执行人
 *       4、一个任务节点有n多人能够执行该任务，但是只要有一个人执行完毕就完成该任务了：组任务
 * @author zd
 *
 */
public class Task2Test {
	@Test
	public void testDeploy(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRepositoryService()
		.createDeployment()
		.addClasspathResource("com/su90/activitydemo/task/qingjia2.bpmn")
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
		variables.put("applicator", "狗蛋");
		/**
		 * 该流程变量可以在MyTaskListener中获取到值
		 */
		variables.put("aaa", "aaa");
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRuntimeService()
		.startProcessInstanceById("qingjia2:1:1104",variables);
	}
	
	/**
	 * 完成请假申请的任务
	 */
	@Test
	public void testFinishApplicator(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getTaskService()
		.complete("2606");
	}
	
	/**
	 * 完成部门经理审批的任务
	 */
	@Test
	public void testFinishManager(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getTaskService()
		.complete("2302");
	}
	
	/**
	 * 通过程序的方式来设置当前正在执行任务的执行人
	 */
	@Test
	public void testAssignTask(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getTaskService()
		.setAssignee("2402", "狗蛋爷"); //给taskId为"2402"的任务赋值执行人
	}
}
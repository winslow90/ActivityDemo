/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.su90.activitydemo.task;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
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
 * @author superman90
 *
 */
public class Task3Test {
	@Test
	public void testDeploy(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRepositoryService()
		.createDeployment()
		.addClasspathResource("com/itheima11/activiti/task/qingjia3.bpmn")
		.deploy();
	}
	
	/**
	 * 启动流程实例
	 *    当启动流程实例以后，进入到了电脑维修的任务，而该任务是一个组任务
	 *    	<userTask id="电脑维修" name="电脑维修" activiti:candidateUsers="工程师1,工程师2,工程师3">
	 *    	</userTask>
	 *    所以candidateUsers指向的组任务的候选人被存储在
	 *    	act_ru_identitylink表
	 *         临时表，如果该任务执行完毕以后，该任务的候选人就从这张表中删除掉了
	 *      act_hi_identitylink表
	 *         历史表，是永久性的表
	 */
	@Test
	public void testStartPI(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRuntimeService()
		.startProcessInstanceById("grouptask:1:2804");
	}

	/**
	 * 根据任务ID查询候选人
	 */
	@Test
	public void testQueryCandidateByTaskId(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<IdentityLink> identityLinks = processEngine.getTaskService()
		.getIdentityLinksForTask("2904");
		for (IdentityLink identityLink : identityLinks) {
			System.out.println(identityLink.getUserId());
		}
	}
	
	/**
	 * 根据PIID查询候选人
	 */
	@Test
	public void testQueryCandidateByPIId(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<IdentityLink> identityLinks = processEngine.getRuntimeService()
				.getIdentityLinksForProcessInstance("2901");
		for (IdentityLink identityLink : identityLinks) {
			System.out.println(identityLink.getUserId());
		}
	}
	
	/**
	 * 根据候选人查询任务
	 */
	@Test
	public void testQueryTaskByCandidate(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<Task> tasks = processEngine.getTaskService()
		.createTaskQuery()
		.taskCandidateUser("工程师1")
		.list();
		for (Task task : tasks) {
			System.out.println(task.getName());
		}
	}
	
	/**
	 * 认领任务
	 */
	@Test
	public void testClaimTask(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getTaskService()
		.claim("2904", "aaa");
	}
}
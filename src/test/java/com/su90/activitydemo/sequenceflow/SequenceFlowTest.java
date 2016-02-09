/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.su90.activitydemo.sequenceflow;


import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;
/**
 * 
 * @author superman90
 */
public class SequenceFlowTest {
	@Test
	public void testDeploy(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRepositoryService()
		.createDeployment()
		.addClasspathResource("com/su90/activitydemo/sequenceflow/qingjia.bpmn")
		.deploy();
	}
	
	@Test
	public void testStartPI(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getRuntimeService()
		.startProcessInstanceById("qingjia:5:3504");
	}
	
	/**
	 * 完成请假申请的任务，并且设置流程变量
	 */
	@Test
	public void testFinishTask_Applicator(){
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("days", 3);
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getTaskService()
		.complete("3604",variables);
	}
	
	/**
	 * 完成部门经理审批的任务
	 */
	@Test
	public void testFinishTask_Manager(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		processEngine.getTaskService()
		.complete("3703");
	}
}
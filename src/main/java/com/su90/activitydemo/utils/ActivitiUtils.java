/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.su90.activitydemo.utils;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/**
 * 
 * @author superman90
 */
public class ActivitiUtils {
	/**
	 * 当前用户-->当前用户正在执行的任务--->当前正在执行的任务的piid-->该任务所在的流程实例
	 * @param assignee
	 * @return
	 */
	public static List<ProcessInstance> getPIByUser(String assignee){
		List<ProcessInstance> pis = new ArrayList<ProcessInstance>();
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		/**
		 * 该用户正在执行的任务
		 */
		List<Task> tasks = processEngine.getTaskService()
		.createTaskQuery()
		.taskAssignee(assignee)
		.list();
		for (Task task : tasks) {
			/**
			 * 根据task-->piid-->pi
			 */
			String piid = task.getProcessInstanceId();
			ProcessInstance pi = processEngine.getRuntimeService()
			.createProcessInstanceQuery()
			.processInstanceId(piid)
			.singleResult();
			pis.add(pi);
		}
		return pis;
	}
	
	/**
	 * 根据当前的登录人能够推导出所在的流程定义
	 */
	public static void getProcessInstance(String assignee){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List<Task> tasks = processEngine.getTaskService()
		.createTaskQuery()
		.taskAssignee(assignee)
		.list();
		for (Task task : tasks) {
			String pdid = task.getProcessDefinitionId();
			ProcessDefinition processDefinition = processEngine.getRepositoryService()
			.createProcessDefinitionQuery()
			.processDefinitionId(pdid)
			.singleResult();
		}
	}
}
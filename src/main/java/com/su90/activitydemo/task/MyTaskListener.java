/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.su90.activitydemo.task;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
/**
 * 
 * @author superman90
 */
public class MyTaskListener implements TaskListener{
	/**
	 * 给MyTaskListener所在的userTask赋值任务的执行人
	 */
	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		/**
		 * 任务的执行人可以动态的赋值
		 *   1、流程变量
		 *        可以通过提取流程变量的方式给任务赋值执行人
                 *          在创建流程时添加 com.su90.activitydemo.task.Task2Test::testStartPI();
		 *   2、可以操作数据库
		 *       WebApplicationContext ac = WebApplicationContextUtils
		 *       	.getWebApplicationContext(ServletActionContext.getServletContext());
				IEmployeeService employeeService = (IEmployeeService) ac.getBean("employeeService");   
		 */
		String value = (String)delegateTask.getVariable("aaa");
		delegateTask.setAssignee(value);
	}
}
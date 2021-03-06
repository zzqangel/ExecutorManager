package com.cooper.proxy.db;

// Generated 2016-12-26 08:25:14 by htoa Tools 1.0

import java.util.Date;

/**
 * HiDbThreadLogDetail generated by ZZQ
 */
public class HiDbThreadLogDetail implements java.io.Serializable {

	private String threadLogDetailId;
	private String threadLogId;
	private String threadClassName;
	private Date invokeStartDate;
	private Date invokeEndDate;
	private String callClass;
	private String callMethod;
	private String callStack;
	private Integer stopFlg;
	private String invokeClassName;
	private String userId;
	private Integer queueAble;
	private String exceptionStack;

	public HiDbThreadLogDetail() {
	}

	public HiDbThreadLogDetail(String threadLogDetailId) {
		this.threadLogDetailId = threadLogDetailId;
	}

	public HiDbThreadLogDetail(String threadLogDetailId, String threadLogId, String threadClassName, 
			Date invokeStartDate, Date invokeEndDate, String callClass, 
			String callMethod, String callStack, Integer stopFlg, 
			String invokeClassName, String userId, Integer queueAble, 
			String exceptionStack) {
		this.threadLogDetailId = threadLogDetailId;
		this.threadLogId = threadLogId;
		this.threadClassName = threadClassName;
		this.invokeStartDate = invokeStartDate;
		this.invokeEndDate = invokeEndDate;
		this.callClass = callClass;
		this.callMethod = callMethod;
		this.callStack = callStack;
		this.stopFlg = stopFlg;
		this.invokeClassName = invokeClassName;
		this.userId = userId;
		this.queueAble = queueAble;
		this.exceptionStack = exceptionStack;
	}

	public String getThreadLogDetailId() {
		return this.threadLogDetailId;
	}

	public void setThreadLogDetailId(String threadLogDetailId) {
		this.threadLogDetailId = threadLogDetailId;
	}

	public String getThreadLogId() {
		return this.threadLogId;
	}

	public void setThreadLogId(String threadLogId) {
		this.threadLogId = threadLogId;
	}

	public String getThreadClassName() {
		return this.threadClassName;
	}

	public void setThreadClassName(String threadClassName) {
		this.threadClassName = threadClassName;
	}

	public Date getInvokeStartDate() {
		return this.invokeStartDate;
	}

	public void setInvokeStartDate(Date invokeStartDate) {
		this.invokeStartDate = invokeStartDate;
	}

	public Date getInvokeEndDate() {
		return this.invokeEndDate;
	}

	public void setInvokeEndDate(Date invokeEndDate) {
		this.invokeEndDate = invokeEndDate;
	}

	public String getCallClass() {
		return this.callClass;
	}

	public void setCallClass(String callClass) {
		this.callClass = callClass;
	}

	public String getCallMethod() {
		return this.callMethod;
	}

	public void setCallMethod(String callMethod) {
		this.callMethod = callMethod;
	}

	public String getCallStack() {
		return this.callStack;
	}

	public void setCallStack(String callStack) {
		this.callStack = callStack;
	}

	public Integer getStopFlg() {
		return this.stopFlg;
	}

	public void setStopFlg(Integer stopFlg) {
		this.stopFlg = stopFlg;
	}

	public String getInvokeClassName() {
		return this.invokeClassName;
	}

	public void setInvokeClassName(String invokeClassName) {
		this.invokeClassName = invokeClassName;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getQueueAble() {
		return this.queueAble;
	}

	public void setQueueAble(Integer queueAble) {
		this.queueAble = queueAble;
	}

	public String getExceptionStack() {
		return this.exceptionStack;
	}

	public void setExceptionStack(String exceptionStack) {
		this.exceptionStack = exceptionStack;
	}

}
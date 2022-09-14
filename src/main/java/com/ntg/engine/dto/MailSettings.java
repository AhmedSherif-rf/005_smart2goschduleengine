package com.ntg.engine.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailSettings {

	@Value("${mailSettings.mailServer}")
	private String mailServer;

	@Value("${mailSettings.serverPort}")
	private String portNumber;

	@Value("${mailSettings.userName}")
	private String mailUserName;

	@Value("${mailSettings.password}")
	private String mailPassword;

	@Value("${mailSettings.starttls}")
	private String starttls;

	@Value("${mailSettings.socketFactoryPort}")
	private String socketFactoryPort;

	@Value("${mailSettings.socketFactoryClass}")
	private String socketFactoryClass;

	@Value("${mailSettings.fromMailAddress}")
	private String fromMailAddress;

	@Value("${mailSettings.sendMailByEngine}")
	private Boolean sendMailByEngine;

	@Value("${mailSettings.ssl}")
	private String ssl;

	@Value("${mailSettings.employeesGroupsEmailsURL}")
	private String employeesGroupsEmailsURL;

	@Value("${mailSettings.auth}")
	private String mailAuth;

	public String getMailServer() {
		return mailServer;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public String getMailUserName() {
		return mailUserName;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public String getStarttls() {
		return starttls;
	}

	public String getSocketFactoryPort() {
		return socketFactoryPort;
	}

	public String getSocketFactoryClass() {
		return socketFactoryClass;
	}

	public String getFromMailAddress() {
		return fromMailAddress;
	}

	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public void setMailUserName(String mailUserName) {
		this.mailUserName = mailUserName;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public void setStarttls(String starttls) {
		this.starttls = starttls;
	}

	public void setSocketFactoryPort(String socketFactoryPort) {
		this.socketFactoryPort = socketFactoryPort;
	}

	public void setSocketFactoryClass(String socketFactoryClass) {
		this.socketFactoryClass = socketFactoryClass;
	}

	public void setFromMailAddress(String fromMailAddress) {
		this.fromMailAddress = fromMailAddress;
	}

	public Boolean getSendMailByEngine() {
		return sendMailByEngine;
	}

	public void setSendMailByEngine(Boolean sendMailByEngine) {
		this.sendMailByEngine = sendMailByEngine;
	}

	public String getSsl() {
		return ssl;
	}

	public void setSsl(String ssl) {
		this.ssl = ssl;
	}

	public String getEmployeesGroupsEmailsURL() {
		return employeesGroupsEmailsURL;
	}

	public void setEmployeesGroupsEmailsURL(String employeesGroupsEmailsURL) {
		this.employeesGroupsEmailsURL = employeesGroupsEmailsURL;
	}

	public String getMailAuth() {
		return mailAuth;
	}

	public void setMailAuth(String mailAuth) {
		this.mailAuth = mailAuth;
	}

}

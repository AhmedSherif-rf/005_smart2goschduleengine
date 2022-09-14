package com.ntg.engine.service.serviceImpl;

import com.ntg.common.NTGMessageOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ntg.engine.dto.LoginUser;
import com.ntg.engine.dto.LoginUserRequest;
import com.ntg.engine.dto.SessionInfo;
import com.ntg.engine.dto.LoginSettings;
import com.ntg.engine.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginSettings login;

	private static String userSessionToken = null;

	@Override
	public String logIn(String companyName) {

		if (userSessionToken == null) {
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("Host", login.getHost());
				headers.set("User-Agent", "schedulerBackEnd");
				headers.set("SessionToken", companyName);
				SessionInfo sInfo = new SessionInfo();
				sInfo.loginUserName = login.getUserName();
				sInfo.companyName = companyName;
				LoginUserRequest loginUser = new LoginUserRequest();
				loginUser.Password = login.getPassword();
				loginUser.LoginUserInfo = sInfo;
				HttpEntity<LoginUserRequest> entity = new HttpEntity<LoginUserRequest>(loginUser, headers);
				String getMainMethodUrl = login.getUrl() + "/rest/MainFunciton/login/";
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<LoginUser> result = restTemplate.exchange(getMainMethodUrl, HttpMethod.POST, entity,
						LoginUser.class);
				LoginUser loginUserSession = ((LoginUser) result.getBody());

				if (loginUserSession != null) {
					userSessionToken = loginUserSession.sessionTokenId;
				}
				return userSessionToken;
			} catch (Exception e) {
				NTGMessageOperation.PrintErrorTrace(e);
			}
			return null;
		} else {
			return userSessionToken;
		}
	}

}

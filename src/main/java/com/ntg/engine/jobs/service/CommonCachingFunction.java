package com.ntg.engine.jobs.service;

import com.ntg.common.IUDAType;
import com.ntg.common.NTGEHCacher;
import com.ntg.engine.dto.LoginSettings;
import com.ntg.engine.dto.LoginUserRequest;
import com.ntg.engine.dto.SessionInfo;
import com.ntg.engine.dto.StringResponse;
import com.ntg.engine.util.JobUtil;
import com.ntg.engine.util.LoginUser;
import com.ntg.engine.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Service()
public class CommonCachingFunction {

	@Value("${GetGridColumnsURL}")
	String GetGridColumnsURL;

	@Value("${wf_loadObject}")
	String wf_loadObject;
	@Value("${wf_loadChartFields}")
	String wf_loadChartFields;

	@Value("${wf_loadAttachments}")
	String wf_loadAttachments;

	private static final String UDA_TYPE_KEY = "udaType";
	public static NTGEHCacher<String, Object> CachingCommonData;
	static int CacheEmployeeInfoFor = 300000;
	static {
		CachingCommonData = new NTGEHCacher<String, Object>(CacheEmployeeInfoFor);
	}

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private LoginSettings loginSettings;

	public String BackEndLogin(String companyName) throws InterruptedException {

		String key = "Login_" + companyName + "_" + loginSettings.getUserName();

		String userSessionToken = (String) CachingCommonData.get(key);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("User-Agent", "schedulerBackEnd");
		headers.set("Host", loginSettings.getHost());
		if (loginSettings.getUrl().contains("/rest/")) {
			loginSettings.setUrl(loginSettings.getUrl().replaceAll("/rest/", ""));
		}

		if (userSessionToken != null) {
			// test the session is not expire
			try {
				headers.set("SessionToken", userSessionToken);
				headers.set("User-Agent", "schedulerBackEnd");

				HttpEntity<String> entity = new HttpEntity<String>(null, headers);

				String TestSesisonTokenUrl = loginSettings.getUrl() + "/rest/MainFunciton/TestSesisonToken/";

				ResponseEntity<StringResponse> res = restTemplate.exchange(TestSesisonTokenUrl, HttpMethod.GET, entity,
						StringResponse.class);
				String reomteToken = null;
				if (Utils.isNotEmpty(res.getBody())) {
					reomteToken = res.getBody().returnValue;
				}
				if (reomteToken == null || reomteToken.contains(userSessionToken) == false) {
					JobUtil.Debug("War:Issue in Test Session Token : " + reomteToken);
					userSessionToken = null;// relogin again
				}
			} catch (Exception ex) {
				JobUtil.Debug("War:Issue in Test Session Token : " + ex.getMessage());
				userSessionToken = null;// relogin again
			}

		}

		while (userSessionToken == null) {
			try {
				LoginUserRequest loginUser = new LoginUserRequest();
				headers.set("SessionToken", companyName);
				headers.set("User-Agent", "schedulerBackEnd");

				SessionInfo sInfo = new SessionInfo();
				sInfo.loginUserName = loginSettings.getUserName();
				sInfo.companyName = companyName;

				JobUtil.Debug("Login URL : ===>" + loginSettings.getUrl() + "/rest/MainFunciton/login/ , C : " + companyName + " ,u :" + loginSettings.getUserName());


				loginUser.Password = loginSettings.getPassword();
				loginUser.LoginUserInfo = sInfo;

				HttpEntity<LoginUserRequest> entity = new HttpEntity<LoginUserRequest>(loginUser, headers);
				// Added By Mahmoud To fix loaded url

				String getMainMethodUrl = loginSettings.getUrl() + "/rest/MainFunciton/login/";
				ResponseEntity<LoginUser> res = restTemplate.exchange(getMainMethodUrl, HttpMethod.POST, entity,
						LoginUser.class);
				if (Utils.isNotEmpty(res.getBody())) {
					LoginUser user = res.getBody();
					userSessionToken = user.UserSessionToken;
					CachingCommonData.put(key, userSessionToken);
					if (userSessionToken != null) {
						JobUtil.Debug("User Session Tocken Is  : ===> Fetched Ok");
					}
				}
			} catch (Exception ex) {
				JobUtil.Debug("War:Issue in Fetch Session Token : " + ex.getMessage());
				userSessionToken = null;
			}
			if (userSessionToken == null) {
				JobUtil.Debug("War:Sleep Try to get Session Token After 1000MSec");
				Thread.sleep(1000);
			}
		}
		return userSessionToken;
	}
//@Aya.Ramadan => Dev-00003294 :For "issue in schedule job when add email template with insert fields. not appear data of UDA you selected it in mail"
	public String resolveDescription(String description, Map obj, boolean isEmailTemplate,Long typeId, Long objectId, String companyName) throws Exception {
		return replaceDescriptionWithValues(description, obj, isEmailTemplate,typeId , objectId , companyName);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String replaceDescriptionWithValues(String description, Map obj, boolean isEmailTemplate,Long typeId, Long objectId, String companyName) throws Exception {
		String condition = description;
		String conditionField;
		String value;
		String[] splitCondition = description.split("\\.");

		for (String splitCond : splitCondition) {
			if (splitCond.contains("}}")) {
				conditionField = splitCond;
				conditionField = conditionField.substring(0, conditionField.indexOf("}}"));
				if (condition.contains(conditionField)) {
					Object v = obj.get(conditionField);
					if (v instanceof Long && conditionField.toLowerCase().contains("date")) {
						Date d = new Date((Long) v);
						final String Format = "yyyy/MM/dd HH:mm";
						final SimpleDateFormat df = new SimpleDateFormat(Format);
						value = df.format(d);
					} else {
						value = (v == null) ? "" : v.toString();
					}

					if (!description.contains("uda")) {
						condition = condition.replace("{{Object." + conditionField + "}}", value);
					} else {
						int index = description.indexOf("{{uda." + conditionField + "}}");
						if (index < 0) {
							condition = condition.replace("{{Object." + conditionField + "}}", value);
						} else { // ======= here to handle uda value =======
							ArrayList<Map> udasValues = (ArrayList<Map>) obj.get("udasValues");
							if (udasValues != null && !udasValues.isEmpty()) {
								for (Map udasValue : udasValues) {
									if (udasValue.get("udaName").equals(conditionField) || (Long.parseLong(udasValue.
											get("udaType").toString()) == IUDAType.FORM && (udasValue.get("udaName")+"_val").equals(conditionField))) {
										if (Long.parseLong(udasValue.get(UDA_TYPE_KEY).toString()) != IUDAType.GRID
												&& Long.parseLong(udasValue.get(UDA_TYPE_KEY).toString()) != IUDAType.GRID_CHART) {

											if (Long.parseLong(udasValue.get(UDA_TYPE_KEY).toString()) == IUDAType.FORM) {
												value = (udasValue.get("udaFormValueString") == null) ? ""
														: udasValue.get("udaFormValueString").toString();
											} else
												value = (udasValue.get("udaValue") == null) ? ""
														: udasValue.get("udaValue").toString();

										}
										// == here if it's email template and
										// grid uda , draw its data in a
										// table===
										else if ((Long.parseLong(udasValue.get(UDA_TYPE_KEY).toString()) == IUDAType.GRID ||
												Long.parseLong(udasValue.get(UDA_TYPE_KEY).toString()) == IUDAType.GRID_CHART)
												&& isEmailTemplate) {
											ArrayList<Map> rowData;
											List<LinkedHashMap<String, Object>> orderedGridColumns;
											if (Long.parseLong(udasValue.get(UDA_TYPE_KEY).toString()) == IUDAType.GRID_CHART) {
												Map<String, Object> map = getGridChartRowData(Long.parseLong(udasValue.get("recId").toString()), typeId, objectId , companyName);
												orderedGridColumns = (List<LinkedHashMap<String, Object>>) map.get("gridColumns");
												rowData = (ArrayList<Map>) map.get("rowData");
											} else {
												rowData = (ArrayList<Map>) udasValue.get("rowData");
												orderedGridColumns =  getGridColumns(
														Long.parseLong(udasValue.get("recId").toString()), companyName);
											}
											value = drawGridTable(value, orderedGridColumns, rowData);

										}
										condition = condition.replace("{{uda." + conditionField + "}}",
												((value == null) ? "" : value.toString()));

									}
								}
							}
						}
					}
				}
			}
		}
		return condition.replace("'", "''");
	}

	private Map<String, Object> getGridChartRowData(long recId, Long typeId, Long objectId , String companyName) throws InterruptedException {
		Map loadedObj = null;
		HttpHeaders headersRoute = new HttpHeaders();
		headersRoute.setContentType(MediaType.APPLICATION_JSON);
		headersRoute.set("SessionToken", BackEndLogin(companyName));
		headersRoute.set("User-Agent", "schedulerBackEnd");

		String loadObject = wf_loadChartFields + "/" + objectId+ "/" + recId+ "/" + typeId;
		HttpEntity<Map<String, Object>> entityRule = new HttpEntity<>( headersRoute);
		ResponseEntity<Map> responseEntity = restTemplate.exchange(loadObject, HttpMethod.GET, entityRule, Map.class);
		if (Utils.isNotEmpty(responseEntity.getBody())) {
			loadedObj = responseEntity.getBody();
		}

		return loadedObj;
	}

	public List<LinkedHashMap<String, Object>> getGridColumns(Long udaGridRecId, String companyName) throws Exception {

		String gridColumnsUrl = GetGridColumnsURL + "/" + udaGridRecId;

		String loginToken = BackEndLogin(companyName);
		HttpHeaders headersRule = new HttpHeaders();
		headersRule.setContentType(MediaType.APPLICATION_JSON);
		headersRule.set("SessionToken", loginToken);
		headersRule.set("User-Agent", "schedulerBackEnd");

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headersRule);
		ResponseEntity<Object> responseEntity = restTemplate.exchange(gridColumnsUrl, HttpMethod.GET, entity,
				Object.class);
		List<LinkedHashMap<String, Object>> gridColumns = (List<LinkedHashMap<String, Object>>) responseEntity
				.getBody();

		// sort columns by sequence
		Comparator<LinkedHashMap<String, Object>> comp = Comparator.comparing(c -> (c.get("seq").toString()));
		Collections.sort(gridColumns, comp);

		return gridColumns;
	}

	// Dev-00003512: grid columns not ordered right in Email Template
	public String drawGridTable(String value, List<LinkedHashMap<String, Object>> orderedGridColumns,
			ArrayList<Map> rowDatas) {

		if (rowDatas != null && rowDatas.size() > 0 && orderedGridColumns.size() > 0) {
			value = "</p><br/> ";
			value += "<table border='2'>";
			value += "<tr border='2'>";

			// to draw headers of table
			for (LinkedHashMap<String, Object> UdaGridColumn : orderedGridColumns) {
				value += "<th border='2' align='center'> <font size='4'> <strong>"
						+ UdaGridColumn.get("caption").toString() + "</strong></font> </th>";
			}

			value += "</tr>";

			// to draw rows data
			for (Map rawData : rowDatas) {
				value += "<tr border='2'>";
				for (LinkedHashMap<String, Object> UdaGridColumn : orderedGridColumns) {
					value += "<td border='1'> <font size='3'>" + rawData.get(UdaGridColumn.get("name")).toString()
							+ "</font></td>";
				}
				value += "</tr>";
			}
			value += "</table>";
			value += "<p>";
		}

		return value;
	}

	public Map loadCrmObject(Long typeId, Long objectId, String companyName) throws Exception {
		String key = "Obj_" + objectId + "_" + typeId;
		Map loadedObj = null;
		HttpHeaders headersRoute = new HttpHeaders();
		headersRoute.setContentType(MediaType.APPLICATION_JSON);
		headersRoute.set("SessionToken", BackEndLogin(companyName));
		headersRoute.set("User-Agent", "schedulerBackEnd");

		List<Map<String, Object>> listToBeSent = new ArrayList<>();
		Map<String, Object> mapToBeSent = new HashMap<>();
		mapToBeSent.put("recid", objectId);

		listToBeSent.add(mapToBeSent);

		String loadObject = wf_loadObject + "/" + typeId;
		HttpEntity<Map<String, Object>> entityRule = new HttpEntity<>(listToBeSent.get(0), headersRoute);
		ResponseEntity<Map> responseEntity = restTemplate.exchange(loadObject, HttpMethod.POST, entityRule, Map.class);
		if (Utils.isNotEmpty(responseEntity.getBody())) {
			loadedObj = responseEntity.getBody();
			CachingCommonData.put(key, loadedObj);
		}

		return loadedObj;
	}

	public byte[] loadEmailAttachments(Long emailId, Long objectId, String companyName) throws Exception {
		byte[] loadedAttachments = null;
		HttpHeaders headersRoute = new HttpHeaders();
		headersRoute.setContentType(MediaType.APPLICATION_JSON);
		headersRoute.set("SessionToken", BackEndLogin(companyName));
		headersRoute.set("User-Agent", "schedulerBackEnd");
		String loadObject = wf_loadAttachments  + emailId + "/" + objectId;
		HttpEntity<Map<String, Object>> entityRule = new HttpEntity<>(headersRoute);
		ResponseEntity<byte[]> responseEntity = restTemplate.exchange(loadObject, HttpMethod.GET, entityRule, byte[].class);
		if (Utils.isNotEmpty(responseEntity.getBody())) {
			loadedAttachments = responseEntity.getBody();
		}
		return loadedAttachments;
	}
}

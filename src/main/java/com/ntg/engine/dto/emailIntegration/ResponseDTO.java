package com.ntg.engine.dto.emailIntegration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class ResponseDTO {


	private String code;
	private String key;

	public ResponseDTO() {

	}

	public ResponseDTO(String code, String key) {
		super();
		this.code = code;
		this.key = key;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public static ResponseDTO handleSuccess(ResponseDTO responseDTO, String operationName, String serviceName) {
		ServletRequestAttributes t = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest req = t.getRequest();
//        req.getHeader(Constants.creatorIdHTTPHeaderField);
	//	responseDTO.setCode(CodesAndMessages.NO_ERRORS_CODE);
	//	responseDTO.setKey(CodesAndMessages.NO_ERRORS_KEY);
	/*	log.info("[NTG_LOG_START] 200 OK, " + operationName + " Operation " + serviceName
				+ " Service request succeeded for User ("+req.getHeader(Constants.creatorIdHTTPHeaderField)+ ") [NTG_LOG_END]");
*/		return responseDTO;
	}

}
package com.github.ngj1129.fileextensionblock.common.exception.extension;

import org.springframework.http.HttpStatus;

import com.github.ngj1129.fileextensionblock.common.exception.ApiException;

public class CustomExtensionLimitExceededException extends ApiException {
	public CustomExtensionLimitExceededException(int limit) {
		super(HttpStatus.BAD_REQUEST, "CUSTOM_EXT_LIMIT_EXCEEDED",
			"커스텀 확장자는 최대 " + limit + "개까지 등록할 수 있습니다.");
	}
}

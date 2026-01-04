package com.github.ngj1129.fileextensionblock.extensionblock.common.exception;

import org.springframework.http.HttpStatus;

public class CustomExtensionAlreadyExistsException extends ApiException {
	public CustomExtensionAlreadyExistsException(String ext) {
		super(HttpStatus.CONFLICT, "CUSTOM_EXT_DUPLICATED",
			"이미 등록된 커스텀 확장자입니다: " + ext);
	}
}

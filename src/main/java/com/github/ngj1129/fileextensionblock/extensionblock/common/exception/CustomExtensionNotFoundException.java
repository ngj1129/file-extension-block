package com.github.ngj1129.fileextensionblock.extensionblock.common.exception;

import org.springframework.http.HttpStatus;

public class CustomExtensionNotFoundException extends ApiException {
	public CustomExtensionNotFoundException(String ext) {
		super(HttpStatus.NOT_FOUND, "CUSTOM_EXT_NOT_FOUND",
			"존재하지 않는 커스텀 확장자: " + ext);
	}
}

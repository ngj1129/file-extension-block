package com.github.ngj1129.fileextensionblock.common.exception.extension;

import org.springframework.http.HttpStatus;

import com.github.ngj1129.fileextensionblock.common.exception.ApiException;

public class CustomExtensionNotFoundException extends ApiException {
	public CustomExtensionNotFoundException(String ext) {
		super(HttpStatus.NOT_FOUND, "CUSTOM_EXT_NOT_FOUND",
			"존재하지 않는 커스텀 확장자: " + ext);
	}
}

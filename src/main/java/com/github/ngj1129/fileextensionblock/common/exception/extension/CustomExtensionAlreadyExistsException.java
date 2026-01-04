package com.github.ngj1129.fileextensionblock.common.exception.extension;

import org.springframework.http.HttpStatus;

import com.github.ngj1129.fileextensionblock.common.exception.ApiException;

public class CustomExtensionAlreadyExistsException extends ApiException {
	public CustomExtensionAlreadyExistsException(String ext) {
		super(HttpStatus.CONFLICT, "CUSTOM_EXT_DUPLICATED",
			"이미 등록된 커스텀 확장자입니다: " + ext);
	}
}

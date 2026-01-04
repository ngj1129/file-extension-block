package com.github.ngj1129.fileextensionblock.extensionblock.common.exception;

import org.springframework.http.HttpStatus;

public class FixedExtensionNotFoundException extends ApiException {
	public FixedExtensionNotFoundException(String ext) {
		super(HttpStatus.NOT_FOUND, "FIXED_EXT_NOT_FOUND",
			"존재하지 않는 고정 확장자: " + ext);
	}
}

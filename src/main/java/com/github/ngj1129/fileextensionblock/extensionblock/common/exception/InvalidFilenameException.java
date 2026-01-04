package com.github.ngj1129.fileextensionblock.extensionblock.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidFilenameException extends ApiException {
	public InvalidFilenameException() {
		super(HttpStatus.BAD_REQUEST, "INVALID_FILENAME", "파일명이 유효하지 않습니다.");
	}
}

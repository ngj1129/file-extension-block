package com.github.ngj1129.fileextensionblock.common.exception.blocktest;

import org.springframework.http.HttpStatus;

import com.github.ngj1129.fileextensionblock.common.exception.ApiException;

public class InvalidFilenameException extends ApiException {
	public InvalidFilenameException() {
		super(HttpStatus.BAD_REQUEST, "INVALID_FILENAME", "파일명이 유효하지 않습니다.");
	}
}

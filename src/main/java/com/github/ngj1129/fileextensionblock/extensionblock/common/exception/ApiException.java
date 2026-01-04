package com.github.ngj1129.fileextensionblock.extensionblock.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class ApiException extends RuntimeException {
	private final HttpStatus status;
	private final String code;

	protected ApiException(HttpStatus status, String code, String message) {
		super(message);
		this.status = status;
		this.code = code;
	}
}

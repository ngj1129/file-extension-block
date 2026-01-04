package com.github.ngj1129.fileextensionblock.common.exception.blocktest;

import org.springframework.http.HttpStatus;

import com.github.ngj1129.fileextensionblock.common.exception.ApiException;

public class BlockedExtensionException extends ApiException {
	public BlockedExtensionException(String ext) {
		super(HttpStatus.BAD_REQUEST, "EXT_BLOCKED",
			"차단된 확장자입니다: " + ext);
	}
}

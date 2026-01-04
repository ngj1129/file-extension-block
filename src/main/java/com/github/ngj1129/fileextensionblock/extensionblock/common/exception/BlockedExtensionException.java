package com.github.ngj1129.fileextensionblock.extensionblock.common.exception;

import org.springframework.http.HttpStatus;

public class BlockedExtensionException extends ApiException {
	public BlockedExtensionException(String ext) {
		super(HttpStatus.BAD_REQUEST, "EXT_BLOCKED",
			"차단된 확장자입니다: " + ext);
	}
}

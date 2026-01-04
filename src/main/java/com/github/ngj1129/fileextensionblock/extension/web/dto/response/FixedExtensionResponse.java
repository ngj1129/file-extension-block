package com.github.ngj1129.fileextensionblock.extension.web.dto.response;

import com.github.ngj1129.fileextensionblock.extension.domain.FixedExtension;

public record FixedExtensionResponse(
	String ext,
	boolean isChecked
) {
	public static FixedExtensionResponse from(FixedExtension fixedExtension) {
		return new FixedExtensionResponse(fixedExtension.getExt(), fixedExtension.isBlocked());
	}
}

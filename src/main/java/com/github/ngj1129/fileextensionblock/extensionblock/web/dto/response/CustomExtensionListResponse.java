package com.github.ngj1129.fileextensionblock.extensionblock.web.dto.response;

import java.util.List;

import com.github.ngj1129.fileextensionblock.extensionblock.domain.CustomExtension;

public record CustomExtensionListResponse(
	List<String> customExtensions
) {
	public static CustomExtensionListResponse from(List<CustomExtension> customExtensions) {
		return new CustomExtensionListResponse(customExtensions.stream()
			.map(CustomExtension::getExt)
			.toList());
	}
}

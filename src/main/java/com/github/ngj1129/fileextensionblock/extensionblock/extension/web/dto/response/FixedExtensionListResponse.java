package com.github.ngj1129.fileextensionblock.extensionblock.extension.web.dto.response;

import java.util.List;

import com.github.ngj1129.fileextensionblock.extensionblock.extension.domain.FixedExtension;

public record FixedExtensionListResponse(
	List<FixedExtensionResponse> fixedExtensions
) {
	public static FixedExtensionListResponse from(List<FixedExtension> fixedExtensions) {
		return new FixedExtensionListResponse(fixedExtensions.stream()
			.map(FixedExtensionResponse::from)
			.toList());
	}
}

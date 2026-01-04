package com.github.ngj1129.fileextensionblock.extensionblock.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomExtensionRequest(
	@NotBlank(message = "확장자는 빈 값일 수 없습니다.")
	@Size(max = 20, message = "확장자의 최대 길이는 20자입니다.")
	String ext
) {
}

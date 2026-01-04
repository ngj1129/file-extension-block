package com.github.ngj1129.fileextensionblock.extensionblock.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ngj1129.fileextensionblock.extensionblock.service.ExtensionBlockService;
import com.github.ngj1129.fileextensionblock.extensionblock.web.dto.request.FixedExtensionUpdateRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/extensions")
@RequiredArgsConstructor
public class ExtensionBlockController {

	private final ExtensionBlockService extensionBlockService;

	// 고정 확장자 체크 업데이트
	@PatchMapping("/fixed/{ext}")
	public ResponseEntity<Void> updateFixedExtension(@PathVariable String ext,
		@RequestBody FixedExtensionUpdateRequest request) {
		extensionBlockService.updateFixedExtension(ext, request);
		return ResponseEntity.noContent().build();
	}


}

package com.github.ngj1129.fileextensionblock.extensionblock.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ngj1129.fileextensionblock.extensionblock.web.dto.FixedExtensionUpdateRequest;

@RestController
@RequestMapping("/api/extensions")
public class ExtensionBlockController {
	// 고정 확장자 체크 업데이트
	@PatchMapping("/fixed/{ext}")
	public ResponseEntity<Void> updateFixedExtension(@PathVariable String ext,
		@RequestBody FixedExtensionUpdateRequest request) {
		return ResponseEntity.noContent().build();
	}
}

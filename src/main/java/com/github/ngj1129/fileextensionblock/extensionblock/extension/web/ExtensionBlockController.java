package com.github.ngj1129.fileextensionblock.extensionblock.extension.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ngj1129.fileextensionblock.extensionblock.extension.service.ExtensionBlockService;
import com.github.ngj1129.fileextensionblock.extensionblock.extension.web.dto.request.CustomExtensionRequest;
import com.github.ngj1129.fileextensionblock.extensionblock.extension.web.dto.request.FixedExtensionUpdateRequest;
import com.github.ngj1129.fileextensionblock.extensionblock.extension.web.dto.response.CustomExtensionListResponse;
import com.github.ngj1129.fileextensionblock.extensionblock.extension.web.dto.response.FixedExtensionListResponse;

import jakarta.validation.Valid;
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

	// 고정 확장자 리스트 조회
	@GetMapping("/fixed")
	public ResponseEntity<FixedExtensionListResponse> getFixedExtensionList() {
		return ResponseEntity.ok(extensionBlockService.getFixedExtensionList());
	}

	// 커스텀 확장자 추가
	@PostMapping("/custom")
	public ResponseEntity<Long> createCustomExtension(
		@RequestBody @Valid CustomExtensionRequest request) {
		return ResponseEntity.status(201)
			.body(extensionBlockService.createCustomExtension(request));
	}

	// 커스텀 확장자 삭제
	@DeleteMapping("/custom/{ext}")
	public ResponseEntity<Void> deleteCustomExtension(@PathVariable String ext) {
		extensionBlockService.deleteCustomExtension(ext);
		return ResponseEntity.noContent().build();
	}

	// 커스텀 확장자 리스트 조회
	@GetMapping("/custom")
	public ResponseEntity<CustomExtensionListResponse> getCustomExtensionList() {
		return ResponseEntity.ok(extensionBlockService.getCustomExtensionList());
	}
}

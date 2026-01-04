package com.github.ngj1129.fileextensionblock.extensionblock.blocktest.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.ngj1129.fileextensionblock.extensionblock.blocktest.service.BlockTestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/block-test")
@RequiredArgsConstructor
public class BlockTestController {

	private final BlockTestService blockTestService;

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> upload(@RequestPart("file") MultipartFile file) {
		blockTestService.validate(file);
		return ResponseEntity.noContent().build();
	}
}

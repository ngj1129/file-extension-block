package com.github.ngj1129.fileextensionblock.blocktest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.ngj1129.fileextensionblock.common.exception.blocktest.BlockedExtensionException;
import com.github.ngj1129.fileextensionblock.common.exception.blocktest.InvalidFilenameException;
import com.github.ngj1129.fileextensionblock.extension.domain.FixedExtension;
import com.github.ngj1129.fileextensionblock.extension.repository.CustomExtensionRepository;
import com.github.ngj1129.fileextensionblock.extension.repository.FixedExtensionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BlockTestService {

	private final FixedExtensionRepository fixedExtensionRepository;
	private final CustomExtensionRepository customExtensionRepository;

	public void validate(MultipartFile file) {
		String filename = file.getOriginalFilename();

		// 파일명이 없으면 예외
		if (filename == null || filename.isBlank()) {
			throw new InvalidFilenameException();
		}

		// 확장자가 없을 수도 있음 (허용)
		String ext = extractExt(filename);
		if (ext.isBlank()) {
			return;
		}

		// 1) 고정 확장자 차단 여부
		FixedExtension fixed = fixedExtensionRepository.findByExt(ext).orElse(null);
		if (fixed != null && fixed.isBlocked()) {
			throw new BlockedExtensionException(ext);
		}

		// 2) 커스텀 확장자 존재하면 차단
		if (customExtensionRepository.existsByExt(ext)) {
			throw new BlockedExtensionException(ext);
		}
	}

	private String extractExt(String filename) {
		String name = filename.trim();
		int lastDot = name.lastIndexOf('.');
		if (lastDot < 0 || lastDot == name.length() - 1) {
			return "";
		}
		return name.substring(lastDot + 1).trim().toLowerCase();
	}
}

package com.github.ngj1129.fileextensionblock.extensionblock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ngj1129.fileextensionblock.extensionblock.common.exception.FixedExtensionNotFoundException;
import com.github.ngj1129.fileextensionblock.extensionblock.domain.FixedExtension;
import com.github.ngj1129.fileextensionblock.extensionblock.repository.FixedExtensionRepository;
import com.github.ngj1129.fileextensionblock.extensionblock.web.dto.FixedExtensionUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExtensionBlockService {

	private final FixedExtensionRepository fixedExtensionRepository;

	@Transactional
	public void updateFixedExtension(String ext, FixedExtensionUpdateRequest request) {
		FixedExtension fixedExtension = fixedExtensionRepository.findByExt(ext)
			.orElseThrow(() -> new FixedExtensionNotFoundException(ext));
		fixedExtension.updateBlocked(request.blocked());
	}
}

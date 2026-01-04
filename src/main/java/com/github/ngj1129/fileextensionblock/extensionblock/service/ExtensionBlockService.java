package com.github.ngj1129.fileextensionblock.extensionblock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ngj1129.fileextensionblock.extensionblock.common.exception.CustomExtensionAlreadyExistsException;
import com.github.ngj1129.fileextensionblock.extensionblock.common.exception.CustomExtensionLimitExceededException;
import com.github.ngj1129.fileextensionblock.extensionblock.common.exception.CustomExtensionNotFoundException;
import com.github.ngj1129.fileextensionblock.extensionblock.common.exception.FixedExtensionNotFoundException;
import com.github.ngj1129.fileextensionblock.extensionblock.domain.CustomExtension;
import com.github.ngj1129.fileextensionblock.extensionblock.domain.FixedExtension;
import com.github.ngj1129.fileextensionblock.extensionblock.repository.CustomExtensionRepository;
import com.github.ngj1129.fileextensionblock.extensionblock.repository.FixedExtensionRepository;
import com.github.ngj1129.fileextensionblock.extensionblock.web.dto.request.CustomExtensionRequest;
import com.github.ngj1129.fileextensionblock.extensionblock.web.dto.response.CustomExtensionListResponse;
import com.github.ngj1129.fileextensionblock.extensionblock.web.dto.response.FixedExtensionListResponse;
import com.github.ngj1129.fileextensionblock.extensionblock.web.dto.request.FixedExtensionUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExtensionBlockService {

	private static final int CUSTOM_LIMIT = 200;

	private final FixedExtensionRepository fixedExtensionRepository;
	private final CustomExtensionRepository customExtensionRepository;

	@Transactional
	public void updateFixedExtension(String ext, FixedExtensionUpdateRequest request) {
		FixedExtension fixedExtension = fixedExtensionRepository.findByExt(ext)
			.orElseThrow(() -> new FixedExtensionNotFoundException(ext));
		fixedExtension.updateBlocked(request.blocked());
	}

	public FixedExtensionListResponse getFixedExtensionList() {
		List<FixedExtension> fixedExtensions = fixedExtensionRepository.findAll();
		return FixedExtensionListResponse.from(fixedExtensions);
	}

	@Transactional
	public Long createCustomExtension(CustomExtensionRequest request) {
		String ext = request.ext();

		// 1) 개수 제한
		long count = customExtensionRepository.count();
		if (count >= CUSTOM_LIMIT) {
			throw new CustomExtensionLimitExceededException(CUSTOM_LIMIT);
		}

		// 2) 중복 검사
		if (customExtensionRepository.existsByExt(ext)) {
			throw new CustomExtensionAlreadyExistsException(ext);
		}

		// 3) 저장
		CustomExtension saved = customExtensionRepository.save(CustomExtension.create(ext));
		return saved.getId();
	}

	@Transactional
	public void deleteCustomExtension(String ext) {
		CustomExtension customExtension = customExtensionRepository.findByExt(ext)
			.orElseThrow(() -> new CustomExtensionNotFoundException(ext));

		customExtensionRepository.delete(customExtension);
	}

	public CustomExtensionListResponse getCustomExtensionList() {
		List<CustomExtension> customExtensions = customExtensionRepository.findAll();
		return CustomExtensionListResponse.from(customExtensions);
	}
}

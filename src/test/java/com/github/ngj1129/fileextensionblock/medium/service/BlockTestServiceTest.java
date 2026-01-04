package com.github.ngj1129.fileextensionblock.medium.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.github.ngj1129.fileextensionblock.extensionblock.blocktest.service.BlockTestService;
import com.github.ngj1129.fileextensionblock.extensionblock.common.exception.BlockedExtensionException;
import com.github.ngj1129.fileextensionblock.extensionblock.common.exception.InvalidFilenameException;
import com.github.ngj1129.fileextensionblock.extensionblock.extension.domain.FixedExtension;
import com.github.ngj1129.fileextensionblock.extensionblock.extension.repository.CustomExtensionRepository;
import com.github.ngj1129.fileextensionblock.extensionblock.extension.repository.FixedExtensionRepository;

@ExtendWith(MockitoExtension.class)
class BlockTestServiceTest {

	@InjectMocks
	BlockTestService blockTestService;

	@Mock
	FixedExtensionRepository fixedExtensionRepository;

	@Mock
	CustomExtensionRepository customExtensionRepository;

	@Test
	@DisplayName("파일명이 없으면 허용하지 않는다")
	void validate_throwIfFilenameMissing() {
		// given
		MockMultipartFile file = new MockMultipartFile(
			"file",
			null,
			"application/octet-stream",
			"dummy".getBytes(StandardCharsets.UTF_8)
		);

		// when & then
		assertThatThrownBy(() -> blockTestService.validate(file))
			.isInstanceOf(InvalidFilenameException.class)
			.hasMessageContaining("파일명");
	}

	@Test
	@DisplayName("확장자 없는 경우는 허용한다")
	void validate_allowIfNoExtension() {
		// given (README 처럼 확장자 없음)
		MockMultipartFile file = new MockMultipartFile(
			"file",
			"README",
			"text/plain",
			"dummy".getBytes(StandardCharsets.UTF_8)
		);

		// when & then
		assertThatCode(() -> blockTestService.validate(file))
			.doesNotThrowAnyException();
	}

	@Test
	@DisplayName("고정 확장자가 차단 상태이면 업로드를 차단한다")
	void validate_blockIfFixedExtensionBlocked() {
		// given
		MockMultipartFile file = new MockMultipartFile(
			"file",
			"virus.EXE",
			"application/octet-stream",
			"dummy".getBytes(StandardCharsets.UTF_8)
		);

		FixedExtension fixed = FixedExtension.create("exe", true);

		given(fixedExtensionRepository.findByExt("exe"))
			.willReturn(Optional.of(fixed));

		// when & then
		assertThatThrownBy(() -> blockTestService.validate(file))
			.isInstanceOf(BlockedExtensionException.class)
			.hasMessageContaining("차단된 확장자");
	}

	@Test
	@DisplayName("커스텀 확장자가 존재하면 업로드를 차단한다")
	void validate_blockIfCustomExtensionExists() {
		// given
		MockMultipartFile file = new MockMultipartFile(
			"file",
			"script.sh",
			"text/plain",
			"dummy".getBytes(StandardCharsets.UTF_8)
		);

		given(fixedExtensionRepository.findByExt("sh"))
			.willReturn(Optional.empty());
		given(customExtensionRepository.existsByExt("sh"))
			.willReturn(true);

		// when & then
		assertThatThrownBy(() -> blockTestService.validate(file))
			.isInstanceOf(BlockedExtensionException.class)
			.hasMessageContaining("차단된 확장자");
	}
}

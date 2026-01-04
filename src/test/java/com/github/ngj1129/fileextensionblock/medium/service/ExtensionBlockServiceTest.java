package com.github.ngj1129.fileextensionblock.medium.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.ngj1129.fileextensionblock.extensionblock.common.exception.CustomExtensionAlreadyExistsException;
import com.github.ngj1129.fileextensionblock.extensionblock.common.exception.CustomExtensionLimitExceededException;
import com.github.ngj1129.fileextensionblock.extensionblock.common.exception.FixedExtensionNotFoundException;
import com.github.ngj1129.fileextensionblock.extensionblock.extension.domain.CustomExtension;
import com.github.ngj1129.fileextensionblock.extensionblock.extension.repository.CustomExtensionRepository;
import com.github.ngj1129.fileextensionblock.extensionblock.extension.repository.FixedExtensionRepository;
import com.github.ngj1129.fileextensionblock.extensionblock.extension.service.ExtensionBlockService;
import com.github.ngj1129.fileextensionblock.extensionblock.extension.web.dto.request.CustomExtensionRequest;
import com.github.ngj1129.fileextensionblock.extensionblock.extension.web.dto.request.FixedExtensionUpdateRequest;

@ExtendWith(MockitoExtension.class)
class ExtensionBlockServiceTest {

	ExtensionBlockService extensionBlockService;

	@Mock
	FixedExtensionRepository fixedExtensionRepository;

	@Mock
	CustomExtensionRepository customExtensionRepository;

	@BeforeEach
	void setUp() {
		extensionBlockService = new ExtensionBlockService(
			fixedExtensionRepository,
			customExtensionRepository,
			200
		);
	}

	@Test
	@DisplayName("경로의 ext에 해당하는 고정 확장자가 존재하지 않으면 예외를 반환한다")
	void updateFixedExtension_throwIfNotFound() {
		// given
		String ext = "exe";
		given(fixedExtensionRepository.findByExt(ext))
			.willReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> extensionBlockService.updateFixedExtension(
			ext, new FixedExtensionUpdateRequest(true)))
			.isInstanceOf(FixedExtensionNotFoundException.class)
			.hasMessageContaining("존재하지 않는 고정 확장자");

		then(fixedExtensionRepository).should(times(1)).findByExt(ext);
	}

	@Test
	@DisplayName("커스텀 확장자가 이미 200개 있으면 예외를 반환한다")
	void createCustomExtension_throwIfLimitExceeded() {
		// given
		given(customExtensionRepository.count()).willReturn(200L);

		// when & then
		assertThatThrownBy(() -> extensionBlockService.createCustomExtension(new CustomExtensionRequest("exe")))
			.isInstanceOf(CustomExtensionLimitExceededException.class);
	}

	@Test
	@DisplayName("커스텀 확장자가 중복이면 예외를 반환한다")
	void createCustomExtension_throwIfDuplicated() {
		// given
		given(customExtensionRepository.count()).willReturn(0L);
		given(customExtensionRepository.existsByExt("exe")).willReturn(true);

		// when & then
		assertThatThrownBy(() -> extensionBlockService.createCustomExtension(new CustomExtensionRequest("exe")))
			.isInstanceOf(CustomExtensionAlreadyExistsException.class);
	}

	@Test
	@DisplayName("커스텀 확장자를 만들면 id를 반환한다")
	void createCustomExtension_returnsId() {
		// given
		given(customExtensionRepository.count()).willReturn(0L);
		given(customExtensionRepository.existsByExt("exe")).willReturn(false);

		CustomExtension saved = CustomExtension.builder()
			.id(1L)
			.ext("exe")
			.build();

		given(customExtensionRepository.save(any(CustomExtension.class)))
			.willReturn(saved);

		// when
		Long id = extensionBlockService.createCustomExtension(new CustomExtensionRequest("exe"));

		// then
		assertThat(id).isEqualTo(1L);
	}
}

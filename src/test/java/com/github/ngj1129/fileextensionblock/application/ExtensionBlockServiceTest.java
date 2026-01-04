package com.github.ngj1129.fileextensionblock.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.ngj1129.fileextensionblock.extensionblock.common.exception.FixedExtensionNotFoundException;
import com.github.ngj1129.fileextensionblock.extensionblock.repository.FixedExtensionRepository;
import com.github.ngj1129.fileextensionblock.extensionblock.service.ExtensionBlockService;
import com.github.ngj1129.fileextensionblock.extensionblock.web.dto.request.FixedExtensionUpdateRequest;

@ExtendWith(MockitoExtension.class)
class ExtensionBlockServiceTest {

	@InjectMocks
	ExtensionBlockService extensionBlockService;

	@Mock
	FixedExtensionRepository fixedExtensionRepository;

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
}

package com.github.ngj1129.fileextensionblock.unit;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.ngj1129.fileextensionblock.extensionblock.domain.FixedExtension;

class FixedExtensionTest {

	@Test
	@DisplayName("차단 여부가 업데이트된다")
	void updateBlocked() {
		// given
		FixedExtension fixed = FixedExtension.create("exe", false);

		// when
		fixed.updateBlocked(true);

		// then
		assertThat(fixed.isBlocked()).isTrue();
	}
}

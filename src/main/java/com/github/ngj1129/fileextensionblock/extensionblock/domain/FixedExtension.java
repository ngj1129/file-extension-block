package com.github.ngj1129.fileextensionblock.extensionblock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fixed_extension")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FixedExtension {
	@Id @GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true, length = 20)
	private String ext;

	@Column(nullable = false)
	private boolean blocked;

	public static FixedExtension create(String ext, boolean blocked) {
		return FixedExtension.builder()
			.ext(ext)
			.blocked(blocked)
			.build();
	}

	public void updateBlocked(boolean blocked) {
		this.blocked = blocked;
	}
}

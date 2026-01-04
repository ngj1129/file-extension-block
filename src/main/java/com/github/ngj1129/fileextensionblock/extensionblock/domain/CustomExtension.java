package com.github.ngj1129.fileextensionblock.extensionblock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "custom_extension")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class CustomExtension {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true, length = 20)
	private String ext;

	public static CustomExtension create(String ext) {
		return CustomExtension.builder()
			.ext(ext)
			.build();
	}
}

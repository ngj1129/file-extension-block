package com.github.ngj1129.fileextensionblock.extensionblock.extension.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.ngj1129.fileextensionblock.extensionblock.extension.domain.CustomExtension;

public interface CustomExtensionRepository extends JpaRepository<CustomExtension, Long> {
	boolean existsByExt(String ext);

	Optional<CustomExtension> findByExt(String ext);
}

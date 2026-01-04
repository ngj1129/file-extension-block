package com.github.ngj1129.fileextensionblock.extension.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.ngj1129.fileextensionblock.extension.domain.CustomExtension;

public interface CustomExtensionRepository extends JpaRepository<CustomExtension, Long> {
	boolean existsByExt(String ext);

	Optional<CustomExtension> findByExt(String ext);
}

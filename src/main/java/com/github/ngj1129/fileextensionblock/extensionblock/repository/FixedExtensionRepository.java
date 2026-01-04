package com.github.ngj1129.fileextensionblock.extensionblock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.ngj1129.fileextensionblock.extensionblock.domain.FixedExtension;

public interface FixedExtensionRepository extends JpaRepository<FixedExtension, Long> {
	Optional<FixedExtension> findByExt(String ext);
}

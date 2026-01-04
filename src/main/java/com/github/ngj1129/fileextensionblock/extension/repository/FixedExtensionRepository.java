package com.github.ngj1129.fileextensionblock.extension.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.ngj1129.fileextensionblock.extension.domain.FixedExtension;

public interface FixedExtensionRepository extends JpaRepository<FixedExtension, Long> {
	Optional<FixedExtension> findByExt(String ext);
}

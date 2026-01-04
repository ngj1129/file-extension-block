package com.github.ngj1129.fileextensionblock.extensionblock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.ngj1129.fileextensionblock.extensionblock.domain.CustomExtension;

public interface CustomExtensionRepository extends JpaRepository<CustomExtension, Long> {
}

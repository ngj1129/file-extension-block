package com.github.ngj1129.fileextensionblock.extensionblock.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.github.ngj1129.fileextensionblock.extensionblock.service.ExtensionBlockService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ExtensionBlockPageController {

	private final ExtensionBlockService extensionBlockService;

	@GetMapping({"/", "/extensions"})
	public String page(Model model) {
		var fixed = extensionBlockService.getFixedExtensionList().fixedExtensions();
		var custom = extensionBlockService.getCustomExtensionList().customExtensions();

		model.addAttribute("fixedExtensions", fixed);
		model.addAttribute("customExtensions", custom);
		model.addAttribute("customCount", custom.size());
		model.addAttribute("customLimit", 200);

		return "extensions/index";
	}
}

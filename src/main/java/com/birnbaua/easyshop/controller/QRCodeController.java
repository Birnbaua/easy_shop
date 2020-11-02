package com.birnbaua.easyshop.controller;

import java.io.ByteArrayInputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.birnbaua.easyshop.qr.QrCodeGenerator;

@Controller
@RequestMapping("/qrcode")
public class QRCodeController {
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping()
	public ResponseEntity<InputStreamResource> getPdf(@RequestParam String website, @RequestParam String shop, @RequestParam String prefix, @RequestParam Integer min, @RequestParam Integer max) {
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(new ByteArrayInputStream(QrCodeGenerator.getPdfBytes(website,shop,prefix,min,max))));
	}
}

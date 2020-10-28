package com.birnbaua.easyshop.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.birnbaua.easyshop.qr.QrCodeGenerator;

@Controller
@RequestMapping("/qrcode")
public class QRCodeController {
	
	@GetMapping()
	public ResponseEntity<InputStreamResource> getPdf(@RequestParam String website, @RequestParam String shop, @RequestParam String prefix, @RequestParam Integer min, @RequestParam Integer max) {
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(new ByteArrayInputStream(QrCodeGenerator.getPdfBytes(website,shop,prefix,min, max))));
	}
	
	//converter for buffered image
	@Bean
	public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
	    return new BufferedImageHttpMessageConverter();
	}
}

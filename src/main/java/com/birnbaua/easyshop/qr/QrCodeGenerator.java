package com.birnbaua.easyshop.qr;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class QrCodeGenerator {
	
	public static List<BufferedImage> getCodes(String prefix, int from, int to) {
		List<BufferedImage> codes = new LinkedList<>();
		for(int i = from;i < to; i++) {
			try {
				codes.add(generateQRCodeImage(prefix + i));
			} catch (WriterException e) {e.printStackTrace();}
		}
		return codes;
	}
	
	public static byte[] getPdfBytes(List<BufferedImage> images) {
		Document doc = new Document(PageSize.A4,20,20,20,20);
		ByteArrayOutputStream pdfByteStream = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(doc, pdfByteStream);
			doc.open();
		} catch (DocumentException e1) {e1.printStackTrace();}
		images.forEach(x -> {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(x, "jpg", baos);
				baos.flush();
				doc.add(Image.getInstance(baos.toByteArray()));
				doc.newPage();
			} catch (DocumentException | IOException e) {
				e.printStackTrace();
			} finally {
				try {baos.close();} catch (IOException e) {}
			}
		});
		doc.close();
		return pdfByteStream.toByteArray();
	}
	
	private static BufferedImage generateQRCodeImage(String text) throws WriterException {
		QRCodeWriter barcodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = barcodeWriter.encode(text, BarcodeFormat.QR_CODE, 550, 550);
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

}

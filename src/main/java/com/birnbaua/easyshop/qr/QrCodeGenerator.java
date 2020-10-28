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
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class QrCodeGenerator {
	
	public static byte[] getPdfBytes(String website, String shop, String prefix, int from, int to) {
		if(to-from >= 100) {
			throw new IllegalArgumentException("You are only allowed to create max. 100 qr codes at once. You tried to create " + (to-from));
		}
		Document doc = new Document(PageSize.A4,20,20,20,20);
		ByteArrayOutputStream pdfByteStream = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(doc, pdfByteStream);
			doc.open();
		} catch (DocumentException e1) {e1.printStackTrace();}
		for(int i = from;i <= to;i++) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(getCode(website,shop,prefix,i), "jpg", baos);
				baos.flush();
				doc.add(Image.getInstance(baos.toByteArray()));
				Paragraph p = new Paragraph();
				p.setAlignment(Element.ALIGN_CENTER);
				Font f = FontFactory.getFont(FontFactory.TIMES, 60f);
				Chunk c = new Chunk(String.format("%s%s", prefix,i),f);
				p.add(c);
				doc.add(p);
				doc.newPage();
			} catch (DocumentException | IOException e) {
				e.printStackTrace();
			} finally {
				try {baos.close();} catch (IOException e) {}
			}
		}
		doc.close();
		return pdfByteStream.toByteArray();
	}
	
	public static BufferedImage getCode(String website, String shop, String prefix, int nr) {
		BufferedImage image = null;
		try {
			image = generateQRCodeImage(website + "/shop/" + shop + "?table=" + prefix + nr);
		} catch (WriterException e) {e.printStackTrace();}
		return image;
	}
	
	private static BufferedImage generateQRCodeImage(String text) throws WriterException {
		QRCodeWriter barcodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = barcodeWriter.encode(text, BarcodeFormat.QR_CODE, 550, 550);
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

}

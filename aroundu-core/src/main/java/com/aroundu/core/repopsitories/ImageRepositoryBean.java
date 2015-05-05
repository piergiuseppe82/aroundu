/**
 * 
 */
package com.aroundu.core.repopsitories;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.imageio.ImageIO;

import org.geotools.data.Base64;

import com.aroundu.core.infrastructure.RepositoryBean;
import com.aroundu.core.supports.Utility;

/**
 * @author piergiuseppe82
 *
 */
public class ImageRepositoryBean extends RepositoryBean {
	
	
	
	public enum ImageDimesionType{
		
		LARGE_IMAGE (2048,"img/original"),
		THUMBNAILS_IMAGE(800,"img/thumb"),
		PROFILE_IMAGE(320,"img/avatar");
		
	    private final int wSize;
	    private final String name;
	    
	    ImageDimesionType(int wSize, String name) {
	        this.wSize = wSize;
	        this.name = name;
	    }
	    
	    public String toString(){
	    	return name;
	    }
	    
	    public int toValue(){
	    	return wSize;
	    }
	    
	}
	
	public String saveImage(String lexicalXSDBase64Binary,  ImageDimesionType type, String username) throws Exception{
		 try {
			int indexOfEnd = lexicalXSDBase64Binary.indexOf(";");
			int indexOfStart = lexicalXSDBase64Binary.indexOf("/");
			String imageFormat  = lexicalXSDBase64Binary.substring(indexOfStart+1,indexOfEnd);
			byte[] originalBinary = Base64.decode(lexicalXSDBase64Binary.split(",")[1]);
			String fileName = new BigInteger(130, new SecureRandom()).toString(32);
			if(type.equals(ImageDimesionType.PROFILE_IMAGE)){				
				return saveImage(fileName,imageFormat,ImageDimesionType.PROFILE_IMAGE,username,originalBinary);
			}else{
				saveImage(fileName,imageFormat,ImageDimesionType.LARGE_IMAGE,username,originalBinary);
				return saveImage(fileName,imageFormat,ImageDimesionType.THUMBNAILS_IMAGE,username,originalBinary);
			}			
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Exception("Error during write image");
		}
	}

	/**
	 * @param string
	 * @param largeImage
	 * @param userId
	 * @param originalBinary 
	 * @throws Exception 
	 */
	private String saveImage(String filename, String fileformat,ImageDimesionType typeImage,
			String username, byte[] in) throws Exception {
		String urlImage = typeImage.toString()+"/"+filename+"."+fileformat;
		String relativePath = username+"/"+urlImage;
		writeImage(in, typeImage, fileformat, relativePath);
		return urlImage;
	}

	

	/**
	 * @param relativePath
	 * @return
	 */
	private File checkFullPath(String relativePath) {
		String fullPath = Utility.getImagesDirectoryPath()+File.separator+relativePath;
		File file = new File(fullPath);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		return file;
	}

	private void writeImage(byte[] in, ImageDimesionType imageDimesionType,String imageFormat,String relativePath ) throws Exception {
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(in);
			BufferedImage img = ImageIO.read(is);
    		if(img.getWidth()>imageDimesionType.toValue()){
    			img = scale(imageDimesionType.toValue(), 0, in,imageFormat);
    		}
    		File output = checkFullPath(relativePath);
			ImageIO.write(img,imageFormat, output);
    	} catch (Throwable e) {
    		e.printStackTrace();
    		throw new Exception("Image not resizable");
    	}
    }

	private BufferedImage scale(int width, int height, byte[] in, String format) throws IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(in);
		BufferedImage img = ImageIO.read(is);
		if(height == 0) {
			height = (width * img.getHeight())/ img.getWidth(); 
		}
		if(width == 0) {
			width = (height * img.getWidth())/ img.getHeight();
		}
		return resize(img, width, height);
	}
	
	
	
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());  
        Graphics2D g = dimg.createGraphics();  
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);  
        g.dispose();  
        return dimg;  
    }  
	
	
}

package com.aroundu.rest.resource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aroundu.core.supports.Utility;

/**
 * @author piergiuseppe82
 *
 */
@Path("/img")
public class ImageResource extends AbstractAppResource {
	Logger log = LoggerFactory.getLogger(ImageResource.class);
	
	@Context HttpServletRequest req;
	
	@Path("/avatar/{filename}.{type}")
    @GET
    public Response getAvatar(@PathParam("filename") String filename,@PathParam("type") String type){
    	log.debug("Called GET - img/avatar");
		getAuthorizedUser(req);
		try {

		    BufferedImage image = ImageIO.read(new File(Utility.getImagesDirectoryPath()+File.separator+"img"+File.separator+"avatar"+File.separator+filename+"."+type));

		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    ImageIO.write(image, type, baos);
		    byte[] imageData = baos.toByteArray();

		    // uncomment line below to send non-streamed
		     return Response.ok(imageData).header("Content-Type", "img/"+type).build();

//		     return Response.ok(new ByteArrayInputStream(imageData)).header("Content-Type", "img/"+type).build();
			
		} catch (Exception e) {
			log.error("Error",e);
			return Response.status(Status.NOT_FOUND).build();
		}
    }
	
	
	@Path("/thumb/{filename}.{type}")
    @GET
    public Response getThumb(@PathParam("filename") String filename,@PathParam("type") String type){
    	log.debug("Called GET - img/thumb");
    	getAuthorizedUser(req);
		try {

		    BufferedImage image = ImageIO.read(new File(Utility.getImagesDirectoryPath()+File.separator+"img"+File.separator+"thumb"+File.separator+filename+"."+type));

		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    ImageIO.write(image, type, baos);
		    byte[] imageData = baos.toByteArray();

		    // uncomment line below to send non-streamed
		     return Response.ok(imageData).header("Content-Type", "img/"+type).build();

//		     return Response.ok(new ByteArrayInputStream(imageData)).header("Content-Type", "img/"+type).build();
			
		} catch (Exception e) {
			log.error("Error",e);
			return Response.status(Status.NOT_FOUND).build();
		}
    }
	
	
	
		
	

}

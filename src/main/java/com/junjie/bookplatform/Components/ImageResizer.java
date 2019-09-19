package com.junjie.bookplatform.Components;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Component
public class ImageResizer {

//    pixels

    private int IMG_BOOK_SIZE = 500;

    private int PROFILE_THUMBNAIL_SIZE= 150;

    public byte[] resize(MultipartFile file ) throws IOException {
        BufferedImage img = ImageIO.read(file.getInputStream());
        BufferedImage resized = this.resize(img);

        /*
            got return image resized @ method :  this.resize();
         */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resized,"jpg",baos);
        baos.flush();
        byte[] resultImg = baos.toByteArray();
        baos.close();
        return resultImg;
    }

    private  BufferedImage resize(BufferedImage img) {
        BufferedImage thumbImg = Scalr.resize(img, Scalr.Method.AUTOMATIC,
                Scalr.Mode.AUTOMATIC, IMG_BOOK_SIZE, Scalr.OP_ANTIALIAS);
        return thumbImg;
    }
}

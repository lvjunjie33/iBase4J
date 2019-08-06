package top.ibase4j.core.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;


















public final class ImageUtil
{
  public static final void changeImge(File img, int width, int height) {
    try {
      Thumbnails.of(new File[] { img }).size(width, height).keepAspectRatio(false).toFile(img);
    } catch (IOException e) {
      e.printStackTrace();
      throw new IllegalStateException("图片转换出错！", e);
    } 
  }









  
  public static final void scale(BufferedImage orgImg, double scale, String targetFile) throws IOException { Thumbnails.of(new BufferedImage[] { orgImg }).scale(scale).toFile(targetFile); }


  
  public static final void scale(String orgImgFile, double scale, String targetFile) throws IOException { Thumbnails.of(new String[] { orgImgFile }).scale(scale).toFile(targetFile); }













  
  public static final void format(String orgImgFile, int width, int height, String suffixName, String targetFile) throws IOException { Thumbnails.of(new String[] { orgImgFile }).size(width, height).outputFormat(suffixName).toFile(targetFile); }









  
  public static final double scaleWidth(BufferedImage orgImg, int targetWidth, String targetFile) throws IOException {
    int orgWidth = orgImg.getWidth();
    
    double scale = targetWidth * 1.0D / orgWidth;
    
    scale(orgImg, scale, targetFile);
    
    return scale;
  }
  
  public static final void scaleWidth(String orgImgFile, int targetWidth, String targetFile) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(new File(orgImgFile));
    scaleWidth(bufferedImage, targetWidth, targetFile);
  }








  
  public static final double scaleHeight(BufferedImage orgImg, int targetHeight, String targetFile) throws IOException {
    int orgHeight = orgImg.getHeight();
    double scale = targetHeight * 1.0D / orgHeight;
    scale(orgImg, scale, targetFile);
    return scale;
  }
  
  public static final void scaleHeight(String orgImgFile, int targetHeight, String targetFile) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(new File(orgImgFile));
    
    scaleHeight(bufferedImage, targetHeight, targetFile);
  }

  
  public static final void scaleWidth(File file, Integer width) throws IOException {
    String fileName = file.getName();
    String filePath = file.getAbsolutePath();
    String postFix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    
    BufferedImage bufferedImg = ImageIO.read(file);
    String targetFile = filePath + "_s" + postFix;
    scaleWidth(bufferedImg, width.intValue(), targetFile);
    String targetFile2 = filePath + "@" + width;
    (new File(targetFile)).renameTo(new File(targetFile2));
  }
}

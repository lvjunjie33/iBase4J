package top.ibase4j.core.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;











public class QrcodeUtil
{
  private static Logger logger = LogManager.getLogger();

  
  public static String createQrcode(String dir, String content) { return createQrcode(dir, content, 300, 300); }

  
  public static String createQrcode(String dir, String content, int width, int height) {
    try {
      File dirs = new File(dir);
      if (!dirs.exists()) {
        dirs.mkdirs();
      }
      String qrcodeFormat = "png";
      HashMap<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
      hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
      BitMatrix bitMatrix = (new MultiFormatWriter()).encode(content, BarcodeFormat.QR_CODE, width, height, hints);
      
      File file = new File(dir + "/" + UUID.randomUUID().toString() + "." + qrcodeFormat);
      MatrixToImageWriter.writeToPath(bitMatrix, qrcodeFormat, file.toPath());
      return file.getAbsolutePath();
    } catch (Exception e) {
      logger.error("", e);
      
      return "";
    } 
  }
  public static String decodeQr(String filePath) {
    String retStr = "";
    if ("".equalsIgnoreCase(filePath) && filePath.length() == 0) {
      return "图片路径为空!";
    }
    try {
      BufferedImage bufferedImage = ImageIO.read(new FileInputStream(filePath));
      BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(bufferedImage);
      HybridBinarizer hybridBinarizer = new HybridBinarizer(bufferedImageLuminanceSource);
      BinaryBitmap bitmap = new BinaryBitmap(hybridBinarizer);
      HashMap<DecodeHintType, Object> hintTypeObjectHashMap = new HashMap<DecodeHintType, Object>();
      hintTypeObjectHashMap.put(DecodeHintType.CHARACTER_SET, "UTF-8");
      Result result = (new MultiFormatReader()).decode(bitmap, hintTypeObjectHashMap);
      retStr = result.getText();
    } catch (Exception e) {
      logger.error("", e);
    } 
    return retStr;
  }
}

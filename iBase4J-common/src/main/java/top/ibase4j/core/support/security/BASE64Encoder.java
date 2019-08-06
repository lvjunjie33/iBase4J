package top.ibase4j.core.support.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;









public class BASE64Encoder
{
  private static final char[] PEM_ARRAY = { 
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '+', '/' };










  
  private static final byte[] pem_convert_array = new byte[256];
  
  private byte[] decodeBuffer = new byte[4];






  
  public String encode(byte[] bt) {
    int totalBits = bt.length * 8;
    int nn = totalBits % 6;
    int curPos = 0;
    StringBuilder toReturn = new StringBuilder(32);
    while (curPos < totalBits) {
      int pos, bytePos = curPos / 8;
      switch (curPos % 8) {
        case 0:
          toReturn.append(PEM_ARRAY[(bt[bytePos] & 0xFC) >> 2]);
          break;
        case 2:
          toReturn.append(PEM_ARRAY[bt[bytePos] & 0x3F]);
          break;
        case 4:
          if (bytePos == bt.length - 1) {
            toReturn.append(PEM_ARRAY[(bt[bytePos] & 0xF) << 2 & 0x3F]); break;
          } 
          pos = ((bt[bytePos] & 0xF) << 2 | (bt[bytePos + 1] & 0xC0) >> 6) & 0x3F;
          toReturn.append(PEM_ARRAY[pos]);
          break;
        
        case 6:
          if (bytePos == bt.length - 1) {
            toReturn.append(PEM_ARRAY[(bt[bytePos] & 0x3) << 4 & 0x3F]); break;
          } 
          pos = ((bt[bytePos] & 0x3) << 4 | (bt[bytePos + 1] & 0xF0) >> 4) & 0x3F;
          toReturn.append(PEM_ARRAY[pos]);
          break;
      } 


      
      curPos += 6;
    } 
    if (nn == 2) {
      toReturn.append("==");
    } else if (nn == 4) {
      toReturn.append("=");
    } 
    return toReturn.toString();
  }



  
  public byte[] decode(String str) throws IOException {
    byte[] arrayOfByte = str.getBytes();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(arrayOfByte);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    decodeBuffer(inputStream, outputStream);
    return outputStream.toByteArray();
  }
  
  private void decodeBuffer(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
    PushbackInputStream localPushbackInputStream = new PushbackInputStream(paramInputStream);
    int j = 0;
    try {
      while (true) {
        int k = bytesPerLine();
        int i = 0;
        if (i + bytesPerAtom() < k) {
          decodeAtom(localPushbackInputStream, paramOutputStream, bytesPerAtom());
          j += bytesPerAtom();
          i += bytesPerAtom();
          
          continue;
        } 
        if (i + bytesPerAtom() == k) {
          decodeAtom(localPushbackInputStream, paramOutputStream, bytesPerAtom());
          j += bytesPerAtom(); continue;
        } 
        decodeAtom(localPushbackInputStream, paramOutputStream, k - i);
        j += k - i;
      } 
    } catch (RuntimeException e) {
      String.valueOf(j);
      return;
    } 
  }


  
  private int bytesPerAtom() { return 4; }


  
  private int bytesPerLine() { return 72; }



  
  private void decodeAtom(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream, int paramInt) throws IOException {
    int j = -1;
    int k = -1;
    int m = -1;
    int n = -1;
    
    if (paramInt < 2) {
      throw new ArrayStoreException("BASE64Decoder: Not enough bytes for an atom.");
    }
    int i;
	do {
      i = paramPushbackInputStream.read();
      if (i == -1) {
        throw new RuntimeException();
      }
    } while (i == 10 || i == 13);
    this.decodeBuffer[0] = (byte)i;
    
    int is = readFully(paramPushbackInputStream, this.decodeBuffer, 1, paramInt - 1);
    if (is == -1) {
      throw new RuntimeException();
    }
    
    if (paramInt > 3 && this.decodeBuffer[3] == 61) {
      paramInt = 3;
    }
    if (paramInt > 2 && this.decodeBuffer[2] == 61) {
      paramInt = 2;
    }
    switch (paramInt) {
      case 4:
        n = pem_convert_array[this.decodeBuffer[3] & 0xFF];
      case 3:
        m = pem_convert_array[this.decodeBuffer[2] & 0xFF];
      case 2:
        k = pem_convert_array[this.decodeBuffer[1] & 0xFF];
        j = pem_convert_array[this.decodeBuffer[0] & 0xFF];
        break;
    } 
    switch (paramInt) {
      case 2:
        paramOutputStream.write((byte)(j << 2 & 0xFC | k >>> 4 & 0x3));
        break;
      case 3:
        paramOutputStream.write((byte)(j << 2 & 0xFC | k >>> 4 & 0x3));
        paramOutputStream.write((byte)(k << 4 & 0xF0 | m >>> 2 & 0xF));
        break;
      case 4:
        paramOutputStream.write((byte)(j << 2 & 0xFC | k >>> 4 & 0x3));
        paramOutputStream.write((byte)(k << 4 & 0xF0 | m >>> 2 & 0xF));
        paramOutputStream.write((byte)(m << 6 & 0xC0 | n & 0x3F));
        break;
    } 
  }
  
  private int readFully(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
    for (int i = 0; i < paramInt2; i++) {
      int j = paramInputStream.read();
      if (j == -1) {
        return (i == 0) ? -1 : i;
      }
      paramArrayOfByte[i + paramInt1] = (byte)j;
    } 
    return paramInt2;
  }
  
  static  {
    for (int i = 0; i < 255; i++) {
      pem_convert_array[i] = -1;
    }
    for (int i = 0; i < PEM_ARRAY.length; i++)
      pem_convert_array[PEM_ARRAY[i]] = (byte)i; 
  }
}

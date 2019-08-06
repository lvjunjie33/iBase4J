package org.ibase4j.core;

import top.ibase4j.core.Constants;
public class Configs {
	  public static void main(String[] args) {
	        String encrypt = SecurityUtil.encryptDes("3nodapp", Constants.DB_KEY.getBytes());
	        System.out.println(encrypt);
	        System.out.println(SecurityUtil.decryptDes(encrypt, Constants.DB_KEY.getBytes()));
	    }
}

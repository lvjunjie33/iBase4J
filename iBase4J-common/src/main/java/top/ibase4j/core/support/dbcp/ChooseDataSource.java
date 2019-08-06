package top.ibase4j.core.support.dbcp;

import java.util.Map;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;














public class ChooseDataSource
  extends AbstractRoutingDataSource
{
  protected Object determineCurrentLookupKey() { 
	  return HandleDataSource.getDataSource(); 
	  
  }
  
  public void setMethodType(Map<String, String> map) {
	  // Byte code:
  }
    //   0: aload_1
    //   1: invokeinterface entrySet : ()Ljava/util/Set;
    //   6: invokeinterface iterator : ()Ljava/util/Iterator;
    //   11: astore_2
    //   12: aload_2
    //   13: invokeinterface hasNext : ()Z
    //   18: ifeq -> 126
    //   21: aload_2
    //   22: invokeinterface next : ()Ljava/lang/Object;
    //   27: checkcast java/util/Map$Entry
    //   30: astore_3
    //   31: new java/util/ArrayList
    //   34: dup
    //   35: invokespecial <init> : ()V
    //   38: astore #4
    //   40: aload_3
    //   41: invokeinterface getValue : ()Ljava/lang/Object;
    //   46: checkcast java/lang/String
    //   49: ldc ','
    //   51: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   54: astore #5
    //   56: aload #5
    //   58: astore #6
    //   60: aload #6
    //   62: arraylength
    //   63: istore #7
    //   65: iconst_0
    //   66: istore #8
    //   68: iload #8
    //   70: iload #7
    //   72: if_icmpge -> 106
    //   75: aload #6
    //   77: iload #8
    //   79: aaload
    //   80: astore #9
    //   82: aload #9
    //   84: invokestatic isNotBlank : (Ljava/lang/CharSequence;)Z
    //   87: ifeq -> 100
    //   90: aload #4
    //   92: aload #9
    //   94: invokeinterface add : (Ljava/lang/Object;)Z
    //   99: pop
    //   100: iinc #8, 1
    //   103: goto -> 68
    //   106: getstatic top/ibase4j/core/support/dbcp/HandleDataSource.METHODTYPE : Ljava/util/Map;
    //   109: aload_3
    //   110: invokeinterface getKey : ()Ljava/lang/Object;
    //   115: aload #4
    //   117: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   122: pop
    //   123: goto -> 12
    //   126: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #27	-> 0
    //   #28	-> 31
    //   #29	-> 40
    //   #30	-> 56
    //   #31	-> 82
    //   #32	-> 90
    //   #30	-> 100
    //   #35	-> 106
    //   #36	-> 123
    //   #37	-> 126
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   82	18	9	type	Ljava/lang/String;
    //   40	83	4	v	Ljava/util/List;
    //   56	67	5	types	[Ljava/lang/String;
    //   31	92	3	entry	Ljava/util/Map$Entry;
    //   0	127	0	this	Ltop/ibase4j/core/support/dbcp/ChooseDataSource;
    //   0	127	1	map	Ljava/util/Map;
    // Local variable type table:
    //   start	length	slot	name	signature
    //   40	83	4	v	Ljava/util/List<Ljava/lang/String;>;
    //   31	92	3	entry	Ljava/util/Map$Entry<Ljava/lang/String;Ljava/lang/String;>;
    //   0	127	1	map	Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>; }
//}
}
package com.lew.scott.screencapture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.plaf.TreeUI;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Judge {
    /**
     * 图片转化成base64字符串
     * @param imgPath
     * @return
     */
    public static String GetImageStr(String imgPath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = imgPath;// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        String encode = null; // 返回Base64编码过的字节数组字符串
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            // 读取图片字节数组
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            encode = encoder.encode(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return encode;
    }

    /**
     * base64字符串转化成图片
     * 
     * @param imgData
     *            图片编码
     * @param imgFilePath
     *            存放到本地路径
     * @return
     * @throws IOException
     */
    @SuppressWarnings("finally")
    public static boolean GenerateImage(String imgData, String imgFilePath) throws IOException { // 对字节数组字符串进行Base64解码并生成图片
        if (imgData == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            out = new FileOutputStream(imgFilePath);
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgData);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            out.write(b);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
            return true;
        }
    }
    //这个函数应该是判断是否是同一张图片后删除同一张图片才对
    public  Judge(String Path) {
    	//java找到一个路径下所有的.jpg文件
    	//文件按照创建时间再进行排序
    	File file = new File(Path);
    	File[] tempList = file.listFiles();
    	//这是排序
    	Arrays.sort(tempList, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;//如果 if 中修改为 返回-1 同时此处修改为返回 1  排序就会是递减
            }

            public boolean equals(Object obj) {
                return true;
            }

        });
//    	for(int i = 0 ;i < tempList.length;i++)
//    	{
//    		fileNameList.add(tempList[i].getAbsolutePath());
//    	}
    		//1,0个
    		if(tempList.length==1||tempList.length==0)
    			return;
    		//2个
    		else if(tempList.length==2)
    			{
    				//tempList[0]
    				//tempList[1]
    			//先得到地址,再得到码
    		String Path1 = tempList[0].getAbsolutePath();
    		String Path2 = tempList[1].getAbsolutePath();
    		String image1 = Judge.GetImageStr(Path1);
    		String image2 = Judge.GetImageStr(Path2);
    		if(image1.equals(image2))
    		{
    			//删掉
    			tempList[1].delete();
    			//这里是删除的File文件对象
    			return;
    		}
    		
    			}
    		//大于等于3个
    		//只判断最近的两个就可
    		else {
    			
    			for(int i=0;i<tempList.length-1;i++)
    			{
    				String Path1 = tempList[i].getAbsolutePath();
    				String Path2 = tempList[i+1].getAbsolutePath();
    				String image1 = Judge.GetImageStr(Path1);
    				String image2 = Judge.GetImageStr(Path2);
    				if(image1.equals(image2))
    	    		{
    	    			//删掉
    	    			tempList[i+1].delete();
    	    			//这里是删除的File文件对象
    	    		}
    			}
    			
    		}
    }
    //算计
}
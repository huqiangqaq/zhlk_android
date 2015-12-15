package android.hardware.barcode;
   
import java.io.IOException;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class Scanner {
	static public final int BARCODE_READ= 10;
	static public final int BARCODE_NOREAD = 12;
	 static Boolean m_bASYC=false;	  
	 static int m_nCommand=0;
	 static public Handler m_handler=null;
	 /**
	  * 同步扫描一维/二维码，扫描不到将等待一段时间，摩托罗拉的扫描头等待最长3秒，
	  * 自主研发扫描头最长等待10秒，自动识别是自主研发扫描头还是摩托罗拉的扫描头
	  * @return 		：返回扫描到的条码，如果没扫到将为空字符串
	  */
	 static public native String ReadSCAAuto(int nTimeout);
	 /**
	  * 同步扫描一维/二维码，扫描不到将等待一段时间，摩托罗拉的扫描头等待最长3秒，
	  * 自主研发扫描头最长等待10秒
	  * @param 	nCode 	：扫描头标示，自主研发扫描头0x55,摩托罗拉扫描头0x00
	  * @return			：返回扫描到的条码，如果没扫到将为空字符串
	  */
	 static public native String ReadSCA(int nCode,int nTimeout);
	 /**
	  * 同步扫描一维/二维码，扫描不到将等待一段时间，摩托罗拉的扫描头等待最长3秒，
	  * 自主研发扫描头最长等待10秒
	  * @param nCommand	:扫描头标示，自主研发扫描头0x55,摩托罗拉扫描头0x00
	  * @param nCode	:条码的编码方式，GB2312为1,UTF为0
	  * @return			：返回扫描到的条码，如果没扫到将为空字符串
	  */
	 static public native String ReadSCAEx(int nCommand,int nCode,int nTimeout);
	 /**
	  * 同步扫描一维/二维码，扫描不到将等待一段时间，摩托罗拉的扫描头等待最长3秒，
	  * 自主研发扫描头最长等待10秒
	  * @param nCommand	:扫描头标示，自主研发扫描头0x55,摩托罗拉扫描头0x00
	  * @param buf		:存放条码内容，读回来后根据条码本身格式转成字符串
	  * @return			：返回扫描到的条码，如果没扫到将为空字符串
	  */
	 static public native int ReadDataSCA(int nCommand,byte []buf,int nTimeout);
	 /**
	  * 同步扫描一维/二维码，扫描不到将等待一段时间，摩托罗拉的扫描头等待最长3秒，
	  * 自主研发扫描头最长等待10秒
	  * @param nCommand	:扫描头标示，自主研发扫描头0x55,摩托罗拉扫描头0x00
	  * @return			:返回扫描到的条码内容
	  */

	 static public native byte[] ReadData(int nCommand,int nTimeout);
	 /**
	  * 初始化设备  
	  * 
	  * @return			：成功返回0
	  */
	 static public native int InitSCA();
	 /**
	  * 异步扫描，调用该函数会创建一个扫描线程去扫描，
	  * 扫描到条码或者扫描超时都会通过handle发送消息给接受者，
	  * 所以使用异步扫描的话必须在你的接收代码里创建个handle,
	  * 并把handle赋给m_handler，扫描到条码发送Message BARCODE_READ，
	  * 超时发送Message BARCODE_NOREAD
	  */
	 static public void Read()
	   {
		 //Log.d("ScannerJNI", "Read");
		   if(m_bASYC)
		   {
			   //Log.d("ScannerJNI", "m_bASYC");
		       return;
		   }
		   else
		   {
			   //m_nCommand=nCode;
			   StartASYC();  
		   }
	   }
	 /**
	  * 扫描线程
	  */
	   static  void StartASYC()
      {
		   //Log.d("ScannerJNI", "StartASYC");
		   m_bASYC=true;		   
		   Thread thread = new Thread(new Runnable() {
  		   public void run() {  		  
  			  
  			   if(m_handler!=null)
  			   {    
  				 Log.d("ScannerJNI", "ReadSCAAuto");
  				 String str=ReadSCAAuto(1000000);   
  				   Message msg=new Message();  
  				   msg.what=str.length()>0?BARCODE_READ:BARCODE_NOREAD;
  				   msg.obj=str;   
  				          
  				   m_handler.sendMessage(msg);   
  				 
  			   }       
  			 m_bASYC=false;
  		   }  
  		 });
		   thread.start();
      }
	   /**
	    * 虚拟键盘消息，可以把String按键盘消息方式发送给系统
	    * @param str		：要发送的字符串
	    */
	 static public  void SendString(String str)               
	    {    			     
	        try {  
	        	Runtime.getRuntime().exec("input keyevent 66"); 
	            Runtime.getRuntime().exec("input text "+str);  	

	        } catch (IOException e) { 
	            // TODO Auto-generated catch block             
	            e.printStackTrace();            
	            Log.i("run",e.toString());                                            
	        				}    
	    }  
	  static 	private void showToast(String str){ 
    	    Toast.makeText(null, str, Toast.LENGTH_SHORT).show();}
    	

	static{
		//System.loadLibrary("Cilico-Scan");    
		System.loadLibrary("scan-tools");  
	}	

}
    

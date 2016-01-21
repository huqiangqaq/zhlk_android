package cilico.tools;

import java.io.IOException;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcV;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
public class control_nfc {
	static public byte[] getUID(Intent intent) {
		if (intent != null)
			return intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
		else
			return null;
	}
	/**
	 * @author Administrator
	 * 读取标签块内容 
	 * 
	 * ****/
	static public String readSingOneBlock(Intent intent, String password,
			int address) {
		Tag m_tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		if (intent != null) {
			byte[] passw = stringToBytes(password);
			if (address<0) {
				return "";
			} else {
				//int add = Integer.valueOf(address);
				if (m_tagFromIntent != null) {
					MifareClassic mfc = MifareClassic.get(m_tagFromIntent);
					if (mfc != null) {
						try {
							mfc.connect();
							int bSec = mfc.blockToSector(address);
							Log.i("ichoiceTest", "Addr in readSingOneBlock is : " + address);
							Log.i("ichoiceTest", "Sector is : "+bSec);
							Log.i("ichoiceTest", "Total Sector is : "+mfc.getSectorCount());
							Log.i("ichoiceTest", mfc.toString());
							if (bSec < mfc.getSectorCount()) {
								if (mfc.authenticateSectorWithKeyA(bSec, passw)) {
									byte[] data = mfc.readBlock(address);
									if (data != null) 
									{
										String strid = bytesToHexString(data);
										strid=hexStr2Str(strid.substring(0,16));
										mfc.close();
										return strid;
									} else {
										mfc.close();
										return "";
									}
								}
							} else
								// strUI += "Please check you password";
								mfc.close();
						} 
						catch (IOException e) {
							//e.printStackTrace();
                            return "";
						}
					}
				}
			}
			return "";
		}
		return "";
	}
	/**
	 * 写标签
	 * @author Administrator
	 * 2015-07-24
	 * */
	// write one block
	static public Boolean WriteSigoneBlock(Intent intent, String password,
			int address,String dataE) {
			byte[] passw = stringToBytes(password);													// number
			byte[] myByte = dataE.trim().getBytes();
			byte[] pwrite = new byte[16];
			System.arraycopy(myByte, 0, pwrite, 0, myByte.length > 16 ? 16
					: myByte.length);
			Tag m_tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			if (address<0) {
				return  false;
			}
			else {
				if (m_tagFromIntent != null) {
					MifareClassic mfc = MifareClassic.get(m_tagFromIntent);
					if (mfc != null) {
						try {
							mfc.connect();
							int bSec = mfc.blockToSector(address);
							if (bSec < mfc.getSectorCount()) {
								if (mfc.authenticateSectorWithKeyA(bSec,
										passw)) {
									mfc.writeBlock(address, pwrite);
									mfc.close();
									return true;
								}
							} else
							{
							  mfc.close();
							  return false;
							}
						} catch (IOException e) {
							
							return false;
							//e.printStackTrace();

						}
					}
				}
			}
			return false;
		}
	/**   
	 * 十六进制转换字符串  
	 * @param String str Byte字符串(Byte之间无分隔符 如:[616C6B])  
	 * @return String 对应的字符串  
	 */      
	public static String hexStr2Str(String hexStr)    
	{      
	    String str = "0123456789ABCDEF";      
	    char[] hexs = hexStr.toCharArray();      
	    byte[] bytes = new byte[hexStr.length()/2];      
	    int n;      
	    for (int i = 0; i < bytes.length; i++)    
	    {      
	        n = str.indexOf(hexs[2 * i]) * 16;      
	        n += str.indexOf(hexs[2 * i + 1]);      
	        bytes[i] = (byte) (n & 0xff);      
	    }      
	    return new String(bytes);      
	}    
	// 字符序列转换为16进制字符串
	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		char[] buffer = new char[2];
		for (int i = 0; i < src.length; i++) {
			buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
			buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
			System.out.println(buffer);
			stringBuilder.append(buffer);
		}
		return stringBuilder.toString();
	}

	private static byte[] stringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}

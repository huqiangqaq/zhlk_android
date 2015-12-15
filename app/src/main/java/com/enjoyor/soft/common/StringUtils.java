package com.enjoyor.soft.common;

/**
 * Copyright (c) by hutuanle
 * All right reserved.
 * email:354777464@qq.com
 * Create Author: 胡团乐
 * Create Date: 2013-6-26上午11:33:52
 * File Name: 数字字符到对应的字符串转换
 * Last version: 1.0
 * Last Update Date: 2013-6-26
 * Change Log:
 */
public class StringUtils {

	public static String Num2Str(String type,String status){
		String stastr = "";
		
		switch (Integer.parseInt(status)) {
		case 0:
			stastr = "采购未卸货";
			break;
		case 1:
			stastr = ("采购卸货中");
			break;
		case 2:
			//未开始装卸货
			if("0".equalsIgnoreCase(type)){
				stastr = ("采购 未卸货");
			}else if("1".equalsIgnoreCase(type)){
				stastr = ("销售 未装货");
			}
//			stastr = ("采购卸货完成");
			break;
		case 3:
			//正在装卸货
			if("0".equalsIgnoreCase(type)){
				stastr = ("采购 正在卸货");
			}else if("1".equalsIgnoreCase(type)){
				stastr = ("销售 正在装货");
			}
//			stastr = ("销售未装货");
			break;
		case 4:
			//装卸货已完成
			if("0".equalsIgnoreCase(type)){
				stastr = ("采购 卸货完成");
			}else if("1".equalsIgnoreCase(type)){
				stastr = ("销售 装货完成");
			}
//			stastr = ("销售装货中");
			break;
		case 5:
			stastr = ("销售装货完成");
			break;
		default:
			break;
		}
		return stastr;
	}
}

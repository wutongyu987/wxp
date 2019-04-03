package com.wxp.common.util;//package com.wish.common.util;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.taobao.api.ApiException;
//import com.taobao.api.DefaultTaobaoClient;
//import com.taobao.api.TaobaoClient;
//import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
//import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
//
//public class MsgTool {
//	public static String SendURL="http://gw.api.taobao.com/router/rest";
//	public static String AppKey="23775404";
//	public static String Secret="bd993e9dc6803cdbd2204c4c9ed7a382";
//	public static void sendMsg(String recNum,String signName,String code,String para) throws ApiException {
//		TaobaoClient client = new DefaultTaobaoClient(SendURL, AppKey, Secret);
//		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//		req.setExtend("123456");
//		req.setSmsType("normal");
//		req.setSmsFreeSignName(signName);
//		req.setSmsParamString(para);
//		req.setRecNum(recNum);
//		req.setSmsTemplateCode(code);
//		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//	}
//
//
//	public static boolean resetPassword(String phone,String code){
//		try {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("code",code);
//			sendMsg(phone,"点盈科技","SMS_62645026","{\"code\":\""+code+"\",\"product\":\"路由上报\"}");
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//
//	/**
//	 * 创建指定数量的随机字符串
//	 * @param numberFlag 是否是数字
//	 * @param length
//	 * @return
//	 */
//	public static String createRandom(boolean numberFlag, int length){
//		String retStr = "";
//		String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
//		int len = strTable.length();
//		boolean bDone = true;
//		do {
//			retStr = "";
//			int count = 0;
//			for (int i = 0; i < length; i++) {
//				double dblR = Math.random() * len;
//				int intR = (int) Math.floor(dblR);
//				char c = strTable.charAt(intR);
//				if (('0' <= c) && (c <= '9')) {
//					count++;
//				}
//				retStr += strTable.charAt(intR);
//			}
//			if (count >= 2) {
//				bDone = false;
//			}
//		} while (bDone);
//		return retStr;
//	}
//
//
//	public static void main(String[] args) throws ApiException{
//		resetPassword("18115124359","1234556");
//	}
//
//
//}

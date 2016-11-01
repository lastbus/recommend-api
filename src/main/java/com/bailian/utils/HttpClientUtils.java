package com.bailian.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description: 发送http的get、post请求
 * @user zhangwenming
 * @date 2016年1月9日 下午2:18:53
 */
public class HttpClientUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
	
	private HttpClientUtils() {
		
	}
	
	/**
	 * 发送post http请求
	 * @param path  请求路径
	 * @param params  请求参数
	 * @param encoding  编码
	 * @return 请求是否成功
	 * @throws Exception  
	 * @author zhangwenming
	 */
	public static String postByString(String url, Map<String, String> params, String encoding) {

		StringBuilder data = new StringBuilder();
		if (params != null && !params.isEmpty()) {

			for (Map.Entry<String, String> entry : params.entrySet()) {
				// 取出参数名称，加到url中
				data.append(entry.getKey()).append("=");
				// 防止乱码
				try {
					data.append(URLEncoder.encode(entry.getValue(), encoding));
				} catch (UnsupportedEncodingException e) {
					logger.error("编码转换异常", e);
				}
				data.append("&");
			}
			data.deleteCharAt(data.length() - 1);
		}
		return sendHttpPost(url, data.toString(), "application/x-www-form-urlencoded");
	}
	
	
	/**
	 * 发送post http请求
	 * @param path  请求路径
	 * @param params  请求参数
	 * @param encoding  编码
	 * @return
	 * @throws Exception  
	 * @author zhangwenming
	 */
	public static String postByString(String url, JSONObject params, String encoding) {
		StringBuilder data = new StringBuilder();
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				data.append(key).append("=");
				// 防止乱码
				try {
					data.append(URLEncoder.encode(params.getString(key), encoding));
				} catch (UnsupportedEncodingException e) {
					logger.error("编码转换异常", e);
				} 
				data.append("&");
			}
			data.deleteCharAt(data.length() - 1);
		}
		return sendHttpPost(url, data.toString(), "application/x-www-form-urlencoded");
	}
	
	/**
	 * 
	 * @param url
	 * @param jsonparam  json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/> 
	 * @param encoding
	 * @return  
	 * @author zhangwenming
	 */
	public static String postByJSON(String url, String jsonparam, String encoding) {
		return sendHttpPost(url, jsonparam, "application/json");
	}
	
	
	public static String sendHttpPost(String url, String params, String contentType) {
		byte[] entity = params.getBytes();// 生成实体数据
		OutputStream outStream = null;
		String res = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);// 允许对外输出数据
			//conn.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
			conn.setRequestProperty("Content-Type", contentType);// 设置HTTP协议头字段
			conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
			// 管道建立好后，可以向 服务器传送数据
			outStream = conn.getOutputStream();
			outStream.write(entity);
			// 其实输出的东西此时没有输出，只有祈求想得到服务器段的某些信息时，才会发送出去
			if (conn.getResponseCode() == 200) {
				InputStream inStream = conn.getInputStream();
				byte[] result = null;
				result = inputStreamToBytes(inStream);
				return new String(result);
			}
			res = String.valueOf(conn.getResponseCode());
		} catch (IOException e ) {
			logger.error("发送POST请求出现异常！", e);
		} finally {
			try {
				if (outStream != null) {
					outStream.close();
				}
			} catch (IOException e) {
				logger.error("OutputStream管道关闭失败", e);
			}
		}
		return res;
	}

	/**
	 * 流转换成字节
	 * @param in
	 * @return  
	 * @author zhangwenming
	 */
	public static byte[] inputStreamToBytes(InputStream in) {
		byte[] result = null;
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			in.close();
			out.flush();
			result = out.toByteArray();
		} catch (IOException e) {
			logger.error("流关闭失败", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				logger.error("流关闭失败", ex);
			}
		}
		return result;
	}
	

	/**
	 * 发送get http请求
	 * @param path  请求路径
	 * @param params  请求参数
	 * @param encoding  编码
	 * @return
	 * @throws Exception  
	 * @author zhangwenming
	 */
	public static String getByString(String url, JSONObject params, String encoding) {
		StringBuilder data = new StringBuilder();
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				data.append(key).append("=");
				// 防止乱码
				try {
					data.append(URLEncoder.encode(params.getString(key), encoding));
				} catch (UnsupportedEncodingException e) {
					logger.error("编码转换异常", e);
				} 
				data.append("&");
			}
			data.deleteCharAt(data.length() - 1);
		}
		return sendHttpGet(url, data.toString());
	}
	
    /**
     * 向指定URL发送GET方法的请求
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendHttpGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            HttpURLConnection conn = (HttpURLConnection) new URL(urlNameString).openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            conn.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送GET请求出现异常", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            	logger.error("流关闭失败", e2);
            }
        }
        return result;
    }
	
}

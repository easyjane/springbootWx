package com.test.wechat.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtils {
    public static String doHttpGet(String url) throws Exception{
        String result = "";
        URL urlGet = new URL(url);
        HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
        http.setRequestMethod("GET"); // 必须是get方式请求
        http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        http.setDoOutput(true);
        http.setDoInput(true);
        http.connect();
        InputStream is = http.getInputStream();
        int size = is.available();
        byte[] jsonBytes = new byte[size];
        is.read(jsonBytes);
        String message = new String(jsonBytes, "UTF-8");
        result = message;
        is.close();
        return result;
    }

    /**
     * get方法直接调用post方法
     * @param url 网络地址
     * @return 返回服务器请求回来的数据
     */
    public static String getHttpData(String url){
        return postHttpData(url, null);
    }

    /**
     * 设定post方法获取网络资源,如果参数为null,实际上设定为get方法
     * @param url 网络地址
     * @param param 请求参数键值对
     * @return 返回读取数据
     */
    @SuppressWarnings("rawtypes")
    public static <Key, Value> String postHttpData(String url,Map<Key, Value> param){

        // 创建一个HttpURLConnection对象，用于连接指定站定
        HttpURLConnection conn = null;
        try {
            // 创建一个URL对象，并绑定相应的站点
            URL u = new URL(url);
            // 打开于站点的连接
            conn = (HttpURLConnection) u.openConnection();
            // 创建一个缓冲区，用于存放请求回来的数据
            StringBuffer sb = null;
            // 如果请求参数不为空
            if(param != null){

                sb = new StringBuffer();
                // 默认为false,post方法需要写入参数,设定true
                conn.setDoOutput(true);
                // 设定post方法,默认get
                conn.setRequestMethod("POST");
                // 获得输出流
                OutputStream out = conn.getOutputStream();
                // 对输出流封装成高级输出流
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
                // 将参数封装成键值对的形式
                for(Map.Entry s: param.entrySet()){

                    sb.append(s.getKey()).append("=").append(s.getValue()).append("&");
                }
                //将参数通过输出流写入
                writer.write(sb.deleteCharAt(sb.toString().length()-1).toString());
                writer.close();//一定要关闭,不然可能出现参数不全的错误
                sb=null;
            }
            conn.connect();//建立连接
            sb=new StringBuffer();
            //获取连接状态码
            int recode = conn.getResponseCode();
            BufferedReader reader=null;
            if(recode == 200){
                //Returns an input stream that reads from this open connection
                //从连接中获取输入流
                InputStream in = conn.getInputStream();
                //对输入流进行封装
                reader=new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String str=null;
                sb = new StringBuffer();
                //从输入流中读取数据
                while((str = reader.readLine())!=null){
                    sb.append(str).append(System.getProperty("line.separator"));
                }
                //关闭输入流
                reader.close();
                if (sb.toString().length() == 0) {
                    return null;
                }
                return sb.toString().substring(0,
                        sb.toString().length() - System.getProperty("line.separator").length());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally{
            if(conn!=null)//关闭连接
                conn.disconnect();
        }
        return null;
    }
}

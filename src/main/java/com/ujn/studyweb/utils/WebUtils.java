package com.ujn.studyweb.utils;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;


import javax.servlet.ServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WebUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebUtils.class);



    /**
     * 将 Base64 字符串解码，再解码URL参数, 默认使用 UTF-8
     * @param source 原始 Base64 字符串
     * @return decoded string
     *
     * aHR0cHM6Ly9maWxlLmtla2luZy5jbi9kZW1vL%2BS4reaWhy5wcHR4 -> https://file.keking.cn/demo/%E4%B8%AD%E6%96%87.pptx -> https://file.keking.cn/demo/中文.pptx
     */
    public static String decodeUrl(String source) {
        String url = decodeBase64String(source, StandardCharsets.UTF_8);
        if (! StringUtils.isNotBlank(url)){
            return null;
        }

        return url;
    }

    /**
     * 将 Base64 字符串使用指定字符集解码
     * @param source 原始 Base64 字符串
     * @param charsets 字符集
     * @return decoded string
     */
    public static String decodeBase64String(String source, Charset charsets) {
        /*
         * url 传入的参数里加号会被替换成空格，导致解析出错，这里需要把空格替换回加号
         * 有些 Base64 实现可能每 76 个字符插入换行符，也一并去掉
         * https://github.com/kekingcn/kkFileView/pull/340
         */
        try {
            return new String(Base64Utils.decodeFromString(source.replaceAll(" ", "+").replaceAll("\n", "")), charsets);
        } catch (Exception e) {
            LOGGER.error("url解码异常，可能是接入方法错误或者未使用BASE64", e);
            return null;
        }
    }


}

package com.chinobot.common.message.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * @program: chinobot
 * @description:
 * @author: shizt
 * @create: 2019-12-12 10:25
 */
public class FreeMarkerUtils {
    /**
     * 解析字符串模板,通用方法
     *
     * @param template
     *            字符串模板
     * @param model;
     *            数据
     * @param configuration
     *            配置
     * @return 解析后内容
     */
    public static String process(String template, Map<String, ?> model, Configuration configuration)
            throws IOException, TemplateException {
        if (template == null) {
            return null;
        }
        if (configuration == null) {
            configuration = new Configuration();
        }
        StringWriter out = new StringWriter();
        new Template("template", new StringReader(template), configuration).process(model, out);
        return out.toString();
    }
}

package com.vnetoo.examination;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.vnetoo.common.SystemGlobals;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 页面静态化类
 */
public class FreeMarketUtil {
	private static final Logger log = Logger.getLogger(FreeMarketUtil.class);
	
	private static Configuration config = new Configuration();
	private static ServletContext servletContext;
	
	private Locale locale;
	
	private String charset;
	
	public FreeMarketUtil(){
		locale = Locale.getDefault();
		charset = "UTF-8";
	}

	public FreeMarketUtil(Locale locale, String charset){
		this.locale = locale;
		this.charset = charset;
	}
	/**
	 * 页面静态化处理
	 */
	public  void processTemplate(String templateName, Map<String, Object> root, Writer out) {
		try {
			ResourceBundle rbundle = ResourceBundle.getBundle("globalResources", locale);
			ResourceBundleModel resourceBundle = new ResourceBundleModel(rbundle, BeansWrapper.getDefaultInstance());
			root.put("resourceBundle", resourceBundle);
			Template template = config.getTemplate(templateName, charset);
			template.process(root, out);
			out.flush();
		} catch (IOException e) {
			log.error("IOException occur", e);
		} catch (TemplateException e) {
			log.error("TemplateException occur", e);
		} finally {
			try {
				if(out != null){
					out.close();
					out = null;
				}
			} catch (IOException e) {
				log.error("IOException occur when closed the output stream", e);
			}
		}
	}

	/**
	 * 试卷页面静态化
	 */
	public String processPaperTemplate(Map<String, Object> root, String fileName) {
		String pageUrl = "";
		try {
			String path = servletContext.getRealPath("/");
			String paperDir = SystemGlobals.getValue(ExamConstant.PAPER_DIR);
			File file = new File(path + File.separator + paperDir + File.separator + fileName + ".html");
			Writer write = new OutputStreamWriter(new FileOutputStream(file));
			processTemplate("examPaper/examination.htm", root, write);
			pageUrl = paperDir.replace("\\", "/") + "/" + fileName + ".html"; 
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException", e);
		}
		return pageUrl;
	}

	/**
	 * 初始化模板配置
	 */
	public static void initConfig(ServletContext servletContext, String templateDir) {
		FreeMarketUtil.servletContext = servletContext;
		config.setDefaultEncoding("utf-8");
		config.setServletContextForTemplateLoading(servletContext, templateDir);
		config.setObjectWrapper(new DefaultObjectWrapper());
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
}

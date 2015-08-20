package otc.healthcare.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import otc.healthcare.util.HealthcareConfiguration;
import otc.healthcare.util.SpringWiredBean;

@Component
public class WordService implements IService{

	//freemarker configuration
	private Configuration configuration;
	
	@SuppressWarnings("deprecation")
	public Configuration getConfiguration(){
		if(this.configuration == null){
			configuration = new Configuration();
			configuration.setDefaultEncoding("UTF-8");
		}
		return configuration;
	}
	
	public void createWordFromFtl(HttpServletRequest req, HttpServletResponse resp){
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		getData(req , dataMap);
		
		File file = null;  
	    file = createDoc(dataMap, "test"); 
	    
	    /*  浏览器 --- 文件下载
	    InputStream fin = null;  
	    ServletOutputStream out = null;  
        try {  
            // 调用createDoc方法生成Word文档  
            file = createDoc(dataMap, "test");  
            fin = new FileInputStream(file);  
              
            resp.setCharacterEncoding("utf-8");  
            resp.setContentType("application/msword");  
            // 设置浏览器以下载的方式处理该文件默认名为resume.doc  
            resp.addHeader("Content-Disposition", "attachment;filename=resume.doc");  
              
            out = resp.getOutputStream();  
            byte[] buffer = new byte[512];  // 缓冲区  
            int bytesToRead = -1;  
            // 通过循环将读入的Word文件的内容输出到浏览器中  
            while((bytesToRead = fin.read(buffer)) != -1) {  
                out.write(buffer, 0, bytesToRead);  
            }  
        } catch (IOException e) {
			e.printStackTrace();
		} finally {  
				try {
					  if(fin != null) fin.close();
					  if(out != null) out.close();  
			           if(file != null) file.exists(); // 删除临时文件 
				} catch (IOException e) {
					e.printStackTrace();
				}  
        }  
        */
            
        System.out.println("加载word模版成功，生成word文件！");
	}

	
	 public File createDoc(Map<?, ?> dataMap, String typeName) {  
	        String name = "E:/outFile" +  (int)(Math.random() * 100000) + ".doc";  
	        File f = new File(name);  
	        getConfiguration();
	        Template t = getTemplates(typeName);  
	        try {  
	            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
	        	Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "utf-8"));
//	            Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");  
	            t.process(dataMap, out);  
	            out.flush();
	            out.close();  
	        } catch (Exception ex) {  
	            ex.printStackTrace();  
	            throw new RuntimeException(ex);  
	        }  
	        return f;  
	   }  
	
	 
	/*
	 * 获取word——ftl模版
	 */
	private Template getTemplates(String TemplateName) {
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext(); 
	    ServletContext servletContext = webApplicationContext.getServletContext(); 
		configuration.setServletContextForTemplateLoading(servletContext, "/resources/doc_ftl");
//		configuration.setDirectoryForTemplateLoading(new File("C:/Users/Andy/Documents/workspace-sts-3.7.0.RELEASE/java2word"));
		Template t = null;
		try {
			t = configuration.getTemplate(TemplateName+".ftl");
		} catch (TemplateNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedTemplateNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	private void getData(HttpServletRequest req, Map<String, Object> dataMap) {
		String hc_username = req.getParameter("username");
		String hc_email = req.getParameter("email");
		System.out.println("test : " + hc_username);
		dataMap.put("hc_username", hc_username);
		dataMap.put("hc_usertel", hc_email);
//		dataMap.put("month", "2");
//		dataMap.put("day", "13");
//		dataMap.put("auditor", "唐鑫");
//		dataMap.put("phone", "13020265912");
//		dataMap.put("weave", "占文涛");
//		dataMap.put("number", 1);
//		dataMap.put("content", "内容"+2);
		
//		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//		for (int i = 0; i < 10; i++) {
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("number", i);
//			map.put("content", "内容"+i);
//			list.add(map);
//		}
//		dataMap.put("list", list);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int checkStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

}

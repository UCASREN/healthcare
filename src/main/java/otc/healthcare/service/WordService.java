package otc.healthcare.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.mchange.v2.async.StrandedTaskReporting;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import otc.healthcare.util.HealthcareConfiguration;
import otc.healthcare.util.MSWordManager;
import otc.healthcare.util.docConvertUtil;

/**
 * @author Andy
 *
 */

@Component
public class WordService implements IService{

	//freemarker configuration
	private Configuration configuration;
	@Autowired
	private HealthcareConfiguration hcConfiguration;
	
	@Autowired
	OracleService oracleService;
	public OracleService getOracleService() {
		return oracleService;
	}

	public void setOracleService(OracleService oracleService) {
		this.oracleService = oracleService;
	}
/*	private HealthcareConfiguration getHealthcareConfiguration() {
		if (hcConfiguration == null)
			hcConfiguration = SpringWiredBean.getInstance().getBeanByClass(HealthcareConfiguration.class);
		return hcConfiguration;
	}*/
	
	@SuppressWarnings("deprecation")
	public Configuration getConfiguration(){
		if(this.configuration == null){
			configuration = new Configuration();
			configuration.setDefaultEncoding("UTF-8");
		}
		return configuration;
	}
	
	public void createWordFromFtl(HttpServletRequest req, HttpServletResponse resp, String f_path_name){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		getData(req , dataMap);
	    createDoc(dataMap, "dataApply",f_path_name); 
        System.out.println("加载word模版成功，生成word文件！");
	}

	
	/**
	 * DOC  xml--->doc
	 * 将freemarker生成的xml格式文档，转换为doc格式
	 * @param servletContext2 
	 * @param fileName 
	 */
	public void changeDocFormat(File openFile, ServletContext servletContext) {
		String docPath = servletContext.getRealPath("/resources/swf");
		String saveFileName = docPath+"/"+ openFile.getName();
        MSWordManager ms = new MSWordManager(false);     
        ms.openDocument(openFile.getAbsolutePath());
        ms.save(saveFileName);     
        ms.close();     
        ms.closeDocument();    
        System.out.println("doc 格式转换成功!");
	}
	
	/**
	 * 
	 * @param dataMap 数据
	 * @param typeName 模版类型
	 * @param f_path_name 文件名称
	 * @return
	 */
	 public File createDoc(Map<?, ?> dataMap, String typeName, String f_path_name) {  
	        
	    	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext(); 
		    ServletContext servletContext = webApplicationContext.getServletContext(); 
	        getConfiguration();
	        Template t = getTemplates(servletContext,typeName);  
	        File f = new File(f_path_name);
	        try {  
	        	
	            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
	        	Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "utf-8"));
	        	
	            t.process(dataMap, out);  
	            out.flush();
	            out.close();  
	        } catch (Exception ex) {  
	            ex.printStackTrace();  
	            throw new RuntimeException(ex);  
	        } finally {
	        	changeDocFormat(f,servletContext);
			} 
	        return f;  
	   }  
	
	 
	/*
	 * 获取word——ftl模版
	 */
	private Template getTemplates(ServletContext servletContext, String TemplateName) {
		
		configuration.setServletContextForTemplateLoading(servletContext, "resources");
		Template t = null;
		try {
			t = configuration.getTemplate("/hc_docftl/"+TemplateName+".ftl");
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
		
		String hc_userName = req.getParameter("userName");
		String hc_userDepartment  = req.getParameter("userDepartment");
		String hc_userAddress = req.getParameter("userAddress");
		String hc_userTel = req.getParameter("userTel");
		String hc_userEmail = req.getParameter("userEmail");
		
		String hc_userDemandType = req.getParameter("userDemandType");
		String hc_userDemand = req.getParameter("userDemand");
		
		String hc_useFields = req.getParameter("allUseField");
		String hc_projectName = req.getParameter("projectName");
		String hc_projectChairman = req.getParameter("projectChairman");
		String hc_projectSource = req.getParameter("projectSource");
		String hc_projectUndertaking = req.getParameter("projectUndertaking");
		String hc_applyDate = req.getParameter("applyDate");
		String hc_projectRemarks = req.getParameter("projectRemarks");
		
		//user info(5)
		dataMap.put("hc_UserName", hc_userName);
		dataMap.put("hc_UserDepartment", hc_userDepartment);
		dataMap.put("hc_UserAddress", hc_userAddress);
		dataMap.put("hc_UserTel", hc_userTel);
		dataMap.put("hc_UserEmail",hc_userEmail);
		
		//data demand(2)
		dataMap.put("hc_UserDemand",hc_userDemand);
		
		//project info(5)
		dataMap.put("hc_ProName", hc_projectName);
		dataMap.put("hc_ProChair", hc_projectChairman);
		dataMap.put("hc_ProSource", hc_projectSource);
		dataMap.put("hc_ProUndertaking", hc_projectUndertaking);
		dataMap.put("hc_ProRemarks", hc_projectRemarks);
		
	}
	
	/*
	 * word在线预览
	 */
	public void docConvert(HttpServletRequest req,String name) {
		String swftoolPath = hcConfiguration.getProperty(HealthcareConfiguration.SWFTOOLS_PATH);
	    ServletContext servletContext = req.getServletContext(); 
		String swfPath = servletContext.getRealPath("/resources/swf");
		String docName = swfPath +"\\" + name;
		File tmpFile = new File(docName);
		
		if(!tmpFile.exists()){
			String hc_docPath = hcConfiguration.getProperty(HealthcareConfiguration.HC_DOCPATH);
			File openFile = new File(hc_docPath + "\\" + name);
			changeDocFormat( openFile,  servletContext);
		}
		//开始转换
		docConvertUtil docUtil = new docConvertUtil(docName,swfPath);
		String swfFilePath = docUtil.conver(swftoolPath);
		
		if(!swfFilePath.equals("")){
			System.out.println("****swf生成操作成功，可以完成在线预览****");
			String swfpath = "../resources/swf"+swfFilePath.substring(swfFilePath.lastIndexOf("/"));
			req.getSession().setAttribute("swfFilePath", swfpath);
		}
		else
			System.out.println("****swf生成操作失败****");
	}	
	
	
	/*
	 * 删除word文档
	 */
	public boolean deleteDoc(String[] applydataidList) {
		boolean swf_flag;
		boolean open_flag;
		for(String applydataid : applydataidList){
			String doc_name = oracleService.getDocByApplyDataID(applydataid).getDocName();
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext(); 
		    ServletContext servletContext = webApplicationContext.getServletContext(); 
			String swfPath = servletContext.getRealPath("/resources/swf");
			File swfFile = new File(swfPath + "\\" + doc_name);
			File swfFile1 = new File(swfPath + "\\" + doc_name);
			if(swfFile.exists()){
				swfFile.delete();
				swfFile1.delete();
				swf_flag = true;
			}else{
				swf_flag = false;
			}
			
			String hc_docPath = hcConfiguration.getProperty(HealthcareConfiguration.HC_DOCPATH);
			File openFile = new File(hc_docPath + "\\" +  doc_name);
			if(openFile.exists()){
				openFile.delete();
				open_flag = true;
			}else{
				open_flag = false;
			}
			
			if(!open_flag)
				System.out.println("hc_doc not exists");
			if(!swf_flag)
				System.out.println("swf_doc not exists");
			if(open_flag && swf_flag)
				System.out.println("delete doc<2处doc> success ---> " + applydataid);
		}
		return true;
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

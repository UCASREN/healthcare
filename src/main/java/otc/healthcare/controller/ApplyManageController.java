package otc.healthcare.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import otc.healthcare.pojo.HcApplydata;
import otc.healthcare.service.MySQLServiceApply;

@Controller
@RequestMapping("/applymanager")
public class ApplyManageController {

	@Autowired
	private MySQLServiceApply mySQLServiceApply;
	
	@RequestMapping(value = "/downLoadApplyData", method = RequestMethod.GET)
	@ResponseBody
	public void handleApplyDataDownLoad(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "ApplyID", required = true) String ApplyID)  {
		
		System.out.println("开始下载  ------- "+ApplyID);
		HcApplydata hcApplydata = this.mySQLServiceApply.getDataDocByApplyDataID(ApplyID);
		String filePath = hcApplydata.getApplyDataDir();
		String[] tmp = filePath.split("\\\\");
		String fileName = tmp[tmp.length-1];
		
		 BufferedInputStream bis = null;
		 BufferedOutputStream bos = null;
		 
		 try {
				bis = new BufferedInputStream(new FileInputStream(filePath));
				bos = new BufferedOutputStream(response.getOutputStream());
				
				 long fileLength = new File(filePath).length();
				 
				 response.setCharacterEncoding("UTF-8");
				 response.setContentType("multipart/form-data");
		    
			    //解决各浏览器的中文乱码问题
			    String userAgent = request.getHeader("User-Agent");
			    byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes():fileName.getBytes("UTF-8"); // fileName.getBytes("UTF-8")处理safari的乱码问题
			    fileName = new String(bytes, "ISO-8859-1"); // 各浏览器基本都支持ISO编码
			    response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
			    response.setHeader("Content-Length", String.valueOf(fileLength));
			    
			    byte[] buff = new byte[2048];
			    int bytesRead;
			    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			        bos.write(buff, 0, bytesRead);
			    }
			    bis.close();
			    bos.close();
				System.out.println("完成下载  ------- "+ApplyID);
			} catch (IOException e) {
				e.printStackTrace();
			}
		 
	}
	
	@RequestMapping(value = "/applydataupload", method = RequestMethod.POST)
	@ResponseBody
	public String handleApplyDataUpload(HttpServletRequest request) {
		
		String applyid = request.getParameter("upload_applyid");
		
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		for (int i = 0; i < files.size(); ++i) {
			MultipartFile file = files.get(i);
			String name = file.getName();
			
			if (!file.isEmpty()) {
				User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				System.out.println(user.getAuthorities());
				System.out.println("用户：" + user);
				System.out.println("文件长度: " + file.getSize());
				System.out.println("文件类型: " + file.getContentType());
				System.out.println("文件名称: " + file.getName());
				System.out.println("文件原名: " + file.getOriginalFilename());
				System.out.println("========================================");
				String realPath = request.getSession().getServletContext().getRealPath(File.separator+"WEB-INF"+File.separator+"ApplyDataUpload");
				File exitfile = new File(realPath);
				// 如果文件夹不存在则创建
				if (!exitfile.exists() && !exitfile.isDirectory()) {
					System.out.println("目录 "+realPath+" 不存在");
					if(exitfile.mkdir())
						System.out.println("目录 "+realPath+" 创建完毕");
				} else {
					System.out.println("目录 "+realPath+" 存在");
				}
				
				// 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
				try {
					HcApplydata hcApplydata = this.mySQLServiceApply.getDataDocByApplyDataID(applyid);
					String hc_userName = hcApplydata.getHcUsername();
					String applyName =  hcApplydata.getName();
					String hc_projectName = hcApplydata.getProName();

					File upload = new File(realPath, applyName+"_"+file.getOriginalFilename());
					FileUtils.copyInputStreamToFile(file.getInputStream(), upload);
					
					hcApplydata.setFlagApplydata("4");
					hcApplydata.setApplyDataDir(upload.getAbsolutePath());
					this.mySQLServiceApply.getHcApplydataDao().attachDirty(hcApplydata);	
					
				} catch (IOException e) {
					e.printStackTrace();
					return "You failed to upload " + name + " because internal error.";
				}
			} else {
				return "You failed to upload " + name + " because the file was empty.";
			}
		}
		
		return "upload successful";
	}


}
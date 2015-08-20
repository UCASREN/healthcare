package otc.healthcare.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import otc.healthcare.service.WordService;

@Controller
@RequestMapping("/apply")
public class ApplyController {

	@Autowired
	WordService WordService;
	
	@RequestMapping(value = "/applydata", method = RequestMethod.GET)
	public String getDataResource() {
		return "apply";
	}
	
	@RequestMapping(value = "/createdataword", method = RequestMethod.POST)
	public String createDataApplyWordFromFtl(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req. setCharacterEncoding("UTF-8");
			this.WordService.createWordFromFtl(req, resp);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	public WordService getWordService() {
		return WordService;
	}

	public void setWordService(WordService wordService) {
		WordService = wordService;
	}
	
}

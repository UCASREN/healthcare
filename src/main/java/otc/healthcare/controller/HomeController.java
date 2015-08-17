package otc.healthcare.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import otc.healthcare.util.SpringWiredBean;
import otc.healthcare.util.hcConfiguration;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private hcConfiguration hcConfiguration;
	private hcConfiguration gethcConfiguration() {
		if(hcConfiguration==null)
			hcConfiguration= SpringWiredBean.getInstance().getBeanByClass(hcConfiguration.class);
		return hcConfiguration;
	}
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		String test = this.gethcConfiguration().getProperty(hcConfiguration.DB_URL);
		System.out.println("njz_test : "+test);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			model.addAttribute("username", ((UserDetails) principal).getUsername());
		} else {
			model.addAttribute("username", principal);
		}
		model.addAttribute("message", "helloworld");
		return "home";
	}
	
}

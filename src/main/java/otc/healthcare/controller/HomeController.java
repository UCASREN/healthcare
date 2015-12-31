package otc.healthcare.controller;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import otc.healthcare.util.SpringWiredBean;
import otc.healthcare.pojo.DatabaseInfo;
import otc.healthcare.util.HealthcareConfiguration;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			model.addAttribute("username", ((UserDetails) principal).getUsername());
		} else {
			model.addAttribute("username", principal);
		}
		model.addAttribute("message", "helloworld");
		return "home";
	}
	// @RequestMapping(value = "/databasemanager", method = RequestMethod.GET)
	// public String home(Model model) {
	// DatabaseInfo databaseinfo=new DatabaseInfo();
	// model.addAttribute(databaseinfo);
	// return "databasemanager";
	// }

	// @RequestMapping(value = "/apply_status", method = RequestMethod.GET)
	// public String showDocWordOnline(HttpServletRequest req,
	// HttpServletResponse resp,
	// @RequestParam(value = "docid", required = false) String docid) {
	// resp.addHeader("x-frame-options","SAMEORIGIN");
	// return "apply_status";
	// }

}

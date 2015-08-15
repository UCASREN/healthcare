package otc.healthcare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xingkong
 */
@Controller
public class LoginController {
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
}
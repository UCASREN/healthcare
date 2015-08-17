package otc.healthcare.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Eric
 * 
 */
@Component
public class SpringWiredBean implements ApplicationContextAware {

	private SpringWiredBean() {
	}

	private static SpringWiredBean instance;
	
	
	static {

		instance = new SpringWiredBean();
	}
	private static ApplicationContext ctx = null;

	public <T> T getBeanByClass(Class<T> beanClass) {
		return ctx.getBean(beanClass);	
	}

	public static SpringWiredBean getInstance() {
		return instance;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		ctx = arg0;

	}
}



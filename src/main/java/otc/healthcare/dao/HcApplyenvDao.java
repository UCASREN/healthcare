package otc.healthcare.dao;
// default package
// Generated 2015-12-29 16:34:07 by Hibernate Tools 4.0.0

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import otc.healthcare.pojo.HcApplyenv;

/**
 * Home object for domain model class HcApplyenv.
 * @see .HcApplyenv
 * @author Hibernate Tools
 */

@Transactional
public class HcApplyenvDao {

	private static final Log log = LogFactory.getLog(HcApplyenvDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(HcApplyenv transientInstance) {
		log.debug("persisting HcApplyenv instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(HcApplyenv instance) {
		log.debug("attaching dirty HcApplyenv instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(HcApplyenv instance) {
		log.debug("attaching clean HcApplyenv instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(HcApplyenv persistentInstance) {
		log.debug("deleting HcApplyenv instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public HcApplyenv merge(HcApplyenv detachedInstance) {
		log.debug("merging HcApplyenv instance");
		try {
			HcApplyenv result = (HcApplyenv) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	
	//通过序列得到---不用这个
	public HcApplyenv findById(long id) {
		log.debug("getting HcApplyenv instance with id: " + id);
		try {
			HcApplyenv instance = (HcApplyenv) sessionFactory.getCurrentSession().get("HcApplyenv", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(HcApplyenv instance) {
		log.debug("finding HcApplyenv instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("HcApplyenv").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	
	//通过属性值得到--这个
	public HcApplyenv findByApplyID(Object value) {
		String propertyName = "idApplydata";
		log.debug("finding HcApplyenv instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from HcApplyenv as model where model."
					+ propertyName + "= ?";
			Query queryObject = sessionFactory.getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			List<HcApplyenv> rs =  queryObject.list();
			return rs.get(0);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	
	//通过属性值得到
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding HcApplyenv instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from HcApplyenv as model where model."
					+ propertyName + "= ?";
			Query queryObject = sessionFactory.getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	
	//获得整个表内容
	public List findAll() {
		log.debug("finding all HcApplyenv instance");
		try {
			String queryString = "from HcApplyenv as model";
			Query queryObject = sessionFactory.getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	
	//通过系统用户名得到对应申请内容---当前用户查看自己申请
	public List findByHcUserName(String hcUserName){
		List<HcApplyenv> rs = findByProperty("hcUsername", hcUserName);
		return rs;
	}
	
	public HcApplyenv findByDocName(String hcDocName){
		List<HcApplyenv> rs = findByProperty("docName", hcDocName);
		return rs.get(0);
	}
	
	
	//缺少status参数
	public void changeApplyStatus(long applyID, String status) {
		String propertyName = "flagApplydata";
		log.debug("update HcApplyenv instance with property: " + propertyName + ", id: " + applyID);
		try {
			String updateString = "update HcApplyenv as h set h.flagApplydata=? where h.idApplydata=?";
			Session session = sessionFactory.getCurrentSession();
			Query queryObject = session.createQuery(updateString);
			queryObject.setParameter(0, status);
			queryObject.setParameter(1, applyID);
			queryObject.executeUpdate();
//			session.getTransaction().commit();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public void setApplyFailReason(long applyID, String rejectReason) {
		String propertyName = "applyRejectReason";
		log.debug("update HcApplyenv instance with property: " + propertyName + ", id: " + applyID);
		try {
			String updateString = "update HcApplyenv as h set h.applyRejectReason=? where h.idApplydata=?";
			Session session = sessionFactory.getCurrentSession();
			Query queryObject = session.createQuery(updateString);
			queryObject.setParameter(0, rejectReason);
			queryObject.setParameter(1, applyID);
			queryObject.executeUpdate();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	
	public void setApplyEnvUrl(long applyID, String EnvUrl) {
		String propertyName = "envUrl";
		log.debug("update HcApplyenv instance with property: " + propertyName + ", id: " + applyID);
		try {
			String updateString = "update HcApplyenv as h set h.envUrl=? where h.idApplydata=?";
			Session session = sessionFactory.getCurrentSession();
			Query queryObject = session.createQuery(updateString);
			queryObject.setParameter(0, EnvUrl);
			queryObject.setParameter(1, applyID);
			queryObject.executeUpdate();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}

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

import otc.healthcare.pojo.HcApplydata;

/**
 * Home object for domain model class HcApplydata.
 * 
 * @see .HcApplydata
 * @author Hibernate Tools
 */

@Transactional
public class HcApplydataDao {

	private static final Log log = LogFactory.getLog(HcApplydataDao.class);

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

	public void persist(HcApplydata transientInstance) {
		log.debug("persisting HcApplydata instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(HcApplydata instance) {
		log.debug("attaching dirty HcApplydata instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(HcApplydata instance) {
		log.debug("attaching clean HcApplydata instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(HcApplydata persistentInstance) {
		log.debug("deleting HcApplydata instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public HcApplydata merge(HcApplydata detachedInstance) {
		log.debug("merging HcApplydata instance");
		try {
			HcApplydata result = (HcApplydata) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	// 通过序列得到---不用这个
	public HcApplydata findById(long id) {
		log.debug("getting HcApplydata instance with id: " + id);
		try {
			HcApplydata instance = (HcApplydata) sessionFactory.getCurrentSession().get("HcApplydata", id);
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

	public List findByExample(HcApplydata instance) {
		log.debug("finding HcApplydata instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("HcApplydata")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	// 通过applyid得到--hql
	public HcApplydata findByApplyID(String value) {
		long value_long = Long.valueOf(value);
		String propertyName = "idApplydata";
		log.debug("finding HcApplydata instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from HcApplydata as model where model." + propertyName + "= ?";
			Query queryObject = sessionFactory.getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value_long);
			List<HcApplydata> rs = queryObject.list();
			return rs.get(0);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 通过属性值得到
	public List<HcApplydata> findByProperty(String propertyName, String value) {
		log.debug("finding HcApplydata instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from HcApplydata as model where model." + propertyName + "= ?";
			Query queryObject = sessionFactory.getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 获得整个表内容
	public List findAll() {
		log.debug("finding all HcApplydata instance");
		try {
			String queryString = "from HcApplydata as model";
			Query queryObject = sessionFactory.getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 通过系统用户名得到对应申请内容---当前用户查看自己申请
	public List findByHcUserName(String hcUserName) {
		List<HcApplydata> rs = findByProperty("hcUsername", hcUserName);
		return rs;
	}

	public HcApplydata findByDocName(String hcDocName) {
		List<HcApplydata> rs = findByProperty("docName", hcDocName);
		return rs.get(0);
	}

	// 缺少status参数
	public void changeApplyStatus(long applyID, String status) {
		String propertyName = "flagApplydata";
		log.debug("update HcApplydata instance with property: " + propertyName + ", id: " + applyID);
		try {
			String updateString = "update HcApplydata as h set h.flagApplydata=? where h.idApplydata=?";
			Session session = sessionFactory.getCurrentSession();
			Query queryObject = session.createQuery(updateString);
			queryObject.setParameter(0, status);
			queryObject.setParameter(1, applyID);
			queryObject.executeUpdate();
			// session.getTransaction().commit();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public void setApplyFailReason(long applyID, String rejectReason) {
		String propertyName = "applyRejectReason";
		log.debug("update HcApplydata instance with property: " + propertyName + ", id: " + applyID);
		try {
			String updateString = "update HcApplydata as h set h.applyRejectReason=? where h.idApplydata=?";
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
}

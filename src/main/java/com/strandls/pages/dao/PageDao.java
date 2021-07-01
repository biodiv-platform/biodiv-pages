/**
 * 
 */
package com.strandls.pages.dao;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.strandls.pages.pojo.Page;
import com.strandls.pages.util.AbstractDAO;

/**
 * 
 * @author vilay
 *
 */
public class PageDao extends AbstractDAO<Page, Long>{

	private final Logger logger = LoggerFactory.getLogger(PageDao.class);
	
	/**
	 * @param sessionFactory
	 */
	@Inject
	protected PageDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Page findById(Long id) {
		Session session =sessionFactory.openSession();
		Page entity = null;
		try {
			entity = session.get(Page.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return entity;
	}
	
	public List<Page> getByUserGroupAndLanguage(Long userGroupId, Long languageId, Boolean sticky) {
		String queryStr = ""
				+ "from " + daoType.getSimpleName() + " t "
						+ " where ((t.userGroupId is null and :userGroupId is null) or t.userGroupId = :userGroupId) and "
						+ "t.languageId = :languageId and is_deleted = false and "
						+ ( sticky.booleanValue() ? "sticky = true " : "sticky = false " );
		
		Session session = sessionFactory.openSession();
		Query<Page> query = session.createQuery(queryStr, Page.class);
		query.setParameter("userGroupId", userGroupId);
		query.setParameter("languageId", languageId);
		
		List<Page> resultList;
		resultList = query.getResultList();
		
		session.close();
		return resultList;
	}

}

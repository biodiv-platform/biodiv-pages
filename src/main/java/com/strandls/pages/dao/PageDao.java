/**
 * 
 */
package com.strandls.pages.dao;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.strandls.pages.pojo.Page;
import com.strandls.pages.util.AbstractDAO;
import com.strandls.userGroup.pojo.UserGroupSpeciesGroup;

/**
 * 
 * @author vilay
 *
 */
public class PageDao extends AbstractDAO<Page, Long> {

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
		Session session = sessionFactory.openSession();
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
		String queryStr = "" + "from Page t "
				+ " where ((t.userGroupId is null and :userGroupId is null) or t.userGroupId = :userGroupId) and "
				+ "t.languageId = :languageId and is_deleted = false and sticky = :sticky";

		Session session = sessionFactory.openSession();
		Query<Page> query = session.createQuery(queryStr, Page.class);
		query.setParameter("userGroupId", userGroupId);
		query.setParameter("languageId", languageId);
		query.setParameter("sticky", sticky);

		List<Page> resultList;
		resultList = query.getResultList();

		session.close();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public void deletePagesByUgId(Long userGroupId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		String qry = "update page set is_deleted= true where user_group_id= :id";
 		try {
			transaction = session.beginTransaction();
			Query<Page> query = session.createNativeQuery(qry);
			query.setParameter("id", userGroupId);
			query.executeUpdate();
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error(e.getMessage());
		} finally {
			session.close();
		}

	}

}

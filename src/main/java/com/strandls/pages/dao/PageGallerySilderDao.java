package com.strandls.pages.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.strandls.pages.pojo.PageGallerySlider;
import com.strandls.pages.util.AbstractDAO;

public class PageGallerySilderDao extends AbstractDAO<PageGallerySlider, Long> {

	private final Logger logger = LoggerFactory.getLogger(PageGallerySilderDao.class);

	/**
	 * @param sessionFactory
	 */
	@Inject
	protected PageGallerySilderDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public PageGallerySlider findById(Long id) {
		Session session = sessionFactory.openSession();
		PageGallerySlider entity = null;
		try {
			entity = session.get(PageGallerySlider.class, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<PageGallerySlider> findByPageId(Long pgId) {
		String qry = "from PageGallerySlider where pageId = :pgId";
		Session session = sessionFactory.openSession();
		List<PageGallerySlider> result = new ArrayList<>();
		try {
			Query<PageGallerySlider> query = session.createQuery(qry);
			query.setParameter("pgId", pgId);
			result = query.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
		return result;

	}

}

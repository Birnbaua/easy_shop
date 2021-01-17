package com.birnbaua.easyshop.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class JpaService<T,S> {
	
	public long count() {
		return getRepository().count();
	}
	
	public long count(Example<T> example) {
		return getRepository().count(example);
	}
	
	public boolean exists(Example<T> example) {
		return getRepository().exists(example);
	}
	
	public boolean existsById(S id) {
		return getRepository().existsById(id);
	}
	
	public T save(T entity) {
		return getRepository().save(entity);
	}
	
	public List<T> saveAll(List<T> 	entities) {
		return getRepository().saveAll(entities);
	}
	
	public T saveAndFlush(T entity) {
		return getRepository().saveAndFlush(entity);
	}
	
	public List<T> findAll() {
		return getRepository().findAll();
	}
	
	public List<T> findAllById(List<S> ids) {
		return getRepository().findAllById(ids);
	}
	
	public List<T> findAll(Example<T> example) {
		return getRepository().findAll(example);
	}
	
	public Page<T> findAll(Example<T> example, Pageable pageable) {
		return getRepository().findAll(example, pageable);
	}
	
	public List<T> findAll(Example<T> example, Sort sort) {
		return getRepository().findAll(example, sort);
	}
	
	public T findById(S id) {
		return getRepository().findById(id).orElse(null);
	}
	
	public void delete(T entity) {
		getRepository().delete(entity);
	}
	
	public void deleteById(S id) {
		getRepository().deleteById(id);
	}
	
	public void deleteInBatchById(List<S> ids, int batchSize) {
		int i = 0;
		List<S> bufferIds;
		do {
			bufferIds = ids.subList(i*batchSize, (i+1)*batchSize);
			deleteInBatch(getRepository().findAllById(bufferIds));
			i++;
		}while(i*batchSize<ids.size());
	}
	
	public void deleteInBatchById(List<S> ids) {
		deleteInBatch(getRepository().findAllById(ids));
	}
	
//	/**
//	 * Warning, this methods deletes the entries of the given keys without loading them into the entity manager!
//	 * @param ids
//	 */
//	@Transactional
//	public void deleteById(List<S> ids) {
//		getRepository().flush();
//		getRepository().deleteByIds(ids);
//	}
	
	public void deleteAll() {
		getRepository().deleteAll();
	}
	
	public void deleteAllInBatch() {
		getRepository().deleteAllInBatch();
	}
	
	public void deleteInBatch(List<T> entities) {
		getRepository().deleteInBatch(entities);
	}
	
	public abstract JpaRepository<T,S> getRepository();
}

package jpabook.jpabook.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import jpabook.jpabook.domain.Member;
import jpabook.jpabook.domain.Order;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

	private final EntityManager em;
	
	public void save(Order order) {
		em.persist(order);
	}
	public Order findOne(Long id) {
		return em.find(Order.class, id );
	}
	
	
	/*
	 * JPA Criteria 
	 * 얘도 안쓰임 밑에 코드를 보면 이게 어떤 코드인지 알기가 쉽지 않음 
	 * 결론 유지보수가 어려움
	 */

	public List<Order> findAllByCriteria(OrderSearch orderSearch) {
		 CriteriaBuilder cb = em.getCriteriaBuilder();
		 CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		 Root<Order> o = cq.from(Order.class);
		 Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
		 
		 List<Predicate> criteria = new ArrayList<>();
		 //주문 상태 검색
		 if (orderSearch.getOrderStatus() != null) {
		 Predicate status = cb.equal(o.get("status"),orderSearch.getOrderStatus());
		 criteria.add(status);
		 }
		 
		 //회원 이름 검색
		 if (StringUtils.hasText(orderSearch.getMemberName())) {
		 Predicate name =
		 cb.like(m.<String>get("name"), "%" +orderSearch.getMemberName() + "%");
		 criteria.add(name);
		 }
		 
		 cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
		 TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대1000건
		 return query.getResultList();
		
	}
	
	
	
	//JPQL로 처리 실무에서 안쓰임
	public List< Order> findAll(OrderSearch orderSearch){
		//language=JPAQL
		 String jpql = "select o From Order o join o.member m";
		 boolean isFirstCondition = true;
		 
		 //주문 상태 검색
		 if (orderSearch.getOrderStatus() != null) {
			 if (isFirstCondition) {
			 jpql += " where";
			 isFirstCondition = false;
			 } else {
			 jpql += " and";
			 }
			 jpql += " o.status = :status";
		 }
		 
		 //회원 이름 검색
		 if (StringUtils.hasText(orderSearch.getMemberName())) {
			 if (isFirstCondition) {
			 jpql += " where";
			 isFirstCondition = false;
			 } else {
			 jpql += " and";
			 }
			 jpql += " m.name like :name";
			 }
			 TypedQuery<Order> query = em.createQuery(jpql, Order.class)
			 .setMaxResults(1000); //최대 1000건
			 if (orderSearch.getOrderStatus() != null) {
			 query = query.setParameter("status", orderSearch.getOrderStatus());
			 }
			 if (StringUtils.hasText(orderSearch.getMemberName())) {
			 query = query.setParameter("name", orderSearch.getMemberName());
			 }
			 return query.getResultList();
		}
	
	
}
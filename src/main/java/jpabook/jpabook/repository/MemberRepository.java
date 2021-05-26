package jpabook.jpabook.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import jpabook.jpabook.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
// repository 어노테이션 해주고
	
	// PersistenceContext 로 스프링이 엔티티 메니저를 주입을 해줌
	//@PersistenceContext
	//private EntityManager em;
	
	private final EntityManager em;
	
	
	public void save(Member member) {
		em.persist(member);
	}
	public Member findOne(Long id) {
		return em.find(Member.class, id);
	}
	// jpql 사용 여기서는 from의 대상이 엔티티(테이블)가 되어야함 
	public List<Member> findAll (){
		
		return em.createQuery("select m from Member m",Member.class)
		.getResultList();
	}
	
	public List<Member> findByName(String name ){
		return em.createQuery("select m from Member m where m.name =:name",Member.class)
				.setParameter("name", name)
				.getResultList();
	}
	
}

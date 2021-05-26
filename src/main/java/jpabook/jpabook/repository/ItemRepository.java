package jpabook.jpabook.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpabook.jpabook.domain.Item;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

	private final EntityManager em ;
	
	public void save(Item item) {
		if(item.getId() ==null) {
			em.persist(item);
		}else {
			 em.merge(item);
			//Item marge = em.merge(item);  
			 //값을 받을때는 이런식으로 받아야 함 marge에서 중요한 점은 만약 파라미터로 넘어 온 값이 변경됨 
			 //만약  null로 넘어 오면 값이 null 로 변경됨 그래서 marge 사용 보단 변경 감지를 사용해야함
		}
	}
	
	public Item  findOne(Long id) {
		return em.find(Item.class, id);
	}
	
	public List<Item> findAll(){
		return em.createQuery("select i from Item i",Item.class)
				.getResultList();
	}
	
}

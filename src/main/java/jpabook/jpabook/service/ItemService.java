package jpabook.jpabook.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpabook.domain.Item;
import jpabook.jpabook.domain.item.Book;
import jpabook.jpabook.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository ;
	
	@Transactional
	public void saveItem(Item item) {
		itemRepository.save(item);
	}
	
	
	public List<Item> findItems(){
		return itemRepository.findAll();
	}
	
	public Item findOne(Long id) {
		return itemRepository.findOne(id);
	}
	
	@Transactional
	public Item updateItem(Long itemId, Book param ) {
		Item findItem = itemRepository.findOne(itemId);
		
		// 여기서 set으로 셋팅되면서 JPA가 감지 하고 update 쿼리를 날려줌  
		//findItem.change(price,name,stockQuantity); 밑에 있는 set 으로 하는것 보다 이렇게 의미 있는 메서드를 만드는 것이 좀더 효과 적임
		findItem.setPrice(param.getPrice());
		findItem.setName(param.getName());
		findItem.setStockQuantity(param.getStockQuantity());
	
		// findItem 같은 경우 이미 변경 된 값이 저장되어 있기 때문에 변경 된 값을 리턴 받고 싶으면 findItem을 리턴 받으면됨
		return findItem; 
	
	}
	
	@Transactional
	public void updateItem(Long itemId, String name, int price, int stockQuantity) {
		// TODO Auto-generated method stub
		Item findItem = itemRepository.findOne(itemId);
		
		// 여기서 set으로 셋팅되면서 JPA가 감지 하고 update 쿼리를 날려줌  
		//findItem.change(price,name,stockQuantity); 밑에 있는 set 으로 하는것 보다 이렇게 의미 있는 메서드를 만드는 것이 좀더 효과 적임
		findItem.setPrice(price);
		findItem.setName(name);
		
		findItem.setStockQuantity(stockQuantity);
	
		// findItem 같은 경우 이미 변경 된 값이 저장되어 있기 때문에 변경 된 값을 리턴 받고 싶으면 findItem을 리턴 받으면됨
	    //	return findItem; 
	
	}
}

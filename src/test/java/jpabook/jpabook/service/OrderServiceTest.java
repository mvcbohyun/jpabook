package jpabook.jpabook.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpabook.domain.Address;
import jpabook.jpabook.domain.Item;
import jpabook.jpabook.domain.Member;
import jpabook.jpabook.domain.Order;
import jpabook.jpabook.domain.OrderStatus;
import jpabook.jpabook.domain.item.Book;
import jpabook.jpabook.exception.NotEnoughStockException;
import jpabook.jpabook.repository.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

	@PersistenceContext EntityManager em;
	
	@Autowired OrderService orderService;
	@Autowired OrderRepository orderRepository;

	@Test
	 public void 상품주문() throws Exception {
	//given
	Member member = createMember();
	
	Book book = createBook("spring boot",10000,100);
	
	int orderCount= 3;
	
	//when
	Long orderId = orderService.order(member.getId(),book.getId() , orderCount);
	
		
	//then
	Order getOrder =orderRepository.findOne(orderId);
	
	assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER,getOrder.getStatus());
	assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1,getOrder.getOrderItems().size());
	assertEquals("주문 가격은 가격*수량이다", 10000*orderCount,getOrder.getTotalprice());
	assertEquals("주문 수량만큼 재고가 줄어야 한다.", 97,book.getStockQuantity());
	
	}
	
	@Test(expected = NotEnoughStockException.class)
	public void 상품주문_재고수량초과() throws Exception {
	//given
		Member member = createMember();
		
		Item item = createBook("spring boot",10000,100);
		
		int ordercount =111;
	//when
		orderService.order(member.getId(), item.getId(), ordercount);
					
	//then 	
		fail("재고 수량 부족 예외가 발생해야 한다.");
	}
	
	 @Test
	 public void 주문취소() {
	//given
		Member member = createMember();
		Item item = createBook("시골 JPA", 10000, 10); //이름, 가격, 재고
		int orderCount = 2;

		Long orderId = orderService.order(member.getId(), item.getId(),orderCount);	
	//when
		orderService.cancelOrder(orderId);
				
	//then 
		Order getOrder = orderRepository.findOne(orderId);
		assertEquals("주문 취소시 상태는 CANCEL 이다.",OrderStatus.CANCEL,getOrder.getStatus());
		assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10,item.getStockQuantity());
	 }
	
	
	private Book createBook(String name ,int price ,int qty) {
		Book book =new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(qty);
		em.persist(book);
		return book;
	}
	private Member createMember() {
		Member member = new Member();
		member.setName("회원1");
		member.setAddress(new Address("서울", "마포구", "123-123"));
		em.persist(member);
		return member;
	}

		
}

package jpabook.jpabook.service;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpabook.domain.Delivery;
import jpabook.jpabook.domain.Item;
import jpabook.jpabook.domain.Member;
import jpabook.jpabook.domain.Order;
import jpabook.jpabook.domain.OrderItem;
import jpabook.jpabook.repository.ItemRepository;
import jpabook.jpabook.repository.MemberRepository;
import jpabook.jpabook.repository.OrderRepository;
import jpabook.jpabook.repository.OrderSearch;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;
	/*
	 * 주문
	 */
	@Transactional
	public Long order(Long memberId, Long itemId , int count) {
		// 엔티티 조회
		Member member = memberRepository.findOne(memberId);
		Item item =itemRepository.findOne(itemId);
		
		//배송정보 생성
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());
		
		//주문 상품 생성
		OrderItem orderItem= OrderItem.createOrderItem(item, item.getPrice(), count);
		
		//주문 생성
		Order order =Order.createOrder(member, delivery, orderItem);
		
		//주문 저장 mappedBy = "order", cascade = CascadeType.ALL (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
		// cascade 가 all 로 되어 있어서 따로 저장을 안해도 같이 저장됨 ! -- 생성도 같이되고 삭제도 같이 되기 때문에 진짜 둘정보가 딱딱 맞는경우 아니면 사용하지 말것
		orderRepository.save(order);
		
		
		return order.getId();
	}
	/*
	 * 주문 취소
	 */
	// 따로 쿼리를 작성 하지 않아도 jpa가  변경된 내역을 감지해서 알아서 update 쿼리를 날려줌
	@Transactional
	public void cancelOrder(Long orderId) {
		// orderid를 통해 order를 찾고  (주문 엔티티 조회) 
		Order order = orderRepository.findOne(orderId);
		// 취소 해주면 됨 
		order.cancel();
	}
	/*
	 * 검색
	 */
	public List<Order> findOrders(OrderSearch orderSearch){
		return orderRepository.findAll(orderSearch);
	}
	
}

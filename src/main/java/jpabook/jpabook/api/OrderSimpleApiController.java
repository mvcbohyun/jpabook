package jpabook.jpabook.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpabook.domain.Address;
import jpabook.jpabook.domain.Order;
import jpabook.jpabook.domain.OrderStatus;
import jpabook.jpabook.repository.OrderRepository;
import jpabook.jpabook.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

	private final OrderRepository orderRepository;
	
	@GetMapping("/api/v1/simple-orders")
	public List<Order> orderV1(){
		List<Order> all = orderRepository.findAll(new OrderSearch());
		for(Order order : all) {
			order.getMember().getName();// Lazy 강제 초기화 
			order.getDelivery().getAddress(); // Lazy 강제 초기화 
		}
		return all;
	}
	
	@GetMapping("/api/v2/simple-orders")
	public List<SimpleOrderDto> ordersV2() {
		
	 List<Order> orders = orderRepository.findAll(new OrderSearch());
	 
	 List<SimpleOrderDto> result = orders.stream()
	 .map(o -> new SimpleOrderDto(o))
	 .collect(Collectors.toList());
	 return result;
	}
	
	/**
	 * V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
	 * - fetch join으로 쿼리 1번 호출
	 * 참고: fetch join에 대한 자세한 내용은 JPA 기본편 참고(정말 중요함)
	 */
	@GetMapping("/api/v3/simple-orders")
	public List<SimpleOrderDto> ordersV3() {
		
		List<Order> orders = orderRepository.findAllWithMemberDelivery();
		
		List<SimpleOrderDto> result = orders.stream()
				.map(o -> new SimpleOrderDto(o))
				.collect(Collectors.toList());
		return result;
	}
	
	@Data
	static class SimpleOrderDto {
	 private Long orderId;
	 private String name;
	 private LocalDateTime orderDate; //주문시간
	 private OrderStatus orderStatus;
	 private Address address;
	 public SimpleOrderDto(Order order) {
	 orderId = order.getId();
	 name = order.getMember().getName(); // Lazy 강제 초기화 
	 orderDate = order.getOrderDate();
	 orderStatus = order.getStatus();
	 address = order.getDelivery().getAddress();// Lazy 강제 초기화 
	 }
	}
	
}

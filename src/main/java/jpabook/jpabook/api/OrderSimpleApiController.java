package jpabook.jpabook.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpabook.domain.Order;
import jpabook.jpabook.repository.OrderRepository;
import jpabook.jpabook.repository.OrderSearch;
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
	
	
}

package jpabook.jpabook.repository;

import java.time.LocalDateTime;

import jpabook.jpabook.domain.Address;
import jpabook.jpabook.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderSimpleQueryDto {
	private Long orderId;
	private String name;
	private LocalDateTime orderDate; //주문시간
	private OrderStatus orderStatus;
	private Address address;
	public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime
			orderDate, OrderStatus orderStatus, Address address) {
		this.orderId = orderId;
		this.name = name;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.address = address;
	}
}

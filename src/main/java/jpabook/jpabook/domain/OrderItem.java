package jpabook.jpabook.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
public class OrderItem {

	@Id @GeneratedValue
	 @Column(name = "order_item_id")
	 private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	 private Item item; //주문 상품
	
	@ManyToOne(fetch = FetchType.LAZY)
	
	 @JoinColumn(name = "order_id")
	 private Order order; //주문
	 
	 private int orderPrice; //주문 가격
	 private int count; //주문 수량
	
	// OrderItem orderitem =enw OrderItem(); 으로 생성을 막기 위해서 사용 여기는 생성 메서드를 통해 생성해야함 
	//@NoArgsConstructor(access = AccessLevel.PROTECTED) 이거랑 같은거임 및 코드는
	//	protected  OrderItem() {	
	//	}
	 
	//==비지니스 로직==//
	public void cancel() {
		getItem().addStock(count);
	}
	//==조회 로직==//
	/*
	 * 주문상품 전체 가격 조회 
	 */
	public int getTotalPrice() {
		// TODO Auto-generated method stub
		return getOrderPrice() * getCount();
	}
	//== 생성 메서드 == //
	public static OrderItem createOrderItem(Item item, int orderPrice , int count) {
		OrderItem orderItem =new OrderItem();
		orderItem.setItem(item);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(count);
		
		item.removeStock(count);
		return orderItem;
	}
}

package jpabook.jpabook.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jpabook.jpabook.domain.Item;
import jpabook.jpabook.domain.Member;
import jpabook.jpabook.domain.Order;
import jpabook.jpabook.repository.OrderSearch;
import jpabook.jpabook.service.ItemService;
import jpabook.jpabook.service.MemberService;
import jpabook.jpabook.service.OrderService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	private final MemberService memberService;
	private final ItemService itemService;

	@GetMapping("/order")
	public String createForm(Model model) {
		List<Member> members = memberService.findmebers();
		List<Item> items = itemService.findItems();
		
		model.addAttribute("members",members);
		model.addAttribute("items",items);
		
		return "order/orderForm";
		
	}
	
	@PostMapping("/order")
	public String order(@RequestParam("memberId") Long memberId,
						@RequestParam("itemId") Long itemId,
						@RequestParam("count") int count ) {
		orderService.order(memberId, itemId, count);
		
		return "redirect:/orders";
		
		
	}
	
	@GetMapping("/orders")
	public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch,Model model ) {
		List<Order> orders = orderService.findOrders(orderSearch);
		model.addAttribute("orders",orders);
		
		return "order/orderList";
	}
	
	@PostMapping(value = "/orders/{orderId}/cancel")
	 public String cancelOrder(@PathVariable("orderId") Long orderId) {
		System.out.println("111111111111");
		System.out.println(orderId);
		System.out.println("111111111111");
		
	 orderService.cancelOrder(orderId);
	 return "redirect:/orders";
	 }
}

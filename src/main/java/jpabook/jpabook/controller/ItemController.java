package jpabook.jpabook.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jpabook.jpabook.domain.Item;
import jpabook.jpabook.domain.item.Book;
import jpabook.jpabook.service.ItemService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;
	
	@GetMapping("/items/new")
	public String createForm(Model model) {
		model.addAttribute("form", new BookForm());
		
		return "items/createItemForm";
	}
	@PostMapping("/items/new")
	public String create(BookForm form) {
		Book book  = new Book();
		book.setName(form.getName());
		book.setPrice(form.getPrice());
		book.setStockQuantity(form.getStockQuantity());
		book.setAuthor(form.getAuthor());
		book.setIsbn(form.getIsbn());
		
		itemService.saveItem(book);
		return "redirect:/items";
	}
	@GetMapping("/items")
	public String list(Model model) {
		List<Item> items = itemService.findItems();
		model.addAttribute("items",items);
		return"items/itemList";
	}
	
	@GetMapping("/items/{itemId}/edit")
	public String updateItemForm(@PathVariable("itemId") Long itemid , Model model) {
		Book item = (Book) itemService.findOne(itemid);
		
		BookForm form = new BookForm();
		form.setId(itemid);
		form.setName(item.getName());
		form.setPrice(item.getPrice());
		form.setStockQuantity(item.getStockQuantity());
		form.setAuthor(item.getAuthor());
		form.setIsbn(item.getIsbn());
		model.addAttribute("form",form);
		
		return "items/updateItemForm";
	}
	
	@PostMapping("/items/{itemId}/edit")
	public String updateItem(@PathVariable Long itemId,@ModelAttribute("form") BookForm form) {
		
//		Book book = new Book();
//		 book.setId(form.getId());
//		 book.setName(form.getName());
//		 book.setPrice(form.getPrice());
//		 book.setStockQuantity(form.getStockQuantity());
//		 book.setAuthor(form.getAuthor());
//		 book.setIsbn(form.getIsbn());
		 //itemService.saveItem(book);
		 
		 //itemService.updateItem(itemId, book);
		 itemService.updateItem(itemId, form.getName(), form.getPrice(),form.getStockQuantity());
		 
		 return "redirect:/items";
	}
}

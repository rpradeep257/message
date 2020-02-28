package com.oodlefinance.pradeep.rajendran.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {
	
	private MessageService messageService;
	
	@Autowired
	public MessageController(MessageService messageService) {
		this.messageService =  messageService;
	}

	@GetMapping
	public List<Message> get() {
		return messageService.get();
	}
	
	@GetMapping("/{id}")
	public Message get(@PathVariable("id") Long id) {
		return messageService.get(id);
	}
	
	@PostMapping
	public Long create(@RequestBody Message message) {
		return messageService.create(message);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		messageService.delete(id);
		return new ResponseEntity<>("Message deleted", HttpStatus.OK);
	}
	
	
}

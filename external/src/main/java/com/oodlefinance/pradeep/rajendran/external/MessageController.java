package com.oodlefinance.pradeep.rajendran.external;

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

	private MessageServiceProxy messageServiceProxy;

	@Autowired
	public MessageController(MessageServiceProxy messageService) {
		this.messageServiceProxy = messageService;
	}

	@GetMapping
	public List<Message> get() {
		return messageServiceProxy.get();
	}

	@GetMapping("/{id}")
	public Message get(@PathVariable("id") Long id) {
		return messageServiceProxy.get(id);
	}

	@PostMapping
	public Long create(@RequestBody Message message) {
		return messageServiceProxy.create(message);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		messageServiceProxy.delete(id);
		return new ResponseEntity<>("Message deleted", HttpStatus.OK);
	}

}

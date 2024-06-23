package com.library.manage;

import com.library.manage.Domain.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class SystemApplicationTests {

	@Test
	void contextLoads() throws JsonProcessingException {
		Book book=new Book();
		book.setTitle("testBook");
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(book);
		System.out.println(json);
	}

}

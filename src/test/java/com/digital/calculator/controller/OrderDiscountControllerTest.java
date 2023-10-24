package com.digital.calculator.controller;

import org.junit.jupiter.api.Test;
 
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.digital.calculator.model.CustomerType;
import com.digital.calculator.model.OrderDiscount;
import com.digital.calculator.model.OrderRequest;
import com.digital.calculator.service.OrderDiscountService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(OrderDiscountController.class)
public class OrderDiscountControllerTest { 

	@MockBean
    private OrderDiscountService orderDiscountService;

	@Autowired
	private MockMvc mockMvc;

    @Test
    void testGetDiscount() throws Exception {
        OrderRequest request = new OrderRequest("123",15,100,CustomerType.DISSATISFIED);
       
        OrderDiscount discount = new OrderDiscount();
        discount.setDiscount(5);
        when(orderDiscountService.getDiscount(request)).thenReturn(discount);
		// this.mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
		// 		.andExpect(content().string(containsString("Hello, Mock")));
        this.mockMvc.perform(
            post("/get-discount")
             .content(asJsonString(request))
             .contentType(MediaType.APPLICATION_JSON)
	          .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
            //.andExpect(content().json(asJsonString(discount), false));
    }
    public static String asJsonString(final Object obj) {
    try {
        return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
}

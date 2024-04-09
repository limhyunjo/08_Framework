package com.kh.test.customer.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.test.customer.model.dto.Customer;
import com.kh.test.customer.model.mapper.CustomerMapper;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

	private final CustomerMapper mapper;

	@Override
	public int addCustomer(String customerName, String customerTel, String customerAddress) {
		
		Customer customer = new Customer();
		
		customer.setCustomerName(customerName);
		customer.setCustomerTel(customerTel);
		customer.setCustomerAddress(customerAddress);
		
		
		return mapper.addCustomer(customer);
	}
}

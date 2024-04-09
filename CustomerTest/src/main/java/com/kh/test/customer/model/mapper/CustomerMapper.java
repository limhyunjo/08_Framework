package com.kh.test.customer.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.kh.test.customer.model.dto.Customer;

@Mapper
public interface CustomerMapper {

	/**
	 * @param customer
	 * @return
	 */
	int addCustomer(Customer customer);

}

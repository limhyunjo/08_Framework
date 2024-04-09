package com.kh.test.customer.model.service;

public interface CustomerService {

	/** 고객 추가
	 * @param customerName
	 * @param customerTel
	 * @param customerAddress
	 * @return
	 */
	int addCustomer(String customerName, String customerTel, String customerAddress);

}

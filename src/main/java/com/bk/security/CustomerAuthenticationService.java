package com.bk.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bk.model.Customer;

@Service
public class CustomerAuthenticationService {
	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private CustomerDetailsService customerDetailsService;

	public Customer authenticateUser(String username, String password) {
		try {
			CustomerDetails customerDetails = (CustomerDetails) customerDetailsService
					.loadUserByUsername(username);
			Authentication request = new UsernamePasswordAuthenticationToken(
					username, password);
			Authentication result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
			return customerDetails.getCustomer();
		} catch (UsernameNotFoundException e) {
			throw new UsernameNotFoundException("Not found");
		}
	}
}

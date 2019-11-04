package com.nagarro.RouletteAPI.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.nagarro.RouletteAPI.dto.LoginServicesDTO;
import com.nagarro.RouletteAPI.services.ValidateUserServices;

@Path("validate")
@Controller
public class ValidateUserController {

	@Autowired
	ValidateUserServices validateUserServices;

	@GET
	@Path("validateUser/{customerID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public LoginServicesDTO validateUserAndGetUserDetails(@PathParam("customerID") String customerID) {

		LoginServicesDTO userInformation = validateUserServices.validateUserAndGetUserDetails(customerID);

		return userInformation;
	}

}

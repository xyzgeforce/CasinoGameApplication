package com.nagarro.adminPanel.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nagarro.adminPanel.model.CustomerDetails;
import com.nagarro.adminPanel.services.UserRegistrationServices;
import com.nagarro.adminPanel.utilities.AgeCalculator;
import com.nagarro.adminPanel.utilities.GenerateRandomID;

@Controller
public class UserRegistrationController {

	@Autowired
	UserRegistrationServices userRegistrationServices;

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public ModelAndView showRegisterPage(HttpServletRequest request, HttpServletResponse response, String errorMessage)
			throws Exception {

		ModelAndView modelAndView = new ModelAndView("RegisterUser");
		modelAndView.addObject("command", new CustomerDetails());
		modelAndView.addObject("statusMessage", errorMessage);
		return modelAndView;
	}

	@RequestMapping(value = "registerUser", method = RequestMethod.POST)
	public ModelAndView registerNewUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("customerDetails") CustomerDetails userInformation) throws Exception {

		String dateOfBirth = userInformation.getDateOfBirth();
		String emailID = userInformation.getEmailID();
		String contactNumber = userInformation.getContactNumber();
		String customerName = userInformation.getCustomerName();

		boolean is18Above = AgeCalculator.isValidAge(dateOfBirth);

		ModelAndView modelAndView = null;

		if (!is18Above) {
			String errorMessage = "Please provide a valid input and try again!";
//			modelAndView = new ModelAndView("RegisterUser");
//			modelAndView.addObject("statusMessage", errorMessage);
//			return modelAndView;

			modelAndView =  showRegisterPage(request, response, errorMessage);
			return modelAndView;

		}

		String customerID = GenerateRandomID.generateID(emailID, contactNumber, customerName);

		userInformation.setCustomerID(customerID);
		userInformation.setAccountBalance(500);

		String newCustomerID = null;

		newCustomerID = userRegistrationServices.registerUser(userInformation);

		if (newCustomerID == null) {
			String errorMessage = "Please provide a valid input and try again!";
			modelAndView = new ModelAndView("RegisterUser");
			modelAndView.addObject("statusMessage", errorMessage);
			return modelAndView;
		}

		modelAndView = new ModelAndView("index");
		modelAndView.addObject("successMessage", newCustomerID);
		return modelAndView;
	}
}

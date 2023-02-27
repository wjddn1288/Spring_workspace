package com.edu.teamproject.admin.controller;

import javax.annotation.processing.Generated;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@GetMapping("/")
	public String getMain() {
		return "/admin/index";
	}
	
	@GetMapping("/addpage")
	public String addPage() {
		return "/admin/addPage";
	}
}
 
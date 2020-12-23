/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ostrichemulators.shirotest.crosscutting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A class to let you know something worked
 * @author ryan
 */
@RestController
public class HelloController {

	@GetMapping( "/" )
	public String get() {
		return "reporting api GET";
	}

	@PutMapping( "/" )
	public String put() {
		return "reporting api PUT";
	}

	@PostMapping( "/" )
	public String post() {
		return "reporting api POST";
	}
}

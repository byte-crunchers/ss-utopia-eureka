package com.smoothstack.EurekaClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utopia")
public class SampleController {

	@Autowired
	private Environment environment;

	@RequestMapping("/test")
	public String readTest() {

		String port = environment.getProperty("local.server.port");

		return "<h1>Hello world! <br> Eureka client running on port = " + port + "</h1>";

	}

}

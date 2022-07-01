package com.lesonTask.practic;

import com.lesonTask.practic.service.PopulateDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PracticalApplication {

	final int PRODUCT_ENTITY_COUNT = 50;
	final int CART_ENTITY_COUNT = 20;

	public static void main(String[] args) {
		SpringApplication.run(PracticalApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(final PopulateDataService populateDataService){
		return args -> {
			populateDataService.setProductsData(PRODUCT_ENTITY_COUNT);
			populateDataService.setCartData(CART_ENTITY_COUNT);
			populateDataService.fillCartWithProduct();
			populateDataService.setUpRoles();
			populateDataService.setUpUsers();
		};
	}

}

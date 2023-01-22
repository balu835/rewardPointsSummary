package com.customer.rewardPoints;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages="com.customer.rewardPoints.repository")
@EnableTransactionManagement
@EntityScan(basePackages="com.customer.rewardPoints.entity")
public class RewardPointsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardPointsApplication.class, args);
	}

}

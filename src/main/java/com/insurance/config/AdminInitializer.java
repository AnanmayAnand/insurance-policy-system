package com.insurance.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.insurance.entity.Admin;
import com.insurance.repository.AdminRepository;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner initAdmin(AdminRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                Admin admin = new Admin();
                admin.setUsername("admin");
                admin.setPassword("admin123");
                repo.save(admin);
            }
        };
    }
}

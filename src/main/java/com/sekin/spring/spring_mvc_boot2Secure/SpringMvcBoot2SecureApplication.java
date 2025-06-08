package com.sekin.spring.spring_mvc_boot2Secure;

//import com.sekin.spring.spring_mvc_boot2Secure.dao.UserDao;
//import com.sekin.spring.spring_mvc_boot2Secure.dao.UserDaoImpl;
//import com.sekin.spring.spring_mvc_boot2Secure.model.Role;
//import com.sekin.spring.spring_mvc_boot2Secure.model.User;
//import com.sekin.spring.spring_mvc_boot2Secure.service.UserServiceImpl;
import com.sekin.spring.spring_mvc_boot2Secure.model.Role;
import com.sekin.spring.spring_mvc_boot2Secure.repository.RolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableJpaRepositories
public class SpringMvcBoot2SecureApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringMvcBoot2SecureApplication.class, args);





	}

}

package jpabook.jpabook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@SpringBootApplication
public class JpabookApplication {

	public static void main(String[] args) {
	
		SpringApplication.run(JpabookApplication.class, args);
	}
//	@Bean
//	Hibernate5Module hibernate5Module() {
//		return new Hibernate5Module();
//	}

	@Bean
	Hibernate5Module hibernate5Module() {
	 Hibernate5Module hibernate5Module = new Hibernate5Module();
	// hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING,true);
	 return hibernate5Module;
	}
}


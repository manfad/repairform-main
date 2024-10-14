package jans.repairform;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());

        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

		http
			.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/login", "/register","/newform","/qrcode/**","/repairform/save").permitAll() 
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/login")
                .defaultSuccessUrl("/", true)      
				.permitAll()
			)
			
			.logout((logout) -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.permitAll());

		return http.build();
	}
    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Disable password encoding by using NoOpPasswordEncoder
        return NoOpPasswordEncoder.getInstance();
    }
	
}

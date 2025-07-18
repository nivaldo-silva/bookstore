package io.github.nivaldosilva.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@OpenAPIDefinition(info = @Info(
        title = "Bookstore API", 
        version = "1.0.0", 
        description = "API para gerenciamento de livraria online. Permite gerenciar autores, livros, clientes e pedidos.", contact = @Contact(
                name = "Nivaldo Silva", 
                email = "nivaldosilva.contato@gmail.com", 
                url = "https://github.com/nivaldo-silva")))
public class BookstoreApplication {

        public static void main(String[] args) {
                SpringApplication.run(BookstoreApplication.class, args);
        }

}

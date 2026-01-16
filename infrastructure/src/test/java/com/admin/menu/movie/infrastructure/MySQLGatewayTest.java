package com.admin.menu.movie.infrastructure;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;
import java.util.Collection;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test")
/**
 * Utilizando o DataJpaTest a classe CategoryMySQLGateway não é reconhecida isso porque esta anotado com o @Service,
 * e utilizar o SpringBootTest não é interessante porque o mesmo carrega todas as dependencies do projeto a cada teste.
 * O SpringBootTest é interessante utilizar quando estamos realizando teste end-to-end.
 *
 * Por isso utilizei o ComponentScan desta forma, filtrando todas as classes que finaliza MySQLGateway
 * resolvendo este problema.
 */
@ComponentScan(includeFilters = {
		@ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*[MySQLGateway]")
})
@DataJpaTest
@ExtendWith(MySQLGatewayTest.CleanUpExtensions.class)
public @interface MySQLGatewayTest {
	class CleanUpExtensions implements BeforeEachCallback {

		@Override
		public void beforeEach(ExtensionContext context) throws Exception {
			final var repositories = SpringExtension
					.getApplicationContext(context)
					.getBeansOfType(CrudRepository.class)
					.values();

			cleanUp(repositories);
		}

		private void cleanUp(final Collection<CrudRepository> repositories) {
			repositories.forEach(CrudRepository::deleteAll);
		}
	}
}

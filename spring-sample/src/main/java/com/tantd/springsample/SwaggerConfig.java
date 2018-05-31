/**
 *
 */
package com.tantd.springsample;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * 
	 * http://localhost:9009/swagger-ui.html
	 * 
	 * @return
	 */

	@Bean
	public Docket infoDeviceControllerApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("api").apiInfo(apiInfo()).select().paths(headerPaths())
				.build().globalOperationParameters(Arrays.asList(new ParameterBuilder().name("X-AUTH-TOKEN")
						.description("Access Token").modelRef(new ModelRef("string")).parameterType("header").build()));

	}

	private Predicate<String> headerPaths() {
		return or(regex("/dooor/.*"), regex("/api/.*"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Universe API").description("Hi Wade!")
				.termsOfServiceUrl("Someone API terms of service")
				.contact(new Contact("whocare", "blah...blah", "moon@universe.com"))
				.license("Errr... Licensing Terms and Condition").licenseUrl("someone-care-about.it/api").version("1.0")
				.build();
	}

}
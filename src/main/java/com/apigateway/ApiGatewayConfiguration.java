package com.apigateway;

import java.util.function.Function;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		Function<PredicateSpec, Buildable<Route>> cinema_record_system = p -> (p.path("/movie/**")
				.uri("lb://cinema-record-system"));

		Function<PredicateSpec, Buildable<Route>> cinema_record_system_redirect = p -> (p
				.path("/movie-redirected-path/**")
				.filters(f -> f.rewritePath("/movie-redirected-path/(?<segment>.*)", "/movie/${segment}"))
				.uri("lb://cinema-record-system"));

		Function<PredicateSpec, Buildable<Route>> movie_review_system = p -> (p.path("/review/**")
				.uri("lb://movie-review-system"));

		Function<PredicateSpec, Buildable<Route>> ticket_booking_system = p -> (p.path("/ticket/**")
				.uri("lb://ticket-booking-system"));

		Function<PredicateSpec, Buildable<Route>> demoURI = p -> (p.path("/get")
				.filters(f -> f.addRequestHeader("Header1", "val1").addRequestParameter("param1", "val1"))
				.uri("http://httpbin.org:80"));

		return builder.routes().route(cinema_record_system).route(movie_review_system).route(ticket_booking_system)
				.route(cinema_record_system_redirect).route(demoURI).build();
	}

}

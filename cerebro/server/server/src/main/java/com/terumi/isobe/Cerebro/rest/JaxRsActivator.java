package com.terumi.isobe.Cerebro.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.terumi.isobe.Cerebro.model.UltrasoundImage;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

@ApplicationPath("/")
public class JaxRsActivator extends Application {

	public JaxRsActivator() {
		BeanConfig conf = new BeanConfig();
		conf.setTitle("WebServices API");
		conf.setDescription("API do projeto CEREBRO");
		conf.setVersion("1.0.0");
		conf.setHost("localhost:8080");
		conf.setBasePath("/");
		conf.setSchemes(new String[] { "http" });
		conf.setResourcePackage("com.terumi.isobe.cerebro");
		conf.setScan(true);
	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new HashSet<>();
		resources.add(ApiListingResource.class);
		resources.add(SwaggerSerializers.class);
		resources.add(FlightRest.class);
		resources.add(AccommodationRest.class);
		resources.add(ReconstructionRest.class);
		resources.add(PackageRest.class);
		return resources;
	}
}

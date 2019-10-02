package org.HomeCooking.rest;

import javax.ws.rs.core.Application;
import javax.annotation.security.PermitAll;
import javax.ws.rs.ApplicationPath;

@PermitAll
@ApplicationPath("/rest")
public class RestApplication extends Application {
}
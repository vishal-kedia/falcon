package com.kedialabs.application.filters;

import java.io.IOException;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import com.google.common.base.Strings;
import com.kedialabs.domain.UserSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AuthFilter
public class UserAuthenticationFilter implements ContainerRequestFilter, ContainerResponseFilter {

    public static final String X_SESSION_ID = "X-SESSION-ID";

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        log.trace("Clearing the tenant context information for the request {}", responseContext);
        UserContext.instance().clear();

    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        log.trace("Populating the tenant context information for the request {}", requestContext);

        final UserContext.Context context = UserContext.instance().getContext();
        final String sessionId = requestContext.getHeaderString(X_SESSION_ID);

        log.debug(X_SESSION_ID + " -  {}", sessionId);

        if (Strings.isNullOrEmpty(sessionId)) {
            log.error("X-SESSION-ID is mandatory.", sessionId);
            throw new NotAuthorizedException("X-SESSION-ID = " + sessionId + " is not authorized");
        }

        final UserSession userSession = UserSession.first("uuid", sessionId,"deleted",Boolean.FALSE);
        if (userSession == null || userSession.getUser().getDeleted() || userSession.getUser().getProject().getDeleted()
                || userSession.getUser().getProject().getContractor().getDeleted()) {
            throw new NotAuthorizedException("X-SESSION-ID = " + sessionId + " is not authorized");
        }
        context.setUser(userSession.getUser());
    }

}

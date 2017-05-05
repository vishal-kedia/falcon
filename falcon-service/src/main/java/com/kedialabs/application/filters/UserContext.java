package com.kedialabs.application.filters;

import com.kedialabs.domain.User;

public class UserContext {

    private ThreadLocal<UserContext.Context> context = new ThreadLocal<>();
    private static final UserContext INSTANCE = new UserContext();

    /**
     * private constructor for singleton class
     */
    private UserContext() {
    }

    /**
     * @return UserContext instance
     */
    public static UserContext instance() {
        return INSTANCE;
    }

    /**
     * Clear context
     */
    public void clear() {
        this.context.remove();
    }

    /**
     * @return the context
     */
    public UserContext.Context getContext() {
        UserContext.Context context = this.context.get();
        if (context == null) {
            context = new UserContext.Context();
            this.context.set(context);
        }
        return context;
    }

    /**
     * Context
     */
    public static class Context {
        private User user;

        /**
         * @return the tenant
         */
        public User getUser() {
            return user;
        }

        /**
         * @param tenant the tenant to set
         */
        public void setUser(User user) {
            this.user = user;
        }

    }
}

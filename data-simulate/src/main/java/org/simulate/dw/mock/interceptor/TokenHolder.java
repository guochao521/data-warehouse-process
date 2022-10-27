package org.simulate.dw.mock.interceptor;

/**
 *
 */
public final class TokenHolder {

    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    private TokenHolder() {
    }

    public static void setToken(String token) {
        holder.set(token);
    }

    public static String getToken() {
        return holder.get();
    }

    public static void removeToken() {
        holder.remove();
    }

}

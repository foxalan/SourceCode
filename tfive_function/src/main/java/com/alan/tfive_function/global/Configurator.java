package com.alan.tfive_function.global;

import android.app.Activity;
import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;


public final class Configurator {

    private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    private static final StringBuilder USER_AGENT_STRING_BUILDER = new StringBuilder();


    private Configurator() {
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
        LATTE_CONFIGS.put(ConfigKeys.HANDLER, HANDLER);
    }

    static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<Object, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure() {
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
    }

    public final Configurator withNativeApiHost(String host) {
        LATTE_CONFIGS.put(ConfigKeys.NATIVE_API_HOST, host);
        return this;
    }

    public final Configurator withWebApiHost(String host) {
        //只留下域名，否则无法同步cookie，不能带http://或末尾的/
        String hostName = host
                .replace("http://", "")
                .replace("https://", "");
        hostName = hostName.substring(0, hostName.lastIndexOf('/'));
        LATTE_CONFIGS.put(ConfigKeys.WEB_API_HOST, hostName);
        return this;
    }

    public final Configurator withLoaderDelayed(long delayed) {
        LATTE_CONFIGS.put(ConfigKeys.LOADER_DELAYED, delayed);
        return this;
    }


    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withWeChatAppId(String appId) {
        LATTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_ID, appId);
        return this;
    }

    public final Configurator withWeChatAppSecret(String appSecret) {
        LATTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_SECRET, appSecret);
        return this;
    }

    public final Configurator withActivity(Activity activity) {
        LATTE_CONFIGS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }

//    public Configurator withJavascriptInterface(@NonNull String name) {
//        LATTE_CONFIGS.put(ConfigKeys.JAVASCRIPT_INTERFACE, name);
//        return this;
//    }
//
//    public Configurator withWebEvent(@NonNull String name, @NonNull Event event) {
//        final EventManager manager = EventManager.getInstance();
//        manager.addEvent(name, event);
//        return this;
//    }

    //浏览器加载的HOST
    public Configurator withWebHost(String host) {
        LATTE_CONFIGS.put(ConfigKeys.WEB_HOST, host);
        return this;
    }

    public Configurator addWebUserAgent(String userAgent) {
        USER_AGENT_STRING_BUILDER.append(userAgent).append(" ");
        LATTE_CONFIGS.put(ConfigKeys.USER_AGENTS, USER_AGENT_STRING_BUILDER);
        return this;
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = LATTE_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) LATTE_CONFIGS.get(key);
    }
}

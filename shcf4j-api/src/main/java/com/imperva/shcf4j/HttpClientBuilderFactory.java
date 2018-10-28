package com.imperva.shcf4j;

import com.imperva.shcf4j.helpers.NOPServiceProvider;
import com.imperva.shcf4j.helpers.Util;
import com.imperva.shcf4j.spi.SHC4JServiceProvider;

import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * <b>HttpClientBuilderFactory</b>
 *
 * @author maxim.kirilov
 */
public class HttpClientBuilderFactory {


    static final AtomicReference<SHC4JServiceProvider> PROVIDER = new AtomicReference<>();
    static final AtomicReference<State> FACTORY_STATE = new AtomicReference<>(State.UNINITIALIZED);

    static final String UNSUCCESSFUL_INIT_MSG =
            "com.imperva.shcf4j.HttpClientBuilderFactory in failed state. Original exception was thrown EARLIER.";

    private enum State {
        UNINITIALIZED,
        FAILED_INITIALIZATION,
        SUCCESSFUL_INITIALIZATION,
        NOP_FALLBACK_INITIALIZATION
    }

    static {
        bind();
    }


    private HttpClientBuilderFactory() {
    }

    private static List<SHC4JServiceProvider> findServiceProviders() {
        return StreamSupport
                .stream(ServiceLoader.load(SHC4JServiceProvider.class).spliterator(), false)
                .collect(Collectors.toCollection(LinkedList::new));
    }


    private final static void bind() {
        try {
            List<SHC4JServiceProvider> providersList = findServiceProviders();
            reportMultipleBindingAmbiguity(providersList);
            if (providersList != null && !providersList.isEmpty()) {
                PROVIDER.set(providersList.get(0));
                PROVIDER.get().initialize();
                Util.report("Actual provider is of type [" + PROVIDER.get().getClass().getCanonicalName() + "]");
                FACTORY_STATE.set(State.SUCCESSFUL_INITIALIZATION);
            } else {
                Util.report("No SHCF4J providers were found.");
                Util.report("Defaulting to no-operation (NOP) HTTP Client implementation");
                FACTORY_STATE.set(State.NOP_FALLBACK_INITIALIZATION);
            }
        } catch (Exception e) {
            FACTORY_STATE.set(State.FAILED_INITIALIZATION);
            failedBinding(e);
            throw new IllegalStateException("Unexpected initialization failure", e);
        }
    }


    static void failedBinding(Throwable t) {
        Util.report("Failed to instantiate SLF4J LoggerFactory", t);
    }


    /**
     * Prints a warning message on the console if multiple bindings were found
     * on the class path. No reporting is done otherwise.
     */
    private static <T> void reportMultipleBindingAmbiguity(List<T> providerList) {
        if (isAmbiguousProviderList(providerList)) {
            Util.report("Class path contains multiple SHCF4J providers.");
            for (T provider : providerList) {
                Util.report("Found provider [" + provider.getClass().getCanonicalName() + "]");
            }
        }
    }


    private static <T> boolean isAmbiguousProviderList(List<T> providerList) {
        return providerList.size() > 1;
    }


    static SHC4JServiceProvider getProvider() {
        switch (FACTORY_STATE.get()) {
            case SUCCESSFUL_INITIALIZATION:
                return PROVIDER.get();
            case NOP_FALLBACK_INITIALIZATION:
                return NOPServiceProvider.INSTANCE;
            case FAILED_INITIALIZATION:
                throw new IllegalStateException(UNSUCCESSFUL_INIT_MSG);
        }
        throw new IllegalStateException("Unreachable code");
    }


    /**
     * @return a {@link HttpClientBuilder}
     */
    public static HttpClientBuilder getHttpClientBuilder() {
        return getProvider().getHttpClientBuilder();
    }


    /**
     * @return a {@link HttpAsyncClientBuilder}
     */
    public static HttpAsyncClientBuilder getHttpAsyncClientBuilder() {
        return getProvider().getHttpAsyncClientBuilder();
    }


}

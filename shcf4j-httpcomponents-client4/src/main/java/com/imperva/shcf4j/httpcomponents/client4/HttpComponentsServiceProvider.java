package com.imperva.shcf4j.httpcomponents.client4;

import com.imperva.shcf4j.AsyncHttpClientBuilder;
import com.imperva.shcf4j.SyncHttpClientBuilder;
import com.imperva.shcf4j.helpers.Util;
import com.imperva.shcf4j.spi.SHC4JServiceProvider;
import org.apache.http.util.VersionInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <b>HttpComponentsServiceProvider</b>
 *
 * @author maxim.kirilov
 */
public class HttpComponentsServiceProvider implements SHC4JServiceProvider {

    private static final Pattern HTTP_COMPONENTS_VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)");

    private static final int HTTP_COMPONENTS_SYNC_SUPPORTED_VERSION_MAJOR = 4;
    private static final int HTTP_COMPONENTS_SYNC_SUPPORTED_VERSION_MINOR = 3;

    private static final int HTTP_COMPONENTS_ASYNC_SUPPORTED_VERSION_MAJOR = 4;
    private static final int HTTP_COMPONENTS_ASYNC_SUPPORTED_VERSION_MINOR = 1;

    public HttpComponentsServiceProvider() {
        checkSyncHttpClientClasspathVersionCompatibility();
        checkAsyncHttpClientClasspathVersionCompatibility();
    }


    private void checkSyncHttpClientClasspathVersionCompatibility() {
        String packageName = "org.apache.http.client";
        String httpComponentsRelease =
                VersionInfo.loadVersionInfo(packageName, getClass().getClassLoader()).getRelease();
        checkVersionCompatibility(packageName, httpComponentsRelease,
                HTTP_COMPONENTS_SYNC_SUPPORTED_VERSION_MAJOR, HTTP_COMPONENTS_SYNC_SUPPORTED_VERSION_MINOR);

    }

    private void checkAsyncHttpClientClasspathVersionCompatibility() {
        String packageName = "org.apache.http.nio.client";
        String httpComponentsRelease =
                VersionInfo.loadVersionInfo(packageName, getClass().getClassLoader()).getRelease();

        checkVersionCompatibility(packageName, httpComponentsRelease,
                HTTP_COMPONENTS_ASYNC_SUPPORTED_VERSION_MAJOR, HTTP_COMPONENTS_ASYNC_SUPPORTED_VERSION_MINOR);
    }


    private void checkVersionCompatibility(String packageName, String version, int supportedMajorVersion, int supportedMinorVersion) {
        Matcher m = HTTP_COMPONENTS_VERSION_PATTERN.matcher(version);

        if (m.matches()) {
            int major = Integer.parseInt(m.group(1));
            int minor = Integer.parseInt(m.group(2));
            if (supportedMajorVersion != major || minor < supportedMinorVersion) {
                Util.report("The runtime version of " + packageName + " :"
                        + version + " not supported, " +
                        "please use version higher than: " + supportedMajorVersion + "." + supportedMinorVersion + ".x");
            }
        }
    }


    @Override
    public SyncHttpClientBuilder getHttpClientBuilder() {
        return HttpClients.custom();
    }

    @Override
    public AsyncHttpClientBuilder getHttpAsyncClientBuilder() {
        return HttpAsyncClients.custom();
    }

}

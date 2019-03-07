package com.imperva.shcf4j.ahc2.client.async;

import com.imperva.shcf4j.Header;
import com.imperva.shcf4j.ProcessingException;
import com.imperva.shcf4j.client.CredentialsProvider;
import io.netty.handler.codec.http.HttpHeaders;
import org.asynchttpclient.Realm;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class ConversionUtils {


    static List<Header> convert(HttpHeaders headers) {
        return StreamSupport
                .stream(headers.spliterator(), false)
                .map(e -> Header.builder().name(e.getKey()).name(e.getValue()).build())
                .collect(Collectors.toList());
    }

    static List<? extends Realm> convert(CredentialsProvider cp) {
        if (cp != null) {

            if (cp.getCredentials().size() > 1) {
                throw new ProcessingException("AHC2 supports only single credentials entry");
            }

            return cp.getCredentials()
                    .entrySet()
                    .stream()
                    .map(e -> {
                        Realm.Builder realm = new Realm.Builder(
                                e.getValue().getUserPrincipal().getName(),
                                e.getValue().getPassword());
                        if (e.getKey().getScheme() != null) { // Auth scheme
                            switch (e.getKey().getScheme().toUpperCase()) {
                                case "BASIC":
                                    realm.setScheme(Realm.AuthScheme.BASIC);
                                    break;
                                case "DIGEST":
                                    realm.setScheme(Realm.AuthScheme.DIGEST);
                                    break;
                                case "NTLM":
                                    realm.setScheme(Realm.AuthScheme.NTLM);
                                    break;
                                case "SPNEGO":
                                    realm.setScheme(Realm.AuthScheme.SPNEGO);
                                    break;
                                case "KERBEROS":
                                    realm.setScheme(Realm.AuthScheme.KERBEROS);
                                    break;
                            }
                        } else { // Default scheme
                            realm.setScheme(Realm.AuthScheme.BASIC);
                        }

                        if (e.getKey().getRealm() != null) { // Auth realm name
                            realm.setRealmName(e.getKey().getRealm());
                        }

                        // Auth hostname
                        // Auth port

                        return realm.build();
                    }).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}

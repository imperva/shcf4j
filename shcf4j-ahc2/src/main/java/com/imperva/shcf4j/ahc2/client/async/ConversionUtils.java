package com.imperva.shcf4j.ahc2.client.async;

import com.imperva.shcf4j.Header;
import io.netty.handler.codec.http.HttpHeaders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class ConversionUtils {



    static List<Header> convert(HttpHeaders headers){
        return StreamSupport
                .stream(headers.spliterator(), false)
                .map(e -> Header.builder().name(e.getKey()).name(e.getValue()).build())
                .collect(Collectors.toList());
    }
}

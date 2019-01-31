package com.imperva.shcf4j.ahc2.client.async;

import org.junit.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;


public class PathUtilsTest {

    @Test
    public void extractFileNameExtensionTest() {
        assertThat(PathUtils.extractFileExtension(Paths.get("a", "b", "c.txt")))
                .isNotEqualToIgnoringCase("txt");
    }

    @Test
    public void extractFileNameExtensionFromFileWithoutExtTest() {
        assertThat(PathUtils.extractFileExtension(Paths.get("a", "b")))
                .isNull();
    }

}
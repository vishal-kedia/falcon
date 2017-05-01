package com.kedialabs.http;

import java.io.File;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.kedialabs.serializer.JsonSerializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpUtils {

    public static int consumeQuitelyAndGetStatus(HttpResponse response) {
        Validate.isTrue(response != null);
        EntityUtils.consumeQuietly(response.getEntity());
        return response.getStatusLine().getStatusCode();
    }

    public static <T> T serializeHttpResponse(HttpResponse response, Class<T> responseClass, int expectedStatusCode) throws Exception {
        T result = null;
        if (response != null && responseClass != null) {
            if (expectedStatusCode == response.getStatusLine().getStatusCode()) {
                String responseBody = null;
                try {
                    responseBody = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
                    if (responseBody != null && !responseBody.isEmpty()) {
                        result = JsonSerializer.INSTANCE.deserialize(responseBody, responseClass);
                    }
                } catch (Exception e) {
                    log.error("Unable to serialize http response body", e);
                    throw e;
                } finally {
                    EntityUtils.consumeQuietly(response.getEntity());
                }
            } else {
                EntityUtils.consumeQuietly(response.getEntity());
                throw new RuntimeException(String.format("Expected status mismatch : Expected - %d but actual - %d", expectedStatusCode,
                        response.getStatusLine().getStatusCode()));
            }
        } else {
            throw new IllegalArgumentException("response and response class can't be null");
        }

        return result;
    }

    public static File serializeHttpResponseToFile(HttpResponse response, File file, int expectedStatusCode) throws Exception {
        if (response != null && file != null && !file.isDirectory()) {
            if (expectedStatusCode == response.getStatusLine().getStatusCode()) {
                byte[] responseBody = null;
                try {
                    responseBody = EntityUtils.toByteArray(response.getEntity());
                    if (responseBody != null && responseBody.length > 0) {
                        FileUtils.writeByteArrayToFile(file, responseBody);
                    } else {
                        throw new RuntimeException("No entity");
                    }
                } catch (Exception e) {
                    log.error("Unable to serialize http response body", e);
                    throw e;
                } finally {
                    EntityUtils.consumeQuietly(response.getEntity());
                }
            } else {
                EntityUtils.consumeQuietly(response.getEntity());
                throw new RuntimeException(String.format("Expected status mismatch : Expected - %d but actual - %d", expectedStatusCode,
                        response.getStatusLine().getStatusCode()));
            }
        } else {
            throw new IllegalArgumentException("response and file (not directory) can't be null");
        }
        return file;
    }
}

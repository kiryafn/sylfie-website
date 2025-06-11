package com.sylfie.util;

import software.amazon.awssdk.services.s3.endpoints.internal.Value;

public record S3Info(String key, String url, String bucket) {
}

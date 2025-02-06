package com.auth.global;

import java.util.Map;

public record BaseResponse(
        Boolean result,
        String message,
        Object data
) {
}

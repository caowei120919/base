package com.datacvg.sempmobile.baseandroid.oklog;

import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.baseandroid.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * FileName: LogDataBuilder
 * Author: 曹伟
 * Date: 2019/9/16 16:20
 * Description:
 */

public class LogDataBuilder {
    private static final int JSON_INDENT = 4;

    // region BodyState
    public enum BodyState {

        PLAIN_BODY(1),
        NO_BODY(2),
        ENCODED_BODY(3),
        BINARY_BODY(4),
        CHARSET_MALFORMED(5);

        private final int intValue;

        BodyState(int intValue) {
            this.intValue = intValue;
        }

        int getIntValue() {
            return intValue;
        }
    }

    // endregion BodyState
    private String requestMethod;
    private String requestUrl;
    private String requestUrlPath;
    private String protocol;
    private String requestContentType;
    private long requestContentLength;
    private List<HeaderDataBuilder> requestHeaders;
    private String requestBody;
    private BodyState requestBodyState;
    private boolean requestFailed;

    private int responseCode;
    private String responseMessage;
    private String responseUrl;
    private long responseDurationMs;
    private long responseContentLength;
    private List<HeaderDataBuilder> responseHeaders;
    private BodyState responseBodyState;
    private long responseBodySize;
    private String responseBody;

    LogDataBuilder() {
        this.requestBodyState = BodyState.PLAIN_BODY;
        this.responseBodyState = BodyState.PLAIN_BODY;
    }

    LogDataBuilder requestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    LogDataBuilder requestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
        return this;
    }

    LogDataBuilder requestUrlPath(String requestUrlPath) {
        this.requestUrlPath = requestUrlPath;
        return this;
    }

    LogDataBuilder protocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    LogDataBuilder requestContentType(String contentType) {
        this.requestContentType = contentType;
        return this;
    }

    LogDataBuilder requestContentLength(long contentLength) {
        this.requestContentLength = contentLength;
        return this;
    }

    LogDataBuilder addRequestHeader(String name, String value) {
        if (this.requestHeaders == null) {
            this.requestHeaders = new ArrayList<>();
        }

        this.requestHeaders.add(new HeaderDataBuilder(name, value));
        return this;
    }

    LogDataBuilder requestBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    LogDataBuilder requestBodyState(BodyState requestBodyState) {
        this.requestBodyState = requestBodyState;
        return this;
    }

    public LogDataBuilder requestFailed() {
        this.requestFailed = true;
        return this;
    }

    LogDataBuilder responseCode(int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    LogDataBuilder responseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
        return this;
    }

    LogDataBuilder responseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
        return this;
    }

    public LogDataBuilder responseDurationMs(long responseDurationMs) {
        this.responseDurationMs = responseDurationMs;
        return this;
    }

    LogDataBuilder responseContentLength(long responseContentLength) {
        this.responseContentLength = responseContentLength;
        return this;
    }

    LogDataBuilder addResponseHeader(String name, String value) {
        if (this.responseHeaders == null) {
            this.responseHeaders = new ArrayList<>();
        }

        this.responseHeaders.add(new HeaderDataBuilder(name, value));
        return this;
    }

    LogDataBuilder responseBodyState(BodyState responseBodyState) {
        this.responseBodyState = responseBodyState;
        return this;
    }

    LogDataBuilder responseBodySize(long responseBodySize) {
        this.responseBodySize = responseBodySize;
        return this;
    }

    LogDataBuilder responseBody(String responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    // region Getters

    String getRequestMethod() {
        return requestMethod;
    }

    String getRequestUrl() {
        return requestUrl;
    }

    String getRequestUrlPath() {
        return requestUrlPath;
    }

    String getProtocol() {
        return protocol;
    }

    String getRequestContentType() {
        return requestContentType;
    }

    long getRequestContentLength() {
        return requestContentLength;
    }

    List<HeaderDataBuilder> getRequestHeaders() {
        return requestHeaders;
    }

    String getRequestBody() {
        return requestBody;
    }

    BodyState getRequestBodyState() {
        return requestBodyState;
    }

    boolean isRequestFailed() {
        return requestFailed;
    }

    int getResponseCode() {
        return responseCode;
    }

    String getResponseMessage() {
        return responseMessage;
    }

    String getResponseUrl() {
        return responseUrl;
    }

    long getResponseDurationMs() {
        return responseDurationMs;
    }

    long getResponseContentLength() {
        return responseContentLength;
    }

    List<HeaderDataBuilder> getResponseHeaders() {
        if (responseHeaders == null) {
            responseHeaders = new ArrayList<>();
        }
        return responseHeaders;
    }

    BodyState getResponseBodyState() {
        return responseBodyState;
    }

    long getResponseBodySize() {
        return responseBodySize;
    }

    String getResponseBody() {
        if (StringUtils.isEmpty(responseBody)) {
            requestBody = "";
        }
        return responseBody;
    }

    // endregion Getters

    @Override
    public String toString() {
        StringBuilder logger = new StringBuilder();
        PLog.e(" ---------------> " + requestMethod + " [" + requestUrl + "] " + protocol);
        if (!StringUtils.isEmpty(getRequestBody())) {
            PLog.e(" (" + requestContentLength + "-byte body)");
            PLog.e("\r\n");
        }
        if (!StringUtils.isEmpty(getRequestBody())) {
            if (!StringUtils.isEmpty(getRequestContentType())) {
                PLog.e(" ---------------> " + "Content-Type: " + getRequestContentType());
            }
            if (requestContentLength != -1) {
                PLog.e(" ---------------> " + "Content-Length: " + requestContentLength);
            }
        }

        List<HeaderDataBuilder> headerDataBuilderReqList = getRequestHeaders();
        for (int i = 0, count = headerDataBuilderReqList.size(); i < count; i++) {
            HeaderDataBuilder headerDataBuilder = headerDataBuilderReqList.get(i);
            if (!"Content-Type".equalsIgnoreCase(headerDataBuilder.getName()) && !"Content-Length"
                    .equalsIgnoreCase(headerDataBuilder.getName())) {
                PLog.e(" ---------------> " + headerDataBuilder.getName() + ": " + headerDataBuilder.getValue());
            }
        }

        if (requestBodyState == BodyState.NO_BODY) {
            PLog.e(" ---------------> " + "--> END " + requestMethod);
        } else if (requestBodyState == BodyState.ENCODED_BODY) {
            PLog.e(" ---------------> " + "--> END " + requestMethod + " (encoded body omitted)");
        } else if (requestBodyState == BodyState.BINARY_BODY) {
            PLog.e(" ---------------> " + "--> END " + requestMethod + " (binary " + requestContentLength + "-byte body " + "omitted)");
        } else {
            PLog.e(" ---------------> " + getRequestBody());
            PLog.e(" ---------------> " + "--> END " + requestMethod + " (" + requestContentLength + "-byte body)");
        }

        String bodySize = responseContentLength != -1 ? responseContentLength + "-byte" : "unknown-length";
        PLog.e(" <--------------- "  + responseCode + ' ' + responseMessage + ' ' + responseUrl + " (" + responseDurationMs + "ms" + ", " + bodySize + " body" + ')');

        List<HeaderDataBuilder> headerDataBuilderRspList = getResponseHeaders();
        for (int i = 0, count = headerDataBuilderRspList.size(); i < count; i++) {
            HeaderDataBuilder headerDataBuilder = headerDataBuilderRspList.get(i);
            PLog.e(" <--------------- " + headerDataBuilder.getName() + ": " + headerDataBuilder.getValue());
        }

        if (responseBodyState == BodyState.NO_BODY) {
            PLog.e(" <--------------- END HTTP");
        } else if (responseBodyState == BodyState.ENCODED_BODY) {
            PLog.e(" <--------------- END HTTP (encoded body omitted)");
        } else if (responseBodyState == BodyState.BINARY_BODY) {
            PLog.e(" <--------------- END HTTP (binary " + responseContentLength + "-byte body omitted)");
        } else {
            // format body to json
            PLog.e(" <--------------- " + formatBodyToJson(responseBody));
            PLog.e(" <--------------- END HTTP (" + responseContentLength + "-byte body)");
        }
        return logger.toString();
    }

    static final class HeaderDataBuilder {

        private final String name;
        private final String value;

        private HeaderDataBuilder(String name, String value) {
            this.name = name;
            this.value = value;
        }

        String getName() {
            return name;
        }

        String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "HeaderDataBuilder{" + "name='" + name + '\'' + ", value='" + value + '\'' + '}';
        }
    }

    /**
     * 格式化Body内容为Json格式
     */
    public static String formatBodyToJson(String body) {
        if (StringUtils.isEmpty(body)) {
            return "body is null";
        }
        String result;
        try {
            if (body.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(body);
                result = jsonObject.toString(JSON_INDENT);
            } else if (body.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(body);
                result = jsonArray.toString(JSON_INDENT);
            } else {
                result = body;
            }
        } catch (JSONException e) {
            result = body;
        }

        return result;
    }

}

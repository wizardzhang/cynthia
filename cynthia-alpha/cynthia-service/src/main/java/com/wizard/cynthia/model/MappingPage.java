/**
 * Copyright 2021 json.cn
 */
package com.wizard.cynthia.model;

import java.util.List;

/**
 * Auto-generated: 2021-04-30 17:8:53
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class MappingPage {

    private List<Mappings> mappings;
    private Meta meta;

    public void setMappings(List<Mappings> mappings) {
        this.mappings = mappings;
    }

    public List<Mappings> getMappings() {
        return mappings;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Meta getMeta() {
        return meta;
    }

    /**
     * Auto-generated: 2021-04-30 17:8:53
     *
     * @author json.cn (i@json.cn)
     * @website http://www.json.cn/java2pojo/
     */
    public static class Mappings {

        private String id;
        private Request request;
        private Response response;
        private String uuid;
        public void setId(String id) {
             this.id = id;
         }
         public String getId() {
             return id;
         }

        public void setRequest(Request request) {
             this.request = request;
         }
         public Request getRequest() {
             return request;
         }

        public void setResponse(Response response) {
             this.response = response;
         }
         public Response getResponse() {
             return response;
         }

        public void setUuid(String uuid) {
             this.uuid = uuid;
         }
         public String getUuid() {
             return uuid;
         }

    }

    /**
     * Auto-generated: 2021-04-30 17:8:53
     *
     * @author json.cn (i@json.cn)
     * @website http://www.json.cn/java2pojo/
     */
    public static class Meta {

        private int total;
        public void setTotal(int total) {
             this.total = total;
         }
         public int getTotal() {
             return total;
         }

    }

    /**
     * Auto-generated: 2021-04-30 17:8:53
     *
     * @author json.cn (i@json.cn)
     * @website http://www.json.cn/java2pojo/
     */
    public static class Request {

        private String url;
        private String method;
        public void setUrl(String url) {
             this.url = url;
         }
         public String getUrl() {
             return url;
         }

        public void setMethod(String method) {
             this.method = method;
         }
         public String getMethod() {
             return method;
         }

    }

    /**
     * Auto-generated: 2021-04-30 17:8:53
     *
     * @author json.cn (i@json.cn)
     * @website http://www.json.cn/java2pojo/
     */
    public static class Response {

        private int status;
        private String body;
        public void setStatus(int status) {
             this.status = status;
         }
         public int getStatus() {
             return status;
         }

        public void setBody(String body) {
             this.body = body;
         }
         public String getBody() {
             return body;
         }

    }
}
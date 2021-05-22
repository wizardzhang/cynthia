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

    private List<Mapping> mappings;
    private Meta meta;

    public void setMappings(List<Mapping> mappings) {
        this.mappings = mappings;
    }

    public List<Mapping> getMappings() {
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
    public static class Meta {

        private int total;
        public void setTotal(int total) {
             this.total = total;
         }
         public int getTotal() {
             return total;
         }

    }
}
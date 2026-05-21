package com.fastproject.system.vo.dictdata;

import lombok.Data;

import java.util.List;

@Data
public class Dict {
    private String type;

    private List<DictData> data;

    @Data
    public static class DictData {
        private String label;
        private String value;
    }
}

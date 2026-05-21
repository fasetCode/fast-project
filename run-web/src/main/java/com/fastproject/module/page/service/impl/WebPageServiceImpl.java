package com.fastproject.module.page.service.impl;

import com.fastproject.module.page.service.WebPageService;
import com.fastproject.page.service.PageWebConfigService;
import com.fastproject.page.vo.pagewebconfig.PageWebConfigVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebPageServiceImpl implements WebPageService {

    private final PageWebConfigService webConfigService;

    @Override
    public String getPathPage(String path,String appCode) {
        PageWebConfigVo configVo = webConfigService.findByConfigAndCode(path,appCode);
        if (configVo!=null) {
            return configVo.getConfig();
        }
        return "";
    }
}

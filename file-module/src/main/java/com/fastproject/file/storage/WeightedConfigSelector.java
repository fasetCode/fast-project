package com.fastproject.file.storage;

import com.fastproject.file.vo.config.FileConfigVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 基于权重的配置选择器
 * 权重越大的配置被选中的概率越高
 */
@Slf4j
@Component
public class WeightedConfigSelector implements ConfigSelector {

    private final Random random = new Random();

    @Override
    public FileConfigVo select(List<FileConfigVo> configs) {
        if (configs == null || configs.isEmpty()) {
            return null;
        }

        // 过滤出可用的配置（状态为启用）
        List<FileConfigVo> availableConfigs = configs.stream()
                .filter(c -> c.getStatus() == null || c.getStatus() == 1)
                .collect(Collectors.toList());

        if (availableConfigs.isEmpty()) {
            log.warn("没有可用的存储配置");
            return null;
        }

        // 如果只有一个配置，直接返回
        if (availableConfigs.size() == 1) {
            return availableConfigs.get(0);
        }

        // 计算总权重
        int totalWeight = availableConfigs.stream()
                .mapToInt(c -> c.getWeight() == null ? 1 : c.getWeight())
                .sum();

        // 随机选择一个权重值
        int randomWeight = random.nextInt(totalWeight);

        // 根据权重选择配置
        int currentWeight = 0;
        for (FileConfigVo config : availableConfigs) {
            int weight = config.getWeight() == null ? 1 : config.getWeight();
            currentWeight += weight;
            if (randomWeight < currentWeight) {
                log.debug("选中存储配置：id={}, type={}, weight={}",
                        config.getId(), config.getType(), weight);
                return config;
            }
        }

        // 默认返回最后一个
        return availableConfigs.get(availableConfigs.size() - 1);
    }
}

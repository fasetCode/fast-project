package com.fastproject.service;

import com.fastproject.domain.Application;
import com.fastproject.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    public Optional<Application> findById(Long id) {
        return applicationRepository.findById(id);
    }

    @Transactional
    public Application create(Application application) {
        if (application.getAppId() == null || application.getAppId().isEmpty()) {
            application.setAppId(UUID.randomUUID().toString().replace("-", ""));
        }
        if (application.getAppSecret() == null || application.getAppSecret().isEmpty()) {
            application.setAppSecret(UUID.randomUUID().toString().replace("-", ""));
        }
        if (application.getStatus() == null) {
            application.setStatus(1);
        }
        return applicationRepository.save(application);
    }

    @Transactional
    public Application update(Application application) {
        Application byId = applicationRepository.findById(application.getId()).orElseThrow(() -> new RuntimeException("应用不存在"));
        application.setAppId(byId.getAppId());
        application.setAppSecret(byId.getAppSecret());
        return applicationRepository.save(application);
    }

    @Transactional
    public void deleteById(Long id) {
        applicationRepository.deleteById(id);
    }

    public Application findByAppId(String appId) {
        return applicationRepository.findByAppId(appId);
    }
}

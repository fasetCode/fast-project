package com.fastproject.service.impl;

import com.fastproject.domain.Cup;
import com.fastproject.repository.CupRepository;
import com.fastproject.service.CupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CupServiceImpl implements CupService {

    private final CupRepository cupRepository;

    @Override
    public Cup save(Cup cup) {
        return cupRepository.save(cup);
    }

    @Override
    public Optional<Cup> findById(Long id) {
        return cupRepository.findById(id);
    }

    @Override
    public List<Cup> findAll() {
        return cupRepository.findAll();
    }

    @Override
    public List<Cup> findByRecordTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return cupRepository.findByRecordTimeBetween(startTime, endTime);
    }

    @Override
    public List<Cup> findLatest(int limit) {
        return cupRepository.findAll(
                PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "recordTime"))
        ).getContent();
    }

    @Override
    public void deleteById(Long id) {
        cupRepository.deleteById(id);
    }

    @Override
    public List<Cup> saveAll(List<Cup> cups) {
        return cupRepository.saveAll(cups);
    }
}

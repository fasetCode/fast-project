package com.fastproject.service.impl;

import com.fastproject.domain.Memory;
import com.fastproject.repository.MemoryRepository;
import com.fastproject.service.MemoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoryServiceImpl implements MemoryService {

    private final MemoryRepository memoryRepository;

    @Override
    public Memory save(Memory memory) {
        return memoryRepository.save(memory);
    }

    @Override
    public Optional<Memory> findById(Long id) {
        return memoryRepository.findById(id);
    }

    @Override
    public List<Memory> findAll() {
        return memoryRepository.findAll();
    }

    @Override
    public List<Memory> findByRecordTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return memoryRepository.findByRecordTimeBetween(startTime, endTime);
    }

    @Override
    public List<Memory> findLatest(int limit) {
        return memoryRepository.findAll(
                PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "recordTime"))
        ).getContent();
    }

    @Override
    public void deleteById(Long id) {
        memoryRepository.deleteById(id);
    }

    @Override
    public List<Memory> saveAll(List<Memory> memories) {
        return memoryRepository.saveAll(memories);
    }
}

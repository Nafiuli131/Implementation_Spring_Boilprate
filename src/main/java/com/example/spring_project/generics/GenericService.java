package com.example.spring_project.generics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public class GenericService<T extends BaseEntity> {
    @Autowired
    private GenericRepository<T> genericRepository;

    public T save(T entity) throws Exception {
        return genericRepository.save(entity);
    }

    public T update(T entity) throws Exception {
        Optional<T> getEntity = genericRepository.findById(entity.getId());
        return genericRepository.save(entity);
    }

    public Optional<T> findById(Long id) throws Exception {
        return genericRepository.findById(id);
    }

    public List<T> findAll() throws Exception {
        return genericRepository.findAll();
    }

    public void deleteById(Long id) throws Exception {
        genericRepository.deleteById(id);
    }

    public PaginationResponseDto<T> paginationWithSortingAsc(int page, int pageSize) {
        int adjustedPage = page - 1;
        Page<T> pageResult = genericRepository.findAll(PageRequest.of(adjustedPage, pageSize)
                .withSort(Sort.by("id").ascending()));
        PaginationResponseDto<T> paginationResponseDto = new PaginationResponseDto<>();
        paginationResponseDto.setContent(pageResult.getContent());
        paginationResponseDto.setPageNumber(pageResult.getNumber() + 1);
        paginationResponseDto.setPageSize(pageResult.getSize());
        paginationResponseDto.setTotalPage(pageResult.getTotalPages());
        paginationResponseDto.setTotalContent(pageResult.getTotalElements());
        return paginationResponseDto;
    }

    public PaginationResponseDto<T> paginationWithSortingDesc(int page, int pageSize) {
        int adjustedPage = page - 1;
        Page<T> pageResult = genericRepository.findAll(PageRequest.of(adjustedPage, pageSize)
                .withSort(Sort.by("id").descending()));
        PaginationResponseDto<T> paginationResponseDto = new PaginationResponseDto<>();
        paginationResponseDto.setContent(pageResult.getContent());
        paginationResponseDto.setPageNumber(pageResult.getNumber() + 1);
        paginationResponseDto.setPageSize(pageResult.getSize());
        paginationResponseDto.setTotalPage(pageResult.getTotalPages());
        paginationResponseDto.setTotalContent(pageResult.getTotalElements());
        return paginationResponseDto;
    }
}

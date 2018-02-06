package com.example.member.repository;

import com.example.member.model.MemberEx;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MemberExRepository extends PagingAndSortingRepository<MemberEx, Long> {}

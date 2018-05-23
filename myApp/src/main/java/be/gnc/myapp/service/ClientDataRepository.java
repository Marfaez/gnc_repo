package be.gnc.myapp.service;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import be.gnc.myapp.entities.Client;


@Repository
public interface ClientDataRepository extends PagingAndSortingRepository<Client, Long> {
	
	
}


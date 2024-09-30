package com.ucsal.arqsoftware.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ucsal.arqsoftware.dto.PhysicalSpaceDTO;
import com.ucsal.arqsoftware.dto.RequestDTO;
import com.ucsal.arqsoftware.entities.PhysicalSpace;
import com.ucsal.arqsoftware.entities.Request;
import com.ucsal.arqsoftware.repositories.PhysicalSpaceRepository;
import com.ucsal.arqsoftware.servicies.exceptions.DatabaseException;
import com.ucsal.arqsoftware.servicies.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PhysicalSpaceService {

	@Autowired
	private PhysicalSpaceRepository repository;
	
	public PhysicalSpaceDTO findById(Long id) {
		PhysicalSpace physicalSpace = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new PhysicalSpaceDTO(physicalSpace);
	}
	
	public Page<PhysicalSpaceDTO> findAll(Pageable pageable) {
		Page<PhysicalSpace> result = repository.findAll(pageable);
		return result.map(x -> new PhysicalSpaceDTO(x));
	}
	
	public PhysicalSpaceDTO insert(PhysicalSpaceDTO dto) {
		PhysicalSpace entity = new PhysicalSpace();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new PhysicalSpaceDTO(entity);
	}
	
	public PhysicalSpaceDTO update(Long id, PhysicalSpaceDTO dto) {
		try {
			PhysicalSpace entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new PhysicalSpaceDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}

	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
	        repository.deleteById(id);    		
		}
	    catch (DataIntegrityViolationException e) {
	        throw new DatabaseException("Falha de integridade referencial");
	   	}
	}

	private void copyDtoToEntity(PhysicalSpaceDTO dto, PhysicalSpace entity) {
		entity.setName(dto.getName());
		entity.setLocation(dto.getLocation());
		entity.setCapacity(dto.getCapacity());
		entity.setType(dto.getType());
		entity.setResources(dto.getResources());
		entity.getRequests().clear();
		for (RequestDTO reqDto : dto.getRequests()) {
			Request req = new Request();
			req.setId(reqDto.getId());
			entity.getRequests().add(req);
		}
	}
}
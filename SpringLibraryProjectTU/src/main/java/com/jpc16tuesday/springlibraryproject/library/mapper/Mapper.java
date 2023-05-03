package com.jpc16tuesday.springlibraryproject.library.mapper;

import com.jpc16tuesday.springlibraryproject.library.dto.GenericDTO;
import com.jpc16tuesday.springlibraryproject.library.model.GenericModel;

import java.util.List;

public interface Mapper<E extends GenericModel, D extends GenericDTO> {

    E toEntity(D dto);

    D toDTO(E entity);

    List<E> toEntities(List<D> dtos);

    List<D> toDTOs(List<E> entities);
}

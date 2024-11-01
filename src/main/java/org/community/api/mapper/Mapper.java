package org.community.api.mapper;

import java.util.List;

public interface Mapper<T, E>{
    E toEntity(T t);
    T toDTO(E e);
    List<T> toDTOList(List<E> e);
}
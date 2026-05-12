package mvc.service;

import mvc.utils.exception.NotFoundException;
import java.util.List;

public interface IService<T> {
    List<T> findAll();
    void add(T entity);
    void delete(String id) throws NotFoundException;
}
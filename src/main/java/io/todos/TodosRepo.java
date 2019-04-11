package io.todos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodosRepo extends CrudRepository<Todo, Long> {
}

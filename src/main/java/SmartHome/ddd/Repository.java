package SmartHome.ddd;

import java.util.Optional;

public interface Repository<ID extends DomainId, T extends AggregateRoot<ID> > {
  
  T save(T entity);
  
  Iterable<T> findAll();
  
  Optional<T> findById(ID id);
  
  boolean existsById(ID id);
}
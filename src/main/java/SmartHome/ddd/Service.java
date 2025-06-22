package SmartHome.ddd;

import org.apache.commons.lang3.tuple.Pair;

public interface Service<ID, DTO, T> {
    DTO add(DTO dto);
    Iterable<DTO> findAll();
    DTO findById(T id);
    Pair<Boolean, ID> existsById(T id);
}

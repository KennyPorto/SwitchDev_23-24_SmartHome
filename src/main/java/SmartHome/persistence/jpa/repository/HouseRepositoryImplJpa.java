package SmartHome.persistence.jpa.repository;

import SmartHome.domain.house.House;
import SmartHome.domain.repository.HouseRepository;
import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;
import SmartHome.mapper.HouseMapper;
import SmartHome.persistence.jpa.datamodel.HouseDataModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class HouseRepositoryImplJpa implements HouseRepository
{
    @PersistenceContext
    private final EntityManager entityManager;

    public HouseRepositoryImplJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public House save(House entity)
    {
        HouseDataModel houseDataModel = new HouseDataModel(entity);

        HouseDataModel result = entityManager.merge(houseDataModel);

        return HouseMapper.houseDataModelToDomain(result);
    }

    @Override
    public Iterable<House> findAll()
    {
        TypedQuery<HouseDataModel> query = entityManager.createQuery("SELECT h FROM HouseDataModel h",
                HouseDataModel.class);
        List<HouseDataModel> houseDataModels = query.getResultList();

        return HouseMapper.housesDataModelToDomain(houseDataModels);
    }

    @Override
    public Optional<House> findById(HouseId id)
    {
        HouseDataModel houseDataModel = entityManager.find(HouseDataModel.class, id.id);

        if (houseDataModel == null) return Optional.empty();

        House house = HouseMapper.houseDataModelToDomain(houseDataModel);

        return Optional.of(house);
    }

    @Override
    public boolean existsById(HouseId id)
    {
        Optional<House> house = findById(id);

        return house.isPresent();
    }

    @Override
    public House configureLocation(long houseId, Address address, GPS gps) {
        HouseDataModel houseDataModel = entityManager.find(HouseDataModel.class, houseId);
        if (houseDataModel == null) throw new EntityNotFoundException();

        houseDataModel.configureLocation(address.street, address.doorNumber, address.zipCode, address.city,
                address.country, gps.latitude, gps.longitude);

        entityManager.merge(houseDataModel);

        return HouseMapper.houseDataModelToDomain(houseDataModel);
    }
}

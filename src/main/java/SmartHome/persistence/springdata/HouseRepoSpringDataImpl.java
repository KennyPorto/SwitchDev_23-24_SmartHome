package SmartHome.persistence.springdata;

import SmartHome.domain.house.House;
import SmartHome.domain.repository.HouseRepository;
import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;
import SmartHome.mapper.HouseMapper;
import SmartHome.persistence.jpa.datamodel.HouseDataModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static SmartHome.mapper.HouseMapper.housesDataModelToDomain;

@Repository
public class HouseRepoSpringDataImpl implements HouseRepository {
    private final HouseRepoSpringDataInterface _houseRepoSpringDataInterface;

    public HouseRepoSpringDataImpl(HouseRepoSpringDataInterface HouseRepoSpringDataInterface) {
        this._houseRepoSpringDataInterface = HouseRepoSpringDataInterface;
    }

    @Override
    public House save(House entity) {
        HouseDataModel houseDataModel = new HouseDataModel(entity);

        HouseDataModel result = _houseRepoSpringDataInterface.save(houseDataModel);

        return HouseMapper.houseDataModelToDomain(result);
    }

    @Override
    public Iterable<House> findAll() {
        Iterable<HouseDataModel> houseDataModels = _houseRepoSpringDataInterface.findAll();

        return housesDataModelToDomain(houseDataModels);
    }

    @Override
    public Optional<House> findById(HouseId id) {
        Optional<HouseDataModel> houseDataModel = _houseRepoSpringDataInterface.findById(id.id);

        if (houseDataModel.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(HouseMapper.houseDataModelToDomain(houseDataModel.get()));
    }

    @Override
    public boolean existsById(HouseId id) {
        return _houseRepoSpringDataInterface.existsById(id.id);
    }

    @Override
    public House configureLocation(long houseId, Address address, GPS gps) {
        Optional<HouseDataModel> houseDataModel = _houseRepoSpringDataInterface.findById(houseId);
        if (houseDataModel.isEmpty()) throw new EntityNotFoundException();
        HouseDataModel dmEdited = houseDataModel.get();
        dmEdited.configureLocation(address.street, address.doorNumber, address.zipCode, address.city,
                address.country, gps.latitude, gps.longitude);
        _houseRepoSpringDataInterface.save(dmEdited);
        return HouseMapper.houseDataModelToDomain(dmEdited);
    }
}

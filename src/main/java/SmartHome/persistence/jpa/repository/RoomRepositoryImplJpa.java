package SmartHome.persistence.jpa.repository;

import SmartHome.domain.repository.RoomRepository;
import SmartHome.domain.room.Room;
import SmartHome.domain.valueObjects.OutdoorIndoor;
import SmartHome.domain.valueObjects.RoomId;
import SmartHome.mapper.RoomMapper;
import SmartHome.persistence.jpa.datamodel.RoomDataModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional

public class RoomRepositoryImplJpa implements RoomRepository
{
    @PersistenceContext
    EntityManager _entityManager;

    public RoomRepositoryImplJpa(EntityManager entityManager)
    {
        this._entityManager = entityManager;
    }

    public Room save(Room entity)
    {
        RoomDataModel roomDataModel = new RoomDataModel(entity);

        RoomDataModel result = _entityManager.merge(roomDataModel);

        return RoomMapper.roomDataModelToDomain(result);
    }

    @Override
    public Iterable<Room> findAllByHouseId(Long houseId)
    {
        TypedQuery<RoomDataModel> query = _entityManager.createQuery(
                "SELECT e FROM RoomDataModel e WHERE e.houseId = :houseId", RoomDataModel.class);
        query.setParameter("houseId", houseId);

        List<RoomDataModel> listDataModel = query.getResultList();

        return RoomMapper.roomsDataModelToDomain(listDataModel);
    }

    @Override
    public Iterable<Room> findAllByHouseIdAndOutdoorIndoor(long id, OutdoorIndoor outdoorIndoor) {
        TypedQuery<RoomDataModel> query = _entityManager.createQuery(
                "SELECT e FROM RoomDataModel e WHERE e.houseId = :houseId AND e.outdoorIndoor = :outdoorIndoor",
                RoomDataModel.class);
        query.setParameter("houseId", id);
        query.setParameter("outdoorIndoor", outdoorIndoor.toString());

        List<RoomDataModel> listDataModel = query.getResultList();

        return RoomMapper.roomsDataModelToDomain(listDataModel);
    }

    @Override
    public Iterable<Room> findAll()
    {
        TypedQuery<RoomDataModel> query = _entityManager.createQuery(
                "SELECT e FROM RoomDataModel e", RoomDataModel.class);

        List<RoomDataModel> roomDataModel = query.getResultList();

       return RoomMapper.roomsDataModelToDomain(roomDataModel);
    }

    @Override
    public Optional<Room> findById(RoomId id)
    {
        RoomDataModel roomDataModel = _entityManager.find(RoomDataModel.class, id.id);

        if (roomDataModel == null) return Optional.empty();
        Room room = RoomMapper.roomDataModelToDomain(roomDataModel);

        return Optional.of(room);
    }

    @Override
    public boolean existsById(RoomId id)
    {
        Optional<Room> optionalRoom = findById(id);
        return optionalRoom.isPresent();
    }

}
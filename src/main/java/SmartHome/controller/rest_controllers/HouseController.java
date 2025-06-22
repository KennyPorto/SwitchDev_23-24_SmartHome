package SmartHome.controller.rest_controllers;

import SmartHome.controller.rest_controllers.resources.HouseResourceHandler;
import SmartHome.domain.house.House;
import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;
import SmartHome.mapper.HouseDTO;
import SmartHome.mapper.HouseMapper;
import SmartHome.mapper.PeakPowerDTO;
import SmartHome.service.HouseService;
import SmartHome.service.PeakPowerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/houses")
@AllArgsConstructor
public class HouseController
{
    private final HouseService houseService;
    private final PeakPowerService peakPowerService;

    @PostMapping
    public ResponseEntity<HouseDTO> addHouse(@RequestBody HouseDTO dto)
    {
        House house = houseService.add(
                new HouseId(dto.houseId),
                new Address(dto.street, dto.doorNumber, dto.zipCode, dto.city, dto.country),
                new GPS(dto.latitude, dto.longitude));

        return ResponseEntity.status(HttpStatus.CREATED).body(HouseResourceHandler.manageAddAndPut(
                HouseMapper.houseToDTO(house)));
    }

    @PutMapping("/{house-id}")
    public ResponseEntity<Object> configureLocation(@PathVariable("house-id") long houseId, @RequestBody HouseDTO dto)
    {
        House house = houseService.configureLocation(
                houseId,
                new Address(dto.street, dto.doorNumber, dto.zipCode, dto.city, dto.country),
                new GPS(dto.latitude, dto.longitude));

        return ResponseEntity.status(HttpStatus.CREATED).body(HouseResourceHandler.manageAddAndPut(
                HouseMapper.houseToDTO(house)));
    }

    @GetMapping("/{house-id}")
    public ResponseEntity<HouseDTO> findHouseById(@PathVariable("house-id") long houseId)
    {
        House house = houseService.findById(houseId);
        return ResponseEntity.status(HttpStatus.OK).body(
                HouseResourceHandler.manageFindById(HouseMapper.houseToDTO(house)));
    }

    @GetMapping
    public ResponseEntity<Iterable<HouseDTO>> findAllHouses()
    {
        Iterable<House> houses = houseService.findAll();
        Iterable<HouseDTO> houseDTOS = HouseResourceHandler.manageFindAll(HouseMapper.houseListToDTO(houses));

        return ResponseEntity.status(HttpStatus.OK).body(houseDTOS);
    }

    @GetMapping(value = "/{house-id}/peak-power",
            params = {"start-date", "end-date", "delta"})
    public ResponseEntity<PeakPowerDTO> findPeakPower(
            @PathVariable("house-id") long houseId,
            @RequestParam("start-date") String start,
            @RequestParam("end-date") String end,
            @RequestParam("delta") String delta
    ) {
        long deltaL = Long.parseLong(delta);
        HouseId id = new HouseId(houseId);
        ZonedDateTime startDate = ZonedDateTime.parse(start);
        ZonedDateTime endDate = ZonedDateTime.parse(end);

        double result = peakPowerService.findPeakPower(id, startDate, endDate, deltaL);

        return ResponseEntity.status(HttpStatus.OK).body(HouseResourceHandler.managePeakPower(houseId, new PeakPowerDTO(result)));
    }

}
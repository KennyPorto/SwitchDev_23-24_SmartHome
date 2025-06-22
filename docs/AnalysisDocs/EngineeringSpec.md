# SmartHome Engineering Specification

## General Details

- System to allow users to manage the smart home through network connected *Devices* by changing *Sensor* and *Actuator* behavior
- *House* includes Gardens and other outside Buildings
- *House* has *Address*
- *Address* includes among other, *ZipCode* and *GPS Location*
- *Room* is a part of a *House* which has independent logical and _**possibly**_ physical representation
- *Room* has *name*, *house floor* and *dimensions*
- *Device*s in *Room* have the same set of *permissions*
- *Device* has a *name*, *type/model* and *location (in a room)*
- *Sensors* and *Actuators* have measurement logs, described each as *ActivityLogs* interface with each having its own interface

## System Design Requirements
1. *House* can be created without *Room*s (referring to independent logical and _**possibly**_ physical representation);
2. *House* has its own identity, defined by its ~~name~~ id, and cannot be created without a specified *Location*;
3. *Location* is part of the *House* and yet immutable, meaning the system do represent the *House* as a physical concept that exist in the real world;
4. *Location* *GPS* is represented by latitude and longitude coordinates meaning that the rules are latitude not less or bigger than 90 and longitude not less or bigger than 180;
5. *Room* has its own identity, defined by its ~~name~~ id. It will be defined by specific system users and I might be changed upon time;
6. *Room* *Dimensions* have just one rule, which is, any of the dimensions must be bigger than zero;
7. *Room* *Dimensions* are defined by length and width;
8. *Floor* is defined within the *Room* creation. Since *Room* is not necessarily matching a physical room, also the concept of *Floor* is contained within the *Room* concept; 
9. *Device* does not exist without the *Room*. Stated in Sprint 1 on 13/12/2023 to Diogo Oliveira at Teams (point Q: "9. Pode um Device não ter Room?" A: "Não.");
10. *Device* has its own identity defined by its ~~name~~ id. Starts without *Sensor*s and *Actuator*s which give it functionality;
11. *Device* has its own logs, but at the moment it's not specified anything else;
12. *Device* has a device model, which for the moment, is a name that the user specifies;
13. *Device*s from the same model can exist within the same *House* and same *Room*
14. *Device* can be deactivated but its logs and configuration (eg. Sensor and Actuator List) are kept. After deactivation, I can no longer be used, meaning there's no way to activate it anymore while its still in the system and its sensors and actuators are also not deleted or moved (no deletion);
15. After *Device* deactivation, no sensor or actuator can be added to that Device. Stated on 05/03/2024 on reply to Luis Ferreira at Teams.
16. *Sensor* exists without the *Device* and has a *Functionality/Type* matching its behaviour;
17. Each of the *Sensor*s in the system have its own identity defined by its name, along with its own unique behavior;
18. *Actuator* exists without the *Device* and has a *Functionality/Type* matching its behaviour;
19. Each of the *Actuator*s in the system have its own identity defined by its name, along with its own unique behavior;
20. *Functionality/Type* is predefined on the system and can only the expanded on the system upon request;
21. *User Permissions and Roles* are not yet to be implemented in the system;
#### **User Story Specifics:**
22. *WindSensor*s only measures positive speed values without maximum limit nor precision setting. Direction is measured in ~~8 cardinal points~~ radians. Stated at 28/02/2024 to Luis Ferreira at Teams;
23. *TemperatureSensor*s have no precision nor min and max limits setting;
24. *HumiditySensor*s have no precision setting, and it's min limit is 0 and max limit is 100 in percentage;
25. *ScaleSensor*s have no precision setting, and it's min limit is 0 and max limit is 100 in percentage;
26. *BlindRollerActuator*s have no precision setting, and it's min limit is 0 and max limit 100 in percentage;
27. *RangeActuatorInt*s have no precision nor max range setting. Limits should be defined upon instantiation. Reply to Tatiana on 07/03/2024 at Teams;
28. *RangeActuatorDecimal*s have no max range setting and precision is predefined being > 0 without max precision setting. Limits should be defined upon instantiation. Reply to Tatiana on 07/03/2023 at Teams;;
29. *US09 - getAllDevicesByFunctionalityAndRoom* will not need to capture the Devices *without Sensor*s or *Actuator*s. Reply to Mariana da Cruz Pereira in 23/01/204 at Teams;
30. *US33* As a Room Owner [or Power User, or Administrator], I want to get a list of all measurement of a device in a room, in a given period given by the user.
31. *US34* As a Room Owner [or Power User, or Administrator], I want to get the maximum instantaneous temperature difference between a device in the room and the outside, in a given period. 
The algorithm finds the shortest possible time difference between the two temperature sensors logs in the given time period and calculates the instant difference between both.

## Quality Gates
1. *Unit tests (with isolation)* for the domain classes;
2. *Integration tests (without isolation)* for the controller classes;
3. *Code Coverage and Mutation Coverage* at a minimum of 95%;
4. All code and documentation written in English;

## Acceptance criteria
- **US01 - configure the location of the house:**
  1. Test for valid input data for address and gps;
  2. Test for invalid input data for address, such as null, blank and empty Strings;
  3. Test for invalid input data for gps, such as latitude <90, >90 and longitude <180 and >180;
- **US02 - add a new room to the house:**
  1. Test for valid input data for room and dimensions;
  2. Test for invalid input data for room name and room floor, such as null, blank and empty Strings;
  3. Test for invalid input data for room dimensions, such as negative length and width;
  4. Test for duplication based on the identifier;
- **US03 - have a list of existing rooms:**
  1. Test for return of empty room list;
  2. Test for return of room list with at least 1 room;
- **US04: no valid anymore. Specified why in *Functionality/Type* topic on the System Design Requirements Section**
- **US05: updated to US05v2**
- **US05v2 - add a new device to a room:**
  1. Test for successful addition. Returns True;
  2. Test for addition to a non-existing Room (found by its identifier). Returns False;
  3. Test for invalid input data for device name and device model, such as null, blank and empty Strings. Returns False;
  4. Test for duplication based on the identifier. Returns False;
- **US06 - get a list of all devices in a room:**
  1. Test for return of empty device list on existing room, based on its identifier;
  2. Test for return of device list with at least 1 device on existing room, based on its identifier;
  3. Test for return of null for non-existing room (Controller tests);
- **US07 - add a sensor to an existing device in a room. The sensor must be of a model of an existing type of sensor:**
  1. Test for successful addition. Returns True;
  2. Test for addition to a non-existing Device (found by its identifier) and existing Room. Returns False;
  3. Test for invalid input data for Sensor Name, such as null, blank and empty Strings. Returns False;
  4. Test for invalid input data for Sensor Model, such as null, blank, empty and non-existing Sensor Model. Returns False;
  5. Test for duplication based on the identifier. Returns False;
- **US08 - I want to deactivate a device, so that it is no longer used. Nevertheless, it should be possible to access its configuration and activity log:**
  1. Test for successful deactivation. Returns True;
  2. Test for non-existing Device. Returns False;
  3. Test for not being able to add a sensor or actuator after deactivated device. (Stated on 05/03/2024 on reply to Luis Ferreira at Teams)
- **US09 - get a list of all devices in a house, grouped by device functionality types. It must include device location:**
  1. Test for empty list of room. Returns null;
  2. Test for empty device list. Returns null;
  3. Test for device without sensors and actuators. Returns "Without Functionalities...";
  4. Test for device with one sensor. Returns "Functionality...";
  5. Test for device with one actuator. Returns "Functionality...";
  6. Test for one device with one sensor and one actuator and other device without sensors and actuators;
- **US10 - sensor that measures temperature (C):**
  1. Test for successful creation. Returns True;
  2. Test for duplicate creation based on its identifier. Returns False;
  3. Test for reading valid measurement.
- **US11 - sensor that measures humidity (%):**
  1. Test for successful creation. Returns True;
  2. Test for duplicate creation based on its identifier. Returns False;
  3. Test for reading valid measurement on the min and max limits;
  4. Test for invalid measurement above 0 and over 100. Returns null;
- **US12 - add an actuator to an existing device in a room. The actuator must be of a model of an existing type of actuator:**
  1. Test for successful addition. Returns True;
  2. Test for addition to a non-existing Device (found by its identifier) and existing Room. Returns False;
  3. Test for invalid input data for Actuator Name, such as null, blank and empty Strings. Returns False;
  4. Test for invalid input data for Actuator Model, such as null, blank, empty and non-existing Sensor Model. Returns False;
  5. Test for duplication based on the identifier. Returns False;
- **US13 - actuator that switches a load ON/OFF:**
  1. Test for successful creation. Returns True;
  2. Test for duplicate creation based on its identifier. Returns False;
  3. Test for set on/off;
- **US14 - actuator that open/closes a blind roller (0% closed, 100% fully open):**
  1. Test for successful creation. Returns True;
  2. Test for duplicate creation based on its identifier. Returns False;
  3. Test for reading valid measurement on the min and max limits;
  4. Test for invalid measurement above 0 and over 100. Returns null;
- **US15 - actuator that sets an integer value in the range defined by [lower limit, upper limit]:**
  1. Test for successful creation. Returns True;
  2. Test for duplicate creation based on its identifier. Returns False;
  3. Test for reading valid measurement on the min and max limits;
  4. Test for invalid measurement reading above minLimit and over upperLimit. Returns null;
  5. Test for setting minLimit > upperLimit. Throws exception/Returns False;
- **US16 - actuator that sets a decimal value in the range defined by [lower limit, upper limit] and predefined precision:**
  1. Test for successful creation. Returns True;
  2. Test for duplicate creation based on its identifier. Returns False;
  3. Test for reading valid measurement on the min, max limits and precision;
  4. Test for invalid measurement reading above minLimit and over upperLimit. Returns null;
  5. Test for setting minLimit > upperLimit. Throws exception/Returns False;
  6. Test for setting precision > 0;
- **US17 - sensor that gives the status of a binary switch (ON/OFF):**
  1. Test for successful creation. Returns True;
  2. Test for duplicate creation based on its identifier. Returns False;
  3. Test for set on/off;
- **US18 - sensor that gives the current value/position in a scale (%):**
  1. Test for successful creation. Returns True;
  2. Test for duplicate creation based on its identifier. Returns False;
  3. Test for reading valid measurement on the min and max limits;
  4. Test for invalid measurement above 0 and over 100. Returns null;
- **US19 -  sensor that gives the wind speed (km/h) and direction (~~8 cardinal points~~ radians):**
  1. Test for successful creation. Returns True;
  2. Test for duplicate creation based on its identifier. Returns False;
  3. Test for reading valid measurement over 0km/h;
  4. Test for invalid measurement above 0 and over 100. Returns null;
- **US20 - sensor that gives the sunrise instant for a given calendar date:**
  1. Test for successful creation. Returns True;
  2. TEst for duplicate creation based on its identifier. Returns False;
  3. Test for reading valid calculation of sunrise instant with valid calendar date;
  4. Test for reading invalid calculation of sunrise instant with invalid calendar date;
- **US21 - sensor that gives the sunset instant for a given calendar date:**
  1. Test for successful creation. Returns True;
  2. Test for duplicate creation based on its identifier. Returns False;
  3. Test for reading valid calculation of sunset instant with valid calendar date;
  4. Test for reading invalid calculation of sunset instant with invalid calendar date;
- **US22 - sensor that gives the dew point (C):**
  1. Test for successful creation. Returns True;
  2. Test for duplicate creation based on its identifier. Returns False;
  3. Test for reading valid measurement.
- **US23 - sensor that gives the solar irradiance (W/m2):**
  1. Test for successful creation. Returns True;
  2. Test for duplicate creation based on its identifier. Returns False;
  3. Test for reading valid measurement (always > 0);
- **US24 - sensor that gives the power consumption in a given instant (W):**
  1. Test for successful creation. Returns True;
  2. Test for duplicate creation based on its identifier. Returns False;
  3. Test for reading valid measurement (always > 0);
- **US25 - sensor that gives the average power consumption over a period (W):**
  1. Test for successful creation. Returns True;
  2. Test for duplicate creation based on its identifier. Returns False;
  3. Test for reading valid average power in a valid period (always > 0);
- **US26 - sensor that gives the electric energy consumption over a period (Wh):**
  1. Test for successful creation.
  2. Test for duplicate creation based on its identifier.
  3. Test for reading valid electric energy consumption in a valid period (always > 0);
- **US30 - Implement Domain Driven Design**
- **US31 - I want the system to support data persistence using JPA:**
  1. Testing for JPA implementation in a Main class or a Test without commit to the repository
- **US32 - I want the system to support data persistence using Spring:**
  1. Testing for Spring Data implementation in a Main class or a Test without commit to the repository
- **US33 - I want to get a list of all measurement of a device in a room, in a given period**
  1. Test for start limit bigger than lower limit
  2. Test for any of the limits being a future date
  3. Test for not having any device.
  4. Test for not have any logs in that period
  5. Test for bad period inputs
  6. Test for successful result
- **US34 - I want to get the maximum instantaneous temperature difference between a device in the room and the outside, in a given period**
  1. Test for start limit bigger than lower limit
  2. Test for any of the limits being a future date
  3. Test for not having any device.
  4. Test for not have any logs in that period
  5. Test for bad period inputs
  6. Test for successful result
- **US35 - I want the project to have a REST API (HTTP REST controllers, implemented using Spring Boot, including HATEOAS).**
  1. Test for successful creation and interaction with REST API.
  2. Test for proper implementation of HATEOAS.
  3. Test for endpoint accessibility and response correctness.
- **US36 - I want to get the peak power consumption of the house, in a given period.**
  1. Test for successful calculation of peak power consumption.
  2. Test for correct aggregation of data from multiple power meters.
  3. Test for accurate results over specified periods.
- **US37 - I want to close a blind roller that is in a room.**
  1. Test for successful command to close the blind roller.
  2. Test for correct state change of the blind roller.
  3. Test for handling multiple blind rollers in the same room.
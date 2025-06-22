import BaseLayout from "../components/BaseLayout/BaseLayout";
import Content from "../components/BaseLayout/Content";
import {useState} from "react";
import InputText from "../components/Form/InputText";
import Form from "../components/Form/Form";
import Dropdown from "../components/Form/DropDown.jsx";
import {useNavigate, useParams} from "react-router-dom";
import {Bounce, toast, ToastContainer} from "react-toastify";
import {saveSensor} from "../services/SensorService.jsx";
import {useDevice} from "../context/DeviceContext.jsx";
import {useSensor} from "../context/SensorContext.jsx";

const AddSensor = () => {
    const sensorModels = ["TemperatureSensor",
        "HumiditySensor",
        "ScaleSensor",
        "DewPointSensor",
        "SolarIrradianceSensor",
        "WindSensor",
        "InstantPowerConsumptionSensor",
        "AveragePowerConsumptionSensor",
        "ElectricEnergyConsumptionSensor",
        "SunriseSensor",
        "SunsetSensor",
        "BinarySwitch",
        "WindDirection"];

    const sensorModelsOptions = sensorModels.map((model) => ({
        label: model,
        value: model,
    }));
    const redirect = useNavigate();
    const {deviceId} = useParams();
    const devices = useDevice();
    const sensors = useSensor();
    const match = devices.devices.find((device) => device.deviceID?.toString() === deviceId);
    const [formInput, setFormInput] = useState({
        name: "",
        model: sensorModels[0],
        sensorType: "",
        deviceId: ""
    })

    const handleSubmit = async (event) => {
        event.preventDefault();
        const postRequest = saveSensor(formInput);
        toast.promise(
            postRequest,
            {
                pending: 'The Sensor is being created...',
                success: 'The Sensor has been created! Redirecting...',
                error: 'An Error has occurred!'
            }, {
                autoClose: 3000,
                theme: "colored",
                transition: Bounce,
            }
        ).then((data) => {
            setTimeout(() => redirect(-1), 3000)
            sensors.addData(data);
        });
    }

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setFormInput((prevInput) => ({
            ...prevInput,
            [name]: value,
            deviceId: match?.deviceID ?? ``,
        }));
    };

    const handleDropdownChange = (e) => {
        setFormInput((prevInput) => ({
            ...prevInput,
            model: e.target.value
        }));
    };

    return (
        <BaseLayout pageTitle={`Add a new sensor`}>
            <Content title="Sensor Information">
                <Form onSubmit={handleSubmit}
                      cancelText={"Cancel"}
                      cancelAction={() => redirect((-1))}>
                    <InputText
                        label="sensor name"
                        name="name"
                        value={formInput.name}
                        onChange={handleInputChange}
                        placeholder="Temperature, Humidity"
                        isRequired
                    />

                    <Dropdown
                        label="Sensor Model"
                        name="model"
                        value={formInput.model}
                        onChange={handleDropdownChange}
                        options={sensorModelsOptions}
                        isRequired
                    />

                    <div className="grid-form two-columns">

                        <InputText
                            label="sensor type"
                            name="sensorType"
                            value={formInput.sensorType}
                            onChange={handleInputChange}
                            placeholder="Thermometer (temperature), Thermal (humidity)"
                            isRequired
                        />

                        <InputText
                            label="device"
                            name="device"
                            value={match ? match?.deviceName : ""}
                            isDisabled
                        />
                    </div>
                </Form>
            </Content>
            <ToastContainer className="toast-container"/>
        </BaseLayout>
    );
};

export default AddSensor;
import BaseLayout from "../components/BaseLayout/BaseLayout";
import Content from "../components/BaseLayout/Content";
import {useState} from "react";
import InputText from "../components/Form/InputText";
import Form from "../components/Form/Form";
import {useNavigate, useParams} from "react-router-dom";
import Dropdown from "../components/Form/DropDown.jsx";
import {Bounce, toast, ToastContainer} from "react-toastify";
import {saveActuator} from "../services/ActuatorService.jsx";
import {useDevice} from "../context/DeviceContext.jsx";
import {useActuator} from "../context/ActuatorContext.jsx";

const AddActuator = () => {
    const actuatorModels = ["BlindRollerActuator",
        "SwitchOnOffActuator",
        "RangeActuatorInt",
        "RangeActuatorDecimal"];

    const actuatorModelsOptions = actuatorModels.map((model) => ({
        label: model,
        value: model
    }));

    const redirect = useNavigate();
    const {deviceId} = useParams();
    const devices = useDevice();
    const actuators = useActuator();
    const match = devices.devices.find((device) => device.deviceID?.toString() === deviceId);
    const [formInput, setFormInput] = useState({
        name: "",
        model: actuatorModels[0],
        actuatorType: "",
        deviceId: "",
        upperLimit: "",
        lowerLimit: "",
        upperLimitFractional: "",
        lowerLimitFractional: ""
    })

    const handleSubmit = async (event) => {
        event.preventDefault();
        let actuatorData = {...formInput};
        if (formInput.model === "RangeActuatorInt") {
            if (formInput.upperLimit <= formInput.lowerLimit) {
                toast.error('Upper Limit must be higher than Lower Limit');
                return;
            }
            actuatorData = {
                ...actuatorData,
                lowerLimit: formInput.lowerLimit,
                upperLimit: formInput.upperLimit
            };
        } else if (formInput.model === "RangeActuatorDecimal") {
            if (formInput.upperLimitFractional <= formInput.lowerLimitFractional) {
                toast.error('Upper Limit must be higher than Lower Limit');
                return;
            }
            actuatorData = {
                ...actuatorData,
                lowerLimitFractional: formInput.lowerLimitFractional,
                upperLimitFractional: formInput.upperLimitFractional
            };
        }
        const postRequest = saveActuator(actuatorData);
        toast.promise(
            postRequest,
            {
                pending: 'The Actuator is being created...',
                success: 'The Actuator has been created! Redirecting...',
                error: 'An Error has occurred!'
            }, {
                autoClose: 3000,
                theme: "colored",
                transition: Bounce,
            }
        ).then((data) => {
            setTimeout(() => redirect(-1), 3000)
            actuators.addData(data);
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
        const model = e.target.value;
        setFormInput((prevInput) => {
            const additionalFields = model === "RangeActuatorInt" ? {upperLimit: 0, lowerLimit: 0} :
                model === "RangeActuatorDecimal" ? {upperLimitFractional: 0.0, lowerLimitFractional: 0.0} :
                    {};
            return {
                ...prevInput,
                model,
                ...additionalFields
            };
        });
    };

    return (
        <BaseLayout pageTitle={`Add a new actuator`}>
            <Content title="Actuator Information">
                <Form onSubmit={handleSubmit}
                      cancelText={"Cancel"}
                      cancelAction={() => redirect((-1))}>
                    <InputText
                        label="actuator name"
                        name="name"
                        value={formInput.name}
                        onChange={handleInputChange}
                        placeholder="BlindRollerActuator"
                        isRequired
                    />

                    <Dropdown
                        label="Actuator Model"
                        name="model"
                        value={formInput.model}
                        onChange={handleDropdownChange}
                        options={actuatorModelsOptions}
                        isRequired
                    />

                    {formInput.model === "RangeActuatorInt" && (
                        <div className="grid-form two-columns">
                            <InputText
                                label="Lower Limit"
                                name="lowerLimit"
                                value={formInput.lowerLimit}
                                onChange={handleInputChange}
                                placeholder="Enter lower limit"
                                isRequired
                            />
                            <InputText
                                label="Upper Limit"
                                name="upperLimit"
                                value={formInput.upperLimit}
                                onChange={handleInputChange}
                                placeholder="Enter upper limit"
                                isRequired
                            />
                        </div>
                    )}

                    {formInput.model === "RangeActuatorDecimal" && (
                        <div className="grid-form two-columns">
                            <InputText
                                label="Lower Limit"
                                name="lowerLimitFractional"
                                value={formInput.lowerLimitFractional}
                                onChange={handleInputChange}
                                placeholder="Enter lower limit"
                                isRequired
                            />
                            <InputText
                                label="Upper Limit"
                                name="upperLimitFractional"
                                value={formInput.upperLimitFractional}
                                onChange={handleInputChange}
                                placeholder="Enter upper limit"
                                isRequired
                            />
                        </div>
                    )}

                    <div className="grid-form two-columns">

                        <InputText
                            label="actuator type"
                            name="actuatorType"
                            value={formInput.actuatorType}
                            onChange={handleInputChange}
                            placeholder="Shutter"
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

export default AddActuator;
import BaseLayout from "../components/BaseLayout/BaseLayout";
import Content from "../components/BaseLayout/Content";
import {useState} from "react";
import InputText from "../components/Form/InputText";
import Form from "../components/Form/Form";
import {useNavigate, useParams} from "react-router-dom";
import {Bounce, toast, ToastContainer} from "react-toastify";
import {useRoom} from "../context/RoomContext.jsx";
import {saveDevice} from "../services/DeviceService.jsx";
import {useDevice} from "../context/DeviceContext.jsx";

const AddDevice = () => {
    const redirect = useNavigate();
    const {roomId} = useParams();
    const devices = useDevice();
    const rooms = useRoom();
    const match = rooms.find((room) => room.roomId.toString() === roomId);
    const [formInput, setFormInput] = useState({
        deviceModel: '',
        isActive: true,
        deviceName: '',
        roomId: '',
    });

    const handleSubmit = (event) => {
        event.preventDefault();
        const postRequest = saveDevice(formInput);
        toast.promise(
            postRequest,
            {
                pending: 'The Device is being created...',
                success: 'The Device has been created! Redirecting...',
                error: 'An Error has occurred!'
            }, {
                autoClose: 3000,
                theme: "colored",
                transition: Bounce,
            }
        ).then((data) => {
            setTimeout(() => redirect(-1), 3000);
            devices.addData(data);
        });
    };

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setFormInput((prevInput) => ({
            ...prevInput,
            [name]: value,
            roomId: match?.roomId ?? ``,
        }));
    };

    return (
        <BaseLayout pageTitle={`Add a new device`}>
            <Content title="Device Information">
                <Form
                    onSubmit={handleSubmit}
                    cancelText={"Cancel"}
                    cancelAction={() => redirect(-1)}
                >
                    <InputText
                        label="device name"
                        name="deviceName"
                        value={formInput.name}
                        onChange={handleInputChange}
                        placeholder="Heater, Microwave, Fridge"
                        isRequired
                    />

                    <div className="grid-form two-columns">
                        <InputText
                            label="device model"
                            name="deviceModel"
                            value={formInput.model}
                            onChange={handleInputChange}
                            placeholder="Xiomi, Samsung, Apple"
                            isRequired
                        />

                        <InputText label="room" name="room"
                                   value={match ? match?.roomName : ""}
                                   isDisabled
                        />
                    </div>
                </Form>
            </Content>
            <ToastContainer className="toast-container"/>
        </BaseLayout>
    );
};

export default AddDevice;

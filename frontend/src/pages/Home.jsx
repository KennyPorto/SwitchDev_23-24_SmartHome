import HousesList from '../components/HousesList/HousesList';
import '../styles/layout.css';
import '../styles/main.css';
import BaseLayout from "../components/BaseLayout/BaseLayout.jsx";
import Content from "../components/BaseLayout/Content.jsx";
import {useHouse} from "../context/HouseContext.jsx";

const Home = () => {
    const {houses} = useHouse();


    return (
        <BaseLayout pageTitle="Welcome," subtitle={"My Smart Home!"}>
            <Content title="Houses">

                {houses?.map(house => (
                    <HousesList key={house.houseId} house={house}/>
                ))}

            </Content>
        </BaseLayout>
    );
};

export default Home;
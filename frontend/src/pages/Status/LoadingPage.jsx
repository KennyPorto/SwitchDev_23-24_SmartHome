import styles from "./status.module.css";
import PropTypes from "prop-types";
import Loading from "../../components/Loading/Loading.jsx";

const ErrorPage = () => {
    return (
        <main className={`${styles.center}`}>
            <Loading/>
        </main>
    );
};

export default ErrorPage;

ErrorPage.propTypes = {
    customError: PropTypes.string
}
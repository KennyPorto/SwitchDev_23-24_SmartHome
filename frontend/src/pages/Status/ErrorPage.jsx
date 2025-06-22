import {useRouteError} from "react-router-dom";
import styles from "./status.module.css";
import {Warning} from "@phosphor-icons/react";
import PropTypes from "prop-types";

const ErrorPage = ({customError}) => {
    const error = useRouteError();
    return (
        <main className={`fade-in ${styles.center} ${styles.error}`}>
            <Warning size={125} weight="duotone"/>
            <div>
                <h1>Error</h1>
                <i className={styles.errorText}>{error?.statusText || error?.message || customError}</i>
            </div>
        </main>
    );
};

export default ErrorPage;

ErrorPage.propTypes = {
    customError: PropTypes.string
}
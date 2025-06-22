import PropTypes from "prop-types";
import Button from "../Button/Button.jsx";

const Form = ({onSubmit, children, cancelText, cancelAction}) => {
    return (
        <form onSubmit={onSubmit} className="grid-form last-fade-down">
            {children}
            <div className="submit-buttons">
                <Button onClick={cancelAction} text={cancelText} isSecondary/>
                <Button type={"submit"} text={"Save"}/>
            </div>
        </form>
    );
};

Form.propTypes = {
    onSubmit: PropTypes.func.isRequired,
    children: PropTypes.node,
    cancelText: PropTypes.string,
    cancelAction: PropTypes.func
}

export default Form;

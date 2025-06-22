import PropTypes from "prop-types";

const Button = ({
                    type = "button",
                    isSecondary = false,
                    onClick,
                    text
                }) => {
    return (
        <button
            type={type}
            className={isSecondary ? "secondary" : ""}
            onClick={onClick}
        >
            {text}
        </button>

    )
}


export default Button;

Button.propTypes = {
    text: PropTypes.oneOfType([PropTypes.string, PropTypes.element]),
    onClick: PropTypes.func,
    isSecondary: PropTypes.bool,
    type: PropTypes.oneOf(["button", "submit", "reset"]),
}
import PropTypes from "prop-types";

const Dropdown = ({
                      label,
                      name,
                      value,
                      onChange,
                      options = [],
                      isRequired = false,
                      isDisabled = false
                  }) => {
    const upperCaseLabel = label.charAt(0).toUpperCase() + label.slice(1);
    const id = label.toLowerCase().trim().replace(" ", "-");
    return (
        <label htmlFor={id}>
            {upperCaseLabel}
            <select
                id={id}
                name={name}
                value={value}
                onChange={onChange}
                required={isRequired}
                disabled={isDisabled}
            >
                {options.map((option, index) => (
                    <option key={index} value={option.value || option.name}>
                        {option.label || option.name}
                    </option>
                ))}
            </select>
        </label>
    );
};

Dropdown.propTypes = {
    label: PropTypes.string,
    name: PropTypes.string,
    onChange: PropTypes.func,
    options: PropTypes.arrayOf(
        PropTypes.shape({
            label: PropTypes.string,
            value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
        })
    ),
    isRequired: PropTypes.bool,
    isDisabled: PropTypes.bool,
    value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};

export default Dropdown;

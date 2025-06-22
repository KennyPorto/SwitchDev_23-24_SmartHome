import PropTypes from "prop-types";

const InputText = ({
  label,
  name,
  value,
  onChange,
  placeholder = "",
  isRequired = false,
  isDisabled = false,
  type = "text",
}) => {
  const upperCaseLabel = label.charAt(0).toUpperCase() + label.slice(1);
  const id = label.toLowerCase().trim().replace(" ", "-");
  return (
    <label htmlFor={id}>
      {upperCaseLabel}
      <input
        type={type}
        id={id}
        name={name}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        required={isRequired}
        disabled={isDisabled}
      />
    </label>
  );
};

InputText.propTypes = {
  label: PropTypes.string,
  name: PropTypes.string,
  onChange: PropTypes.func,
  placeholder: PropTypes.string,
  isRequired: PropTypes.bool,
  isDisabled: PropTypes.bool,
  value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};

export default InputText;

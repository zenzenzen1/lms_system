import { useField, useFormikContext } from "formik";
import { InputText } from "primereact/inputtext";
import DatePicker from "react-multi-date-picker";
import Toolbar from "react-multi-date-picker/plugins/toolbar";
import { months, weekDays } from "src/configurations/common/date";
import { inputTextClassName } from "src/configurations/common/className";
import { Action } from "src/configurations/common/form";

type TextInputFieldType = {
    fieldName: string,
    type?: string,
    label: string,
    className?: string,
    validate?: boolean;
    placeholder?: string;
    disabled?: boolean;
}

export const FormikWrapper = (props: { children: React.ReactNode }) => {
    return (
        <div className="flex flex-col gap-2">
            {props.children}
        </div>
    );
}

export const DatePickerField = ({ fieldName, label, className = "", validate = true, disabled = false }: { fieldName: string, label: string, className?: string, validate?: boolean, disabled?: boolean }) => {
    const [field, meta] = useField({
        name: fieldName,
    });
    const { setFieldValue, validateField } = useFormikContext();
    return (
        <>
            <label className="form-label" htmlFor={fieldName}>{label}</label>
            <DatePicker
                disabled={disabled}
                autoFocus={false}
                weekDays={weekDays}
                months={months}
                value={new Date(field.value)}
                onChange={(date) => {
                    // convert to date string in ISO format with time is  00:00:00
                    const _date = date?.toDate();
                    if (_date) {
                        // console.log(((new Date(Date.UTC(_date.getFullYear(), _date.getMonth(), _date.getDate(), 0, 0, 0, 0))).toISOString()));
                        setFieldValue(fieldName, ((new Date(Date.UTC(_date.getFullYear(), _date.getMonth(), _date.getDate(), 0, 0, 0, 0))).toISOString()));
                    } else {
                        setFieldValue(fieldName, "");
                    }
                }}
                multiple={false}
                format="DD/MM/YYYY"
                // name={fieldName}
                render={(value, openCalendar, handleChange) => {
                    return (
                        <input {...field} disabled={disabled} className={inputTextClassName} readOnly value={value} onChange={handleChange} autoComplete="off" placeholder="" onClick={openCalendar} />
                    )
                }}
                plugins={[
                    <Toolbar
                        position="bottom"
                        names={{
                            close: "Đóng",
                            deselect: "Bỏ chọn",
                            today: "Hôm nay",
                        }}
                    />,
                ]}
            />
            {meta.touched ? (meta.error && Array.isArray(meta.error) ? meta.error.map((msg, i) => {
                return (
                    <small className="p-error d-block" key={i}>
                        {msg}
                    </small>
                )
            }) : (
                <small className="p-error d-block">{meta.error}</small>
            )) : null}
        </>
    );
}

export const RadioButtonField = ({ fieldName, label, options, className = "", validate = true, disabled = false }: { fieldName: string, label: string, options: { label: string, value: string }[], className?: string, validate?: boolean, disabled?: boolean }) => {
    const [field, meta] = useField({
        name: fieldName,
    });
    const { setFieldValue, validateField } = useFormikContext();
    return (
        <>
            <label className="form-label" htmlFor={fieldName}>{label}</label>
            <div className={`flex flex-wrap gap-2 ${className}`}>
                {options.map((option, index) => (
                    <div key={index} className="flex items-center">
                        <input
                            type="radio"
                            id={`${fieldName}-${option.value}`}
                            name={fieldName}
                            value={option.value}
                            checked={field.value + "" === option.value}
                            onChange={(e) => {
                                setFieldValue(fieldName, e.target.value);
                                // validate && validateField(fieldName);
                            }}
                            disabled={disabled}
                        />
                        <label htmlFor={`${fieldName}-${option.value}`} className="ml-2">{option.label}</label>
                    </div>
                ))}
            </div>
            {meta.touched ? (meta.error && Array.isArray(meta.error) ? meta.error.map((msg, i) => {
                return (
                    <small className="p-error d-block" key={i}>
                        {msg}
                    </small>
                )
            }) : (
                <small className="p-error d-block">{meta.error}</small>
            )) : null}
        </>
    );
}

export const SelectField = ({ onChange, fieldName, label, options, className = "", validate = true, placeholder, disabled = false }: { onChange?: () => void, fieldName: string, label: string, options: { label: string, value: string }[], className?: string, validate?: boolean, placeholder?: string, disabled?: boolean }) => {
    const [field, meta] = useField({
        name: fieldName,
    });
    const { setFieldValue, validateField, errors, values } = useFormikContext();
    return (
        <>
            <label className="form-label" htmlFor={fieldName}>{label}</label>
            <select disabled={disabled} {...field}
                onBlur={async (e) => {
                    await setFieldValue(fieldName, e.target.value);
                    await validateField(fieldName);
                    field.onBlur(e);
                }} name={fieldName} className={`${inputTextClassName} ${className}`}
                onChange={(e) => {
                    field.onChange(e);
                    if (onChange && typeof onChange === "function") {
                        onChange();
                    }
                }}
            >
                <option value="" disabled>{placeholder || "Chọn trạng thái"}</option>
                {options.map((option, index) => (
                    <option key={index} value={option.value}>{option.label}</option>
                ))}
            </select>
            {meta.touched ? (meta.error && Array.isArray(meta.error) ? meta.error.map((msg, i) => {
                return (
                    <small className="p-error d-block" key={i}>
                        {msg}
                    </small>
                )
            }) : (
                <small className="p-error d-block">{meta.error}</small>
            )) : null}
        </>
    );
}

export const TextInputField = ({ fieldName, type = "text", label, className = "", validate = true, placeholder, disabled = false }: TextInputFieldType) => {
    const [field, meta] = useField({
        name: fieldName,
        type: type
    });
    const { setFieldValue, validateField, errors } = useFormikContext();
    // console.log(errors);
    return (
        <>
            <label className="form-label" htmlFor={fieldName}>{label}</label>
            <InputText
                invalid={meta.touched && !!meta.error}
                disabled={disabled} {...field}
                onBlur={async (e) => {
                    if (e.target.value) {
                        const trimmed = e.target.value.trim();
                        await setFieldValue(fieldName, trimmed);
                        await validateField(fieldName);
                    }
                    field.onBlur(e);
                }}

                name={fieldName} placeholder={placeholder} type={type} className={`${inputTextClassName} ${className}`} />
            {/* <InputText value={field.value} name={fieldName} onBlur={field.onBlur} onChange={field.onChange} type={type} className={`${inputTextClassName} ${className}`} id={fieldName} placeholder={placeholder} /> */}
            {/* <FieldErrors formik={formik} name={fieldName} /> */}

            {/* {meta.touched && meta.error && <span>{meta.error}</span>} */}

            {meta.touched ? (meta.error && Array.isArray(meta.error) ? meta.error.map((msg, i) => {
                return (

                    <small className="p-error d-block" key={i}>
                        {msg}
                    </small>
                )
            }) : (
                <small className="p-error d-block">{meta.error}</small>
            )) : null}
        </>
    );
};

export const FormHeaderDialog = (props: { action: string, createHeader: string, updateHeader: string, detailHeader: string }) => {
    return <>
        {props.action === Action.detail ? props.detailHeader : (props.action === Action.create ? props.createHeader : props.updateHeader)}
    </>
};

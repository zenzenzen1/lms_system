
type FieldErrorsProps = {
    name: string;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    formik: any;
};

const FieldErrors = ({ name, formik }: FieldErrorsProps) => {
    const errorList = formik.errors[name];
    const touched = formik.touched[name];
    

    if (!touched) return null;

    return (
        <>
            {Array.isArray(errorList) ? errorList.map((msg, i) => (
                <small className="p-error d-block" key={i}>
                    {msg}
                </small>
            )) : (
                <small className="p-error d-block">{errorList}</small>
            )}
        </>
    );
};

export default FieldErrors;
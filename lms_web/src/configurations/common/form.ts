

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const handleTrimBlur = (e: React.FocusEvent<HTMLInputElement>, formik: any): undefined => {
    if (e.target.value) {
        const trimmed = e.target.value.trim();
        formik.setFieldValue(e.target.id, trimmed); // update Formik value
    }
    formik.handleBlur(e);
};

export type ActionTypes = typeof Action[keyof typeof Action];

export const Action = {
    detail: "detail",
    create: "create",
    update: "update"
}
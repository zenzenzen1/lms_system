import type { GetAllParameterType } from "../types/Type";

export const formatDate = (date: Date | string): string => {
    const d = new Date(date);
    const options: Intl.DateTimeFormatOptions = {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        // hour: '2-digit',
        // minute: '2-digit',
        // second: '2-digit',
        // timeZoneName: 'short'
    };
    return d.toLocaleDateString('vi-VN', options).replace(',', '');
}

export const formatDateTime = (date: Date | string): string => {
    const d = new Date(date);
    const options: Intl.DateTimeFormatOptions = {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        timeZoneName: 'short'
    };
    return d.toLocaleString('vi-VN', options);
}

export const isObjectEmpty = (obj: Record<string, unknown>): boolean => {
    return !obj || Object.keys(obj).length === 0 && obj.constructor === Object;
}

export const calculatePaging = (first: number, rows: number) : GetAllParameterType => {
    return { pageIndex: (first / rows) + 1, pageSize: rows }
} 
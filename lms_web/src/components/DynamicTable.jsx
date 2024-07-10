import { DataTable } from 'primereact/datatable';

import { Column } from 'primereact/column';
import "primereact/resources/primereact.min.css";
import "primereact/resources/themes/lara-light-indigo/theme.css";
import { useState } from 'react';
import { FilterMatchMode } from 'primereact/api';
import { InputText } from 'primereact/inputtext';

// id, name, age, city
const data = [
    {
        id: 1,
        name: 'John',
        age: 20,
        city: 'New York'
    },
    {
        id: 2,
        name: 'Doe',
        age: 25,
        city: 'Chicago'
    },
    {
        id: 3,
        name: 'Smith',
        age: 30,
        city: 'San Francisco'
    },
    {
        id: 4,
        name: 'Jane',
        age: 35,
        city: 'Los Angeles'
    },
    {
        id: 5,
        name: 'Doe',
        age: 40,
        city: 'Boston'
    },
    {
        id: 9,
        name: 'Doe',
        age: 60,
        city: 'San Diego'
    },
    {
        id: 10,
        name: 'Smith',
        age: 65,
        city: 'Las Vegas'
    },
    {
        id: 6,
        name: 'Smith',
        age: 45,
        city: 'Seattle'
    },
    {
        id: 7,
        name: 'Jane',
        age: 50,
        city: 'Denver'
    },
    {
        id: 8,
        name: 'John',
        age: 55,
        city: 'Miami'
    },

]

let DynamicTable = () => {
    const [filters, setFilters] = useState({
        global: {
            value: "",
            matchMode: FilterMatchMode.CONTAINS
        }
    });
    console.log(filters);
    return (
        <>
            <div>
                <InputText
                    value={filters.global.value}
                    onInput={e => {
                        setFilters(state => {
                            return (!e.target.value.trim() && !state.global.value) ? {...state} : (
                                {
                                    ...state,
                                    global: {
                                        ...state.global,
                                        value: e.target.value
                                    }
                                });
                        });
                    }}
                />

                {/* <Tooltip target=".p-datatable" content="hold control and you can choose multiple column to sort" 
                    my='' at="center" autoHide/> */}
                <DataTable className='p-datatable' value={data} sortMode='multiple' filters={filters} showGridlines
                    paginator
                    rows={5}
                    rowsPerPageOptions={[1, 2, 3, 4, 5]}
                    totalRecords={data.length}
                >
                    <Column style={{color: "red"}} field="id" header="ID" sortable />
                    <Column field="name" header="Name" sortable />
                    <Column field="age" header="Age" sortable />
                    <Column field="city" header="City" sortable />
                </DataTable>
            </div>
        </>
    );
}

export default DynamicTable;
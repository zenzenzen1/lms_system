import { Button } from 'primereact/button'
import { Column } from 'primereact/column'
import { DataTable } from 'primereact/datatable'
import { Dialog } from 'primereact/dialog'
import { Tag } from 'primereact/tag'
import React, { useState } from 'react'
import "./TableDemo.css"
import { Dropdown } from 'primereact/dropdown'
import { MultiSelect } from 'primereact/multiselect'

// name, country, agent, status
const initData = [
    {
        name: "James Butt",
        country: "United States",
        agent: "Ioni Bowcher",
        status: "unqualified",
    },
    {
        name: "Josephine Darakjy",
        country: "Egypt",
        agent: "Amy Elsner",
        status: "negotiation",
    },
    {
        name: "Art Venere",
        country: "Japan",
        agent: "Asiya Javayant",
        status: "qualified",
    },
    {
        name: "Lenna Paprocki",
        country: "Slovenia",
        agent: "Ivan Magalhaes",
        status: "new",
    },
    {
        name: "Hê Nhô Wua",
        country: "Vietnam",
        agent: "John Doe",
        status: "renewal",
    },
    {
        name: "kư rư mi",
        country: "Vietnam",
        agent: "John Doe",
        status: "renewal",
    },
    {
        name: "Nguyễn Văn A",
        country: "Vietnam",
        agent: "Ivan Magalhaes",
        status: "renewal",
    },
    {
        name: "徳田",
        country: "Japan",
        agent: "Asiya Javayant",
        status: "qualified",
    },
    {
        name: "Lê Đức Thanh",
        country: "Vietnam",
        agent: "John Doe",
        status: "renewal",
    },
    {
        name: "sldf hskd fhskd jhf kjasdh ksfdjh fksdj hf kjsad hfksljd hfkasjdh fkjsdh kfjsd h",
        country: "Vietnam",
        agent: "John Doe",
        status: "renewal",
    }
]

const Header = () => {
    return (
        <div>
            <h2 className='text-red-300'>Table Demo</h2>
        </div>
    )
}



const statusField = (rowData) => {
    return <Tag value={rowData.status} severity={getSeverity(rowData.status)} />;
}
const getSeverity = (status) => {
    switch (status) {
        case 'unqualified':
            return 'danger';

        case 'qualified':
            return 'success';

        case 'new':
            return 'info';

        case 'negotiation':
            return 'warning';

        case 'renewal':
            return null;
    }
};

const statusItemTemplate = (option) => {
    // console.log(option);
    return <Tag value={option} severity={getSeverity(option)} />;
};

const TableDemo = () => {
    const Footer = () => {
        return (
            <div>
                <h2>results: {data.length}</h2>
            </div>
        )
    }

    const [data, setData] = useState(initData);
    const [statuses, setStatuses] = useState(initData
        // .map(d => d.status)
        .reduce((acc, cur) => {
            if (acc.indexOf(cur.status) === -1) {
                acc.push(cur.status);
            }
            return acc;
        }, []));
    // console.log(statuses);
    // console.log('TableDemo');
    const [countries, setCountries] = useState(initData.reduce((prev, curr) => {
        if (prev.indexOf(curr.country) === -1) {
            prev.push(curr.country);
        }
        return prev;
    }, []));
    const [selectCountries, setSelectedCountries] = useState([]);

    const statusRowFilterTemplate = (options) => {
        return (
            <Dropdown
                value={options.value} options={statuses} onChange={(e) => {
                    return options.filterApplyCallback(e.value)
                }}
                itemTemplate={statusItemTemplate} placeholder="Select One"
                style={{ minWidth: '12rem' }} />
        );
    };

    const countryFilter = (options) => {
        return (
            // <Dropdown value={options.value} options={countries} onChange={(e) => { return options.filterApplyCallback(e.value)}}
            //     // itemTemplate={statusItemTemplate} 
            //     placeholder="Select One" className="p-column-filter" showClear
            //     style={{ minWidth: '12rem' }} />
            <MultiSelect className=''
                value={options.value} options={countries}
                onChange={(e) => {
                    console.log({ e });
                    return options.filterApplyCallback(e.value);
                    // setData(e.value.length === 0
                    //     ? initData
                    //     : initData.filter(d => {
                    //         console.log(e.value.indexOf(d.country));
                    //         return e.value.indexOf(d.country) !== -1
                    //     }));
                    // setSelectedCountries(e.value)
                }}
            />
        );
    }
    // statusRowFilterTemplate({ value: 'unqualified', filterApplyCallback: (value) => console.log(value) });
    
    
    return (
        <div className='card'>
            <MultiSelect className=''
                value={""} options={countries} onChange={(e) => { console.log(e.value); }}
            />
            <h2 className=''>Table Demo</h2>
            <div className='data-table'>
                <DataTable className='table table-striped table-hover table-'
                    value={data} showGridlines size='small' header={Header} footer={Footer} tableStyle={{ minWidth: '50rem' }} stripedRows
                    resizableColumns columnResizeMode='fit' reorderableColumns onColumnResizerDoubleClick={(e) => console.log(e)} onColumnResizerClick={(e) => console.log(e)}
                    // paginator rows={5} rowsPerPageOptions={[5, 10, 15]}
                    removableSort sortMode='multiple'
                    filterDisplay='row'
                >
                    <Column className='max-w-72 whitespace-normal'
                        sortable field="name" header="Name"
                        filter filterPlaceholder="Search by name" showFilterMenu={false} filterMatchMode='contains'
                    // rowSpan={2}

                    >
                    </Column>
                    <Column filter sortable field="country" header="Country"
                        filterMatchMode='in'
                        filterElement={countryFilter}
                        showFilterMenu={false}
                    ></Column>
                    <Column sortable field="agent" header="Agent"></Column>
                    <Column
                        filter filterElement={statusRowFilterTemplate} showFilterMenu={false}
                        sortable field="status" header="Status" body={statusField}
                    ></Column>
                    {/* <Column header="Action"
                        
                    >
                    </Column> */}
                </DataTable>
            </div>
            <Button className='pr-3 pl-3 pt-1 pb-1' label="Submit" icon="pi pi-check" severity='info' iconPos='right' />
        </div>
    )
}

export default TableDemo

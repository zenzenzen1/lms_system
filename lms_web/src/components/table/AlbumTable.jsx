import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { Dropdown } from 'primereact/dropdown';
import { InputText } from 'primereact/inputtext';
import React, { useEffect, useState } from 'react';
import "./AlbumTable.css";

const AlbumTable = () => {
    const [albums, setAlbums] = useState([]);

    useEffect(() => {
        albums.length === 0 && fetch('https://jsonplaceholder.typicode.com/albums')
            .then(response => response.json())
            .then(json => {
                setAlbums(json);
            });

        return () => {

        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])


    const editComplete = (e) => {
        // let _albums = [...albums];
        // let { newData, index } = e;
        // _albums[index] = newData;
        // setAlbums(_albums);

        console.log(e);
        fetch('https://jsonplaceholder.typicode.com/albums/' + e.data.id, {
            method: 'PUT',
            body: JSON.stringify(e.newData),
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            },
        })
            .then((response) => response.json())
            .then((json) => {
                console.log(json);
                setAlbums(a => {
                    const _a = [...a];
                    _a[a.findIndex(t => t.id === json.id)] = json;
                    return _a;
                })
            });
    };

    const allowEdit = (rowData) => {
        return true;
    };

    const textEditor = (options) => {
        return <InputText className='p-1 w-full' type="text" value={options.value} onChange={(e) => options.editorCallback(e.target.value)} />;
    };

    const userIdEditor = (options) => {
        return <Dropdown className='p-1 h-9 w-full' options={albums.reduce((prev, curr) => {
            return prev.indexOf(curr.userId) === -1 ? [...prev, curr.userId] : prev
        }, []
        )}
            value={options.value}
            onChange={(e) => options.editorCallback(e.value)}
        />
    }
    return (
        <div>
            <DataTable value={albums} size='small'
                rows={10} paginator stripedRows
                editMode='row' onRowEditComplete={editComplete}
            >
                <Column style={{ width: '10%' }} field="id" header="ID" ></Column>
                <Column style={{ width: '50%' }} field="title" header="Title" editor={(options) => textEditor(options)}></Column>
                <Column style={{ width: '20%' }} field="userId" header="User ID" editor={(options) => userIdEditor(options)}></Column>
                <Column rowEditor={allowEdit} headerStyle={{ alignItems: "center"}} header="Edit" className='w-1' bodyStyle={{ textAlign: 'center' }}></Column>
                <Column className='' body={<span className="pi pi-times"></span>} header="Remove"/>
            </DataTable>
        </div>
    )


}

export default AlbumTable

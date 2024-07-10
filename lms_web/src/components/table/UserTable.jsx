import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { Tree } from 'primereact/tree';
import React, { useEffect, useState } from 'react';
import treeDemoData from "./treeDemoData.json";
import "./UserTable.css"

const TreeNode = (node) => {
    // console.log(node);
    const addressNode = [{
        key: node.id + "",
        label: node.address.city,
        children: [
            {
                key: node.id + "-street",
                label: "street: " + node.address.street
            }
        ]
    }];
    return (
        <>
            <Tree value={addressNode} key={node.id} className="" ></Tree>
        </>
    )
}

const TestTree = () => {
    // const data = 
    // fetch("./treeDemoData.json").then(res => res.json()).then(d => console.log(d));
    const addressNode = [{
        key: 1 + "",
        label: "node.address.city",
        children: [
            {
                label: "node.address.street"
            }
        ]
    }];
    return (
        <>
            <Tree value={addressNode} className="w-full md:w-30rem"></Tree>
        </>
    )
}

const UserTable = () => {

    const [users, setUsers] = useState([]);
    const [selectedUsers, setSelectedUsers] = useState([])
    const [address, setAddress] = useState([])
    useEffect(() => {
        users.length === 0 && fetch('https://jsonplaceholder.typicode.com/users')
            .then(response => response.json())
            .then(json => {
                setUsers(json);
                setAddress(json.map((user, i) => {
                    return {
                        key: i + "",
                        label: user.address.city,
                        children: [

                        ]
                    }
                }));
            })

        return () => {

        }
    }, [])
    return (
        <div>
            {/* <div>
                <TestTree/>
            </div> */}
            <DataTable value={users} size='small' selectionMode={'single'} selection={selectedUsers} onSelectionChange={(e) => {
                setSelectedUsers(t => {
                    // console.log(t);
                    // t.push(e.value);
                    // return t;
                    return e.value;
                }
                )
            }}>
                <Column field="id" header="ID"></Column>
                <Column field="name" header="Name"></Column>
                <Column field="username" header="User Name"></Column>
                <Column field="email" header="Email"></Column>
                <Column field="phone" header="Phone"></Column>
                <Column body={TreeNode} header="Address"></Column>

            </DataTable>
        </div>
    )
}


export default UserTable

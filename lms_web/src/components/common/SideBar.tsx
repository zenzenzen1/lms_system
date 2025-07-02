import { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { SideBarTabs, SideBarType } from "src/types/type";

const SideBar = (props: { show: boolean, sidebarTabs: SideBarType[] }) => {
    const [activeTabs, setActiveTabs] = useState<number[]>([0]);
    const [currentTabChild, setCurrentTabChild] = useState<string>("");
    const [currentTab, setCurrentTab] = useState<SideBarTabs>();

    const handleToggleTab = (tabId: number) => {
        setActiveTabs((prev) => {
            const state = prev.includes(tabId) ? prev.filter((id) => id !== tabId) : [...prev, tabId];
            // if (prev.includes(tabId)) {
            //     return prev.filter((id) => id !== tabId);
            // } else {
            //     return [...prev, tabId];
            // }

            return state;
        });
    };
    
    const location = useLocation();
    useEffect(() => {
        let i: number = 0;
        function findParentComponentName(url: string): undefined | SideBarType {
            for (const tab of props.sidebarTabs) {
                // Direct match at top level
                if (tab.linkUrl === url) {
                    return tab;
                }
                // Check in children
                if (tab.children) {
                    for (const child of tab.children) {
                        if (child.linkUrl === url) {
                            return tab; // return parent componentName
                        }
                        i++;
                    }
                }
            }

            return undefined; // not found
        }
        const tab = findParentComponentName(location.pathname);
        if (tab) {
            setActiveTabs(prev => [...prev, props.sidebarTabs.findIndex(_tab => _tab.componentName === tab.componentName)]);
            setCurrentTab(tab.componentName as SideBarTabs);
            setCurrentTabChild((tab.children && tab.children.length > 0) ? (tab.children.find(c => c.linkUrl === location.pathname))?.componentName || "" : "");
        }
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])
    return (
        <>
            {
                props.show &&
                <nav id="sidebar" className="sidebar js-sidebar sticky left-0 top-0 z-10 h-screen transition-all duration-300 ease-in-out ">
                    <div className="sidebar-content js-simplebar">
                        <div className="w-[264px] bg-white flex flex-col overflow-x-hidden overflow-y-hidden hover:overflow-y-auto sidebar-scrollbar">
                            {/* <div className="pl-4 py-3.5">
                                logo
                            </div> */}
                            <div className="mt-2">
                                <ul className={`sidebar-nav `}>
                                    {props.sidebarTabs.map((tab, index) => (
                                        <li key={tab.componentName} className={`sidebar-item ${currentTab === tab.componentName ? "active" : ""}  `}>
                                            <Link data-bs-target={tab.children && tab.children.length > 0 ? `#${tab.name}` : undefined} data-bs-toggle={tab.children && tab.children.length > 0 ? "collapse" : undefined}
                                                className="sidebar-link hover:!bg-gray-200 text-[.96rem]" to={tab.children && tab.children.length > 0 ? "#" : tab.linkUrl}
                                                onClick={() => {
                                                    if (!tab.children || tab.children.length === 0) {
                                                        setCurrentTab(tab.componentName as SideBarTabs);
                                                        // setCurrentTab(index);
                                                    }
                                                    handleToggleTab(index);
                                                }}
                                            >
                                                <i className={`align-middle ${tab.icon}`}></i> <span className="align-middle">{tab.name}</span>
                                            </Link>
                                            {tab.children && tab.children.length > 0 && (
                                                <ul id={`${tab.name}`} className={`  sidebar-dropdown list-unstyled ${activeTabs.includes(index) ? "show" : "collapse"}`} data-bs-parent="#sidebar">
                                                    {tab.children.map((child) => (
                                                        child.name &&
                                                        <li key={child.componentName} className={`sidebar-item ${currentTabChild === child.componentName && currentTab === tab.componentName ? "active underline decoration-blue-500" : ""}`}
                                                            onClick={() => {
                                                                // props.setCurrentTab(child.componentName as SideBarTabs);
                                                                setCurrentTab(tab.componentName as SideBarTabs);
                                                                setCurrentTabChild(child.componentName);
                                                            }}
                                                        >
                                                            <Link className="sidebar-link text-[.91rem]!" to={child.children ? "#" : child.linkUrl} replace>{child.name}</Link>
                                                        </li>
                                                    ))}
                                                </ul>
                                            )}
                                        </li>
                                    ))}


                                </ul>

                            </div>
                        </div>
                    </div>
                </nav>
            }
        </>
    )
}

export default SideBar
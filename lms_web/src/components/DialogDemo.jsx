import { Button } from "primereact/button";
import { Dialog } from "primereact/dialog";
import { useState } from "react";

let DialogDemo = () => {
    const [visible, setVisible] = useState(false);
    const [visibleRight, setVisibleRight] = useState(false);

    return (
        <div style={{ display: "flex", gap: "2em" }}>
            <Button label="Show Modal" /* icon="pi pi-external-link" */ onClick={() => setVisible(true)} />
            <Dialog visible={visible} style={{ width: ' 50%' }} header="Header" modal onHide={() => setVisible(false)}
                draggable={false}
                dismissableMask={true}
                position="top-right"
            >
                <p>
                    Lorem ipsum dolor, sit amet consectetur adipisicing elit. Nemo, doloremque. Rerum earum, delectus fugiat voluptatem consectetur quidem sint amet voluptatibus harum nisi cumque maxime cum dolores nam odit. Ab, a.
                </p>
                <p>
                    sum dolor, sit amet consectetur adipisicing elit. Voluptates dolore eligendi dolorem et libero dolores, delectus id odio, a error omnis incidunt itaque mollitia molestias animi similique praesentium officiis eum.
                </p>
                
            </Dialog>

            <Button label="Show Right" onClick={() => setVisibleRight(true)} />
            <Dialog visible={visibleRight} style={{ width: "50%" }} position="right" header="Header Right"
                dismissableMask={true}
                onHide={() => setVisibleRight(false)} >
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Mollitia dolore qui velit. Enim cumque nulla corporis similique optio delectus molestias aspernatur officiis aliquam dolore laboriosam, nam numquam sed. Similique, veniam!
            </Dialog>
        </div>
    );
};

export default DialogDemo;
import SockJS from "sockjs-client";
import { Client, CompatClient, Stomp } from "@stomp/stompjs";
import { useEffect, useRef, useState } from "react";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";

const notificationUrl = import.meta.env.VITE_API_NOTIFICATION_WS_URL;

const WsDemo = () => {
    const [message, setMessage] = useState("");
    const [messages, setMessages] = useState<string[]>([]);
    const stompClient = useRef<CompatClient | null>(null);
    const clientRef = useRef<Client | null>(null);
    const [connected, setConnected] = useState(false);

    useEffect(() => {
        const client = new Client({
            brokerURL: notificationUrl,
            // webSocketFactory: () => new SockJS(notificationUrl),
            reconnectDelay: 5000,
            debug: (str) => console.log("STOMP: " + str), // 👀 enable debug logs
            onConnect: () => {
                console.log("✅ Connected");
                setConnected(true);

                client.subscribe("/topic/notification", (msg) => {
                    setMessages((prev) => [...prev, msg.body]);
                });
            },
            onStompError: (frame) => {
                console.error("❌ Broker error: ", frame.headers["message"], frame.body);
            },

        });

        client.activate();
        console.log("WebSocket client activated");
        clientRef.current = client;

        return () => {
            client.deactivate();
        };
    }, []);


    const sendMessage = () => {
        if (message && message.trim() && connected && clientRef.current) {
            clientRef.current.publish({
                destination: "/app/sendMessage",
                body: message.trim(),
            });
            setMessage("");
        }
    };
    
    return (
        <>
            <h3>Notifications</h3>
            <div>{messages.map((m, i) => <div key={i}>{m}</div>)}</div>
            <div className="flex gap-3">
                <InputText value={message} onChange={(e) => setMessage(e.target.value)} />
                <Button onClick={sendMessage} disabled={!connected}>
                    Send
                </Button>
            </div>
            <div>
                <InputText />
            </div>
        </>
    );
};


export default WsDemo
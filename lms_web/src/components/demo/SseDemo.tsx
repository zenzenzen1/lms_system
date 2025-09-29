import { useEffect, useState } from "react";

const SseDemo = () => {
    const [messages, setMessages] = useState<string[]>([]);
    const notificationUrl = import.meta.env.VITE_API_NOTIFICATION_URL;
    useEffect(() => {
        // Connect to SSE endpoint
        const eventSource = new EventSource(notificationUrl + "/sse/stream");

        eventSource.onmessage = (event) => {
            setMessages((prev) => [...prev, event.data]);
        };

        eventSource.onerror = (error) => {
            console.error("SSE error:", error);
            eventSource.close();
        };

        return () => {
            eventSource.close();
        };
    }, []);

    return (
        <div style={{ padding: "20px" }}>
            <h2>Spring Boot + React SSE Demo</h2>
            <ul>
                {messages.map((msg, idx) => (
                    <li key={idx}>{msg}</li>
                ))}
            </ul>
        </div>
    );
}

export default SseDemo
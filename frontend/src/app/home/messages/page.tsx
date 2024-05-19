"use client";

import { useState, useEffect } from "react";
import {
    getMessages,
    MessageListResponse,
    Message,
} from "@/deps/api_requests";
import Cookie from "js-cookie";
import MessageComponent from "../components/messages";
import { Boxes } from "../components/ui/background-boxes";

export default function MessagesPage() {
    const [messages, setMessages] = useState<Message[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const token: string | undefined = Cookie.get("token");
        if (!token) {
            return;
        }

        getMessages(token)
            .then((res: MessageListResponse) => {
                setMessages(res.messages);
                setLoading(false);
            })
            .catch((err) => {
                console.log("Failed to fetch messages");
                console.error(err);
                setLoading(false);
            });
    }, []);

    return (
        <div>
            <Boxes className="fixed inset-0 z-[-1]" />
            <div className="border-b-2 border-b-blue-50 mb-5 p-5">
                <h1 className="text-3xl font-bold">Direct Messages</h1>
            </div>
            {loading && <p>Loading messages...</p>}
            {!loading && messages.length === 0 && <p>No messages found.</p>}
            {!loading && messages.length > 0 && (
                <div>
                    {messages.map((message) => (
                        <MessageComponent
                            key={message.messageId}
                            message={message}
                        />
                    ))}
                </div>
            )}
        </div>
    );
}

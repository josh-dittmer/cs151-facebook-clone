// notification/page.tsx
"use client";

import { useState, useEffect } from "react";
import {
  getNotifications,
  NotificationListResponse,
  Notification,
} from "@/deps/api_requests";
import Cookie from "js-cookie";
import NotificationComponent from "../components/notification";

import { Boxes } from "../../home/components/ui/background-boxes";

export default function NotificationPage() {
  const [notifications, setNotifications] = useState<Notification[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const token: string | undefined = Cookie.get("token");
    if (!token) {
      return;
    }

    getNotifications(token)
      .then((res: NotificationListResponse) => {
        setNotifications(res.notifications);
        setLoading(false);
      })
      .catch((err) => {
        console.log("Failed to fetch notifications");
        console.error(err);
        setLoading(false);
      });
  }, []);

  return (
    <div>
      <Boxes className="fixed inset-0 z-[-1]" />
      <div className="border-b-2 border-b-blue-50 mb-5 p-5">
        <h1 className="text-3xl font-bold">Notifications</h1>
      </div>
      {loading && <p>Loading notifications...</p>}
      {!loading && notifications.length === 0 && <p>No notifications found.</p>}
      {!loading && notifications.length > 0 && (
        <div>
          {notifications.map((notification) => (
            <NotificationComponent
              key={notification.notificationId}
              notification={notification}
            />
          ))}
        </div>
      )}
    </div>
  );
}

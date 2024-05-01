import { Notification } from '@/deps/api_requests';

interface NotificationProps {
    notification: Notification;
}

const NotificationComponent: React.FC<NotificationProps> = ({ notification }) => {
    return (
        <div>
            {/* Render notification content here */}
            <p>{notification.action}</p>
        </div>
    );
};

export default NotificationComponent;
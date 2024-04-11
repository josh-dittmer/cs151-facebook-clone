import Link from 'next/link';

export default function HomeLayout({ children }: Readonly<{children: React.ReactNode;}>) {
    return (
        <div>
            <nav className="fixed top-0 z-50 w-full bg-white border-b border-gray-200">
            <div className="px-3 py-3 lg:px-5 lg:pl-3">
                <div className="flex items-center justify-between">
                    <div className="flex items-center justify-start rtl:justify-end">
                        <button data-drawer-target="sidebar" data-drawer-toggle="sidebar" type="button" className="inline-flex items-center p-2 mx-3 text-sm text-gray-500 rounded-lg sm:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 text-gray-400">
                            <span className="sr-only">Open sidebar</span>
                            <img src="/img/sidebar.png" className="w-6 h-6" />
                        </button>
                        
                        <h1 className="p-2 rounded bg-blue-500 text-white text-2xl text-bold">Facebook😂😂</h1>
                    </div>
                </div>
            </div>
            </nav>

            <aside id="sidebar" className="fixed top-0 left-0 z-40 w-64 h-screen pt-20 transition-transform -translate-x-full bg-white border-r border-gray-200 sm:translate-x-0">
            <div className="h-full px-3 pb-4 overflow-y-none bg-whit">
                <ul className="space-y-2 font-medium">
                    <li>
                        <Link href="/home/timeline" className="flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-100 group">
                            <img src="/img/home.png" className="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 group-hover:text-gray-900" />
                            <span className="flex-1 ms-3 whitespace-nowrap">Home</span>
                        </Link>
                    </li>
                    <li>
                        <Link href="/home/search" className="flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-100 group">
                            <img src="/img/search.png" className="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 group-hover:text-gray-900" />
                            <span className="flex-1 ms-3 whitespace-nowrap">Search</span>
                        </Link>
                    </li>
                    <li>
                        <Link href="/home/notifications" className="flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-100 group">
                            <img src="/img/notification.png" className="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 group-hover:text-gray-900" />
                            <span className="flex-1 ms-3 whitespace-nowrap">Notifications</span>
                            <span className="inline-flex items-center justify-center w-3 h-3 p-3 ms-3 text-sm font-medium text-blue-500 bg-blue-100 rounded-full">69</span>
                        </Link>
                    </li>
                    <li>
                        <Link href="/home/profile/me" className="flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-100 group">
                            <img src="/img/profile.png" className="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 group-hover:text-gray-900" />
                            <span className="flex-1 ms-3 whitespace-nowrap">My Profile</span>
                        </Link>
                    </li>
                </ul>
            </div>
            </aside>

            <div className="p-4 sm:ml-64">
            <div className="p-4 mt-14">
                <div className="block">
                    {children}
                </div>
            </div>
            </div>
        </div>
    );
}
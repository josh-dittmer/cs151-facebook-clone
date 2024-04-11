'use client';

import Image from 'next/image';

export default function SearchPage() {
    const handleTextChange = (e: any) => {
        console.log(e.target.value);
    };
    
    return (
        <div>
            <div className="border-b-2 border-b-blue-50 mb-5 p-5">
                <h1 className="text-3xl font-bold">Search</h1>
            </div>
            <div className="flex p-2 border-2 border-gray-500 rounded-full inner-focus-scale">
                <Image src="/img/search.png" width="30" height="30" alt="Search" />
                <input
                    type="text"
                    onChange={(e) => {handleTextChange(e)}}
                    placeholder="Search..."
                    className="ml-2 w-full focus:outline-none"
                />
            </div>
        </div>
    );
}
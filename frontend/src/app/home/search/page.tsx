'use client';

import Image from 'next/image';

export default function SearchPage() {
    const handleTextChange = (e: any) => {
        console.log(e.target.value);
    };
    
    return (
        <div>
            <div className="flex justify-center">
                <div className="flex p-2 border-2 border-gray-500 rounded-full inner-focus-scale w-3/4">
                    <Image src="/img/search.png" width="30" height="30" alt="Search" />
                    <input
                        type="text"
                        onChange={(e) => {handleTextChange(e)}}
                        placeholder="Search..."
                        className="ml-2 w-full focus:outline-none"
                    />
                </div>
            </div>
        </div>
    );
}
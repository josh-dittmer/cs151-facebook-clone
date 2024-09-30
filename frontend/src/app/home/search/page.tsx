"use client";

import { useState, useEffect } from "react";
import Image from "next/image";

import { search, getUserProfile, UserListResponse, User, UserProfileResponse } from "@/deps/api_requests";

import Cookie from "js-cookie";
import ProfileCardComponent from "../components/profile_card";

import { Boxes } from "../../home/components/ui/background-boxes";

export default function SearchPage() {
  const [userResults, setUserResults] = useState<React.ReactElement[]>([]);
  //const [numResults, setNumResults] = useState<number>(0);
  const [resultText, setResultText] = useState<string>('');

  useEffect(() => {
    const token: string | undefined = Cookie.get("token");
    if (!token) {
        return;
    }

    getUserProfile('0TcA9QwXjeVUG1RmyRMaYjldCE1hqh0zmiRmZnkxDHu40ZCksiLb3r5kxWhJIP71', token)
    .then((res: UserProfileResponse) => {
      setUserResults([<ProfileCardComponent key={res.user.userId} user={res.user} />])
      //setNumResults(1);
      setResultText('Recommended');
    })
    .catch((err) => {
      console.log('Failed to find recommended user');
      setUserResults([]);
    });
  }, [])

  const handleTextChange = (e: any) => {
    if(e.target.value === '') {
      setUserResults([]);
      setResultText('');
      return;
    }

    const token: string | undefined = Cookie.get("token");
    if (!token) {
        return;
    }

    search(e.target.value, token)
      .then((res: UserListResponse) => {
        const userResultArr: React.ReactElement[] = [];

        res.users.forEach((user: User) => {
          userResultArr.push(
            <ProfileCardComponent key={user.userId} user={user} />
          );
        });

        setUserResults(userResultArr);
        //setNumResults(userResultArr.length);
        setResultText('Results (' + userResultArr.length + ')');
      })
      .catch((err) => {
        console.log("Search failed!");
        console.log(err);
      });
  };

  return (
    <div>
      <div className="flex justify-center">
        <div className="flex p-2 border-2 border-gray-500 rounded-full inner-focus-scale w-3/4">
          <Image src="/img/search.png" width="30" height="30" alt="Search" />
          <input
            type="text"
            onChange={(e) => {
              handleTextChange(e);
            }}
            placeholder="Search..."
            className="ml-2 w-full focus:outline-none"
          />
        </div>
      </div>
      <div>
        {userResults.length > 0 && (
          <div className="border-b-2 border-b-blue-50 mb-5 p-5">
            <h1 className="text-xl font-bold">{resultText}</h1>
          </div>
        )}

        {userResults}
      </div>
    </div>
  );
}

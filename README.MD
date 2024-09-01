# Project Report + Final Check-in

## Team 4 
* Josh Dittmer, Nathan Wong, Brandon Ho, Joseph Hawkins

## Proposal Contributions
* Josh Dittmer: Project details, operations
* Nathan Wong: Project approach/design, project functionality
* Brandon Ho: Project details
* Joseph Hawkins: Operations


## Project Presentation Contributions
* Josh Dittmer: Application of Object Oriented Principles, Testing and error handling
* Nathan Wong: Class and Interface Design (Back-End)
* Brandon Ho: Class and Interface Design (Front-End)
* Joseph Hawkins: Explanation of Design Decisions


## Project Code Contributions
* Josh Dittmer: Main UI, backend requests/responses, database-related code, code structure/organization
* Nathan Wong: General frontend/backend contributions, messages, database-related code, code structure/organization
* Brandon Ho: General frontend/backend contributions
* Joseph Hawkins: General frontend/backend contributions, notifications, UI formatting


## Problems and Issues
* Problem
  * Creating an application that would allow users to communicate and share posts to other users on the same platform.
    * Initial Challenge: Setting up a integration between frontend and backend.

## Assumptions / Operating Environments / Intended usage 
* Assumptions:
   - the users are familiar with using social media sites such as facebook
   - UI facilitates easy navigation and usage
* Operating Environments
   - SQL database H2 will handle storage of user and program information
* Intended Usage
   - Social interaction and community building between users
   - Content creation using posts with texts and images

## Diagrams
* The diagrams are located here: https://github.com/josh-dittmer/cs151-facebook-clone/blob/main/diagrams/README.md

## Functionality
* Communication between the frontend and backend is achieved via an HTTP API
* The frontend sends the backend an HTTP request any time a user wishes to perform an an action
   * For example, logging in, creating/deleting a post, following/unfollowing a user, creating/deleting a comment, liking/unliking a post
* All requests to the backend are authenticated with a session ID, which is generated when the user logs in
* The backend stores information about posts, comments, users, sessions, etc. in an SQL database (H2)
* The frontend uses the Javascript fetch API to perform requests to the backend
* The backend uses a Java HTTP framework called Blade

## Operations
* User
   - Can sign in with existing account or sign up and create a new account
   - Create posts, and comment/like other posts
   - Follow/Unfollow other users
   - View timeline containing posts of people the user follows
   - Search up other users in the search bar
   - View notifications

## Solution
* Our project solves the problem of creating an application that allows users to communicate by:
   - Allowing users to share posts with captions and images
   - Allowing users to like other users' posts
   - Allowing users to comment on other users' posts
   - Allowing users to follow other users so their posts appear on their feed
   - Allowing users to search for other users by username or display name
   - Allowing users to customize their profile with a bio, profile picture, and display name
* All of these features contribute to creating a platform where users can communicate with each other and express themselves

## Steps to run the code
1) First clone the code from the github using the link: https://github.com/josh-dittmer/cs151-facebook-clone.git
2) Open the "backend" folder in IntelliJ
   * Note: For the project to work correctly, you must open the "backend" folder in IntelliJ, not the "cs151-facebook-clone" folder
3) Once the application is open, a popup on the lower right hand corner will show up saying "Maven Build Script Found". Click the Load Button to load the script.
4) Now head to the Application class. It is located at the path backend/src/main/java/apps/Application.java
5) Once you are at the Application.java file, run it. 
   * Note: If you are having trouble starting the application, please make sure the following folders exist in the app's working directory:
      * db/
      * static/
      * uploads/posts/
      * uploads/profile_pics/
   * The app will automatically create the database in the "db" folder if it doesn't already exist, but the folder must exist first
   * If you are having further issues, try deleting all files in the "db" directory and restart the backend
6) Now head to the terminal and go to the "frontend" directory. Run "npm install" (npm i framer-motion clsx tailwind-merge) if this is your first time running the program
7) Now once npm is installed type "npm run dev" into the terminal
8) A link should then pop up on the terminal. http://localhost:3000/login. Click it and it should lead you to the website.
   * Note: Going to http://localhost:3000/ will automatically redirect to http://localhost:3000/login
9) Congratulations! You are now at the login page. You will have to Sign-up if it is your first time. 

 > One last note, make sure both the backend or the frontend are running. Otherwise the program will not work properly!

## Snapshots of running program
* Link to running program snapshots: https://github.com/josh-dittmer/cs151-facebook-clone/blob/main/Working%20Code%20Snapshots/README.md

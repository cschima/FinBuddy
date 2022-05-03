# FinGrow

## What is FinGrow
FinGrow is a strategic financial consulting app that helps you achieve your goals through zero-fee finaical mentors.

### Problem Statement

How might low to middle income post-university young adults receive personalized financial guidance so that they can secure long-term financial stability?

### Reseach Findings
Young adults need pesonalized finaincial advice to make good long term finaical decicsions. They are often overwelmed and have the misconcpetion that they are below the required finaicial theshold to work with a financial advisor. They need a reliable,
knowledable finnaical mentor to keep them accountable and guide them in the right direction.

### Our Solution

A pro-bono financial consulting app that matches users based on their personalized needs. These pro-bono financial advisiors will provide long-term guidance and keep young adults accountable. 

### Key Features
- Advisor matching based on their needs
- Goal setting and tracking
- Scheduling with an advisor
- Chatting and video calling with an advisor
- Document sharing


## How to Contribute

### Technologies
- Android Studio (Kotlin)
- Dependencies:
    - Room Database
    - Material
    - Lifecycle
    - kotlinx coroutines
- Java Version 1.8
- Andriod 11, API 30
    - Minimum Andriod 5.0, API 21

### Design
- Application design is based on [Figma Prototype](https://www.figma.com/proto/sH2dhWzGftKnmQlpTOoKIE/INFO-490---FinGrow?node-id=940%3A5580&scaling=min-zoom&page-id=941%3A4480&starting-point-node-id=940%3A5580)

### Research
- User Research was conducted through surveys and interviews.
- We recommend loading app onto Andriod device for user testing for best results. 


### Running App
- Ensure you have installed all dependancies in build.gradle (Module)
- Create Device or pair physical device
    - Recommended API 30, Andriod 11
- Run ```'app'```

### Activities

- Landing Page: prompts user to Sign-up or login
- Sign-in/ Login: prompts user for username, email, and password
- Onboarding: asks users a series of questions related to their finances
- Main Activity: Main page holding access to other pages
    - Navbar fragments: Goals page (main), Doc, Chat, Profile
- New Goals: Allows user to add new goals

### Needed Work
- ```Chat```: Intended to use SendBird library for chat and video call integration.
- ```Link to bank```: Currently, money added towards a goal needs to be done manually and as no relation to their bank.
- ```Finincial Mentor side```: The current implementation is only for clients/mentees. There needs to be another implementation for mentors of the app
- ```Algorithm to match Mentors```:
- ```Scanning Docmuments```: Intended to use camera to photograph financial documents that could be stored and sent to finaincial mentors for review and guidance
- ```Hook up with cloud server```: All data is currently stored locally with Room database.
- ```Database Relationships```: Current database relationship not fleshed out. Needs review. 

## License
MIT License: ```LICENSE.txt```

## Contact Us

General: fingrowuw@gmail.com</br>

## Contributors:

### Team Point A to B 2021-2022
Camden Schiman, cschima@uw.edu</br>
Justin Ung, ungjus19@uw.edu</br>
Quynh Doan, qdoan@uw.edu</br>
Sophia Jung, sjung18@uw.edu

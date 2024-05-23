# TheScoreQAChallenge
Anthony's TheScore QA Automation Submission

## Get Started
### Install the necessary dependencies
  - Install HomeBrew if you do not have it already ```/bin/bash -c “$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)”```
  - Check for updates ```brew update```
  - Install LTS node using the command ```brew install node@20```
  > ![image](https://github.com/Antohknee/TheScoreQAChallenge/assets/48763075/a1f3e2ba-a720-4b6b-8b59-af5d0ca1a066)
  - Install Java 11 and set symlink for java
    - Install Java 11 using the following command: ```brew install java11```
    - Set symlink for Java 11 pointing to the downloaded JDK: ```sudo ln -sfn /usr/local/opt/openjdk@11/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-11.jdk```
    > Verify that running the command ```java --version``` gives something similar to the following: ![image](https://github.com/Antohknee/TheScoreQAChallenge/assets/48763075/c38df5d8-1004-4e34-a108-3ff4cc2677ab)
  - Install Maven using ```brew install maven```
  - Install Appium 2 using the command ```npm i --location=global appium```
  > Verify that is installed by doing ```appium --version``` and seeing if returns a version.

> IMPORTANT NOTE: For this project, I mainly focused testing on Android since I am running a Windows machine and XCode is unavailable to me. 
### Install Android Studio and Android Emulators
  - For this project [Android Studio Jellyfish](https://developer.android.com/studio) was used
  - Open Android Studio and select More Actions and then click Virtual Device Manager
  - Next in the top left of the Device Manager window that appears we will click the +
  - After in the new Virtual Device Configuration window we will be creating two new hardware profiles.

    - ![image](https://github.com/Antohknee/TheScoreQAChallenge/assets/48763075/b31461f5-9bd3-45c1-890d-9fa861bc757e)

    - ![image](https://github.com/Antohknee/TheScoreQAChallenge/assets/48763075/baa40b7b-076b-4cfd-b76a-28218a031dfe)
   
    - Source of dimensions: [S24](https://developer.samsung.com/galaxy-emulator-skin/galaxy-s.html) and [Tab S9](https://developer.samsung.com/galaxy-emulator-skin/galaxy-tab.html)

## How to Run the Tests
- In the Device Manager from before in Android Studio, launch one of the devices created and have it actively running. This is the local emulator that will be used when tests are ran. 
- For this project my main IDE was IntelliJ IDEA Ultimate (using free trial)
- Clone the repository and then ensure that all maven dependencies are installed
- Tests are located in the tests directory and all testcases include the suffix -Test
- To run tests individually, you can navigate to a test and right-click on the file and click on the option to "Run ... Test" or the green arrow button next to test function in the test class can be pressed to run the test.
- For now there is only one test suite setup with the tests but to run that this command can be used to run that specific TestNG suite ```mvn test -DsuiteXmlFile=testng.xml```

## Rationale Behind Test Design
- Appium can get messy very easily, very fast. To counteract that and to start early it is easier to separate the necessary WebElements into its respective page. This way it will avoid creating all WebElements in a single page (Java Class) and a huge clutter that will become almost unreadable as the file grows bigger. Similarly, each page can have its own respective functions according to the features located on that page (Ex. On Player page, a function could be verifying the stats/info of that player)
- Utils files are crucial helpers that are there to make life a bit easier using Appium. For anything that I believed were general helper classes/structs/enums or general purpose functions, they were placed in the util directory.
- Some key points that I ensured when testing were to validate text that I should be seeing on the screen. For example, if I am looking at the player profile of Lebron James, when I search his name and click his profile, I should verify that I am indeed on Lebron James' profile. Similarly, I wanted to focus verifying that data I knew would not change, since Lebron James is playing a sport, after every game his stats can change. Due to that, I chose to verify data that will remain consistent such as his birthday.
- For parts of the application that required swiping, I made an effort to use points and location relative to the device being tested to ensure it scales (so it doesn't just pass on one device and fail on another).
- In terms of how I found XPath locators I did this by using the Appium Web Inspector. Since I am operating on Windows, I have to use the Web Client.![image](https://github.com/Antohknee/TheScoreQAChallenge/assets/48763075/1d30ca91-3b4a-4d45-8027-378cde063714)
- To use data-driven testing, I tested my PlayerSearchTest across 3 NBA players, checking for expected behaviour across the 3 players and did the same thing across 3 NBA teams. 

## Test Results
- Samsung Galaxy S24:
![image](https://github.com/Antohknee/TheScoreQAChallenge/assets/48763075/29361d96-13d7-43a6-b6c5-da2c0e3f1425)

- Samsung Tab S9:
![image](https://github.com/Antohknee/TheScoreQAChallenge/assets/48763075/306fa3dd-98c2-413e-af3f-5096d12841a7)

## Next steps / Plans for Improvement:
- Include println's that tell us which elements were clicked
- Include more helper functions that can simplify tests.
- Create a wider coverage of tests.







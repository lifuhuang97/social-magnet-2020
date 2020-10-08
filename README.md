<!-- PROJECT LOGO -->
<br />
<p align="center">
  <h3 align="center">Social Magnet - Terminal Based Social App</h3>

  <p align="center">
    This is a school project that's built using Java and works only in the terminal
    <br />
    <br />
    <a href="https://github.com/lifuhuang97/social-magnet-2020/issues">Report Bug</a>
    ·
    <a href="https://github.com/lifuhuang97/social-magnet-2020/issues">Request Feature</a>
  </p>
</p>

<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Features](#features)
* [Cloning](#cloning)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Roadmap](#roadmap)
* [Contributing](#contributing)
* [Contact](#contact)

<!-- ABOUT THE PROJECT -->
## About The Project

This is a school project for the IS442 Object Oriented Programming course, it is completed in a team of 3.

Social Magnet is a terminal app that has features similar to Facebook - add friends, post on their profiles, write comments on posts. It also includes a minigame that's similar to FarmVille where you buy seeds, plant them and harvest them to gain experience and gold. Experience helps the user to level up and get more plots to plant seeds in, and gold allows users to buy higher tier seeds to plant and hence gain more experience. Users can also send seeds to their friends. All of the data are stored within a MySQL database.

However, Social Magnet is merely a proof of concept and doesn't function as intended - the database has to be installed locally and isn't shared so users can't actually add friends across machines. Also, this application would be a lot more functional if a frontend is developed but it couldn't be done with the limited time given for the project.

<!-- BUILT WITH -->
### Built With
* Java

<!-- USAGE EXAMPLES -->
## Features

### Social Features
<img src="https://user-images.githubusercontent.com/54570187/95410494-ada45e80-0956-11eb-851b-7617f415b1dc.png" width="1200px" />

### Farm Features
<img src="https://user-images.githubusercontent.com/54570187/95410769-69fe2480-0957-11eb-8db4-6ae193ec0ee2.png" width="1200px" />

### Farm Shop
<img src="https://user-images.githubusercontent.com/54570187/95410887-ba758200-0957-11eb-9f09-93af92fd27e3.png" width="1200px" />

### Farm Plot
<img src="https://user-images.githubusercontent.com/54570187/95410944-d1b46f80-0957-11eb-95e9-4619ab0551cd.png" width="1200px" />

<!-- CLONING -->
## Cloning

To get a local copy up and running follow the following steps.

### Prerequisites
* MAMP / WAMP
* Java
* MySQL Database

### Installation
1. Clone this repo
```sh
git clone https://github.com/lifuhuang97/social-magnet-2020.git
```

2. Run compile.bat (change any : into ; for Windows, vice versa for Mac)
```sh
./compile.bat (source compile.bat for Mac)
```

3. Run sql/deploy.sql in your local MySQL Database

4. Change port number of CONNECTION_STRING at src/main/java/project/utilities/DataUtility.java (line 16)

<img src="https://user-images.githubusercontent.com/54570187/95410369-5c946a80-0956-11eb-9c2f-7f3e8e5d147c.png" width="1300px" />

* NOTE: Mac - default is 8889

```java
public static final String CONNECTION_STRING = "jdbc:mysql://localhost:8889/" 
```
Uncomment lines 19,20 and comment line 23,24
<br>
* NOTE: Windows - default is 3306
```java
public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" 
```
Uncomment lines 23,24 and comment line 19,20

5. Run run.bat to get the app running (change any : into ; for Windows, vice versa for Mac)
```sh
./run.bat (source run.bat for Mac)
```

<!-- ROADMAP -->
## Roadmap

### Open Issues
See the [open issues](https://github.com/lifuhuang97/social-magnet-2020/issues) for a list of proposed features (and known issues).

<!-- CONTRIBUTING -->
## Contributing

Any contributions or suggestions as to how the project could be improved are **greatly appreciated**!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<!-- CONTACT -->
## Contact

Huang Lifu - lifuhuang97@gmail.com

Project Link: [https://github.com/lifuhuang97/social-magnet-2020/](https://github.com/lifuhuang97/social-magnet-2020/)

# ITS181-3_Dog-Adoption-App: DoggoForever

DoggoForever is a full stack mobile application. The app maintains records of dogs for adoption. Users may log in as an applicant or an admin. Admins can create, view, update, and delete dog record, while Applicants can view dog information and submit an application to adopt their chosen dog.

## Prerequisites

Please ensure that your device has the following software installed:
- JDK 21
- Apache Maven 
- MySQL Workbench
- Android Studio IDE

## Installation and Setup

To start, clone or download the main branch to your device. Take note of the project's location.

### Database

- In MySQL Workbench, create a schema named **"doggoforeverdb"**.
- Populate the schema by doing the following:
    - Go to Server > Data Import 
    - Select Import From Self-Contained File
    - Click the three dots and navigate to the project folder
    - Select the **doggoforever.sql** file and click Open
    - Click Start Import
- The data should be imported with no errors.

### Backend

- Using any text or code editor, open the **application.properties** file found at *backend\src\main\resources*
- Replace the **spring.datasource.password** property with your database connection's password.
- Using Command Prompt or any terminal, navigate to *ITS181-3_Dog-Adoption-App\backend* and execute the command **mvn spring-boot:run**
- The backend server should start successfully.

### Frontend

- Using Command Prompt, execute the command **ipconfig** and take note of your device's IPv4 address.
- In Android Studio, open the **frontend** folder found in the project files.
- In the following files, replace the current IP address (192.168.1.5) with your IPv4 address: 
    - app\java\com.example.appdev3_project\retrofit\\**RetrofitService.java**
    - app\java\com.example.appdev3_project\\**MyUtil.java**
    - app\res\xml\\**network_security_config.xml**
- Select your chosen virtual or physical device from the top of the screen (we recommend Pixel 6 API 30).
- Run the app. Your virtual or physical device should successfully install the program and display the homescreen.

## Existing Accounts

- For demo purposes, the app has been populated with existing user accounts with the following credentials:
    - Applicant Account
        - Email: user@gmail.com
        - Password: 123
    - Admin Account 
        - Email: admin@gmail.com
        - Password: 123
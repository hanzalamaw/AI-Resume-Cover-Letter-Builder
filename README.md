# **AI Resume & Cover Letter Builder**

## 📄 **Project Overview**

Welcome to the **AI Resume & Cover Letter Builder** project! This application is designed to help job seekers create professional resumes and cover letters in a matter of minutes. Powered by AI, the tool generates personalized application documents based on user input, ensuring that each document is tailored to the individual's experiences, skills, and job preferences.

This project is part of our **Object-Oriented Programming (OOP) course** for **Second Semester**. The goal is to apply core OOP principles in building a real-world application using Java for the backend, with JavaScript, HTML, CSS, and TailwindCSS for the frontend (frontend work is pending).

---

## ⚙️ **Features**

- **Dashboard**: Simple home screen allowing users to choose between generating a resume or a cover letter.
- **Input Forms**: Structured forms to collect essential details like experience, skills, education, and job preferences.
- **AI Text Generation**: Converts the user's data into a well-written, professional resume or cover letter using AI.
- **Preview**: Displays the AI-generated content before finalizing.
- **PDF Export**: Allows users to download the generated resume or cover letter as a professionally formatted PDF.
- **Error Handling**: Provides real-time feedback to ensure smooth processing.

---

## 🛠️ **Technologies Used**

- **Backend**: Java 
- **Frontend**: JavaScript, HTML, CSS
- **AI Integration**: Google Gemini API
- **PDF Generation**: iText library
- **Error Handling**: Custom error management

---

## 🤝 **Team Members**

- **Hanzala**
- **Anoosh**
- **Ashita**

---

## 🚀 **Installation**

To run this project locally, follow these steps:

### **1. Download the Project as a ZIP File**

Click on the green **"Code"** button above and select **"Download ZIP"** to get the project files.

### **2. Install Java Development Kit (JDK)**

This project uses **Java** for the backend, so make sure you have the **JDK** installed on your system.

- Download and install the **JDK** (version 8 or higher) from the official [Oracle website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
- After installation, verify your JDK setup by running the following command in your terminal:

```bash
java -version
```

### **3. Install Apache Maven**

The project uses **Maven** for dependency management. If Maven is not already installed, follow these steps:

- Download **Apache Maven** from the official [Maven website](https://maven.apache.org/download.cgi).
- Install Maven and verify the installation by running the following command:

```bash
mvn -version
```

### **4. Install Tomcat**

- Download **TOmcat** from the official [Tomcat website]([https://maven.apache.org/download.cgi](https://tomcat.apache.org/download-90.cgi)).
- Install Tomcat and run the following command in the terminal:

```bash
mvn clean package
```

### **5. Starting the Local Server**

This will create a `ai_resume.war` file in target folder, place that file in `tomcat\webapps\ai_resume.war`.

Then navigate to `tomcat\bin\` using `cd` and use this command in terminal to start the server on `localhost:8080`:

```bash
startup.bat
```

### **6. Run the Project**

Once the server is started, you can visit `http://localhost:8080/ai_resume/index.html` to view the AI Resume Builder.

## 📋 **Usage**

Once both the backend and frontend are running:

1. Go to `http://localhost:8080/ai_resume/index.html` in your browser to access the dashboard.
2. Choose whether you'd like to generate a **Resume** or **Cover Letter**.
3. Fill out the form with your details (experience, skills, etc.).
4. Preview the generated document.
5. Download the document as a **PDF**.

---

## 💻 **Contributing**

We welcome contributions! If you'd like to contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature-name`).
3. Make your changes and commit them (`git commit -am 'Add your feature'`).
4. Push to the branch (`git push origin feature/your-feature-name`).
5. Create a new Pull Request.

---

## 🌟 **Future Improvements**

Here are some ideas for future updates and features:

- **Multiple Resume Templates**: Allow users to select different resume templates for a customized look.
- **Cover Letter Customization**: Enable users to choose from different cover letter styles based on job type.
- **User Authentication**: Implement a user login system to save generated resumes and cover letters for future access.
- **Job Recommendation System**: Integrate a job matching feature to recommend job openings based on the user's resume data.
- **Multi-language Support**: Expand the application to support multiple languages for global users.

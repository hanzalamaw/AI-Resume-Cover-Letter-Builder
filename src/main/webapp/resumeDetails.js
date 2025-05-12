// FADE IN ANIMATION

document.addEventListener("DOMContentLoaded", function () {
    const sections = document.querySelectorAll(".fade-in-section");

    const observer = new IntersectionObserver(
        (entries, observer) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add("visible");
                    observer.unobserve(entry.target); 
                }
            });
        },
        { threshold: 0.2 } 
    );

    sections.forEach(section => {
        observer.observe(section);
    });
});

// RENDERING BASED ON THE CURRENT SESSION

const sections = ["personal", "education", "experience", "projects", "awards"];
let currentSectionIndex = 0;
const formData = {
    personal: {},
    education: "",
    experience: "",
    projects: "",
    awards: ""
};

const leftSections = document.querySelectorAll('.leftSection');
const rightTop = document.querySelector('.rightTop');
const form = document.getElementById('userData');
const rightInput = document.querySelector('.rightInput');
const submitBtn = document.querySelector('#submit button');

function renderSection() {
    leftSections.forEach((section, i) => {
        section.classList.remove('selected');
        if (i < currentSectionIndex) section.classList.add('done');
        if (i === currentSectionIndex) section.classList.add('selected');
    });

    const sectionName = sections[currentSectionIndex];
    rightTop.innerHTML = `<h1>${capitalize(sectionName)}</h1>`;

    let innerFields = '';
    if (sectionName === "personal") {
        innerFields = `
            <div class="rightSection"><label for="firstName">First Name</label><input type="text" id="firstName" value="${formData.personal.firstName || ''}" autocomplete="nope" required></div>
            <div class="rightSection"><label for="lastName">Last Name</label><input type="text" id="lastName" value="${formData.personal.lastName || ''}" autocomplete="off"  required></div>
            <div class="rightSection"><label for="profession">Profession</label><input type="text" id="profession" value="${formData.personal.profession || ''}" autocomplete="nope" required></div>
            <div class="rightSection"><label for="contactNo">Contact No</label><input type="text" id="contactNo" value="${formData.personal.contactNo || ''}" autocomplete="nope" required></div>
            <div class="rightSection"><label for="email">Email</label><input type="text" id="email" value="${formData.personal.email || ''}" autocomplete="nope" required></div>
            <div class="rightSection"><label for="address">Address</label><input type="text" id="address" value="${formData.personal.address || ''}" autocomplete="nope" required></div>
        `;
    } else {
        document.getElementById("userData").style.gridTemplateColumns = "1fr";
        const label = {
            education: "Describe your education",
            experience: "Describe your experience",
            projects: "Describe your projects",
            awards: "Describe your awards"
        }[sectionName];
        const value = formData[sectionName] || '';
        innerFields = `
            <div class="rightSection">
                <label for="${sectionName}">${label}</label>
                <textarea id="${sectionName}" rows="8" style="width: 95%; height: 150px; resize: none; padding: 0.7rem; margin-top: 0.1rem;" required>${value}</textarea>
            </div>
        `;
    }

    form.innerHTML = innerFields + `
        <div class="rightSection"><p>‎</p></div>
        <div id="submit"><button type="submit" style="cursor: pointer;">${currentSectionIndex === sections.length - 1 ? 'Generate' : 'Next Section'}</button></div>
    `;
}

function capitalize(str) {
    return str.charAt(0).toUpperCase() + str.slice(1).replace("/", " / ");
}

let firstName = "";
let lastName = "";

form.addEventListener('submit', function (e) {
    e.preventDefault();

    const current = sections[currentSectionIndex];
    if (current === "personal") {
        formData.personal = {
            firstName: document.getElementById('firstName').value,
            lastName: document.getElementById('lastName').value,
            profession: document.getElementById('profession').value,
            contactNo: document.getElementById('contactNo').value,
            email: document.getElementById('email').value,
            address: document.getElementById('address').value
        };

        firstName = document.getElementById('firstName').value;
        lastName  = document.getElementById('lastName').value;
    } else {
        formData[current] = document.getElementById(current).value;
    }

    if (currentSectionIndex < sections.length - 1) {
        currentSectionIndex++;
        renderSection();
    } else {
        fetch('/ai_resume/generateResume', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
            })
            .then(async (response) => {
            const data = await response.json();
            if (!response.ok) {
                throw new Error(data.error || 'Unknown server error');
            }
            let fullName = `${firstName}_${lastName}`;
            fullName = fullName.replaceAll(" ", "_");
            window.location.href = `http://localhost:8080/Resume/${fullName}_Resume.pdf`;
            })
            .catch(error => alert('Error: ' + error.message));
    } 
});

renderSection();
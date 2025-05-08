document.addEventListener("DOMContentLoaded", function () {
    const sections = document.querySelectorAll(".fade-in-section");

    const observer = new IntersectionObserver(
        (entries, observer) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add("visible");
                    observer.unobserve(entry.target); // Unobserve after first reveal
                }
            });
        },
        { threshold: 0.2 } // Adjust threshold for earlier/later triggering
    );

    sections.forEach(section => {
        observer.observe(section);
    });
});
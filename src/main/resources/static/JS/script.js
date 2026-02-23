console.log("Script loaded");
let currentTheme = getTheme();
document.addEventListener("DOMContentLoaded", () => {
    changeTheme();
})

function changeTheme() {
    document.querySelector("html").classList.add(currentTheme);

    const themebtn = document.querySelector("#theme_change_btn");
    themebtn.addEventListener("click", function () {
        document.querySelector("html").classList.remove(currentTheme);

        if (currentTheme === "dark") {
            currentTheme = "light";
        } else {
            currentTheme = "dark";
        }

        setTheme(currentTheme);
        document.querySelector("html").classList.add(currentTheme);
        themebtn.querySelector("span").textContent = currentTheme ==="dark" ? "Dark" : "Light";
    });
}

function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

function getTheme() {
    const theme = localStorage.getItem("theme"); // 👈 was missing this
    return theme ? theme : "light";
}
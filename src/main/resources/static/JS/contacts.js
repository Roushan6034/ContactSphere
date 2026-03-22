console.log("contacts.js loaded");
const baseURL = window.location.origin + "/";
document.addEventListener('DOMContentLoaded', function () {

    const viewContactModal = document.getElementById("view-contact-modal");
    if (!viewContactModal) {
        console.error("Modal element #view-contact-modal not found!");
        return;
    }

    const options = {
        placement: 'center',
        backdrop: 'dynamic',
        backdropClasses: 'bg-gray-900/60 backdrop-blur-sm fixed inset-0 z-40',
        closable: true,
        onShow: () => {
            // Re-trigger slide-up animation every time modal opens
            const panel = document.querySelector('#view-contact-modal .modal-panel');
            if (panel) {
                panel.classList.remove('modal-animate');
                void panel.offsetWidth; // force reflow to restart animation
                panel.classList.add('modal-animate');
            }
        },
        onHide: () => {
            const panel = document.querySelector('#view-contact-modal .modal-panel');
            if (panel) panel.classList.remove('modal-animate');
        },
    };

    const instanceOptions = { id: 'view-contact-modal', override: true };
    window.contactModal = new Modal(viewContactModal, options, instanceOptions);
});

function openContactModal() {
    if (window.contactModal) {
        window.contactModal.show();
    } else {
        console.error("Modal not initialized!");
    }
}

function closeContactModal() {
    if (window.contactModal) {
        window.contactModal.hide();
    } else {
        console.error("Modal not initialized!");
    }
}

function populateContactModal(data) {
    // Avatar
    const pic = document.getElementById("modal-picture");
    pic.src = data.picture && data.picture.trim() !== ""
        ? data.picture
        : `https://ui-avatars.com/api/?name=${encodeURIComponent(data.name || 'Contact')}&background=6366f1&color=fff&size=96`;

    // Name
    document.getElementById("modal-name").textContent = data.name || "—";

    // Email (text inside the span next to the icon)
    document.getElementById("modal-email-text").textContent = data.email || "—";

    // Favourite badge
    document.getElementById("modal-favorite-badge")
        .classList.toggle("hidden", !data.favorite);

    // Phone
    const phoneRow = document.getElementById("modal-phone-row");
    if (data.phoneNumber) {
        document.getElementById("modal-phone").textContent = data.phoneNumber;
        phoneRow.classList.remove("hidden");
    } else {
        phoneRow.classList.add("hidden");
    }

    // Address
    const addressRow = document.getElementById("modal-address-row");
    if (data.address) {
        document.getElementById("modal-address").textContent = data.address;
        addressRow.classList.remove("hidden");
    } else {
        addressRow.classList.add("hidden");
    }

    // Website
    const websiteRow = document.getElementById("modal-website-row");
    if (data.websiteLink && data.websiteLink.trim() !== "") {
        const websiteEl = document.getElementById("modal-website");
        websiteEl.href = data.websiteLink;
        websiteEl.textContent = data.websiteLink.replace(/^https?:\/\//, "");
        websiteRow.classList.remove("hidden");
    } else {
        websiteRow.classList.add("hidden");
    }

    // Description
    const descRow = document.getElementById("modal-desc-row");
    if (data.description) {
        document.getElementById("modal-description").textContent = data.description;
        descRow.classList.remove("hidden");
    } else {
        descRow.classList.add("hidden");
    }

    // Social links
    const linkedinEl  = document.getElementById("modal-linkedin");
    const instagramEl = document.getElementById("modal-instagram");
    const socialRow   = document.getElementById("modal-social-row");

    const hasLinkedin  = data.linkedinLink  && data.linkedinLink.trim()  !== "";
    const hasInstagram = data.instagramLink && data.instagramLink.trim() !== "";

    linkedinEl.href  = hasLinkedin  ? data.linkedinLink  : "#";
    instagramEl.href = hasInstagram ? data.instagramLink : "#";
    linkedinEl.classList.toggle("hidden",  !hasLinkedin);
    instagramEl.classList.toggle("hidden", !hasInstagram);
    socialRow.classList.toggle("hidden", !hasLinkedin && !hasInstagram);
}

async function loadContactdata(id) {
    console.log("Loading contact id:", id);
    try {
        const response = await fetch(`/api/contacts/${id}`);
        if (!response.ok) throw new Error(`Server error: ${response.status}`);

        const data = await response.json();
        console.log("Contact data:", data);

        populateContactModal(data);
        openContactModal();

    } catch (error) {
        console.error("Failed to load contact:", error);
    }
}
async function deleteContact(id) {

    Swal.fire({
        title: "Are you sure u want to delete this contact?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
    }).then((result) => {
        if (result.isConfirmed){
            console.log("Confirm deleted!");
            const url= `${baseURL}user/contacts/delete/`+id;
            window.location.replace(url);
        }
            // Swal.fire({
        //
        //     title: "Deleted!",
        //     text: "Your file has been deleted.",
        //     icon: "success"
        // });
    });
}
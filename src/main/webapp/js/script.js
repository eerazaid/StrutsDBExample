// modal.js
function openDeleteModal(userId) {
    document.getElementById('modalUserId').value = userId; // Set the user ID to delete
    document.getElementById('deleteModal').style.display = 'block'; // Show the modal
}

function closeDeleteModal() {
    document.getElementById('deleteModal').style.display = 'none'; // Hide the modal
}

// Close the modal when the user clicks anywhere outside of it
window.onclick = function(event) {
    var modal = document.getElementById('deleteModal');
    if (event.target === modal) {
        closeDeleteModal();
    }
};

function confirmDelete() {
    var userId = document.getElementById('modalUserId').value;
    // Add your delete logic here (e.g., send a request to delete the user)
    console.log("User deleted with ID:", userId);
    closeDeleteModal(); // Close the modal after deleting
}



// DataTable js
new DataTable('#example', {
    layout: {
        topStart: {
            buttons: ['copyHtml5', 'excelHtml5', 'csvHtml5', 'pdfHtml5']
        }
    }
});

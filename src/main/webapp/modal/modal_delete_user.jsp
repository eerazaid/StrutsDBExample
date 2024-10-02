
<!-- Modal Structure -->
<div id="deleteModal" class="modal" style="display: none;">
    <div class="modal-content">
        <span class="close" onclick="closeDeleteModal()">&times;</span>
        <h2>Confirm Deletion</h2>
        <p>Are you sure you want to delete this user?</p>
        <form id="deleteUserForm" action="deleteUser.do" method="post" style="text-align: center;">
            <input type="hidden" name="id" id="modalUserId" value=""/>
            <button type="button" class="submit_btn cancel_btn button-gap" onclick="closeDeleteModal()">Cancel</button>
            <input type="submit" value="Delete" class="submit_btn delete_btn button-gap"/>
        </form>
    </div>
</div>